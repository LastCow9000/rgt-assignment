package com.rgt.assignment.filter;

import com.rgt.assignment.constants.Auth;
import com.rgt.assignment.constants.Role;
import com.rgt.assignment.constants.TokenType;
import com.rgt.assignment.dto.CustomUserDetails;
import com.rgt.assignment.entity.User;
import com.rgt.assignment.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String SPACE = " ";
    private static final String EXPIRED_TOKEN = "Access token was expired";
    private static final String INVALID_TOKEN = "Invalid access token";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(Auth.AUTHORIZATION.getValue());
        if (authHeader == null || !authHeader.startsWith(Auth.BEARER.getValue().concat(SPACE))) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authHeader.split(SPACE)[1];
        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            response.getWriter().print(EXPIRED_TOKEN);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (SignatureException e) {
            response.getWriter().print(INVALID_TOKEN);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String type = jwtUtil.getType(accessToken);
        if (!type.equals(TokenType.ACCESS.toString())) {
            response.getWriter().print(INVALID_TOKEN);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String username = jwtUtil.getUsername(accessToken);
        Role role = Role.valueOf(jwtUtil.getRole(accessToken));

        User user = User.builder()
                .username(username)
                .role(role)
                .build();
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
