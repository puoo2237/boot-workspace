package com.ex01.basic.repository;

import com.ex01.basic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    @Query(value = "select * from member where username=:username and password=:password",
            nativeQuery = true)
    Optional<MemberEntity> findByLoginInfo(String username, String password);

    @Query(value = "select * from member where username=:username",
            nativeQuery = true)
    Optional<MemberEntity> findByUserName(String username);

}
