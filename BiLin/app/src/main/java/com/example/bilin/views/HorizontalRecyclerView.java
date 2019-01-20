package com.example.bilin.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by zhangdi on 4/23/18.
 */
public class HorizontalRecyclerView extends RecyclerView {
    private ViewParent parent = this;

    public HorizontalRecyclerView(Context context) {
        super(context);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //Logger.e(parent.getClass().getName());
        while (!(parent instanceof ViewPager)) {
            parent = parent.getParent();
            //Logger.e(parent.getClass().getName());
        }
        //判断Recycler是否滑到顶部
        //if (this.canScrollHorizontally(-1))
        //判断Recycler是否水平滑到底部
        if (this.canScrollHorizontally(-1))
            parent.requestDisallowInterceptTouchEvent(true);//请求拦截事件
        //(this.canScrollHorizontally(-1))
//            parent.requestDisallowInterceptTouchEvent(true);//请求拦截事件
        return super.dispatchTouchEvent(ev);
    }

}
