package com.sam_chordas.android.stockhawk.touchhelper;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.sam_chordas.android.stockhawk.viewholder.NoItemViewHolder;

/**
 * Created by tonyofrancis on 6/30/16.
 */
public final class ItemTouchHelperSimpleCallback extends ItemTouchHelper.SimpleCallback {

    private final ItemTouchHelperAdapter mAdapter;

    public ItemTouchHelperSimpleCallback(int dragDirs, int swipeDirs, @NonNull ItemTouchHelperAdapter adapter) {
        super(dragDirs, swipeDirs);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        mAdapter.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if (direction == ItemTouchHelper.LEFT) {
            mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (viewHolder instanceof NoItemViewHolder) {
            return 0;
        }

        return super.getSwipeDirs(recyclerView, viewHolder);
    }


}