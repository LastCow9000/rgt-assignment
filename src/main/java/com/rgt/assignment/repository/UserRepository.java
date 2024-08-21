package com.rgt.assignment.repository;

import com.rgt.assignment.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
