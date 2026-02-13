package com.example.db_test.service;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberModifyDto;
import com.example.db_test.dto.MemberRegDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.exception.MemberDuplicateException;
import com.example.db_test.exception.MemberNotFoundException;
import com.example.db_test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<MemberAllDto> getList(int start) {

        int size = 3;
        Pageable pageable = PageRequest.of(
                start,
                size,
                Sort.by(
                        Sort.Order.desc("number")
                ));
        List<MemberAllDto> list = memberRepository.findAll(pageable)
                .stream()
                .map(MemberAllDto::new)
                .toList();

        if (list.isEmpty())
            throw new MemberNotFoundException();
        return list;
//        System.out.println("service getList: " + list);
    }

    @Transactional(readOnly = true)
    public MemberAllDto getMember(String userId) {
        return memberRepository
                .findByUserId(userId)
                .map(MemberAllDto::new)
                .orElseThrow(() -> new MemberNotFoundException("사용자 없음"));
        //        return memberRepository
//                .findByUserId(userId)
//                .map(dto -> {
//                    MemberAllDto memberAllDto = new MemberAllDto();
//                    BeanUtils.copyProperties(dto, memberAllDto);
//                    return memberAllDto;
//                })
//                .orElseThrow(RuntimeException::new);
    }

    public void insert(MemberRegDto memberRegDto) {
        boolean bool = memberRepository.existsByUserId(memberRegDto.getUserId());
        if (bool)
            throw new MemberDuplicateException("중복 아이디");
        MemberEntity memberEntity = new MemberEntity();
        BeanUtils.copyProperties(memberRegDto, memberEntity);
        memberRepository.save(memberEntity);
    }

    public void delete(long id) {
//        if (memberRepository.existsById(id))
//            throw new MemberNotFoundException("삭제할 사용자 없음");
//        MemberEntity memberEntity = memberRepository
//                .findById(id)
//                .orElseThrow(
//                        () -> new MemberNotFoundException("삭제할 사용자가 없습니다.")
//                );
//        memberEntity
//                .getPosts()
//                .forEach(post -> post.setMemberEntity(null));
//        memberEntity.getPosts().clear();
        memberRepository.deleteById(id);
    }

    public void modify(long id, MemberModifyDto memberModifyDto) {
        MemberEntity memberEntity = memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("수정할 사용자 없음"));
        BeanUtils.copyProperties(memberModifyDto, memberEntity);
        memberRepository.save(memberEntity);
    }


    public MemberEntity getTestMember(long number) {
        return memberRepository
                .findById(number)
                .orElse(null);
    }
}
