package com.example.bilin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bilin.bean.LifeStyleData;
import com.example.bilin.utils.Logger;
import com.example.bilin.views.PullRefreshView;

/**
 * Created by zhangdi on 5/17/18.
 */
public class LifeStyleDetailsFragment extends Fragment {

    private Toolbar toolbar;
    private ImageButton imageButton;
    private TextView level, area, desc,toolbarTitle;

    private View parent;
    private PullRefreshView pullRefreshView;
    private LifeStyleData data;
    private String address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.life_style_details_main_view, container, false);
        parent = (View) container.getParent();
       // container.getParent();
        Logger.e("parent:"+parent+",,,container = "+container);
        if(parent instanceof PullRefreshView){
            pullRefreshView = ((PullRefreshView)parent);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            data = getArguments().getParcelable("lifedata");
            address = getArguments().getString("address");
        }
        initView(view);
        initData();
    }

    private void initData() {
        if(data != null){
            toolbarTitle.setText(data.getType());
            level.setText(data.getBrf());
            area.setText(address);
            desc.setText(data.getTxt());
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        imageButton = view.findViewById(R.id.home_up_indicator);
        toolbarTitle = view.findViewById(R.id.detail_toolbar_title);
        level = view.findViewById(R.id.level);
        area = view.findViewById(R.id.area);
        desc = view.findViewById(R.id.details_desc);
        if(pullRefreshView != null){
            Logger.e("initView pullRefreshView = "+pullRefreshView);
            pullRefreshView.enablePullRefreshView(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(pullRefreshView != null){
            Logger.e("onDestroyView pullRefreshView = "+pullRefreshView);
            pullRefreshView.enablePullRefreshView(true);
        }
    }
}
