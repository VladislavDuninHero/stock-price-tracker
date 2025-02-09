export class Constant {
    static LOGIN_URL = "/api/v1/user/login";
    static LOGIN_PAGE_URL = "/login";
    static REGISTRATION_URL = "/api/v1/user/registration";
    static HOME_URL = "/";
    static SAVE_TICKER_URL = "/api/v1/tracker/save";
    static GET_SAVED_TICKER_URL = "/api/v1/tracker/saved";
    static RESTORE_PASSWORD_URL = "/api/v1/user/restore";
    static UPDATE_PASSWORD_AFTER_RESTORE_URL = "/api/v1/user/restore/update";

    static REGISTRATION_FORM_ERROR_CONTAINER = ".registration-form__error-container";
    static LOGIN_FORM_ERROR_CONTAINER = ".login-form__error-container";

    static SUCCESS_MESSAGE = "Success";
    static SUCCESS_MESSAGE_AFTER_SEND_LINK_FOR_RESTORE_PASSWORD = "Письмо успешно отправлено на ваш email";

    static TICKERS_EMPTY_FIELD = ".article-section-content__span-if-empty";
    static TICKERS_EMPTY_MESSAGE = "Tickers is not added";

    static COOKIE_TOKEN = "token";
    static BEARER = "Bearer ";
}