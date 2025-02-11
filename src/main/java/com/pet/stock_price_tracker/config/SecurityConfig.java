package com.pet.stock_price_tracker.config;

import com.pet.stock_price_tracker.config.filter.JwtAuthenticationFilter;
import com.pet.stock_price_tracker.constants.Cookies;
import com.pet.stock_price_tracker.constants.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(
                                        "/css/**",
                                        "/js/**",
                                        "/images/**"
                                ).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_LOGIN_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_REGISTRATION_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_REFRESH_TOKEN_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_LOGOUT_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.GET, Routes.LOGIN_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.LOGOUT_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.GET, Routes.REGISTRATION_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.GET, Routes.ACTUATOR_PROMETHEUS_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.GET, Routes.USER_RESTORE_PASSWORD_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.GET, Routes.USER_RESTORE_PASSWORD_UPDATE_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_RESTORE_PASSWORD_ROUTE).permitAll()
                                .requestMatchers(HttpMethod.POST, Routes.API_USER_RESTORE_PASSWORD_UPDATE_ROUTE).permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl(Routes.LOGOUT_ROUTE)
                        .logoutSuccessUrl(Routes.LOGIN_ROUTE)
                        .deleteCookies(Cookies.TOKEN)
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
