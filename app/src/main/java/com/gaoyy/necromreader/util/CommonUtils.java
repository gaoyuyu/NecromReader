package com.gaoyy.necromreader.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;

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
}
