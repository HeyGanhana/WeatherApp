package com.example.bilin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bilin.adapters.HorizontalWeatherAdapter;
import com.example.bilin.adapters.LifeStyleAdapter;
import com.example.bilin.adapters.WeatherAdapter;
import com.example.bilin.bean.AllWeatherData;
import com.example.bilin.bean.LifeStyleData;
import com.example.bilin.bean.OneDayInForecast7;
import com.example.bilin.bean.WeatherBasic;
import com.example.bilin.bean.WeatherDailyForecast;
import com.example.bilin.bean.WeatherNowData;
import com.example.bilin.listener.AppBarLayoutStateChangedListener;
import com.example.bilin.listener.OnRecyclerItemClickListener;
import com.example.bilin.utils.ACache;
import com.example.bilin.utils.ImageUtils;
import com.example.bilin.utils.JsonParasUtils;
import com.example.bilin.utils.Logger;
import com.example.bilin.utils.XmlParasUtils;
import com.example.bilin.views.HorizontalRecyclerView;
import com.example.bilin.views.PullRefreshView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangdi on 2018/3/28.
 */

public class TabFragment extends Fragment implements OnRecyclerItemClickListener, Toolbar
        .OnMenuItemClickListener {

    private static final String KEY = "key=e3f1ac3534b547da995fb02ba1667cf3";
    private static final String LOCATION_AUTO = "location=";
    private static final String ALL_WEATHER = "https://free-api.heweather.com/s6/weather?";
    private static final int REQUEST_FORECAST_WEATHER_DATA = 0x123;
    private static final int REQUEST_7FORECAST_WEATHER_DATA = 0x456;
    private static final int REQUEST_LIFE_STYLE = 0x789;
    private String allWeatherUrl;
    private String ariUrl;
    private NestedScrollView nestedScrollView;
    private PullRefreshView pullToRefreshView;
    private RecyclerView recyclerView;
    private HorizontalRecyclerView horizontalRecyclerView;
    private RecyclerView gridRecyclerView;
    private LinearLayout todayLinearLayout;
    private ImageView imgToday;
    private TextView tvTitle;

    private AllWeatherData allWeatherData;
    private List<OneDayInForecast7> oneDayInForecast7List;
    private List<WeatherDailyForecast> dailyForecastList = new ArrayList<>();
    private WeatherBasic weatherBasic;
    private WeatherNowData weatherNowData;
    private LifeStyleData lifeStyleData;
    private HashMap<String, LifeStyleData> lifeStyleDataHashMap = new HashMap<>();
    private List<LifeStyleData> lifeStyleDataList;
    private WeatherAdapter adapter;
    private HorizontalWeatherAdapter horiAdapter;
    private LifeStyleAdapter lifeStyleAdapter;
    private ImageUtils imageUtils = null;

    private String defaultLocation = "CN101010300";
    private String location = defaultLocation;
    private boolean isNeedReset = false;

    private MainActivity mainActivity;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private TextView toolBarTitle;

    private ACache aCache;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_FORECAST_WEATHER_DATA:
                    mainActivity.cancelProgressDialog();
                    pullToRefreshView.onRefreshFinish();

                    tvTitle.setText(weatherNowData.getCond_txt());
                    collapsingToolbarLayout.setTitle(weatherNowData.getMessage());
                    toolBarTitle.setText(weatherBasic.getSimpleLocation());

                    initLifeStyleAdapter();

                    initHoriAdapter();

                    initTodayView();
                    break;
                case REQUEST_7FORECAST_WEATHER_DATA:
                    adapter = new WeatherAdapter(mainActivity, oneDayInForecast7List);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    };
    private Bundle priBundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        View view = inflater.inflate(R.layout.contact_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        Logger.e("data:" + dailyForecastList.size());
        Logger.e("onCreateView getArguments() = " + getArguments());
        Logger.e("onCreateView bundle = " + priBundle);
        if (getArguments() != null)
            location = getArguments().getString("location");
        if (savedInstanceState != null) {
            dailyForecastList = savedInstanceState.getParcelableArrayList("dailyForecastList");
            priBundle = savedInstanceState.getBundle("localbundle");
            Logger.e("savedInstanceState--> bundle = " + priBundle);
        }
        if (priBundle != null) {
            location = priBundle.getString("location", "CN101010100");
            isNeedReset = priBundle.getBoolean("isNeedReset", false);
            Logger.e("tabfragment get location:" + location + ",isNeedReset :" + isNeedReset);
            priBundle = null;
        }
        initData();
        initEvent();


    }

    public void updataArguments(Bundle bundle) {
        Logger.e("update arguments getArguments() = " + bundle);
        this.location = bundle.getString("location");
        this.isNeedReset = bundle.getBoolean("isNeedReset");
        priBundle = getArguments();
        if (priBundle != null) {
            priBundle.putString("location",location);
            priBundle.putBoolean("isNeedReset",isNeedReset);
        }

    }

    private void initView(View view) {
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout = view.findViewById(R.id.appbar);
        toolbar = view.findViewById(R.id.tool_bar);
        toolBarTitle = view.findViewById(R.id.toolbar_title);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.main);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        Logger.e("setSupportActionBar initView");
        toolbar.setOnMenuItemClickListener(this);

//        StatusBarCompat.setStatusBarColorForCollapsingToolbar(mainActivity, appBarLayout,
//                collapsingToolbarLayout, toolbar, getResources().getColor(R.color
// .float_transparent));

        nestedScrollView = view.findViewById(R.id.nestScrollview);
        //nestedScrollView.setNestedScrollingEnabled(false);
        recyclerView = view.findViewById(R.id.weather_list);
        horizontalRecyclerView = view.findViewById(R.id
                .horizontal_weather_list);
        gridRecyclerView = view.findViewById(R.id.life_style_recycler);
        pullToRefreshView = view.findViewById(R.id.swipe_refresh);
        todayLinearLayout = collapsingToolbarLayout.findViewById(R.id.ll_today);
        imgToday = todayLinearLayout.findViewById(R.id.img_icon);
        tvTitle = todayLinearLayout.findViewById(R.id.tv_title);
        /*tvMessage = todayLinearLayout.findViewById(R.id.tv_message);
        tvDetails = todayLinearLayout.findViewById(R.id.tv_details);*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        Logger.e("inflate menu");
    }


    private void initEvent() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayoutStateChangedListener() {
            @Override
            public void onAppBarStateChangedListener(AppBarLayout appBarLayout, State state, int
                    verticalOffset
            ) {
                if (state == State.COLLAPSE) {//-360
                    pullToRefreshView.setEnabled(false);
                    //StatusBarCompat.translucentStatusBar(mainActivity, true);
                    mainActivity.getWindow().getDecorView().setSystemUiVisibility(View
                            .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                            .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } else if (state == State.EXPEND) {//0
                    pullToRefreshView.setEnabled(true);
                    //StatusBarCompat.translucentStatusBar(mainActivity, true);
                } else {
                    //设置statusbar 背景颜色
                    mainActivity.getWindow().getDecorView().setSystemUiVisibility(View
                            .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                            .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

                }
            }
        });
        pullToRefreshView.setOnRefreshListener(new PullRefreshView.OnRefreshListener() {
            @Override
            public void onRefreshFinish() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            requestWeatherData(allWeatherUrl, REQUEST_FORECAST_WEATHER_DATA);
                            requestWeatherData(ariUrl, REQUEST_7FORECAST_WEATHER_DATA);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onLoadMoreFinish() {

            }
        });
        //adapter.setOnRecyclerItemClickListener(this);
    }

    private void initData() {
        aCache = ACache.get(getContext());
        if (isNeedReset) {
            weatherBasic = null;
            weatherNowData = null;
            lifeStyleData = null;
            dailyForecastList.clear();
            allWeatherData = null;
            isNeedReset = false;
            lifeStyleDataList.clear();
        }
        allWeatherUrl = ALL_WEATHER + LOCATION_AUTO + location + "&" + KEY;

        ariUrl = "http://api.k780.com/?app=weather" +
                ".future&weaid=" + location.substring(2, location.length()) +
                "&appkey=33924&sign=08ee33557c361a6ddc46db72d5b922f6" +
                "&format=json";
        Logger.e("ariurl = " + ariUrl);
        if (oneDayInForecast7List == null) {
            requestWeatherData(ariUrl, REQUEST_7FORECAST_WEATHER_DATA);
        } else {
            adapter = new WeatherAdapter(mainActivity, oneDayInForecast7List);
            recyclerView.setAdapter(adapter);
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1) {
            @Override
            public boolean canScrollVertically() {
                return super.canScrollVertically();
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout
                .VERTICAL));

        XmlParasUtils xpu = XmlParasUtils.getInstance(getContext());
        Logger.e("initData() allWeatherUrl: " + allWeatherUrl);
        mainActivity.setActionBarTitle(xpu.getCityNameFromCNId(location));
        allWeatherData = (AllWeatherData) aCache.getAsObject("allWeatherData");
        if (allWeatherData == null) {
            requestWeatherData(allWeatherUrl, REQUEST_FORECAST_WEATHER_DATA);
        } else {
            weatherBasic = allWeatherData.getBasic();
            dailyForecastList = allWeatherData.getDayForecastList();
            weatherNowData = allWeatherData.getWeatherNowData();
            lifeStyleDataHashMap = allWeatherData.getliftStyleDataMap();
            lifeStyleDataList = new ArrayList<>(lifeStyleDataHashMap.values());

            initHoriAdapter();

            initLifeStyleAdapter();
        }


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setNestedScrollingEnabled(false);

        GridLayoutManager gridManager = new GridLayoutManager(getActivity(), 4);
        gridManager.setOrientation(GridLayoutManager.VERTICAL);
        gridRecyclerView.setLayoutManager(gridManager);
        gridRecyclerView.setNestedScrollingEnabled(false);

        imageUtils = ImageUtils.getInstance(getContext());
        Logger.e("weatherNowData:" + weatherNowData);

        if (weatherBasic != null) {
            toolBarTitle.setText(weatherBasic.getSimpleLocation());
        }

        if (weatherNowData != null) {
            //Logger.e("weatherNowData:" + weatherNowData.toString());
            pullToRefreshView.setVisibility(View.VISIBLE);
            //todayLinearLayout.setVisibility(View.VISIBLE);
            tvTitle.setText(weatherNowData.getCond_txt());
            toolbar.setTitle(weatherNowData.getMessage());
            initTodayView();
        }
        //forecast weather data
        /*if (dailyForecastList.size() != 0) {
            pullToRefreshView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            horizontalRecyclerView.setVisibility(View.VISIBLE);
        }
        if (lifeStyleDataList != null && lifeStyleDataList.size() != 0) {
            gridRecyclerView.setVisibility(View.VISIBLE);
        }*/
    }

    private void initHoriAdapter() {
        horiAdapter = new HorizontalWeatherAdapter(getContext(), dailyForecastList);
        horiAdapter.setOnRecyclerItemClickListener(TabFragment.this);
        horizontalRecyclerView.setAdapter(horiAdapter);
    }

    private void initLifeStyleAdapter() {
        lifeStyleAdapter = new LifeStyleAdapter(getActivity(), lifeStyleDataList);
        gridRecyclerView.setAdapter(lifeStyleAdapter);
        lifeStyleAdapter.setOnItemClickerListener(TabFragment.this);
    }

    //init weather view now
    private void initTodayView() {
        if (weatherNowData != null) {
            Bitmap bitmap = imageUtils.getBitmap("w" + weatherNowData.getCond_code() + "" +
                    ".png");
            imgToday.setImageBitmap(bitmap);
            collapsingToolbarLayout.setTitle(weatherNowData.getMessage());
        }
    }

    /**
     * @param url 需要请求的url
     * @param
     */
    private void requestWeatherData(final String url, final int requestFlag) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Logger.e("request data failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    doWithResponse(response, requestFlag);
                }

            }
        });

    }

    private void doWithResponse(Response response, int requestFlag) throws IOException {

        String jsonString = getJsonStringFromResponse(response);

        //Log.e("zhangdi", "get weather data success data = " + data);
        if (REQUEST_FORECAST_WEATHER_DATA == requestFlag) {
            Log.e("zhangdi", "all weather data jsonString:" + jsonString);
            allWeatherData = JsonParasUtils.parasWeatherData(jsonString);
            Logger.e("error:" + allWeatherData);
            if (allWeatherData == null) return;
            Logger.e(allWeatherData.toString());
            //aCache.put("allWeatherData", (Serializable) allWeatherData);
            weatherBasic = allWeatherData.getBasic();
            dailyForecastList = allWeatherData.getDayForecastList();
            weatherNowData = allWeatherData.getWeatherNowData();
            lifeStyleDataHashMap = allWeatherData.getliftStyleDataMap();
            if (lifeStyleDataList == null)
                lifeStyleDataList = new ArrayList<>(lifeStyleDataHashMap.values());
            else {
                lifeStyleDataList.clear();
                lifeStyleDataList.addAll(lifeStyleDataHashMap.values());
            }
        } else if (REQUEST_7FORECAST_WEATHER_DATA == requestFlag) {
            Log.e("zhangdi", "hourly weather data jsonString11:" + jsonString);
            oneDayInForecast7List = JsonParasUtils.parasAllForecastArray(jsonString);
            Log.e("zhangdi", "oneDayInForecast7List.size:" + oneDayInForecast7List.size());
        }
        mHandler.sendEmptyMessage(requestFlag);
    }

    private String getJsonStringFromResponse(Response response) throws IOException {
        InputStream is = response.body().byteStream();
        byte[] buffer = new byte[4096];
        int len;
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = is.read(buffer)) > 0) {
            String info = new String(buffer, "UTF-8");
            Logger.e("string in json = " + info);
            stringBuilder.append(info);
        }
        return stringBuilder.toString();
    }

    @Override
    public void onItemClick(View view, int position) {
        Logger.e("onItemClick view:" + view.getParent());
        Toast.makeText(mainActivity, "position:" + position, Toast.LENGTH_SHORT).show();
        LifeStyleData data = lifeStyleDataList.get(position);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("lifedata", data);
        if (weatherBasic != null) {
            bundle.putString("address", weatherBasic.getSimpleLocation());
        }
        intent.putExtra("lifeStyleData",bundle);
        intent.setClass(mainActivity,LifeStyleActivity.class);
        mainActivity.startActivity(intent);
        /*FragmentTransaction ft = getFragmentManager().beginTransaction();
        LifeStyleDetailsFragment lsdf = new LifeStyleDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("lifedata", data);
        if (weatherBasic != null) {
            bundle.putString("address", weatherBasic.getSimpleLocation());
        }

        lsdf.setArguments(bundle);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.replace(R.id.main_content, lsdf);
        ft.addToBackStack(null);
        ft.commit();*/
    }

    @Override
    public void onItemAddButtonClicked(View view, String adcode) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("dailyForecastList", (ArrayList<? extends Parcelable>)
                dailyForecastList);
        outState.putBundle("localbundle", this.priBundle);
        super.onSaveInstanceState(outState);
        Logger.e(this.getClass() + " onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("zhangdi", "onPause()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("zhangdi", "onStop()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("zhangdi", "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("zhangdi", "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("zhangdi", "onDetach()");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent();
                intent.setClass(getActivity(), SearchActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_CODE);
                break;
        }
        return false;
    }
}
