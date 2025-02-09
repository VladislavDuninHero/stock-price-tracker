import {Constant} from "../Constant.js";
import {ExceptionHandler} from "../ExceptionHandler.js";

document.querySelector(".update-password-container__update-password-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const password = document.querySelector(".update-password-form__password-field");
        const errorField = document.querySelector(".update-password-form__error-container");
        const updateButton = document.querySelector(".update-password-form-controllers__send-mail-button");
        const exceptionHandler = new ExceptionHandler(errorField);

        errorField.textContent = "";
        updateButton.disabled = true;

        const userData = {
            "newPassword": password.value
        };

        const tokenParam = document.URL.substring(document.URL.indexOf("token"));

        fetch(Constant.UPDATE_PASSWORD_AFTER_RESTORE_URL + "?" + tokenParam, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(userData)
        })
            .then(async response => {
                const json = await response.json();

                updateButton.disabled = false;

                if (response.ok) {
                    errorField.style.color = "green";
                    errorField.style.border = "1px solid #01810133";
                    errorField.style.backgroundColor = "#03ff0361";
                    errorField.style.borderRadius = "5px";
                    errorField.style.padding = "5px";
                    errorField.style.display = "flex"
                    errorField.style.justifyContent = "center"
                    errorField.style.alignItems = "center"
                    errorField.textContent = Constant.SUCCESS_MESSAGE;
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