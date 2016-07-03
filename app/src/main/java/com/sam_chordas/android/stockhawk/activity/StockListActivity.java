package com.sam_chordas.android.stockhawk.activity;

import android.support.v4.app.Fragment;

import com.sam_chordas.android.stockhawk.fragment.StockListFragment;

public class StockListActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return StockListFragment.newInstance();
  }
}
