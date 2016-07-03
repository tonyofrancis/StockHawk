package com.sam_chordas.android.stockhawk.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.activity.SearchActivity;
import com.sam_chordas.android.stockhawk.activity.StockDetailActivity;
import com.sam_chordas.android.stockhawk.adapter.StockAdapter;
import com.sam_chordas.android.stockhawk.adapter.StockDataOnClickListener;
import com.sam_chordas.android.stockhawk.loader.StockLoader;
import com.sam_chordas.android.stockhawk.model.StockData;
import com.sam_chordas.android.stockhawk.touchhelper.ItemTouchHelperAdapter;
import com.sam_chordas.android.stockhawk.touchhelper.ItemTouchHelperSimpleCallback;
import com.sam_chordas.android.stockhawk.util.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tonyofrancis on 6/19/16.
 */

public final class StockListFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<List<StockData>>,
                   StockDataOnClickListener, ItemTouchHelperAdapter {

    private static final String PREFS = "com.sam_chordas.android.stockhawk.fragment.StockListFragment.PREFS";

    private static final int STOCK_LOADER_ID = 101;

    @BindView(R.id.stock_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ItemTouchHelper mItemTouchHelper;
    private StockAdapter mStockAdapter;
    private boolean isRefreshing;
    private boolean isInit;


    public static StockListFragment newInstance() {
        return new StockListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isRefreshing = false;

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperSimpleCallback(0, ItemTouchHelper.LEFT,this));
        mStockAdapter = new StockAdapter(this);

        if(savedInstanceState == null) {
            isInit = true;
        } else {
            isInit = false;
        }

        getLoaderManager().initLoader(STOCK_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.fragment_stock_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);

        setUpRecyclerView();
        setUpSwipeToRefresh();
        
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mStockAdapter);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    private void setUpSwipeToRefresh() {

        int color1 = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        int color2 = ContextCompat.getColor(getActivity(), R.color.colorSecondary);
        int color3 = ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark);

        mSwipeRefreshLayout.setColorSchemeColors(color1,color2,color3);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(!isRefreshing) {

                   Loader<List<StockData>> loader = getLoaderManager().getLoader(STOCK_LOADER_ID);
                    ((StockLoader)loader).refreshData();
                    isRefreshing = true;
                } else {
                    stopRefresh();
                }
            }
        });
    }

    private void stopRefresh() {
        isRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void showSwipeToRefreshMessage() {
        AppUtils.showSnackBarMessage(mRecyclerView, R.string.swipe_to_refresh_message);
        savePrefs();
    }

    private void savePrefs() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        SharedPreferencesCompat.EditorCompat
                                .getInstance()
                                .apply(sharedPreferences.edit().putBoolean(PREFS,true));
    }

    private boolean isFirstAppLaunch() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        return !sharedPreferences.contains(PREFS);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_change_units: {
                mStockAdapter.switchStockChangeView();
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(StockData stockData) {
        startActivity(StockDetailActivity.newIntent(getActivity(),stockData));
    }

    @Override
    public Loader<List<StockData>> onCreateLoader(int id, Bundle args) {

        if (id == STOCK_LOADER_ID) {
            return StockLoader.newInstance(getActivity());
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<StockData>> loader, List<StockData> dataSet) {

        if (loader.getId() == STOCK_LOADER_ID) {

            if(dataSet == null || dataSet.size() == 0) {
                mStockAdapter.swapDataSet(null);
            } else {
                mStockAdapter.swapDataSet(dataSet);

                if(isFirstAppLaunch()) {
                    showSwipeToRefreshMessage();
                }
            }

            stopRefresh();

            //App was just launched. Pull new data from API
            if(isInit) {
                isInit = false;
                ((StockLoader)loader).refreshData();
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<StockData>> loader) {

        if (loader.getId() == STOCK_LOADER_ID) {
            mStockAdapter.swapDataSet(null);
            stopRefresh();
        }
    }


    @OnClick(R.id.add_stock_button)
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),SearchActivity.class));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    @Override
    public void onItemDismiss(int position) {

        //User deleted item from the watch list. Inform the loader
        StockData stockData = mStockAdapter.getItem(position);
        Loader<List<StockData>> loader = getLoaderManager().getLoader(STOCK_LOADER_ID);
        ((StockLoader)loader).discardItem(stockData);
    }
}
