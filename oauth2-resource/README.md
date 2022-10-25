>OAuth2 资源服务器

# 支持类型

Spring Security 支持两种OAuth2的保护端点，一种是Jwt，一种是Opaque Tokens，资源服务器可以设置使用哪种类型（可以去我的博客去看）。

# 项目

## 创建受保护的资源
可以使用`@PreAuthorize("hasRole('ADMIN')")`等注解方式控制角色访问权限

# 测试

如果不提供token，则返回如下结果

![image-20220809133530137](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202208091335858.png)

通过获取到的token获取请求资源接口

![image-20220809143128548](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202208091431885.png)

---
请求基于注解权限的接口目前存在如下BUG:
### 用户角色无法获取
spring security5 默认的令牌校验逻辑只处理scope，而忽略了用户的授权信息。我又仔细看了下官方文档，没有相关设置。
但是对于Opaque模式的token可以使用自定义的方式来实现。JWT的方式目前我还没尝试如何解决。
解决方法如下：
资源服务器需要加上ResourceConfig配置类。
