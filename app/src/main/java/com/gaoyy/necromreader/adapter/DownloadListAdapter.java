package com.gaoyy.necromreader.adapter;

import android.content.Context;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by gaoyy on 2017/9/25 0025.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter
{
    private Context context;
    private LayoutInflater inflater;
    private List<String> data;
    private String imagePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + Constant.PIC_PATH;

    public DownloadListAdapter(Context context, List<String> data)
    {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = inflater.inflate(R.layout.item_download, parent, false);
        DownloadViewHolder vh = new DownloadViewHolder(rootView);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        DownloadViewHolder downloadViewHolder = (DownloadViewHolder) holder;
        String fileName = data.get(position);

        Picasso.with(context)
                .load(new File(imagePath, fileName))
                .placeholder(R.mipmap.loading_bg)
                .error(R.mipmap.error_bg)
                .fit()
                .into(downloadViewHolder.itemDownloadImg);

        downloadViewHolder.itemDownloadFileName.setText(fileName);

    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public void update(List<String> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        //交换位置
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDissmiss(int position)
    {
        File file = new File(imagePath, data.get(position));
        if (file.isFile() && file.exists())
        {
            boolean isSuccess = file.delete();
            if(isSuccess)
            {
                CommonUtils.showToast(context,"删除成功");
                //移除数据
                data.remove(position);
                notifyItemRemoved(position);
            }
            else
            {
                CommonUtils.showToast(context,"删除成功");
            }
        }

    }


    public static class DownloadViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView itemDownloadImg;
        public TextView itemDownloadFileName;
        public View itemDownloadDivider;
        public ImageView itemDownloadSlideImg;
        public TextView itemDownloadSlideText;

        public DownloadViewHolder(View itemView)
        {
            super(itemView);
            itemDownloadImg = (ImageView) itemView.findViewById(R.id.item_download_img);
            itemDownloadFileName = (TextView) itemView.findViewById(R.id.item_download_file_name);
            itemDownloadDivider = itemView.findViewById(R.id.item_download_divider);
            itemDownloadSlideImg = (ImageView) itemView.findViewById(R.id.item_download_slide_img);
            itemDownloadSlideText = (TextView) itemView.findViewById(R.id.item_download_slide_text);
        }
    }


}

