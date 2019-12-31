package com.tiancaijiazu.app.activitys.evalua.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;


public class Testadapter extends PagerAdapter {

    private Context mContext;
    private ArrayList<View> views;

    public Testadapter(Context context, ArrayList<View> views) {
       this.mContext = context;
        this.views = views;
    }

    @Override
    public int getCount() {
        return views.size();
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        container.addView(views.get(position));
        return views.get(position);
    }
}
