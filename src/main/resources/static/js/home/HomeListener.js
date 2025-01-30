import {SaveTicker} from "./SaveTicker.js";
import {RenderContent} from "./RenderContent.js";

document.addEventListener("DOMContentLoaded", e => {


    const renderContent = new RenderContent();
    renderContent.render();

    const saveTickerHandler = new SaveTicker();
    saveTickerHandler.save();
    saveTickerHandler.getSaved();
})

