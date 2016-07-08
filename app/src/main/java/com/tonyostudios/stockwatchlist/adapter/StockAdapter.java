package com.tonyostudios.stockwatchlist.adapter;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tonyostudios.stockwatchlist.R;
import com.tonyostudios.stockwatchlist.model.StockData;
import com.tonyostudios.stockwatchlist.viewholder.NoItemViewHolder;
import com.tonyostudios.stockwatchlist.viewholder.StockViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonyofrancis on 6/19/16.
 */

public final class StockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StockData> mDataSet;
    private final StockDataOnClickListener mClickListener;
    private boolean isEmpty;
    private boolean isShowPercentChange;
    private int mEmptyStringId;


    public StockAdapter(StockDataOnClickListener listener) {
        this(listener, R.string.no_stock_item);
    }

    public StockAdapter(StockDataOnClickListener listener,int emptyStringId) {
        super();
        mDataSet = new ArrayList<>();
        mClickListener = listener;
        isEmpty = true;
        isShowPercentChange = true;
        mEmptyStringId = emptyStringId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == R.layout.list_item_no_quote) {
            return new NoItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_no_quote,parent,false));
        }

        return new StockViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_quote,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(!isEmpty) {
            ((StockViewHolder)holder).bindStockData(mDataSet.get(position),isShowPercentChange,mClickListener);
        } else {
            ((NoItemViewHolder)holder).bind(mEmptyStringId);
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);

        if(mDataSet.size() == 0) {
            return R.layout.list_item_no_quote;
        }

        return R.layout.list_item_quote;
    }

    public void setEmptyStringId(@StringRes int stringId) {
        mEmptyStringId = stringId;
    }

    @Override
    public int getItemCount() {

        if(isEmpty) {
            return 1;
        }

        return mDataSet.size();
    }

    public void swapDataSet(List<StockData> dataSet) {

        if (dataSet == null) {
            mDataSet = new ArrayList<>();
            isEmpty = true;
        } else {
            mDataSet = dataSet;
            isEmpty = false;
        }

        notifyDataSetChanged();
    }

    public void switchStockChangeView() {

        if(!isEmpty) {

            isShowPercentChange = !isShowPercentChange;
            notifyDataSetChanged();
        }
    }

    public StockData getItem(int position) {

        if(!isEmpty) {
            return mDataSet.get(position);
        }

        return null;
    }

    public void add(StockData stockData) {

        mDataSet.add(stockData);
        isEmpty = false;

        notifyItemRangeInserted(mDataSet.size() - 1, 1);
    }
}
