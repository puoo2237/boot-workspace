package com.example.db_test.native_test;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberRegDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;

    public List<MemberAllDto> getContents() {
        return testRepository
                .findAllList()
                .stream()
                .map(MemberAllDto::new)
                .toList();
    }

    public MemberAllDto getContent(long num) {
        return testRepository
                .findByContent(num)
                .map(MemberAllDto::new)
                .orElse(null);
    }

    public void insertContent(MemberRegDto memberRegDto) {
        testRepository
                .insertContent(
                        memberRegDto.getUserId(),
                        memberRegDto.getUserName(),
                        memberRegDto.getAge());
    }
}
