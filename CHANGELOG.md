# 更新日志 Changelog

## Unreleased

### 增加 Added

### 修复 Fixed

### 修改 Changed

### 优化 Refactored

### 移除 Removed

---

## 0.2.0 - 2020.05.29

### 增加 Added

- 定时遍历水表为每个水表生成一个随机记录。目前默认的是每分钟第 0 秒执行一次定时计划任务。

### 优化 Refactored

- Service 业务层从 Controller 控制层分离出来。

- 增加和更新记录的业务方法高度相似，合并到一个方法中。添加操作不需要提供主键，参数为 null，更新操作需要提供主键，来自前端请求中的参数。

- 各层引用对象不使用 Autowired 注解，改用构造方法。

---

## 0.1.0 - 2020.05.16

### 增加 Added

- 能够响应前端的获取 GET、增加 POST、更新 PUT、删除 DELETE 四种请求，返回需要的数据。
