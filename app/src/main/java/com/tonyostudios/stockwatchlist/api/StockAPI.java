package com.tonyostudios.stockwatchlist.api;

import yahoofinance.YahooFinance;

/**
 * Created by tonyofrancis on 6/19/16.
 */
public final class StockAPI extends YahooFinance {

    private final static String[] sDefaultStocks = new String[] {"YHOO","AAPL","GOOG","MSFT"};

    public static String[] getDefaultStocks() {
        return sDefaultStocks;
    }
}
