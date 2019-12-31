package com.tiancaijiazu.app.activitys.musiclrc;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by entalent on 2015/12/19.
 */
public class LyricView extends ScrollView {

    LinearLayout rootView;
    LinearLayout lyricList;
    ArrayList<TextView> lyricItems = new ArrayList<>();

    public List<LyricContent> mLrcList = new ArrayList<LyricContent>();

    ArrayList<Integer> lyricItemHeights;

    int height;
    int width;

    int prevSelected = 0;

    OnLyricScrollChangeListener listener;

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = new LinearLayout(getContext());
        rootView.setOrientation(LinearLayout.VERTICAL);
        final ViewTreeObserver vto = rootView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = LyricView.this.getHeight();
                width = LyricView.this.getWidth();
                refreshRootView();
            }
        });
        addView(rootView);
    }

    void refreshRootView() {
        rootView.removeAllViews();
        LinearLayout blank1 = new LinearLayout(getContext()),
                blank2 = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height / 2);
        rootView.addView(blank1, params);
        if(lyricList != null)
            rootView.addView(lyricList);
        rootView.addView(blank2, params);
    }

    void refreshLyricList() {

        if(lyricList == null)
            lyricList = new LinearLayout(getContext());
        lyricList.setOrientation(LinearLayout.VERTICAL);
        lyricList.removeAllViews();
        lyricItems.clear();
        lyricItemHeights = new ArrayList<>();
        prevSelected = 0;

        for(int i = 0; i < mLrcList.size(); i++) {
            final TextView textView = new TextView(getContext());
            textView.setText(mLrcList.get(i).Lyric);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            final ViewTreeObserver vto = textView.getViewTreeObserver();
            final int index = i;
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    lyricItemHeights.add(index, textView.getHeight());
                }
            });
            lyricList.addView(textView);
            lyricItems.add(index, textView);
        }

        refreshRootView();
    }

    public void scrollToIndex(int index) {
        if(index < 0) {
            scrollTo(0, 0);
        }
        if(index < mLrcList.size()) {
            int sum = 0;
            for(int i = 0; i <= index - 1; i++){
                sum += lyricItemHeights.get(i);
            }
            sum += lyricItemHeights.get(index) / 2;
            scrollTo(0, sum);
        }
    }

    int getIndex(int length) {
        int index = 0;
        int sum = 0;
        while(sum <= length) {
            sum += lyricItemHeights.get(index);
            index++;
        }
        return index - 1;
    }

    void setSelected(int index) {
        if(index == prevSelected)
            return;
        for(int i = 0; i < lyricItems.size(); i++) {
            if(i == index)
                lyricItems.get(i).setTextColor(Color.parseColor("#333333"));
            else
                lyricItems.get(i).setTextColor(Color.parseColor("#999999"));
        }
        prevSelected = index;
    }

    public void setLyricText(List<LyricContent> textList) {

        this.mLrcList = textList;
        refreshLyricList();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setSelected(getIndex(t));
        if(listener != null) {
            listener.onLyricScrollChange(getIndex(t), getIndex(oldt));
        }
    }

    public void setOnLyricScrollChangeListener(OnLyricScrollChangeListener i) {
        listener = i;
    }

    public interface OnLyricScrollChangeListener {
        void onLyricScrollChange(int index, int oldindex);
    }
}
