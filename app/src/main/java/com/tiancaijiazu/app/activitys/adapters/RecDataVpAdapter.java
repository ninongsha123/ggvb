package com.tiancaijiazu.app.activitys.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.RecDataActivity;
import com.tiancaijiazu.app.beans.ArticleDatas;

import java.util.List;

/**
 * Created by Administrator on 2019/5/29/029.
 */

public class RecDataVpAdapter extends PagerAdapter{
    private int screenWidth;
    public List<ArticleDatas.ResultBean.ImgListBean> mData;
    private RecDataActivity mContext;
    private onClickLisiter mLisiter;
    private int[] imgheights;

    public RecDataVpAdapter(List<ArticleDatas.ResultBean.ImgListBean> imgList, RecDataActivity recDataActivity, int screenWidth) {
        this.mData = imgList;
        this.mContext = recDataActivity;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        if (imgheights == null || imgheights.length != mData.size()) {
            imgheights = null;
            imgheights = new int[mData.size()];
        }
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(mContext).load(mData.get(position).getImgUri()).into(new ImageViewTarget<Drawable>(imageView) {
            @Override
            protected void setResource(@Nullable Drawable resource) {
                if (resource != null) {
                    float scale = (float) resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                    imgheights[position] = (int) (scale * screenWidth);
                    imageView.setImageDrawable(resource);
                } else {
                    //Toast.makeText(MainActivity.this, "图片为空", Toast.LENGTH_LONG).show();
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    public interface onClickLisiter{
        void onClicker(View view,int position,List<ArticleDatas.ResultBean.ImgListBean> mData);
    }
    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
