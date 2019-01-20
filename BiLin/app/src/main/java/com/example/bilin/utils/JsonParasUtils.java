package com.example.bilin.utils;


import android.util.Log;

import com.example.bilin.bean.AllWeatherData;
import com.example.bilin.bean.LifeStyleData;
import com.example.bilin.bean.OneDayInForecast7;
import com.example.bilin.bean.WeatherBasic;
import com.example.bilin.bean.WeatherDailyForecast;
import com.example.bilin.bean.WeatherHourlyForecast;
import com.example.bilin.bean.WeatherNowData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangdi on 2018/4/6.
 */

public class JsonParasUtils {


    /**
     * @param json json string
     * @return Object AllWeatherData
     */
    public static AllWeatherData parasWeatherData(String json) {
        AllWeatherData allWeatherData = null;
        try {
            JSONObject total = new JSONObject(json);
            JSONArray heWeather6 = total.getJSONArray("HeWeather6");
            String dataStatus = (String) heWeather6.getJSONObject(0).get("status");
            if ("ok".equals(dataStatus)) {
                allWeatherData = new AllWeatherData();
                for (int i = 0; i < heWeather6.length(); i++) {
                    JSONObject object = heWeather6.getJSONObject(i);
                    //basic
                    JSONObject basic = object.getJSONObject("basic");
                    allWeatherData.setBasic(parasWeatherBasicObj(basic));
                    //update
                    JSONObject update = object.getJSONObject("update");
                    String updateTime = update.getString("loc");
                    allWeatherData.setUpdateTime(updateTime);
                    //now
                    JSONObject now = object.getJSONObject("now");
                    Logger.e("now:" + now.toString());
                    allWeatherData.setWeatherNowData(parasWeatherNowData(now));
                    //daily_forecast
                    JSONArray dailyForecast = object.getJSONArray("daily_forecast");
                    allWeatherData.setDayForecastList(parasDailyForecastArray(dailyForecast));
                    //lifestyle
                    JSONArray lifeStyleArray = object.getJSONArray("lifestyle");
                    Logger.e("lifeStyleArray length = " + lifeStyleArray);
                    allWeatherData.setliftStyleDataMap(paresLifeStyleArray(lifeStyleArray));
                    //hourly
//                    JSONArray hourlyForecast = object.getJSONArray("hourly");
//                    Logger.e("hourlyforecast length = "+hourlyForecast);
//                    allWeatherData.setHourlyForecastList(parasHourlyForecastArray
// (hourlyForecast));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allWeatherData;
    }

    /*"cond_code": "501",
    "cond_txt": "雾",
    "fl": "8",
    "hum": "94",
    "pcpn": "0",
    "pres": "1018",
    "tmp": "9",
    "vis": "2",
    "wind_deg": "48",
    "wind_dir": "东北风",
    "wind_sc": "微风",
    "wind_spd": "7"*/
    private static WeatherNowData parasWeatherNowData(JSONObject now) {
        WeatherNowData data = null;
        try {
            data = new WeatherNowData();
            data.setCond_code((String) now.get("cond_code"));
            data.setCond_txt((String) now.get("cond_txt"));
            data.setFl((String) now.get("fl"));
            data.setTmp(now.getString("tmp"));
            data.setHum((String) now.get("hum"));
            data.setPcpn((String) now.get("pcpn"));
            data.setPres(now.getString("pres"));
            data.setVis((String) now.get("vis"));
            data.setWind_deg(now.getString("wind_deg"));
            data.setWind_dir((String) now.get("wind_dir"));
            data.setWind_sc((String) now.get("wind_sc"));
            data.setWind_spd((String) now.get("wind_spd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*"cloud": "8",
    "cond_code": "100",
    "cond_txt": "晴",
    "hum": "84",
    "pop": "0",
    "pres": "1018",
    "time": "2017-10-27 01:00",
    "tmp": "8",
    "wind_deg": "49",
    "wind_dir": "东北风",
    "wind_sc": "微风",
    "wind_spd": "2"*/
    private static List<WeatherHourlyForecast> parasHourlyForecastArray(JSONArray
                                                                                hourlyForecastArray) {
        Logger.e("parasHourlyForecastArray ----> hourlyForecastArray length=" +
                hourlyForecastArray.length());
        List<WeatherHourlyForecast> list = new ArrayList<>();
        for (int i = 0; i < hourlyForecastArray.length(); i++) {
            WeatherHourlyForecast hourlyForecast = new WeatherHourlyForecast();
            try {
                JSONObject object = hourlyForecastArray.getJSONObject(i);

                hourlyForecast.setCloud(object.getString("cloud"));
                hourlyForecast.setCond_code(object.getString("cond_code"));
                hourlyForecast.setCond_txt(object.getString("cond_txt"));
                hourlyForecast.setHum(object.getString("hum"));
                hourlyForecast.setPop(object.getString("pop"));
                hourlyForecast.setPres(object.getString("pres"));
                hourlyForecast.setTime(object.getString("time"));
                hourlyForecast.setTmp(object.getString("tmp"));
                hourlyForecast.setWind_deg(object.getString("wind_deg"));
                hourlyForecast.setWind_dir(object.getString("wind_dir"));
                hourlyForecast.setWind_sc(object.getString("wind_sc"));
                hourlyForecast.setWind_spd(object.getString("wind_spd"));

                list.add(i, hourlyForecast);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        Logger.e("hourly list:" + list.size());
        return list;
    }

    /*"cid": "CN101010100",
    "location": "北京",
    "parent_city": "北京",
    "admin_area": "北京",
    "cnty": "中国",
    "lat": "39.90498734",
    "lon": "116.40528870",
    "tz": "8.0"*/
    private static WeatherBasic parasWeatherBasicObj(JSONObject basic) {
        WeatherBasic weatherBasic = new WeatherBasic();
        try {
            weatherBasic.setCid(basic.getString("cid"));
            weatherBasic.setLocation(basic.getString("location"));
            weatherBasic.setParent_city(basic.getString("parent_city"));
            weatherBasic.setAdmin_area(basic.getString("admin_area"));
            weatherBasic.setCnty(basic.getString("cnty"));
            weatherBasic.setLat(basic.getString("lat"));
            weatherBasic.setLon(basic.getString("lon"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weatherBasic;
    }

    private static List<WeatherDailyForecast> parasDailyForecastArray(JSONArray dailyForecast) {
        Logger.e("parasDailyForecastArray ----> dailyForecast length=" + dailyForecast.length());
        List<WeatherDailyForecast> list = new ArrayList<>();
        for (int i = 0; i < dailyForecast.length(); i++) {
            WeatherDailyForecast dailyForecastItem = new WeatherDailyForecast();
            try {
                JSONObject object = dailyForecast.getJSONObject(i);
                /*"cond_code_d": "103",
                "cond_code_n": "101",
                "cond_txt_d": "晴间多云",
                "cond_txt_n": "多云",
                "date": "2017-10-26",
                "hum": "57",
                mr
                ms
                "pcpn": "0.0",
                "pop": "0",
                "pres": "1020",
                sr
                ss
                "tmp_max": "16",
                "tmp_min": "8",
                "uv_index": "3",
                "vis": "16",
                "wind_deg": "0",
                "wind_dir": "无持续风向",
                "wind_sc": "微风",
                "wind_spd": "5"*/
                dailyForecastItem.setCond_code_d(object.getString("cond_code_d"));
                dailyForecastItem.setCond_code_n(object.getString("cond_code_n"));
                dailyForecastItem.setCond_txt_d(object.getString("cond_txt_d"));
                dailyForecastItem.setCond_txt_n(object.getString("cond_txt_n"));

                dailyForecastItem.setDate(object.getString("date"));
                dailyForecastItem.setMr(object.getString("mr"));
                dailyForecastItem.setMs(object.getString("ms"));
                dailyForecastItem.setHum(object.getString("hum"));
                dailyForecastItem.setPcpn(object.getString("pcpn"));
                dailyForecastItem.setPop(object.getString("pop"));

                dailyForecastItem.setPres(object.getString("pres"));
                dailyForecastItem.setSr(object.getString("sr"));
                dailyForecastItem.setSs(object.getString("ss"));
                dailyForecastItem.setTmp_max(object.getString("tmp_max"));
                dailyForecastItem.setTmp_min(object.getString("tmp_min"));
                dailyForecastItem.setUv_index(object.getString("uv_index"));

                dailyForecastItem.setVis(object.getString("vis"));
                dailyForecastItem.setWind_deg(object.getString("wind_deg"));
                dailyForecastItem.setWind_dir(object.getString("wind_dir"));
                dailyForecastItem.setWind_sc(object.getString("wind_sc"));
                dailyForecastItem.setWind_spd(object.getString("wind_spd"));

                list.add(i, dailyForecastItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /*"brf": "较差",
    "txt": "气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。",
    "type": "air"*/
    private static HashMap<String, LifeStyleData> paresLifeStyleArray(JSONArray lifeStyleArray) {
        HashMap<String, LifeStyleData> map = new HashMap<>();
        for (int i = 0; i < lifeStyleArray.length(); i++) {
            LifeStyleData lifeStyleData = new LifeStyleData();

            try {
                JSONObject object = lifeStyleArray.getJSONObject(i);

                lifeStyleData.setBrf(object.getString("brf"));
                lifeStyleData.setTxt(object.getString("txt"));
                lifeStyleData.setType(object.getString("type"));

                map.put(lifeStyleData.getType(), lifeStyleData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return map;
    }

    //now api
    /*"weaid": "1",
    "days": "2014-07-30",
    "week": "星期三",
    "cityno": "beijing",
    "citynm": "北京",
    "cityid": "101010100",
    "temperature": "23℃/11℃", *//*温度*//*
    "humidity": "0%/0%", *//*湿度,后期气像局未提供,如有需要可使用weather.today接口 *//*
    "weather": "多云转晴",
    "weather_icon": "http://api.k780.com/upload/weather/d/1.gif", *//*气象图标(白天) 全部气象图标下载*//*
    "weather_icon1": "http://api.k780.com/upload/weather/d/0.gif", *//*气象图标(夜间) 全部气象图标下载*//*
    "wind": "微风", *//*风向*//*
    "winp": "小于3级", *//*风力*//*
    "temp_high": "31", *//*最高温度*//*
    "temp_low": "24", *//*最低温度*//*
    "humi_high": "0", *//*湿度栏位已不再更新*//*
    "humi_low": "0",*//*湿度栏位已不再更新*//*
    "weatid": "2", *//*白天天气ID，可对照weather.wtype接口中weaid*//*
    "weatid1": "1", *//*夜间天气ID，可对照weather.wtype接口中weaid*//*
    "windid": "1", *//*风向ID(暂无对照表)*//*
    "winpid": "2" *//*风力ID(暂无对照表)*//*
    "weather_iconid": "1", *//*气象图标编号(白天),对应weather_icon 1.gif*//*
    "weather_iconid1": "0" *//*气象图标编号(夜间),对应weather_icon1 0.gif*/
    public static List<OneDayInForecast7> parasAllForecastArray(String jsonString) {
        List<OneDayInForecast7> oneDayInForecast7List = new ArrayList<>();
        try {
            Log.e("zhangdi", "parasAllForecastArray jsonString = "+jsonString);
            JSONObject jsonObject = null;
            if (jsonString != null && !"".equals(jsonString)) {
                jsonObject = new JSONObject(jsonString);
            }
            Log.e("zhangdi", "22222222222222222222222222222222222222222222222");

            //Log.i("zhangdi", "success:" + jsonObject.getString("success"));
            if (jsonObject != null && "1".equals(jsonObject.getString("success"))) {
                JSONArray array = jsonObject.getJSONArray("result");
                for (int i = 0; i < array.length(); i++) {
                    OneDayInForecast7 oneDayInForecast7 = new OneDayInForecast7();
                    JSONObject object = array.getJSONObject(i);
                    String days = object.getString("days");
                    String week = object.getString("week");
                    String temperature = object.getString("temperature");
                    String weather = object.getString("weather");
                    //白天icon
                    String weather_icon = object.getString("weather_icon");
                    //night icon
                    String weather_icon1 = object.getString("weather_icon1");
                    //风向
                    String wind = object.getString("wind");
                    //风力
                    String winp = object.getString("winp");
                    //白天天气id
                    String weatid = object.getString("weatid");
                    //夜间天气id
                    String weatid1 = object.getString("weatid1");

                    String temp_high = object.getString("temp_high");
                    String temp_low = object.getString("temp_low");
                    //天气icon id
                    String weather_iconid = object.getString("weather_iconid");
                    String weather_iconid1 = object.getString("weather_iconid1");

                    oneDayInForecast7.setDays(days);
                    oneDayInForecast7.setWeek(week);
                    oneDayInForecast7.setTemperature(temperature);
                    oneDayInForecast7.setWeather(weather);
                    oneDayInForecast7.setWeather_icon(weather_icon);
                    oneDayInForecast7.setWeather_icon1(weather_icon1);
                    oneDayInForecast7.setWind(wind);
                    oneDayInForecast7.setWinp(winp);
                    oneDayInForecast7.setWeatid(weatid);
                    oneDayInForecast7.setWeatid1(weatid1);
                    oneDayInForecast7.setTemp_high(temp_high);
                    oneDayInForecast7.setTemp_low(temp_low);
                    oneDayInForecast7.setWeather_iconid(weather_iconid);
                    oneDayInForecast7.setWeather_iconid1(weather_iconid1);
                    Logger.e("onedayInforecast = " + oneDayInForecast7.toString());
                    oneDayInForecast7List.add(i, oneDayInForecast7);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oneDayInForecast7List;
    }


    //YY url
    /*private String now_rain;
    private String now_temp;
    private String now_date;
    private String now_wind_direct;
    private String now_wind_power;
    private String now_aircomf;*/
    /*public static AllDayTimeWeatherData parasAllForecastArray(JSONObject jsonObject) {

        //Logger.e("AllDayTimeWeatherData ----> dailyForecast length=" + jsonString);
//        List<AllDayTimeWeatherData> list = new ArrayList<>();
        AllDayTimeWeatherData allDayTimeWeatherData = new AllDayTimeWeatherData();
        try {
            //JSONObject jsonObject = new JSONObject(jsonString);
            Logger.e("AllDayTimeWeatherData ----> jsonObject.get(error_code)=" + jsonObject.get
            ("error_code"));
            if (jsonObject != null && jsonObject.get("error_code").equals("0")) {

                //now
                JSONArray nowArray = jsonObject.getJSONArray("now");
                JSONObject nowObj = (JSONObject) nowArray.get(0);
                String now_rain = nowObj.getString("now_rain");
                String now_temp = nowObj.getString("now_temperature");
                String now_date = nowObj.getString("now_publish_time");
                String now_wind_direct = nowObj.getString("now_wind_direction");
                String now_wind_power = nowObj.getString("now_wind_power");
                String now_aircomf = nowObj.getString("now_rcomfort");

                allDayTimeWeatherData.setNow_rain(now_rain);
                allDayTimeWeatherData.setNow_temp(now_temp);
                allDayTimeWeatherData.setNow_date(now_date);
                allDayTimeWeatherData.setNow_wind_direct(now_wind_direct);
                allDayTimeWeatherData.setNow_wind_power(now_wind_power);
                allDayTimeWeatherData.setNow_aircomf(now_aircomf);

                //forecast7
                JSONArray forecastArray = jsonObject.getJSONArray("forecast7");
                OneDayInForecast7 oneDayInForecast7 = new OneDayInForecast7();
                JSONObject forecastObj = forecastArray.getJSONObject(0);
                JSONObject dayForecastObj = forecastObj.getJSONArray("day").getJSONObject(0);
                JSONObject nightForecastObj = forecastObj.getJSONArray("night").getJSONObject(0);

                //day
                String day_img = dayForecastObj.getString("img");
                String day_weather = dayForecastObj.getString("weather");
                String day_power = dayForecastObj.getString("power");
                String day_direction = dayForecastObj.getString("direction");
                String day_temp = dayForecastObj.getString("temprature");

                oneDayInForecast7.setDay_img(day_img);
                oneDayInForecast7.setDay_weather(day_weather);
                oneDayInForecast7.setDay_power(day_power);
                oneDayInForecast7.setDay_direction(day_direction);
                oneDayInForecast7.setDay_temp(day_temp);

                //night
                String night_img = nightForecastObj.getString("img");
                String night_weather = nightForecastObj.getString("weather");
                String night_power = nightForecastObj.getString("power");
                String night_direction = nightForecastObj.getString("direction");
                String night_temp = nightForecastObj.getString("temprature");

                oneDayInForecast7.setNight_img(night_img);
                oneDayInForecast7.setNight_weather(night_weather);
                oneDayInForecast7.setNight_power(night_power);
                oneDayInForecast7.setNight_direction(night_direction);
                oneDayInForecast7.setNight_temp(night_temp);

                //public
                String date = dayForecastObj.getString("date");
                String warn = dayForecastObj.getString("warn");
                String week = dayForecastObj.getString("week");
                String sunset = dayForecastObj.getString("sunset");
                String sunrise = dayForecastObj.getString("sunrise");
                String rcomfort = dayForecastObj.getString("rcomfort");

                oneDayInForecast7.setDate(date);
                oneDayInForecast7.setWarn(warn);
                oneDayInForecast7.setWeek(week);
                oneDayInForecast7.setSunset(sunset);
                oneDayInForecast7.setSunrise(sunrise);
                oneDayInForecast7.setRcomfort(rcomfort);
                //24HOUR
                JSONArray jsonArray24 = forecastObj.getJSONArray("hour24");
                List<OneHourData> oneHourDataList = new ArrayList<>();
                for (int i = 0; i < jsonArray24.length(); i++) {
                    JSONObject object = jsonArray24.getJSONObject(i);
                    OneHourData oneHourData = new OneHourData();
                    String time = object.getString("time");
                    String temperature = object.getString("temperature");

                    oneHourData.setTime(time);
                    oneHourData.setTemperature(temperature);

                    oneHourDataList.add(i, oneHourData);
                }
                oneDayInForecast7.setHours24Datas(oneHourDataList);
                //3HOUR
                JSONArray jsonArray3 = forecastObj.getJSONArray("hourForcast3");
                List<Hours3Forecast> hours3ForecastList = new ArrayList<>();
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject object = jsonArray3.getJSONObject(i);
                    Hours3Forecast hours3Forecast = new Hours3Forecast();

                    String weather_img = object.getString("weather");
                    String time = object.getString("time");
                    String direction = object.getString("direction");
                    String temp = object.getString("temprature");
                    String rain = object.getString("rain");

                    hours3Forecast.setweather_img(weather_img);
                    hours3Forecast.setTime(time);
                    hours3Forecast.setdirection(direction);
                    hours3Forecast.setTemp(temp);
                    hours3Forecast.setRain(rain);

                    hours3ForecastList.add(i, hours3Forecast);
                }
                oneDayInForecast7.setHours3Datas(hours3ForecastList);

                allDayTimeWeatherData.setForecast7s(oneDayInForecast7);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allDayTimeWeatherData;
    }*/
//    /*
//    {
//        "error_code":0,
//            "data":{
//        "province":"北京市",  /*省份(直辖市)*/
//                "province_py":"ABJ",
//                "city":"北京",  /*城市*/
//                "city_py":"beijing",  /*城市拼音*/
//                "city_id":"54511",  /*城市ID*/
//                "post_code":"100000",  /*邮编*/
//                "area_code":"010",  /*区号*/
//        "now":[  /*当前实况*/
//        {
//            "now_rain":"0mm",  /*降水*/
//                "now_feelst":"23.7℃",  /*体感温度*/
//                "now_humidity":"33%",  /*相对湿度*/
//                "now_icomfort":"舒适，最可接受",  /*舒适度*/
//                "now_rcomfort":"良",  /*空气质量*/
//                "now_wind_power":"微风",  /*风速*/
//                "now_airpressure":"1004hPa",  /*气压*/
//                "now_temperature":"23.3℃",  /*气温*/
//                "now_publish_time":"2018-05-04 12:35",  /*预报发布时间*/
//                "now_wind_direction":"西北风"  /*风向*/
//        }
//        ],
//        "forecast7":[  /*七天天气预报*/
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"27℃"  /*温度*/
//            }
//                ],
//            "date":"05月04日",  /*日期*/
//                "warn":"暂无预警",
//                "week":"星期五",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"16℃"  /*温度*/
//            }
//                ],
//            "hour24":[  /*24小时实况*/
//            {
//                "time":"2018-05-04 12:00",  /*时间*/
//                    "humidity":"33%",  /*相对湿度*/
//                    "pressure":"1004hPa",  /*气压*/
//                    "temperature":"22.5℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 11:00",  /*时间*/
//                    "humidity":"37%",  /*相对湿度*/
//                    "pressure":"1005hPa",  /*气压*/
//                    "temperature":"21℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 10:00",  /*时间*/
//                    "humidity":"39%",  /*相对湿度*/
//                    "pressure":"1006hPa",  /*气压*/
//                    "temperature":"19.9℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 09:00",  /*时间*/
//                    "humidity":"39%",  /*相对湿度*/
//                    "pressure":"1006hPa",  /*气压*/
//                    "temperature":"18.9℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 08:00",  /*时间*/
//                    "humidity":"42%",  /*相对湿度*/
//                    "pressure":"1007hPa",  /*气压*/
//                    "temperature":"17.1℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 07:00",  /*时间*/
//                    "humidity":"48%",  /*相对湿度*/
//                    "pressure":"1007hPa",  /*气压*/
//                    "temperature":"15.4℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 06:00",  /*时间*/
//                    "humidity":"61%",  /*相对湿度*/
//                    "pressure":"1007hPa",  /*气压*/
//                    "temperature":"12.1℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 05:00",  /*时间*/
//                    "humidity":"60%",  /*相对湿度*/
//                    "pressure":"1007hPa",  /*气压*/
//                    "temperature":"11.6℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 04:00",  /*时间*/
//                    "humidity":"59%",  /*相对湿度*/
//                    "pressure":"1008hPa",  /*气压*/
//                    "temperature":"11.4℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 03:00",  /*时间*/
//                    "humidity":"50%",  /*相对湿度*/
//                    "pressure":"1008hPa",  /*气压*/
//                    "temperature":"13.2℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 02:00",  /*时间*/
//                    "humidity":"42%",  /*相对湿度*/
//                    "pressure":"1008hPa",  /*气压*/
//                    "temperature":"15.1℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 01:00",  /*时间*/
//                    "humidity":"38%",  /*相对湿度*/
//                    "pressure":"1008hPa",  /*气压*/
//                    "temperature":"16.4℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-04 00:00",  /*时间*/
//                    "humidity":"36%",  /*相对湿度*/
//                    "pressure":"1008hPa",  /*气压*/
//                    "temperature":"17.2℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 23:00",  /*时间*/
//                    "humidity":"34%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"17.3℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 22:00",  /*时间*/
//                    "humidity":"31%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"17.8℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 21:00",  /*时间*/
//                    "humidity":"28%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"18.7℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 20:00",  /*时间*/
//                    "humidity":"25%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"19.8℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 19:00",  /*时间*/
//                    "humidity":"23%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"21.1℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 18:00",  /*时间*/
//                    "humidity":"19%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"22.4℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 17:00",  /*时间*/
//                    "humidity":"17%",  /*相对湿度*/
//                    "pressure":"1009hPa",  /*气压*/
//                    "temperature":"23.2℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 16:00",  /*时间*/
//                    "humidity":"14%",  /*相对湿度*/
//                    "pressure":"1010hPa",  /*气压*/
//                    "temperature":"23.7℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 15:00",  /*时间*/
//                    "humidity":"14%",  /*相对湿度*/
//                    "pressure":"1011hPa",  /*气压*/
//                    "temperature":"23.5℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 14:00",  /*时间*/
//                    "humidity":"12%",  /*相对湿度*/
//                    "pressure":"1012hPa",  /*气压*/
//                    "temperature":"22.8℃"  /*气温*/
//            },
//            {
//                "time":"2018-05-03 13:00",  /*时间*/
//                    "humidity":"12%",  /*相对湿度*/
//                    "pressure":"1013hPa",  /*气压*/
//                    "temperature":"22.1℃"  /*气温*/
//            }
//                ],
//            "sunset":"19:11",  /*日落时间*/
//                "sunrise":"05:12",  /*日出时间*/
//                "rcomfort":"良",  /*空气质量*/
//                "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"3米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"38.6%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"26.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"3.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"39.2%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"24.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"3%",  /*云量*/
//                    "power":"2.2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"58.6%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"21.9℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"19.5%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"61.1%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"21.9℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05日02:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"1.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"75.9%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"17.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"77.3%",  /*云量*/
//                    "power":"1.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"64.4%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"16.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"47.1%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"21.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"34%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"24.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"25℃"  /*温度*/
//            }
//                ],
//            "date":"05月05日",  /*日期*/
//                "week":"星期六",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"15℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"47.1%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"21.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"34%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"24.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"2.4米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"37.5%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"24.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"0.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"40.5%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"24.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"1.4米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"44.2%",  /*相对湿度*/
//                    "direction":"西北风",  /*风向*/
//                    "temprature":"22℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"51.4%",  /*云量*/
//                    "power":"1.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"47.9%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"20.3℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"06日02:00",  /*时间*/
//                    "cloud":"18.4%",  /*云量*/
//                    "power":"2.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"51.4%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"17.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"43.3%",  /*云量*/
//                    "power":"2.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"46.3%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"15.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"27℃"  /*温度*/
//            }
//                ],
//            "date":"05月06日",  /*日期*/
//                "week":"星期日",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"13℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"65.7%",  /*云量*/
//                    "power":"1.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"34.4%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"19.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"1.2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"18.8%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"22.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.8米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"21.3%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"25.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"0.2%",  /*云量*/
//                    "power":"2.7米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"19.9%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"26.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"0.3%",  /*云量*/
//                    "power":"3.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"41.6%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"19.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"48.4%",  /*相对湿度*/
//                    "direction":"西南风",  /*风向*/
//                    "temprature":"18.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"07日02:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.3米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"60.4%",  /*相对湿度*/
//                    "direction":"西风",  /*风向*/
//                    "temprature":"16℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"66.8%",  /*相对湿度*/
//                    "direction":"西北风",  /*风向*/
//                    "temprature":"13.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"28℃"  /*温度*/
//            }
//                ],
//            "date":"05月07日",  /*日期*/
//                "week":"星期一",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"13℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"1%",  /*云量*/
//                    "power":"0.4米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"45.2%",  /*相对湿度*/
//                    "direction":"北风",  /*风向*/
//                    "temprature":"19.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"20.2%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"24.9℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"2.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"23.6%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"27.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"3.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"24.5%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"27.3℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"3.2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"39.5%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"21.5℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"46.8%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"18.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08日02:00",  /*时间*/
//                    "cloud":"0.1%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"59.4%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"15.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"2.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"61.3%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"13.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"28℃"  /*温度*/
//            }
//                ],
//            "date":"05月08日",  /*日期*/
//                "week":"星期二",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/0.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"晴",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"14℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"2.5%",  /*云量*/
//                    "power":"0.7米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"41.6%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"18℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"23.8%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"22.1℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"2.8米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"26.6%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"27.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"3.4米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"25%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"27.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"3.2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"30.8%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"22.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"2.3米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"44.8%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"21.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"09日02:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"59.8%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"14.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"10%",  /*云量*/
//                    "power":"0.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"58.4%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"14.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*图片*/
//                    "power":"3~4级",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"30℃"  /*温度*/
//            }
//                ],
//            "date":"05月09日",  /*日期*/
//                "week":"星期三",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"18℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"0%",  /*云量*/
//                    "power":"1.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  /*天气现象*/
//                    "humidity":"50%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"15.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"26.3%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"23.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"34.5%",  /*云量*/
//                    "power":"3米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"35.5%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"29.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"5.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"27.8%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"28.1℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"2.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"47.5%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"26.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"1.6米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"49.1%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"22.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"10日02:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"2米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"68.7%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"18.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"71.2%",  /*云量*/
//                    "power":"2.4米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"61.3%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"18.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        },
//        {
//            "day":[  /*白天*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"29℃"  /*温度*/
//            }
//                ],
//            "date":"05月10日",  /*日期*/
//                "week":"星期四",  /*星期*/
//                "night":[  /*晚上*/
//            {
//                "img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/night/1.png",  /*图片*/
//                    "power":"微风",  /*风速*/
//                    "weather":"多云",  /*天气*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"18℃"  /*温度*/
//            }
//                ],
//            "hourForcast3":[  /*3小时预报*/
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"08:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"0.7米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"54.8%",  /*相对湿度*/
//                    "direction":"东北风",  /*风向*/
//                    "temprature":"20.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"4.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"29.5%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"25.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"14:00",  /*时间*/
//                    "cloud":"16.4%",  /*云量*/
//                    "power":"3.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"31.8%",  /*相对湿度*/
//                    "direction":"南风",  /*风向*/
//                    "temprature":"28.8℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"17:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"4.7米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"34.1%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"27.6℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"20:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"3.3米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"33.9%",  /*相对湿度*/
//                    "direction":"东南风",  /*风向*/
//                    "temprature":"24.4℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"23:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"2.1米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"47.4%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"20.7℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"11日02:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"1.9米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"49.8%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"19.1℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            },
//            {
//                "see":"-",  /*能见度*/
//                    "rain":"无降水",  /*降水*/
//                    "time":"05:00",  /*时间*/
//                    "cloud":"79.9%",  /*云量*/
//                    "power":"1.5米/秒",  /*风速*/
//                    "weather":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  /*天气现象*/
//                    "humidity":"50.3%",  /*相对湿度*/
//                    "direction":"东风",  /*风向*/
//                    "temprature":"18.2℃",  /*气温*/
//                    "airpressure":"-"  /*气压*/
//            }
//                ]
//        }
//        ],
//        "climate":[  /*气候背景*/
//        {
//            "rain":"2.64mm",  /*月平均降水*/
//                "month":"一月",  /*月份*/
//                "max_temp":"1.72℃",  /*月平均最高温*/
//                "min_temp":"-8.53℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"5.94mm",  /*月平均降水*/
//                "month":"二月",  /*月份*/
//                "max_temp":"4.78℃",  /*月平均最高温*/
//                "min_temp":"-5.88℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"8.98mm",  /*月平均降水*/
//                "month":"三月",  /*月份*/
//                "max_temp":"11.6℃",  /*月平均最高温*/
//                "min_temp":"0.24℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"22.18mm",  /*月平均降水*/
//                "month":"四月",  /*月份*/
//                "max_temp":"20.2℃",  /*月平均最高温*/
//                "min_temp":"7.81℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"34.72mm",  /*月平均降水*/
//                "month":"五月",  /*月份*/
//                "max_temp":"26.59℃",  /*月平均最高温*/
//                "min_temp":"13.89℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"76.29mm",  /*月平均降水*/
//                "month":"六月",  /*月份*/
//                "max_temp":"30.35℃",  /*月平均最高温*/
//                "min_temp":"18.86℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"179.78mm",  /*月平均降水*/
//                "month":"七月",  /*月份*/
//                "max_temp":"31.16℃",  /*月平均最高温*/
//                "min_temp":"22.09℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"170.52mm",  /*月平均降水*/
//                "month":"八月",  /*月份*/
//                "max_temp":"29.91℃",  /*月平均最高温*/
//                "min_temp":"20.91℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"55.12mm",  /*月平均降水*/
//                "month":"九月",  /*月份*/
//                "max_temp":"25.95℃",  /*月平均最高温*/
//                "min_temp":"14.91℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"23.24mm",  /*月平均降水*/
//                "month":"十月",  /*月份*/
//                "max_temp":"19.15℃",  /*月平均最高温*/
//                "min_temp":"7.83℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"9.07mm",  /*月平均降水*/
//                "month":"十一月",  /*月份*/
//                "max_temp":"10.11℃",  /*月平均最高温*/
//                "min_temp":"-0.02℃"  /*月平均最低温*/
//        },
//        {
//            "rain":"2.33mm",  /*月平均降水*/
//                "month":"十二月",  /*月份*/
//                "max_temp":"3.33℃",  /*月平均最高温*/
//                "min_temp":"-6.2℃"  /*月平均最低温*/
//        }
//        ]
//    },
//        "reason":"success"
//    }/*


}
