package com.example.jwt_test.service;

import com.example.jwt_test.entity.MemberEntity;
import com.example.jwt_test.exception.MemberNotFoundException;
import com.example.jwt_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("auth service username: " + username);
        MemberEntity memberEntity = memberRepository
                .findByUsername(username)
                .orElseThrow(() -> new MemberNotFoundException("로그인 실패"));
        return User
                .builder()
                .username(memberEntity.getUsername())
                .password(memberEntity.getPassword())
                .roles(memberEntity.getRole())
                .build();
    }
}
