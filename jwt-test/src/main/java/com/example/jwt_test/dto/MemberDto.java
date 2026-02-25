package com.example.jwt_test.dto;

import com.example.jwt_test.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String username, password, role;

    public MemberDto(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity, this);
    }
}
