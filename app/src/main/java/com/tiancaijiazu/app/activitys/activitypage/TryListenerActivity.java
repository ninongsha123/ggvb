package com.tiancaijiazu.app.activitys.activitypage;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.EarlyActivity;
import com.tiancaijiazu.app.activitys.early.datepicker.CustomDatePicker;
import com.tiancaijiazu.app.activitys.early.datepicker.DateFormatUtils;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.activitys.views.TextSwitchView;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AuditionBean;
import com.tiancaijiazu.app.beans.GetCardsBean;
import com.tiancaijiazu.app.beans.StudyCardOrderCreateBenas;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.audition.JZVideoPlayerAudition;
import com.tiancaijiazu.app.utils.audition.MyJZVideoPlayerStandardAudition;
import com.tiancaijiazu.app.utils.audition.systems.MyIJKMediaSystemAudition;
import com.tiancaijiazu.app.utils.audition.systems.MyJZMediaSystemAudition;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TryListenerActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.fen_xiang)
    ImageView mFenXiang;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.get_try_card)
    ImageView mGetTryCard;
    @BindView(R.id.jiaozi_player)
    MyJZVideoPlayerStandardAudition mJiaoziPlayer;
    @BindView(R.id.iv_play)
    ImageView mIvPlay;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.re)
    RelativeLayout mRe;
    @BindView(R.id.nested)
    NestedScrollView mNested;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.textSwitcher)
    TextSwitchView mTextSwitcher;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    private CustomDatePicker mDatePicker;
    private TextView select_text_babyBirthday;
    private Intent intent;
    private StudyCardOrderCreateBenas.ResultBean result1;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;
    private TextView mTv;
    private TextView mBaby_birthday;
    // 要显示的文本
    private int index = 0;

    private boolean isRuning = true;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String referrerCode = PreUtils.getString("referrerCode", "");
            if (shareU.contains("?")) {
                shareU = shareU + "&referrerCode=" + referrerCode;
            } else {
                shareU = shareU + "?referrerCode=" + referrerCode;
            }
            if (msg.what == 199) {
                if (mTextSwitcher != null) {
                    mTextSwitcher.setText(Html.fromHtml(upText()));
                }
            }
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl(shareU, bitmap, title, true,tname,true);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl(shareU, bitmap, title, false,tname,true);
                    break;
            }
        }
    };

    private List<GetCardsBean.ResultBean> mResult;
    private View mInflate;
    private PopupWindow mPopupWindow2;
    private App mApplication;
    private View mInflate1;
    private PopupWindow mPopupWindows;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        intent = getIntent();
        initDatePicker();
        showPop();
        initPop();
        initPop1();
        mRela.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mJZMediaSystem = new MyJZMediaSystemAudition();
        mIJKMediaSystem = new MyIJKMediaSystemAudition();
        mPresenter.getDataP1("", DifferentiateEnum.AUDITION,mLoadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.GETCARDS,mLoadingLayout);
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.AUDITION,mLoadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.GETCARDS,mLoadingLayout);
            }
        });
        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mJiaoziPlayer.startVideo();
                mRela.setVisibility(View.GONE);
            }
        });

    }

    private void initSwitcher() {
        mTextSwitcher.setText(Html.fromHtml(upText()));
        new Thread() {
            @Override
            public void run() {
                while (isRuning) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();

    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();
            return true;
        } else {
            return false;
        }
    }


    private void showHint() {
        mInflate1 = LayoutInflater.from(TryListenerActivity.this).inflate(R.layout.shiting_card_hint, null);
        mPopupWindows = new PopupWindow(mInflate1,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mPopupWindows.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindows.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindows.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindows.setAnimationStyle(R.style.popwin_anim_style);

        Button my_locations = mInflate1.findViewById(R.id.my_locations);

        my_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindows.dismiss();
            }
        });

        mPopupWindows.showAtLocation(mInflate1, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus){
            if (isFirstStart(TryListenerActivity.this)){
                showHint();
            }
        }
    }

    private void initPop() {
        if (select_text_babyBirthday.getText().toString() != null) {
            mPopupWindow1 = new PopupWindow();
            View inflate = LayoutInflater.from(TryListenerActivity.this).inflate(R.layout.get_try_card_babtbirthday, null, false);
            ImageView right = inflate.findViewById(R.id.right);
            ImageView amend = inflate.findViewById(R.id.amend);
            TextView tv = inflate.findViewById(R.id.tv);
            mPopupWindow1.setClippingEnabled(false);
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopupWindow1.dismiss();
                }
            });
            mBaby_birthday = inflate.findViewById(R.id.baby_birthday);
            mPopupWindow1.setContentView(inflate);
            mPopupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow1.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow1.setOutsideTouchable(true);
            mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mPopupWindow1.dismiss();
                }
            });
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> map = new HashMap<>();
                    String s = select_text_babyBirthday.getText().toString();
                    map.put("babyBirthday", s);
                    map.put("cardType", 0);
                    mPresenter.getDataP(map, DifferentiateEnum.STUDYCARDORDERCREATE);
                }
            });
            amend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow1.dismiss();
                }
            });

        } else {
            ToastUtils.showShortToast(TryListenerActivity.this, "还未选择宝宝生日");
        }
    }

    private void showPop() {
        mPopupWindow = new PopupWindow();
        View inflate = LayoutInflater.from(TryListenerActivity.this).inflate(R.layout.get_try_card, null, false);
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        ImageView try_right = inflate.findViewById(R.id.try_right);
        mTv = inflate.findViewById(R.id.tv);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        select_text_babyBirthday = inflate.findViewById(R.id.select_text_babyBirthday);
        mApplication = (App) getApplication();
        String birtoday = mApplication.getBirtoday();
        String s = select_text_babyBirthday.getText().toString();
        if (birtoday.equals("")){
            select_text_babyBirthday.setText("选择宝宝出生日期");
        }else {
            select_text_babyBirthday.setText(birtoday);
        }
        select_text_babyBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = select_text_babyBirthday.getText().toString();
                String nowThree = TimeUtil.getNowThree();
                if ("".equals(time)) {
                    mDatePicker.show(nowThree);
                } else {
                    mDatePicker.show(time);
                }
            }
        });
        mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow.dismiss();
            }
        });

        try_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = select_text_babyBirthday.getText().toString();
                if ("选择宝宝出生日期".equals(s)) {
                    Toast.makeText(mActivity, "请选择宝宝生日", Toast.LENGTH_SHORT).show();
                } else {
                    mBaby_birthday.setText("宝宝生日：" + s);
                    mPopupWindow1.showAtLocation(mIvFinis, Gravity.CENTER, 0, 0);
                }
            }
        });
        initNest();
    }

    String title ="天才家族-家庭早教课试听卡免费抢购中";
    String tname="天才家族APP";
    String mPicture ="";
    String shareU="http://m.h5.tiancaijiazu.com/college/audition.html";
    public void initPop1() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow2 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow2.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow2.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow2.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow2.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow2.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(mPicture)){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    String referrerCode = PreUtils.getString("referrerCode", "");
                    if (shareU.contains("?")) {
                        shareU = shareU + "&referrerCode=" + referrerCode;
                    } else {
                        shareU = shareU + "?referrerCode=" + referrerCode;
                    }
                    WechatShareTool.shareToWXUrl(shareU, bitmap, title, true, tname, false);
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mPicture).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=1;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(mPicture)){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    String referrerCode = PreUtils.getString("referrerCode", "");
                    if (shareU.contains("?")) {
                        shareU = shareU + "&referrerCode=" + referrerCode;
                    } else {
                        shareU = shareU + "?referrerCode=" + referrerCode;
                    }
                    WechatShareTool.shareToWXUrl(shareU, bitmap, title, false,tname,false);
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mPicture).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=2;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

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


    private void initNest() {
        mNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                boolean b = mLine.getLocalVisibleRect(new Rect());

                if (b == false) {
                    if (MyJZVideoPlayerStandardAudition.b == 1) {
                        JZVideoPlayerAudition.quitFullscreenOrTinyWindow();
                    }
                    MyJZVideoPlayerStandardAudition.b = 0;
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_try_listener;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

        switch (differentiateEnum) {
            case STUDYCARDORDERCREATE:
                StudyCardOrderCreateBenas studyCardOrderCreateBenas = (StudyCardOrderCreateBenas) o;
                result1 = studyCardOrderCreateBenas.getResult();
                if (studyCardOrderCreateBenas.getCode().equalsIgnoreCase("0")) {
                    if ("ok".equals(studyCardOrderCreateBenas.getMsg())) {
                        ToastUtils.showShortToast(TryListenerActivity.this, "领取成功");
                    }
                    Intent intent = new Intent(TryListenerActivity.this, EarlyActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case AUDITION:
                AuditionBean auditionBean = (AuditionBean) o;
                String vidoUri = auditionBean.getResult().getVidoUri();
                String cover = auditionBean.getResult().getCover();
                Log.i("yx332", "show: "+cover);
                Glide.with(getApplicationContext()).load(cover).into(mJiaoziPlayer.thumbImageView);
                mJiaoziPlayer.setUp(vidoUri, JZVideoPlayerAudition.SCREEN_WINDOW_NORMAL, "");
                break;
            case GETCARDS:
                GetCardsBean getCardsBean = (GetCardsBean) o;
                mResult = getCardsBean.getResult();
                initSwitcher();
                break;
        }
    }

    public String upText() {
        index++;
        if(mResult!=null){
            if (index > mResult.size() - 1) {
                index = 0;
            }
            int cardNo = mResult.get(index).getCardNo();
            String nickName = mResult.get(index).getNickName();
            String text = "第<font color='#00D9D1'>" + cardNo + "</font>名用户 " + nickName + "免费领取了试听课";
            return text;
        }
        return "";
    }

    //系统播放器引擎
    MyJZMediaSystemAudition mJZMediaSystem;
    MyIJKMediaSystemAudition mIJKMediaSystem;

    @Override
    public void showError(String error) {

    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayerAudition.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayerAudition.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyJZVideoPlayerStandardAudition.c == 1) {
                MyJZVideoPlayerStandardAudition.backButton.performClick();
                MyJZVideoPlayerStandardAudition.c = 0;
            } else {
                setResult(24,intent);
                finish();
            }
        }

        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayerAudition.releaseAllVideos();
        JZVideoPlayerAudition.setMediaInterface(mIJKMediaSystem);
        JZVideoPlayerAudition.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        JZVideoPlayerAudition.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    public void onResume() {
        super.onResume();
        JZVideoPlayerAudition.setMediaInterface(mIJKMediaSystem);
        initPlayer();

    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String s = DateFormatUtils.long2Str(timestamp, false);
                Log.d("bjg", "onTimeSelected: " + s);
                String nowThree = TimeUtil.getNowThree();
                boolean b = TimeUtil.compareTwoTime(s, nowThree);
                if (b) {
                    select_text_babyBirthday.setText(s);
                    select_text_babyBirthday.setTextColor(Color.parseColor("#333333"));
                } else {
                    Toast.makeText(mActivity, "您当前选择日期大于当天，请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
        }, beginTimestamp, endTimestamp);
        mDatePicker.setTitle("选择宝宝生日");
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    protected void onDestroy() {
        isRuning = false;
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
       //Glide.with(getApplicationContext()).pauseRequests();
    }

    @OnClick({R.id.iv_finis, R.id.get_try_card,R.id.fen_xiang})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                setResult(24,intent);
                finish();
                break;
            case R.id.get_try_card:
                mPopupWindow.showAtLocation(mGetTryCard, Gravity.CENTER, 0, 0);
                break;
            case R.id.fen_xiang:
                mPopupWindow2.showAtLocation(mGetTryCard, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

}
