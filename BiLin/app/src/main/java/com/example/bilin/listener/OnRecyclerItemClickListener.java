package com.example.bilin.listener;

import android.view.View;

/**
 * Created by zhangdi on 4/27/18.
 */
public interface OnRecyclerItemClickListener {
    void onItemClick(View view, int position);
    void onItemAddButtonClicked(View view, String adcode);

}
