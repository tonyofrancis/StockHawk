package com.sam_chordas.android.stockhawk.provider;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.activity.StockListActivity;
import com.sam_chordas.android.stockhawk.service.WidgetService;

/**
 * Created by tonyofrancis on 6/27/16.
 */
public class WidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {

            Intent svcIntent = new Intent(context, WidgetService.class);

            svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

            views.setRemoteAdapter(R.id.stock_list_view,svcIntent);


            Intent clickIntent = new Intent(context, StockListActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.stock_list_view,clickPI);

            appWidgetManager.updateAppWidget(appWidgetIds[i],views);
        }

        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }
}
