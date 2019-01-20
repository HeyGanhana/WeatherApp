package com.example.bilin.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 5/25/18.
 */
public class AllDayTimeWeatherData {

    //now
    private String now_rain;
    private String now_temp;
    private String now_date;
    private String now_wind_direct;
    private String now_wind_power;
    private String now_aircomf;


    //forecast7

    private List<OneDayInForecast7> forecast7s = new ArrayList<>();

    public List<OneDayInForecast7> getForecast7s() {
        return forecast7s;
    }

    public void setForecast7s(OneDayInForecast7 oneDayInForecast7) {
        forecast7s.add(oneDayInForecast7);
    }

    public String getNow_rain() {
        return now_rain;
    }

    public void setNow_rain(String now_rain) {
        this.now_rain = now_rain;
    }

    public String getNow_temp() {
        return now_temp;
    }

    public void setNow_temp(String now_temp) {
        this.now_temp = now_temp;
    }

    public String getNow_date() {
        return now_date;
    }

    public void setNow_date(String now_date) {
        this.now_date = now_date;
    }

    public String getNow_wind_direct() {
        return now_wind_direct;
    }

    public void setNow_wind_direct(String now_wind_direct) {
        this.now_wind_direct = now_wind_direct;
    }

    public String getNow_wind_power() {
        return now_wind_power;
    }

    public void setNow_wind_power(String now_wind_power) {
        this.now_wind_power = now_wind_power;
    }

    public String getNow_aircomf() {
        return now_aircomf;
    }

    public void setNow_aircomf(String now_aircomf) {
        this.now_aircomf = now_aircomf;
    }

    /*{
        "day":[  *//*白天*//*
        {
            "img":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  *//*图片*//*
                "power":"微风",  *//*风速*//*
                "weather":"晴",  *//*天气*//*
                "direction":"西南风",  *//*风向*//*
                "temprature":"27℃"  *//*温度*//*
        }
        ],
        "date":"05月04日",  *//*日期*//*
        "warn":"暂无预警",
        "week":"星期五",  *//*星期*//*
        "night":[  *//*晚上*//*
            {
                "img":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/night/1.png",  *//*图片*//*
                    "power":"微风",  *//*风速*//*
                    "weather":"多云",  *//*天气*//*
                    "direction":"南风",  *//*风向*//*
                    "temprature":"16℃"  *//*温度*//*
            }
        ],
        "hour24":[  *//*24小时实况*//*
        {
            "time":"2018-05-04 12:00",  *//*时间*//*
                "humidity":"33%",  *//*相对湿度*//*
                "pressure":"1004hPa",  *//*气压*//*
                "temperature":"22.5℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 11:00",  *//*时间*//*
                "humidity":"37%",  *//*相对湿度*//*
                "pressure":"1005hPa",  *//*气压*//*
                "temperature":"21℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 10:00",  *//*时间*//*
                "humidity":"39%",  *//*相对湿度*//*
                "pressure":"1006hPa",  *//*气压*//*
                "temperature":"19.9℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 09:00",  *//*时间*//*
                "humidity":"39%",  *//*相对湿度*//*
                "pressure":"1006hPa",  *//*气压*//*
                "temperature":"18.9℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 08:00",  *//*时间*//*
                "humidity":"42%",  *//*相对湿度*//*
                "pressure":"1007hPa",  *//*气压*//*
                "temperature":"17.1℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 07:00",  *//*时间*//*
                "humidity":"48%",  *//*相对湿度*//*
                "pressure":"1007hPa",  *//*气压*//*
                "temperature":"15.4℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 06:00",  *//*时间*//*
                "humidity":"61%",  *//*相对湿度*//*
                "pressure":"1007hPa",  *//*气压*//*
                "temperature":"12.1℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 05:00",  *//*时间*//*
                "humidity":"60%",  *//*相对湿度*//*
                "pressure":"1007hPa",  *//*气压*//*
                "temperature":"11.6℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 04:00",  *//*时间*//*
                "humidity":"59%",  *//*相对湿度*//*
                "pressure":"1008hPa",  *//*气压*//*
                "temperature":"11.4℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 03:00",  *//*时间*//*
                "humidity":"50%",  *//*相对湿度*//*
                "pressure":"1008hPa",  *//*气压*//*
                "temperature":"13.2℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 02:00",  *//*时间*//*
                "humidity":"42%",  *//*相对湿度*//*
                "pressure":"1008hPa",  *//*气压*//*
                "temperature":"15.1℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 01:00",  *//*时间*//*
                "humidity":"38%",  *//*相对湿度*//*
                "pressure":"1008hPa",  *//*气压*//*
                "temperature":"16.4℃"  *//*气温*//*
        },
        {
            "time":"2018-05-04 00:00",  *//*时间*//*
                "humidity":"36%",  *//*相对湿度*//*
                "pressure":"1008hPa",  *//*气压*//*
                "temperature":"17.2℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 23:00",  *//*时间*//*
                "humidity":"34%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"17.3℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 22:00",  *//*时间*//*
                "humidity":"31%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"17.8℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 21:00",  *//*时间*//*
                "humidity":"28%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"18.7℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 20:00",  *//*时间*//*
                "humidity":"25%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"19.8℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 19:00",  *//*时间*//*
                "humidity":"23%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"21.1℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 18:00",  *//*时间*//*
                "humidity":"19%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"22.4℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 17:00",  *//*时间*//*
                "humidity":"17%",  *//*相对湿度*//*
                "pressure":"1009hPa",  *//*气压*//*
                "temperature":"23.2℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 16:00",  *//*时间*//*
                "humidity":"14%",  *//*相对湿度*//*
                "pressure":"1010hPa",  *//*气压*//*
                "temperature":"23.7℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 15:00",  *//*时间*//*
                "humidity":"14%",  *//*相对湿度*//*
                "pressure":"1011hPa",  *//*气压*//*
                "temperature":"23.5℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 14:00",  *//*时间*//*
                "humidity":"12%",  *//*相对湿度*//*
                "pressure":"1012hPa",  *//*气压*//*
                "temperature":"22.8℃"  *//*气温*//*
        },
        {
            "time":"2018-05-03 13:00",  *//*时间*//*
                "humidity":"12%",  *//*相对湿度*//*
                "pressure":"1013hPa",  *//*气压*//*
                "temperature":"22.1℃"  *//*气温*//*
        }
        ],
        "sunset":"19:11",  *//*日落时间*//*
            "sunrise":"05:12",  *//*日出时间*//*
            "rcomfort":"良",  *//*空气质量*//*
            "hourForcast3":[  *//*3小时预报*//*
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"14:00",  *//*时间*//*
                "cloud":"10%",  *//*云量*//*
                "power":"3米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  *//*天气现象*//*
                "humidity":"38.6%",  *//*相对湿度*//*
                "direction":"南风",  *//*风向*//*
                "temprature":"26.8℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"17:00",  *//*时间*//*
                "cloud":"0%",  *//*云量*//*
                "power":"3.1米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  *//*天气现象*//*
                "humidity":"39.2%",  *//*相对湿度*//*
                "direction":"南风",  *//*风向*//*
                "temprature":"24.4℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"20:00",  *//*时间*//*
                "cloud":"3%",  *//*云量*//*
                "power":"2.2米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/0.png",  *//*天气现象*//*
                "humidity":"58.6%",  *//*相对湿度*//*
                "direction":"南风",  *//*风向*//*
                "temprature":"21.9℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"23:00",  *//*时间*//*
                "cloud":"19.5%",  *//*云量*//*
                "power":"2米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  *//*天气现象*//*
                "humidity":"61.1%",  *//*相对湿度*//*
                "direction":"西南风",  *//*风向*//*
                "temprature":"21.9℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"05日02:00",  *//*时间*//*
                "cloud":"79.9%",  *//*云量*//*
                "power":"1.1米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  *//*天气现象*//*
                "humidity":"75.9%",  *//*相对湿度*//*
                "direction":"西南风",  *//*风向*//*
                "temprature":"17.7℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"05:00",  *//*时间*//*
                "cloud":"77.3%",  *//*云量*//*
                "power":"1.9米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  *//*天气现象*//*
                "humidity":"64.4%",  *//*相对湿度*//*
                "direction":"北风",  *//*风向*//*
                "temprature":"16.2℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"08:00",  *//*时间*//*
                "cloud":"79.9%",  *//*云量*//*
                "power":"3.5米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  *//*天气现象*//*
                "humidity":"47.1%",  *//*相对湿度*//*
                "direction":"北风",  *//*风向*//*
                "temprature":"21.2℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        },
        {
            "see":"-",  *//*能见度*//*
                "rain":"无降水",  *//*降水*//*
                "time":"11:00",  *//*时间*//*
                "cloud":"79.9%",  *//*云量*//*
                "power":"3.6米/秒",  *//*风速*//*
                "weather":"http://image.nmc.cn/static2/site/nmc/themes/basic/weather/white/day/1.png",  *//*天气现象*//*
                "humidity":"34%",  *//*相对湿度*//*
                "direction":"北风",  *//*风向*//*
                "temprature":"24.6℃",  *//*气温*//*
                "airpressure":"-"  *//*气压*//*
        }
                ]
    }*/

    @Override
    public String toString() {
        return "AllDayTimeWeatherData{" +
                "now_rain='" + now_rain + '\'' +
                ", now_temp='" + now_temp + '\'' +
                ", now_date='" + now_date + '\'' +
                ", now_wind_direct='" + now_wind_direct + '\'' +
                ", now_wind_power='" + now_wind_power + '\'' +
                ", now_aircomf='" + now_aircomf + '\'' +
                ", forecast7s=" + forecast7s +
                '}';
    }
}
