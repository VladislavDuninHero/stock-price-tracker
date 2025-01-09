import {ExceptionHandler} from "./ExceptionHandler.js";

document.addEventListener("DOMContentLoaded", (e) => {

    const exceptionContainer = document.querySelector(".registration-form__error-container");
    const exceptionHandler = new ExceptionHandler(exceptionContainer);
    const handler = new RegistrationForm(exceptionHandler);

    document.querySelector(".main-registration-page__registration-form")
        .addEventListener("submit", e => {
            e.preventDefault();

            const response = handler.submitFormInitiate();
            if (response.betError) {
                return;
            }

            exceptionContainer.textContent = "";

        });
});

class RegistrationForm {

    url = "/api/v1/user";
    betError = false;

    constructor(exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    async submitFormInitiate() {
        const login = document.querySelector(".registration-form__login-field");
        const password = document.querySelector(".registration-form__password-field");
        const form = document.querySelector(".main-registration-page__registration-form");

        form.addEventListener("submit", (e) => e.preventDefault());

        const userDTO = {
            login: login.value,
            password: password.value
        };

        return this.submitForm(userDTO);
    }

    async submitForm(data) {
        const response = await fetch(this.url, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(data)
        });

        try {
            if (!response.ok) {
                const json = await response.json();
                const errors = json.errors.map((el) => {
                    return {
                        field: el.field,
                        code: el.errorCode,
                        message: el.errorMessage
                    }
                });
                this.betError = true;

                throw new AggregateError(errors);
            }
        } catch (error) {
            error.errors.forEach(error => {
                this.exceptionHandler.handle(error.field, error.code, error.message);
            })
        }
    }
}
