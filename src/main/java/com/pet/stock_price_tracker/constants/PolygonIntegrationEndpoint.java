package com.pet.stock_price_tracker.constants;

public final class PolygonIntegrationEndpoint {
    private PolygonIntegrationEndpoint() {}
    public static final String GET_TICKERS_ENDPOINT = "/aggs/ticker/{ticker}/range/{multiplier}/{timespan}/{from}/{to}";
}
