package com.tiancaijiazu.app.fragments.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/5/10/010.
 */

public class VpAdapter extends FragmentStatePagerAdapter{
    private ArrayList<String> mTitle;
    private ArrayList<Fragment> mFrag;

    public VpAdapter(FragmentManager fm, ArrayList<String> title, ArrayList<Fragment> fragments) {
        super(fm);
        this.mTitle = title;
        this.mFrag = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFrag.get(i);
    }

    @Override
    public int getCount() {
        return mFrag.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
