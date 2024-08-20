package com.rgt.assignment.controller;

import com.rgt.assignment.constants.ResultStatus;
import com.rgt.assignment.dto.RegisterDto;
import com.rgt.assignment.dto.ResponseDto;
import com.rgt.assignment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ResponseEntity<ResponseDto> register(@RequestBody RegisterDto registerDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            this.userService.register(registerDto);
            responseDto.setMessage(ResultStatus.SUCCESS.toString());
        } catch (ResponseStatusException e) {
            responseDto.setMessage(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(responseDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
