package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.utils.CopyButtonLibrary;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *  我的-推荐码
 *
 */

public class GeneralizeCodeActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.generalize)
    TextView mGeneralize;
    @BindView(R.id.fuzhi)
    TextView mFuzhi;
    @BindView(R.id.fenxiang)
    TextView fenxiang;
    private int referrerCode;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    String s = (String) msg.obj;
                    WechatShareTool.shareText(s, true);
                    break;
                case 2:
                    String s1 = (String) msg.obj;
                    WechatShareTool.shareText(s1, false);
                    break;
            }

        }
    };
    private View mInflate;
    private PopupWindow mPopupWindow1;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        referrerCode = intent.getIntExtra("referrerCode", 0);
        mGeneralize.setText(referrerCode + "");
        initPop();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_generalize_code;
    }


    @OnClick({R.id.iv_finis, R.id.fuzhi, R.id.fenxiang})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.fenxiang:
                //分享推荐码
                mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.fuzhi:
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(this, mGeneralize);
                copyButtonLibrary.init();
                break;
        }
    }
private String scode;
    public void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mGeneralize.getText().toString();
                scode="天才家族育儿早教App推广码："+s;
                Message obtain = Message.obtain();
                obtain.obj = scode;
                obtain.arg1 = 1;
                handler.sendMessage(obtain);
            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = mGeneralize.getText().toString();
                scode="天才家族育儿早教App推广码："+s;
                Message obtain = Message.obtain();
                obtain.obj = scode;
                obtain.arg1 = 2;
                handler.sendMessage(obtain);
            }
        });
        TextView quxiao = mInflate.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
    }
}
