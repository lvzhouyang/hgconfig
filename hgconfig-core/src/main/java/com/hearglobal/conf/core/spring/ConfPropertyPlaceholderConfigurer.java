package com.hearglobal.conf.core.spring;

import com.hearglobal.conf.core.ConfigUtilAdapter;
import com.hearglobal.conf.core.ConfigZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.util.StringValueResolver;

import java.util.Properties;

/**
 * The type Conf property placeholder configurer.
 * 配置解析器
 *
 * @author lvzhouyang
 * @version 1.0
 * @since 2017.04.18
 */
public class ConfPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static Logger logger = LoggerFactory.getLogger(ConfPropertyPlaceholderConfigurer.class);

    private String env;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        ConfigZkClient.setEnv(env);

        // init value resolver
        StringValueResolver valueResolver = new StringValueResolver() {
            String placeholderPrefix = "${";
            String placeholderSuffix = "}";

            @Override
            public String resolveStringValue(String strVal) {
                StringBuffer buf = new StringBuffer(strVal);
                // loop replace by xxl-conf, if the value match '${***}'
                boolean start = strVal.startsWith(placeholderPrefix);
                boolean end = strVal.endsWith(placeholderSuffix);
                while (start && end) {
                    // replace by xxl-conf
                    String key = buf.substring(placeholderPrefix.length(), buf.length() - placeholderSuffix.length());
                    String zkValue = ConfigUtilAdapter.getString(key);
                    buf = new StringBuffer(zkValue);
                    logger.info(">>>>>>>>>>> resolved placeholder '" + key + "' to value [" + zkValue + "]");
                    start = buf.toString().startsWith(placeholderPrefix);
                    end = buf.toString().endsWith(placeholderSuffix);
                }
                return buf.toString();
            }
        };

        // init bean define visitor
        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);

        // visit bean definition
        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        if (beanNames != null && beanNames.length > 0) {
            for (String beanName : beanNames) {
                if (!(beanName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
                    BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(beanName);
                    visitor.visitBeanDefinition(bd);
                }
            }
        }

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
        super.setIgnoreUnresolvablePlaceholders(true);
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}