export class ExceptionHandler {

    constructor(field) {
        this.field = field;
    }

    errors = new Map([
        ["VALIDATION_ERROR", "Invalid value"],
        ["USER_IS_ALREADY_EXISTS", "User is already registered"],
        ["INVALID_CHARACTERS", "Login or password contains invalid characters"],
        ["USER_NOT_FOUND", "User not found"]
    ]);

    handle(errorCode, error) {
        const span = document.createElement("span");

        this.field.style.color = "red";
        this.field.style.border = "1px solid rgb(129 1 1 / 20%)";
        this.field.style.backgroundColor = "rgb(255 76 76 / 38%)";

        span.textContent = this.errors.get(errorCode) + ": " + error;
        this.field.append(span);
    }
}