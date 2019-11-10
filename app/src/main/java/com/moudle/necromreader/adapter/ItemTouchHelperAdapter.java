package com.moudle.necromreader.adapter;

/**
 * Created by moudle on 2017/10/9 0009.
 */

public interface ItemTouchHelperAdapter
{
    //数据交换
    void onItemMove(int fromPosition, int toPosition);

    //数据删除
    void onItemDissmiss(int position);
}
