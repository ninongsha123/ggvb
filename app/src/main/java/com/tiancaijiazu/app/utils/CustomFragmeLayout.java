package com.tiancaijiazu.app.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class CustomFragmeLayout extends NestedScrollView {

    private Onitemclicklistener mOnitemclicklistener;
    private int mIsbo;

    public CustomFragmeLayout(@NonNull Context context) {
        this(context, null);
    }

    public CustomFragmeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFragmeLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mOnitemclicklistener!=null){
            mOnitemclicklistener.Onitemclick(ev);
        }
        float y = ev.getY();
        //Log.i("yx----", mIsbo+"onTouch: " + y);
        if (y>=mIsbo) {
            //Log.i("yx----", "dispatchTouchEvent: "+"大");
            return super.dispatchTouchEvent(ev);
        } else {
            //Log.i("yx----", "dispatchTouchEvent: "+"小");
            return false;
        }
    }

    public void addSua(int isobo) {
        this.mIsbo = isobo;
        requestLayout();
        invalidate();
    }

    public interface Onitemclicklistener {
        void Onitemclick(MotionEvent event);
    }

    public void setOnitemclicklistener(Onitemclicklistener onitemclicklistener) {
        this.mOnitemclicklistener = onitemclicklistener;
    }
}
