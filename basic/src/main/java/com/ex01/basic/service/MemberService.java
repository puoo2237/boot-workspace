package com.ex01.basic.service;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicationException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberService() {
        System.out.println("MemberService 생성자");
    }

    public void serviceTest() {
        System.out.println("서비스 test 연결: " + memberRepository);
        memberRepository.repositoryTest();
    }

    public List<MemberDto> getList() {
        List<MemberDto> list = memberRepository.findAll();
        if (list.isEmpty())
            throw new MemberNotFoundException("데이터 없다.");
        return list;
    }

    public MemberDto getOne(int id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public void modify(int id, MemberDto memberDto){
        if(!memberRepository.exist(id)) throw new MemberNotFoundException();
        memberRepository.save(id, memberDto);

    }

    public void delMember(int id){
        if(!memberRepository.exist(id)) throw new MemberNotFoundException();
        memberRepository.deleteById(id);
    }

    public void insert(MemberDto memberDto){
        if(memberRepository.exist(memberDto.getId())) throw new MemberDuplicationException();
        memberRepository.save(memberDto);
    }
}
