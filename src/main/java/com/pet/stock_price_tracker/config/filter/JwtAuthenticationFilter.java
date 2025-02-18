package com.pet.stock_price_tracker.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.stock_price_tracker.config.Config;
import com.pet.stock_price_tracker.constants.Cookies;
import com.pet.stock_price_tracker.constants.OfficialProperties;
import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.dto.error.UserErrorDTO;
import com.pet.stock_price_tracker.enums.ErrorCode;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final Config config;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            ObjectMapper objectMapper,
            Config config,
            UserDetailsService userDetailsService
    ) {
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
        this.config = config;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getRequestURI();

        if (
                request.getServletPath().equalsIgnoreCase(Routes.LOGIN_ROUTE)
                        || config.acceptedRoutesConfigurer().contains(requestPath)
                        || request.getRequestURI().startsWith("/css/")
                        || request.getRequestURI().startsWith("/js/")
                        || request.getRequestURI().startsWith("/images/")

        ) {
            filterChain.doFilter(request, response);
            return;
        }

        if (requestPath.equalsIgnoreCase(Routes.API_USER_RESTORE_PASSWORD_UPDATE_ROUTE)) {

            String token = request.getParameter("token");

            if (token != null) {
                try {
                    jwtService.validateToken(token);
                } catch (ExpiredJwtException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.EXPIRED_TOKEN.name(), e.getMessage());
                    response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                    return;
                } catch (SignatureException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.INVALID_TOKEN.name(), e.getMessage());
                    response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                    return;
                }
            }

            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (
                authorizationHeader == null
                        && request.getCookies() == null
                        && !config.acceptedRoutesConfigurer().contains(requestPath)
        ) {
            response.sendRedirect(Routes.LOGIN_ROUTE);
            return;
        } else if (
                authorizationHeader == null
                        && request.getCookies() != null
                        && !config.acceptedRoutesConfigurer().contains(requestPath)
        ) {
            List<Cookie> cookies = List.of(request.getCookies());

            if (!validateCookie(cookies, request, response)) {
                response.sendRedirect(Routes.LOGIN_ROUTE);
                return;
            }
        }

        if (authorizationHeader != null && authorizationHeader.startsWith(OfficialProperties.BEARER_TOKEN_PREFIX)) {
            String token = authorizationHeader.substring(OfficialProperties.BEARER_TOKEN_PREFIX.length());

            try {

                Claims validated = jwtService.validateToken(token);
                String login = validated.getSubject();

                if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(login);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            login,
                            null,
                            userDetails.getAuthorities()
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
            } catch (UsernameNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.INVALID_TOKEN.name(), e.getMessage());
                response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                return;
            }
        }

        if (
                authorizationHeader == null
                        && request.getCookies() != null
                        && !config.acceptedRoutesConfigurer().contains(requestPath)
        ) {
            List<Cookie> cookies = List.of(request.getCookies());

            if (!validateCookie(cookies, request, response)) {
                return;
            };
        }


        filterChain.doFilter(request, response);
    }


    private boolean validateCookie(
            List<Cookie> cookies,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(Cookies.TOKEN)) {

                try {

                    Claims validated = jwtService.validateToken(cookie.getValue());
                    String login = validated.getSubject();
                    System.out.println(SecurityContextHolder.getContext().getAuthentication());
                    if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                login, null, Collections.emptyList()
                        );

                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        return true;
                    }

                } catch (ExpiredJwtException e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.EXPIRED_TOKEN.name(), e.getMessage());
                    response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                    return false;
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    UserErrorDTO userErrorDTO = new UserErrorDTO(ErrorCode.INVALID_TOKEN.name(), e.getMessage());
                    response.getWriter().write(objectMapper.writeValueAsString(userErrorDTO));

                    return false;
                }
            }
        }

        return true;
    }


}
