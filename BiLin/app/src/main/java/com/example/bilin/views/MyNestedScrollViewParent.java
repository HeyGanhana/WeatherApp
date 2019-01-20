package com.example.bilin.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bilin.R;

/**
 * Created by zhangdi on 5/3/18.
 */
public class MyNestedScrollViewParent extends LinearLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper nestedScrollingParentHelper;

    private ImageView img;
    private MyNestedScrollViewChild myNestedScrollViewChild;
    private RelativeLayout rlToolbarContainer;
    private int imgHeight;

    public MyNestedScrollViewParent(@NonNull Context context) {
        this(context, null);
    }

    public MyNestedScrollViewParent(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyNestedScrollViewParent(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        nestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    private TextView textView;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        img = (ImageView) findViewById(R.id.refresh_icon);
        /*textView = (TextView) getChildAt(1);*/
        /*rlToolbarContainer = (RelativeLayout) getChildAt(1);*/
        myNestedScrollViewChild = (MyNestedScrollViewChild) getChildAt(1);
        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                if (imgHeight <= 0) {
                    imgHeight = img.getMeasuredHeight();
                    Log.v("zhangdi11", "imgHeight:" + imgHeight);
                }
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        imgHeight = img.getMeasuredHeight();

        heightMeasureSpec = heightMeasureSpec + imgHeight + img.getPaddingBottom() + img
                .getPaddingTop();
        Log.d("zhangdi22", "heightMeasureSpec:" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /*@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        imgHeight = img.getMeasuredHeight();
        Log.i("zhangdi22", "topheight=" + imgHeight);
    }*/

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        Log.v("zhangdi22", "MyNestedScrollViewParent onStartNestedScroll child:" + child + "," +
                "target:" + target + ",axes:" + axes + ",type:");
        return true;//(axes == ViewCompat.SCROLL_AXIS_VERTICAL) && (child instanceof
        //MyNestedScrollViewChild);
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        Log.v("zhangdi22", "MyNestedScrollViewParent onNestedScrollAccepted child:" + child +
                "target:" + target + ",axes:" + axes + ",type:");
        nestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        Log.v("zhangdi22", "MyNestedScrollViewParent onStopNestedScroll target:" + target + "," +
                "type:");
        nestedScrollingParentHelper.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int
            dxUnconsumed, int dyUnconsumed) {
        Log.v("zhangdi22", "MyNestedScrollViewParent onNestedScroll target:" + target + "," +
                "dxConsumed:" + dxConsumed + ",dyConsumed:" + dyConsumed + ",dxUnconsumed:" +
                dxUnconsumed + ",dyUnconsumed:" + dyUnconsumed + ",type:");
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    /*
      lanjie shijian
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        Log.v("zhangdi22", "MyNestedScrollViewParent onNestedPreScroll target:" + target + ",dx:"
                + dx + ",dy:" + dy + ",consumed[]：" + consumed + "type:");
        Log.v("zhangdi11", "dy:" + dy);
        boolean hiddenTop = dy > 0 && getScrollY() < imgHeight;
        boolean showTop = dy < 0 && this.getScrollY() > 0 && !ViewCompat.canScrollVertically(target,
                -1)&& myNestedScrollViewChild.getScrollY() == 0;
        Log.i("zhangdi22", "dy:" + dy + ",getScrollY()：" + getScrollY() + ",imgHeight:" +
                imgHeight);
        Log.i("zhangdi22", "hiddenTop:" + hiddenTop + ",showTop：" + showTop);
        if (showTop || hiddenTop) {
            scrollBy(0, dy);// down + up -
            consumed[1] = dy;
        }
    }

    private boolean hideImg(int dy) {
        Log.i("zhangdi22", "hideImg dy:" + dy + ",getScrollY()：" + getScrollY() + ",imgHeight:" +
                imgHeight);
        if (dy > 0) {
            if (this.getScrollY() < imgHeight) {
                return true;
            }
        }
        return false;
    }

    private boolean showImg(int dy) {
        Log.i("zhangdi22", "showImg dy:" + dy + ",getScrollY()：" + getScrollY() + ",imgHeight:" +
                imgHeight + ",myNestedScrollViewChild.getScrollY()" + myNestedScrollViewChild
                .getScrollY());
        if (dy < 0) {//xia la
            if (getScrollY() > 0 && myNestedScrollViewChild.getScrollY() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void scrollTo(int x, int y) {//y down + up -
        if (y < 0) {
            y = 0;
        }
        if (y > imgHeight) {
            y = imgHeight;
        }
        super.scrollTo(x, y);
    }

}
