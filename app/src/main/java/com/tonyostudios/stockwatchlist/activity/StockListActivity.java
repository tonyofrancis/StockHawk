package com.tonyostudios.stockwatchlist.activity;

import android.support.v4.app.Fragment;

import com.tonyostudios.stockwatchlist.fragment.StockListFragment;


public class StockListActivity extends SingleFragmentActivity {

  @Override
  protected Fragment createFragment() {
    return StockListFragment.newInstance();
  }
}
