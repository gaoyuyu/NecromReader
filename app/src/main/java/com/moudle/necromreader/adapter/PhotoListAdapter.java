package com.moudle.necromreader.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.moudle.necromreader.R;
import com.moudle.necromreader.api.bean.PhotoInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by moudle on 2017/3/19 0019.
 */
public class PhotoListAdapter extends BaseQuickAdapter<PhotoInfo.ResultsBean, BaseViewHolder>
{

    public PhotoListAdapter(@Nullable List<PhotoInfo.ResultsBean> data)
    {
        super(R.layout.item_photo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoInfo.ResultsBean item)
    {
        helper.getView(R.id.item_photo_cardview).setTag(item);
        ImageView iv = helper.getView(R.id.item_photo_img);

        Picasso.with(mContext)
                .load(item.getUrl())
                .placeholder(R.mipmap.loading_bg)
                .error(R.mipmap.error_bg)
                .into(iv);

    }
}