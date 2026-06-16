# devtrack-server

## 目录结构

- src/：服务端源码（Spring Boot + MyBatis-Plus）
- db/sql/：数据库脚本（建表、初始化数据等）
- docs/：项目文档
- logs/：运行日志（已加入 .gitignore，不建议提交到仓库）

## 业务模块（源码分包）

- com.devtrack.common：通用能力（异常、返回体、工具、常量等）
- com.devtrack.config：配置（Security/MyBatis/Interceptor/DatabaseInitializer 等）
- com.devtrack.interceptor：拦截器
- com.devtrack.modules：按业务域拆分
  - user：用户与登录
  - rbac：角色/权限/项目权限校验
  - project：项目
  - member：项目成员
  - task：任务
  - dashboard：看板/统计
  - log：操作日志/登录日志
  - shared：通用 DTO/VO（分页、通用字典项等）

## 数据库自动初始化

项目启动时会自动执行数据库初始化检查：

1. **检查数据库**：检查本地MySQL中是否存在`devtrack`数据库，不存在则自动创建
2. **检查表结构**：遍历`db/sql/*.sql`文件中的所有建表语句，检查每个表是否存在
3. **自动创建缺失的表**：如果发现缺失的表，会自动执行对应的CREATE TABLE语句创建

### 配置说明

在`application.yml`中可以配置SQL文件路径：

```yaml
database:
  init:
    sql-path: classpath*:db/sql/*.sql  # 默认值，可根据需要修改
```

### 工作原理

- 首先尝试从classpath读取SQL文件（适用于打包后的jar）
- 如果classpath未找到，则从项目根目录的`db/sql/`读取（适用于开发环境）
- 支持多个SQL文件，会按文件名顺序解析所有建表语句

### 注意事项

- 数据库连接信息需要在`application.yml`中正确配置
- SQL文件应放在`db/sql/`目录下（项目根目录）或`src/main/resources/db/sql/`（classpath）
- 已存在的表不会被重新创建或修改，只会创建缺失的表
- 如果需要更新表结构，请手动执行ALTER TABLE语句
- 日志输出会显示哪些表已存在、哪些表被创建
