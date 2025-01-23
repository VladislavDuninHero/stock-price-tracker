package com.pet.stock_price_tracker.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.stock_price_tracker.constants.ExceptionMessage;
import com.pet.stock_price_tracker.constants.OfficialProperties;
import com.pet.stock_price_tracker.constants.Pages;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.error.UserErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(JwtService jwtService, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (
                request.getServletPath().equalsIgnoreCase(Routes.LOGIN_ROUTE)
                || request.getServletPath().equalsIgnoreCase(Routes.REGISTRATION_ROUTE)
                || request.getRequestURI().startsWith("/css/")
                || request.getRequestURI().startsWith("/js/")
                || request.getRequestURI().startsWith("/images/")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith(OfficialProperties.BEARER_TOKEN_PREFIX)) {
            String token = authorizationHeader.substring(OfficialProperties.BEARER_TOKEN_PREFIX.length());

            try {

                Claims validated = jwtService.validateToken(token);
                String login = validated.getSubject();

                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            login, null, Collections.emptyList()
                    );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.EXPIRED_TOKEN.name(), e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                return;
            }
        }

        filterChain.doFilter(request, response);
    }

}
