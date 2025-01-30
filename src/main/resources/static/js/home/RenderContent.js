import {Constant} from "../Constant.js";

export class RenderContent {

    tickersContainer;
    tickersContent;
    personalAccountContainer;

    constructor() {
        this.tickersContainer = document.querySelector(".main__section-content-ticker-container");
        this.tickersContent = document.querySelector(".content-ticker-container__content");
        this.personalAccountContainer = document.querySelector(".personal-account-container");
    }

    render() {

        this.renderEmptyContentMessage();
    }

    renderEmptyContentMessage() {
        let span = document.querySelector(".content-ticker-container__ticker-message");

        if (
            this.tickersContent.children.length === 0
            && span != null
        ) {

            span.textContent = Constant.TICKERS_EMPTY_MESSAGE;

        }

        if (span != null && this.tickersContent.children.length > 0) {
            span.remove();
        }
    }

}