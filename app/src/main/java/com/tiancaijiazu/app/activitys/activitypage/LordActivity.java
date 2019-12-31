package com.tiancaijiazu.app.activitys.activitypage;



import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.homepagefragment.CollegeFragment;
import com.tiancaijiazu.app.homepagefragment.HomePageFragment;
import com.tiancaijiazu.app.homepagefragment.MyFragment;
import com.tiancaijiazu.app.homepagefragment.ParentingEncyclopediaFragment;
import com.tiancaijiazu.app.homepagefragment.ShoppingMallFragment;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.views.JZVideoPlayer;
import com.tiancaijiazu.app.utils.views.MyJZVideoPlayerStandard;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

import static com.tiancaijiazu.app.homepagefragment.HomePageFragment.mCancle;

/**
 *   主activity-五个fragment的依附页面
 *
 */

public class LordActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    //替换ID
    @BindView(R.id.fragment_group)
    FrameLayout mFragmentGroup;
    //首页图标
    @BindView(R.id.home_iv)
    ImageView mHomeIv;
    //首页文字 id
    @BindView(R.id.home_tv)
    TextView mHomeTv;
    //首页点击事件 id
    @BindView(R.id.home)
    LinearLayout mHome;
    //学院图标
    @BindView(R.id.college_iv)
    ImageView mCollegeIv;
    //学院文字 id
    @BindView(R.id.college_tv)
    TextView mCollegeTv;
    //学院点击事件 id
    @BindView(R.id.college)
    LinearLayout mCollege;
    //天才家族图标
    @BindView(R.id.parentingEncyclopedia_iv)
    ImageView mParentingEncyclopediaIv;
    //天才家族文字 id
    @BindView(R.id.parentingEncyclopedia_tv)
    TextView mParentingEncyclopediaTv;
    //天才家族点击事件
    @BindView(R.id.parentingEncyclopedia)
    LinearLayout mParentingEncyclopedia;
    //社区图标
    @BindView(R.id.shoppingMall_iv)
    ImageView mShoppingMallIv;
    //社区文字 id
    @BindView(R.id.shoppingMall_tv)
    TextView mShoppingMallTv;
    //社区点击事件 id
    @BindView(R.id.shoppingMall)
    LinearLayout mShoppingMall;
    //我的图标
    @BindView(R.id.my_iv)
    ImageView mMyIv;
    //我的文字 id
    @BindView(R.id.my_tv)
    TextView mMyTv;
    //我的点击事件 id
    @BindView(R.id.my)
    LinearLayout mMy;
    //管理导航栏图标和文字
    private static List<ImageView> iv_list;
    private static List<TextView> tv_list;
    //首页Fragment对象
    private HomePageFragment mHomePageFragment;
    //替换Fragment空对象
    public static Fragment fragment_now = null;
    //学院Fragment对象
    public static CollegeFragment mCollegeFragment;
    //天才家族Fragment对象
    public static ParentingEncyclopediaFragment mParentingEncyclopediaFragment;
    //社区Fragment对象
    public static ShoppingMallFragment mShoppingMallFragment;
    //我的Fragment对象
    private MyFragment mMyFragment;
    public static boolean isbo;
    public static boolean sboo;
    public static boolean obo;
    public static LinearLayout mLine;


    @Override
    protected void initEventAndData() {

        DestroyActivityUtil.addDestoryActivityToMap(LordActivity.this, "LordActivity");
        ScreenStatusUtil.setFillDip(this);
        //设置Fragment切换逻辑
        inint();
        mLine = findViewById(R.id.line);
        PreUtils.putString("changed", "yes");
        mPresenter.getDataP("", DifferentiateEnum.USERINFO);
        mPresenter.getDataP("", DifferentiateEnum.PARENTID);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        String cards = PreUtils.getString("lord", "");
        if ("ok".equals(cards)) {
            if (mParentingEncyclopediaFragment == null) {
                mParentingEncyclopediaFragment = ParentingEncyclopediaFragment.newInstance();
            }
            changePageSelect(3);
            switchFragment(fragment_now, mParentingEncyclopediaFragment);
            isPlay();
            mHomePageFragment.GbPlay();
            isbo = true;
            PreUtils.putString("lord", "no");
        }
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected int creatrLayoutId() {
        /*String appName = PackageUtils.getAppName(LordActivity.this);
        UpdateUtils.checkUpdate(LordActivity.this, appName
                , false);*/
        return R.layout.activity_lord;
    }


    //点击事件
    @OnClick({R.id.home, R.id.college, R.id.parentingEncyclopedia, R.id.shoppingMall, R.id.my})
    public void onViewClicked(View view) {
        //点击切换判断方法
        changePageFragment(view.getId());
    }

    //将导航栏图标与文字放入集合统一管理
    private void inint() {
        iv_list = new ArrayList<>();
        tv_list = new ArrayList<>();
        iv_list.add(mHomeIv);
        iv_list.add(mCollegeIv);
        iv_list.add(mParentingEncyclopediaIv);
        iv_list.add(mShoppingMallIv);
        iv_list.add(mMyIv);
        tv_list.add(mHomeTv);
        tv_list.add(mCollegeTv);
        tv_list.add(mParentingEncyclopediaTv);
        tv_list.add(mShoppingMallTv);
        tv_list.add(mMyTv);
        changePageSelect(0);
        if (mHome != null) {
            changePageFragment(R.id.home);
        }
    }

    /**
     * 选中的tab 和 没有选中的tab 的图标和字体颜色
     *
     * @param index
     */
    public static void changePageSelect(int index) {
        for (int i = 0; i < iv_list.size(); i++) {
            if (index == i) {
                iv_list.get(i).setEnabled(false);
                tv_list.get(i).setTextColor(Color.parseColor("#00DEFF"));
            } else {
                iv_list.get(i).setEnabled(true);
                tv_list.get(i).setTextColor(Color.parseColor("#BBBBBB"));
            }
        }
    }

    /**
     * 当点击导航栏时改变 fragment
     *
     * @param id
     */
    public void changePageFragment(int id) {
        switch (id) {
            case R.id.home:
            case R.id.home_iv:
                if (mHomePageFragment == null) {//减少new fragmnet,避免不必要的内存消耗
                    mHomePageFragment = HomePageFragment.newInstance();
                }
                changePageSelect(0);
                switchFragment(fragment_now, mHomePageFragment);
                if (isbo) {
                    mHomePageFragment.KaiPlay();
                    mHomePageFragment.bao();
                }
                break;
            case R.id.college:
            case R.id.college_iv:
                if (mCollegeFragment == null) {
                    mCollegeFragment = CollegeFragment.newInstance();
                }
                //initSet();
                changePageSelect(1);
                switchFragment(fragment_now, mCollegeFragment);
                isPlay();
                mHomePageFragment.GbPlay();
                isbo = true;
                break;
            case R.id.parentingEncyclopedia:
            case R.id.parentingEncyclopedia_iv:
                if (mShoppingMallFragment == null) {
                    mShoppingMallFragment = ShoppingMallFragment.newInstance();
                }
                changePageSelect(2);
                switchFragment(fragment_now, mShoppingMallFragment);
                isPlay();
                mHomePageFragment.GbPlay();
                isbo = true;
                break;
            case R.id.shoppingMall:
            case R.id.shoppingMall_iv:
                if (mParentingEncyclopediaFragment == null) {
                    mParentingEncyclopediaFragment = ParentingEncyclopediaFragment.newInstance();
                }
                changePageSelect(3);
                switchFragment(fragment_now, mParentingEncyclopediaFragment);
                isPlay();
                mHomePageFragment.GbPlay();
                isbo = true;
                break;
            case R.id.my:
            case R.id.my_iv:
                if (mMyFragment == null) {
                    mMyFragment = MyFragment.newInstance();
                }
                changePageSelect(4);
                switchFragment(fragment_now, mMyFragment);
                isPlay();
                mHomePageFragment.GbPlay();
                if (sboo) {
                    mMyFragment.bao();
                }
                sboo = true;
                isbo = true;
                break;
        }
    }

    private void isPlay() {
        if (MyJZVideoPlayerStandard.b == 1) {
            JZVideoPlayer.quitFullscreenOrTinyWindow();
        }
        MyJZVideoPlayerStandard.b = 0;
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.fragment_group, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.fragment_group, to).show(to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commitAllowingStateLoss();
        }
        fragment_now = to;
    }

    /**
     * 单击回退
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyJZVideoPlayerStandard.c == 1) {
                MyJZVideoPlayerStandard.backButton.performClick();
                MyJZVideoPlayerStandard.c = 0;
            } else {
                exitBy2Click();
            }
        }
        return false;
    }

    /**
     * 双击退出
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(mActivity, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            //finish();
            String music = PreUtils.getString("music", "");
            if("".equals(music)){
                finishAffinity();
                System.exit(0);
            }else {
                mCancle.performClick();
                obo = true;
            }
        }
    }

    @Override
    public void showError(String error) {
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case USERINFO:
                UserInfoBean userInfoBean = (UserInfoBean) o;
                long userid = userInfoBean.getResult().getUserid();
                String nickname = userInfoBean.getResult().getNickname();
                String avatar = userInfoBean.getResult().getAvatar();
                String rongcloudToken = userInfoBean.getResult().getRongcloudToken();
                Log.i("userid", "show: " + userid);
                PreUtils.putString("userId", userid + "");
                PreUtils.putString("userName", nickname);
                PreUtils.putString("avatar", avatar);
                PreUtils.putString("rongcloudToken", rongcloudToken);
                PreUtils.putString("referrerCode", userInfoBean.getResult().getReferrerCode()+"");
                connectRongServer(rongcloudToken);
                break;
            case PARENTID:
                CollegeParentBean collegeParentBean = (CollegeParentBean) o;
                List<CollegeParentBean.ResultBean> result = collegeParentBean.getResult();
                PreUtils.putString("collegeone", "" + result.get(0).getCatalogId());
                PreUtils.putString("collegetwo", "" + result.get(1).getCatalogId());
                PreUtils.putString("collegethree", "" + result.get(2).getCatalogId());
                break;
        }
    }
    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
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
                Log.e("rongyun", "成功"+s);
                PreUtils.putString("rongUser",s);
                //Toast.makeText(getContext(), "连接成功 ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("rongyun", "失败"+errorCode.getValue());
                //Toast.makeText(getContext(), errorCode.getValue() + "", Toast.LENGTH_SHORT).show();
            }
        });
        /*RongIM.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus) {
                Log.i("yxRong", "onChanged: "+connectionStatus.getValue());
            }
        });*/

    }
}
