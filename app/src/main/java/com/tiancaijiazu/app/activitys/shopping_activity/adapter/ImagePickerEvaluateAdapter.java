package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.shopping_activity.EvaluateActivity;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagePickerEvaluateAdapter extends RecyclerView.Adapter {
    public ArrayList<ImageItem> mData;
    private int maxImgCount;
    public boolean isAdded = true;
    private EvaluateActivity mContext;
    private OnRecyclerViewItemClickListener listener;
    private boolean isbo = true;

    public ImagePickerEvaluateAdapter(EvaluateActivity evaluateActivity, ArrayList<ImageItem> imageItems, int maxImgCount) {
        this.mContext = evaluateActivity;
        this.mData = imageItems;
        this.maxImgCount = maxImgCount;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image_evaluate, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        ViewHolder holder = (ViewHolder) viewHolder;
        Log.i("yx=", "onBindViewHolder: " + i);
        int screenWidth = ScreenUtil.getInstance(mContext).getScreenWidth();
        int i1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, mContext.getResources().getDisplayMetrics());
        int i2 = (screenWidth - i1) / 4;
        ViewGroup.LayoutParams params = holder.mIvImg.getLayoutParams();
        params.height = i2;
        params.width = i2;
        holder.mIvImg.setLayoutParams(params);
        if (i == 8) {
            holder.mIvImg.setVisibility(View.GONE);
        }
        if (isAdded && i == getItemCount() - 1) {
            holder.mIvImg.setImageResource(R.mipmap.add_picture);
            holder.mMinShan.setVisibility(View.GONE);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, EvaluateActivity.IMAGE_ITEM_ADD);
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
                        //setImages(mData);
                        mData.add(new ImageItem());
                        isAdded = true;
                        notifyDataSetChanged();
                    } else {
                        mData.remove(i);
                        //setImages(mData);
                        isAdded = true;
                        notifyDataSetChanged();
                    }

                    /*notifyItemRemoved(i);
                    notifyItemRangeChanged(i, mData.size() - (i + 1));*/
                }
            });
            //ImagePicker.getInstance().getImageLoader().displayImage((Activity) mContext, mData.get(i).path, holder.mIvImg, i2, i2);
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

    public void setImg(ArrayList<ImageItem> images){
        this.mData = images;
        if(isbo){
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
        if (images.size() < 8) {
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

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
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
}
