package com.cos.security1.config;

import com.cos.security1.auth.PrincipalDetails;
import com.cos.security1.auth.PrincipalDetilsService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PrincipalDetilsService principalDetilsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @SuppressWarnings("unchecked")
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        PrincipalDetails user = (PrincipalDetails) principalDetilsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException(username);
        }

        if(!user.isEnabled()) {
            throw new BadCredentialsException(username);
        }
        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

    }

    // 위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
    // null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}