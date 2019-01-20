package com.example.bilin.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bilin.R;
import com.example.bilin.bean.LifeStyleData;
import com.example.bilin.listener.OnRecyclerItemClickListener;
import com.example.bilin.utils.ImageUtils;
import com.example.bilin.utils.Logger;

import java.util.List;

/**
 * Created by zhangdi on 4/27/18.
 */
public class LifeStyleAdapter extends RecyclerView.Adapter<LifeStyleAdapter.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private List<LifeStyleData> lifeStyleDataList;

    public LifeStyleAdapter(Context context, List<LifeStyleData> lifeStyleDataList) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.lifeStyleDataList = lifeStyleDataList;
    }


    @NonNull
    @Override
    public LifeStyleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.tab_fragment_grid_life_style_item, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LifeStyleAdapter.ViewHolder holder, int position) {
        Bitmap bitmap = ImageUtils.getInstance(mContext).getBitmap(lifeStyleDataList.get
                (position).getIcon()+".png");
        holder.lifeStyleImg.setImageBitmap(bitmap);
        Logger.e("data.tostring:"+lifeStyleDataList.get(position).toString());
        holder.lifeStyleTitle.setText(lifeStyleDataList.get(position).getType());
        holder.lifeStyleSummary.setText(lifeStyleDataList.get(position).getBrf());

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return lifeStyleDataList.size();
    }

    public void setOnItemClickerListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onRecyclerItemClickListener.onItemClick(v, (Integer) v.getTag());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView lifeStyleImg;
        private TextView lifeStyleTitle;
        private TextView lifeStyleSummary;

        public ViewHolder(View itemView) {
            super(itemView);
            lifeStyleImg = itemView.findViewById(R.id.img_life_style);
            lifeStyleTitle = itemView.findViewById(R.id.tv_life_style_title);
            lifeStyleSummary = itemView.findViewById(R.id.tv_life_style_summary);
        }
    }
}
