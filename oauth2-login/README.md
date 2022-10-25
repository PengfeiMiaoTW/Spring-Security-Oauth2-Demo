> Spring Security OAuth2 Login基本使用， 本文主要介绍下使用Github和自定义授权服务器登录功能。

![image-20220805151832899](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202208051518254.png)

## 说明

先启动oauth2-server项目，再启动oauth2-login项目，同时要修改hosts文件添加如下内容：

```tex
127.0.0.1 oauth2server
127.0.0.1 oauth2login
```
由于oauth2对于回调redirect_uri不允许本地回环地址。为了本地测试稳定性，有必要修改hosts。

请求oauth2-login项目地址: 
http://oauth2login:8082

输入用户名（user）密码（password）
![image-20220805135011406](https://itlab1024-1256529903.cos.ap-beijing.myqcloud.com/202208051350572.png)


# 附录
本初始化项目就是一个基本的OAuth2Login登录。
也确实如此，这得益于框架为我们做了很多东西，很多东西是框架默认配置的，两个重要的配置类是
OAuth2ClientAutoConfiguration 和 OAuth2WebSecurityConfiguration
