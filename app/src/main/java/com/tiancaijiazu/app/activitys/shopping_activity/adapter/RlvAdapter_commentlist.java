package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_commentlist extends RecyclerView.Adapter {

    private final ArrayList<String> mDataImage;
    private Context context;
    private setOnItemClickListener listener;

    public RlvAdapter_commentlist(ArrayList<String> split) {
        this.mDataImage=split;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.shop_data_two_commentlist, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        Glide.with(context).load(mDataImage.get(i)).into(holder.mIv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (listener!=null){
                   listener.setOnItemClick(v,i,mDataImage);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataImage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv)
        ImageView mIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface  setOnItemClickListener{
        void setOnItemClick(View v ,int position,ArrayList<String> mDataImage);
    }
    public void setOnItemLickListener(setOnItemClickListener listener){
        this.listener=listener;
    }
}
