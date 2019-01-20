package com.example.bilin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bilin.bean.LifeStyleData;
import com.example.bilin.utils.Logger;

import qiu.niorgai.StatusBarCompat;

public class LifeStyleActivity extends Activity {

    private final static String TAG = "LifeStyleActivity";

    private LifeStyleData data;
    private String address;

    private Toolbar toolbar;
    private ImageButton imageButton;
    private TextView level, area, desc, toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.translucentStatusBar(this, true);
        this.getWindow().getDecorView().setSystemUiVisibility(View
                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.life_style_details_main_view);
        initView();
        initData();
    }


    private void initData() {
        Bundle bundle = getIntent().getBundleExtra("lifeStyleData");
        address = bundle.getString("address");
        data = bundle.getParcelable("lifedata");
        if (data != null) {
            toolbarTitle.setText(data.getType());
            level.setText(data.getBrf());
            area.setText(address);
            desc.setText(data.getTxt());
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        imageButton = findViewById(R.id.home_up_indicator);
        toolbarTitle = findViewById(R.id.detail_toolbar_title);
        level = findViewById(R.id.level);
        area = findViewById(R.id.area);
        desc = findViewById(R.id.details_desc);
    }
}
