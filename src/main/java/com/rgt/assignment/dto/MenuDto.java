package com.rgt.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private Long menuId;
    private String name;
    private Integer price;
    private Integer quantity;
}
