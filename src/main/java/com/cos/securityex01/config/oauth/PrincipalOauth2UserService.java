package com.cos.securityex01.config.oauth;

import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("getClientRegistration : " + userRequest.getClientRegistration()); //registrationId로 어떤 Oauth 로 로그인 했는지 알수 있음.
        System.out.println("getAccessToken : " + userRequest.getAccessToken());
        System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());
        //구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인을 완료 -> code를 리턴(Oauth-Client라이브러리) -> AccessToken요청
        //userRequest 정보 ->loadUser함수 호출-> 회원프로필 받을 수 있음.
        System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());
        System.out.println("user Request : " + userRequest);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        //회원가입을 강제로 진행해볼 예정

        return super.loadUser(userRequest);
    }
}
