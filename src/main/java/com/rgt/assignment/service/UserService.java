package com.rgt.assignment.service;

import com.rgt.assignment.constants.Role;
import com.rgt.assignment.dto.RegisterDto;
import com.rgt.assignment.entity.User;
import com.rgt.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void register(RegisterDto registerDto) {
        String username = registerDto.getUsername();
        String password = registerDto.getPassword();

        boolean isExist = this.userRepository.existsByUsername(username);
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Already exist id");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        User newUser = User.builder()
                .username(username)
                .password(encodedPassword)
                .role(Role.CUSTOMER)
                .build();

        this.userRepository.save(newUser);
    }
}
