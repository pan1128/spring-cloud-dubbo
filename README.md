# spring-cloud-dubbo
## 项目介绍
spring-cloud-alibaba、springboot、springcloud、dubbo项目脚手架，开箱即用

## 项目结构

``` lua

├── common -- 工具类及通用代码模块
├── gateway -- 网关微服务
├── user-service -- 用户微服务
├── order-service -- 订单微服务
└── config -- 配置中心存储的配置
```
## 技术选型

### 后端技术

| 技术                   | 说明                 | 版本                                                 |
| ---------------------- | -------------------- | ---------------------------------------------------- |
| Spring Cloud           | 微服务框架           | Hoxton.SR12            |
| Spring Cloud Alibaba   | 微服务框架           | 2.2.10-RC1     |
| Spring Boot            | 容器+MVC框架         | 2.3.12.RELEASE               |
| MyBatis                | ORM框架              | 3.5.5       |
| PageHelper             | MyBatis物理分页插件  | http://git.oschina.net/free/Mybatis_PageHelper       |
| Elasticsearch          | 搜索引擎             | https://github.com/elastic/elasticsearch             |
| RabbitMq               | 消息队列             | https://www.rabbitmq.com/                            |
| Redis                  | 分布式缓存           | https://redis.io/                                    |
| Redisson               | 分布式锁             | 3.22.0                                  |
| MongoDb                | NoSql数据库          | https://www.mongodb.com/                             |
| Docker                 | 应用容器引擎         | https://www.docker.com/                              |
| sentinel               | 服务治理框架         | 1.8.5                     |
| OSS                    | 对象存储             | https://github.com/aliyun/aliyun-oss-java-sdk        |
| MinIO                  | 对象存储             | https://github.com/minio/minio                       |
| JWT                    | JWT登录支持          | https://github.com/jwtk/jjwt                         |
| dubbo                  | 日志收集             | 3.2.0-beta.4 |
| Lombok                 | 简化对象封装工具     | https://github.com/rzwitserloot/lombok               |
| Seata                  | 全局事务管理框架     | 1.6.1                       |
