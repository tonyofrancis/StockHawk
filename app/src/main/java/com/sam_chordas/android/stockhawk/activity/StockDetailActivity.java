package com.sam_chordas.android.stockhawk.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.sam_chordas.android.stockhawk.fragment.StockDetailFragment;
import com.sam_chordas.android.stockhawk.model.StockData;

/**
 * Created by tonyofrancis on 6/25/16.
 */
public class StockDetailActivity extends SingleFragmentActivity {

    private static final String STOCK_EXTRA = "com.sam_chordas.android.stockhawk.activity.stockDetailActivity.EXTRA";

    @Override
    protected Fragment createFragment() {
        return StockDetailFragment.newInstance(getIntent().getStringExtra(STOCK_EXTRA));
    }

    public static Intent newIntent(Context context, StockData stockData) {
        Intent intent = new Intent(context,StockDetailActivity.class);
        intent.putExtra(STOCK_EXTRA,stockData.getSymbol());

        return intent;
    }
}
