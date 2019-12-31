package com.tiancaijiazu.app.fragments.outermostlayer.view;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.FloatRange;
import android.support.annotation.IntDef;
import android.support.annotation.IntRange;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 具有弹性的ScrollView
 * Created by Administrator on 2019/8/15/015.
 */

public class BounceScrollView extends NestedScrollView {
    private static final int ENABLED_ALL = 0;//默认
    private static final int ENABLED_TOP = 1;
    private static final int ENABLED_BOTTOM = 2;
    private static final int ENABLED_NONE = 3;

    private static final float arg = 1.01F;//加重阻尼效果，辅助手段

    private int mLastY;
    private int mActivePointerId;
    private View mChild;
    private boolean canPullDown;//是否可以下拉
    private boolean canPullUp;//是否可以上拉
    private int mLastFrameValue;//上一帧的值
    private ValueAnimator mAnimator;

    private int mBounceType = ENABLED_ALL;

    private float mBounceFraction = 0.6F;//阻尼系数 介于0~1之间，值越大，阻力越大

    private int maxDistance;//最大滑动距离，默认ScrollView的高度

    private TimeInterpolator mInterpolator;//插值器，默认OvershootInterpolator

    public BounceScrollView(Context context) {
        this(context, null);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mInterpolator = new OvershootInterpolator();
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(400).setInterpolator(mInterpolator);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int animatedValue = (int) animation.getAnimatedValue();
                final int perFrameValue = animatedValue - mLastFrameValue;
                //mChild每次只移动每一帧的值
                mChild.offsetTopAndBottom(perFrameValue);
                mLastFrameValue = animatedValue;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChild = getChildAt(0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) maxDistance = getHeight();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (mAnimator.isStarted()) mAnimator.cancel();
                mActivePointerId = ev.getPointerId(0);
                mLastY = (int) ev.getY();
                canPullDown = isCanPullDown();
                canPullUp = isCanPullUp();
                break;
            case MotionEvent.ACTION_MOVE:
                final int y = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
                int diffY = y - mLastY;
                if ((canPullUp || canPullDown)) {
                    ViewParent parent = getParent();
                    if (parent != null) parent.requestDisallowInterceptTouchEvent(true);
                    move(diffY);
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (canPullDown || canPullUp) {
                    final int scrollY = mChild.getTop();
                    mLastFrameValue = scrollY;
                    mAnimator.setIntValues(scrollY, 0);
                    mAnimator.start();
                }
                mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                final int downActionIndex = ev.getActionIndex();
                mLastY = (int) ev.getY(downActionIndex);
                mActivePointerId = ev.getPointerId(downActionIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                final int upActionIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(upActionIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = upActionIndex == 0 ? 1 : 0;
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                mLastY = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
                break;
        }
        super.dispatchTouchEvent(ev);//分发父view的事件
        return true;
    }

    private void move(int diffY) {
        if (canPullDown && diffY > 0) {
            int childTop = mChild.getTop();
            if (childTop <= maxDistance) {
                //阻尼系数：计算规则y=kx+b
                float k = diffY * (1 - mBounceFraction);
                float x = 1 - (childTop * arg / maxDistance);
                diffY = (int) (k * x);
                mChild.offsetTopAndBottom(diffY);
            }
        }
        if (canPullUp && diffY < 0) {
            int childBottom = mChild.getBottom() - mChild.getHeight();
            int absChildBottom = Math.abs(childBottom);
            if (absChildBottom <= maxDistance) {
                //阻尼系数：计算规则y=kx+b
                float k = diffY * (1 - mBounceFraction);
                float x = 1 - (absChildBottom * arg / maxDistance);
                diffY = (int) (k * x);
                mChild.offsetTopAndBottom(diffY);
            }
        }
    }

    /**
     * 是否可以下拉
     */
    private boolean isCanPullDown() {
        if (mBounceType == ENABLED_NONE || mBounceType == ENABLED_BOTTOM) return false;
        return mChild != null && getScrollY() == 0;
    }

    /**
     * 是否可以上拉
     */
    private boolean isCanPullUp() {
        if (mBounceType == ENABLED_NONE || mBounceType == ENABLED_TOP) return false;
        if (mChild == null) return false;
        boolean isCanPullUp = true;
        if (mChild.getHeight() - getHeight() > 0) {
            isCanPullUp = mChild.getHeight() - getHeight() == getScrollY();
        }
        return isCanPullUp;
    }

    public float getBounceFraction() {
        return mBounceFraction;
    }

    public void setBounceFraction(@FloatRange(from = 0, to = 1) float bounceFraction) {
        mBounceFraction = bounceFraction;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(@IntRange(from = 0) int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        if (interpolator == null || interpolator == mAnimator.getInterpolator()) return;
        mAnimator.setInterpolator(interpolator);
        mInterpolator = interpolator;
    }

    public int getBounceType() {
        return mBounceType;
    }

    public void setBounceType(@BounceType int bounceType) {
        mBounceType = bounceType;
    }

    /**
     * 必要时调用该方法销毁mAnimator，防止内存泄露
     */
    public void destroy() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }


    @IntDef({ENABLED_ALL, ENABLED_TOP, ENABLED_BOTTOM, ENABLED_NONE})
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    public @interface BounceType {
    }
}
