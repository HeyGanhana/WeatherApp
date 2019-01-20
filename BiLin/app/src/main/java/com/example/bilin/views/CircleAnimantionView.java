package com.example.bilin.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.bilin.R;

import java.util.ArrayList;

/**
 * Created by zhangdi on 5/14/18.
 */
public class CircleAnimantionView extends View {

    private int iconWidth;
    private int iconColor = Color.BLACK;
    private int bigCircleThickness = 2;
    //中间大圆半径
    private int bigRadius = 20;
    //周边小圆半径
    private int smallRadius = 1;
    private Paint bigCirclePaint;
    private Paint smallCirclePaint;
    private Rect drawableRect;
    //大圆和小圆之前的距离
    private int padding = 5;
    private Canvas canvas = new Canvas();
    private ArrayList<Float> smallDegrees = new ArrayList<>();

    public CircleAnimantionView(Context context) {
        this(context, null);
    }

    public CircleAnimantionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleAnimantionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleAnimantionView);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CircleAnimantionView_circle_color:
                    iconColor = a.getColor(attr, 0xffffff);
                    break;
                case R.styleable.CircleAnimantionView_icon_size:
                    iconWidth = (int) a.getDimension(attr, TypedValue.applyDimension(TypedValue
                            .COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        a.recycle();
        smallDegrees.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom());
        Log.i("zhangdi", "getMeasuredHeight()：" + getMeasuredHeight() + ",getMeasuredWidth():" +
                getMeasuredWidth());
        Log.i("zhangdi", "iconWidth:" + iconWidth);
        int left = getMeasuredWidth() / 2 - iconWidth / 2;
        int top = getMeasuredHeight() / 2 - iconWidth / 2;

        drawableRect = new Rect(left, top, iconWidth + left, iconWidth + top);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        bigCirclePaint = new Paint();
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setDither(true);
        bigCirclePaint.setColor(iconColor);
        bigCirclePaint.setStyle(Paint.Style.STROKE);
        bigCirclePaint.setStrokeWidth(bigCircleThickness);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawCircle(drawableRect.width() / 2, drawableRect.height() / 2, bigRadius,
                bigCirclePaint);
//        bigCirclePaint.setColor(Color.TRANSPARENT);
//        canvas.drawCircle(drawableRect.width() / 2, drawableRect.height() / 2,
//                bigRadius - bigCircleThickness, bigCirclePaint);

        smallCirclePaint = new Paint();
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setDither(true);
        smallCirclePaint.setColor(iconColor);

        int smallCircleRange = bigRadius + padding + smallRadius;
//        Log.i("zhangdi", "Math.sin(45):" + Math.sin(Math.PI));
        if (smallDegrees.size() <= 0) return;
        for (float degree : smallDegrees) {
            float smallCx = (float) (iconWidth / 2 + smallCircleRange * Math.sin(degree));
            float smallCy = (float) (iconWidth / 2 - smallCircleRange * Math.cos(degree));
            Log.i("zhangdi", "smallCx = " + smallCx + ",smallCy = " + smallCy + ",degree:" +
                    degree);
            canvas.drawCircle(smallCx, smallCy, 4, smallCirclePaint);
        }
    }

    public void setCurrentDegree(float degree) {
        degree = (float) (degree / 180 * Math.PI);
        smallDegrees.add(degree);
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void resetSmallView(){
        smallDegrees.clear();
    }

    public void removeDegree(float degree){
        degree = (float) (degree / 180 * Math.PI);
        Log.e("zhangdi","smallDegrees.indexOf(degree) = "+smallDegrees.indexOf(degree));
        smallDegrees.remove(degree);
        invalidateView();
    }
}
