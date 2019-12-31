package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class VpAdapter_early extends FragmentStatePagerAdapter {

    private List<String> mTitle;
    private List<Fragment> mFrag;

    public VpAdapter_early(FragmentManager fm, List<String> dataList, List<Fragment> fragments) {
        super(fm);
        this.mTitle = dataList;
        this.mFrag = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return mFrag.get(i);
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
