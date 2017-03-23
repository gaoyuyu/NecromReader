package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */

public class PhotoListAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<PhotoInfo.ResultsBean> list;

    private int[] heights ={650,700} ;

    public PhotoListAdater(Context context, List<PhotoInfo.ResultsBean> list)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_photo, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(rootView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {



        PhotoInfo.ResultsBean resultsBean = list.get(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.tv.setText(resultsBean.get_id());

//        ViewGroup.LayoutParams lp = itemViewHolder.img.getLayoutParams();
//        if(position%3 > 1&&position%3 < 3)
//        {
//            lp.height = heights[0];
//        }
//        else if(position%3 > 3)
//        {
//            lp.height = heights[1];
//        }
//        itemViewHolder.img.setLayoutParams(lp);


        //Picasso加载图片
        Picasso.with(context)
                .load(resultsBean.getUrl())
                .placeholder(R.mipmap.loading_bg)
                .error(R.mipmap.error_bg)
                .fit()
                .into(itemViewHolder.img);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tv;
        private ImageView img;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
            img = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public void update(List<PhotoInfo.ResultsBean> list)
    {
        this.list = list;
        notifyDataSetChanged();

    }
}
