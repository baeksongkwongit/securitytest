package com.cos.securityex01.config.oauth;

import com.cos.securityex01.config.auth.PricialpalDetails;
import com.cos.securityex01.config.oauth.provider.FacebookUserInfo;
import com.cos.securityex01.config.oauth.provider.GoogleUserInfo;
import com.cos.securityex01.config.oauth.provider.NaverUserInfo;
import com.cos.securityex01.config.oauth.provider.Oauth2Userinfo;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

//구글로 부터 받은 userRequest데이터에 대한 후처리되는 함수
//@함수 종료시 @authenticationPricipal 어노테이션이 만들어진다.
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

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


        Oauth2Userinfo oauth2Userinfo = null;

        if(userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("구글 로그인 요청");
            oauth2Userinfo = new GoogleUserInfo(oAuth2User.getAttributes());

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            oauth2Userinfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oauth2Userinfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else{
            System.out.println("우리는 구글과 페이스북만 지원해요~!!");

        }
//
//        String provider = userRequest.getClientRegistration().getRegistrationId();//google
//        String providerId = oAuth2User.getAttribute("sub");
//        String username =provider + "_" +providerId;
//        String password = bCryptPasswordEncoder.encode("겟인데어");
//
//        String email = oAuth2User.getAttribute("email");
        User userEntity = null;

        if(oauth2Userinfo !=null){
            String provider = oauth2Userinfo.getProvider();//google
            String providerId = oauth2Userinfo.getProviderId();
            String username =provider + "_" +providerId;
            String password = bCryptPasswordEncoder.encode("겟인데어");

            String email = oauth2Userinfo.getEmail();


            String role = "ROLE_USER";

            userEntity = userRepository.findByUsername(username);
            if(userEntity==null){
                System.out.println("구글로그인이 최조 입니다.");
                userEntity = User.builder()
                        .username(username)
                        .password(password)
                        .email(email)
                        .role(role)
                        .provider(provider)
                        .providerId(providerId)
                        .build();
                userRepository.save(userEntity);
            }else{
                System.out.println("구글로그인을 이미 한적있습니다.당신은자동회원가입이 되어있습니다.");
            }
        }



        //회원가입을 강제로 진행해볼 예정

        return new PricialpalDetails(userEntity, oAuth2User.getAttributes());
    }
}
