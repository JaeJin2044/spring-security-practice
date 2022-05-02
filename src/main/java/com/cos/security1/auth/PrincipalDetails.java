package com.cos.security1.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 session을 만들어준다.(Security ContextHolder)
// Security ContextHolder에 들어 갈수 있는 Object -> Authentication 타입 객체 이다.
// Authentication 안에 User정보가 있어야한다.
// User Object 타입 -> UserDetails 타입이어야한다.

import com.cos.security1.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Security Sesstion -> Authentication => UserDetils(PrincipalDetails)
public class PrincipalDetails implements UserDetails {

    private User user; //컴포지션을 해줘야함.

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     * 해당 User의 권한을 리턴하는 곳이다.
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collect = new ArrayList<>();
//        collect.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return user.getRole();
//            }
//        });

//        collect.add( () -> {
//            return user.getRole();
//        });

        return Collections.singleton((GrantedAuthority) () -> user.getRole());
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    //계정 기간 유효성 여부
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정 잠김 여부
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정 활성화 여부
    @Override
    public boolean isEnabled() {
        //휴면 설정은 여기서 해도됨
        //현재시간 - 로그인 시간으로 계산 후 false로 해도됨
        return true;
    }
}
