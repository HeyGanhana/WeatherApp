package com.example.bilin.views;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.bilin.R;
import com.example.bilin.utils.Logger;

/**
 * Created by zhangdi666 on 5/8/18.
 */
public class PullRefreshView extends ViewGroup implements AppBarLayout.OnOffsetChangedListener {

    private static final int ANIMATION_DURATION = 600;

    OnRefreshListener onRefreshListener;
    float largeDegree = 0;
    private View header;
    private View footer;
    private TextView mHeaderText;
    private TextView mFooterText;
    private ProgressBar mHeaderProgressBar;
    private ProgressBar mFooterProgressBar;
    private CircleAnimantionView mHeaderRefreshImg;
    private ImageView mFooterLoadImg;
    private int mLayoutContentHeight;
    private int mLastChildIndex;
    private int mLastY;
    private int mInterceptY;
    private int mEffectiveHeaderHeight;
    private int mEffectiveFooterHeight;
    private LayoutInflater mLayoutInflater;
    private State mState = State.NORMAL;
    private boolean isIntercept = false;
    private ObjectAnimator rotate;
    private boolean enable = true;

    public PullRefreshView(Context context) {
        this(context, null);
    }

    public PullRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public PullRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("zhangdi666", "onFinishInflate()");
        mLastChildIndex = getChildCount() - 1;
        CoordinatorLayout coordinatorLayout = findViewById(R.id.main_content);
        AppBarLayout appBarLayout = coordinatorLayout.findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(this);
        addHeaderView();
        //addFooterView();
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("zhangdi666", "onMeasure() getChildCount()" + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /*
     *
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == header) {
                Log.e("zhangdi666", "header  l =" + l + ",t = " + t + ",r = " + r + ",b = " + b);
                Logger.e("111child.getMeasuredHeight() = " + child.getMeasuredHeight() + ",child" +
                        ".getHeight() = " + child.getHeight());
                child.layout(0, 0 - child.getMeasuredHeight(), child
                        .getMeasuredWidth(), 0);
                mEffectiveHeaderHeight = child.getHeight() + 24;
                Logger.e("222child.mEffectiveHeaderHeight = " + mEffectiveHeaderHeight + ",child" +
                        ".getPaddingTop()  " + child.getPaddingTop());
            } /*else if (child == footer) {
                Log.e("zhangdi666", "footer  l =" + l + ",t = " + t + ",r = " + r + ",b = " + b);
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(),
                        mLayoutContentHeight + child.getMeasuredHeight());
                mEffectiveFooterHeight = child.getHeight();
            } */ else {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(),
                        mLayoutContentHeight + child.getMeasuredHeight());
                if (i < getChildCount()) {
                    Log.e("zhangdi666", "getChildCount() = " + getChildCount() + "child:" + child);
                    /*if (child instanceof CoordinatorLayout) {
                        Log.e("zhangdi666", "CollapsingToolbarLayout  getMeasuredHeight() =" +
                                getMeasuredHeight());
                        mLayoutContentHeight += getMeasuredHeight();
                        continue;
                    }*/
                    Log.e("zhangdi666", "CollapsingToolbarLayou22t  child.getMeasuredHeight()() =" +
                            child.getMeasuredHeight());
                    mLayoutContentHeight += child.getMeasuredHeight();
                }
            }
        }
        Log.e("zhangdi666", "mLayoutContentHeight =" + mLayoutContentHeight);
    }

    public void enablePullRefreshView(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        if (!enable) {
            return false;
        }
        if (mState == State.REFRESHING || mState == State.LOADING) {
            return false;
        }

        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //mInterceptY = y;
                mLastY = y;
                intercept = false;
                Log.e("zhangdi666", "onInterceptTouchEvent ACTION_DOWN intercept = " + intercept);
                break;
            case MotionEvent.ACTION_MOVE:
                if (y > mInterceptY) {//refresh
                    Log.e("zhangdi666", "onInterceptTouchEvent ACTION_move y > mInterceptY = ");
                    View child = getChildAt(1);

                    intercept = getInterceptFromChild(child);
                    if (intercept) {
                        updateState(State.TRY_REFRESH);
                    }
                } /*else if (y < mInterceptY) {

                } */ else {
                    intercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        mInterceptY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mState == State.REFRESHING || mState == State.LOADING) {
            return true;
        }
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastY - y;
                mLastY = y;
                //xia la refresh
                if (getScrollY() <= 0 && dy <= 0) {
                    Log.e("zhangdi666", "ontouchevent11 mstate:" + mState);
                    if (mState == State.TRY_REFRESH) {
                        scrollBy(0, dy / 3);
                    } else {
                        scrollBy(0, dy);
                    }
                } /*else if (getScrollY() >= 0 && dy >= 0) {
                    Log.e("zhangdi666", "ontouchevent22 mstate:" + mState);
                    if (mState == State.TRY_LOAD) {
                        scrollBy(0, dy / 3);
                    } else {
                        scrollBy(0, dy / 3);
                    }
                } */ else {
                    Log.e("zhangdi777", "scrollBy");
                    scrollBy(0, dy / 3);
                }

                beforeRefreshing(dy);
                //beforeLoading();
                break;
            case MotionEvent.ACTION_UP:
                mLastY = y;
                if (getScrollY() <= -mEffectiveHeaderHeight) {
                    releaseWithStateRefreshing();
                    //jian ting
                    Log.e("zhangdi666", "on touch event action up refresh");
                    //onRefreshFinish();
                    if (onRefreshListener != null) {
                        onRefreshListener.onRefreshFinish();
                    }
                } /*else if (getScrollY() >= mEffectiveFooterHeight) {
                    releaseWithStateLoading();
                    //jian ting
                    //onLoadMoreFinish();
                    Log.e("zhangdi666", "on touch event action up load more");
                    if (onRefreshListener != null) {
                        onRefreshListener.onLoadMoreFinish();
                    }
                }*/ else {
                    Log.e("zhangdi777", "ACTION_UP 7777");
                    releaseWithStateTryRefresh();
                    //releaseWithStateTryLoad();

                }
                break;
        }
        mLastY = y;
        return super.onTouchEvent(event);
    }

    //释放为tryload状态
    private void releaseWithStateTryLoad() {
        scrollBy(0, getScrollY());
        mFooterText.setText("上拉加载");
        //mFooterProgressBar.setVisibility(GONE);
        updateState(State.NORMAL);
    }

    //释放为try refresh状态
    private void releaseWithStateTryRefresh() {
        scrollBy(0, -getScrollY());
        mHeaderText.setText("下拉刷新");
        //mHeaderProgressBar.setVisibility(GONE);
        updateState(State.NORMAL);
        mHeaderRefreshImg.resetSmallView();
    }

    //释放为 正在loading状态
    private void releaseWithStateLoading() {
        scrollBy(0, mEffectiveFooterHeight);
        mFooterText.setText("松开加载");
        mFooterProgressBar.setVisibility(VISIBLE);
        updateState(State.LOADING);
    }

    //释放为 refreshing状态
    private void releaseWithStateRefreshing() {
        Log.e("zhangdi666", "mEffectiveHeaderHeight11 = " + mEffectiveHeaderHeight);
        mHeaderText.setText("正在刷新...");
        //mHeaderProgressBar.setVisibility(VISIBLE);
        updateState(State.REFRESHING);
        scrollTo(0, -mEffectiveHeaderHeight);
        rotate = ObjectAnimator.ofFloat(mHeaderRefreshImg, ROTATION, 0.0f, 360.0f);
        rotate.setRepeatCount(-1);
        rotate.setDuration(ANIMATION_DURATION);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.start();
    }

    public void resetView() {
        mHeaderRefreshImg.resetSmallView();
    }

    private void beforeLoading() {
        Log.e("zhangdi666", "beforeLoading ,mEffectiveHeaderHeight = " +
                mEffectiveHeaderHeight);
        if (getScrollY() >= mEffectiveFooterHeight) {
            mFooterText.setText("松开加载更多");
        } else {
            mFooterText.setText("上拉加载更多");
        }
    }

    private void beforeRefreshing(int dy) {
        //refresh view
        int scrollY = Math.abs(getScrollY());
        scrollY = scrollY > mEffectiveHeaderHeight ? mEffectiveFooterHeight : scrollY;
        float angle = (float) (scrollY * 1.0 / mEffectiveHeaderHeight * 360);
        if (angle >= 0 && angle < 45) {
            angle = 0;
        } else if (angle >= 45 && angle < 90) {
            angle = 45;
        } else if (angle >= 90 && angle < 135) {
            angle = 90;
        } else if (angle >= 135 && angle < 180) {
            angle = 135;
        } else if (angle >= 180 && angle < 225) {
            angle = 180;
        } else if (angle >= 225 && angle < 270) {
            angle = 225;
        } else if (angle >= 270 && angle < 315) {
            angle = 270;
        } else if (angle >= 315 && angle < 360) {
            angle = 315;
        } else {
            angle = 360;
        }
        Log.e("zhangdi666", "angle = " + angle + ",largeDegree = " + largeDegree);
        /*if (angle < largeDegree) {
            mHeaderRefreshImg.removeDegree(largeDegree);
        }*/
        largeDegree = angle;
        mHeaderRefreshImg.setCurrentDegree(angle);
        //mHeaderRefreshImg.setRotation(angle);


        if (getScrollY() <= -mEffectiveHeaderHeight) {
            mHeaderText.setText("松开刷新");
        } else {
            mHeaderText.setText("下拉刷新");
        }
    }

    private void addHeaderView() {

        header = mLayoutInflater.inflate(R.layout.pull_refresh_header, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LayoutParams params1 = generateDefaultLayoutParams();
        params1.height = LayoutParams.WRAP_CONTENT;
        params1.width = LayoutParams.MATCH_PARENT;
        header.setLayoutParams(params1);
        addView(header, 0);

        mHeaderText = header.findViewById(R.id.header_text);
        mHeaderProgressBar = header.findViewById(R.id.header_progress);
        mHeaderRefreshImg = header.findViewById(R.id.header_img);
    }

    private void addFooterView() {
        footer = mLayoutInflater.inflate(R.layout.pull_load_footer, null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        addView(footer, params);

        mFooterText = footer.findViewById(R.id.footer_text);
        mFooterProgressBar = footer.findViewById(R.id.footer_progress);
        mFooterLoadImg = footer.findViewById(R.id.footer_img);
    }

    private boolean getInterceptFromChild(View child) {
        boolean intercept = false;
        Log.e("zhangdi666", "getInterceptFromChild child：" + child);
        if (child instanceof AdapterView) {
            Log.e("zhangdi666", "is AdapterView");
            intercept = adapterViewRefreshIntercept(child);
        } else if (child instanceof ScrollView) {
            Log.e("zhangdi666", "is ScrollView");
            intercept = false;//scrollViewRefreshIntercept(child);
        } else if (child instanceof RecyclerView) {
            Log.e("zhangdi666", "is RecyclerView");
            intercept = false;//recyclerViewRefreshIntercept(child);
        } else if (child instanceof CoordinatorLayout) {
            Log.e("zhangdi666", "isAppBarLayout");
            intercept = collapsingLayoutRefreshIntercept(child);
        } else if (child instanceof NestedScrollView) {

        }
        return intercept;
    }

    private boolean collapsingLayoutRefreshIntercept(View child) {
        boolean intercept = false;
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) child;
        /*for (int i = 0; i < coordinatorLayout.getChildCount(); i++) {
            View childView = coordinatorLayout.getChildAt(i);
            Log.e("zhangdi666", "collapsingLayoutRefreshIntercept childView= " + childView);
            if (childView instanceof AppBarLayout) {
                AppBarLayout appBarLayout = (AppBarLayout) childView;
                Log.e("zhangdi666", "appBarLayout.getTotalScrollRange() = " + appBarLayout
                        .getTotalScrollRange());
                //if (appBarLayout.getTotalScrollRange() == 0) {
                intercept = true;
                //}
            }
        }*/
        Log.e("zhangdi666", "collapsingLayoutRefreshIntercept isIntercept = " + isIntercept);
        if (isIntercept) intercept = true;
        Log.e("zhangdi666", "collapsingLayoutRefreshIntercept intercept = " + intercept);
        return intercept;
    }

    private boolean recyclerViewRefreshIntercept(View child) {
        boolean intercept = false;
        RecyclerView recyclerView = (RecyclerView) child;
        if (recyclerView.computeVerticalScrollOffset() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    private boolean scrollViewRefreshIntercept(View child) {
        boolean intercept = false;
        ScrollView scrollView = (ScrollView) child;
        if (scrollView.getY() <= 0) {
            intercept = true;
        }
        return intercept;
    }

    private boolean adapterViewRefreshIntercept(View child) {
        boolean intercept = true;
        AdapterView adapterViewChild = (AdapterView) child;
        //判断adapterView第一个显示的view 对应的position是不是0  并且第一个child到顶部的距离为0
        if (adapterViewChild.getFirstVisiblePosition() != 0 && adapterViewChild.getChildAt(0)
                .getTop() != 0) {
            intercept = false;
        }
        return intercept;
    }

    private void updateState(State state) {
        this.mState = state;
    }

    public void onRefreshFinish() {
        scrollTo(0, 0);
        mHeaderText.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(GONE);
        updateState(State.NORMAL);
        if (rotate != null)
            rotate.end();
        mHeaderRefreshImg.resetSmallView();
    }

    public void onLoadMoreFinish() {
        scrollTo(0, 0);
        mFooterText.setText("上拉加载");
        mFooterProgressBar.setVisibility(GONE);
        updateState(State.NORMAL);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            isIntercept = false;
        } else if (verticalOffset == 0) {//expand
            isIntercept = true;
        } else {
            isIntercept = false;
        }
    }

    public enum State {
        TRY_REFRESH,
        REFRESHING,
        TRY_LOAD,
        LOADING,
        NORMAL
    }

    public interface OnRefreshListener {
        void onRefreshFinish();

        void onLoadMoreFinish();
    }
}
