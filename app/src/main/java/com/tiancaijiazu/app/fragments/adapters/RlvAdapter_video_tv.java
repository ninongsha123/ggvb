package com.tiancaijiazu.app.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;
import com.tiancaijiazu.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/24/024.
 */

public class RlvAdapter_video_tv extends RecyclerView.Adapter {
    private ArrayList<LelinkServiceInfo> mData;
    private onClickLisiterTv mLisiter;

    public RlvAdapter_video_tv(ArrayList<LelinkServiceInfo> list) {
        this.mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tv_name_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTv.setText(mData.get(i).getName());
        Log.i("yx456", "onBindViewHolder: "+mData.get(i).getIp());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClickerTv(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<LelinkServiceInfo> list) {
        if(mData.size()==list.size()){

        }else {
            if(mData!=null){
                mData.clear();
            }
            this.mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterTv{
        void onClickerTv(View view,int position,ArrayList<LelinkServiceInfo> mData);
    }

    public void setOnClickLisiterTv(onClickLisiterTv lisiterTv){
        this.mLisiter = lisiterTv;
    }
}
