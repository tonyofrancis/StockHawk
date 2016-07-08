package com.tonyostudios.stockwatchlist.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tonyostudios.stockwatchlist.R;
import com.tonyostudios.stockwatchlist.model.StockPrice;
import com.tonyostudios.stockwatchlist.util.StockUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tonyofrancis on 7/2/16.
 */
public final class StockPriceViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.stock_close_text_view)
    TextView mCloseTextView;
    @BindView(R.id.stock_open_text_view)
    TextView mOpenTextView;
    @BindView(R.id.stock_high_text_view)
    TextView mHighTextView;
    @BindView(R.id.stock_low_text_view)
    TextView mLowTextView;
    @BindView(R.id.stock_date_text_view)
    TextView mDateTextView;

    public StockPriceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindStockPrice(StockPrice stockPrice) {

        Context ctx = itemView.getContext();

        mDateTextView.setText(StockUtils.getDateAsString(stockPrice.getDate()));

        DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
        df.applyPattern("0.00");

        if(stockPrice.getClose() == -1) {
            mCloseTextView.setText(ctx.getString(R.string.stock_price_close,"-"));
        } else {
            mCloseTextView.setText(ctx.getString(R.string.stock_price_close,df.format(stockPrice.getClose())));
        }

        mOpenTextView.setText(ctx.getString(R.string.stock_price_open,df.format(stockPrice.getOpen())));
        mHighTextView.setText(ctx.getString(R.string.stock_price_high,df.format(stockPrice.getHigh())));
        mLowTextView.setText(ctx.getString(R.string.stock_price_low,df.format(stockPrice.getLow())));
    }
}