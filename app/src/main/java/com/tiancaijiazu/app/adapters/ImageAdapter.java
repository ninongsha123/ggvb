package com.tiancaijiazu.app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.SizaUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private List<String> mData;
    private final int mCountLimit = 9;
    private Context mContext;

    public ImageAdapter(List<String> data,Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(SizaUtil.dip2px(parent.getContext(), 95), SizaUtil.dip2px(parent.getContext(), 95));
        params.setMargins(10, 10, 10, 10);
        params.gravity = Gravity.CENTER;
        imageView.setLayoutParams(params);
        return new MyViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
            holder.mImageview.setImageResource(R.mipmap.addbaby);
            holder.mImageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
    }

    @Override
    public int getItemCount() {
        // 满 9张图就不让其添加新图
        if (mData != null && mData.size() >= mCountLimit) {
            return mCountLimit;
        } else {
            return mData == null ? 1 : mData.size() + 1;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageview)
        ImageView mImageview;

        private MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
