import {Constant} from "./Constant.js";
import {ExceptionHandler} from "./ExceptionHandler.js";
import {FieldFactory} from "./FieldFactory.js";
import {FieldNameEnum} from "./FildNameEnum.js";

document.querySelector(".main-registration-page__registration-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const login = document.querySelector(".registration-form__login-field");
        const password = document.querySelector(".registration-form__password-field");
        const exceptionHandler = new ExceptionHandler();
        const fieldFactory = new FieldFactory();

        const userDate = {
            "login": login.value,
            "password": password.value
        };

        fetch(Constant.REGISTRATION_URL, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(userDate)
        })
            .then(async response => {
                const json = await response.json();
                if (response.ok) {
                    return json;
                }

                const errors = json.errors.map(el => {
                    return {
                        errorCode: el.errorCode,
                        errorMessage: el.errorMessage
                    }
                })

                console.log(errors)

                throw new AggregateError(errors);
            })
            .then(data => {
                localStorage.setItem("token", data.authentication.accessToken);

                return fetch(Constant.LOGIN_URL, {
                    headers: {
                        "Authorization": "Bearer " + data.authentication.accessToken
                    }
                });
            })
            .catch(async error => error.errors
                .forEach(e => exceptionHandler.handle(
                        fieldFactory.getField(FieldNameEnum.REGISTRATION_FORM_ERROR_CONTAINER),
                        e.errorCode,
                        e.errorMessage
                    )
                )
            );
    });