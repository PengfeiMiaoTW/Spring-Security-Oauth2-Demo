# 说明

`Spring Authorization Server` 遵循[Oauth2.1](https://datatracker.ietf.org/doc/html/draft-ietf-oauth-v2-1-05)和[OpenID Connect 1.0](https://openid.net/specs/openid-connect-core-1_0.html)，它建立在`Spring Security`之上。

# 最小化项目

## 快速开始
### 1. 授权码模式

#### 1.1 获取code

浏览器访问 http://127.0.0.1:8081，会打开登陆界面。填写用户名、密码后提交会跳转到如下地址：

`http://127.0.0.1:8080/oauth2/authorize?response_type=code&client_id=messaging-client&scope=message.read&redirect_uri=http://127.0.0.1:8080/authorized`

> 需要注意的是`redirect_uri`必须是`RegisteredClient` Bean实例中设置的对应地址（如果是用jdbc加载的注册信息，则看数据库中对应地址）。

![授权码模式登录界面](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231150833.png)

输入用户名(默认 user)、密码(默认 password）后

![授权界面](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231151478.png)

提交后，会自动跳转到`redirect_uri`地址，并且路径参数会带上`code`。

![redirect_uri回调后携带code字段](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231151774.png)

然后需要使用这个`code`获取token（可以使用postman模拟请求）。

#### 1.2 获取token

授权码获取token的请求地址是`oauth2/token`，post请求：

![请求体](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231151778.png)

这三个参数是必须的，并且要跟代码中设置完全一致，还要传递client_id和client_secret参数，默认不支持表单传递，所以通过header传递。

比如在postman中

![通过header传递client_id和client_secret](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231152534.png)

本质上是在header中传递了一个header，key=Authorization，value是client_id:client_secret，然后使用base64加密的字符串,然后前面加上`Basic `(注意后面有空格)。

返回结果如下：

```json
{
    "access_token": "eyJraWQiOiIxNGMxOTM5Yy02YzcxLTQ1MGMtOTg4OS1jOTdiNjM5NTE3ZmEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY1NDMyMzg0OSwic2NvcGUiOlsibWVzc2FnZS5yZWFkIl0sImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMTo4MDgwIiwiZXhwIjoxNjU0MzI0MTQ5LCJpYXQiOjE2NTQzMjM4NDl9.r6KSDrVbd65n_KRC2SnOH93nGYnoP2uWZwyiamke5PGWa72OHPxgwktgAxK0gHIjQ_sgh5tD4R2swb9bARIn2ZvUb3DtIXpLzEoCGRu4DqJoaUFnj71oAvX1MSruHeLqQaCwL2nJ-C-TNwj_mFHzcZFdaFZRQIIIkaG46Zgj1G0BCxpKtJy3FVIcbGJK-HYHHdh2XOMAIyCA5MrDn2VtZmJDwSbhSSEdU8jY8n41LPUd79koozIH_6onrx-y9ly3-evV3cAGBvsWA26h6PAR0Nxv47LXaUM5Hn_6OA20noCi53CC0qdahRJSs9eHpXsLd0rpjPDrk4nK9S7G0wTIlw",
    "refresh_token": "2CvlhRXdg6EK0ZzS_3kI-AI-AeCXBFpvD1krSbu28sTundjXnwvZT4AuQ03rtUr5TD2VFUWyuAJ68fAmNIonUVSRaDKzdx-Z2Z61np_HlcBF2iUxLRyl4JW9jeBQ7CZG",
    "scope": "message.read",
    "token_type": "Bearer",
    "expires_in": 299
}
```

#### 1.3 刷新token

![刷新token请求方式](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231152083.png)

结果是：

```json
{
    "access_token": "eyJraWQiOiIxNGMxOTM5Yy02YzcxLTQ1MGMtOTg4OS1jOTdiNjM5NTE3ZmEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY1NDMyNDU1OCwic2NvcGUiOlsibWVzc2FnZS5yZWFkIl0sImlzcyI6Imh0dHA6XC9cLzEyNy4wLjAuMTo4MDgwIiwiZXhwIjoxNjU0MzI0ODU4LCJpYXQiOjE2NTQzMjQ1NTh9.pOsWaoBrNrJyGTYOyvlN1d4FrKjpo2PxRIi7SHLfYjQ0xuqnuYaqPVOhs8rw9VN1hhjpl1d59RixOXkOAIK6PUI_-y_6MTmXL71YZ1lmrifhZ24bYkqXQKMAsbFvj3bXn6RyVnTwFsiy9IzZBRK_-PTPWQd9DbaYkmpryeZtGBqUFYAyBDrgCTYgw0SEoDI2qEX_W3Bgxiz9yTDH5Gszdbe0CzxvHP7LOGDi7-q-WziGhQCoMfFMK0P2WvzeAagseUEUpoSJTk8IMh-_8EgatrwilSYjkKKwgf_-hd9UXDi4bsW9MNA9iIDCYqKJ5dflTutoUJX8oxpnYTwP8iGNDA",
    "refresh_token": "2CvlhRXdg6EK0ZzS_3kI-AI-AeCXBFpvD1krSbu28sTundjXnwvZT4AuQ03rtUr5TD2VFUWyuAJ68fAmNIonUVSRaDKzdx-Z2Z61np_HlcBF2iUxLRyl4JW9jeBQ7CZG",
    "scope": "message.read",
    "token_type": "Bearer",
    "expires_in": 299
}
```

### 2. 简化模式

在oauth2.1中被移除

### 3. 客户端模式

#### 3.1 获取token

![客户端模式获取access_token](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231152276.png)

结果是：

```json
{
    "access_token": "eyJraWQiOiIxNGMxOTM5Yy02YzcxLTQ1MGMtOTg4OS1jOTdiNjM5NTE3ZmEiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJtZXNzYWdpbmctY2xpZW50IiwiYXVkIjoibWVzc2FnaW5nLWNsaWVudCIsIm5iZiI6MTY1NDMyNDc5Nywic2NvcGUiOlsib3BlbmlkIiwibWVzc2FnZS5yZWFkIiwibWVzc2FnZS53cml0ZSJdLCJpc3MiOiJodHRwOlwvXC8xMjcuMC4wLjE6ODA4MCIsImV4cCI6MTY1NDMyNTA5NywiaWF0IjoxNjU0MzI0Nzk3fQ.CMWqUxhjOlYzg6SY5uKkWIQDy96XV559TmG2YHZYlwe08a6u7xrwEm_b9m3rd9-QqkQpuxbFBD_o4dk3wl7PKVlZuWNCVrcvEXMFREexU6wwKtzTWKTBWYtDOAvKJN81iJ34UqsXRQ_M3xvUlpVXMjFKY9c3hsP9te8FpfcMi4IZfnHS79CunTh7tgovEo53nu9UNQ2qKy_MR9a13cXpe_AepOP_68gaLO-SAdRI-H9L4e57Y3w7Lq-UWUxywtnAtEcnm_PTGaA-gIEvCiN0rx6pZFBOxv-58OhNfp79oTN33yBDN-E3dSWgioQDp-Sc7kIb8z-rzXa1ZQgx19xTGg",
    "scope": "openid message.read message.write",
    "token_type": "Bearer",
    "expires_in": 299
}
```

客户端模式没有刷新token模式。

### 4. 密码模式

在oauth2.1中被移除

# 配置

## 1. 默认配置

`Spring Authorization Server`还提供了一种实现最小配置的默认配置形式。就是通过`OAuth2AuthorizationServerConfiguration`这个类。

这个类的源码路径为
`org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration`

`OAuth2AuthorizationServerConfiguration`类中注入一个叫做`authorizationServerSecurityFilterChain`的bean，有了这个bean，就会支持如下协议端点：

- [OAuth2 Authorization endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oauth2-authorization-endpoint)
- [OAuth2 Token endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oauth2-token-endpoint)
- [OAuth2 Token Introspection endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oauth2-token-introspection-endpoint)
- [OAuth2 Token Revocation endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oauth2-token-revocation-endpoint)
- [OAuth2 Authorization Server Metadata endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oauth2-authorization-server-metadata-endpoint)
- [JWK Set endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#jwk-set-endpoint)
- [OpenID Connect 1.0 Provider Configuration endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oidc-provider-configuration-endpoint)
- [OpenID Connect 1.0 UserInfo endpoint](https://docs.spring.io/spring-authorization-server/docs/current/reference/html/protocol-endpoints.html#oidc-user-info-endpoint)


## 2. 存储配置

`Spring Authorization Server`默认是支持内存和JDBC两种存储模式的，内存模式只适合开发和简单的测试。接下来我们来实现JDBC存储方式。

修改步骤如下：
1. 引入JDBC相关依赖。
2. 创建数据库并初始化表，以及在`application.yaml`文件中配置数据库连接。
3. 修改`Spring Security`和`Spring authorization Server`的配置。
4. 初始化表数据
5. 测试服务

## 3. 自定义jwt字段

jwt令牌增加一个自定义header和claim，使用`OAuth2TokenCustomizer`实现。


# OpenID Connect 1.0协议

`Spring Authorization Server`支持OAuth2.1协议，同时也支持`OpenID Connect 1.0`协议，该协议是`OAuth2`协议的上层协议

默认是不支持该协议的，需要单独进行配置。

## 1. 用户端点

对`AuthorizationServerConfig`配置类中的`authorizationServerSecurityFilterChain`实例做相关修改：
调用
```
authorizationServerConfigurer.oidc(oidc -> {
    oidc.userInfoEndpoint(...)
    ...
})
```
注册用户端点 ，同时client（如 trello-auth）设置的时候在scope中增加对openid的支持。

访问`localhost:8081/userinfo`，通过token，获取OIDC的用户端点：

![image-20220702214350843](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231153021.png)

这里的sub就是用户的标志，可以增加更多自定义信息。

## 2. 客户端注册端点

OpenID Connect 1.0客户端注册端点默认禁用，因为许多部署不需要动态客户端注册。
增加如下配置
```
authorizationServerConfigurer.oidc(oidc -> {
    ...
    oidc.clientRegistrationEndpoint(Customizer.withDefaults());
})
```
注册客户端信息。

请注意：

![image-20220703213506592](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202207231154932.png)


---

> Spring Authorization Server目前在不断完善中。
>
> 如果有人对openid connect1.0协议不了解，建议查看https://openid.net/connect/。
