package com.example.db_test.dto;

import com.example.db_test.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberAllDto {
    private Long number;
    private String userId, userName;
    private int age;
    public MemberAllDto(MemberEntity memberEntity){
        BeanUtils.copyProperties(memberEntity, this);
    }
}
