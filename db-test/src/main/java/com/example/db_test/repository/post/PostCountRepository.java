package com.example.db_test.repository.post;

import com.example.db_test.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Long> {
    boolean existsByMemberEntity_NumberAndPostEntity_Id(Long memberId, Long postId);

    long countByPostEntity_Id(Long postId);
}
