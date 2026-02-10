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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberFileService memberFileService;

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

    public void update(int id, MemberRegDto memberRegDto, MultipartFile multipartFile) {
        if (!memberRepository.existsById(id)) throw new MemberNotFoundException();
        String changeFileName = memberFileService.saveFile(multipartFile);
        if (!changeFileName.equals("nan")) {
            memberFileService.deleteFile(memberRegDto.getFileName());
            memberRegDto.setFileName(changeFileName);
        }

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

    public void insert(MemberRegDto memberRegDto, MultipartFile multipartFile) {
        if (memberRepository.findByUserName(memberRegDto.getUsername()).isPresent())
            throw new MemberDuplicationException();

        String fileName = memberFileService.saveFile(multipartFile);
        memberRegDto.setFileName(fileName);
        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save(memberEntity);
//        System.out.println("파일 이름: "+ multipartFile.getOriginalFilename());
//        File file = new File("C:\\Users\\seung\\Downloads\\[IBM] Cloud Native Dev base AI agent 3기\\00. practice\\boot-workspace\\" + multipartFile.getOriginalFilename());
//        try {
//            multipartFile.transferTo(file);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void login(LoginDto loginDto) {
        boolean chck = memberRepository
                .findByLoginInfo(loginDto.getUsername(), loginDto.getPassword())
                .isEmpty();
        if (chck) throw new InvalidLoginException();
    }

}
