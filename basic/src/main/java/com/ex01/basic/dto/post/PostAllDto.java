package com.ex01.basic.dto.post;

import com.ex01.basic.entity.post.PostEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostAllDto {

    private int id, memUserId, postCount, likeCount;
    private boolean liked;
    private String title, content, memUserName;
    private LocalDateTime createdAt, updateTime;

    public PostAllDto(
            PostEntity postEntity,
            int postCount,
            boolean liked,
            int likeCount
            ){
        BeanUtils.copyProperties(postEntity, this);
        this.memUserId = postEntity.getMemberEntity().getId();
        this.memUserName = postEntity.getMemberEntity().getUsername();

        this.postCount = postCount;
        this.liked = liked;
        this.likeCount = likeCount;
    }
}
