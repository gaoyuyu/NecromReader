package com.gaoyy.necromreader.util;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

/**
 * Created by gaoyy on 2017/7/23 0023.
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
}
