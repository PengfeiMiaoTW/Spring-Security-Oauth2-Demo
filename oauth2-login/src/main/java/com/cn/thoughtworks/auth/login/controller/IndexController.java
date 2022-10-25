package com.cn.thoughtworks.auth.login.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 登录成功后才能访问到用户信息和授权信息等
     *
     */
    @GetMapping("/")
    public String index(Model model,
                        @RegisteredOAuth2AuthorizedClient("trello-auth") OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User) {
        System.out.println(authorizedClient.getAccessToken().getTokenValue());
        model.addAttribute("authorizedClient", authorizedClient);
        model.addAttribute("oauth2User", oauth2User);
        return "meta";
    }

}
