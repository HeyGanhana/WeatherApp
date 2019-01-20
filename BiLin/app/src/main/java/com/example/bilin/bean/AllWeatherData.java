package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangdi on 4/19/18.
 */
public class AllWeatherData implements Parcelable,Serializable {

    public static final Creator<AllWeatherData> CREATOR = new Creator<AllWeatherData>() {
        @Override
        public AllWeatherData createFromParcel(Parcel in) {
            return new AllWeatherData(in);
        }

        @Override
        public AllWeatherData[] newArray(int size) {
            return new AllWeatherData[size];
        }
    };

    private WeatherBasic basic;
    private List<WeatherDailyForecast> dayForecastList;
    private List<WeatherHourlyForecast> hourlyForecastList;
    private HashMap<String, LifeStyleData> liftStyleDataMap = new HashMap<>();
    private HashMap<String, String> lifeTypeMap = new HashMap<>();
    private WeatherNowData weatherNowData;
    private String status;
    private String updateTime;

    public AllWeatherData() {
    }

    protected AllWeatherData(Parcel in) {
        basic = in.readParcelable(WeatherBasic.class.getClassLoader());
        dayForecastList = in.createTypedArrayList(WeatherDailyForecast.CREATOR);
        hourlyForecastList = in.createTypedArrayList(WeatherHourlyForecast.CREATOR);
        weatherNowData = in.readParcelable(WeatherNowData.class.getClassLoader());
        status = in.readString();
        updateTime = in.readString();
    }

    public WeatherBasic getBasic() {
        return basic;
    }

    public void setBasic(WeatherBasic basic) {
        this.basic = basic;
    }

    public List<WeatherDailyForecast> getDayForecastList() {
        return dayForecastList;
    }

    public void setDayForecastList(List<WeatherDailyForecast> dayForecastList) {
        this.dayForecastList = dayForecastList;
    }

    public List<WeatherHourlyForecast> getHourlyForecastList() {
        return hourlyForecastList;
    }

    public void setHourlyForecastList(List<WeatherHourlyForecast> hourlyForecastList) {
        this.hourlyForecastList = hourlyForecastList;
    }

    public HashMap<String, LifeStyleData> getliftStyleDataMap() {
        return liftStyleDataMap;
    }

    public void setliftStyleDataMap(HashMap<String, LifeStyleData> liftStyleDataMap) {
        this.liftStyleDataMap = liftStyleDataMap;
    }

    public HashMap<String, String> getLifeTypeMap() {
        return lifeTypeMap;
    }

    public void setLifeTypeMap(HashMap<String, String> lifeTypeMap) {
        this.lifeTypeMap = lifeTypeMap;
    }

    public WeatherNowData getWeatherNowData() {
        return weatherNowData;
    }

    public void setWeatherNowData(WeatherNowData weatherNowData) {
        this.weatherNowData = weatherNowData;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(basic, flags);
        dest.writeTypedList(dayForecastList);
        dest.writeTypedList(hourlyForecastList);
        dest.writeParcelable(weatherNowData, flags);
        dest.writeString(status);
        dest.writeString(updateTime);
    }

    /*{
        "HeWeather6": [
        {
            "basic": {
                "cid": "CN101010100",
                "location": "北京",
                "parent_city": "北京",
                "admin_area": "北京",
                "cnty": "中国",
                "lat": "39.90498734",
                "lon": "116.40528870",
                "tz": "8.0"
            },
            "daily_forecast": [
                {
                    "cond_code_d": "103",
                    "cond_code_n": "101",
                    "cond_txt_d": "晴间多云",
                    "cond_txt_n": "多云",
                    "date": "2017-10-26",
                    "hum": "57",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1020",
                    "tmp_max": "16",
                    "tmp_min": "8",
                    "uv_index": "3",
                    "vis": "16",
                    "wind_deg": "0",
                    "wind_dir": "无持续风向",
                    "wind_sc": "微风",
                    "wind_spd": "5"
                },
                {
                    "cond_code_d": "101",
                    "cond_code_n": "501",
                    "cond_txt_d": "多云",
                    "cond_txt_n": "雾",
                    "date": "2017-10-27",
                    "hum": "56",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1018",
                    "tmp_max": "18",
                    "tmp_min": "9",
                    "uv_index": "3",
                    "vis": "20",
                    "wind_deg": "187",
                    "wind_dir": "南风",
                    "wind_sc": "微风",
                    "wind_spd": "6"
                },
                {
                    "cond_code_d": "101",
                    "cond_code_n": "101",
                    "cond_txt_d": "多云",
                    "cond_txt_n": "多云",
                    "date": "2017-10-28",
                    "hum": "26",
                    "pcpn": "0.0",
                    "pop": "0",
                    "pres": "1029",
                    "tmp_max": "17",
                    "tmp_min": "5",
                    "uv_index": "2",
                    "vis": "20",
                    "wind_deg": "2",
                    "wind_dir": "北风",
                    "wind_sc": "3-4",
                    "wind_spd": "19"
                }
            ],
            "hourly": [
                {
                    "cloud": "8",
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
                    "wind_spd": "2"
                },
                {
                    "cloud": "8",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "81",
                    "pop": "0",
                    "pres": "1018",
                    "time": "2017-10-27 04:00",
                    "tmp": "8",
                    "wind_deg": "29",
                    "wind_dir": "东北风",
                    "wind_sc": "微风",
                    "wind_spd": "2"
                },
                {
                    "cloud": "6",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "95",
                    "pop": "0",
                    "pres": "1019",
                    "time": "2017-10-27 07:00",
                    "tmp": "8",
                    "wind_deg": "37",
                    "wind_dir": "东北风",
                    "wind_sc": "微风",
                    "wind_spd": "2"
                },
                {
                    "cloud": "2",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "75",
                    "pop": "0",
                    "pres": "1018",
                    "time": "2017-10-27 10:00",
                    "tmp": "14",
                    "wind_deg": "108",
                    "wind_dir": "东南风",
                    "wind_sc": "微风",
                    "wind_spd": "3"
                },

                {
                    "cloud": "0",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "62",
                    "pop": "0",
                    "pres": "1016",
                    "time": "2017-10-27 13:00",
                    "tmp": "16",
                    "wind_deg": "158",
                    "wind_dir": "东南风",
                    "wind_sc": "微风",
                    "wind_spd": "6"
                },
                {
                    "cloud": "0",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "73",
                    "pop": "0",
                    "pres": "1016",
                    "time": "2017-10-27 16:00",
                    "tmp": "15",
                    "wind_deg": "162",
                    "wind_dir": "东南风",
                    "wind_sc": "微风",
                    "wind_spd": "6"
                },
                {
                    "cloud": "3",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "92",
                    "pop": "0",
                    "pres": "1018",
                    "time": "2017-10-27 19:00",
                    "tmp": "13",
                    "wind_deg": "206",
                    "wind_dir": "西南风",
                    "wind_sc": "微风",
                    "wind_spd": "4"
                },
                {
                    "cloud": "19",
                    "cond_code": "100",
                    "cond_txt": "晴",
                    "hum": "96",
                    "pop": "0",
                    "pres": "1019",
                    "time": "2017-10-27 22:00",
                    "tmp": "13",
                    "wind_deg": "212",
                    "wind_dir": "西南风",
                    "wind_sc": "微风",
                    "wind_spd": "1"
                }
            ],
            "lifestyle": [
                {
                    "brf": "舒适",
                    "txt": "今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。",
                    "type": "comf"
                },
                {
                    "brf": "较舒适",
                    "txt": "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
                    "type": "drsg"

                },
                {
                    "brf": "少发",
                    "txt": "各项气象条件适宜，无明显降温过程，发生感冒机率较低。",
                    "type": "flu"
                },
                {
                    "brf": "适宜",
                    "txt": "天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。",
                    "type": "sport"
                },
                {
                    "brf": "适宜",
                    "txt": "天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。",
                    "type": "trav"
                },
                {
                    "brf": "弱",
                    "txt": "紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。",
                    "type": "uv"
                },
                {
                    "brf": "较不宜",
                        "txt": "较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。",
                        "type": "cw"
                },
                {
                    "brf": "较差",
                    "txt": "气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。",
                    "type": "air"
                }
            ],
            "now": {
                "cond_code": "501",
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
                "wind_spd": "7"
            },
            "status": "ok",
            "update": {
                "loc": "2017-10-26 23:09",
                "utc": "2017-10-26 15:09"
            }

        }

    ]
}*/

    @Override
    public String toString() {
        return "AllWeatherData{" +
                "basic=" + basic +
                ", dayForecastList=" + dayForecastList +
                ", hourlyForecastList=" + hourlyForecastList +
                ", liftStyleDatamap=" + liftStyleDataMap +
                ", lifeTypeMap=" + lifeTypeMap +
                ", weatherNowData=" + weatherNowData +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }

}
