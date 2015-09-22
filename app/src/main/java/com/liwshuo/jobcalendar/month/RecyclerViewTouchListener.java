package com.liwshuo.jobcalendar.month;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liwshuo on 2015/9/19.
 */
public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener clickListener;
    private GestureDetector gestureDetector;

    public RecyclerViewTouchListener(Context context, final RecyclerView recyclerView, final OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent event) {

                return true;
            }

            @Override
            public void onLongPress(MotionEvent event) {
                View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
                long itemId = recyclerView.getChildItemId(child);
                int position = recyclerView.getChildAdapterPosition(child);
                RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(viewHolder, position, itemId);
                }
            }
        });
    }
    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent event) {
        View child = recyclerView.findChildViewUnder(event.getX(), event.getY());
        long itemId = recyclerView.getChildItemId(child);
        int position = recyclerView.getChildAdapterPosition(child);
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
        if (child != null && clickListener != null) {
            clickListener.onClick(viewHolder, position, itemId);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    static interface OnItemClickListener {
        public void onClick(RecyclerView.ViewHolder viewHolder, int position, long itemId);

        public void onLongClick(RecyclerView.ViewHolder viewHolder, int position, long itemId);
    }
}
