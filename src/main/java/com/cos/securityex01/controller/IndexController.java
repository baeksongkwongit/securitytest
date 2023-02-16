package com.cos.securityex01.controller;

import com.cos.securityex01.config.auth.PricialpalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String loginTest(Authentication authentication, @AuthenticationPrincipal PricialpalDetails pricialpalDetails,@AuthenticationPrincipal UserDetails userDetails){
        System.out.println("/test/logig===========");
        PricialpalDetails pricialpalDetails1 = (PricialpalDetails) authentication.getPrincipal();

        System.out.println("authentication : " + pricialpalDetails1.getUser());
        System.out.println("authentication : " + userDetails.getUsername());
        System.out.println("userDetails : " + pricialpalDetails.getUser());
        return "세션정보확인하기";

    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,@AuthenticationPrincipal OAuth2User oauth){
        System.out.println("/test/logig===========");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        System.out.println("authentication : " + oAuth2User.getAttributes());
        System.out.println("getAttributes : " + oauth.getAttributes());
        return "세션정보확인하기";

    }
    @GetMapping({ "", "/" })
    public String index(){
        return "index";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    //일반 로그인도 pricipalDetail로 로그인가능
    //oauth 로그인도 pricipalDetails 로그인가능
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PricialpalDetails pricialpalDetails){
        System.out.println("pricipalDetails : " + pricialpalDetails.getUser());
        return "user";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager(){
        return "manager";
    }

    @GetMapping("/loginForm")
    public  String login(){
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public  String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword  = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);

        return "redirect:/loginForm";

    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }
}
