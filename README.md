# multi-datasource-demo
这是一个Demo, 主要关于springboot怎么支持多个数据源（多个不同的数据库）。
## 环境
+ JKD 1.8+
+ Windows, Linux
+ 嗯……好像没了
---
## 主要技术
+ dynamic-datasource-spring-boot-starter启动包，大佬封装好的多数据源支持工具包。
+ mybatis, 数据库框架，mybatis还是很强大的
+ webflux, springboot新推出的编程模型，没事可以多了解一下，与多数据源无关
+ lombok，一个工具包，想摆脱GetSet这些方法就靠它，十分好用的一个工具包。
---
## dynamic-datasource-spring-boot-starter介绍
项目地址：https://github.com/baomidou/dynamic-datasource-spring-boot-starter
### 项目导入
maven:
```xml
<dependency>
  <groupId>com.baomidou</groupId>
  <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
  <version>${version}</version>
</dependency>
```
gradle
```groovy
implementation 'com.baomidou:dynamic-datasource-spring-boot-starter:3.0.0'
```
### 配置说明
```yaml
server:
  port: 8080

spring:
  datasource:
    # hikari 数据连接池，springboot自身自带的，国内的好像都喜欢用阿里的druid数据连接池，这个其实也不差。
    # 更多的配置自行百度，我也不懂，手动滑稽
    hikari:
      # 最小空闲连接数量
      minimum-idle: 4
      # 最大连接数量
      maximum-pool-size: 16
      # 空闲超时
      idle-timeout: 30000
      connection-init-sql: set names utf8mb4
    dynamic:
      # 主要数据库，默认值是master, 这里配置为test_database_1, 作用是，如果没有通过 @DS 注解指定数据库的话，就默认使用这个配置
      primary: test_database_1
      datasource:
        # 这下面就是多数据源的配置
        # 数据源1，这里是在同一台机器上不同的数据库，条件有限。理论上是支持各种不同的数据源的。方便演示
        test_database_1:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/test_database_1?characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai
          username: root
          password: mysql
        test_database_2:
          driver-class-name: com.mysql.cj.jdbc.Driver
          url: jdbc:mysql://127.0.0.1:3306/test_database_2?characterEncoding=utf8&useSSL=true&serverTimezone=Asia/Shanghai
          username: root
          password: mysql

# mybatis的配置
mybatis:
  type-aliases-package: com.steven.www.multidatasourcedemo.entity
```
---
## 启动项目
### 创建数据库和表
分别创建数据库test_database_1和test_database_2
```sql
create database test_database_1;
use test_database_1;
CREATE TABLE `user` (
	`id` VARCHAR(64) NOT NULL,
	`NAME` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

create database test_database_2;
use test_database_2;
CREATE TABLE `user` (
	`id` VARCHAR(64) NOT NULL,
	`NAME` VARCHAR(50) NULL DEFAULT NULL,
	PRIMARY KEY (`id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
```
### 启动项目
```shell
./gradlew bootRun
```
---
## API示例
### 插入数据
URL: http://localhost:8080/user/add/{database}

**{database}是用来指定数据源**

Body: User， User对象，一个类。

请求方式：POST

示例：
```text
URL: http://localhost:8080/user/add/test_database_1
body：
{
    "name": "用户1"
}
```

### 查询数据
URL: http://localhost:8080/user/all

请求方式：GET

示例：
```text
http://localhost:8080/user/all
```