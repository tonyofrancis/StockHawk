package com.tonyostudios.stockwatchlist.service;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.tonyostudios.stockwatchlist.factory.StockRemoteViewsFactory;


/**
 * Created by tonyofrancis on 6/27/16.
 */
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockRemoteViewsFactory(getApplicationContext(),intent);
    }
}
