package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 4/19/18.
 */
public class WeatherDailyForecast implements Parcelable {


    public static final Creator<WeatherDailyForecast> CREATOR = new Creator<WeatherDailyForecast>
            () {
        @Override
        public WeatherDailyForecast createFromParcel(Parcel in) {
            return new WeatherDailyForecast(in);
        }

        @Override
        public WeatherDailyForecast[] newArray(int size) {
            return new WeatherDailyForecast[size];
        }
    };
    /*"cond_code_d": "103",
    "cond_code_n": "101",
    "cond_txt_d": "晴间多云",
    "cond_txt_n": "多云",
    "date": "2017-10-26",
    "hum": "57",

    mr 	月升时间 	04:47
    ms
    "pcpn": "0.0",
    "pop": "0",

    "pres": "1020",
    sr 	日出时间 	07:36
    ss 	日落时间 	16:58
    "tmp_max": "16",
    "tmp_min": "8",
    "uv_index": "3",

    "vis": "16",
    "wind_deg": "0",
    "wind_dir": "无持续风向",
    "wind_sc": "微风",
    "wind_spd": "5"*/
    /*白天和晚上的天气icon和描述*/
    private String cond_code_d;
    private String cond_code_n;
    private String cond_txt_d;
    private String cond_txt_n;
    /*  日期
        湿度
        降水量
        降水概率
    */
    private String date;
    private String hum;
    private String mr;
    private String ms;
    private String pcpn;
    /*大气压强
    最高温度
    最低温度
    紫外线等级*/
    private String pop;
    private String pres;
    private String sr;
    private String ss;
    private String tmp_max;
    private String tmp_min;
    private String uv_index;
    /*可见度
    风向360角度
    风向
    风力
    风速
    */
    private String vis;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    public WeatherDailyForecast() {
    }

    protected WeatherDailyForecast(Parcel in) {
        cond_code_d = in.readString();
        cond_code_n = in.readString();
        cond_txt_d = in.readString();
        cond_txt_n = in.readString();
        date = in.readString();
        hum = in.readString();
        mr = in.readString();
        ms = in.readString();
        pcpn = in.readString();
        pop = in.readString();
        pres = in.readString();
        sr = in.readString();
        ss = in.readString();
        tmp_max = in.readString();
        tmp_min = in.readString();
        uv_index = in.readString();
        vis = in.readString();
        wind_deg = in.readString();
        wind_dir = in.readString();
        wind_sc = in.readString();
        wind_spd = in.readString();
    }

    public String getDayInfo() {
        return this.date + "   " + this.cond_txt_d + "    " + tmp_max + "° ~ " + tmp_min + "°";
    }

    public String getNightInfo() {
        return this.date + "   " + this.cond_txt_n + "    " + tmp_max + "° ~ " + tmp_min + "°";
    }

    public String getMr() {
        return mr;
    }

    public void setMr(String mr) {
        this.mr = mr;
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getSs() {
        return ss;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }

    public String getCond_code_d() {
        return cond_code_d;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getCond_code_n() {
        return cond_code_n;
    }

    public void setCond_code_n(String cond_code_n) {
        this.cond_code_n = cond_code_n;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
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

    public String getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(String tmp_max) {
        this.tmp_max = tmp_max;
    }

    public String getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(String tmp_min) {
        this.tmp_min = tmp_min;
    }

    public String getUv_index() {
        return uv_index;
    }

    public void setUv_index(String uv_index) {
        this.uv_index = uv_index;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
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

        dest.writeString(cond_code_d);
        dest.writeString(cond_code_n);
        dest.writeString(cond_txt_d);
        dest.writeString(cond_txt_n);
        dest.writeString(date);
        dest.writeString(hum);
        dest.writeString(mr);
        dest.writeString(ms);
        dest.writeString(pcpn);
        dest.writeString(pop);
        dest.writeString(pres);
        dest.writeString(sr);
        dest.writeString(ss);
        dest.writeString(tmp_max);
        dest.writeString(tmp_min);
        dest.writeString(uv_index);
        dest.writeString(vis);
        dest.writeString(wind_deg);
        dest.writeString(wind_dir);
        dest.writeString(wind_sc);
        dest.writeString(wind_spd);
    }

    @Override
    public String toString() {
        return "WeatherDailyForecast{" +
                "cond_code_d='" + cond_code_d + '\'' +
                ", cond_code_n='" + cond_code_n + '\'' +
                ", cond_txt_d='" + cond_txt_d + '\'' +
                ", cond_txt_n='" + cond_txt_n + '\'' +
                ", date='" + date + '\'' +
                ", hum='" + hum + '\'' +
                ", mr='" + mr + '\'' +
                ", ms='" + ms + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pop='" + pop + '\'' +
                ", pres='" + pres + '\'' +
                ", sr='" + sr + '\'' +
                ", ss='" + ss + '\'' +
                ", tmp_max='" + tmp_max + '\'' +
                ", tmp_min='" + tmp_min + '\'' +
                ", uv_index='" + uv_index + '\'' +
                ", vis='" + vis + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }

    public String getTitle(boolean isDay) {
        return this.date + " " + (isDay ? "白天" : "晚上");
    }

    public String getTmpRange(){
            return this.tmp_max+"° ~ "+this.tmp_min+"°";
    }

}
