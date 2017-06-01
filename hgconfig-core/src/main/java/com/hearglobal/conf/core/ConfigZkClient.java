package com.hearglobal.conf.core;

import com.hearglobal.conf.core.exception.HGConfigException;
import com.hearglobal.conf.core.util.Environment;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * ZooKeeper cfg client (Watcher + some utils)
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.01
 */
public class ConfigZkClient {
    /**
     * The constant logger.
     */
    private static Logger logger = LoggerFactory.getLogger(ConfigZkClient.class);

    /**
     * The constant zooKeeper.
     * zk client
     */
    private static ZooKeeper zooKeeper;
    /**
     * The constant INSTANCE_INIT_LOCK.
     */
    private static ReentrantLock INSTANCE_INIT_LOCK = new ReentrantLock(true);


    public static String env;

    public static String getEnv() {
        return env;
    }

    public static void setEnv(String env) {
        ConfigZkClient.env = env;
    }

    /**
     * Get instance zoo keeper.
     *
     * @return the zoo keeper
     * @since 2017.04.01
     */
    private static ZooKeeper getInstance() {
        initZKClient();
        if (zooKeeper == null) {
            throw new HGConfigException("ConfigZkClient ConfigZkClient.zooKeeper is null.");
        }
        return zooKeeper;
    }

    private static void initZKClient() {
        if (zooKeeper != null) {
            return;
        }
        try {
            if (INSTANCE_INIT_LOCK.tryLock(2, TimeUnit.SECONDS)) {
                try {
                    zooKeeper = new ZooKeeper(Environment.ZK_ADDRESS, 20000, new ConfigWatcher());
                    ConfigZkClient.createWithParent(Environment.CONF_DATA_PATH);    // init cfg root path
                } finally {
                    INSTANCE_INIT_LOCK.unlock();
                }
            }
        } catch (InterruptedException | IOException e) {
            throw new HGConfigException("ConfigZkClient init failed error:" + e.getMessage());
        }
    }

    private static class ConfigWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            try {
                logger.info("ConfigZkClient hgconfig: watcher:{}", watchedEvent);
                // session expire, close old and create new
                if (watchedEvent.getState() == Event.KeeperState.Expired
                        || watchedEvent.getState() == Event.KeeperState.Disconnected) {
                    zooKeeper.close();
                    zooKeeper = null;
                    getInstance();
                }

                String path = watchedEvent.getPath();
                String key = pathToKey(path);
                if (key != null) {
                    // add One-time trigger
                    zooKeeper.exists(path, true);
                    if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
                        ConfigClient.remove(key);
                    } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
                        String data = getPathDataByKey(key);
                        ConfigClient.update(key, data);
                    }
                }
            } catch (KeeperException | InterruptedException e) {
                throw new HGConfigException("ConfigZkClient ConfigWatcher process failed error:" + e.getMessage());
            }
        }
    }


    /**
     * path 2 key
     *
     * @param nodePath the node path
     * @return ZnodeKey string
     * @since 2017.04.01
     */
    private static String pathToKey(String nodePath) {
        if (nodePath == null || nodePath.length() <= Environment.CONF_DATA_PATH.length() || !nodePath.startsWith(Environment.CONF_DATA_PATH)) {
            return null;
        }
        return nodePath.substring(Environment.CONF_DATA_PATH.length() + env.length() + 2, nodePath.length());
    }

    /**
     * key 2 path
     *
     * @param configKey the node key
     * @return znodePath string
     * @since 2017.04.01
     */
    private static String keyToPath(String configKey) {
        return Environment.CONF_DATA_PATH + "/" + env + "/" + configKey;
    }

    /**
     * Generate group key string.
     *
     * @param appName   the node group
     * @param configKey the node key
     * @return the string
     * @since 2017.04.01
     */
    public static String generateAppConfigKey(String appName, String configKey) {
        return appName + "." + configKey;
    }

    /**
     * create node path with parent path (如果父节点不存在,循环创建父节点, 因为父节点不存在zookeeper会抛异常)
     *
     * @param path ()
     * @return the stat
     * @since 2017.04.01
     */
    private static Stat createWithParent(String path) {
        // valid
        if (path == null || path.trim().length() == 0) {
            return null;
        }

        try {
            Stat stat = getInstance().exists(path, true);
            if (stat == null) {
                //  valid parent, createWithParent if not exists
                if (path.lastIndexOf("/") > 0) {
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Stat parentStat = getInstance().exists(parentPath, true);
                    if (parentStat == null) {
                        createWithParent(parentPath);
                    }
                }
                // create desc node path
                zooKeeper.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            return getInstance().exists(path, true);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete path by key
     *
     * @param key the key
     * @since 2017.04.01
     */
    public static void deletePathByKey(String key) {
        String path = keyToPath(key);
        try {
            Stat stat = getInstance().exists(path, true);
            if (stat != null) {
                getInstance().delete(path, stat.getVersion());
            } else {
                logger.info("ConfigZkClient zookeeper node path not found :{}", key);
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * set data to node
     *
     * @param key  the key
     * @param data the data
     * @return path data by key
     */
    public static Stat setPathDataByKey(String key, String data) {
        String path = keyToPath(key);
        return setDataByKey(path, data);
    }

    public static String buildPath(String configKey, String env) {
        return Environment.CONF_DATA_PATH + "/" + env + "/" + configKey;
    }

    public static Stat setDataByKey(String path, String data) {
        try {
            Stat stat = getInstance().exists(path, true);
            if (stat == null) {
                createWithParent(path);
                stat = getInstance().exists(path, true);
            }
            return zooKeeper.setData(path, data.getBytes(), stat.getVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get data from node
     *
     * @param key the key
     * @return string
     * @since 2017.04.01
     */
    public static String getPathDataByKey(String key) {
        String path = keyToPath(key);
        try {
            Stat stat = getInstance().exists(path, true);
            if (stat == null) {
                logger.info("ConfigZkClient znodeKey[{}] not found.", key);
                return null;
            }

            String znodeValue = null;
            byte[] resultData = getInstance().getData(path, true, null);
            if (resultData != null) {
                znodeValue = new String(resultData);
            }
            return znodeValue;

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取配置目录下所有配置
     *
     * @return map
     * @since 2017.04.01
     */
    private static Map<String, String> getAllData() {
        Map<String, String> allData = new HashMap<>();
        try {
            List<String> childKeys = getInstance().getChildren(Environment.CONF_DATA_PATH, true);
            if (childKeys != null && childKeys.size() > 0) {
                for (String key : childKeys) {
                    String data = getPathDataByKey(key);
                    allData.put(key, data);
                }
            }
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return allData;
    }

    public static List<String > getAllKey(){
        try {
            List<String> childKeys = getInstance().getChildren(Environment.CONF_DATA_PATH + "/" + env, true);
            return childKeys;
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}