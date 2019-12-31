package com.tiancaijiazu.app.activitys.evalua;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.NestedScrollView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.ReviewedListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuidePageActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.back_iv)
    ImageView mBackIv;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.first_play)
    ImageView mFirstPlay;
    @BindView(R.id.record)
    ImageView mRecord;
    @BindView(R.id.start)
    ImageView mStart;
    @BindView(R.id.have)
    LinearLayout mHave;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.nested)
    NestedScrollView mNested;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.share)
    ImageView mShare;
    private int page = 1;
    private int size = 20;
    private int record = 0;
    private List<BabyMessageBean.ResultBean> mData;
    private int biao = 0;
    private String babyIdOne = "";
    private String babyIdTwo = "";
    private int overallXScroll = 0;
    private float height = 540;
    private View mInflate;
    private PopupWindow mPopupWindows;
    private View mInflate1;
    private PopupWindow mPopupWindow1;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);

        Intent intent = getIntent();
        mData = (List<BabyMessageBean.ResultBean>) intent.getSerializableExtra("data");
        if (mData.size() == 1) {
            biao = 1;
            HashMap<String, Object> map = new HashMap<>();
            map.put("babyId", mData.get(0).getBabyId() + "");
            map.put("page", page);
            map.put("size", size);
            mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST, loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST, loadingLayout);
                }
            });
        } else {
            biao = 2;
            HashMap<String, Object> map = new HashMap<>();
            map.put("babyId", mData.get(1).getBabyId() + "");
            map.put("page", page);
            map.put("size", size);
            mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST, loadingLayout);
            /*HashMap<String, Object> map1 = new HashMap<>();
            map.put("babyId", mData.get(1).getBabyId() + "");
            map.put("page", page);
            map.put("size", size);*/
            //mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST,loadingLayout);
            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(map, DifferentiateEnum.REVIEWEDLIST, loadingLayout);
                }
            });
        }


        mNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                overallXScroll = overallXScroll + (scrollY - oldScrollY);// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {  //未滑动时，设置透明度为0
                    mRelative.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    mIvFinis.setImageResource(R.mipmap.rec_back);
                    mTitle.setVisibility(View.GONE);
                } else if (overallXScroll > 0 && overallXScroll <= height) { //确定一个渐变区域，背景颜色透明度渐变
                    //设置渐变比例
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    mRelative.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    mIvFinis.setImageResource(R.mipmap.rec_back);
                    mTitle.setVisibility(View.GONE);
                } else {//超过渐变区域，透明度都是满的
                    mRelative.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    mIvFinis.setImageResource(R.mipmap.rec_back);
                    mTitle.setVisibility(View.VISIBLE);
                }
            }
        });

        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initPop();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (isFirstStart(GuidePageActivity.this)) {
                showHint();
            }
        }
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
        mInflate1 = LayoutInflater.from(GuidePageActivity.this).inflate(R.layout.layout_ceping_hint, null);
        mPopupWindows = new PopupWindow(mInflate1,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindows.setFocusable(true);// 取得焦点
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




    private void initPop() {
        mInflate = LayoutInflater.from(GuidePageActivity.this).inflate(R.layout.pop_share_layout, null);
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
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                String referrerCode = PreUtils.getString("referrerCode", "");
                WechatShareTool.shareToWXUrl("http://m.h5.tiancaijiazu.com/Assess/index.html"+ "?referrerCode=" + referrerCode, bitmap, "天才家族儿童综合发育测评系统", true, "让家长能够清楚的了解孩子在不同年龄段的发育指标以及各项能力发展。通过测评结果，可以减少我们家长在育儿过程中的盲目性和偏差性，及时弥补教育过程当中的缺失并对孩子进行针对性的教育。", true);
            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                String referrerCode = PreUtils.getString("referrerCode", "");

                WechatShareTool.shareToWXUrl("http://m.h5.tiancaijiazu.com/Assess/index.html"+ "?referrerCode=" + referrerCode, bitmap, "天才家族儿童综合发育测评系统", false,"让家长能够清楚的了解孩子在不同年龄段的发育指标以及各项能力发展。通过测评结果，可以减少我们家长在育儿过程中的盲目性和偏差性，及时弥补教育过程当中的缺失并对孩子进行针对性的教育。",true);
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


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case REVIEWEDLIST:
                ReviewedListBean reviewedListBean = (ReviewedListBean) o;
                List<ReviewedListBean.ResultBean> result = reviewedListBean.getResult();
                record += reviewedListBean.getResult().size();
                if (record != 0) {
                    mHave.setVisibility(View.VISIBLE);
                    mFirstPlay.setVisibility(View.GONE);
                } else {
                    mHave.setVisibility(View.GONE);
                    mFirstPlay.setVisibility(View.VISIBLE);
                }
                if (biao == 2) {
                    for (int i = 0; i < result.size(); i++) {
                        if (result.get(i).getBabyId() == mData.get(0).getBabyId()) {
                            babyIdOne = result.get(i).getBabyId() + "";
                        }

                        if (result.get(i).getBabyId() == mData.get(1).getBabyId()) {
                            babyIdTwo = result.get(i).getBabyId() + "";
                        }
                    }
                }

                break;
        }
    }

    @Override
    public void showError(String error) {

    }


    @OnClick({R.id.first_play, R.id.record, R.id.start,R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.first_play:
            case R.id.start:
                Intent intent = new Intent(GuidePageActivity.this, EvaluaBabyActivity.class);
                startActivity(intent);
                break;
            case R.id.record:
                Intent intent2 = new Intent(GuidePageActivity.this, AssessmentRecordsActivity.class);
                if (biao == 1) {
                    intent2.putExtra("babyId", mData.get(0).getBabyId() + "");
                } else if (biao == 2) {
                    if (!"".equals(babyIdOne) && "".equals(babyIdTwo)) {
                        intent2.putExtra("babyId", babyIdOne);
                    } else {
                        intent2.putExtra("babyId", babyIdTwo);
                    }
                }
                intent2.putExtra("data", (Serializable) mData);
                startActivity(intent2);
                break;
            case R.id.share:
                mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;
        }
    }

}
