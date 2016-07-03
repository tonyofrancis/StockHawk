package com.sam_chordas.android.stockhawk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.model.StockPrice;
import com.sam_chordas.android.stockhawk.viewholder.StockPriceViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyofrancis on 6/19/16.
 */

public final class StockPriceAdapter extends RecyclerView.Adapter<StockPriceViewHolder> {

    private List<StockPrice> mDataSet;

    public StockPriceAdapter() {
        super();
        mDataSet = new ArrayList<>();
    }

    @Override
    public StockPriceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StockPriceViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_stock_price_item,parent,false));
    }

    @Override
    public void onBindViewHolder(StockPriceViewHolder holder, int position) {
        holder.bindStockPrice(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void swapDataSet(List<StockPrice> dataSet) {

        if (dataSet == null) {
            mDataSet = new ArrayList<>();
        } else {
            mDataSet = dataSet;
        }

        notifyDataSetChanged();
    }
}
