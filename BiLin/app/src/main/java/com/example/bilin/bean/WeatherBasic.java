package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 4/19/18.
 */
public class WeatherBasic implements Parcelable {

    /*"basic": {
        "cid": "CN101010100",
            "location": "北京",
            "parent_city": "北京",
            "admin_area": "北京",
            "cnty": "中国",
            "lat": "39.90498734",
            "lon": "116.40528870",
            "tz": "8.0"
    },*/

    public static final Creator<WeatherBasic> CREATOR = new Creator<WeatherBasic>() {
        @Override
        public WeatherBasic createFromParcel(Parcel in) {
            return new WeatherBasic(in);
        }

        @Override
        public WeatherBasic[] newArray(int size) {
            return new WeatherBasic[size];
        }
    };
    private String cid;
    private String location;
    private String parent_city;
    private String admin_area;
    private String cnty;
    private String lat;
    private String lon;

    public WeatherBasic() {
    }

    protected WeatherBasic(Parcel in) {
        cid = in.readString();
        location = in.readString();
        parent_city = in.readString();
        admin_area = in.readString();
        cnty = in.readString();
        lat = in.readString();
        lon = in.readString();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getParent_city() {
        return parent_city;
    }

    public void setParent_city(String parent_city) {
        this.parent_city = parent_city;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cid);
        dest.writeString(location);
        dest.writeString(parent_city);
        dest.writeString(admin_area);
        dest.writeString(cnty);
        dest.writeString(lat);
        dest.writeString(lon);
    }

    @Override
    public String toString() {
        return "WeatherBasic{" +
                "cid='" + cid + '\'' +
                ", location='" + location + '\'' +
                ", parent_city='" + parent_city + '\'' +
                ", admin_area='" + admin_area + '\'' +
                ", cnty='" + cnty + '\'' +
                ", lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }

    public String getSimpleLocation(){
        return admin_area+"/"+location;
    }
}
