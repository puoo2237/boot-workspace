package com.ex01.basic.repository;

import com.ex01.basic.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

//    Optional<MemberEntity> findByUsernameAndPassword(String username, String password);
    Optional<MemberEntity> findByUsername(String username);

}
