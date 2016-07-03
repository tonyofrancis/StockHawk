package com.sam_chordas.android.stockhawk;

import android.app.Application;

import com.sam_chordas.android.stockhawk.util.AppUtils;

/**
 * Created by tonyofrancis on 7/2/16.
 */
public class StockApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if(!AppUtils.isNetworkAvailable(this)) {
            AppUtils.showToastMessage(this, R.string.network_not_available);
        }
    }
}
