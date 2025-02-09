package com.pet.stock_price_tracker.service.utils.uri.restore.impl;

import com.pet.stock_price_tracker.constants.Routes;
import com.pet.stock_price_tracker.properties.ApplicationProperties;
import com.pet.stock_price_tracker.service.security.jwt.JwtService;
import com.pet.stock_price_tracker.service.utils.uri.restore.RestorePasswordService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class RestorePasswordUriGeneratorImpl implements RestorePasswordService {

    private final JwtService jwtService;
    private final ApplicationProperties applicationProperties;

    public RestorePasswordUriGeneratorImpl(JwtService jwtService, ApplicationProperties applicationProperties) {
        this.jwtService = jwtService;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public URI generateRestorePasswordUri(String login) {
        String token = jwtService.generateRestorePasswordToken(login);

        return URI.create(
                applicationProperties.getUrl() + Routes.USER_RESTORE_PASSWORD_UPDATE_ROUTE + "?token=" + token
        );
    }

}
