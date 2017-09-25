package com.gaoyy.necromreader.bigphoto;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.gaoyy.necromreader.R;
import com.gaoyy.necromreader.api.Constant;
import com.gaoyy.necromreader.base.BaseActivity;
import com.gaoyy.necromreader.util.CommonUtils;
import com.gaoyy.necromreader.view.WaveView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static com.gaoyy.necromreader.R.id.wave;

public class BigPhotoActivity extends BaseActivity implements BigPhotoContract.View, View.OnClickListener
{
    private BigPhotoContract.Presenter mBigPresenter;
    private static final String LOG_TAG = BigPhotoActivity.class.getSimpleName();
    private PhotoView bigImg;
    private Toolbar bigToolbar;
    private LinearLayout bigPhotoTool;
    private TextView bigPhotoDownload;
    private TextView bigPhotoDownloading;
    private TextView bigPhotoDownloadFinish;
    private TextView bigPhotoSettingfor;
    private RelativeLayout bigPhotoLayout;
    private WaveView waveView;
    private File imageFile;

    private DownloadManager downloadManager;
    private DownloadManager.Request request;

    private final int MSG_LOADING = 1;
    private final int MSG_FINISH = 2;


    private Timer timer;
    private long id;
    private TimerTask task;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case MSG_LOADING:
                    Bundle bundle = msg.getData();
                    int precent = bundle.getInt("precent");
                    Log.i(Constant.TAG, "handle precent-->" + precent);
                    int height = waveView.getWaveViewHeight();
                    int riseY = height * (precent / 100);
                    if (riseY >= height)
                    {
                        riseY += 100;
                    }
                    waveView.updateViewWithRiseY(riseY);
                    break;
                case MSG_FINISH:
                    ObjectAnimator waveAlpha = ObjectAnimator.ofFloat(waveView, "Alpha", 1.0f, 0.0f).setDuration(2000);
                    waveAlpha.start();
                    waveAlpha.addListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {
                            super.onAnimationStart(animation);
                            waveView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            bigPhotoDownload.setVisibility(View.GONE);
                            bigPhotoDownloading.setVisibility(View.GONE);
                            bigPhotoDownloadFinish.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
            }

        }
    };


    @Override
    protected void initContentView()
    {
        setContentView(R.layout.activity_big_photo);
    }

    @Override
    protected void assignViews()
    {
        super.assignViews();
        bigImg = (PhotoView) findViewById(R.id.big_img);
        bigToolbar = (Toolbar) findViewById(R.id.big_toolbar);
        bigPhotoTool = (LinearLayout) findViewById(R.id.big_photo_tool);
        bigPhotoDownload = (TextView) findViewById(R.id.big_photo_download);
        bigPhotoDownloading = (TextView) findViewById(R.id.big_photo_downloading);
        bigPhotoDownloadFinish = (TextView) findViewById(R.id.big_photo_download_finsh);
        bigPhotoSettingfor = (TextView) findViewById(R.id.big_photo_settingfor);
        bigPhotoLayout = (RelativeLayout) findViewById(R.id.big_photo_layout);
        waveView = (WaveView) findViewById(wave);
    }

    @Override
    protected void initToolbar()
    {
        super.initToolbar(bigToolbar, "", true, android.R.color.transparent);
    }

    @Override
    protected void configViews(Bundle savedInstanceState)
    {
        super.configViews(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        String name = getIntent().getStringExtra("name");


        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        request = new DownloadManager.Request(Uri.parse(url));

        //指定在WIFI状态下，执行下载操作。
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //指定在MOBILE状态下，执行下载操作
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);

        request.setMimeType("image/*");

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

        //设置文件存放路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS + Constant.PIC_PATH, name);

        String imagePath = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)).toString() + Constant.PIC_PATH;

        Log.i(Constant.TAG, "Downloads Path->" + imagePath);

        imageFile = new File(imagePath, name);

        Log.i(Constant.TAG, "exists ==>" + imageFile.exists());

        if (imageFile.exists())
        {
            //文件已下载，加载本地图片
            bigPhotoDownload.setVisibility(View.GONE);
            bigPhotoDownloading.setVisibility(View.GONE);
            bigPhotoDownloadFinish.setVisibility(View.VISIBLE);

            Picasso.with(this)
                    .load(imageFile)
                    .fit()
                    .into(bigImg);
        }
        else
        {
            //文件未下载，加载网络图片
            bigPhotoDownload.setVisibility(View.VISIBLE);
            bigPhotoDownloading.setVisibility(View.GONE);
            bigPhotoDownloadFinish.setVisibility(View.GONE);

            Picasso.with(this)
                    .load(url)
                    .fit()
                    .into(bigImg);
        }

        bigImg.enable();

        //设置WaveView不可见，alpha=0
        waveView.setAlpha(0f);

        setTimer();

    }

    /**
     * 设置计时器，取下载进度
     */
    private void setTimer()
    {
        final DownloadManager.Query query = new DownloadManager.Query();
        timer = new Timer();
        task = new TimerTask()
        {
            @Override
            public void run()
            {
                Message msg = new Message();
                Cursor cursor = downloadManager.query(query.setFilterById(id));
                if (cursor != null && cursor.moveToFirst())
                {
                    if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL)
                    {
                        task.cancel();
                        handler.sendEmptyMessage(MSG_FINISH);
                    }
                    //Notification 标题
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    //下载的文件到本地的目录
                    String imagePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    int downloadBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int totalBytes = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int precent = (downloadBytes * 100) / totalBytes;


                    Bundle bundle = new Bundle();
                    bundle.putInt("precent", precent);
                    msg.what = MSG_LOADING;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                cursor.close();
            }
        };
        timer.schedule(task, 0, 150);
    }


    @Override
    protected void setListener()
    {
        super.setListener();
        bigPhotoDownload.setOnClickListener(this);
        bigPhotoSettingfor.setOnClickListener(this);
    }

    @Override
    public boolean isActive()
    {
        return true;
    }

    @Override
    public void setPresenter(BigPhotoContract.Presenter presenter)
    {
        Log.i(LOG_TAG, "setPresenter");
        if (presenter != null)
        {
            mBigPresenter = presenter;
        }
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
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.big_photo_download:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    Log.e(Constant.TAG, "====申请权限=====");

                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        Log.e(Constant.TAG, "===REQUEST_WRITE_EXTERNAL_STORAGE again=====");
                        showRequestPermissionDialog();
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, Constant.REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                }
                else
                {
                    Log.e(Constant.TAG, "====文件存储权限已授权=====");
                    downloadImage();
                }
                break;
            case R.id.big_photo_settingfor:
                if (imageFile.exists())
                {
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                    try
                    {
                        wallpaperManager.setBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
                        CommonUtils.showSnackBar(bigPhotoTool, "设置成功");
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        CommonUtils.showSnackBar(bigPhotoTool, "设置失败");
                    }
                }
                else
                {
                    CommonUtils.showSnackBar(bigPhotoTool, "Please Download");
                }
                break;
        }
    }

    private void downloadImage()
    {
        ObjectAnimator waveAlpha = ObjectAnimator.ofFloat(waveView, "Alpha", waveView.getAlpha(), 1.0f).setDuration(2000);
        waveAlpha.start();
        waveAlpha.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                waveView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                id = downloadManager.enqueue(request);
                Log.i(Constant.TAG, "download id -->" + id);
                task.run();

            }
        });


        ObjectAnimator downloadAlpha = ObjectAnimator.ofFloat(bigPhotoDownload, "Alpha", 1.0f, 0.0f).setDuration(150);
        ObjectAnimator downloadingAlpha = ObjectAnimator.ofFloat(bigPhotoDownloading, "Alpha", 0.0f, 1.0f).setDuration(2000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(downloadAlpha, downloadingAlpha);
        animatorSet.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                bigPhotoDownload.setVisibility(View.VISIBLE);
                bigPhotoDownloading.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == Constant.REQUEST_WRITE_EXTERNAL_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.e(Constant.TAG, "====文件存储权限已授权=====");
                downloadImage();
            }
            else
            {
                CommonUtils.showToast(this, "已拒绝文件存储权限的申请，可在手机的设置界面中打开");
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showRequestPermissionDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("应用缺失文件存储权限")
                .setCancelable(false)
                .setPositiveButton("同意", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        ActivityCompat.requestPermissions(BigPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constant.REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //拒绝授权
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
