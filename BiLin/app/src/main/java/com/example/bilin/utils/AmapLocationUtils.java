package com.example.bilin.utils;

import android.content.Context;
import android.content.Intent;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.List;

/**
 * Created by zhangdi on 4/26/18.
 */
public class AmapLocationUtils {
    public static final String LOCATION_CHANGED_ACTION = "com.example.bilin.LOCATION_CHANGED";
    public double latitude, longitude;
    String url = "https://search.heweather.com/find?";
    String key = "key=ad67d665f5d84555bd59c220be248bf0";
    private Context mContext;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private AMapLocation mAMapLocation;
    private XmlParasUtils xmlParasUtils;
    private String location;

    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Logger.e(aMapLocation.toStr());
                    mAMapLocation = aMapLocation;
                    latitude = aMapLocation.getLatitude();
                    longitude = aMapLocation.getLongitude();
                    location = aMapLocation.getCity() + aMapLocation.getDistrict();
                    String cnid = getCNId();
                    Logger.e("latitude" + latitude + ",longitude:" + longitude + ",location = " +
                            location + "cnid = " + cnid);
                    if (cnid != null && !"".equals(cnid)) {
                        Intent intent = new Intent(LOCATION_CHANGED_ACTION);
                        intent.putExtra("cnid",cnid);
                        mContext.sendBroadcast(intent);
                    }else{
                        Logger.e("gps location has some error,maybe cnid is null or \" \"");
                    }
                } else {
                    Logger.e("location geterror code " + aMapLocation.getErrorCode() + "|||error " +
                            "info:" + aMapLocation.getErrorInfo());
                }

            }
        }
    };

    public AmapLocationUtils(Context context) {
        this.mContext = context;
        xmlParasUtils = XmlParasUtils.getInstance(mContext);
        aMapLocationClient = new AMapLocationClient(context);
        aMapLocationClient.setLocationListener(aMapLocationListener);
        initAmapLocationOption();
    }
    //defaule high accuracy mode

    public String getLocation() {
        return this.location;
    }

    /**
     * 初始化定位场景
     * 签到
     * 运动
     * 出行
     * 默认为无场景
     */
    private void initAmapLocationOption() {
        aMapLocationClientOption = new AMapLocationClientOption();
        //default three modes signIn sport  transport
        /*aMapLocationClientOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose
                .SignIn);*/
//        //设置定位信息cache
        aMapLocationClientOption.setLocationCacheEnable(false);
        //设置最近三秒内最精确的位置信息，并且设置单次定位
        aMapLocationClientOption.setOnceLocationLatest(true);
        //设置连续定位间隔，最少1000ms，default 2000ms
        aMapLocationClientOption.setInterval(5000);
        //return address desc
        aMapLocationClientOption.setNeedAddress(true);
        if (aMapLocationClient != null) {
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
        }
    }

    /**
     * 高精度定位模式：会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
     * 低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位
     * 仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位
     *
     * @param mode
     */
    public void setLocationMode(AMapLocationClientOption.AMapLocationMode mode) {
        if (aMapLocationClientOption != null) {
            aMapLocationClientOption.setLocationMode(mode);
            aMapLocationClient.stopLocation();
            aMapLocationClient.startLocation();
        }
    }

    public void startLoction() {
        if (aMapLocationClient != null) {
            if (!aMapLocationClient.isStarted()) {
                aMapLocationClient.stopLocation();
                aMapLocationClient.startLocation();
            }

        }
    }

    public void stopLoction() {
        if (aMapLocationClient != null) {
            if (aMapLocationClient.isStarted())
                aMapLocationClient.stopLocation();
        }
    }

    /**
     * 获取当前所在城市的weatherId
     *
     * @return
     */
    public String getCNId() {
        if (mAMapLocation != null) {
            String locationCode = mAMapLocation.getAdCode().trim();
            List<String> adCodeList = xmlParasUtils.getAdCodeList();
            Logger.e("AmaplocationgetCNIddistrict:" + locationCode + ",adCodeList.size:" +
                    adCodeList.size());
            for (int i = 0; i < adCodeList.size(); i++) {
                String adcode = adCodeList.get(i);
                if (locationCode != null && adcode != null && locationCode.equals(adcode.trim())) {
                    Logger.e("Amaplocation  getCNId name22 :" + adcode);
                    return xmlParasUtils.getCNIdFromAdcode(adcode);
                }
            }
        } else {
            Logger.e("location is undefined");
        }
        return "";
    }

    /**
     * @param adcode 地址代码
     * @return 地址代码所对应的weatherId
     */
    public String getCNId(String adcode) {
        return xmlParasUtils != null ? xmlParasUtils.getCNIdFromAdcode(adcode) : "";
    }

}
