package com.sam_chordas.android.stockhawk.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.adapter.StockDataOnClickListener;
import com.sam_chordas.android.stockhawk.model.StockData;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyofrancis on 7/2/16.
 */
public final class StockViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stock_symbol)TextView mStockSymbolTextView;
    @BindView(R.id.bid_price)
    TextView mBidPriceTextView;
    @BindView(R.id.change)
    TextView mChangeTextView;

    public StockViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindStockData(final StockData stockData, boolean showPercentChange, final StockDataOnClickListener listener) {

        Context ctx = itemView.getContext();
        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("0.00");


        if(!stockData.isUp()) {
            mChangeTextView.setBackgroundResource(R.drawable.percent_change_pill_red);
        } else {
            mChangeTextView.setBackgroundResource(R.drawable.percent_change_pill_green);
        }

        int changStringResId;
        String change;

        if(showPercentChange) {

            change = df.format(stockData.getPercentChange());

            if(!stockData.isUp()) {
                changStringResId = R.string.stock_percent_change_decrease;
            } else {
                changStringResId = R.string.stock_percent_change;
            }

        } else {

            change = df.format(stockData.getChange());

            if(!stockData.isUp()) {
                changStringResId = R.string.stock_change_decrease;
            }else {
                changStringResId = R.string.stock_change;
            }
        }



        mChangeTextView.setText(ctx.getString(changStringResId,change));
        mStockSymbolTextView.setText(stockData.getSymbol());
        mBidPriceTextView.setText(df.format(stockData.getBidPrice()));

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(stockData);
            }
        });
    }
}