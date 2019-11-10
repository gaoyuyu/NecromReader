package com.moudle.necromreader.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.moudle.necromreader.R;

/**
 * Created by moudle on 2017/7/23 0023.
 */

public class CommonUtils
{

    /**
     * 设置SwipeRefreshLayout的刷新时动画的颜色
     *
     * @param context
     * @param layout
     * @return
     */
    public static SwipeRefreshLayout setProgressBackgroundColor(Context context, SwipeRefreshLayout layout)
    {
        layout.setProgressBackgroundColorSchemeResource(android.R.color.white);
        layout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        layout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources()
                        .getDisplayMetrics()));

        return layout;
    }


    /**
     * showToast
     *
     * @param context
     * @param msg
     */
    public static void showToast(Context context, String msg)
    {
        if (null != msg && !CommonUtils.isEmpty(msg))
        {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * showToast
     *
     * @param context
     * @param msgId
     */
    public static void showToast(Context context, int msgId)
    {
        Toast.makeText(context, msgId, Toast.LENGTH_SHORT).show();
    }

    /**
     * showSnackBar
     *
     * @param view
     * @param msg
     */
    public static void showSnackBar(View view, String msg)
    {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static boolean isEmpty(String str)
    {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals(""))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    public static String getTypeName(int type)
    {
        String typeName = "";
        switch (type)
        {
            case R.string.top:
                typeName = "头条";
                break;
            case R.string.shehui:
                typeName = "社会";
                break;
            case R.string.guonei:
                typeName = "国内";
                break;
            case R.string.guoji:
                typeName = "国际";
                break;
            case R.string.yule:
                typeName = "娱乐";
                break;
            case R.string.tiyu:
                typeName = "体育";
                break;
            case R.string.junshi:
                typeName = "军事";
                break;
            case R.string.keji:
                typeName = "科技";
                break;
            case R.string.caijing:
                typeName = "财经";
                break;
            case R.string.shishang:
                typeName = "时尚";
                break;
            case R.string.photo:
                typeName="福利";
                break;
            case R.string.android:
                typeName="Android";
                break;
            case R.string.ios:
                typeName="iOS";
                break;
            case R.string.front_web:
                typeName="前端";
                break;
        }
        return typeName;
    }
}
