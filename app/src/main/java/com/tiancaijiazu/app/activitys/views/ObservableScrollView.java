package com.tiancaijiazu.app.activitys.views;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * 监听ScrollView的滑动数据
 * Create by: chenwei.li
 * Date: 2017/8/22
 * time: 11:36
 * Email: lichenwei.me@foxmail.com
 */
public class ObservableScrollView extends NestedScrollView {
    private int lastInterceptX;
    private int lastInterceptY;
    private int mIsbo;
    private boolean isNeedScroll = true;
    private float xDistance, yDistance, xLast, yLast;
    private int scaledTouchSlop;
    public ObservableScrollView(Context context) {
        this(context, null);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    private OnObservableScrollViewScrollChanged mOnObservableScrollViewScrollChanged;

    public void setOnObservableScrollViewScrollChanged(OnObservableScrollViewScrollChanged mOnObservableScrollViewScrollChanged) {
        this.mOnObservableScrollViewScrollChanged = mOnObservableScrollViewScrollChanged;
    }


    public interface OnObservableScrollViewScrollChanged {
        void onObservableScrollViewScrollChanged(int l, int t, int oldl, int oldt);
    }

     @Override
     public boolean onInterceptTouchEvent(MotionEvent ev) {
         switch (ev.getAction()) {
             case MotionEvent.ACTION_DOWN:
                 xDistance = yDistance = 0f;
                 xLast = ev.getX();
                 yLast = ev.getY();
                 break;
             case MotionEvent.ACTION_MOVE:
                 final float curX = ev.getX();
                 final float curY = ev.getY();

                 xDistance += Math.abs(curX - xLast);
                 yDistance += Math.abs(curY - yLast);
                 xLast = curX;
                 yLast = curY;
                 return !(xDistance >= yDistance || yDistance < scaledTouchSlop) && isNeedScroll;

         }
         return super.onInterceptTouchEvent(ev);
     }
    public void addSua(int isbo) {
        this.mIsbo = isbo;
        requestLayout();
        invalidate();
    }
    /*
        该方法用来处理NestedScrollView是否拦截滑动事件
         */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }
    /**
     * @param l    Current horizontal scroll origin. 当前滑动的x轴距离
     * @param t    Current vertical scroll origin. 当前滑动的y轴距离
     * @param oldl Previous horizontal scroll origin. 上一次滑动的x轴距离
     * @param oldt Previous vertical scroll origin. 上一次滑动的y轴距离
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnObservableScrollViewScrollChanged != null) {
            mOnObservableScrollViewScrollChanged.onObservableScrollViewScrollChanged(l, t, oldl, oldt);
        }
    }
}
