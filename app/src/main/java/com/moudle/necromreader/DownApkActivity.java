package com.moudle.necromreader;

import android.app.Activity;
import android.os.Bundle;

import com.moudle.necromreader.downapk.InstallUtils;


public class DownApkActivity extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_apk);

        String apkurl=getIntent().getStringExtra("data_one");
        InstallUtils.updateApk(this,apkurl);
    }
}
