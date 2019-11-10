package com.moudle.necromreader.adapter;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;

import com.moudle.necromreader.R;
import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.base.recycler.BaseViewHolder;
import com.moudle.necromreader.base.recycler.RecyclerBaseAdapter;
import com.moudle.necromreader.util.CommonUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by moudle on 2017/9/25 0025.
 */

public class DownloadListAdapter extends RecyclerBaseAdapter<String>  implements ItemTouchHelperAdapter
{
    private String imagePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + Constant.PIC_PATH;

    public DownloadListAdapter(Context context, List<String> data)
    {
        super(context, R.layout.item_download, data);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition)
    {
        //交换位置
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDissmiss(int position)
    {
        File file = new File(imagePath, mData.get(position));
        if (file.isFile() && file.exists())
        {
            boolean isSuccess = file.delete();
            if(isSuccess)
            {
                CommonUtils.showToast(mContext,"删除成功");
                //移除数据
                mData.remove(position);
                notifyItemRemoved(position);
            }
            else
            {
                CommonUtils.showToast(mContext,"删除成功");
            }
        }

    }

    @Override
    protected void bindData(BaseViewHolder holder, String itemData, int position)
    {
        String fileName = itemData;
        holder.setText(R.id.item_download_file_name, fileName);
        holder.setImagePath(R.id.item_download_img, new ImageLoader(fileName));
    }

    public class ImageLoader extends BaseViewHolder.ImageLoaderManager
    {
        public ImageLoader(String fileName)
        {
            super(fileName);
        }

        @Override
        public void loadImage(ImageView iv, String fileName)
        {
            //加载网络图片
            Picasso.with(iv.getContext())
                    .load(new File(imagePath, fileName))
                    .placeholder(R.mipmap.loading_bg)
                    .error(R.mipmap.error_bg)
                    .fit()
                    .into(iv);
        }
    }
}
