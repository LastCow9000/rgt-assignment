package com.rgt.assignment.dto;

import com.rgt.assignment.constants.OrderStatus;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersResponseDto {
    private Long orderId;
    private List<MenuDto> menus;
    private Integer totalPrice;
    private OrderStatus status;
}
