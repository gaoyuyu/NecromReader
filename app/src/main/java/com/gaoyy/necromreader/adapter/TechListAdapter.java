package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.api.bean.TechInfo;
import com.squareup.picasso.Picasso;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoyy on 2017/9/25 0025.
 */

public class TechListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<TechInfo.ResultsBean> data;

    public TechListAdapter(Context context, List<TechInfo.ResultsBean> data)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_tech, parent, false);
        TechViewHolder vh = new TechViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        TechViewHolder techViewHolder = (TechViewHolder) holder;
        TechInfo.ResultsBean tech = data.get(position);
        techViewHolder.itemTechTitle.setText(tech.getDesc());
        techViewHolder.itemTechAuthor.setText(tech.getWho());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(tech.getCreatedAt(), pos);
        String date = formatter.format(strToDate);
        techViewHolder.itemTechDate.setText(date);
        techViewHolder.itemTechSource.setText(tech.getSource());

        List<String> imgs = tech.getImages();
        techViewHolder.itemTechImgLayout.removeAllViews();
        if (imgs == null)
        {
            techViewHolder.itemTechImgLayout.setVisibility(View.GONE);
        }
        else
        {
            techViewHolder.itemTechImgLayout.setVisibility(View.VISIBLE);
            Log.d(Constant.TAG,position+"==imgs==>"+imgs.size());
            for (int i = 0; i < imgs.size(); i++)
            {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));  //设置图片宽高
                techViewHolder.itemTechImgLayout.addView(imageView); //动态添加图片
                Picasso.with(context)
                        .load(imgs.get(i))
                        .fit()
                        .into(imageView);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public void update(List<TechInfo.ResultsBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }


    public static class TechViewHolder extends RecyclerView.ViewHolder
    {

        private CardView itemTechCardview;
        private TextView itemTechTitle;
        private LinearLayout itemTechImgLayout;
        private TextView itemTechAuthor;
        private TextView itemTechSource;
        private TextView itemTechDate;

        private void assignViews(View itemView)
        {
            itemTechCardview = (CardView) itemView.findViewById(R.id.item_tech_cardview);
            itemTechTitle = (TextView) itemView.findViewById(R.id.item_tech_title);
            itemTechImgLayout = (LinearLayout) itemView.findViewById(R.id.item_tech_img_layout);
            itemTechAuthor = (TextView) itemView.findViewById(R.id.item_tech_author);
            itemTechSource = (TextView) itemView.findViewById(R.id.item_tech_source);
            itemTechDate = (TextView) itemView.findViewById(R.id.item_tech_date);
        }


        public TechViewHolder(View itemView)
        {
            super(itemView);
            assignViews(itemView);
        }
    }


}

