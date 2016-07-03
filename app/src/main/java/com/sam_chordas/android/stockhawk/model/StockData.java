package com.sam_chordas.android.stockhawk.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by tonyofrancis on 6/19/16.
 */

public class StockData extends RealmObject {

    @PrimaryKey
    String symbol;
    String name;
    double bidPrice;
    double change;
    double percentChange;
    boolean isUp;
    RealmList<StockPrice> stockPrices;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public RealmList<StockPrice> getStockPrices() {
        return stockPrices;
    }

    public void setStockPrices(RealmList<StockPrice> stockPrices) {
        this.stockPrices = stockPrices;
    }
}


