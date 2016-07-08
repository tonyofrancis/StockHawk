package com.tonyostudios.stockwatchlist.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.tonyostudios.stockwatchlist.R;

/**
 * Created by tonyofrancis on 6/28/16.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @IdRes
    protected int getFragmentId() {
        return R.id.fragment_container;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(getFragmentId());

        if(fragment == null) {
            fragment = createFragment();

            fragmentManager.beginTransaction()
                    .add(getFragmentId(),fragment)
                    .commit();
        }
    }
}
