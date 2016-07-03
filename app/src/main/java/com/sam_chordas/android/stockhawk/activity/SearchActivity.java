package com.sam_chordas.android.stockhawk.activity;

import android.support.v4.app.Fragment;

import com.sam_chordas.android.stockhawk.fragment.SearchFragment;

/**
 * Created by tonyofrancis on 6/24/16.
 */
public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return SearchFragment.newInstance();
    }
}
