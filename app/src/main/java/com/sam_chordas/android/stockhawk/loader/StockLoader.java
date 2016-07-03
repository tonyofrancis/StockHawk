package com.sam_chordas.android.stockhawk.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import com.sam_chordas.android.stockhawk.model.StockData;
import com.sam_chordas.android.stockhawk.task.StockQueryAsyncTask;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by tonyofrancis on 6/19/16.
 */

public final class StockLoader extends Loader<List<StockData>>
        implements StockQueryAsyncTask.Callback {

    public static StockLoader newInstance(Context context) {
        return new StockLoader(context);
    }

    public StockLoader(Context context) {
        super(context);
    }


    private Realm getDefaultRealm() {
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder(getContext()).build());
        return Realm.getDefaultInstance();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if(isStarted()) {
            fetchData();
        }
    }

    private void fetchData() {

        final Realm realm = getDefaultRealm();

        List<StockData> dataSet = new ArrayList<>();
        dataSet.addAll(realm.where(StockData.class).findAll().sort("symbol", Sort.ASCENDING));

        super.deliverResult(dataSet);
    }

    @Override
    public void onDataLoaded(List<StockData> dataSet) {
        saveStockDataToRealm(dataSet);
    }

    public void refreshData() {

        String[] symbols = getRealmSymbols();

        if(symbols.length > 0) {
            new StockQueryAsyncTask(this).execute(getRealmSymbols());
        } else {
            startLoading();
        }
    }

    private void saveStockDataToRealm(final List<StockData> dataSet) {

        if(dataSet == null) {
            startLoading();
            return;
        }


        getDefaultRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                for (StockData stockData : dataSet) {
                    realm.copyToRealmOrUpdate(stockData);
                }

                startLoading();
            }
        });

    }

    public void discardItem(final StockData stockData) {

        getDefaultRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                RealmResults<StockData> results = realm.where(StockData.class)
                                                       .equalTo("symbol",stockData.getSymbol())
                                                       .findAll();

                results.deleteFirstFromRealm();
                startLoading();
            }
        });
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
}
