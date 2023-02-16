package com.cos.securityex01.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements Oauth2Userinfo{

    private Map<String,Object> attribute;

    public NaverUserInfo(Map<String,Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProviderId() {
        return (String) attribute.get("id");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getName() {
        return (String) attribute.get("name");
    }
}
