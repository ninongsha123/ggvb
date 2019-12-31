package com.tiancaijiazu.app.homepagefragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.InviteActivity;
import com.tiancaijiazu.app.activitys.activitypage.MyTeamActivity;
import com.tiancaijiazu.app.activitys.activitypage.PopularizeClassActivity;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.AlreadyBoughtActivity;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.BianjiziliaoActivity;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.GeneralizeCodeActivity;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.SettingActivity;
import com.tiancaijiazu.app.activitys.collect.MyCollectActivity;
import com.tiancaijiazu.app.activitys.coupons.CouponsActivity;
import com.tiancaijiazu.app.activitys.down.DownLoadListActivity;
import com.tiancaijiazu.app.activitys.feedback.FeedbackCenterActivity;
import com.tiancaijiazu.app.activitys.income.MonthIncomeActivity;
import com.tiancaijiazu.app.activitys.issue.MyReleasedActivity;
import com.tiancaijiazu.app.activitys.issue.PhotoChaActivity;
import com.tiancaijiazu.app.activitys.qi_activitys.BaoActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.DiZhiActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.OrderListActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.PayActivity;
import com.tiancaijiazu.app.activitys.user_fragment.activitys.AttentionOfItActivity;
import com.tiancaijiazu.app.activitys.user_fragment.activitys.FansItActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.IncomeBean;
import com.tiancaijiazu.app.beans.PersonalDetailsBean;
import com.tiancaijiazu.app.beans.UpgradepromotionBean;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.beans.VipOrderBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.CopyButtonLibrary;
import com.tiancaijiazu.app.utils.FastBlurUtil;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.UserInfo;
import www.linwg.org.lib.LCardView;

import static com.tiancaijiazu.app.R.id.change_vip;
import static com.tiancaijiazu.app.R.id.re;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.changePageSelect;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.fragment_now;
import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.mParentingEncyclopediaFragment;

/**
 * A simple {@link Fragment} subclass.
 * 我的
 */
public class MyFragment extends BaseFragment<IView, Presenter<IView>> implements IView, RongIM.UserInfoProvider {

    private static final String TAG = MyFragment.class.getName();
    @BindView(R.id.tv_data)
    TextView mTvData;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.head)
    ImageView mHead;
    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.normal)
    ImageView mNormal;

    @BindView(R.id.bianji)
    ImageView mBianji;
    @BindView(R.id.sex_woman)
    ImageView mSexWoman;
    @BindView(R.id.country)
    TextView mCountry;
    @BindView(R.id.type)
    TextView mType;
    @BindView(R.id.qianming)
    TextView mQianming;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.fs)
    TextView mFs;
    @BindView(R.id.fensi)
    TextView mFansi;
    @BindView(R.id.gz)
    TextView mGz;
    @BindView(R.id.guanzhu)
    TextView mGuanzhu;
    @BindView(R.id.hz)
    TextView mHz;
    @BindView(R.id.huozan)
    TextView mHuozan;
    @BindView(R.id.iv_add_one)
    CircleImageView mIvAddOne;
    @BindView(R.id.baby_name1)
    TextView mBabyName1;
    @BindView(R.id.right_go_one)
    ImageView mRightGoOne;
    @BindView(R.id.add_babycard1)
    LinearLayout mAddBabycard1;
    @BindView(R.id.generalize_code)
    LinearLayout generalize_code;
    @BindView(R.id.add_lcard_one)
    LCardView mAddLcardOne;
    @BindView(R.id.iv_add_two)
    CircleImageView mIvAddTwo;
    @BindView(R.id.baby_name2)
    TextView mBabyName2;
    @BindView(R.id.right_go_two)
    ImageView mRightGoTwo;
    @BindView(R.id.add_lcard_two)
    LCardView mAddLcardTwo;
    @BindView(change_vip)
    ImageView mChangeVip;
    @BindView(R.id.sice)
    TextView mSice;
    @BindView(R.id.siceicon)
    TextView mSiceicon;
    @BindView(R.id.shengji)
    TextView mShengji;
    @BindView(R.id.go)
    ImageView mGo;
    @BindView(R.id.income)
    TextView mIncome;
    @BindView(R.id.peoplenumber)
    TextView mPeoplenumber;
    @BindView(R.id.invite)
    TextView mInvite;
    @BindView(R.id.invite_login)
    LinearLayout mInviteLogin;
    @BindView(R.id.generalize)
    TextView mGeneralize;
    @BindView(R.id.popularize_class)
    LinearLayout mPopularizeClass;
    @BindView(R.id.leader_type)
    LCardView mLeaderType;
    @BindView(R.id.copun)
    LinearLayout mCopun;
    @BindView(R.id.offcaching)
    LinearLayout mOffcaching;
    @BindView(R.id.bought)
    LinearLayout mBought;
    @BindView(R.id.issues)
    LinearLayout mIssues;
    @BindView(R.id.collecti)
    LinearLayout mCollecti;
    @BindView(R.id.indent)
    LinearLayout mIndent;
    @BindView(R.id.dizhi)
    LinearLayout mDizhi;
    @BindView(R.id.service)
    LinearLayout mService;
    @BindView(R.id.feedback)
    LinearLayout mFeedback;
    @BindView(R.id.setting)
    LinearLayout mSetting;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.nested)
    NestedScrollView mNested;
    @BindView(R.id.cir_da)
    CircleImageView mCirDa;
    @BindView(R.id.lcard)
    LCardView mLcard;
    @BindView(R.id.month_income_line)
    LinearLayout mMonthIncomeLine;
    @BindView(R.id.iv_add)
    CircleImageView mIvAdd;
    @BindView(R.id.baby_name)
    TextView mBabyName;
    @BindView(R.id.right_go)
    ImageView mRightGo;
    @BindView(R.id.add_babycard)
    LinearLayout mAddBabycard;
    @BindView(R.id.add_lcard)
    LCardView mAddLcard;
    @BindView(R.id.team_line)
    LinearLayout teamLine;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.baby_age)
    MediumBoldTextViewStandard mBabyAge;
    @BindView(R.id.ma)
    TextView mMa;
    Unbinder unbinder;
    @BindView(R.id.mivbaby)
    ImageView mMivbaby;
    @BindView(R.id.mivbaby1)
    ImageView mMivbaby1;
    @BindView(R.id.mivbaby2)
    ImageView mMivbaby2;

    private int overallXScroll = 0;
    private float height = 540;// 滑动开始变色的高,此高度是由广告轮播或其他首页view高度决定
    private UserInfoBean.ResultBean mResult;
    private int mVipLevel;
    private long mUserid;
    private String mUserName;
    private int referrerCode;
    private String mAvatarLarge;
    private String mAvatar;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private PopupWindow PopupWindows;
    private TextView mCode;
    private UpgradepromotionBean mUpgradepromotionBean;
    //    private StudyCardOrderCreateBenas mStudyCardOrderCreateBenas;
//    private Boolean mCardTypes;
    private VipOrderBean mVipOrderBean;
    private View mInflate1;

    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void showError(String error) {

    }

    //付费升级处理
    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            //头部显示
            case USERINFO:
                UserInfoBean bean = (UserInfoBean) o;
                mResult = bean.getResult();
                mUserid = mResult.getUserid();
                mPresenter.getDataP1(mUserid + "", DifferentiateEnum.USERCENTER, loadingLayout);
                referrerCode = bean.getResult().getReferrerCode();
                mMa.setText("推荐码：" + referrerCode);
                mCode.setText(referrerCode + "");
                mAvatar = mResult.getAvatar();
                PreUtils.putString("userName", bean.getResult().getNickname());
                PreUtils.putString("avatar", mAvatar);
                PreUtils.putString("rongcloudToken", bean.getResult().getRongcloudToken());
                mUserName = bean.getResult().getNickname();
                mTitleName.setText(bean.getResult().getNickname());
                Glide.with(getContext()).load(mResult.getAvatar()).into(mCirDa);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //下面的这个方法必须在子线程中执行
                        final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(mResult.getAvatar(), 8);
                        //刷新ui必须在主线程中执行
                        App.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mIvBack.setImageBitmap(blurBitmap2);
                            }
                        });
                    }
                }).start();
                int gender = mResult.getGender();
                if (gender == 1) {
                    mSexWoman.setImageResource(R.mipmap.manicon);
                } else if (gender == 2) {
                    mSexWoman.setImageResource(R.mipmap.womanicon);
                }
                mVipLevel = mResult.getVipLevel();
                // 判断是否是第一次开启应用
                if (isFirstStart(getContext()) == true&&mVipLevel==0) {
                    // 如果是第一次启动并且vip等级为0
                    showHint();
                } else {
                    creatrLayoutId();
                }
                Log.i("bbb", mVipLevel + "");
                if (mResult.getCity().equals("")) {
                    mV.setVisibility(View.GONE);
                    mCountry.setVisibility(View.GONE);
                } else {
                    mCountry.setVisibility(View.VISIBLE);
                    mV.setVisibility(View.VISIBLE);
                    mCountry.setText(mResult.getCountry());
                }
                mContent.setText(mResult.getSummary());
                mFansi.setText(mResult.getFans() + "");
                mHuozan.setText(mResult.getLikes() + "");
                mGuanzhu.setText(mResult.getFollow() + "");
                if (mVipLevel == 10) {  //预备推广大使
                    String textSource1 = "您已获得免费<br/>升级为<font color='#00DEFF'>终身推广大使</font>的资格";
                    mTvData.setText(Html.fromHtml(textSource1));
                    mChangeVip.setImageResource(R.mipmap.life_ambassador);
                    //  mNormal.setImageResource(R.mipmap.viphuangg);
                    //  mType.setText(mUpgradepromotionBean.getResult());
                } else if (mVipLevel == 11) {  //推广大使
                    mLeaderType.setVisibility(View.VISIBLE);
                    mLcard.setVisibility(View.VISIBLE);
                    mShengji.setVisibility(View.INVISIBLE);
                    mNormal.setVisibility(View.VISIBLE);
                    mGo.setVisibility(View.INVISIBLE);
                    String textSource3 = "成为 <font color='#00DEFF'>终身代言人</font> 可以获得<br/>双倍的推广收益";
                    mTvData.setText(Html.fromHtml(textSource3));
                    mChangeVip.setImageResource(R.mipmap.upgrade_lifetimespokesperson);
                    TextPaint paint = mSiceicon.getPaint();
                    int i = ScreenStatusUtil.setDp(24, getContext());
                    float measureText = paint.measureText(mResult.getVipTitle()) + i;
                    ViewGroup.LayoutParams layoutParams = mSiceicon.getLayoutParams();
                    layoutParams.width = (int) measureText;
                    mSiceicon.setLayoutParams(layoutParams);
                    mSiceicon.setText(mResult.getVipTitle());
                    mNormal.setImageResource(R.mipmap.leader);
                    mType.setText(mResult.getVipTitle());
                } else if (mVipLevel == 21) {  //代言人
                    mLcard.setVisibility(View.GONE);
                    mLeaderType.setVisibility(View.VISIBLE);
                    mNormal.setVisibility(View.VISIBLE);
                    mGo.setVisibility(View.VISIBLE);
                    mShengji.setVisibility(View.VISIBLE);
                    TextPaint paint = mSiceicon.getPaint();
                    int i = ScreenStatusUtil.setDp(24, getContext());
                    float measureText = paint.measureText(mResult.getVipTitle()) + i;
                    ViewGroup.LayoutParams layoutParams = mSiceicon.getLayoutParams();
                    layoutParams.width = (int) measureText;
                    mSiceicon.setLayoutParams(layoutParams);
                    mSiceicon.setText(mResult.getVipTitle());
                    mNormal.setImageResource(R.mipmap.yellow_chapter);
                    mType.setText(mResult.getVipTitle());
                } else if (mVipLevel == 31) {  //园长
                    mLcard.setVisibility(View.GONE);
                    mLeaderType.setVisibility(View.VISIBLE);
                    mNormal.setVisibility(View.VISIBLE);
                    TextPaint paint = mSiceicon.getPaint();
                    int i = ScreenStatusUtil.setDp(24, getContext());
                    float measureText = paint.measureText(mResult.getVipTitle()) + i;
                    ViewGroup.LayoutParams layoutParams = mSiceicon.getLayoutParams();
                    layoutParams.width = (int) measureText;
                    mSiceicon.setLayoutParams(layoutParams);
                    mSiceicon.setText(mResult.getVipTitle());
                    mNormal.setImageResource(R.mipmap.viphuangg);
                    mType.setText(mResult.getVipTitle());
                    mShengji.setVisibility(View.GONE);
                    mGo.setVisibility(View.GONE);
                } else {
                    String textSource = "成为 <font color='#00DEFF'>园长</font> 可以免费学习一年<br/>父母学院和早教学院所有课程（带全年教具）";
                    mTvData.setText(Html.fromHtml(textSource));
                    mChangeVip.setImageResource(R.mipmap.yuanz);
                    //mNormal.setImageResource(R.mipmap.normal_huangg);
                    mType.setText("普通用户");
                }
                //    mPresenter.getDataP1("", DifferentiateEnum.VIPLIST, loadingLayout);
                break;
            //添加宝宝卡
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                List<BabyMessageBean.ResultBean> result = babyMessageBean.getResult();
                if (result.size() == 0) {
                    mAddLcard.setVisibility(View.VISIBLE);
                    mAddLcardOne.setVisibility(View.GONE);
                    mAddLcardTwo.setVisibility(View.GONE);
                    mIvAdd.setImageResource(R.mipmap.addbaby);
                    mBabyName.setText(Html.fromHtml("添加宝宝卡"));
                    mBabyAge.setText("");
                    mMivbaby.setVisibility(View.GONE);
                } else if (result.size() == 1) {
                    mMivbaby.setVisibility(View.VISIBLE);
                    mAddLcard.setVisibility(View.VISIBLE);
                    mAddLcardOne.setVisibility(View.GONE);
                    mAddLcardTwo.setVisibility(View.GONE);
                    String birthday = result.get(0).getBirthday();
                    String nowTime = TimeUtil.getNowTime();
                    String age = TimeUtil.getAge(birthday, nowTime);
                    if (result.get(0).getGender()==1){
                        mMivbaby.setImageDrawable(getResources().getDrawable(R.mipmap.babynb));
                    }else {
                        mMivbaby.setImageDrawable(getResources().getDrawable(R.mipmap.babyrb));
                    }
                    mBabyName.setText(result.get(0).getName());
                    mBabyAge.setText(age);
                    String avatar = result.get(0).getAvatar();
                    if (avatar.equals("")) {
                        mIvAdd.setBackgroundResource(R.mipmap.baby_pic);
                    } else {
                        Glide.with(getContext()).load(avatar).into(mIvAdd);
                    }
                } else {
                    mAddLcardTwo.setVisibility(View.VISIBLE);
                    mAddLcardOne.setVisibility(View.VISIBLE);
                    mAddLcard.setVisibility(View.GONE);
                    mBabyName1.setText(result.get(0).getName());
                    mBabyName2.setText(result.get(1).getName());
                    String avatar = result.get(0).getAvatar();
                    if (avatar.equals("")) {
                        mIvAddOne.setBackgroundResource(R.mipmap.baby_pic);
                    } else {
                        Glide.with(getContext()).load(avatar).into(mIvAddOne);
                    }
                    String avatar1 = result.get(1).getAvatar();
                    if (avatar1.equals("")) {
                        mIvAddTwo.setBackgroundResource(R.mipmap.baby_pic);
                    } else {
                        Glide.with(getContext()).load(avatar1).into(mIvAddTwo);
                    }
                    if (result.get(0).getGender()==1){
                        mMivbaby1.setImageDrawable(getResources().getDrawable(R.mipmap.babynb));
                    }else {
                        mMivbaby1.setImageDrawable(getResources().getDrawable(R.mipmap.babyrb));
                    }
                    if (result.get(1).getGender()==1){
                        mMivbaby2.setImageDrawable(getResources().getDrawable(R.mipmap.babynb));
                    }else {
                        mMivbaby2.setImageDrawable(getResources().getDrawable(R.mipmap.babyrb));
                    }
                }
                break;
            //会员级别列表
//            case VIPLIST:
//                VipListBean vipListBean = (VipListBean) o;
//                List<VipListBean.ResultBean> result1 = vipListBean.getResult();
//                for (int i = 0; i < result1.size(); i++) {
//                    if (mVipLevel == result1.get(i).getVipLevel()) {
//                        if (i < result1.size() - 1) {
//                            mVipLevel = result1.get(i + 1).getVipLevel();
//                        }
//                        break;
//                    }
//                }
//                break;

            //vip升级
            case VIPORDER:
                mVipOrderBean = (VipOrderBean) o;
                String code = mVipOrderBean.getCode();
                if (code.equals("0")) {
                    if (mVipOrderBean.getResult().getTotalFee() != 0) {
                        Log.i("aaa", mVipOrderBean.getResult().getTotalFee() + "");
                        Log.i("aaa", mVipOrderBean.getResult().getVipLevel() + "");
                        Intent intent = new Intent(getContext(), PayActivity.class);
                        intent.putExtra("biao", "vip");
                        intent.putExtra("vipLevel", mVipOrderBean.getResult().getVipLevel());
                        intent.putExtra("totalFee", mVipOrderBean.getResult().getTotalFee() + "");
                        startActivityForResult(intent, 14);
                    } else {
                        bao();
                    }
                }
                break;

            //预备推广大使
            case UPGRADE:
                UpgradepromotionBean upgradepromotionBean = (UpgradepromotionBean) o;
                String code1 = upgradepromotionBean.getCode();
                if (code1.equals("0")) {
                    bao();
                }
                break;


            //升级完后的列表界面(天才家族、升级)
            case INCOME:
                IncomeBean incomeBean = (IncomeBean) o;
                mIncome.setText("¥" + incomeBean.getResult().getIncome_CurrentMonth());
                mPeoplenumber.setText(incomeBean.getResult().getMyteamCount() + "人");
                //mSice.setText("第" + incomeBean.getResult().getOnlineCompanyNo() + "家线上早教园");
                break;
            case USERCENTER:
                PersonalDetailsBean personalDetailsBean = (PersonalDetailsBean) o;
                mAvatarLarge = personalDetailsBean.getResult().getAvatarLarge();
                break;
        }
    }


    private void showPop() {
        mInflate = LayoutInflater.from(getContext()).inflate(R.layout.promotion_code, null);
        mPopupWindow = new PopupWindow(mInflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        TextView cancle = mInflate.findViewById(R.id.cancle);
        TextView play = mInflate.findViewById(R.id.play);
        mCode = mInflate.findViewById(R.id.code);
        //mText = mInflate.findViewById(R.id.text);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CopyButtonLibrary copyButtonLibrary = new CopyButtonLibrary(getContext(), mCode);
                copyButtonLibrary.init();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                WechatShareTool.shareToWXUrl("https://a.app.qq.com/o/simple.jsp?pkgname=com.tiancaijiazu.app",bitmap, "天才家族", false, "天才家族育儿早教App我的推荐码"+referrerCode, true);
//                WechatShareTool.shareText("天才家族推荐码：" + referrerCode, false);
            }
        });
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_my;
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStartd", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStartd", false).commit();
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void initData() {
        String textSource = "成为 <font color='#00DEFF'>终身代言人</font> 可以免费学习<br/>一年父母学院课程和早教学院课程（带全年教具）";
        mTvData.setText(Html.fromHtml(textSource));
        mUserName = PreUtils.getString("userName", "");
        mName.setText(mUserName);
        initRequest();
        initNested();
        showPop();
    }

    private void showHint() {
        mInflate1 = LayoutInflater.from(getContext()).inflate(R.layout.pop_hint_my, null);
        PopupWindows = new PopupWindow(mInflate1,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        PopupWindows.setFocusable(true);// 取得焦点
        PopupWindows.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        PopupWindows.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        PopupWindows.setOutsideTouchable(true);
        //设置可以点击
        PopupWindows.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        PopupWindows.setAnimationStyle(R.style.popwin_anim_style);

        Button my_locations = mInflate1.findViewById(R.id.my_locations);


        my_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupWindows.dismiss();
            }
        });

        PopupWindows.showAtLocation(mInflate1, Gravity.BOTTOM, 0, 0);
    }

    private void initRequest() {
        mPresenter.getDataP1("", DifferentiateEnum.USERINFO, loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.INCOME, loadingLayout);
    }

    public void bao() {
        mPresenter.getDataP1("", DifferentiateEnum.USERINFO, loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.INCOME, loadingLayout);
        //mPresenter.getDataP1("",DifferentiateEnum.UPGRADE,loadingLayout);
    }


    @SuppressLint("NewApi")
    private void initNested() {
        mNested.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int scrollY, int i2, int oldScrollY) {

                overallXScroll = overallXScroll + (scrollY - oldScrollY);// 累加y值 解决滑动一半y值为0

                if (overallXScroll <= 0) {  //未滑动时，设置透明度为0
                    mRelative.setAlpha(0);
                    mRelative.setClickable(false);
                } else if (overallXScroll > 0 && overallXScroll <= height) { //确定一个渐变区域，背景颜色透明度渐变
                    //设置渐变比例
                    float scale = (float) overallXScroll / height;
                    float alpha = (1 * scale);
                    mRelative.setAlpha(alpha);

                } else {//超过渐变区域，透明度都是满的
                    //mNested.addSua(true);
                    mRelative.setAlpha(1);
                    mRelative.setClickable(true);
                }
            }
        });
    }

    private boolean isBottomShow = true;
    private int cades;

    @OnClick({R.id.bianji, change_vip, R.id.popularize_class, R.id.invite_login, R.id.indent, R.id.setting, R.id.bought, R.id.add_lcard_one, R.id.add_lcard_two, R.id.copun
            , R.id.month_income_line, R.id.collecti, R.id.dizhi, R.id.issues, R.id.feedback, R.id.offcaching, R.id.team_line, R.id.service, R.id.iv_back
            , R.id.fs, R.id.fensi, R.id.generalize_code, R.id.gz, R.id.guanzhu, R.id.hz, R.id.huozan, R.id.shengji, R.id.go, R.id.cir_da, R.id.add_lcard, R.id.ma})
    public void onViewClicked(View view) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        switch (view.getId()) {
            case R.id.ma:
                mPopupWindow.showAtLocation(mInflate, Gravity.CENTER, 0, 0);
                setBack();
                break;
            case R.id.shengji:
                if (mVipLevel == 21) {
                    cades = 31;
                }
                mPresenter.getDataP(cades, DifferentiateEnum.VIPORDER);
            case R.id.go:
                mPresenter.getDataP(mVipLevel, DifferentiateEnum.VIPORDER);
                break;
            case R.id.hz:
            case R.id.huozan:
                /*Intent intent13 = new Intent(getContext(), MyCollectActivity.class);
                startActivity(intent13);*/
                break;
            case R.id.gz:
            case R.id.guanzhu:
                Intent intent12 = new Intent(getContext(), AttentionOfItActivity.class);
                intent12.putExtra("userId", mUserid);
                intent12.putExtra("isbo", true);
                intent12.putExtra("biao", "my");
                startActivity(intent12);
                break;
            case R.id.fs:
            case R.id.fensi:
                Intent intent11 = new Intent(getContext(), FansItActivity.class);
                intent11.putExtra("biao", "my");
                intent11.putExtra("userId", mUserid);
                intent11.putExtra("isbo", true);
                startActivity(intent11);
                break;
            case R.id.bianji:
            case R.id.iv_back:
                Intent in = new Intent(getContext(), BianjiziliaoActivity.class);
                in.putExtra("data", (Serializable) mResult);
                startActivityForResult(in, 110);
                break;
            case change_vip:
                Log.i("yx459", "onViewClicked: " + mVipLevel);
                if (mVipLevel == 10) {
                    mPresenter.getDataP(null, DifferentiateEnum.UPGRADE);
                } else {
                    if (mVipLevel == 0) {
                        cades = 21;
                    } else if (mVipLevel == 11) {
                        cades = 21;
                    } else if (mVipLevel == 21) {
                        cades = 31;
                    }

                    mPresenter.getDataP(cades, DifferentiateEnum.VIPORDER);
                }

                break;
            case R.id.indent:
                Intent intent1 = new Intent(getContext(), OrderListActivity.class);
                startActivity(intent1);
                break;
            case R.id.generalize_code:
                Intent intent2 = new Intent(getContext(), GeneralizeCodeActivity.class);
                intent2.putExtra("referrerCode", referrerCode);
                startActivity(intent2);
                break;
            case R.id.setting:
                Intent in1 = new Intent(getContext(), SettingActivity.class);
                in1.putExtra("data", mResult);
                startActivityForResult(in1, 110);
                break;
            case R.id.bought:
                Intent intent = new Intent(getContext(), AlreadyBoughtActivity.class);
                startActivity(intent);
                break;
            case R.id.add_lcard_one:
                inJp();
                break;
            case R.id.invite_login:
                //邀请注册
                startActivity(new Intent(getContext(), InviteActivity.class));
                break;
            case R.id.popularize_class:
                //推广海报
                startActivity(new Intent(getContext(), PopularizeClassActivity.class));
                break;
            case R.id.add_lcard_two:
                inJp();
                break;
            case R.id.copun:
                Intent intent5 = new Intent(getContext(), CouponsActivity.class);
                startActivityForResult(intent5, 110);
                break;
            case R.id.month_income_line:
                Intent intent3 = new Intent(getContext(), MonthIncomeActivity.class);
                startActivity(intent3);
                break;
            case R.id.collecti:
                Intent intent4 = new Intent(getContext(), MyCollectActivity.class);
                startActivity(intent4);
                break;
            case R.id.issues:
                Intent intent15 = new Intent(getContext(), MyReleasedActivity.class);
                startActivity(intent15);
                break;
            case R.id.feedback:
                Intent intent6 = new Intent(getContext(), FeedbackCenterActivity.class);
                startActivity(intent6);
                break;
            case R.id.offcaching:
                Intent intent7 = new Intent(getContext(), DownLoadListActivity.class);
                startActivity(intent7);
                break;
            case R.id.team_line:
                Intent intent8 = new Intent(getContext(), MyTeamActivity.class);
                startActivity(intent8);
                break;
            case R.id.service:
                CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                CSCustomServiceInfo csInfo = csBuilder.nickName("融云").name(mUserName).portraitUrl(mAvatar).
                        referrer("10001").build();
                /*RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
                    @Override
                    public UserInfo getUserInfo(String id) {
                        return new UserInfo(id,mUserName, Uri.parse(mAvatar));
                    }
                }, true);*/
                String rongUser = PreUtils.getString("rongUser", "");
                RongIM.setUserInfoProvider(this, true);
                RongIM.getInstance().setCurrentUserInfo(new UserInfo(rongUser + "", mUserName, Uri.parse(mAvatar)));
                /**
                 * 启动客户服聊天界面。
                 * @param context           应用上下文。
                 * @param customerServiceId 要与之聊天的客服 Id。
                 * @param title             聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
                 * @param customServiceInfo 当前使用客服者的用户信息。{@link CSCustomServiceInfo}
                 */
                PreUtils.putString("rongyun", "my");
                RongIM.getInstance().startCustomerServiceChat(getActivity(), "service", "在线客服", csInfo);
//                toQQ();
                break;
            case R.id.dizhi:
                Intent intent9 = new Intent(getContext(), DiZhiActivity.class);
                startActivity(intent9);
                break;
            case R.id.cir_da:
                Intent intent14 = new Intent(getContext(), PhotoChaActivity.class);
                intent14.putExtra("image", mAvatarLarge);
                startActivity(intent14);
                mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                break;
            case R.id.add_lcard:
                inJp();
                break;
        }
    }

    //设置背景透明度
    private void setBack() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = (float) 0.3; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void connectRongServer(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            //token1参数报错
            @Override
            public void onTokenIncorrect() {
                Log.e("rongyun", "参数错误");
                //Toast.makeText(getContext(), "token1参数报错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                Log.e("rongyun", "成功");
                //Toast.makeText(getContext(), "连接成功 ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("rongyun", "失败");
                //Toast.makeText(getContext(), errorCode.getValue() + "", Toast.LENGTH_SHORT).show();
            }
        });
        RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                Log.i("yxRong", "onChanged: " + connectionStatus.getValue());
            }
        });

    }

    public void toQQ() {
        // 跳转之前，可以先判断手机是否安装QQ
        if (isQQClientAvailable(getActivity())) {
            // 跳转到客服的QQ
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=";
            Intent intents = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            // 跳转前先判断Uri是否存在，如果打开一个不存在的Uri，App可能会崩溃
            if (isValidIntent(getActivity(), intents)) {
                startActivity(intents);
            } else {
                Toast.makeText(getActivity(), "客服QQ异常", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "请先安装QQ客户端", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断用户是否安装QQ客户端
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

    private void inJp() {
        Intent intent = new Intent(getContext(), BaoActivity.class);
        intent.putExtra("biao", "my");
        startActivityForResult(intent, 111);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 110 && resultCode == 120) {
            initData();
        }
        if (requestCode == 111 && resultCode == 124) {
            mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST, loadingLayout);
        }
        Log.i("yx--==", requestCode + "onActivityResult: " + resultCode);
        if (requestCode == 14) {
            bao();
        }
        if (requestCode == 110 && resultCode == 220) {
            if (mParentingEncyclopediaFragment == null) {
                mParentingEncyclopediaFragment = ParentingEncyclopediaFragment.newInstance();
            }
            changePageSelect(3);
            switchFragment(fragment_now, mParentingEncyclopediaFragment);
        }
    }


    /**
     * 隐藏显示fragment
     *
     * @param from 需要隐藏的fragment
     * @param to   需要显示的fragment
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (to == null)
            return;
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.fragment_group, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.fragment_group, to).show(to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;
    }


    @Override
    public UserInfo getUserInfo(String s) {
        return new UserInfo(s, mUserName, Uri.parse(mAvatar));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
