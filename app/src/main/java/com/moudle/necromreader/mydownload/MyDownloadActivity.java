package com.moudle.necromreader.mydownload;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.moudle.necromreader.R;
import com.moudle.necromreader.adapter.DownloadListAdapter;
import com.moudle.necromreader.api.Constant;
import com.moudle.necromreader.base.BaseActivity;
import com.moudle.necromreader.util.CommonUtils;

import java.io.File;
import java.util.ArrayList;

public class MyDownloadActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
{
    private LinearLayout activityMyDownload;
    private Toolbar myDownloadToolbar;
    private RelativeLayout myDownloadEmptyLayout;
    private ImageView myDownloadEmptyImg;
    private TextView myDownloadEmptyTv;
    private SwipeRefreshLayout myDownloadSrLayout;
    private RecyclerView myDownloadRv;


    private DownloadListAdapter downloadListAdapter;
    private LinearLayoutManager manager;

    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_my_download);
    }


    @Override
    protected void initToolbar()
    {
        super.initToolbar(myDownloadToolbar, "我的下载", true, -1);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        activityMyDownload = (LinearLayout) findViewById(R.id.activity_my_download);
        myDownloadToolbar = (Toolbar) findViewById(R.id.my_download_toolbar);
        myDownloadEmptyLayout = (RelativeLayout) findViewById(R.id.my_download_empty_layout);
        myDownloadEmptyImg = (ImageView) findViewById(R.id.my_download_empty_img);
        myDownloadEmptyTv = (TextView) findViewById(R.id.my_download_empty_tv);
        myDownloadSrLayout = (SwipeRefreshLayout) findViewById(R.id.my_download_srLayout);
        myDownloadRv = (RecyclerView) findViewById(R.id.my_download_rv);
    }


    @Override
    protected void configViews(Bundle savedInstanceState)
    {
        super.configViews(savedInstanceState);

        ArrayList<String> data = listFiles();
        if (data.size() != 0)
        {
            myDownloadEmptyLayout.setVisibility(View.GONE);
            myDownloadSrLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            myDownloadEmptyLayout.setVisibility(View.VISIBLE);
            myDownloadSrLayout.setVisibility(View.GONE);
        }
        downloadListAdapter = new DownloadListAdapter(this, data);
        myDownloadRv.setAdapter(downloadListAdapter);

        //先实例化Callback
        ItemTouchHelper.Callback callback = new BasicItemTouchCallBack(downloadListAdapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(myDownloadRv);

        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myDownloadRv.setLayoutManager(manager);
        myDownloadRv.setItemAnimator(new DefaultItemAnimator());
        //设置刷新时动画的颜色
        myDownloadSrLayout = CommonUtils.setProgressBackgroundColor(this, myDownloadSrLayout);

    }

    @Override
    protected void setListener()
    {
        super.setListener();
        myDownloadSrLayout.setOnRefreshListener(this);
    }

    private ArrayList<String> listFiles()
    {
        ArrayList<String> fileList = new ArrayList<>();
        String imagePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + Constant.PIC_PATH;
        File f = new File(imagePath);
        File[] files = f.listFiles();// 列出所有文件
        if(files !=null&&files.length!=0)
        {
            for (File file : files)
            {
                if (file.isFile())
                {
                    fileList.add(file.getName());
                }
            }
        }
        return fileList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh()
    {
        ArrayList<String> data = listFiles();
        myDownloadSrLayout.setRefreshing(false);
        if (data.size() != 0)
        {
            myDownloadEmptyLayout.setVisibility(View.GONE);
            myDownloadSrLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            myDownloadEmptyLayout.setVisibility(View.VISIBLE);
            myDownloadSrLayout.setVisibility(View.GONE);
        }
        downloadListAdapter.updateData(data);
    }
}
