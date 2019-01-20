package com.example.bilin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 5/29/18.
 */
public class OneDayInForecast7 {

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

    private String days;
    private String week;
    private String temperature;
    private String weather;
    //白天icon
    private String weather_icon;
    //night icon
    private String weather_icon1;
    //风向
    private String wind;
    //风力
    private String winp;
    //白天天气id
    private String weatid;
    //夜间天气id
    private String weatid1;

    private String temp_high;
    private String temp_low;
    //天气icon id
    private String weather_iconid;
    private String weather_iconid1;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWeather_icon() {
        return weather_icon;
    }

    public void setWeather_icon(String weather_icon) {
        this.weather_icon = weather_icon;
    }

    public String getWeather_icon1() {
        return weather_icon1;
    }

    public void setWeather_icon1(String weather_icon1) {
        this.weather_icon1 = weather_icon1;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWinp() {
        return winp;
    }

    public void setWinp(String winp) {
        this.winp = winp;
    }

    public String getWeatid() {
        return weatid;
    }

    public void setWeatid(String weatid) {
        this.weatid = weatid;
    }

    public String getWeatid1() {
        return weatid1;
    }

    public void setWeatid1(String weatid1) {
        this.weatid1 = weatid1;
    }

    public String getTemp_high() {
        return temp_high;
    }

    public void setTemp_high(String temp_high) {
        this.temp_high = temp_high;
    }

    public String getTemp_low() {
        return temp_low;
    }

    public void setTemp_low(String temp_low) {
        this.temp_low = temp_low;
    }

    public String getWeather_iconid() {
        return weather_iconid;
    }

    public void setWeather_iconid(String weather_iconid) {
        this.weather_iconid = weather_iconid;
    }

    public String getWeather_iconid1() {
        return weather_iconid1;
    }

    public void setWeather_iconid1(String weather_iconid1) {
        this.weather_iconid1 = weather_iconid1;
    }

    @Override
    public String toString() {
        return "OneDayInForecast7{" +
                "days='" + days + '\'' +
                ", week='" + week + '\'' +
                ", temperature='" + temperature + '\'' +
                ", weather='" + weather + '\'' +
                ", weather_icon='" + weather_icon + '\'' +
                ", weather_icon1='" + weather_icon1 + '\'' +
                ", wind='" + wind + '\'' +
                ", winp='" + winp + '\'' +
                ", weatid='" + weatid + '\'' +
                ", weatid1='" + weatid1 + '\'' +
                ", temp_high='" + temp_high + '\'' +
                ", temp_low='" + temp_low + '\'' +
                ", weather_iconid='" + weather_iconid + '\'' +
                ", weather_iconid1='" + weather_iconid1 + '\'' +
                '}';
    }
}
