package com.example.db_test.dto.post;

import com.example.db_test.entity.MemberEntity;
import com.example.db_test.entity.post.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDto {
    private Long id, postCount;
    private String title, content, memberUserId, memberUserName;
    private LocalDateTime createdAt, updateTime;

    public PostDetailDto(PostEntity postEntity) {
        BeanUtils.copyProperties(postEntity, this);
        this.memberUserId = postEntity.getMemberEntity().getUserId();
        this.memberUserName = postEntity.getMemberEntity().getUserName();
    }
}
