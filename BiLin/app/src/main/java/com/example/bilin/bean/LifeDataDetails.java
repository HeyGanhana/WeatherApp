package com.example.bilin.bean;

/**
 * Created by zhangdi on 4/16/18.
 */

public class LifeDataDetails {

    public String txt;
    public String brf;
    public String type;

    public LifeDataDetails() {
    }

    public LifeDataDetails(String txt, String brf, String type) {
        this.txt = txt;
        this.brf = brf;
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
