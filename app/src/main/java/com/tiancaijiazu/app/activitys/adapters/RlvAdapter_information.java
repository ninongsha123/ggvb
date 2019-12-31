package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class RlvAdapter_information extends RecyclerView.Adapter {
    private ArrayList<String> mData;

    public RlvAdapter_information(ArrayList<String> ceShiBeans) {
        this.mData = ceShiBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ourgarden_information, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String s = mData.get(i);
        holder.mName.setText(s);
        if("0元体验".equals(s)){
            holder.mIv.setImageResource(R.mipmap.zero);
        }else if("免费Wi-Fi".equals(s)){
            holder.mIv.setImageResource(R.mipmap.wifi);
        }else if("主题活动".equals(s)){
            holder.mIv.setImageResource(R.mipmap.huodong);
        }else if("小班教学".equals(s)){
            holder.mIv.setImageResource(R.mipmap.xiaoban);
        }else if("寄存区".equals(s)){
            holder.mIv.setImageResource(R.mipmap.jicun);
        }else if("家长休息区".equals(s)){
            holder.mIv.setImageResource(R.mipmap.jiazhang);
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(ArrayList<String> list) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView mIv;
        @BindView(R.id.name)
        TextView mName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
