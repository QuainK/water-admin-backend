# water-sys-back-end

**水务用量管理系统 Water Sys 后端**

本工程使用 Maven 构建，使用 SpringBoot 开发。

更新日志请移步至 [CHANGELOG.md](./CHANGELOG.md "CHANGELOG.md")

---

## 一、环境依赖

- Java 11 (open-jdk-11)

  - 作者开发时使用 Java 11，实际使用 Java 8 及以上都可以。

- Maven 3.6.0

- SpringBoot 2.3.0

- MySQL 8.0.19

- SpringBoot 依赖：

  - SpringBoot Web

  - JDBC API

  - Spring Data JPA

  - MySQL Driver

---

## 二、主要功能

- 对前端请求做出响应，返回数据和/或执行一些其他动作。

- 对数据库的两个数据表进行读写，一个是水表的名称位置信息，另一个是水表的用量记录信息。

---

## 三、使用方法

### 1. 开发环境

1. 查看 Maven 配置文件 pom.xml，确保工程项目、SpringBoot、Java 等各项版本号正确。

   > /pom.xml

2. 在 application.yml 文件中设置好端口、数据库账号等。

   > /src/resources/application.yml

3. 运行 WaterSysBackEndApplication 类，位于

   > /src/main/java/org.qk.pyq.water/WaterSysBackEndApplication.java

### 2. 生产环境

1. 使用 Maven 的 Lifecycle 的 package 打包成 jar。

2. 生产环境的服务器开启数据库服务，然后运行此 jar。

---

## 四、工程结构

### 1. Entity 实体层

将数据库中数据表的字段映射成 Java 对象，提供 get/set 方法、toString 方法和构造方法等。数据库和数据表的设置在下文。

### 2. Repository 持久层

对数据库进行数据访问的 DAO 层。此层提供接口，SpringBoot 自动配置好基本的和 SQL 查询语句相关的方法。开发人员不用考虑实现具体方法，也可以根据特定的方法命名规则添加一些和 SQL 查询语句关联的方法。

### 3. Service 业务层

处理具体的业务，向下调用持久层的方法，向上返回结果给控制层。

### 4. Controller 控制层

接收前端请求，调用业务层去处理业务逻辑，将结果响应给前端。

### 5. Configuration 配置器

配置响应前端请求的过滤器和拦截器，给响应头加上内容。

---

## 五、数据库和数据表

- 数据库名 water_database

- 默认用户名 root

- 默认密码 root

- 所有编码 utf8mb4

### 1. 水表位置信息表 water_location

| 字段        | 类型    | 长度 | 小数点 | 非空 | 主键 | 自动递增 | 注释     |
| ----------- | ------- | ---- | ------ | ---- | ---- | -------- | -------- |
| water_id    | int     | -    | -      | √    | √    | √        | 水表编号 |
| name        | varchar | 255  | -      | √    | -    | -        | 水表名称 |
| longitude   | double  | 255  | 6      | √    | -    | -        | 位置经度 |
| latitude    | double  | 255  | 6      | √    | -    | -        | 位置纬度 |
| total_usage | double  | 255  | 2      | √    | -    | -        | 累计用量 |

**附注：** 累计用量字段是后端 0.3.0 开始真正使用的。

### 2. 水表用量记录表 water_record

| 字段          | 类型   | 长度 | 小数点 | 非空 | 主键 | 自动递增 | 注释     |
| ------------- | ------ | ---- | ------ | ---- | ---- | -------- | -------- |
| record_id     | int    | -    | -      | √    | √    | √        | 记录编号 |
| water_id      | int    | -    | -      | √    | -    | -        | 水表编号 |
| record_date   | bigint | 255  | -      | √    | -    | -        | 记录时间 |
| instant_usage | double | 255  | 2      | √    | -    | -        | 瞬时用量 |

---

## 六、其他

本工程的版本控制严格遵守[语义化版本](https://semver.org/lang/zh-CN/)。

本工程创建于 2020.04.28 19:05。
