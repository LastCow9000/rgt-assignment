package com.rgt.assignment.service;

import com.rgt.assignment.dto.CustomUserDetails;
import com.rgt.assignment.entity.User;
import com.rgt.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userRepository.findByUsername(username);
        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
