package com.dl.common.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * created by dalang at 2018/11/16
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener{
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(),
                new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder);
    public abstract void onOtherClick(MotionEvent e);

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onItemClick(childViewHolder);
            } else {
                onOtherClick(e);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(childViewUnder);
                onLongClick(childViewHolder);
            }
        }
    }
}
