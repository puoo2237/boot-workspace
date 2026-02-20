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
public class PostDto {

    private int id;
    private String title, content;
    private LocalDateTime createdAt, updateTime;

    public PostDto(PostEntity postEntity){
        BeanUtils.copyProperties(postEntity, this);
    }
}
