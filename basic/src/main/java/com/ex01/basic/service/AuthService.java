package com.ex01.basic.service;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.exception.InvalidLoginException;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository
                .findByUsername(username)
                .orElseThrow(InvalidLoginException::new);
        return new CustomUserDetails(
                memberEntity.getId(),
                memberEntity.getUsername(),
                memberEntity.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + memberEntity.getRole()))
        );
        //        return User // UserDetails의 기본 구현체
//                .builder()
//                .username(memberEntity.getUsername())
//                .password(memberEntity.getPassword())
//                .roles(memberEntity.getRole())
//                .build();
    }

}
