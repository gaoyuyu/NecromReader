package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }

    private int[] heights = {650, 700};

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
        PhotoViewHolder photoViewHolder = new PhotoViewHolder(rootView);
        return photoViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        PhotoViewHolder photoViewHolder = (PhotoViewHolder) holder;
        PhotoInfo.ResultsBean resultsBean = list.get(position);
        photoViewHolder.itemPhotoCardview.setTag(resultsBean);

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
                .into(photoViewHolder.itemPhotoImg);

        if (onItemClickListener != null)
        {
            photoViewHolder.itemPhotoCardview.setOnClickListener(new BasicOnClickListener(photoViewHolder));
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder
    {
        private CardView itemPhotoCardview;
        private ImageView itemPhotoImg;

        public PhotoViewHolder(View itemView)
        {
            super(itemView);
            itemPhotoCardview = (CardView) itemView.findViewById(R.id.item_photo_cardview);
            itemPhotoImg = (ImageView) itemView.findViewById(R.id.item_photo_img);
        }
    }

    public void update(List<PhotoInfo.ResultsBean> list)
    {
        this.list = list;
        notifyDataSetChanged();
    }

    private class BasicOnClickListener implements View.OnClickListener
    {
        private PhotoViewHolder photoViewHolder;

        public BasicOnClickListener(PhotoViewHolder photoViewHolder)
        {
            this.photoViewHolder = photoViewHolder;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_photo_cardview:
                    onItemClickListener.onItemClick(photoViewHolder.itemPhotoCardview, photoViewHolder.getLayoutPosition());
                    break;
            }
        }
    }
}
