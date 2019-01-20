package com.example.bilin.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by zhangdi on 5/3/18.
 */
public class MyNestedScrollViewChild extends NestedScrollView implements NestedScrollView
        .OnScrollChangeListener {

    int showHeight;
    LinearLayout childContainer;
    private NestedScrollingChildHelper nestedScrollingChildHelper;
    private int lastY;
    private int consumed[] = new int[2];
    private int offset[] = new int[2];
    private LayoutInflater layoutInflater;

    public MyNestedScrollViewChild(@NonNull Context context) {
        this(context, null);
    }

    public MyNestedScrollViewChild(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
        nestedScrollingChildHelper.setNestedScrollingEnabled(true);
        layoutInflater = LayoutInflater.from(context);
    }

    /*@Override
    public void addView(View child) {
        childContainer = findViewById(R.id.ll_child_container);
        View headView = layoutInflater.inflate(R.layout.tool_bar_view,null);
        childContainer.addView(headView);
        super.addView(headView);
    }*/

    /*@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        showHeight = getMeasuredHeight();

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }*/

    @Override
    public boolean startNestedScroll(int axes) {
        Log.v("zhangdi22", "MyNestedScrollViewChild startNestedScroll axes:" + axes);
        return getNestedScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        Log.v("zhangdi22", "MyNestedScrollViewChild stopNestedScroll type:");
        getNestedScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        Log.v("zhangdi22", "MyNestedScrollViewChild isNestedScrollingEnabled" +
                getNestedScrollingChildHelper().isNestedScrollingEnabled());
        return getNestedScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getNestedScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable
            int[] offsetInWindow) {
        Log.i("zhangdi22", "MyNestedScrollViewChild dispatchNestedPreScroll:dx" + dx + ",dy:" +
                dy + ",consumed:" + consumed + "," +
                "offsetInWindow:" + offsetInWindow);
        return getNestedScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed,
                offsetInWindow);
    }

    @Override
    public boolean hasNestedScrollingParent() {
        Log.v("zhangdi22", "MyNestedScrollViewChild hasNestedScrollingParent");
        return getNestedScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int
            dyUnconsumed, @Nullable int[] offsetInWindow) {
        Log.i("zhangdi22", "MyNestedScrollViewChild dispatchNestedScroll:dxConsumed:" +
                dxConsumed + "," +
                "dyConsumed:" + dyConsumed + ",dxUnconsumed:" + dxUnconsumed + ",dyUnconsumed:" +
                dyUnconsumed + ",offsetInWindow:" + offsetInWindow);
        return getNestedScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed,
                dxUnconsumed, dyUnconsumed, offsetInWindow);
    }


    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        Log.i("zhangdi22", "child dispatchNestedFling:velocityX:" + velocityX + ",velocityY:" +
                velocityY + ",consumed:" + consumed);
        return getNestedScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        Log.i("zhangdi22", "child dispatchNestedPreFling:velocityX:" + velocityX + ",velocityY:"
                + velocityY);
        return getNestedScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

    public NestedScrollingChildHelper getNestedScrollingChildHelper() {
        if (nestedScrollingChildHelper == null) {
            nestedScrollingChildHelper = new NestedScrollingChildHelper(this);
            nestedScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return nestedScrollingChildHelper;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) ev.getY();
                int dy = lastY - y;// down +  up - ...
                Log.e("zhangdi666", "lastY = " + lastY + ",y = " + y + ",dy = " + dy);
                lastY = y;
                Log.e("zhangdi666", "startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)+" +
                        startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) +
                        "dispatchNestedPreScroll\n" +
                        "                        (0, dy, consumed, offset)=");
                //getNestedScrollingChildHelper().isNestedScrollingEnabled();
                Log.e("zhangdi666", "getscrolly =" + getScrollY());
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL) && dispatchNestedPreScroll
                        (0, dy, consumed, offset)) {
                    int remain = dy - consumed[1];
                    Log.v("zhangdi666", "remain:" + remain);
                    if (remain != 0) {
                        scrollBy(0, remain);
                    }

                } else {
                    Log.v("zhangdi666", "else else else");
                    scrollBy(0, dy);
                }
                break;
            default:
                stopNestedScroll();
                break;

        }
        return true;
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int
            oldScrollY) {
        Log.e("zhangdi666", "scrollX=" + scrollX + ",scrollY = " + scrollY + ",oldScrollX = " +
                oldScrollX + ",oldScrollY = " + oldScrollY);
    }

    /*@Override
    public void scrollTo(int x, int y) {
        Log.v("zhangdi11", "scrollTo x:" + x + ",y:" + y);
        int maxY = getMeasuredHeight() - showHeight;
        if (y > maxY) {
            y = maxY;
        }
        if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
    }*/
}
