package com.tiancaijiazu.app.activitys.adapters;

import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.utils.shopvideo.JZVideoPlayerShop;
import com.tiancaijiazu.app.utils.shopvideo.MyJZVideoPlayerStandardShop;
import com.tiancaijiazu.app.utils.video.JZVideoPlayerList;
import com.tiancaijiazu.app.utils.video.MyJZVideoPlayerStandardList;

/**
 * Created by Administrator on 2019/5/24/024.
 */

public class VpAdapter_img extends PagerAdapter{

    private TextView mVideoPlay;
    private String[] mData;
    private ShopActivity mContext;
    private String mVideo;
    private int mVpo;

    public VpAdapter_img(ShopActivity context, String[] split, int vpo, TextView videoPlay) {
        this.mContext = context;
        this.mData = split;
        this.mVpo = vpo;
        this.mVideoPlay = videoPlay;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_rec_img, container, false);
        ImageView iv = inflate.findViewById(R.id.iv);
        ImageView cancle = inflate.findViewById(R.id.cancle);

        MyJZVideoPlayerStandardShop jiaoziPlayer = inflate.findViewById(R.id.jiaozi_player);
        if(mVideo!=null){
            if (position == mVpo) {
                jiaoziPlayer.setVisibility(View.VISIBLE);
                iv.setVisibility(View.GONE);
                cancle.setVisibility(View.VISIBLE);
                jiaoziPlayer.setUp(mVideo, JZVideoPlayerList.SCREEN_WINDOW_NORMAL, "");
                jiaoziPlayer.startVideo();
                initPlayer();
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (MyJZVideoPlayerStandardShop.b == 1) {
                            MyJZVideoPlayerStandardShop.quitFullscreenOrTinyWindow();
                        }
                        MyJZVideoPlayerStandardShop.b = 0;
                        jiaoziPlayer.setVisibility(View.GONE);
                        cancle.setVisibility(View.GONE);
                        iv.setVisibility(View.VISIBLE);
                        mVideoPlay.setVisibility(View.VISIBLE);
                    }
                });
            }
        }
        RequestOptions options = new RequestOptions();
        options.dontAnimate().placeholder(iv.getDrawable());
        Glide.with(mContext).load(mData[position]).apply(options).into(iv);
        container.addView(inflate);
        return inflate;
    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayerShop.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayerShop.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public int getCount() {
        return mData.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public void addVideo(String video, int vpo) {
        this.mVpo = vpo;
        this.mVideo = video;
        notifyDataSetChanged();
    }

    private int mChildCount = 0;
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

}
