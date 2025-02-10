package com.pet.stock_price_tracker.controller;

import com.pet.stock_price_tracker.constants.Pages;
import com.pet.stock_price_tracker.constants.Routes;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(value = {Routes.HOME_ROUTE, Pages.HOME_PAGE})
    public String home(Model model) {

        model.addAttribute("login", SecurityContextHolder.getContext().getAuthentication().getName());

        return Pages.HOME_PAGE;
    }

    @GetMapping(Routes.USER_RESTORE_PASSWORD_ROUTE)
    public String restore() {
        return Pages.RESTORE_PAGE;
    }

    @GetMapping(Routes.USER_RESTORE_PASSWORD_UPDATE_ROUTE)
    public String update() {
        return Pages.UPDATE_AFTER_RESTORE_PASSWORD_PAGE;
    }

    @GetMapping(Routes.PROFILE_ROUTE)
    public String profile() {
        return Pages.PROFILE;
    }
}
