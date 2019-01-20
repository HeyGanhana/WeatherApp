package com.example.bilin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilin.R;
import com.example.bilin.bean.WeatherDailyForecast;
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

public class HorizontalWeatherAdapter extends RecyclerView.Adapter<HorizontalWeatherAdapter
        .ViewHolder> implements View.OnClickListener{
    private List<WeatherDailyForecast> datas;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener listener;
    private List<DialerData> dialerDatas = new ArrayList<>();

    private ImageUtils imageUtils;

    public HorizontalWeatherAdapter(Context context, List<WeatherDailyForecast> list) {
        datas = list;
        initDialerData();
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageUtils = ImageUtils.getInstance(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.tab_fragment_hori_listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        //Log.e("zhangdi", "onCreateViewHolder");
        view.setOnClickListener(this);
        return viewHolder;
    }

    public void initDialerData() {
        for (int i = 0; i < datas.size(); i++) {
            DialerData data1 = new DialerData();
            data1.time = datas.get(i).getDate();
            data1.condition_code = datas.get(i).getCond_code_d();
            data1.condition_text = datas.get(i).getCond_txt_d();
            data1.tmp = datas.get(i).getTmp_max();
            DialerData data2 = new DialerData();
            data2.time = datas.get(i).getDate();
            data2.condition_code = datas.get(i).getCond_code_n();
            data2.condition_text = datas.get(i).getCond_txt_n();
            data2.tmp = datas.get(i).getTmp_min();
            dialerDatas.add(data1);
            dialerDatas.add(data2);
        }
        Logger.e(dialerDatas.size()+"6666");
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DialerData data = dialerDatas.get(position);
        Logger.e(data.toString());
        holder.tv_date.setText(data.condition_text);
        holder.tv_condition.setText(data.tmp+"°");
        holder.imgView.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap = imageUtils.getBitmap("w" + data.condition_code + ".png");
        if (bitmap != null) {
            holder.imgView.setImageBitmap(bitmap);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dialerDatas.size();
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onItemClick(v, (int) v.getTag());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_condition;
        private TextView tv_date;
        private ImageView imgView;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_hori_date);
            tv_condition = itemView.findViewById(R.id.tv_hori_condition);
            imgView = itemView.findViewById(R.id.img_hori_condition);
        }
    }

    class DialerData {
        public String time;
        public String condition_code;
        public String condition_text;
        public String tmp;
    }

}

