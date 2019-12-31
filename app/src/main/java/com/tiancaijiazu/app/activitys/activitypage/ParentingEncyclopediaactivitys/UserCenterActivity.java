package com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.PhotoChaActivity;
import com.tiancaijiazu.app.activitys.user_fragment.GroupDataFragment;
import com.tiancaijiazu.app.activitys.user_fragment.OriginalFragment;
import com.tiancaijiazu.app.activitys.user_fragment.UserCollectFragment;
import com.tiancaijiazu.app.activitys.user_fragment.activitys.AttentionOfItActivity;
import com.tiancaijiazu.app.activitys.user_fragment.activitys.FansItActivity;
import com.tiancaijiazu.app.activitys.user_fragment.utils.NoScrollBehavior;
import com.tiancaijiazu.app.activitys.views.ComFragmentAdapter;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ConcernBean;
import com.tiancaijiazu.app.beans.PersonalDetailsBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.FastBlurUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.utils.tabmin.ColorFlipPagerTitleViewYx;
import com.tiancaijiazu.app.utils.tabmin.SimplePagerTitleViewYx;
import com.tiancaijiazu.app.mvp.IView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * 家族圈-列表详情-个人评论-进入评论个人信息
 */

public class UserCenterActivity extends BaseActivity<IView, Presenter<IView>> implements IView, AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.iv_backdrop)
    ImageView mIvBackdrop;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.likes_sum)
    TextView mLikesSum;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.fans_sum)
    TextView mFansSum;
    @BindView(R.id.line2)
    LinearLayout mLine2;
    @BindView(R.id.concern_sum)
    TextView mConcernSum;
    @BindView(R.id.line_guanzu)
    LinearLayout mLineGuanzu;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.data_user)
    TextView mDataUser;
    @BindView(R.id.cir_user)
    CircleImageView mCirUser;
    @BindView(R.id.tv_headerView)
    RelativeLayout mTvHeaderView;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.head_iv)
    CircleImageView mHeadIv;
    @BindView(R.id.cb_check)
    CheckBox mCbCheck;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.fen_xiang)
    ImageView mFenXiang;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.baby_age)
    TextView mBabyAge;

    //用来记录内层固定布局到屏幕顶部的距离

    private long mUserId;
    private String[] mTitles = new String[]{"笔记", "日记","收藏"};
    private List<String> mDataList = Arrays.asList(mTitles);
    private NoScrollBehavior myAppBarLayoutBehavoir;
    private boolean isbo;
    private String mId;
    private PersonalDetailsBean.ResultBean mResult;

    @Override
    protected void initEventAndData() {
        mId = PreUtils.getString("userId", "");
        Intent intent = getIntent();
        mUserId = intent.getLongExtra("userId", 0);
        mPresenter.getDataP1(mUserId + "", DifferentiateEnum.USERCENTER, loadingLayout);
        ScreenStatusUtil.setFillDip(this);

        //.bitmapTransform(new BlurTransformation(this, radius))

        initView();
        /*CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setBehavior(new AppBarLayoutSpringBehavior());
        mAppBar.setLayoutParams(params);*/
        mViewPager.setAdapter(new ComFragmentAdapter(getSupportFragmentManager(), getFragments()));
        mViewPager.setOffscreenPageLimit(10);
        initTab();
        mAppBar.addOnOffsetChangedListener(this);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(mUserId + "", DifferentiateEnum.USERCENTER, loadingLayout);
            }
        });
        mLineGuanzu.measure(0, 0);
        int measuredWidth = mLineGuanzu.getMeasuredWidth();
        int i = ScreenStatusUtil.setDp(97, this);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mBabyAge.getLayoutParams();
        layoutParams.leftMargin = (i+measuredWidth/2);
        mBabyAge.setLayoutParams(layoutParams);
        Log.i("yx223", "initEventAndData: "+measuredWidth);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //顶部渐变 标题栏处理
        float percent = Float.valueOf(Math.abs(verticalOffset)) / Float.valueOf(appBarLayout.getTotalScrollRange());
        int alpha = (int) (255 * percent);
        float v = 255 * percent;
        mRelative.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
        int alpha1 = 1 - alpha;
        mTvTitle.setAlpha(alpha1);
        mCbCheck.setAlpha(alpha);
        mHeadIv.setAlpha(v);
        if (percent < 0.5) {
            mIvFinis.setImageResource(R.mipmap.topic_finis);
            mFenXiang.setImageResource(R.mipmap.topic_fenxiang);
        } else {
            mIvFinis.setImageResource(R.mipmap.rec_back);
            mFenXiang.setImageResource(R.mipmap.share);
        }
        //滑动事件处理
        if (percent == 0) {
            //当完全展开时  appbar可滑动  禁止refresh(可根据需求不禁止刷新)
            myAppBarLayoutBehavoir.setNoScroll(false);
        } else {
            //滑动中 appbar可滑动 禁止refresh(建议禁止刷新,否则会appbar影响滑动流畅)
            myAppBarLayoutBehavoir.setNoScroll(false);
        }
    }


    private void initView() {
        myAppBarLayoutBehavoir = (NoScrollBehavior)
                ((CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams()).getBehavior();
    }

    private void initTab() {
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.65f);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewYx simplePagerTitleView = new ColorFlipPagerTitleViewYx(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(UserCenterActivity.this, R.color.colorTextTitleHome));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(UserCenterActivity.this, R.color.colorZhu));
                simplePagerTitleView.setNormalSize(14);
                simplePagerTitleView.setSelectedSize(14);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index, false);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 25));
                indicator.setRoundRadius(UIUtil.dip2px(context, 1));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(ContextCompat.getColor(UserCenterActivity.this, R.color.colorZhu));
                return indicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    private List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        OriginalFragment instance = OriginalFragment.getInstance();
        UserCollectFragment instance1 = UserCollectFragment.getInstance();
        GroupDataFragment instance2 = GroupDataFragment.getInstance();
        fragments.add(instance);
        fragments.add(instance2);
        fragments.add(instance1);
        Bundle bundle = new Bundle();
        bundle.putString("data", mUserId + "");
        instance.setArguments(bundle);
        instance1.setArguments(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putLong("data", mUserId);
        instance2.setArguments(bundle1);
        return fragments;
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case USERCENTER:
                PersonalDetailsBean personalDetailsBean = (PersonalDetailsBean) o;
                mResult = personalDetailsBean.getResult();
                long userId = mResult.getUserId();
                if (mId != null && mCheckbox != null) {
                    List<PersonalDetailsBean.ResultBean.BabyListBean> babyList = mResult.getBabyList();
                    if(babyList.size()!=0){
                        int gender = babyList.get(0).getGender();
                        String birthday = babyList.get(0).getBirthday();
                        String nowTime = TimeUtil.getNowTime();
                        String age = TimeUtil.getAge(birthday, nowTime);
                        if(gender == 1){
                            mBabyAge.setText("男孩 "+age);
                        }else if(gender == 2){
                            mBabyAge.setText("女孩 "+age);
                        }
                    }
                    if (mId.equals("" + userId)) {
                        mCbCheck.setVisibility(View.GONE);
                        mCheckbox.setVisibility(View.GONE);
                        isbo = true;
                    } else {
                        isbo = false;
                        int isFollow = mResult.getIsFollow();
                        if (isFollow == 0) {
                            mCheckbox.setChecked(false);
                            mCbCheck.setChecked(false);
                        } else {
                            mCheckbox.setChecked(true);
                            mCbCheck.setChecked(true);
                        }
                        mCbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                mPresenter.getDataP1(userId + "", DifferentiateEnum.CONCERN, loadingLayout);
                            }
                        });
                        mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                mPresenter.getDataP1(userId + "", DifferentiateEnum.CONCERN, loadingLayout);
                            }
                        });
                    }
                }
                mTvTitle.setText(mResult.getNickname());
                Glide.with(this).load(mResult.getAvatar()).into(mCirUser);
                Glide.with(this).load(mResult.getAvatar()).into(mHeadIv);
                //Glide.with(this).load(mResult.getAvatar()).into(mIvBackdrop);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //下面的这个方法必须在子线程中执行
                        final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(mResult.getAvatar(), 8);
                        //刷新ui必须在主线程中执行
                        App.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                if(mIvBackdrop!=null){
                                    mIvBackdrop.setImageBitmap(blurBitmap2);
                                }
                            }
                        });
                    }
                }).start();
                mConcernSum.setText(mResult.getFollow() + "");
                mLikesSum.setText(mResult.getLikes() + "");
                mFansSum.setText(mResult.getFans() + "");
                String summary = mResult.getSummary();
                if (!"".equals(summary)) {
                    mDataUser.setText(summary);
                }
                break;
            case CONCERN:
                ConcernBean concernBean = (ConcernBean) o;
                String result1 = concernBean.getResult();
                Toast.makeText(mActivity, "" + result1, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.line2, R.id.line_guanzu, R.id.iv_finis, R.id.cir_user})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line2:
                Intent intent1 = new Intent(this, FansItActivity.class);
                if (mId.equals(mUserId + "")) {
                    intent1.putExtra("biao", "my");
                }
                intent1.putExtra("userId", mUserId);
                intent1.putExtra("isbo", isbo);
                startActivity(intent1);
                break;
            case R.id.line_guanzu:
                Intent intent = new Intent(this, AttentionOfItActivity.class);
                if (mId.equals(mUserId + "")) {
                    intent.putExtra("biao", "my");
                }
                intent.putExtra("userId", mUserId);
                intent.putExtra("isbo", isbo);
                startActivity(intent);
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.cir_user:
                Intent intent2 = new Intent(UserCenterActivity.this, PhotoChaActivity.class);
                Log.i("yx456", "onViewClicked: " + mResult.getAvatarLarge());
                intent2.putExtra("image", mResult.getAvatarLarge());
                startActivity(intent2);
                overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                break;
        }
    }

}
