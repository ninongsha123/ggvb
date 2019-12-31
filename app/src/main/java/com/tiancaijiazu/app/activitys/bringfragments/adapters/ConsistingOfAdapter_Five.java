package com.tiancaijiazu.app.activitys.bringfragments.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.ConsistingOfBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/27/027.
 */

public class ConsistingOfAdapter_Five extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<ConsistingOfBean> mData;
    private onClickLisiter mLisiter;

    public ConsistingOfAdapter_Five(ArrayList<ConsistingOfBean> rlv_one, Context context) {
        this.mData = rlv_one;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.consisting_of_five, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitleFive.setText(mData.get(i).getTitle());
        final Drawable drawable= mContext.getResources().getDrawable(mData.get(i).getIv_title());
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        holder.mTitleFive.setCompoundDrawables(drawable,null,null,null);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title_five)
        TextView mTitleFive;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,ArrayList<ConsistingOfBean> mData);
    }
    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
