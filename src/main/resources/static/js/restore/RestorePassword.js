import {Constant} from "../Constant.js";
import {ExceptionHandler} from "../ExceptionHandler.js";

document.querySelector(".restore-password-container__restore-password-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const email = document.querySelector(".restore-password-form__email-field");
        const errorField = document.querySelector(".restore-password-form__error-container");
        const sendButton = document.querySelector(".restore-password-form-controllers__send-mail-button");
        const exceptionHandler = new ExceptionHandler(errorField);

        errorField.textContent = "";
        sendButton.disabled = true;

        let span = document.createElement("span");
        span.textContent = "Идет отправка сообщения на почту...";
        errorField.style.backgroundColor = "white"
        errorField.style.border = "none";
        span.style.color = "black";
        errorField.append(span);

        const userData = {
            "email": email.value
        };

        fetch(Constant.RESTORE_PASSWORD_URL, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(userData)
        })
            .then(async response => {
                const json = await response.json();

                sendButton.disabled = false;
                errorField.querySelector("span").remove();

                if (response.ok) {
                    errorField.style.color = "green";
                    errorField.style.border = "1px solid #01810133";
                    errorField.style.backgroundColor = "#03ff0361";
                    errorField.style.borderRadius = "5px";
                    errorField.style.padding = "5px";
                    errorField.style.display = "flex"
                    errorField.style.justifyContent = "center"
                    errorField.style.alignItems = "center"
                    errorField.textContent = Constant.SUCCESS_MESSAGE_AFTER_SEND_LINK_FOR_RESTORE_PASSWORD;
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