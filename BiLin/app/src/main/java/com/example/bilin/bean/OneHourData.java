package com.example.bilin.bean;

/**
 * Created by zhangdi on 5/25/18.
 */
public class OneHourData {


    //        {
//            "time":"2018-05-04 12:00",  /*时间*/
//                "humidity":"33%",  /*相对湿度*/
//                "pressure":"1004hPa",  /*气压*/
//                "temperature":"22.5℃"  /*气温*/
//        },
    private String time;
    private String temperature;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
