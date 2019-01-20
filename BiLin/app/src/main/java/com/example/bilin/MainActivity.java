package com.example.bilin;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.bilin.utils.ACache;
import com.example.bilin.utils.AmapLocationUtils;
import com.example.bilin.utils.Logger;
import com.example.bilin.utils.XmlParasUtils;
import com.example.bilin.views.MainBottomCustomerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener,
        MenuItem.OnActionExpandListener {

    private static final String TAG = "mainActivity";

    public static final int REQUEST_CODE = 0x1;
    public static final int RESULT_SEARCHITEM_OK = 0x2;
    public static final int RESULT_SEARCHITEM_BUTTON_OK = 0x3;
    public SweetAlertDialog progressDialog;
    private ViewPager mViewPager;
    private SearchView searchView;
    private FragmentPagerAdapter adapter;
    private MainBottomCustomerView tabContact, tabFriends, tabFound, tabMe;
    //search widget fragement
    private FrameLayout mainView;
    private List<TabFragment> tabs = new ArrayList<>();
    private List<MainBottomCustomerView> mTabIndicators = new ArrayList<>();
    //保留fragment的Tag
    private List<String> tags = new ArrayList<>();
    //防止快速滑动底部view，导致其他tab存在残留透明度
    private boolean isTabclick;
    private int currentIndicatorPositon = 0;
    private boolean isSearchViewClose;
    private boolean[] fragmentNeedUpdate = {false, false, false, false};
    private XmlParasUtils xpu;
    private Bundle bundle;
    private int needUpdatePostion;
    private AmapLocationUtils amapLocationUtils;
    //save postion ---> title
    private List<String> locationList = new ArrayList<>();
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ACache aCache;
    private boolean isLocationChanged = false;
    private String defaultLocation = "CN101010300";//北京/朝阳
    private BroadcastReceiver loactionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AmapLocationUtils.LOCATION_CHANGED_ACTION.equals(action)) {
                if (amapLocationUtils != null) {
                    String lastLocation = aCache.getAsString("lastLocation");
                    defaultLocation = intent.getStringExtra("cnid");
                    Log.e(TAG,"amapLocationUtils --> CNId:" + amapLocationUtils.getCNId());
                    /*if (amapLocationUtils.getCNId() != null && !"".equals(amapLocationUtils
                            .getCNId())) {
                        isLocationChanged = true;
                        defaultLocation = amapLocationUtils.getCNId();
                    } else {
                        isLocationChanged = false;
                        //initLocaion();
                        Toast.makeText(MainActivity.this, "cnid = " + amapLocationUtils.getCNId()
                                , Toast.LENGTH_SHORT).show();
                        //return;
                        defaultLocation = "CN101010300";
                        aCache.put("lastLocation", defaultLocation);
                    }*/
                    aCache.put("lastLocation", defaultLocation);

                    Log.e(TAG,"lastLocation:" + lastLocation);
                    Log.e(TAG,"defaultLocation = " + defaultLocation + ",amapLocationUtils.getCNId" +
                            "() = " + amapLocationUtils.getCNId());
                    if ((!"".equals(lastLocation)) && lastLocation != null) {
                        if (!lastLocation.equals(defaultLocation)) {
                            showLocationChangeAlert();
                            amapLocationUtils.stopLoction();
                            return;
                        } else {
                            amapLocationUtils.stopLoction();
                        }
                    }
                    initData();
                    initEvent();
                    //showLocationChangeAlert();
                } else {
                    Log.e(TAG,"amapLocationUtils not instant");
                }
            }
        }
    };

    public void cancelProgressDialog() {
        progressDialog.dismissWithAnimation();
    }

    private void showLocationChangeAlert() {
        SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this,
                SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText("所在城市 " + amapLocationUtils.getLocation());
        dialog.setConfirmText("切吧");
        dialog.setCancelText("朕不想切换");
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                String CNId = defaultLocation;
                Log.e(TAG,"SweetAlertDialog cnid = " + CNId);
                Bundle bundle = new Bundle();
                bundle.putString("location", CNId);
                bundle.putBoolean("isNeedReset", true);
                notifyFragmentByPosition(0, bundle);
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        dialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        dialog.showCancelButton(true);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setOverflowButtonAlways();

        setContentView(R.layout.activity_main);
        StatusBarCompat.setStatusBarColor(this, getColor(R.color.float_transparent));
        Log.e(TAG,"MainActivity onCreate savedInstanceState =" + savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View
                .SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (savedInstanceState != null) {
            String string = savedInstanceState.getString("test");
            Log.e(TAG,"onCreate --> string: " + string);
        }
//        sp = getSharedPreferences("bilinPreference", Context.MODE_PRIVATE);
//        editor = sp.edit();
        aCache = ACache.get(this);

        initView();
        checkPermission();
        IntentFilter locationFilter = new IntentFilter();
        locationFilter.addAction(AmapLocationUtils.LOCATION_CHANGED_ACTION);
        registerReceiver(loactionReceiver, locationFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (0x666 == requestCode) {
            boolean granted = true;
            boolean mShowPermission = true;
            for (int counter = 0; counter < permissions.length; counter++) {
                granted = granted && (grantResults[counter] == PackageManager.PERMISSION_GRANTED);
                if (grantResults[counter] != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mShowPermission = mShowPermission &&
                                shouldShowRequestPermissionRationale(permissions[counter]);
                    }
                }
                Log.e(TAG,"<onRequestPermissionsResult1>" + granted + mShowPermission);
            }
            if (granted) {
                initLocaion();
            } else {
                finish();
                Toast.makeText(this, "permission denied!", Toast.LENGTH_SHORT).show();
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int coarseLocationPermission = checkSelfPermission(Manifest.permission
                    .ACCESS_COARSE_LOCATION);
            int fineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int writeExStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int readExStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int readPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);

            boolean mRequest = false;
            List<String> mPermissionStrings = new ArrayList<String>();
            if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
                mPermissionStrings.add(Manifest.permission.ACCESS_COARSE_LOCATION);
                mRequest = true;
            }
            if (readPhoneState != PackageManager.PERMISSION_GRANTED) {
                mPermissionStrings.add(Manifest.permission.READ_PHONE_STATE);
                mRequest = true;
            }
            if (fineLocation != PackageManager.PERMISSION_GRANTED) {
                mPermissionStrings.add(Manifest.permission.ACCESS_FINE_LOCATION);
                mRequest = true;
            }
            if (writeExStorage != PackageManager.PERMISSION_GRANTED) {
                mPermissionStrings.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                mRequest = true;
            }
            if (readExStorage != PackageManager.PERMISSION_GRANTED) {
                mPermissionStrings.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                mRequest = true;
            }
            if (mRequest) {
                String[] mPermissions = new String[mPermissionStrings.size()];
                mPermissions = mPermissionStrings.toArray(mPermissions);
                requestPermissions(mPermissions, 0x666);
                return;
            }
            initLocaion();
//            initData();
//            initEvent();
        }
    }

    private void initLocaion() {
        amapLocationUtils = new AmapLocationUtils(getApplicationContext());
        amapLocationUtils.startLoction();
    }

    private void initData() {

        //Set<String> locationSet = sp.getStringSet("locationSet", null);
        List<String> cacheLocationList = (List<String>) aCache.getAsObject("locationList");
        tabs.clear();
        if (cacheLocationList != null && cacheLocationList.size() > 0) {
            //ArrayList<String> locationList = new ArrayList<>(locationSet);
            for (int i = 0; i < cacheLocationList.size(); i++) {
                Log.e(TAG,"cacheLocationList.get(" + i + ") = " + cacheLocationList.get(i));
                addTabFragment(cacheLocationList.get(i));
            }
        } else {
            Log.e(TAG,"zhangdi,amapLocationUtils.getCNId() = " + amapLocationUtils.getCNId());
            addTabFragment(defaultLocation);
        }
        Log.e(TAG,"currentPosition:" + currentIndicatorPositon + "---->" + amapLocationUtils
                .getLocation());
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.e(TAG,"getItem position:" + position);
                return tabs.get(position);
            }

            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public int getItemPosition(Object object) {
                Log.e(TAG,"getItemPosition needUpdatePostion:" + needUpdatePostion);
                if (needUpdatePostion != -1) {
                    Log.e(TAG,"getItemPosition fragmentNeedUpdate[needUpdatePostion % " +
                            "fragmentNeedUpdate.length]:" +
                            fragmentNeedUpdate[needUpdatePostion % fragmentNeedUpdate.length]);
                }
                if (needUpdatePostion != -1 && fragmentNeedUpdate[needUpdatePostion %
                        fragmentNeedUpdate.length]) {
                    needUpdatePostion = -1;
                    return POSITION_NONE;
                }
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                Fragment fragment = (Fragment) super.instantiateItem(container, position);
                String tag = fragment.getTag();
                tags.add(position, tag);
                Log.e(TAG,"instantiateItem position:" + position);
                Log.e(TAG,"fragmentNeedUpdate[position % fragmentNeedUpdate.length]:" +
                        fragmentNeedUpdate[position % fragmentNeedUpdate.length]);
                if (fragmentNeedUpdate[position % fragmentNeedUpdate.length]) {
                    //need update arguments
                    tabs.get(position).updataArguments(bundle);
                    fragmentNeedUpdate[position % fragmentNeedUpdate.length] = false;
                    Log.e(TAG,"position:" + position + ",tag:" + tag);
                }
                return fragment;
            }
        };
        mViewPager.setAdapter(adapter);
    }

    public void addTabFragment(String CNId) {
        TabFragment tabFragment = new TabFragment();
        Bundle tabBundle = new Bundle();
        tabBundle.putString("location", CNId);
        tabFragment.setArguments(tabBundle);
        tabs.add(tabFragment);
        if (locationList.indexOf(CNId) == -1) {
            locationList.add(CNId);
            aCache.put("locationList", (Serializable) locationList);
        }
    }

    /**
     * 初始化事件
     */
    private void initEvent() {
        for (MainBottomCustomerView tab : mTabIndicators) {
            tab.setOnClickListener(this);
        }
        mViewPager.addOnPageChangeListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG,"onActivityResult resultCode = " + resultCode + ",requestCode = " + requestCode);
        switch (resultCode) {
            case RESULT_SEARCHITEM_OK:
                bundle = data.getBundleExtra("searchItem");
                Log.e(TAG,"onActivityResult bundle = " + bundle);
                notifyFragmentByPosition(currentIndicatorPositon, bundle);
                break;
            case RESULT_SEARCHITEM_BUTTON_OK:
                bundle = data.getBundleExtra("searchItemButton");
                String adcode = bundle.getString("adcode");
                Toast.makeText(this, "click button adcode:" + adcode, Toast.LENGTH_SHORT).show();
                String CNId = amapLocationUtils.getCNId(adcode);
                addTabFragment(CNId);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public void notifyFragmentByPosition(int position, Bundle bundle) {
        Log.e(TAG,"notifyFragmentByPosition position:" + position + ",tags.size = " + tags.size());
        //tags.remove(position);
        this.bundle = bundle;
        Log.e(TAG,"notifyFragmentByPosition: bundle=" + bundle);
        needUpdatePostion = position;
        fragmentNeedUpdate[position % fragmentNeedUpdate.length] = true;

        //addTabFragment((String) bundle.get("location"));
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        mViewPager = findViewById(R.id.id_viewpager);
        mainView = findViewById(R.id.ll_mainView);

        tabContact = findViewById(R.id.tab_indicator_one);
        tabFriends = findViewById(R.id.tab_indicator_two);
        tabFound = findViewById(R.id.tab_indicator_three);
        tabMe = findViewById(R.id.tab_indicator_four);

        mTabIndicators.add(tabContact);
        mTabIndicators.add(tabFriends);
        mTabIndicators.add(tabFound);
        mTabIndicators.add(tabMe);

        tabContact.setIconAlpha(1.0f);
        progressDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        progressDialog.setContentText("loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.e(TAG,getLocalClassName() + " onSaveInstanceState");
        outState.putString("test", "test");
        super.onSaveInstanceState(outState);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_search) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SearchActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/

    /**
     * 使用setOptionalIconsVisible  对menu设置图片
     *
     * @param featureId
     * @param menu
     * @return
     */
    /*@Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                            Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }*/

    /**
     * 利用ViewConfiguration 对actionbar 中的menukey进行显示
     */

    /*private void setOverflowButtonAlways() {
        ViewConfiguration configuration = ViewConfiguration.get(this);
        try {
            Field menuKey = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(configuration, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/
    @Override
    public void onClick(View v) {
        if (v instanceof MainBottomCustomerView) {
            isTabclick = true;
            ClickTabs(v);
        }

    }

    //重置其他底部icon颜色
    private void resetOtherTabs() {
        for (MainBottomCustomerView tab : mTabIndicators) {
            tab.setIconAlpha(0f);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        /**
         * 当pager从左向右划position 从0 ~ 1 positionOffset 从 0.0  ~ 1.0
         * 当pager从右向左划 position从1 ~ 0 postionOffset 从 l.0 - 0.0
         */
        //Log.e("zhangdi", "position = " + position);

        /*if (!isTabclick && positionOffset > 0) {

            MainBottomCustomerView left = mTabIndicators.get(position);
            MainBottomCustomerView right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }*/
    }

    public void setActionBarTitle(String title) {
        //获取actionBar title id
        /*int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
        if (titleId <= 0) return;
        TextView titleView = findViewById(titleId);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        titleView.setWidth(displayMetrics.widthPixels);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) titleView.getLayoutParams();
        titleView.setText(title);
        titleView.setLayoutParams(lp);
        titleView.setGravity(Gravity.CENTER);*/
    }

    @Override
    public void onPageSelected(int position) {
        currentIndicatorPositon = position;
        //setActionBarTitle(actionBarTitle.get(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
//        Log.e("zhangdi", "state = " + state);
        if (0 == state) {
            isTabclick = false;
        }
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        Log.e(TAG,"seacch view onMenuItemActionExpand");
        //hideBottomView();
//        searchFragment = new SearchFragment();
//        searchFragment.setSearchItemClickListener(this);
//        showFragmentInsteadViewPager(searchFragment);
//        isSearchViewClose = false;
//        Intent intent = new Intent();
//        intent.setClass(this, SearchActivity.class);
//        startActivityForResult(intent, REQUEST_CODE);
        return true;
    }


    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        Log.e(TAG,"seacch view onMenuItemActionCollapse");
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(searchFragment);
//        fragmentTransaction.commit();
//        showBottomView();
//        isSearchViewClose = true;
        return true;
    }

    private void showFragmentInsteadViewPager(Fragment fragment) {
        Log.e(TAG,"showFragmentInsteadViewPager");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ll_mainView, fragment);
        fragmentTransaction.commit();
    }

    private void ClickTabs(View v) {
        resetOtherTabs();
        switch (v.getId()) {
            case R.id.tab_indicator_one:
                mTabIndicators.get(0).setIconAlpha(1);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.tab_indicator_two:
                mTabIndicators.get(1).setIconAlpha(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.tab_indicator_three:
                mTabIndicators.get(2).setIconAlpha(1);
                mViewPager.setCurrentItem(2);
                break;
            case R.id.tab_indicator_four:
                mTabIndicators.get(3).setIconAlpha(1);
                mViewPager.setCurrentItem(3);
                break;
        }
    }

    private void hideBottomView() {
        mainView.setVisibility(View.GONE);
    }

    private void showBottomView() {
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loactionReceiver != null)
            unregisterReceiver(loactionReceiver);
    }

}
