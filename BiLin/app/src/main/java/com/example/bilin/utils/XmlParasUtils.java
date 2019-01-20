package com.example.bilin.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;

import com.example.bilin.R;
import com.example.bilin.bean.City;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangdi on 2018/4/9.
 */

public class XmlParasUtils {

    public static String REG_CHINESE = "^[\\u0391-\\uFFE5]+$";
    private static Context mContext;
    private static XmlParasUtils xmlParasUtils;
    private int eventType = -1;
    private HashMap<String, City> cityMap = new HashMap<>();
    private City city;
    private HashMap<String, City> keyMap = new HashMap<>();
    private Pattern pattern = Pattern.compile(REG_CHINESE);
    private List<String> nameList = new ArrayList<>();
    private List<String> adCodeList = new ArrayList<>();

    public XmlParasUtils() {
        loadCitysFromXml();
    }

    public static XmlParasUtils getInstance(Context context) {
        mContext = context;
        synchronized (mContext) {
            if (xmlParasUtils == null) {
                xmlParasUtils = new XmlParasUtils();
            }
        }
        return xmlParasUtils;
    }

    public HashMap<String, City> getCitys(){
        return this.cityMap;
    }

    public List<String> getCitysNameList(){
        return this.nameList;
    }

    private void loadCitysFromXml() {

        //            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
//            XmlPullParser xmlPullParser = xppf.newPullParser();
        try {
            nameList.clear();
            cityMap.clear();
            XmlResourceParser xrp = mContext.getResources().getXml(R.xml.citys);
            eventType = xrp.getEventType();
            //Logger.e("eventType = " + eventType);
            long time = -1L;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://xml 文本开始
                        time = System.currentTimeMillis();
                        break;
                    case XmlPullParser.END_DOCUMENT://xml  文本结束
                        break;
                    case XmlPullParser.START_TAG://xml 标签开始
                        String tagName = xrp.getName();
                        if (tagName != null && "city".equals(tagName)) {
                            city = new City();
                            /*<city
                                    name="怀柔"
                            adcode="110116"
                            name_en="beijing"
                            name_py="北京"
                            province="北京"
                            weathercnid="CN101010500"></city>*/
                            String name = xrp.getAttributeValue(null, "name");
                            String adcode = xrp.getAttributeValue(null,"adcode");
                            String nameEn = xrp.getAttributeValue(null, "name_en");
                            String namePy = xrp.getAttributeValue(null, "name_py");
                            String province = xrp.getAttributeValue(null, "province");
                            String weathercnId = xrp.getAttributeValue(null, "weathercnid");


                            city.setName(name);
                            city.setNameEn(nameEn);
                            city.setNamePy(namePy);
                            city.setProvince(province);
                            city.setWeatherId(weathercnId);
                            city.setAdcode(adcode);
                            Logger.e("adcode==="+adcode);
                        }
                        break;
                    case XmlPullParser.END_TAG://xml   标签结束
                        if ("city".equals(xrp.getName())) {
                            //Logger.e("city:" + city.getProvince() + "--->" + city.getName());
                            cityMap.put(city.getWeatherId(), city);//city.getProvince().trim() + city
                            nameList.add(city.getName());
                            adCodeList.add(city.getAdcode());
                            // .getName().trim()
                        }
                        break;
                    case XmlPullParser.TEXT://xml  标签之间的内容
                        break;
                }
                eventType = xrp.next();
                if (eventType == XmlPullParser.END_DOCUMENT) {
                    time = System.currentTimeMillis() - time;
                    Logger.e("waste time:" + time);
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public List<String> getAdCodeList(){
        return this.adCodeList;
    }

    public HashMap<String, City> getKeyListFromValue(String cityName) {
        Matcher matcher = pattern.matcher(cityName);
        //Logger.e("matcher.matches():" + matcher.matches());
        if (!matcher.matches()) return null;
        keyMap.clear();
        if (cityName != null && !"".equals(cityName)) {
            for (String key : cityMap.keySet()) {
                if (cityMap.get(key).getSimpleInfo().contains(cityName)) {
                    keyMap.put(key, cityMap.get(key));
                    //Logger.e("matched:" + cityMap.get(key).getSimpleInfo());
                }
            }
        }
        return keyMap;
    }

    public String getCNIdFromAdcode(String adcode) {
        if (adcode != null && !"".equals(adcode)) {
            for (String key : cityMap.keySet()) {
                if (cityMap.get(key).getAdcode().equals(adcode)) {
                    Logger.e("matched only key:" + key);
                    return key;
                }
            }
        }
        return "";
    }
    public String getCityNameFromCNId(String CNId) {
        if (CNId != null && !"".equals(CNId)) {
//            if(id.contains("CN")){
//                id = id.substring(id.indexOf("N")+1);
//            }
            Logger.e("id = "+CNId);
            return cityMap.get(CNId).getName();
        }
        return "";
    }

}
