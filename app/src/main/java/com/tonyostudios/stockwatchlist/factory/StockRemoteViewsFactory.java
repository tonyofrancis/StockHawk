package com.tonyostudios.stockwatchlist.factory;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tonyostudios.stockwatchlist.R;
import com.tonyostudios.stockwatchlist.activity.StockListActivity;
import com.tonyostudios.stockwatchlist.api.StockAPI;
import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.task.StockQueryAsyncTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by tonyofrancis on 6/30/16.
 */
public class StockRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory,
                                                StockQueryAsyncTask.Callback {

    private List<StockData> mDataList;
    private Context mContext;
    private int appWidgetId;


    public StockRemoteViewsFactory(Context context, Intent intent) {
        super();
        mContext = context;
        mDataList = new ArrayList<>();

        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


        fetchData();
    }

    @Override
    public void onCreate() {

    }

    private void fetchData() {
        String[] symbols = getRealmSymbols();

        if(symbols.length < 1) {
            symbols = StockAPI.getDefaultStocks();
        }

        new StockQueryAsyncTask(this).execute(symbols);
    }

    @Override
    public void onDataLoaded(List<StockData> dataSet) {
        saveStockDataToRealm(dataSet);
    }

    private void notifyDataSetChanged() {
        AppWidgetManager manager = (AppWidgetManager) mContext.getSystemService(mContext.APPWIDGET_SERVICE);
        manager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.stock_list_view);
    }

    private Realm getDefaultRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(mContext).build();

        return Realm.getInstance(configuration);
    }

    private void saveStockDataToRealm(final List<StockData> dataSet) {

        if(dataSet == null) {
            return;
        }

        getDefaultRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (StockData stockData : dataSet) {
                    realm.copyToRealmOrUpdate(stockData);
                }
            }
        });

        mDataList = dataSet;
        notifyDataSetChanged();
    }

    private String[] getRealmSymbols() {

        Realm realm = getDefaultRealm();
        RealmResults<StockData> realmResults =  realm.where(StockData.class).findAll();

        String[] symbols = new String[realmResults.size()];

        for (int i = 0; i < realmResults.size(); i++) {
            symbols[i] = realmResults.get(i).getSymbol();
        }

        return symbols;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        return mDataList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        StockData stockData = mDataList.get(i);

        RemoteViews row;

        if(stockData.isUp()) {
            row = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_list_item_quote_up);
        } else {
            row = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_list_item_quote_down);
        }

        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("0.00");


        row.setTextViewText(R.id.stock_symbol,stockData.getSymbol());
        row.setTextViewText(R.id.bid_price,df.format(stockData.getBidPrice()));


        String change = df.format(stockData.getPercentChange());



        String changeString;

        if(stockData.isUp()) {
           changeString = mContext.getString(R.string.stock_percent_change,change);
        } else {
            changeString = mContext.getString(R.string.stock_percent_change_decrease,change);
        }

        row.setTextViewText(R.id.change,changeString);

        Intent clickIntent = new Intent(mContext, StockListActivity.class);
        row.setOnClickFillInIntent(R.id.widget_list_stock_item,clickIntent);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}