package com.gaoyy.necromreader.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.bean.TechInfo;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaoyy.necromreader.bigphoto.BigPhotoActivity;
import com.squareup.picasso.Picasso;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoyy on 2017/9/25 0025.
 */
public class TechListAdapter extends BaseQuickAdapter<TechInfo.ResultsBean,BaseViewHolder>
{

    public TechListAdapter(@Nullable List<TechInfo.ResultsBean> data)
    {
        super(R.layout.item_tech, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TechInfo.ResultsBean item)
    {
        helper.getView(R.id.item_tech_cardview).setTag(item);
        helper.setText(R.id.item_tech_title,item.getDesc());
        helper.setText(R.id.item_tech_author,item.getWho());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(item.getCreatedAt(), pos);
        String date = formatter.format(strToDate);

        helper.setText(R.id.item_tech_date,date);
        helper.setText(R.id.item_tech_source,item.getSource());

        final List<String> imgs = item.getImages();
        LinearLayout itemTechImgLayout = helper.getView(R.id.item_tech_img_layout);
        itemTechImgLayout.removeAllViews();
        if (imgs == null)
        {
            itemTechImgLayout.setVisibility(View.GONE);
        }
        else
        {
            itemTechImgLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < imgs.size(); i++)
            {
                ImageView imageView = new ImageView(mContext);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(mContext, BigPhotoActivity.class);
                        intent.putExtra("url", imgs.get(finalI));
                        intent.putExtra("name", "");
                        mContext.startActivity(intent);
                    }
                });
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));  //设置图片宽高
                itemTechImgLayout.addView(imageView); //动态添加图片
                Picasso.with(mContext)
                        .load(imgs.get(i))
                        .placeholder(R.mipmap.loading_bg)
                        .error(R.mipmap.error_bg)
                        .fit()
                        .into(imageView);
            }
        }
    }
}