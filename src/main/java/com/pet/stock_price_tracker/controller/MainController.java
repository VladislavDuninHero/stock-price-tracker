package com.pet.stock_price_tracker.controller;

import com.pet.stock_price_tracker.constants.Pages;
import com.pet.stock_price_tracker.constants.Routes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(Routes.LOGIN_ROUTE)
    public String login() {
        return Pages.LOGIN_PAGE;
    }

    @GetMapping(Routes.REGISTRATION_ROUTE)
    public String registration() {
        return Pages.REGISTRATION_PAGE;
    }

    @GetMapping(Routes.HOME_ROUTE)
    public String home() {
        return Pages.HOME_PAGE;
    }
}
