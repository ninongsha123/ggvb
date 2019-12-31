package com.tiancaijiazu.app.fragments.outermostlayer.college_child;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/7/007.
 */

public class VpAdapter_vip extends FragmentStatePagerAdapter {
    private List<CollegeParentBean.ResultBean> mTitle;
    private ArrayList<Fragment> mFrag;

    public VpAdapter_vip(FragmentManager fm, List<CollegeParentBean.ResultBean> title, ArrayList<Fragment> fragments) {
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
        return mTitle.get(position).getName();
    }
}
