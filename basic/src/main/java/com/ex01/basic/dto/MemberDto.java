package com.ex01.basic.dto;

import com.ex01.basic.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private int id;
    private String username, password, role;

    public MemberDto(MemberEntity memberEntity) {
        BeanUtils.copyProperties(memberEntity, this);
    }
}
