package com.moudle.necromreader.gankio;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.moudle.necromreader.adapter.ItemTouchHelperAdapter;
import com.moudle.necromreader.base.recycler.TagTouchHelperViewHolder;

/**
 * Created by moudle on 2017/10/9 0009.
 */

public class TagItemTouchCallBack extends ItemTouchHelper.Callback
{
    private ItemTouchHelperAdapter mAdapter;

    public TagItemTouchCallBack(ItemTouchHelperAdapter adapter)
    {
        mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager)
        {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        else
        {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
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
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target)
    {
        if (source.getItemViewType() != target.getItemViewType())
        {
            return false;
        }
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    /**
     * 选中状态更改背景
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
        {
            if (viewHolder instanceof TagTouchHelperViewHolder)
            {
                TagTouchHelperViewHolder itemViewHolder = (TagTouchHelperViewHolder) viewHolder;
                //背景更改
                itemViewHolder.onItemSelected();
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * isCurrentlyActive 为 false时，表示拖动结束，背景更改为正常
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        if (!isCurrentlyActive)
        {
            if (viewHolder instanceof TagTouchHelperViewHolder)
            {
                TagTouchHelperViewHolder itemViewHolder = (TagTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemNormal();
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        mAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }
}
