package com.tonyostudios.stockwatchlist.activity;

import android.support.v4.app.Fragment;

import com.tonyostudios.stockwatchlist.fragment.SearchFragment;


/**
 * Created by tonyofrancis on 6/24/16.
 */
public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SearchFragment.newInstance();
    }
}
