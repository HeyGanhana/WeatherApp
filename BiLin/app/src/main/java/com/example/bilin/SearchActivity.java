package com.example.bilin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.bilin.adapters.SearchAdapter;
import com.example.bilin.bean.City;
import com.example.bilin.listener.OnRecyclerItemClickListener;
import com.example.bilin.utils.Logger;
import com.example.bilin.utils.XmlParasUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by zhangdi on 5/9/18.
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener,
        OnRecyclerItemClickListener {

    private XmlParasUtils xpu;

    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;

    //searchView 搜索匹配到的城市信息
    private List<City> cityList = new ArrayList<>();
    private List<String> cityIdList = new ArrayList<>();
    private List<String> cityNameList = new ArrayList<>();
    private HashMap<String, City> matchedInfoMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarCompat.translucentStatusBar(this, true);
        this.getWindow().getDecorView().setSystemUiVisibility(View
                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.search_activity_main);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        init();
    }

    private void init() {
        xpu = XmlParasUtils.getInstance(this);

        recyclerView = findViewById(R.id.rv_search);
        searchAdapter = new SearchAdapter(this, cityList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(searchAdapter);

        searchAdapter.setOnRecyclerItemClickListener(this);
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL));

        searchAdapter.setOnRecyclerItemClickListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        Logger.e("ontextchanged :" + newText);
        matchedInfoMap = xpu.getKeyListFromValue(newText);
        cityList.clear();
        cityIdList.clear();
        if (matchedInfoMap != null && matchedInfoMap.size() > 0) {
//            for (String key : matchedInfoMap.keySet()) {
//                Logger.e("key:" + key + ",values:" + matchedInfoMap.get(key));
//            }
            cityList.addAll(matchedInfoMap.values());
            cityIdList.addAll(matchedInfoMap.keySet());
            for (City city : cityList) {
                Logger.e("string in cityList:" + city.getSimpleInfo());
            }
            if (cityList.size() > 0) {
                for (City city : cityList) {
                    cityNameList.add(city.getSimpleInfo());
                }
            }
            searchAdapter = new SearchAdapter(this,cityList);
            searchAdapter.setOnRecyclerItemClickListener(this);
            recyclerView.setAdapter(searchAdapter);
        }
        return false;
    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.e("search item click"+position);
        String location = cityList.get(position).getWeatherId();
        Bundle bundle = new Bundle();
        bundle.putString("location", location);
        bundle.putBoolean("isNeedReset", true);
        Intent intent = new Intent();
        intent.putExtra("searchItem",bundle);
        setResult(MainActivity.RESULT_SEARCHITEM_OK,intent);
        finish();
    }

    @Override
    public void onItemAddButtonClicked(View view, String adcode) {
        Logger.e("search onItemAddButtonClicked "+adcode);
        Bundle bundle = new Bundle();
        bundle.putString("adcode", adcode);
        Intent intent = new Intent();
        intent.putExtra("searchItemButton",bundle);
        setResult(MainActivity.RESULT_SEARCHITEM_BUTTON_OK,intent);
        finish();
    }
}
