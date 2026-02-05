package com.example.db_test.native_test;

import com.example.db_test.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<MemberEntity, Long> {
    @Query(value = "select * from member_test order by number desc", nativeQuery = true)
    List<MemberEntity> findAllList();

    @Query(value = "select * from member_test where number=:n", nativeQuery = true)
    Optional<MemberEntity> findByContent(@Param("n") long num);

    @Modifying
    @Transactional
    @Query(value = "insert into member_test(user_id, user_name, age) " +
            "values(:username, :name, :age)",
            nativeQuery = true)
    int insertContent(
            @Param("username") String id,
            @Param("name") String name,
            @Param("age") int age);
}
