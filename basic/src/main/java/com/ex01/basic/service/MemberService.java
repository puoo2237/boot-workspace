package com.ex01.basic.service;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.exception.InvalidLoginException;
import com.ex01.basic.exception.MemberDuplicationException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Page<MemberEntity> getList(int start) {
        int size = 3;
        Pageable pageable = PageRequest.of(
                start,
                size,
                Sort.by(Sort.Order.desc("id")));
        Page<MemberEntity> page = memberRepository
                .findAll(pageable);
        if (page.isEmpty())
            throw new MemberNotFoundException();
        return page;
    }

    @Transactional(readOnly = true)
    public MemberDto getOne(int id) {
        return memberRepository
                .findById(id)
                .map(MemberDto::new)
                .orElseThrow(MemberNotFoundException::new);
    }

    public void modify(int id, MemberRegDto memberRegDto) {
        if (!memberRepository.existsById(id)) throw new MemberNotFoundException();
        MemberEntity memberEntity = new MemberEntity();
        MemberDto memberDto = new MemberDto();
        BeanUtils.copyProperties(memberRegDto, memberDto);
        memberDto.setId(id);
        BeanUtils.copyProperties(memberDto, memberEntity);
        memberRepository.save(memberEntity);

    }

    public void delMember(int id) {
        if (!memberRepository.existsById(id)) throw new MemberNotFoundException();
        memberRepository.deleteById(id);
    }

    public void insert(MemberRegDto memberRegDto) {
        if (memberRepository.findByUserName(memberRegDto.getUsername()).isPresent())
            throw new MemberDuplicationException();
        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save(memberEntity);
    }

    public void login(LoginDto loginDto) {
        boolean chck = memberRepository
                .findByLoginInfo(loginDto.getUsername(), loginDto.getPassword())
                .isEmpty();
        if (chck) throw new InvalidLoginException();
    }
}
