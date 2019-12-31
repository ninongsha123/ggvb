package com.tiancaijiazu.app.utils.tabmin;

import android.content.Context;

/**
 * Created by Administrator on 2019/8/16/016.
 */

public class ColorFlipPagerTitleViewYx extends SimplePagerTitleViewYx {
    private float mChangePercent = 0.5f;

    public ColorFlipPagerTitleViewYx(Context context) {
        super(context);
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        if (leavePercent >= mChangePercent) {
            setTextColor(mNormalColor);
            setTextSize(mNormalSize);
            //setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            setTextColor(mSelectedColor);
            setTextSize(mSelectedSize);

        }
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        if (enterPercent >= mChangePercent) {
            setTextColor(mSelectedColor);
            setTextSize(mSelectedSize);
            //AssetManager mgr= getContext().getAssets();
            //Typeface fromAsset = Typeface.createFromAsset(mgr, "sourcehansans_medium.ttf");//思源黑体
        } else {
            setTextColor(mNormalColor);
            setTextSize(mNormalSize);
        }
    }

    @Override
    public void onSelected(int index, int totalCount) {
    }

    @Override
    public void onDeselected(int index, int totalCount) {
    }

    public float getChangePercent() {
        return mChangePercent;
    }

    public void setChangePercent(float changePercent) {
        mChangePercent = changePercent;
    }
}
