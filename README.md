# taskhub

基于 Spring Boot 的 TaskHub 项目骨架，已集成 MySQL、Redis、MyBatis-Plus 与 JWT 鉴权。

## 技术栈

- Spring Boot
- Spring Security
- JWT
- Redis
- MyBatis-Plus
- MySQL
- Lombok
- Swagger OpenAPI

## 认证能力

- 用户注册：用户名唯一、邮箱唯一（可选）、密码加密存储、默认普通用户角色
- 用户登录：用户名 + 密码登录，登录成功返回 JWT Token 与当前用户信息
- 当前用户：通过 JWT 解析登录身份并返回当前用户基本信息
- 单点登录：同一用户重复登录会刷新 Redis 中的会话标识，旧 Token 自动失效
- 权限缓存：用户角色权限缓存到 Redis，用于请求鉴权时快速读取

## 接口

- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/auth/me`

## Swagger

- 启动项目后访问 `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON 地址：`http://localhost:8080/v3/api-docs`
- 已放开 Swagger 相关路径，方便开发阶段联调与调试

## 配置

- JWT 配置位于 `application.yml` 的 `security.jwt`
- Redis 配置位于各环境文件的 `spring.data.redis`
- 数据库结构由手动维护，项目不使用 Flyway 自动迁移
