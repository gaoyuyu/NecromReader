package com.moudle.necromreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moudle.necromreader.main.MainActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

public class SplashActivity extends Activity
{

    /**
     * 我们自己后台的
     */
    final String OWNER_SWITCH_URL = "http://tddiosad.hihere.com.cn/web/user/getAppMsg/";

    /**
     * 应用的appid
     */
    final String APP_ID = "fc1150778efb4808a469bd040a97f65a";

    private SplashLayout splashLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashLayout = (SplashLayout) findViewById(R.id.splash_layout);
        splashLayout.attach(this).playAnim();
    }

    public void loadData() {
        OkGo.<String>get(OWNER_SWITCH_URL + APP_ID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.e("tag", response.body());
                        JSONObject o = JSON.parseObject(response.body());
                        int status = o.getIntValue("status");
                        if (status == 0) {
                            JSONObject o1 = o.getJSONObject("result");
                            String vs = o1.getString("vs");
                            String url = o1.getString("url");
                            String ud = o1.getString("ud");
                            if (vs.equals("4")) {
                                Intent intent = new Intent(SplashActivity.this, MyWebViewActivity.class);
                                intent.putExtra("data_one", url);
                                startActivity(intent);
                            } else if (vs.equals("5")) {
                                Intent intent = new Intent(SplashActivity.this, DownApkActivity.class);
                                intent.putExtra("data_one", ud);
                                startActivity(intent);
                            }
                            else{
                                goNormal();
                            }
                        } else {
                            goNormal();
                        }
                        finish();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        goNormal();
                        finish();
                    }
                });
    }

    public void goNormal() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
