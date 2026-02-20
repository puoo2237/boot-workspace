package com.ex01.basic.dto.post;

import com.ex01.basic.entity.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAllDto {

    private int id, memUserId, postCount;
    private String title, content, memUserName;
    private LocalDateTime createdAt, updateTime;

    public PostAllDto(PostEntity postEntity){
        BeanUtils.copyProperties(postEntity, this);
        this.memUserId = postEntity.getMemberEntity().getId();
        this.memUserName = postEntity.getMemberEntity().getUsername();
    }
}
