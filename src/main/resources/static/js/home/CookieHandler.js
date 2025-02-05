export class CookieHandler {

    getCookie(cookieName) {
        const cookies = document.cookie.split(";");

        for (let cookie of cookies) {
            const [key, value] = cookie.split("=");

            if (key === cookieName) {
                return decodeURIComponent(value);
            }
        }

        return null;
    }
}