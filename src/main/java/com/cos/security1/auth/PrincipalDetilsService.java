package com.cos.security1.auth;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * [해당 클래스가 발동 시점]
 * 시큐리티 설정에서 .loginProcessingUrl("/login")
 * /login 요청이 오면 자동으로 UserDetilsService 타입으로 IoC되어 있는 loadUserByUsername함수가 실행된다.
 * 해당 개념은 이해하지말고 규칙으로 받아들이자.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetilsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 시큐리티 session(SecurityContextHolder) = Authentication => UserDetils
    // return ->   시큐리티 Session ( (Authentication(UserDetils) )
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("username :: {}",username);
        User user = userRepository.findByUserId(username);

        if(user == null){
            throw new UsernameNotFoundException(username+"계정은 존재하지 않습니다.");
        }

        log.info("성공");
        return new PrincipalDetails(user);
    }
}
