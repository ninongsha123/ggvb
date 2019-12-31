package com.tiancaijiazu.app.fragments.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tiancaijiazu.app.R;

/**
 * Created by Administrator on 2019/5/8/008.
 */

@SuppressLint("AppCompatCustomView")
public class ArcView extends ImageView {
    /*
     *弧形高度
     */ private int mArcHeight;
    private static final String TAG = "ArcImageView";

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ArcImageView);
        mArcHeight = typedArray.getDimensionPixelSize(R.styleable.ArcImageView_arcHeight, 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 0);
        path.quadTo(getWidth() / 2, getHeight() - 2 * mArcHeight, getWidth(), 0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

}

