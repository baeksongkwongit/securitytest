package com.cos.securityex01.config.oauth.provider;

public interface Oauth2Userinfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
