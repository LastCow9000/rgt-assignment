package com.rgt.assignment.controller;

import com.rgt.assignment.constants.Auth;
import com.rgt.assignment.constants.ResultStatus;
import com.rgt.assignment.constants.TokenType;
import com.rgt.assignment.dto.ResponseDto;
import com.rgt.assignment.dto.TokenDto;
import com.rgt.assignment.service.ReissueService;
import com.rgt.assignment.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/reissue")
@RequiredArgsConstructor
public class ReissueController {

    private static final String NULL_TOKEN = "Refresh token is null";
    private static final String SPACE = " ";

    private final ReissueService reissueService;

    @PostMapping
    public ResponseEntity<ResponseDto> reissue(HttpServletRequest request, HttpServletResponse response) {
        ResponseDto responseDto = new ResponseDto();

        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(TokenType.REFRESH.toString())) {
                refreshToken = cookie.getValue();
            }
        }
        if (refreshToken == null) {
            responseDto.setMessage(NULL_TOKEN);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
        }

        TokenDto tokenDto = null;
        try {
            tokenDto = this.reissueService.reissueTokens(refreshToken);
        } catch (ResponseStatusException e) {
            responseDto.setMessage(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(responseDto);
        }

        String newAccessToken = tokenDto.getAccessToken();
        String newRefreshToken = tokenDto.getRefreshToken();

        response.setHeader(Auth.AUTHORIZATION.getValue(), Auth.BEARER.getValue().concat(SPACE).concat(newAccessToken));
        response.addCookie(CookieUtil.createCookie(TokenType.REFRESH.toString(), newRefreshToken));

        responseDto.setMessage(ResultStatus.SUCCESS.toString());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
