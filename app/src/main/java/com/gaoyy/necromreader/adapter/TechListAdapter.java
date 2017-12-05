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

/*
public class TechListAdapter extends RecyclerBaseAdapter<TechInfo.ResultsBean>
{

    public TechListAdapter(Context context, List<TechInfo.ResultsBean> data)
    {
        super(context, R.layout.item_tech, data);
    }

    @Override
    protected void bindData(BaseViewHolder holder, TechInfo.ResultsBean itemData, int position)
    {
        holder.getView(R.id.item_tech_cardview).setTag(itemData);
        holder.setText(R.id.item_tech_title,itemData.getDesc());
        holder.setText(R.id.item_tech_author,itemData.getWho());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(itemData.getCreatedAt(), pos);
        String date = formatter.format(strToDate);

        holder.setText(R.id.item_tech_date,date);
        holder.setText(R.id.item_tech_source,itemData.getSource());

        final List<String> imgs = itemData.getImages();
        LinearLayout itemTechImgLayout = holder.getView(R.id.item_tech_img_layout);
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

        if (onItemClickListener != null)
        {
            holder.getView(R.id.item_tech_cardview).setOnClickListener(new BasicOnClickListener(holder));
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
                case R.id.item_tech_cardview:
                    onItemClickListener.onItemClick(vh.getView(R.id.item_tech_cardview), vh.getLayoutPosition());
                    break;
            }
        }
    }
}
*/
/*
public class TechListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Context context;
    private LayoutInflater inflater;
    private List<TechInfo.ResultsBean> data;

    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.onItemClickListener = listener;
    }

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


        techViewHolder.itemTechCardview.setTag(tech);
        techViewHolder.itemTechTitle.setText(tech.getDesc());
        techViewHolder.itemTechAuthor.setText(tech.getWho());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strToDate = formatter.parse(tech.getCreatedAt(), pos);
        String date = formatter.format(strToDate);
        techViewHolder.itemTechDate.setText(date);
        techViewHolder.itemTechSource.setText(tech.getSource());

        final List<String> imgs = tech.getImages();
        techViewHolder.itemTechImgLayout.removeAllViews();
        if (imgs == null)
        {
            techViewHolder.itemTechImgLayout.setVisibility(View.GONE);
        }
        else
        {
            techViewHolder.itemTechImgLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < imgs.size(); i++)
            {

                ImageView imageView = new ImageView(context);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(context, BigPhotoActivity.class);
                        intent.putExtra("url", imgs.get(finalI));
                        intent.putExtra("name", "");
                        context.startActivity(intent);
                    }
                });
                imageView.setLayoutParams(new LinearLayout.LayoutParams(300, 300));  //设置图片宽高
                techViewHolder.itemTechImgLayout.addView(imageView); //动态添加图片
                Picasso.with(context)
                        .load(imgs.get(i))
                        .placeholder(R.mipmap.loading_bg)
                        .error(R.mipmap.error_bg)
                        .fit()
                        .into(imageView);
            }
        }

        if (onItemClickListener != null)
        {
            techViewHolder.itemTechCardview.setOnClickListener(new BasicOnClickListener(techViewHolder));
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

    private class BasicOnClickListener implements View.OnClickListener
    {
        private TechViewHolder techViewHolder;

        public BasicOnClickListener(TechViewHolder techViewHolder)
        {
            this.techViewHolder = techViewHolder;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_tech_cardview:
                    onItemClickListener.onItemClick(techViewHolder.itemTechCardview, techViewHolder.getLayoutPosition());
                    break;
            }
        }
    }

}
*/
