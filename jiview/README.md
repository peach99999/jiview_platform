JiView-Boot 系统基础平台
===============

## 后端技术架构
- 基础框架：Spring Boot 2.1.3.RELEASE

- 持久层框架：tk.mybatis 4.0.3

- 安全框架：Jwt

- 数据库连接池：阿里巴巴Druid 1.1.10

- 缓存框架：redis

- 日志打印：logback

- 分页查询：PageHelper

- 其他：fastjson，Swagger-ui，quartz, lombok（简化代码）等。



## 开发环境

- 语言：Java 8

- IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

- 依赖管理：Maven

- 数据库：MySQL5.5.16

- 缓存：Redis

 
## 命名规范（参考阿里巴巴Java开发手册）
-  获取单个对象的方法用 get 做前缀
-  获取多个对象的方法用 list 做前缀
-  获取统计值的方法用 count 做前缀
-  插入的方法用 save(推荐) 或 insert 做前缀
-  删除的方法用 remove(推荐) 或 delete 做前缀
-  修改的方法用 update 做前缀

## 编码规范（阿里编码规约扫描）
-  插件名称：Alibaba Java Coding Guidelines

## 代码检测（sonar扫描）
-  插件名称：sonarLint
