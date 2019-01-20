package com.example.bilin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilin.R;
import com.example.bilin.bean.OneDayInForecast7;
import com.example.bilin.listener.OnRecyclerItemClickListener;
import com.example.bilin.utils.ImageUtils;
import com.example.bilin.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 2018/4/7.
 */

/**
 * Created by wjs on 3/29/18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder>
        implements View.OnClickListener {
    private final static int HEAD = 1;
    private final static int NORMAL = 2;
    private final static int FOOTER = 3;
    private final static int CHILD = 4;
    private List<OneDayInForecast7> datas;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;
    private View headView;
    private List<View> headViews = new ArrayList<>();
    private View footerView;
    private ImageUtils imageUtils;

    public WeatherAdapter(Context context, List<OneDayInForecast7> list) {
        datas = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageUtils = ImageUtils.getInstance(context);
        headViews.clear();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Logger.e("onCreateViewHolder:" + viewType);
        View view = inflater.inflate(R.layout.tab_fragment_forecast_list_item, parent, false);
        if (viewType == HEAD)
            return new ViewHolder(headView);

        if (viewType == FOOTER) return new ViewHolder(footerView);
        ViewHolder viewHolder = new ViewHolder(view);
        //Log.e("zhangdi", "onCreateViewHolder");
        view.setOnClickListener(this);
        return viewHolder;
    }

    public void addHeaderView(View headView) {
        this.headView = headView;
        headViews.add(headView);
    }

    public void addFooterView(View footerView) {
        this.footerView = footerView;
    }

    public int getHeadViewCount() {
        return headViews != null ? headViews.size() : 0;
    }

    public int getFooterViewCount() {
        return footerView != null ? 1 : 0;
    }


    @Override
    public int getItemViewType(int position) {
        Logger.e("getItemViewType getHeadViewCount():" + getHeadViewCount() + ",position" +
                position);
        if (position < getHeadViewCount()) return HEAD;
        if (position >= datas.size() + getHeadViewCount()) return FOOTER;
        return NORMAL;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Logger.e("position  onBindViewHolder:" + position);
        if (getItemViewType(position) == HEAD) return;
        if (getItemViewType(position) == FOOTER) return;

        OneDayInForecast7 data = datas.get(position - getHeadViewCount());
        Logger.e(data.toString());
        holder.tv_date.setText(data.getDays());
        holder.tv_week.setText(data.getWeek());
        holder.tv_day_weather.setText(data.getWeather());
        holder.tv_day.setText("白天");
        holder.tv_night.setText("夜晚");

        holder.img_day.setImageResource(R.mipmap.ic_launcher);
        holder.img_night.setImageResource(R.mipmap.ic_launcher);

        holder.tv_tmp.setText(data.getTemperature());

        Bitmap dayBitmap = imageUtils.getBitmap("d3/" + data.getWeather_iconid() + ".jpg");
        Bitmap nightBitmap = imageUtils.getBitmap("n3/" + data.getWeather_iconid1() + ".jpg");
        if (dayBitmap != null) {
            holder.img_day.setImageBitmap(dayBitmap);
        }
        if (nightBitmap != null) {
            holder.img_night.setImageBitmap(nightBitmap);
        }

        holder.itemView.setTag(position);
        Log.e("zhangdi", "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return datas.size() + getHeadViewCount() + getFooterViewCount();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;


    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onItemClick(v, (int) v.getTag());
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_date;
        private TextView tv_week;
        private TextView tv_day_weather;
        private TextView tv_day;
        private TextView tv_night;
        private TextView tv_tmp;
        private ImageView img_day;
        private ImageView img_night;


        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == headView) return;
            if (itemView == footerView) return;

            tv_date = itemView.findViewById(R.id.tv_date);
            tv_week = itemView.findViewById(R.id.tv_week);
            tv_day_weather = itemView.findViewById(R.id.tv_day_weather);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_night = itemView.findViewById(R.id.tv_night);
            img_day = itemView.findViewById(R.id.img_day_icon);
            img_night = itemView.findViewById(R.id.img_night_icon);
            tv_tmp = itemView.findViewById(R.id.tv_day_tmp);
        }
    }


}

