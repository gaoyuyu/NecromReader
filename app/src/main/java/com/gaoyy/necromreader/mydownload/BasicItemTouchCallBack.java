package com.gaoyy.necromreader.mydownload;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoyy.necromreader.adapter.DownloadListAdapter;
import com.gaoyy.necromreader.adapter.ItemTouchHelperAdapter;

/**
 * Created by gaoyy on 2017/10/9 0009.
 */

public class BasicItemTouchCallBack extends ItemTouchHelper.Callback
{
    public static final float ALPHA_FULL = 1.0f;
    private ItemTouchHelperAdapter mAdapter;

    private float scale = 0.4f;
    private float maxScale = 0.8f;

    public BasicItemTouchCallBack(ItemTouchHelperAdapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean isLongPressDragEnabled()
    {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled()
    {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        ImageView iv = ((DownloadListAdapter.DownloadViewHolder) viewHolder).itemDownloadSlideImg;
        TextView tv = ((DownloadListAdapter.DownloadViewHolder) viewHolder).itemDownloadSlideText;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
        {
            if (Math.abs(dX) <= getSlideLimitation(viewHolder))
            {
                viewHolder.itemView.scrollTo(-(int) dX, 0);
            }
            else if (Math.abs(dX) <= recyclerView.getWidth() / 2)
            {
                double k = (Math.abs(dX) - getSlideLimitation(viewHolder)) / (recyclerView.getWidth() / 2 - Math.abs(dX));
                float s = (float) (scale + k);
                if (s >= maxScale) s = 0.8f;
                iv.setVisibility(View.VISIBLE);
                tv.setText("");
                iv.setScaleX(s);
                iv.setScaleY(s);
            }
        }
        else
        {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        super.clearView(recyclerView, viewHolder);
        ImageView iv = ((DownloadListAdapter.DownloadViewHolder) viewHolder).itemDownloadSlideImg;
        TextView tv = ((DownloadListAdapter.DownloadViewHolder) viewHolder).itemDownloadSlideText;
        //重置改变，防止由于复用而导致的显示问题
        viewHolder.itemView.setScrollX(0);
        tv.setText("左滑删除");
        iv.setScaleX(scale);
        iv.setScaleY(scale);
        iv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }

    /**
     * 获取删除方块的宽度
     */
    private int getSlideLimitation(RecyclerView.ViewHolder viewHolder)
    {
        ViewGroup viewGroup = (ViewGroup) viewHolder.itemView;
        return viewGroup.getChildAt(1).getLayoutParams().width;
    }
}
