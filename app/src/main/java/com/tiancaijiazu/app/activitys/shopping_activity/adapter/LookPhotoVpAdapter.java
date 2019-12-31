package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.adapters.PhotoVpAdapter;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;

import java.util.ArrayList;

public class LookPhotoVpAdapter extends PagerAdapter {
    private  ArrayList<String> mImageData;
    private  LookPhotoActivity mContext;
    private onClickLisiter mLisiter;

    public LookPhotoVpAdapter(ArrayList<String> imagesData, LookPhotoActivity lookPhotoActivity) {
        this.mContext=lookPhotoActivity;
        this.mImageData=imagesData;
    }

    @Override
    public int getCount() {
        return mImageData.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_change, null, false);
        PhotoView img2 = inflate.findViewById(R.id.img2);
        img2.enableRotate();
        img2.enable();
        Glide.with(mContext).load(mImageData.get(position)).into(img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker();
                }
            }
        });
        container.addView(inflate);
        return inflate;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public interface onClickLisiter{
        void onClicker();
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
