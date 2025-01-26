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
    public static final String SYMBOL_PARAM_IS_NOT_BE_EMPTY = "Symbol param is not be empty";
    public static final String INVALID_CHARACTERS_EXCEPTION = "Login or password contains invalid characters";
    public static final String SPACES_SYMBOLS_CONTAINS = "Login or password contains space symbols";
    public static final String LOGIN_IS_NOT_BE_EMPTY = "Login is not be empty";
    public static final String LOGIN_IS_REQUIRED = "Login is required";
    public static final String PASSWORD_IS_NOT_BE_EMPTY = "Password is not be empty";
    public static final String PASSWORD_IS_REQUIRED = "Password is required";
    public static final String LOGIN_LENGTH_IS_INVALID = "Login must be between 1 and 64 symbols";
    public static final String PASSWORD_LENGTH_IS_INVALID = "Password must be between 6 and 64 symbols";
}
