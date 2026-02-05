package com.ex01.basic.repository;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private ArrayList<MemberDto> DB;

    public MemberRepository() {
        System.out.println("MemberRepository 생성자");
        DB = new ArrayList<>();
        DB.add(new MemberDto(1, "aaa", "aaaS", "USER"));
        DB.add(new MemberDto(2, "bbb", "bbbS", "USER"));
        DB.add(new MemberDto(3, "ccc", "cccS", "USER"));
    }

    public List<MemberDto> findAll() {
        return DB;
    }

    public Optional<MemberDto> findById(int id) {
        return DB.stream()
                .filter(mem -> mem.getId() == id)
                .findFirst();
    }

    public boolean exist(int id) {
        return DB.stream()
                .anyMatch(mem -> mem.getId() == id);
    }

    public void save(MemberDto memberDto) {
        DB.add(new MemberDto(
                memberDto.getId(),
                memberDto.getUsername(),
                memberDto.getPassword(),
                memberDto.getRole()
        ));
    }

    public void save(int id, MemberDto memberDto) {
        DB.stream()
                .filter(mem -> mem.getId() == id)
                .findFirst()
                .ifPresent(mem -> { // 존재할 때 실행될 로직, 'mem'은 찾은 객체입니다.
                    mem.setUsername(memberDto.getUsername());
                    mem.setPassword(memberDto.getPassword());
                    mem.setRole(memberDto.getRole());
                });
    }

    public boolean deleteById(int id) {
        return DB.removeIf(mem -> mem.getId() == id);
    }

    public boolean login(LoginDto loginDto) {

        return DB.stream()
                .anyMatch(mem -> (mem.getUsername().equals(loginDto.getUsername()))
                        && (mem.getPassword().equals(loginDto.getPassword())));
    }

    public void repositoryTest() {
        System.out.println("repository 연결");
    }

}
