package com.cn.thoughtworks.auth.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.UUID;

@SpringBootTest
class Oauth2ServerApplicationTests {

    /**
     * 初始化客户端信息
     */
    @Autowired
    private UserDetailsManager userDetailsManager;

    /**
     * 创建用户信息
     */
    @Test
    void testSaveUser() {
        if (!userDetailsManager.userExists("user")) {
            UserDetails userDetails =
                    User.builder().passwordEncoder(s -> "{bcrypt}" + new BCryptPasswordEncoder().encode(s))
                            .username("user")
                            .password("password")
                            .roles("ADMIN")
                            .build();
            userDetailsManager.createUser(userDetails);
        }
    }

    /**
     * 创建clientId信息
     */
    @Autowired
    private RegisteredClientRepository registeredClientRepository;

    @Test
    void testSaveClient() {
        if (registeredClientRepository.findByClientId("trello-auth") == null) {
            RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientId("trello-auth")
                    .clientSecret("{bcrypt}" + new BCryptPasswordEncoder().encode("trello-auth"))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                    .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                    .redirectUri("http://oauth2login:8082/login/oauth2/code/trello-auth")
                    .scope(OidcScopes.OPENID)
                    .scope("message.read")
                    .scope("message.write")
                    .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                    .build();
            registeredClientRepository.save(registeredClient);
        }
    }

}
