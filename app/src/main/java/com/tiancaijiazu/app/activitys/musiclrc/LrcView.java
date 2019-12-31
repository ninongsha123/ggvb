package com.tiancaijiazu.app.activitys.musiclrc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/14/014.
 */

@SuppressLint("AppCompatCustomView")
public class LrcView extends TextView {
    private float width;		//歌词视图宽度
    private float height;		//歌词视图高度
    private TextPaint currentPaint;	//当前画笔对象
    private TextPaint notCurrentPaint;	//非当前画笔对象
    private float textHeight = ScreenStatusUtil.setDp(35,getContext());	//文本高度
    private float textSize = getResources().getDimensionPixelSize(R.dimen.sp_15);		//文本大小
    private int index = 0;		//list集合下标


    public List<LyricContent> mLrcList = new ArrayList<LyricContent>();

    public void setmLrcList(List<LyricContent> mLrcList) {
        this.mLrcList = mLrcList;
    }

    public LrcView(Context context) {
        super(context);
        init();
    }
    public LrcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public LrcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setFocusable(true);		//设置可对焦

        //高亮部分
        currentPaint = new TextPaint();
        currentPaint.setAntiAlias(true);	//设置抗锯齿，让文字美观饱满
        currentPaint.setTextAlign(Paint.Align.CENTER);//设置文本对齐方式

        //非高亮部分
        notCurrentPaint = new TextPaint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 绘画歌词
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(canvas == null) {
            return;
        }

        currentPaint.setColor(Color.parseColor("#333333"));
        notCurrentPaint.setColor(Color.parseColor("#999999"));

        currentPaint.setTextSize(textSize);
        currentPaint.setTypeface(Typeface.SERIF);

        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);

        try {
            setText("");
            StaticLayout layoutopen = new StaticLayout(mLrcList.get(index).getLyric(), currentPaint, ScreenUtil.getInstance(getContext()).getScreenWidth() , Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
            //canvas.drawText(mLrcList.get(index).getLyric(), width / 2, height / 2, currentPaint);
            canvas.save();
            canvas.translate(width / 2, height / 2);
            layoutopen.draw(canvas);
            canvas.restore();
            float tempY = height / 2;
            //画出本句之前的句子
            for(int i = index - 1; i >= 0; i--) {
                //向上推移
                tempY = tempY - textHeight;
                //canvas.drawText(mLrcList.get(i).getLyric(), width / 2, tempY, notCurrentPaint);
                StaticLayout layoutopen1 = new StaticLayout(mLrcList.get(i).getLyric(), notCurrentPaint, ScreenUtil.getInstance(getContext()).getScreenWidth() , Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();
                canvas.translate(width / 2, tempY);
                layoutopen1.draw(canvas);
                canvas.restore();
            }
            tempY = height / 2;
            //画出本句之后的句子
            for(int i = index + 1; i < mLrcList.size(); i++) {
                //往下推移
                tempY = tempY + textHeight;
                //canvas.drawText(mLrcList.get(i).getLyric(), width / 2, tempY, notCurrentPaint);
                StaticLayout layoutopen2 = new StaticLayout(mLrcList.get(i).getLyric(), notCurrentPaint, ScreenUtil.getInstance(getContext()).getScreenWidth() , Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();
                canvas.translate(width / 2, tempY);
                layoutopen2.draw(canvas);
                canvas.restore();
            }
        } catch (Exception e) {
            setText("歌词加载中...");
        }
    }

    /**
     * 当view大小改变的时候调用的方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
