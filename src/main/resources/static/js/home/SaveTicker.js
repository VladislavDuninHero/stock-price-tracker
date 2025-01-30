import {Constant} from "../Constant.js";
import {ExceptionHandler} from "../ExceptionHandler.js";
import {RenderContent} from "./RenderContent.js";

export class SaveTicker {

    saveButtonSelector;
    renderContentService;
    errorGetSavedTickersContainerSelector;
    errorSaveTickerContainerSelector;
    exceptionHandler;

    constructor() {
        this.saveButtonSelector = document.querySelector(".article-save-ticker__button-save");
        this.getButtonSelector = document.querySelector(".article-get-ticker__button-get");
        this.errorGetSavedTickersContainerSelector = document.querySelector(".article-get-ticker-container__error-container");
        this.errorSaveTickerContainerSelector = document.querySelector(".article-save-ticker-container__error-container");
        this.renderContentService = new RenderContent();
    }

    save() {
        this.saveButtonSelector.addEventListener("click", e => {
            let ticker = document.querySelector(".article-save-ticker__input-save-field");
            let start = document.querySelector(".article-save-ticker__input-date-start-field");
            let end = document.querySelector(".article-save-ticker__input-date-end-field");

            const data = {
                "ticker": ticker.value.toUpperCase(),
                "start": start.value,
                "end": end.value
            }

            this.exceptionHandler = new ExceptionHandler(this.errorSaveTickerContainerSelector);

            this.saveButtonSelector.disabled = true;

            fetch("api/v1/tracker/save", {
                method: "POST",
                credentials: "include",
                headers: {
                    "Content-type": "application/json",
                },
                body: JSON.stringify(data)
            })
                .then(async response => {
                    const json = await response.json();

                    if (response.ok) {
                        this.saveButtonSelector.disabled = false;

                        this.errorSaveTickerContainerSelector.style.color = "green";
                        this.errorSaveTickerContainerSelector.style.border = "1px solid #01810133";
                        this.errorSaveTickerContainerSelector.style.backgroundColor = "#03ff0361";
                        this.errorSaveTickerContainerSelector.textContent = "";
                        this.errorSaveTickerContainerSelector.textContent = Constant.SUCCESS_MESSAGE;

                        return json;
                    }

                    this.saveButtonSelector.disabled = false;

                    const errors = json.errors.map(el => {
                        return {
                            errorCode: el.errorCode,
                            errorMessage: el.errorMessage
                        }
                    })

                    this.errorSaveTickerContainerSelector.textContent = "";

                    throw new AggregateError(errors);
                })
                .catch(error => error.errors
                    .forEach(e => this.exceptionHandler.handle(
                            e.errorCode,
                            e.errorMessage
                        )
                    )
                );
        });
    }

    getSaved() {

        this.getButtonSelector.addEventListener("click", e => {

            this.getButtonSelector.disabled = true;

            let ticker = document.querySelector(".article-get-ticker__input-get-field");

            if (document.querySelector(".article-content-container__ticker-container") !== null) {
                document.querySelectorAll(".article-content-container__ticker-container")
                    .forEach(ticker => ticker.remove());
            }

            this.exceptionHandler = new ExceptionHandler(this.errorGetSavedTickersContainerSelector);

            fetch(`api/v1/tracker/saved?symbol=${ticker.value.toUpperCase()}`, {
                method: "GET",
                credentials: "include",
                headers: {
                    "Content-type": "application/json",
                }
            })
                .then(async response => {
                    const json = await response.json();

                    if (response.ok) {
                        this.getButtonSelector.disabled = false

                        this.renderContentService.render();

                        this.errorGetSavedTickersContainerSelector.style.color = "green";
                        this.errorGetSavedTickersContainerSelector.style.border = "1px solid #01810133";
                        this.errorGetSavedTickersContainerSelector.style.backgroundColor = "#03ff0361";
                        this.errorGetSavedTickersContainerSelector.textContent = "";
                        this.errorGetSavedTickersContainerSelector.textContent = Constant.SUCCESS_MESSAGE;

                        return json;
                    }

                    this.getButtonSelector.disabled = false;

                    const errors = json.errors.map(el => {
                        return {
                            errorCode: el.errorCode,
                            errorMessage: el.errorMessage
                        }
                    })

                    this.errorSaveTickerContainerSelector.textContent = "";

                    throw new AggregateError(errors);
                })
                .then(json => {

                    json.data.forEach(ticker => {
                        let div = document.createElement("div");

                        div.classList.add("article-content-container__ticker-container");

                        let date = document.createElement("span");
                        date.classList.add("div-ticker-container__span")
                        date.style.backgroundColor = "#d9d9d9";

                        let open = document.createElement("span");
                        open.classList.add("div-ticker-container__span");
                        open.style.backgroundColor = "#00dd1b87";

                        let close = document.createElement("span");
                        close.classList.add("div-ticker-container__span");
                        close.style.backgroundColor = "#efff0087";

                        let highest = document.createElement("span");
                        highest.classList.add("div-ticker-container__span");
                        highest.style.backgroundColor = "#ff000087";

                        let lowest = document.createElement("span");
                        lowest.classList.add("div-ticker-container__span");
                        lowest.style.backgroundColor = "#005aff87";

                        date.textContent = "date: " + ticker.date + "\n";
                        open.textContent = "open: " + ticker.open + "\n";
                        close.textContent = "close: " + ticker.close + "\n";
                        highest.textContent = "highest: " + ticker.highest + "\n";
                        lowest.textContent = "lowest: " + ticker.lowest + "\n";

                        div.append(date);
                        div.append(open);
                        div.append(close);
                        div.append(highest);
                        div.append(lowest);

                        div.append("\n");

                        document.querySelector(".content-ticker-container__content").append(div)
                    })

                    this.renderContentService.render();
                })
                .catch(error => error.errors
                    .forEach(e => this.exceptionHandler.handle(
                            e.errorCode,
                            e.errorMessage
                        )
                    )
                );

        });

    }
}