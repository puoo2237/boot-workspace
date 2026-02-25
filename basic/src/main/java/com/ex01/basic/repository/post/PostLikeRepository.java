package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Integer> {
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, int postId);
    int countByPostEntity_Id(int postId);

    void deleteByMemberEntity_IdAndPostEntity_Id(int memberId, int postId);
}
