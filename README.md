# auth-service

> Spring Security OAuth2 Auth Server

# 版本说明

JDK11

Spring boot 2.6.13

Spring Authorization Server 0.3.1

Spring Security 5.7.2

# 项目说明

```tex
auth-service/
├── LICENSE
├── README.md
├── oauth2-login #OAuth2 Login 登录
├── oauth2-resource #资源服务器
├── oauth2-server #授权服务器
└── build.gradle
```

## 快速开始

1. 修改hosts文件<br>
    ```tex
    127.0.0.1 oauth2server
    127.0.0.1 oauth2login
    127.0.0.1 resourceserver
    ```
2. 启动oauth2-sever项目，请注意修改application.yaml下的数据库信息。
3. 创建数据库并执行db目录下的oauth2.sql数据库脚本
4. 如果想创建clientId信息，需要先在测试类里修改并执行`Oauth2ServerApplicationTests`
5. 启动资源服务器，接下来开始测试登录。具体请查看该项目下的README说明。
6. 启动oauth2-login，接下来开始测试登录。具体请查看该项目下的README说明。
