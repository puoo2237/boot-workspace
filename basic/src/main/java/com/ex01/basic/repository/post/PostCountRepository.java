package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Integer> {
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, int postId);
    int countByPostEntity_Id(int postId);
}
