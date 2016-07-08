package com.tonyostudios.stockwatchlist.model;

import io.realm.RealmObject;

/**
 * Created by tonyofrancis on 6/25/16.
 */
public class StockPrice extends RealmObject {

    private long date;
    private double low;
    private double high;
    private double close;
    private double open;

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
