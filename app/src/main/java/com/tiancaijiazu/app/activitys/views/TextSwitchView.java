package com.tiancaijiazu.app.activitys.views;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.tiancaijiazu.app.R;


/**
 * Created by libin on 16/11/29.
 */

public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private Context mContext;

    public TextSwitchView(Context context) {
        this(context, null);
    }

    public TextSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        init();

    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_in_left));
        this.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right));
    }

    @Override
    public View makeView() {
        TextView tv =new TextView(mContext);
        tv.setTextColor(Color.parseColor("#FFFFFF"));
        tv.setMaxLines(1);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        return tv;
    }
}
