package com.rgt.assignment.repository;

import com.rgt.assignment.entity.RefreshToken;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Boolean existsByToken(String token);

    @Transactional
    void deleteByToken(String token);

    Optional<List<RefreshToken>> findByExpirationBefore(Date currentTime);
}
