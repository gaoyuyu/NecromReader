package com.moudle.necromreader.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.moudle.necromreader.R;


/**
 * Created by moudle on 2017/1/24.
 */

public class WaveView extends View implements Runnable
{
    private static final String TAG= WaveView.class.getSimpleName();
    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mPaint;
    private Path mPath;
    private int mWidth;
    private int mHeight;

    private int riseY;
    private int offsetX;

    private int mMaskType;
    private int mMaskColor;
    private int mMaskCustomBackgroundID;
    private int mWaveColor;


    private Handler mHandler  = new Handler();

    public int getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(int offsetX)
    {
        this.offsetX = offsetX;
    }

    public int getRiseY()
    {
        return riseY;
    }

    public void setRiseY(int riseY)
    {
        this.riseY = riseY;
    }

    public WaveView(Context context)
    {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        mMaskType = ta.getInt(R.styleable.WaveView_maskType,0);
        mMaskColor = ta.getColor(R.styleable.WaveView_maskColor,Color.LTGRAY);
        mWaveColor = ta.getColor(R.styleable.WaveView_waveColor,Color.BLUE);
        mMaskCustomBackgroundID = ta.getResourceId(R.styleable.WaveView_maskCustomBackground,0);
        ta.recycle();
    }


    private void initPaint()
    {
        mDrawFilter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.DITHER_FLAG);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void run()
    {
        offsetX+=10;
        if(offsetX >= mWidth)
        {
            offsetX= 0;
        }
        setOffsetX(offsetX);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if(mMaskType == 0)
        {
            throw new RuntimeException("请设置类型");
        }

        if(mMaskType == 4 && mMaskCustomBackgroundID == 0)
        {
            throw new RuntimeException("未有设置自定义图片");
        }

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        drawMask(canvas);

        //取交集，和上层
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        drawWave(canvas);


        mPaint.setXfermode(null);
        canvas.restore();

        mHandler.postDelayed(this,20);
    }

    private void drawMask(Canvas canvas)
    {
        RectF rectF = new RectF(0,0,mWidth,mHeight);
        mPaint.setColor(mMaskColor);
        switch (mMaskType)
        {
            case 1:
                //Circle
                canvas.drawOval(rectF,mPaint);
                break;
            case 2:
                //Rect
                canvas.drawRect(rectF,mPaint);
                break;
            case 3:
                //RoundRect
                canvas.drawRoundRect(rectF,30,30,mPaint);
                break;
            case 4:
                //遮罩层bitmap
                Bitmap maskBitmap = ((BitmapDrawable)getResources().getDrawable(mMaskCustomBackgroundID)).getBitmap();
                //bitmap缩放到整个view的大小
                maskBitmap = Bitmap.createScaledBitmap(maskBitmap,mWidth,mHeight,false);
                //获取长度和宽度
                int maskWidth = maskBitmap.getWidth();
                int maskHeight = maskBitmap.getHeight();
                canvas.drawBitmap(maskBitmap, 0, 0, mPaint);
                break;
        }
    }

    private void drawWave(Canvas canvas)
    {
        // 从canvas层面去除锯齿
        canvas.setDrawFilter(mDrawFilter);
        canvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom()) / 2);

        mPaint.setColor(mWaveColor);

        mPath.reset();

        //屏幕外边的一条波纹
        mPath.moveTo(-mWidth*3/2+offsetX,mHeight/2-riseY);
        mPath.quadTo(-mWidth*5/4+offsetX,mHeight/2-20-riseY,-mWidth+offsetX,mHeight/2-riseY);
        mPath.quadTo(-mWidth*3/4+offsetX,mHeight/2-(-20)-riseY,-mWidth/2+offsetX,mHeight/2-riseY);


        //屏幕里面的波纹
        mPath.quadTo(-mWidth/4+offsetX,mHeight/2-20-riseY,0+offsetX,mHeight/2-riseY);
        mPath.quadTo(mWidth/4+offsetX,mHeight/2-(-20)-riseY,mWidth/2+offsetX,mHeight/2-riseY);

        mPath.lineTo(mWidth/2,mHeight/2);
        //在此处封闭Path的时候也同时需要偏移量，否则出现左侧跳动
        mPath.lineTo(-mWidth/2-offsetX,mHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        mWidth = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                //wrap_content下默认为200dp
                break;
        }
        mHeight = result;
        return result;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    public void anim()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 200+100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                riseY = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setStartDelay(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();
    }

    public int getWaveViewHeight()
    {
        return mHeight;
    }

    public void updateViewWithRiseY(int riseY)
    {
        setRiseY(riseY);
        postInvalidate();
    }
}
