package com.tiancaijiazu.app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.hpplay.sdk.source.browse.api.LelinkServiceInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/4/26/026.
 */

public class RlvAdapter extends RecyclerView.Adapter {
    private ArrayList<LelinkServiceInfo> mData;
    private OnClickLisiter mLisiter;

    public RlvAdapter(ArrayList<LelinkServiceInfo> list) {
        this.mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.sou_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        holder1.mTvSou.setText(mData.get(position).getName());
        holder1.mTvSou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClickName(v,position,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_sou)
        TextView mTvSou;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnClickLisiter{
        void onClickName(View view,int position,ArrayList<LelinkServiceInfo> data);
    }

    public void setOnClickLisiter(OnClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
