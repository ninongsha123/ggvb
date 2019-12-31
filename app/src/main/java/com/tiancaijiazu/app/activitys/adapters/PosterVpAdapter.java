package com.tiancaijiazu.app.activitys.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.InviteActivity;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PosterVpAdapter extends PagerAdapter {

    private List<String> mData;
    private Context mContext;
    private WebView mImg2;
    private String content;
    private Drawable drawable;

    public PosterVpAdapter(List<String> strings, Context inviteActivity) {
        this.mData = strings;
        this.mContext = inviteActivity;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View inflate = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_csu, null, false);
       mImg2 = inflate.findViewById(R.id.imgs);
        if (mContext instanceof InviteActivity) {
            content = "<img src=\""+mData.get(position)+"\" alt=\"\" />";
            String linkCss1 = "<style type=\"text/css\"> " +
                    "img {" +
                    "width:100%;" +
                    "height:100%;" +
                    "}" +
                    "</style>";
            String html1 = "<html><header>" + linkCss1 + "</header>" + content + "</body></html>";
            mImg2.loadData(html1, "text/html", "UTF-8");
            container.addView(inflate);
        }else {
            content = "<img src=\""+mData.get(position)+"\" alt=\"\" />";
            String linkCss1 = "<style type=\"text/css\"> " +
                    "img {" +
                    "width:100%;" +
                    "height:auto;" +
                    "}" +
                    "</style>";
            String html1 = "<html><header>" + linkCss1 + "</header>" + content + "</body></html>";
            mImg2.loadData(html1, "text/html", "UTF-8");
            container.addView(inflate);
        }
        return inflate;
    }
}
