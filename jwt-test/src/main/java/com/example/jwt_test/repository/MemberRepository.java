package com.example.jwt_test.repository;

import com.example.jwt_test.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    boolean existsByUsername(String username);
    Optional<MemberEntity> findByUsername(String username);
}
