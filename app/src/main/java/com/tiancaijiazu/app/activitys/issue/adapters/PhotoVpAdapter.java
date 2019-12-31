package com.tiancaijiazu.app.activitys.issue.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.PhotoChaActivity;

/**
 * Created by Administrator on 2019/5/29/029.
 */

public class PhotoVpAdapter extends PagerAdapter{
    private String mData;
    private PhotoChaActivity mContext;
    private onClickLisiter mLisiter;

    public PhotoVpAdapter(String image, PhotoChaActivity photoChaActivity) {
        this.mData = image;
        this.mContext = photoChaActivity;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_change, null, false);
        PhotoView img2 = inflate.findViewById(R.id.img2);
        img2.enableRotate();
        img2.enable();
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker();
                }
            }
        });
        Glide.with(mContext).load(mData).into(img2);
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface onClickLisiter{
        void onClicker();
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
