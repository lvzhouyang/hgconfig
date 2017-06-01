# 《分布式配置管理平台》
## 一、简介
#### 1.1 概述
HGCONFIG是一个分布式配置管理平台，其核心设计目标是“为分布式业务提供统一的配置管理服务”。

地址:http://47.93.76.103:8282/

现在有基本的管理控制,需要添加用户的与@lvzy 联系
#### 1.2 特性
- 1、简单易用: 上手非常简单, 只需要引入maven依赖;
- 2、在线管理: 提供配置管理中心, 支持在线管理配置信息;
- 3、实时推送: 配置信息更新后, Zookeeper实时推送配置信息, 项目中配置数据会实时更新并生效, 不需要重启线上机器;
- 4、高性能: 系统会对Zookeeper推送的配置信息, 在Encache中做本地缓存, 在接受推送更新或者缓存失效时会及时更新缓存数据, 因此业务中对配置数据的查询并不存在性能问题;
- 5、配置备份: 配置数据首先会保存在Zookeeper中, 同时, 在MySQL中会对配置信息做备份, 保证配置数据的安全性;
- 6、HA: 配置中心基于Zookeeper集群, 只要集群节点保证存活数量大于N/2+1, 就可保证服务稳定, 避免单点风险;
- 7、分布式: 可方便的接入线上分布式部署的各个业务线, 统一管理配置信息;
- 8、配置共享: 平台中的配置信息针对各个业务线是平等的, 各个业务线可以共享配置中心的配置信息, 当然也可以配置业务内专属配置信息;
- 9、应用环境分离: 支持不同环境配置,支持应用独立配置

#### 1.3 maven地址
        <dependency>
            <groupId>com.hearglobal</groupId>
            <artifactId>hgconfig-core</artifactId>
            <version>1.3.4-SNAPSHOT</version>
        </dependency>

#### 1.4 使用
##### 1.4.1 初始化环境信息
在通过客户端获取配置信息前,需要指定配置环境
```java
    ConfigZkClient.setEnv("dev");
```
####$ 1.4.2 实例
```java
@Controller
public class IndexController {

    static {
        ConfigZkClient.setEnv("dev");
    }

    /**
     * 说明: API方式获取, 只需要执行ConfigUtilAdapter get相关方法 即可,
     * 在业务中使用比较方便 ,而且接受HGCONFIG实时推送更新。 同时因为底层有配置缓存,并不存在性能问题;
     */
    @RequestMapping("")
    @ResponseBody
    public String index() {

        String paramByClient = ConfigUtilAdapter.getString("hkcontent.test01");
        String result = "XML:<hr>hkcontent.test=" + ConfigUtilAdapter.getString("hkcontent.test");
        result += "<br><br><br>API:<hr>hkcontent.test01=" + paramByClient;
        result += "<br><br><br>API:<hr>hkcontent.switch=" + ConfigUtilAdapter.getBoolean("hkcontent.switch");
        return result;
    }
}
```
##### 1.4.3 一些场景
- 话术配置、"假常量"
- 参数动态调整,如果ES查询超时时间、图片压缩阈值、压缩质量比
- 简化某些功能开发,系统通知、简单权限控制
- 功能开关,服务开关,服务debug等环境管理
- 。。。。。。

##### 1.4.4 规则
新增的Key,不需要包含应用名,建议规则如下:
模块名.XXX.XXX.XXX

获取配置时,使用系统上生成的Config_Key


#### 1.5 架构
整体架构
![](http://onxhuf0hi.bkt.clouddn.com/all.jpeg)
---
客户端架构
![](http://onxhuf0hi.bkt.clouddn.com/client.jpeg)
---
ZK Watcher
![](http://onxhuf0hi.bkt.clouddn.com/QQ20170405-142039@2x.png)
环境管理
![](http://onxhuf0hi.bkt.clouddn.com/QQ20170405-170224@2x.png)
应用管理
![](http://onxhuf0hi.bkt.clouddn.com/QQ20170405-170535@2x.png)
配置管理
![](http://onxhuf0hi.bkt.clouddn.com/QQ20170405-170630@2x.png)

#### 更新说明
2017年4月18日
1、增加ConfPropertyPlaceholderConfigurer,支持Spring配置文件中解析${key}属性配置;
使用说明:
配置文件中增加ConfPropertyPlaceholderConfigurer的配置,指定环境
```xml
    <bean id="xxlConfPropertyPlaceholderConfigurer"
          class="com.hearglobal.conf.core.spring.ConfPropertyPlaceholderConfigurer">
        <property name="env" value="prod"></property>
    </bean>

    <!-- XML占位符方式使用示例,可删除 -->
    <bean id="configuration" class="com.hearglobal.conf.example.controller.Configuration">
        <property name="paramByXml" value="${win-customer.help.link}" />
    </bean>
```
特点:

上面配置说明: 在项目启动时, Configuration的paramByXml属性, 会根据配置的占位符${win-customer.help.link}, 去配置中心匹配KEY=key01的配置信息, 赋值给paramByXml;
目前, 该方式配置信息, 只会在项目启动时加载一次, 项目启动后该值不会变更。 例如配置数据连接信息, 如果平台中连接地址配置改边, 需要重启后才生效;
