package com.tonyostudios.stockwatchlist.util;


import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.model.StockPrice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.realm.RealmList;
import yahoofinance.Stock;
import yahoofinance.histquotes.HistoricalQuote;

/**
 * Created by tonyofrancis on 6/19/16.
 */
public final class StockUtils {

    public static List<StockData> getStockDataList(Map<String, Stock> stocksMap) {

        List<StockData> dataSet = new ArrayList<>();

        for (String key : stocksMap.keySet()) {

            Stock stock = stocksMap.get(key);


            StockData stockData = new StockData();
            stockData.setSymbol(stock.getSymbol());
            stockData.setBidPrice(stock.getQuote().getBid().doubleValue());
            stockData.setChange(stock.getQuote().getChange().doubleValue());
            stockData.setPercentChange(stock.getQuote().getChangeInPercent().doubleValue());
            stockData.setName(stock.getName());


            if(stock.getQuote().getChange().toString().startsWith("-")) {
                stockData.setUp(false);
            } else {
                stockData.setUp(true);
            }


            try {
                stockData.setStockPrices(new RealmList<StockPrice>());
                List<HistoricalQuote> historicalQuotes = stock.getHistory();

                StockPrice currentPrice = new StockPrice();
                currentPrice.setClose(-1);
                currentPrice.setHigh(stock.getQuote().getDayHigh().doubleValue());
                currentPrice.setLow(stock.getQuote().getDayLow().doubleValue());
                currentPrice.setOpen(stock.getQuote().getOpen().doubleValue());
                currentPrice.setDate(Calendar.getInstance().getTimeInMillis());

                stockData.getStockPrices().add(currentPrice);

                for (HistoricalQuote quote : historicalQuotes) {

                    StockPrice stockPrice = new StockPrice();
                    stockPrice.setClose(quote.getClose().doubleValue());
                    stockPrice.setHigh(quote.getHigh().doubleValue());
                    stockPrice.setLow(quote.getLow().doubleValue());
                    stockPrice.setOpen(quote.getOpen().doubleValue());
                    stockPrice.setDate(quote.getDate().getTimeInMillis());

                    stockData.getStockPrices().add(stockPrice);
                }

            }catch (Exception error) {
                error.printStackTrace();
            }

            dataSet.add(stockData);
        }

        return dataSet;
    }

    public static String getDateAsString(long time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());
        Date date = new Date(time);
        return dateFormat.format(date);
    }
}
