package com.example.bilin.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangdi on 4/16/18.
 */

public class LifeStyleData implements Parcelable {

    public static final Creator<LifeStyleData> CREATOR = new Creator<LifeStyleData>() {
        @Override
        public LifeStyleData createFromParcel(Parcel in) {
            return new LifeStyleData(in);
        }

        @Override
        public LifeStyleData[] newArray(int size) {
            return new LifeStyleData[size];
        }
    };

    private String[] types = {"cw","comf", "drsg", "flu", "sport", "trav", "uv", "air"};
    private String[] desc = {"洗车指数","舒适度指数", "穿衣指数", "感冒指数", "运动指数", "旅游指数", "紫外线指数",
            "空气指数"};
    private String brf;
    private String txt;
    private String type;
    private String icon;
    public LifeStyleData() {
    }

    protected LifeStyleData(Parcel in) {
        brf = in.readString();
        txt = in.readString();
        type = in.readString();
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(type != null && !"".equals(type)){
            this.icon = type;
            for (int i = 0; i < types.length; i++) {
                if (type.equals(types[i])) {
                    this.type = desc[i];
                }
            }
        }
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brf);
        dest.writeString(txt);
        dest.writeString(type);
    }

    /*"lifestyle": [
        {
            "brf": "舒适",
                "txt": "今天夜间不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。",
                "type": "comf"
        },
        {
            "brf": "较舒适",
                "txt": "建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。",
                "type": "drsg"

        },
        {
            "brf": "少发",
                "txt": "各项气象条件适宜，无明显降温过程，发生感冒机率较低。",
                "type": "flu"
        },
        {
            "brf": "适宜",
                "txt": "天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。",
                "type": "sport"
        },
        {
            "brf": "适宜",
                "txt": "天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。",
                "type": "trav"
        },
        {
            "brf": "弱",
                "txt": "紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。",
                "type": "uv"
        },
        {
            "brf": "较不宜",
                "txt": "较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。",
                "type": "cw"
        },
        {
            "brf": "较差",
                "txt": "气象条件较不利于空气污染物稀释、扩散和清除，请适当减少室外活动时间。",
                "type": "air"
        }
    ],*/

    @Override
    public String toString() {
        return "LifeStyleData{" +
                "brf='" + brf + '\'' +
                ", txt='" + txt + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getComfLife() {
        return "舒适度：" + "    " + this.brf + "\n" + "\n" +
                this.txt;
    }
}
