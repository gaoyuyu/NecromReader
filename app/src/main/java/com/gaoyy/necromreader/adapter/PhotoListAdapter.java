package com.gaoyy.necromreader.adapter;

import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.bean.PhotoInfo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

/**
 * Created by gaoyy on 2017/3/19 0019.
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

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) iv.getLayoutParams();

        //设置随机数
        Random random = new Random();
        //将高度修改为传入的随机高度
        params.height = random.nextInt(200) + 200;

        //设置修改参数
        iv.setLayoutParams(params);

        Picasso.with(mContext)
                .load(item.getUrl())
                .placeholder(R.mipmap.loading_bg)
                .error(R.mipmap.error_bg)
                .into(iv);

    }
}