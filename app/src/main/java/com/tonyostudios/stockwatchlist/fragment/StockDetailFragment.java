package com.tonyostudios.stockwatchlist.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonyostudios.stockwatchlist.R;
import com.tonyostudios.stockwatchlist.adapter.StockPriceAdapter;
import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.model.StockPrice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by tonyofrancis on 6/25/16.
 */
public class StockDetailFragment extends Fragment {

    private static final String EXTRA_SYMBOL = "com.sam_chordas.android.stockhawk.fragment.StockDetailFragment.SYMBOL";

    @BindView(R.id.line_chart)
    LineChartView mLineChartView;

    @BindView(R.id.stock_price_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.stock_title)
    TextView mTitleTextView;

    public static StockDetailFragment newInstance(String symbol) {
        StockDetailFragment fragment = new StockDetailFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_SYMBOL,symbol);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View view = inflater.inflate(R.layout.fragment_stock_detail,container,false);
        ButterKnife.bind(this,view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final StockData stockData = getStockData();

        setUpRecyclerView(stockData);


        mTitleTextView.setText(stockData.getName());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(stockData.getSymbol());

        setUpChart(stockData);
    }

    private void setUpRecyclerView(StockData stockData) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        StockPriceAdapter adapter = new StockPriceAdapter();
        adapter.swapDataSet(stockData.getStockPrices());

        mRecyclerView.setAdapter(adapter);
    }

    private void setUpChart(StockData stockData) {

        if(stockData.getStockPrices() == null || stockData.getStockPrices().size() == 0) {
            return;
        }

        List<StockPrice> dataSet = new ArrayList<>();
        dataSet.addAll(stockData.getStockPrices());

        Collections.reverse(dataSet);


        List<PointValue> pointList = new ArrayList<>();

        for (int i = 0; i < dataSet.size(); i++) {

            StockPrice stockPrice = dataSet.get(i);

            PointValue pointValue = new PointValue(i,(float) Math.random() * 100);
            pointValue.setTarget(i,(float)stockPrice.getHigh());

            pointList.add(pointValue);
        }


        List<Line> lines = new ArrayList<>();
        lines.add(new Line(pointList).setColor(ContextCompat.getColor(getActivity(), R.color.textColorPrimary)));

        LineChartData data = new LineChartData();
        data.setLines(lines);

        data.setAxisYLeft(new Axis().setTextColor(ContextCompat.getColor(getActivity(), R.color.textColorPrimary)));

        mLineChartView.setLineChartData(data);
        mLineChartView.startDataAnimation();
    }


    private String getSymbol() {
       return getArguments().getString(EXTRA_SYMBOL);
    }

    private Realm getDefaultRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getActivity()).build();
        return Realm.getInstance(configuration);
    }

    private StockData getStockData() {
        return getDefaultRealm().where(StockData.class).equalTo("symbol",getSymbol()).findFirst();
    }
}
