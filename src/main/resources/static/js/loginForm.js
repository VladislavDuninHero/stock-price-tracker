import {Constant} from "./Constant.js";
import {ExceptionHandler} from "./ExceptionHandler.js";


document.querySelector(".main-login-page__login-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const login = document.querySelector(".login-form__login-field");
        const password = document.querySelector(".login-form__password-field");
        const field = document.querySelector(Constant.LOGIN_FORM_ERROR_CONTAINER);
        const exceptionHandler = new ExceptionHandler(field);

        const userData = {
            "login": login.value,
            "password": password.value
        };

        fetch(Constant.LOGIN_URL, {
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
            .then(async data => {
                const now = new Date();
                const daysToAdd = 7;
                now.setTime(now.getTime() + (daysToAdd * 24 * 60 * 60 * 1000));

                const expires = now.toUTCString();

                document.cookie = `token=${data.authentication.accessToken}; expires=${expires}; path=/;`;

                await fetch(Constant.HOME_URL, {
                    headers: {
                        "Authorization": "Bearer " + data.authentication.accessToken
                    }
                });

                window.location.href = Constant.HOME_URL;
            })
            .catch(error => error.errors
                .forEach(e => exceptionHandler.handle(
                        e.errorCode,
                        e.errorMessage
                    )
                )
            );
    });