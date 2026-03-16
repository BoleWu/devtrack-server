# devtrack-server

## 目录结构

- src/：服务端源码（Spring Boot + MyBatis-Plus）
- db/sql/：数据库脚本（建表、初始化数据等）
- docs/：项目文档
- logs/：运行日志（已加入 .gitignore，不建议提交到仓库）

## 业务模块（源码分包）

- com.devtrack.common：通用能力（异常、返回体、工具、常量等）
- com.devtrack.config：配置（Security/MyBatis/Interceptor 等）
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
