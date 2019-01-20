package com.example.bilin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.bilin.R;

/**
 * Created by zhangdi on 2018/3/31.
 */

public class MainBottomCustomerView extends View {

    private static String INSTANCE_STATUS = "instance_status";
    private static String STATUS_ALPHA = "status_alpha";
    private int color = 0x45c01a;
    private Bitmap mIconBitmap;
    private int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
            getResources().getDisplayMetrics());
    private String title = "title";
    private float mAlpha;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Paint mPaint;
    private Paint mTextPaint;
    private Rect mBitmapRect;
    private Rect mTextBound;

    public MainBottomCustomerView(Context context) {
        this(context, null);
    }

    public MainBottomCustomerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 初始化attr
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MainBottomCustomerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MainBottomCustomerView);

        int n = a.getIndexCount();

        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MainBottomCustomerView_icon:
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) a.getDrawable(attr);
                    mIconBitmap = bitmapDrawable.getBitmap();
                    break;
                case R.styleable.MainBottomCustomerView_text:
                    title = a.getString(attr);
                    break;
                case R.styleable.MainBottomCustomerView_color:
                    color = a.getColor(attr, 0X45c01a);
                    break;
                case R.styleable.MainBottomCustomerView_text_size:
                    textSize = (int) a.getDimension(attr, TypedValue.applyDimension(TypedValue
                            .COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }

        a.recycle();

        /**
         * 先初始化完text的范围
         */
        mTextPaint = new Paint();
        mTextBound = new Rect();
        mTextPaint.setColor(0xff555555);
        mTextPaint.setTextSize(textSize);

        mTextPaint.getTextBounds(title, 0, title.length(), mTextBound);
    }

    /**
     * 測量控件高度和寬度
     * text 高度和宽度决定以后，根据系统的测量值，决定Icon的范围
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mIconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - mTextBound.height());

        int left = getMeasuredWidth() / 2 - mIconWidth / 2;
        int top = (getMeasuredHeight() - mIconWidth - mTextBound.height()) / 2;

        mBitmapRect = new Rect(left, top, left + mIconWidth, top + mIconWidth);

    }

    /**
     * 控件大小和位置测量完成之后可以绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mIconBitmap, null, mBitmapRect, null);

        //绘制纯色，setalpha,icon
        int alpha = (int) Math.ceil(255 * mAlpha);
        setUpTargetBitmap(alpha);

        canvas.drawBitmap(mBitmap, 0, 0, null);

        //绘制文本，绘制变色文本
        drawSourceText(canvas, alpha);

        drawTargetText(canvas, alpha);

    }

    /**
     * 绘制变色文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawTargetText(Canvas canvas, int alpha) {
        mTextPaint.setColor(color);
        mTextPaint.setAlpha(alpha);

        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = getMeasuredHeight() - getPaddingBottom();
        //y = mBitmapRect.height() + mTextBound.height();
        canvas.drawText(title, x, y, mTextPaint);
    }

    /**
     * 绘制原文本
     *
     * @param canvas
     * @param alpha
     */
    private void drawSourceText(Canvas canvas, int alpha) {
        mTextPaint.setColor(0Xff333333);
        mTextPaint.setAlpha(255 - alpha);
        int x = getMeasuredWidth() / 2 - mTextBound.width() / 2;
        int y = getMeasuredHeight() - getPaddingBottom();
//        Log.e("zhangdi","y1 = " +y);
        //y = mBitmapRect.height() + mTextBound.height();
//        Log.e("zhangdi","y2 = " +y);
        canvas.drawText(title, x, y, mTextPaint);
    }

    private void setUpTargetBitmap(int alpha) {
        //先画纯色图片
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config
                .ARGB_8888);
        mPaint = new Paint();
        mCanvas = new Canvas(mBitmap);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setAlpha(alpha);
        mPaint.setColor(color);
        mCanvas.drawRect(mBitmapRect, mPaint);
        //再画上面的图标，设置Mode为DST_IN
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setAlpha(alpha);
        mCanvas.drawBitmap(mIconBitmap, null, mBitmapRect, mPaint);
    }

    public void setIconAlpha(float alpha) {
        this.mAlpha = alpha;
        invalidateView();
    }

    /**
     * 重绘view 判断是否在ui线程
     */
    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {//in UI Thread
            invalidate();
        } else {
            postInvalidate();
        }

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        //将父类之前的一些操作保存起来
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        //将透明度保存
        bundle.putFloat(STATUS_ALPHA, mAlpha);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mAlpha = bundle.getFloat(STATUS_ALPHA);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
