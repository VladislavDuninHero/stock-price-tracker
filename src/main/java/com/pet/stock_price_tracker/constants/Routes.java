package com.pet.stock_price_tracker.constants;

public final class Routes {
    private Routes() {}
    public static final String HOME_ROUTE = "/";
    public static final String LOGIN_ROUTE = "/login";
    public static final String REGISTRATION_ROUTE = "/registration";
    public static final String REFRESH_TOKEN_ROUTE = "/refresh";

    public static final String API_USER_REGISTRATION_ROUTE = "/api/v1/user/registration";
    public static final String API_USER_LOGIN_ROUTE = "/api/v1/user/login";
    public static final String API_USER_REFRESH_TOKEN_ROUTE = "/api/v1/user/refresh";
    public static final String API_USER_ROUTE = "/api/v1/user";

    public static final String API_V1_TRACKER_ROUTE = "/api/v1/tracker";
    public static final String STOCK_TICKER_SAVE_ROUTE = "/save";
    public static final String STOCK_TICKER_GET_SAVED_ROUTE = "/saved";
}
