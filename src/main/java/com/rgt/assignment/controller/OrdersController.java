package com.rgt.assignment.controller;

import com.rgt.assignment.dto.OrdersResponseDto;
import com.rgt.assignment.dto.ResponseDto;
import com.rgt.assignment.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @PostMapping("/{tablesId}/orders")
    public ResponseEntity<?> order(@PathVariable Long tablesId) {
        try {
            OrdersResponseDto order = this.ordersService.order(tablesId);
            return ResponseEntity.status(HttpStatus.OK).body(order);
        } catch (ResponseStatusException e) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setMessage(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(responseDto);
        }
    }
}
