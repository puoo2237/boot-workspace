package com.ex01.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private List<String> content;
    private int totalPages, number;
    private boolean first;
}
