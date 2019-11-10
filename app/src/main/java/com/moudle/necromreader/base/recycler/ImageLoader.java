package com.moudle.necromreader.base.recycler;

import android.widget.ImageView;

import com.moudle.necromreader.R;
import com.squareup.picasso.Picasso;

/**
 * Created by moudle on 2017/10/28 0028.
 */

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
                .into(iv);
    }
}
