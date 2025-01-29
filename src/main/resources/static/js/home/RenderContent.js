import {Constant} from "../Constant.js";

export class RenderContent {

    tickersContainer;

    constructor() {
        this.tickersContainer = document.querySelector(".main__section-content-ticker-container");
    }

    render() {

        if (this.tickersContainer.children.length === 0) {
            let span = document.createElement("span");
            span.classList.add("article-section-content__span-if-empty");
            span.textContent = Constant.TICKERS_EMPTY_MESSAGE;

            this.tickersContainer.append(span);

            return;
        }

        let span = this.tickersContainer.querySelector(".article-section-content__span-if-empty");
        if (span != null) {
            span.remove();
        }
    }
}