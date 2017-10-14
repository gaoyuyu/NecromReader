package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.greendao.entity.GankTag;
import com.gaoyy.necromreader.util.CommonUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by gaoyy on 2017/10/14 0014.
 */

public class TagListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<GankTag> data;

    private OnItemClickListener onItemClickListener;

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        Log.d(Constant.TAG, "onItemMove-swap前->" + data.toString());
        //交换位置
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        Log.d(Constant.TAG, "fromPosition-->" + fromPosition);
        Log.d(Constant.TAG, "toPosition-->" + toPosition);
        Log.d(Constant.TAG, "onItemMove-swap后->" + data.toString());

    }

    @Override
    public void onItemDissmiss(int position)
    {

    }


    public interface OnItemClickListener
    {
        void onItemLongClick(View view, int position);
        void onItemClick(View view, int position);
    }

    public void setOnItemLongClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }

    public TagListAdapter(Context context, List<GankTag> data)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_popup, parent, false);
        TagViewHolder vh = new TagViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        TagViewHolder tagViewHolder = (TagViewHolder) holder;
        GankTag gankTag = data.get(position);
        //设置删除的tag为sort字段值
        tagViewHolder.itemPopupDelete.setTag(gankTag.getSort());
        tagViewHolder.itemPopupTv.setText(CommonUtils.getTypeName(gankTag.getTagId()));
        if (gankTag.isShowDelete())
        {
            tagViewHolder.itemPopupDelete.setVisibility(View.VISIBLE);
        }
        else
        {
            tagViewHolder.itemPopupDelete.setVisibility(View.GONE);
        }


        if (onItemClickListener != null)
        {
            tagViewHolder.itemPopupLayout.setOnLongClickListener(new BasicOnClickListener(tagViewHolder));
            tagViewHolder.itemPopupDelete.setOnClickListener(new BasicOnClickListener(tagViewHolder));
        }
    }

    private class BasicOnClickListener implements View.OnLongClickListener,View.OnClickListener
    {
        private TagViewHolder tagViewHolder;

        public BasicOnClickListener(TagViewHolder tagViewHolder)
        {
            this.tagViewHolder = tagViewHolder;
        }

        @Override
        public boolean onLongClick(View view)
        {
            switch (view.getId())
            {
                case R.id.item_popup_layout:
                    onItemClickListener.onItemLongClick(tagViewHolder.itemPopupLayout, tagViewHolder.getLayoutPosition());
                    break;
            }
            return false;
        }

        @Override
        public void onClick(View view)
        {
            switch (view.getId())
            {
                case R.id.item_popup_delete:
                    onItemClickListener.onItemClick(tagViewHolder.itemPopupDelete, tagViewHolder.getLayoutPosition());
                    break;
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    /**
     * 切换状态
     *
     * @param isShowDelete true-显示delete，false-不显示delete
     */
    public void toggleModel(boolean isShowDelete)
    {
        for (int i = 0; i < this.data.size(); i++)
        {
            this.data.get(i).setShowDelete(isShowDelete);
        }
        notifyDataSetChanged();
    }

    /**
     * 更新数据
     *
     * @param data
     */
    public void update(List<GankTag> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    public class TagViewHolder extends RecyclerView.ViewHolder implements TagTouchHelperViewHolder
    {
        private TextView itemPopupTv;
        private ImageView itemPopupDelete;
        private RelativeLayout itemPopupLayout;

        private void assignViews(View itemView)
        {
            itemPopupTv = (TextView) itemView.findViewById(R.id.item_popup_tv);
            itemPopupDelete = (ImageView) itemView.findViewById(R.id.item_popup_delete);
            itemPopupLayout = (RelativeLayout) itemView.findViewById(R.id.item_popup_layout);
        }

        public TagViewHolder(View itemView)
        {
            super(itemView);
            assignViews(itemView);
        }

        @Override
        public void onItemSelected()
        {
            itemPopupTv.setBackground(context.getResources().getDrawable(R.drawable.tag_btn_select));
        }

        @Override
        public void onItemNormal()
        {
            itemPopupTv.setBackground(context.getResources().getDrawable(R.drawable.tag_btn_normal));
        }
    }
}
