import {Constant} from "./Constant.js";
import {ExceptionHandler} from "./ExceptionHandler.js";

document.querySelector(".main-login-page__login-form")
    .addEventListener("submit", (e) => {
        e.preventDefault();

        const login = document.querySelector(".login-form__login-field");
        const password = document.querySelector(".login-form__password-field");

        const userDate = {
            "login": login.value,
            "password": password.value
        };

        fetch(Constant.LOGIN_URL, {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(userDate)
        })
            .then(response => {
                if (response.ok) {
                    return response.json();
                }

                throw new Error("login failed");
            })
            .then(data => {
                localStorage.setItem("token", data.authentication.accessToken);

                return fetch(Constant.HOME_URL, {
                    headers: {
                        "Authorization": "Bearer " + data.authentication.accessToken
                    }
                });
            })
            .catch(error => console.log(error));
    });