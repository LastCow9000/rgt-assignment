package com.rgt.assignment.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rgt.assignment.constants.Auth;
import com.rgt.assignment.constants.Time;
import com.rgt.assignment.constants.TokenType;
import com.rgt.assignment.service.ReissueService;
import com.rgt.assignment.util.CookieUtil;
import com.rgt.assignment.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final String USERNAME = "id";
    private static final String PASSWORD = "password";
    private static final String SPACE = " ";
    private static final String PARSE_ERROR_MESSAGE = "Failed to parse authentication request body";

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ReissueService reissueService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        String username = null;
        String password = null;

        try {
            Map<String, String> requestMap = objectMapper.readValue(request.getInputStream(), Map.class);
            username = requestMap.get(USERNAME);
            password = requestMap.get(PASSWORD);
        } catch (IOException e) {
            throw new RuntimeException(PARSE_ERROR_MESSAGE);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password,
                null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication authentication) {

        String username = authentication.getName();

        GrantedAuthority auth = authentication.getAuthorities().iterator().next();
        String role = auth.getAuthority();

        String accessToken = jwtUtil.createJwt(TokenType.ACCESS.toString(), username, role, Time.TEN_MIN.getValue());
        String refreshToken = jwtUtil.createJwt(TokenType.REFRESH.toString(), username, role,
                Time.TWENTY_FOUR_HOUR.getValue());

        this.reissueService.saveRefreshToken(username, refreshToken);

        response.setHeader(Auth.AUTHORIZATION.getValue(), Auth.BEARER.getValue().concat(SPACE).concat(accessToken));
        response.addCookie(CookieUtil.createCookie(TokenType.REFRESH.toString(), refreshToken));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(401);
    }
}
