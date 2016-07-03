package com.sam_chordas.android.stockhawk.viewholder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sam_chordas.android.stockhawk.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public final class NoItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.empty_text_view)
    TextView mEmptyTextView;

    public NoItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(int stringId) {
        mEmptyTextView.setText(itemView.getContext().getString(stringId));
    }
}