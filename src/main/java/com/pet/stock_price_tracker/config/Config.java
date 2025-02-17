package com.pet.stock_price_tracker.config;


import com.pet.stock_price_tracker.constants.Routes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class Config {

    @Bean
    public List<String> acceptedRoutesConfigurer() {
        return List.of(
                Routes.LOGIN_ROUTE,
                Routes.REGISTRATION_ROUTE,
                Routes.API_USER_LOGIN_ROUTE,
                Routes.API_USER_REGISTRATION_ROUTE,
                Routes.LOGOUT_ROUTE,
                Routes.API_USER_LOGOUT_ROUTE,
                Routes.USER_RESTORE_PASSWORD_ROUTE,
                Routes.USER_RESTORE_PASSWORD_UPDATE_ROUTE,
                Routes.API_USER_RESTORE_PASSWORD_ROUTE
        );
    }
}
