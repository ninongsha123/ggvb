package com.tiancaijiazu.app.activitys.take.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.take.NoteTakingActivity;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_Photo_Nine extends RecyclerView.Adapter {
    public ArrayList<ImageItem> mData;
    private int maxImgCount;
    private NoteTakingActivity mContext;
    public boolean isAdded = true;
    public boolean isbo = true;
    private OnRecyclerViewItemClickListener listener;

    public RlvAdapter_Photo_Nine(ArrayList<ImageItem> imageItems, int maxImgCount, NoteTakingActivity noteTakingActivity) {
        this.mData = imageItems;
        this.maxImgCount = maxImgCount;
        this.mContext = noteTakingActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_photo_nine_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int screenWidth = ScreenUtil.getInstance(mContext).getScreenWidth();
        int i1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, mContext.getResources().getDisplayMetrics());
        int i2 = (screenWidth - i1) / 3;
        ViewGroup.LayoutParams params = holder.mIvImg.getLayoutParams();
        params.height = i2;
        params.width = i2;
        holder.mIvImg.setLayoutParams(params);
        if (i == 9) {
            holder.mIvImg.setVisibility(View.GONE);
        }
        if (isAdded && i == getItemCount() - 1) {
            holder.mIvImg.setImageResource(R.drawable.selector_image_add);
            holder.mMinShan.setVisibility(View.GONE);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, NoteTakingActivity.IMAGE_ITEM_ADD);
                    }
                }
            });
        } else {
            holder.mMinShan.setVisibility(View.VISIBLE);
            holder.mMinShan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAdded == false) {
                        mData.remove(i);
                        mData.add(new ImageItem());
                        isAdded = true;
                        notifyDataSetChanged();
                    } else {
                        mData.remove(i);
                        isAdded = true;
                        notifyDataSetChanged();
                    }

                }
            });
            Glide.with(mContext).load(mData.get(i).path).into(holder.mIvImg);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, i);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setImg(ArrayList<ImageItem> images) {
        this.mData = images;
        if (isbo) {
            mData.add(new ImageItem());
            isbo = false;
        }
        isAdded = true;
        notifyDataSetChanged();
    }

    public void setImages(ArrayList<ImageItem> images) {
        if (mData != null) {
            mData.clear();
        }
        if (images.size() < 9) {
            this.mData.addAll(images);
            this.mData.add(new ImageItem());
            isAdded = true;
        } else {
            this.mData.addAll(images);
            isAdded = false;
            isbo = true;
        }

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_img)
        RoundedImageView mIvImg;
        @BindView(R.id.min_shan)
        ImageView mMinShan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }
}
