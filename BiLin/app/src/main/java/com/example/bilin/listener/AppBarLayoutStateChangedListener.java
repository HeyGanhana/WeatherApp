package com.example.bilin.listener;

import android.support.design.widget.AppBarLayout;

/**
 * Created by zhangdi on 5/5/18.
 */
public abstract class AppBarLayoutStateChangedListener implements AppBarLayout
        .OnOffsetChangedListener {
    public enum State {
        EXPEND,
        COLLAPSE,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPEND) {
                onAppBarStateChangedListener(appBarLayout, State.EXPEND,verticalOffset);
            }
            mCurrentState = State.EXPEND;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState!= State.COLLAPSE){
                onAppBarStateChangedListener(appBarLayout,State.COLLAPSE,verticalOffset);
            }
            mCurrentState = State.COLLAPSE;
        } else {
            if(mCurrentState != State.IDLE){
                onAppBarStateChangedListener(appBarLayout,State.IDLE,verticalOffset);
            }
            mCurrentState = State.IDLE;

        }
    }

    public abstract void onAppBarStateChangedListener(AppBarLayout appBarLayout, State state,int verticalOffset);

}
