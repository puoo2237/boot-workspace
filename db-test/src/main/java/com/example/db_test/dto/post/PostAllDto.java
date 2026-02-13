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
public class PostAllDto {
    private Long id;
    private String title, content;
    private LocalDateTime createdAt, updateTime;
//    private MemberEntity memberEntity;

    public PostAllDto(PostEntity postEntity) {
        System.out.println(postEntity);
        BeanUtils.copyProperties(postEntity, this);
//        System.out.println("멤버 데이터 가져옴");
//        postEntity.getMemberEntity().getUserName();
    }
}
