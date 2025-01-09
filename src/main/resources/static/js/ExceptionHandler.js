export class ExceptionHandler {

    constructor(field) {
        this.field = field;
    }

    errors = new Map([
        ["VALIDATION_ERROR", "Invalid value"],
        ["USER_IS_ALREADY_EXISTS", "User is already registered"]
    ]);

    handle(field, errorCode, error) {
        this.field.style.color = "red";
        this.field.textContent = this.errors.get(errorCode) + ": " + field + " " + error;
    }
}