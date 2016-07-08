package com.tonyostudios.stockwatchlist.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

/**
 * Created by tonyofrancis on 7/2/16.
 */
public class AppUtils {

    public static void showToastMessage(Context context, @StringRes int stringRes) {
        Toast.makeText(context,stringRes, Toast.LENGTH_LONG).show();
    }

    public static void showSnackBarMessage(View view, @StringRes int stringRes) {
        Snackbar.make(view,stringRes, Snackbar.LENGTH_LONG).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
