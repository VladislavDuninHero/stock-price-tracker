import {Constant} from "./Constant.js";
import {ExceptionHandler} from "./ExceptionHandler.js";

document.querySelector(".main-registration-page__registration-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const login = document.querySelector(".registration-form__login-field");
        const password = document.querySelector(".registration-form__password-field");
        const field = document.querySelector(Constant.REGISTRATION_FORM_ERROR_CONTAINER);
        const exceptionHandler = new ExceptionHandler(field);

        field.textContent = "";

        const userData = {
            "login": login.value,
            "password": password.value
        };

        fetch(Constant.REGISTRATION_URL, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(userData)
        })
            .then(async response => {
                const json = await response.json();

                if (response.ok) {
                    field.style.color = "green";
                    field.style.border = "1px solid #01810133";
                    field.style.backgroundColor = "#03ff0361";
                    field.textContent = Constant.SUCCESS_MESSAGE;
                    return json;
                }

                const errors = json.errors.map(el => {
                    return {
                        errorCode: el.errorCode,
                        errorMessage: el.errorMessage
                    }
                })

                throw new AggregateError(errors);
            })
            .catch(error => error.errors
                .forEach(e => exceptionHandler.handle(
                        e.errorCode,
                        e.errorMessage
                    )
                )
            );
    });