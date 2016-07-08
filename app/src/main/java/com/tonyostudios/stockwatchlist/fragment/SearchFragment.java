package com.tonyostudios.stockwatchlist.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tonyostudios.stockwatchlist.R;
import com.tonyostudios.stockwatchlist.adapter.StockAdapter;
import com.tonyostudios.stockwatchlist.adapter.StockDataOnClickListener;
import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.task.StockQueryAsyncTask;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tonyofrancis on 6/24/16.
 */
public class SearchFragment extends Fragment
        implements StockDataOnClickListener, StockQueryAsyncTask.Callback {

    @BindView(R.id.results_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_edit_text)
    SearchView mSearchView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar mProgressBar;

    private StockAdapter mStockAdapter;
    private boolean isSearching;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isSearching = false;
        mStockAdapter = new StockAdapter(this, R.string.search_empty);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mStockAdapter);

        showLoadingView(isSearching);
        setUpSearchView();
        setUpProgressBar();
    }

    private void setUpProgressBar() {
        mProgressBar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.off_white));
        mProgressBar.getIndeterminateDrawable()
                    .setColorFilter(ContextCompat.getColor(getActivity(), R.color.colorSecondary), PorterDuff.Mode.SRC_IN);
    }



    private void setUpSearchView() {

        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint(getString(R.string.symbol_search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mStockAdapter.swapDataSet(null);

                if (!isSearching && !query.isEmpty()) {

                    isSearching = true;
                    showLoadingView(isSearching);
                    new StockQueryAsyncTask(SearchFragment.this).execute(query);
                    return true;
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
                    mStockAdapter.setEmptyStringId(R.string.search_empty);
                    mStockAdapter.swapDataSet(null);
                }

                return false;
            }
        });
    }

    @Override
    public void onItemClick(StockData stockData) {

        String message = getString(R.string.stock_added,stockData.getSymbol());
        Toast.makeText(getActivity(),message, Toast.LENGTH_SHORT).show();

        saveStockInRealm(stockData);
        getActivity().finish();
    }

    private Realm getDefaultRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getActivity()).build();

        return Realm.getInstance(configuration);
    }

    private void saveStockInRealm(StockData stockData) {
        Realm realm = getDefaultRealm();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(stockData);
        realm.commitTransaction();
    }

    private void showLoadingView(boolean isLoading) {

        if(isLoading) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDataLoaded(List<StockData> dataSet) {

        isSearching = false;
        showLoadingView(isSearching);

        if(dataSet == null || dataSet.size() == 0) {
            mStockAdapter.setEmptyStringId(R.string.no_item_found);
        }

        mStockAdapter.swapDataSet(dataSet);
    }
}