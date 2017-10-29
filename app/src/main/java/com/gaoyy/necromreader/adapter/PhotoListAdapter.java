package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.gaoyy.necromreader.base.recycler.BaseViewHolder;
import com.gaoyy.necromreader.base.recycler.RecyclerBaseAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gaoyy on 2017/3/19 0019.
 */
public class PhotoListAdapter extends RecyclerBaseAdapter<PhotoInfo.ResultsBean>
{

    public PhotoListAdapter(Context context, List<PhotoInfo.ResultsBean> data)
    {
        super(context, R.layout.item_photo, data);
    }

    @Override
    protected void bindData(BaseViewHolder holder, PhotoInfo.ResultsBean itemData, int position)
    {
        holder.getView(R.id.item_photo_cardview).setTag(itemData);

        holder.setImagePath(R.id.item_photo_img, new ImageLoader(itemData.getUrl()));

        if (onItemClickListener != null)
        {
            holder.getView(R.id.item_photo_cardview).setOnClickListener(new BasicOnClickListener(holder));
        }
    }

    public class ImageLoader extends BaseViewHolder.ImageLoaderManager
    {
        public ImageLoader(String path)
        {
            super(path);
        }

        @Override
        public void loadImage(ImageView iv, String path)
        {
            //加载网络图片
            Picasso.with(iv.getContext())
                    .load(path)
                    .placeholder(R.mipmap.loading_bg)
                    .error(R.mipmap.error_bg)
                    .fit()
                    .into(iv);
        }
    }

    private class BasicOnClickListener implements View.OnClickListener
    {
        private BaseViewHolder vh;

        public BasicOnClickListener(BaseViewHolder vh)
        {
            this.vh = vh;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_photo_cardview:
                    onItemClickListener.onItemClick(vh.getView(R.id.item_photo_cardview), vh.getLayoutPosition());
                    break;
            }
        }
    }

}