package com.pet.stock_price_tracker.constants;

public final class ExceptionMessage {
    private ExceptionMessage() {}
    public static final String USER_IS_ALREADY_REGISTERED_EXCEPTION = "User is already registered";
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    public static final String USER_PASSWORD_IS_INVALID = "User password or login is invalid";
    public static final String NOT_IMPLEMENTED_EXCEPTION = "%s is not implemented";
    public static final String TICKER_NOT_FOUND_EXCEPTION = "Ticker not found";
    public static final String END_BEFORE_START_DATE_EXCEPTION = "The end date cannot be before the start date.";
    public static final String AUTHORIZATION_HEADER_IS_MISSING_EXCEPTION = "Authorization header is missing";
}
