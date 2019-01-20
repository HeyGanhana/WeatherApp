package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 2018/4/6.
 */

public class WeatherData implements Parcelable {

    public static final Creator<WeatherData> CREATOR = new Creator<WeatherData>() {
        @Override
        public WeatherData createFromParcel(Parcel in) {
            return new WeatherData(in);
        }

        @Override
        public WeatherData[] newArray(int size) {
            return new WeatherData[size];
        }
    };
    private String conname;
    private String pname;
    private String name;
    private int cityId;
    //jsonArray
    private String conditionDay;
    private String conditionId;
    private String conditionIdNight;
    private String conditionNight;
    private String predictDate;
    private String tempDay;
    private String tempNight;
    private String windDirDay;
    private String windDirNight;
    private String windLevelDay;
    private String windLevelNight;

    public WeatherData() {
    }

    protected WeatherData(Parcel in) {
        conname = in.readString();
        pname = in.readString();
        name = in.readString();
        cityId = in.readInt();
        conditionDay = in.readString();
        conditionId = in.readString();
        conditionIdNight = in.readString();
        conditionNight = in.readString();
        predictDate = in.readString();
        tempDay = in.readString();
        tempNight = in.readString();
        windDirDay = in.readString();
        windDirNight = in.readString();
        windLevelDay = in.readString();
        windLevelNight = in.readString();
    }

    public String getConname() {
        return conname;
    }

    public void setConname(String conname) {
        this.conname = conname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getConditionDay() {
        return conditionDay;
    }

    public void setConditionDay(String conditionDay) {
        this.conditionDay = conditionDay;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionIdNight() {
        return conditionIdNight;
    }

    public void setConditionIdNight(String conditionIdNight) {
        this.conditionIdNight = conditionIdNight;
    }

    public String getConditionNight() {
        return conditionNight;
    }

    public void setConditionNight(String conditionNight) {
        this.conditionNight = conditionNight;
    }

    public String getPredictDate() {
        return predictDate;
    }

    public void setPredictDate(String predictDate) {
        this.predictDate = predictDate;
    }

    public String getTempDay() {
        return tempDay;
    }

    public void setTempDay(String tempDay) {
        this.tempDay = tempDay;
    }

    public String getTempNight() {
        return tempNight;
    }

    public void setTempNight(String tempNight) {
        this.tempNight = tempNight;
    }

    public String getWindDirDay() {
        return windDirDay;
    }

    public void setWindDirDay(String windDirDay) {
        this.windDirDay = windDirDay;
    }

    public String getWindDirNight() {
        return windDirNight;
    }

    public void setWindDirNight(String windDirNight) {
        this.windDirNight = windDirNight;
    }

    public String getWindLevelDay() {
        return windLevelDay;
    }

    public void setWindLevelDay(String windLevelDay) {
        this.windLevelDay = windLevelDay;
    }

    public String getWindLevelNight() {
        return windLevelNight;
    }

    public void setWindLevelNight(String windLevelNight) {
        this.windLevelNight = windLevelNight;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return predictDate + "国家：" + conname + ",省份：" + pname + "城市：" + name + "\n" +
                "白天天气：" + conditionDay + "白天温度：" + conditionNight + "白天风向：" + windLevelDay + "级"
                + windDirDay +
                "\n晚上天气：" + tempDay + "晚上温度：" + tempNight + "晚上风向：" + windLevelNight + "级" +
                windDirNight;
    }

    /**
     * 返回城市信息
     *
     * @return
     */
    public String getLocationTitle() {
        String title = //predictDate + " " + conname + " " +
                pname + " " + name;
        return title;
    }

    public String getDayInfo() {
        return "白天：" + conditionDay + " 温度：" + tempDay + "℃ " +
                windLevelDay + "级" + windDirDay;
    }

    public String getNightInfo() {
        return "晚上：" + conditionNight + " 温度：" + tempNight + "℃ "
                + windLevelNight + "级" + windDirNight;
    }

    /**
     * 返回天气信息
     *
     * @return
     */
    public String getWeatherSimpleInfoTitle() {
        String content = "白天：" + conditionDay + " 温度：" + tempDay + "℃ " + windLevelDay + "级" +
                windDirDay +
                "\n晚上：" + conditionNight + " 温度：" + tempNight + "℃ " + windLevelNight + "级" +
                windDirNight;
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(conname);
        dest.writeString(pname);
        dest.writeString(name);
        dest.writeInt(cityId);
        dest.writeString(conditionDay);
        dest.writeString(conditionId);
        dest.writeString(conditionIdNight);
        dest.writeString(conditionNight);
        dest.writeString(predictDate);
        dest.writeString(tempDay);
        dest.writeString(tempNight);
        dest.writeString(windDirDay);
        dest.writeString(windDirNight);
        dest.writeString(windLevelDay);
        dest.writeString(windLevelNight);
    }
}
