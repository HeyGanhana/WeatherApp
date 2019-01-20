package com.example.bilin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bilin.R;
import com.example.bilin.bean.City;
import com.example.bilin.listener.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangdi on 2018/4/12.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements View
        .OnClickListener {

    private Context mContext;
    private List<City> citys;
    private List<String> cityInfo;
    private LayoutInflater layoutInflater;

    private OnRecyclerItemClickListener onRecyclerItemClickListener;

    public SearchAdapter(Context context, List<City> list) {
        mContext = context;
        citys = list;
        cityInfo = new ArrayList<>();
        for (int i = 0; i < citys.size(); i++) {
            cityInfo.add(citys.get(i).getSimpleInfo());
        }
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.onRecyclerItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.search_fragment_listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        viewHolder.add.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(cityInfo.get(position));
        holder.add.setTag(citys.get(position).getAdcode());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return citys.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            if (onRecyclerItemClickListener != null)
                onRecyclerItemClickListener.onItemAddButtonClicked(v, (String) v.getTag());
        } else {
            if (onRecyclerItemClickListener != null)
                onRecyclerItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageButton add;

        public ViewHolder(View itemView) {
            super(itemView);
            add = itemView.findViewById(R.id.add);
            textView = itemView.findViewById(R.id.tv_search_city);
        }

    }
}
