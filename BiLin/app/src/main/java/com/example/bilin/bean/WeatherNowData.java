package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wjs on 4/14/18.
 */

public class WeatherNowData implements Parcelable {

    /*
    * {"HeWeather6":
    * [
    *   {
    *       "basic":
    *       {
        *       "cid":"CN101210101",
        *       "location":"杭州",
        *       "parent_city":"杭州",
        *       "admin_area":"浙江",
        *       "cnty":"中国",
        *       "lat":"30.28745842",
        *       "lon":"120.15357971",
        *       "tz":"+8.00"
    *       },
    *       "update":
    *       {
        *       "loc":"2018-04-16 09:47",
        *       "utc":"2018-04-16 01:47"
    *       },
    *       "status":"ok",
    *
    *       "cond_code": "501",
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
    *
    *       "now":{
        *       //"cloud":"0",
        *       "cond_code":"104",
        *       "cond_txt":"阴",
        *       "fl":"14",
        *       "hum":"40",
        *       "pcpn":"0.0",
        *       "pres":"1021",
        *       "tmp":"15",
        *       "vis":"10",
        *       "wind_deg":"83",
        *       "wind_dir":"东风",
        *       "wind_sc":"1",
        *       "wind_spd":"2"
    *       }
    *   }
    * ]}
    *
    * */


    public static final Creator<WeatherNowData> CREATOR = new Creator<WeatherNowData>() {
        @Override
        public WeatherNowData createFromParcel(Parcel in) {
            return new WeatherNowData(in);
        }

        @Override
        public WeatherNowData[] newArray(int size) {
            return new WeatherNowData[size];
        }
    };
    private String cond_code;
    private String cond_txt;
    private String fl;
    private String tmp;
    private String hum;
    private String pcpn;
    private String pres;
    private String vis;
    private String wind_deg;
    private String wind_dir;
    private String wind_sc;
    private String wind_spd;

    public WeatherNowData() {
    }

    protected WeatherNowData(Parcel in) {
        cond_code = in.readString();
        cond_txt = in.readString();
        fl = in.readString();
        tmp = in.readString();
        hum = in.readString();
        pcpn = in.readString();
        pres = in.readString();
        vis = in.readString();
        wind_deg = in.readString();
        wind_dir = in.readString();
        wind_sc = in.readString();
        wind_spd = in.readString();
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
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

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
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

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    /*
    fl 	体感温度，默认单位：摄氏度 	23
    tmp 	温度，默认单位：摄氏度 	21
    cond_code 	实况天气状况代码 	100
    cond_txt 	实况天气状况代码 	晴
    wind_deg 	风向360角度 	305
    wind_dir 	风向 	西北
    wind_sc 	风力 	3
    wind_spd 	风速，公里/小时 	15
    hum 	相对湿度 	40
    pcpn 	降水量 	0
    pres 	大气压强 	1020
    vis 	能见度，默认单位：公里 	10
    cloud 	云量
    * */

    /*@Override
    public String toString() {
        return "icon:" + cond_code + "\n" +
                "天气：" + cond_txt + "当前温度：" + tmp + "℃" + ",体感温度：" + fl + "℃" + ",湿度：" + hum + "," +
                "降水量：" + pcpn + "\n" +
                "能见度：" + vis + "公里" + ",风力：" + wind_sc + "级" + wind_dir + ",风速：" + wind_spd +
                "公里/小时";
    }*/

    public String getTitle() {
        return "";
    }

    public String getMessage() {
        return  tmp + "°";
    }

    public String getDetails() {
        return "湿度：" + hum + "  降水量：" + pcpn + "  能见度：" + vis + "公里" + "\n"
                + "风力：" + wind_sc + "级" + wind_dir + "  风速：" + wind_spd + "公里/小时";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cond_code);
        dest.writeString(cond_txt);
        dest.writeString(fl);
        dest.writeString(tmp);
        dest.writeString(hum);
        dest.writeString(pcpn);
        dest.writeString(pres);
        dest.writeString(vis);
        dest.writeString(wind_deg);
        dest.writeString(wind_dir);
        dest.writeString(wind_sc);
        dest.writeString(wind_spd);
    }

    @Override
    public String toString() {
        return "WeatherNowData{" +
                "cond_code='" + cond_code + '\'' +
                ", cond_txt='" + cond_txt + '\'' +
                ", fl='" + fl + '\'' +
                ", tmp='" + tmp + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pres='" + pres + '\'' +
                ", vis='" + vis + '\'' +
                ", wind_deg='" + wind_deg + '\'' +
                ", wind_dir='" + wind_dir + '\'' +
                ", wind_sc='" + wind_sc + '\'' +
                ", wind_spd='" + wind_spd + '\'' +
                '}';
    }
}
