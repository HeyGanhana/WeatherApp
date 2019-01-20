package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 2018/4/9.
 */

public class City implements Parcelable {
    //<city id="2" name="北京市" name_en="Beijing" name_py="beijingshi" province="北京市"
    // weathercnid="101010100"/>

    private String id;
    private String name;
    private String nameEn;
    private String namePy;
    private String province;
    private String weatherId;
    private String adcode;

    public City() {
    }


    protected City(Parcel in) {
        id = in.readString();
        name = in.readString();
        nameEn = in.readString();
        namePy = in.readString();
        province = in.readString();
        weatherId = in.readString();
        adcode = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNamePy() {
        return namePy;
    }

    public void setNamePy(String namePy) {
        this.namePy = namePy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public String getSimpleInfo() {
        return this.province.trim() + this.name.trim();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(nameEn);
        dest.writeString(namePy);
        dest.writeString(province);
        dest.writeString(weatherId);
        dest.writeString(adcode);
    }
}
