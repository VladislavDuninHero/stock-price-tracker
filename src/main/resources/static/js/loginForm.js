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
                const now = new Date();
                const daysToAdd = 7;
                now.setTime(now.getTime() + (daysToAdd * 24 * 60 * 60 * 1000));

                const expires = now.toUTCString();
                console.log("cookie")
                document.cookie = `token=${data.authentication.accessToken}; expires=${expires}; path=/;`;

                fetch(Constant.HOME_URL, {
                    headers: {
                        "Authorization": "Bearer " + data.authentication.accessToken
                    }
                });

                window.location.href = Constant.HOME_URL;
            })
            .catch(error => console.log(error));
    });