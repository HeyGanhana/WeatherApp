package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 4/19/18.
 */
public class WeatherHourlyForecast implements Parcelable {

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

    public static final Creator<WeatherHourlyForecast> CREATOR = new
            Creator<WeatherHourlyForecast>() {
                @Override
                public WeatherHourlyForecast createFromParcel(Parcel in) {
                    return new WeatherHourlyForecast(in);
                }

                @Override
                public WeatherHourlyForecast[] newArray(int size) {
                    return new WeatherHourlyForecast[size];
                }
            };
    private String cloud;
    private String cond_code;
    private String cond_txt;
    private String hum;
    private String pop;
    private String pres;
    private String time;
    private String tmp;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    public WeatherHourlyForecast() {
    }

    protected WeatherHourlyForecast(Parcel in) {
        cloud = in.readString();
        cond_code = in.readString();
        cond_txt = in.readString();
        hum = in.readString();
        pop = in.readString();
        pres = in.readString();
        time = in.readString();
        tmp = in.readString();
        wind_deg = in.readString();
        wind_dir = in.readString();
        wind_sc = in.readString();
        wind_spd = in.readString();
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getCond_code() {
        return cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_sc() {
        return wind_sc;
    }

    public void setWind_sc(String wind_sc) {
        this.wind_sc = wind_sc;
    }

    public String getWind_spd() {
        return wind_spd;
    }

    public void setWind_spd(String wind_spd) {
        this.wind_spd = wind_spd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cloud);
        dest.writeString(cond_code);
        dest.writeString(cond_txt);
        dest.writeString(hum);
        dest.writeString(pop);
        dest.writeString(pres);
        dest.writeString(time);
        dest.writeString(tmp);
        dest.writeString(wind_deg);
        dest.writeString(wind_dir);
        dest.writeString(wind_sc);
        dest.writeString(wind_spd);
    }

    @Override
    public String toString() {
        return "WeatherHourlyForecast{" +
                "cloud='" + cloud + '\'' +
                ", cond_code='" + cond_code + '\'' +
                ", cond_txt='" + cond_txt + '\'' +
                ", hum='" + hum + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", time='" + time + '\'' +
                ", tmp='" + tmp + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }
}
