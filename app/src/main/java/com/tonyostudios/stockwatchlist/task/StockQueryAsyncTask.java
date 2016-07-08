package com.tonyostudios.stockwatchlist.task;

import android.os.AsyncTask;

import com.tonyostudios.stockwatchlist.api.StockAPI;
import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.util.StockUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.histquotes.Interval;

/**
 * Created by tonyofrancis on 6/29/16.
 */
public class StockQueryAsyncTask extends AsyncTask<String, Void, List<StockData>> {

    private Callback mCallback;

    public StockQueryAsyncTask(Callback callback) {
        super();
        mCallback = callback;
    }

    @Override
    protected List<StockData> doInBackground(String... strings) {

        try {

            Calendar from = Calendar.getInstance();
            Calendar to = Calendar.getInstance();
            from.add(Calendar.WEEK_OF_MONTH, -2);

            Map<String, Stock> dataSet = StockAPI.get(strings,from,to, Interval.DAILY);
            return StockUtils.getStockDataList(dataSet);

        }catch (Exception error) {
            error.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<StockData> dataSet) {
        super.onPostExecute(dataSet);
        mCallback.onDataLoaded(dataSet);
    }

    public interface Callback {
        void onDataLoaded(List<StockData> dataSet);
    }
}