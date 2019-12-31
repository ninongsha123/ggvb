package com.tiancaijiazu.app.activitys.issue.widget;

import android.content.Context;
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
import com.tiancaijiazu.app.activitys.issue.MOPPActivity;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================
 * 作    者：ikkong （ikkong@163.com），修改 jeasonlzy（廖子尧）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：微信图片选择的Adapter, 感谢 ikkong 的提交
 * ================================================
 */
public class ImagePickerAdapter extends RecyclerView.Adapter {

    private Context mContext;
    public ArrayList<ImageItem> mData;
    private int maxImgCount;
    private OnRecyclerViewItemClickListener listener;
    private onClikcLisiter mLisiter;
    public boolean isAdded = true;
    public boolean isbo = true;

    public ImagePickerAdapter(Context moppActivity, ArrayList<ImageItem> selImageList, int maxImgCount) {
        this.mContext = moppActivity;
        this.mData = selImageList;
        this.maxImgCount = maxImgCount;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        viewHolder.setIsRecyclable(false);
        ViewHolder holder = (ViewHolder) viewHolder;
        Log.i("yx=", "onBindViewHolder: " + i);
        int screenWidth = ScreenUtil.getInstance(mContext).getScreenWidth();
        int i1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 46, mContext.getResources().getDisplayMetrics());
        int i2 = (screenWidth - i1) / 3;
        ViewGroup.LayoutParams params = holder.mIvImg.getLayoutParams();
        params.height = i2;
        params.width = i2;
        holder.mIvImg.setLayoutParams(params);
        if(mData.size()>1&&i==0){
            holder.mFeng.setImageResource(R.mipmap.feng_mian);
        }
        if (i == 9) {
            holder.mIvImg.setVisibility(View.GONE);
        }
        if(isAdded&&i==getItemCount()-1){
            holder.mIvImg.setImageResource(R.drawable.selector_image_add);
            holder.mMinShan.setVisibility(View.GONE);
            holder.mIvImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(v, MOPPActivity.IMAGE_ITEM_ADD);
                    }
                }
            });
        }else {
            holder.mMinShan.setVisibility(View.VISIBLE);
            holder.mMinShan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isAdded==false){
                        mData.remove(i);
                        //setImages(mData);
                        mData.add(new ImageItem());
                        isAdded = true;
                        notifyDataSetChanged();
                    }else {
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
        if(images.size()<9){
            this.mData.addAll(images);
            this.mData.add(new ImageItem());
            isAdded = true;
        }else {
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
        @BindView(R.id.feng)
        ImageView mFeng;

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

    public interface onClikcLisiter {
        void onClicker(View view, int position, ArrayList<ImageItem> mData);
    }

    public void setOnClickLisiter(onClikcLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}