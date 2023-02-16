package com.cos.securityex01.config.auth;

//시큐리티가 /login 주소 요청이 오면 낚아 채서 로그인을 진행시킨다.
//로그인을 진행이 완료되면 시큐리티 session을 만들어줍니다.(Security ContextHolder)
// 오브젝트 => Authentication 타입 객체
//Authentication안에 User 정보가 있어야 됨.
//User오브젝트타입 => UserDetails 타입 객체

import com.cos.securityex01.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
//Security Session=> Authentication => UserDetails
public class PricialpalDetails implements UserDetails , OAuth2User {
    private User user;

    private Map<String,Object>  attribute;

    //일반로그인
    public PricialpalDetails(User user){
        this.user = user;
    }
    //Oauth 로그인
    public PricialpalDetails(User user, Map<String , Object> attribute){
        this.user = user;
        this.attribute = attribute;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    //해당 유저의 권한을 리턴하는곳.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add((new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        }));
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        //우리 회원이 1년동안 회원이 로그인을 안하면 //휴면 계정
        //1년이 지나면 false 로 변경하면 휴면 처리 됨.
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
