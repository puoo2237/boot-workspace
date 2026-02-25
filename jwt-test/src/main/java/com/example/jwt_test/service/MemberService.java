package com.example.jwt_test.service;

import com.example.jwt_test.dto.MemberDto;
import com.example.jwt_test.dto.MemberRegDto;
import com.example.jwt_test.entity.MemberEntity;
import com.example.jwt_test.exception.MemberAccessDeniedException;
import com.example.jwt_test.exception.MemberConflictException;
import com.example.jwt_test.exception.MemberNotFoundException;
import com.example.jwt_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberDto> getList() {
        List<MemberDto> list = memberRepository
                .findAll()
                .stream()
                .map(MemberDto::new)
                .toList();
        if (list.isEmpty())
            throw new MemberNotFoundException("저장된 데이터 없음");
        return list;
    }

    public void register(MemberRegDto memberRegDto) {
        if (memberRepository.existsByUsername(memberRegDto.getUsername()))
            throw new MemberConflictException("동일한 id 존재함");

        memberRegDto.setPassword(passwordEncoder.encode(memberRegDto.getPassword()));

        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save(memberEntity);
    }

    public void delete(Long id, String username) {
//        if(!memberRepository.existsById(id))
//            throw new MemberNotFoundException("삭제할 사용자가 없습니다.");
        MemberEntity memberEntity = memberRepository
                .findById(id)
                .orElseThrow(() -> new MemberNotFoundException("삭제할 사용자가 없습니다."));
        if (!memberEntity.getUsername().equals(username)) {
            throw new MemberAccessDeniedException("삭제 권한이 없습니다.");
        }
        memberRepository.deleteById(id);
    }
}
