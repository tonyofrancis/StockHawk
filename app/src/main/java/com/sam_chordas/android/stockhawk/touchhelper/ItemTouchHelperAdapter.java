package com.sam_chordas.android.stockhawk.touchhelper;

/**
 * Created by tonyofrancis on 6/30/16.
 */
public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
