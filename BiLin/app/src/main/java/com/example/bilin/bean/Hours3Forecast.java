package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 5/25/18.
 */
public class Hours3Forecast implements Parcelable {


    public static final Creator<Hours3Forecast> CREATOR = new Creator<Hours3Forecast>() {
        @Override
        public Hours3Forecast createFromParcel(Parcel in) {
            return new Hours3Forecast(in);
        }

        @Override
        public Hours3Forecast[] newArray(int size) {
            return new Hours3Forecast[size];
        }
    };
    private String weather_img;
    private String time;
    private String direction;
    private String temp;
    private String rain;
    //    "hourForcast3":[  /*3小时预报*/
//    {
//        "see":"-",  /*能见度*/
//            "rain":"无降水",  /*降水*/
//            "time":"14:00",  /*时间*/
//            "cloud":"10%",  /*云量*/
//            "direction":"3米/秒",  /*风速*/
//            "weather_img":"http://image.nmc
// .cn/static2/site/nmc/themes/basic/weather_img/white/day/0.png",  /*天气现象*/
//            "humidity":"38.6%",  /*相对湿度*/
//            "direction":"南风",  /*风向*/
//            "temprature":"26.8℃",  /*气温*/
//            "airpressure":"-"  /*气压*/
//    },
    public Hours3Forecast() {

    }

    protected Hours3Forecast(Parcel in) {
        weather_img = in.readString();
        time = in.readString();
        direction = in.readString();
        temp = in.readString();
        rain = in.readString();
    }

    public String getweather_img() {
        return weather_img;
    }

    public void setweather_img(String weather_img) {
        this.weather_img = weather_img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getdirection() {
        return direction;
    }

    public void setdirection(String direction) {
        this.direction = direction;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weather_img);
        dest.writeString(time);
        dest.writeString(direction);
        dest.writeString(temp);
        dest.writeString(rain);
    }
}
