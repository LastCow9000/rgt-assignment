package com.rgt.assignment.service;

import com.rgt.assignment.constants.Time;
import com.rgt.assignment.constants.TokenType;
import com.rgt.assignment.dto.TokenDto;
import com.rgt.assignment.entity.RefreshToken;
import com.rgt.assignment.repository.RefreshTokenRepository;
import com.rgt.assignment.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private static final String EXPIRED_TOKEN = "Refresh token was expired";
    private static final String INVALID_TOKEN = "Invalid refresh token";

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenDto reissueTokens(String refreshToken) {
        try {
            jwtUtil.isExpired(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, EXPIRED_TOKEN);
        }

        String type = jwtUtil.getType(refreshToken);
        boolean isExist = this.refreshTokenRepository.existsByToken(refreshToken);
        if (!type.equals(TokenType.REFRESH.toString()) || !isExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, INVALID_TOKEN);
        }

        String username = jwtUtil.getUsername(refreshToken);
        String role = jwtUtil.getRole(refreshToken);

        String newAccessToken = jwtUtil.createJwt(TokenType.ACCESS.toString(), username, role, Time.TEN_MIN.getValue());
        String newRefreshToken = jwtUtil.createJwt(TokenType.REFRESH.toString(), username, role,
                Time.TWENTY_FOUR_HOUR.getValue());

        this.refreshTokenRepository.deleteByToken(refreshToken);
        this.saveRefreshToken(username, newRefreshToken);

        return TokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public void saveRefreshToken(String username, String refreshToken) {
        Date date = new Date(System.currentTimeMillis() + 10 * 1000L);
        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .username(username)
                .token(refreshToken)
                .expiration(date)
                .build();

        this.refreshTokenRepository.save(refreshTokenEntity);
    }

    @Scheduled(cron = "0 0 0/3 * * *")
    @Transactional
    public void deleteExpiredRefreshToken() {
        List<RefreshToken> refreshTokens = this.refreshTokenRepository.findByExpirationBefore(
                new Date(System.currentTimeMillis()));
        if (!refreshTokens.isEmpty()) {
            this.refreshTokenRepository.deleteAll(refreshTokens);
        }
    }
}
