package com.gaoyy.necromreader.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.bean.NewsInfo;
import com.gaoyy.necromreader.base.recycler.BaseViewHolder;
import com.gaoyy.necromreader.base.recycler.ImageLoader;
import com.gaoyy.necromreader.base.recycler.RecyclerBaseAdapter;

import java.util.List;

/**
 * Created by gaoyy on 2016/8/24 0024.
 */
public class NewsListAdapter extends RecyclerBaseAdapter<NewsInfo.ResultBean.DataBean>
{
    public NewsListAdapter(Context context, List<NewsInfo.ResultBean.DataBean> data)
    {
        super(context, R.layout.item_news, data);
    }

    @Override
    protected void bindData(BaseViewHolder holder, NewsInfo.ResultBean.DataBean itemData, int position)
    {
        holder.setText(R.id.item_news_title, itemData.getTitle())
                .setText(R.id.item_news_date, itemData.getAuthor_name() + "        " + itemData.getDate());
        RelativeLayout layout = holder.getView(R.id.item_news_layout);
        layout.setTag(itemData);
        setUpAnim(layout);
        holder.setImagePath(R.id.item_news_img, new ImageLoader(itemData.getThumbnail_pic_s()));
        if (onItemClickListener != null)
        {
            layout.setOnClickListener(new BasicOnClickListener(holder));
        }
    }

    /**
     * 设置动画效果
     *
     * @param layout
     */
    private void setUpAnim(RelativeLayout layout)
    {
        int screenWidth = ((AppCompatActivity) layout.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        layout.setTranslationX(screenWidth / 2);
        ObjectAnimator xAnim = ObjectAnimator.ofFloat(layout, "TranslationX", screenWidth / 2, 0).
                setDuration(500);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(layout, "Alpha", 0.5f, 1.0f).
                setDuration(500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(xAnim, alphaAnim);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }

    private class BasicOnClickListener implements View.OnClickListener
    {
        private BaseViewHolder baseViewHolder;

        public BasicOnClickListener(BaseViewHolder baseViewHolder)
        {
            this.baseViewHolder = baseViewHolder;
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.item_news_layout:
                    onItemClickListener.onItemClick(baseViewHolder.getView(R.id.item_news_layout), baseViewHolder.getLayoutPosition());
                    break;
            }
        }
    }

}
