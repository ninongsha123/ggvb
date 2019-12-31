package com.tiancaijiazu.app.homepagefragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.adapters.VpAdapter;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.fragments.outermostlayer.college_child.Early_educationFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.college_child.Parent_educationFragment;
import com.tiancaijiazu.app.fragments.outermostlayer.college_child.Wisdom_educationFragment;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 *  学院
 */
public class CollegeFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private PopupWindow PopupWindows;
    private View mInflate1;

    private List<CollegeParentBean.ResultBean> mResult;
    private ArrayList<String> mStrings;


    public static CollegeFragment newInstance() {
        CollegeFragment fragment = new CollegeFragment();
        return fragment;
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStarta", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStarta", false).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_college;
    }

    @Override
    protected void initData() {
        // 判断是否是第一次开启应用
        if(isFirstStart(getContext())==true){
            // 如果是第一次启动
            showHint();
        }else {
            creatrLayoutId();
        }
        mPresenter.getDataP("",DifferentiateEnum.PARENTID);
    }

    @Override
    public void showError(String error) {
    }

    private void showHint() {
        mInflate1 = LayoutInflater.from(getContext()).inflate(R.layout.pop_hint_college, null);
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

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case PARENTID:
                CollegeParentBean collegeParentBean = (CollegeParentBean) o;
                mResult = collegeParentBean.getResult();
                PreUtils.putString("collegeone", "" + mResult.get(0).getCatalogId());
                PreUtils.putString("collegetwo", "" + mResult.get(1).getCatalogId());
                PreUtils.putString("collegethree", "" + mResult.get(2).getCatalogId());
                ArrayList<Fragment> fragments = new ArrayList<>();
                //父母学院
                fragments.add(Parent_educationFragment.newInstance(mResult.get(0).getName(),mResult.get(0).getCatalogId()+""));
                fragments.add(Early_educationFragment.newInstance());
                fragments.add(Wisdom_educationFragment.newInstance(mResult.get(2).getName(),mResult.get(2).getCatalogId()+""));
                mStrings = new ArrayList<>();
                mStrings.add(mResult.get(0).getName());
                mStrings.add(mResult.get(1).getName());
                mStrings.add(mResult.get(2).getName());
                VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager(), mStrings, fragments);
                mViewPager.setAdapter(vpAdapter);
                mTab.setupWithViewPager(mViewPager);
                mTab.getTabAt(0).setCustomView(getTabView(0));
                mTab.getTabAt(1).setCustomView(getTabView(1));
                mTab.getTabAt(2).setCustomView(getTabView(2));
                mTab.setSelectedTabIndicatorHeight(0);
                mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        changeTabSelect(tab);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        changeTabNormal(tab);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                mViewPager.setCurrentItem(1);
                break;
        }

    }
    //选中时tab字体颜色
    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.parents_school_tv);
        txt_title.setTextColor(Color.parseColor("#333333"));

    }
    //未选中时tab字体颜色
    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();
        TextView txt_title = (TextView) view.findViewById(R.id.parents_school_tv);
        txt_title.setTextColor(Color.parseColor("#D6D6D6"));
    }
    //tab图标数组
    private int[] tabIcons = {
            R.drawable.selector_parents_school,
            R.drawable.selector_early_education_college,
            R.drawable.selector_institute_of_wisdom
    };
    //tab样式及赋值
    public View getTabView(int position) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_tab, null);
        TextView txt_title = (TextView) view.findViewById(R.id.parents_school_tv);
        txt_title.setText(mStrings.get(position));
        txt_title.setTextColor(R.drawable.selector_sholl);
        ImageView img_title = (ImageView) view.findViewById(R.id.parents_school_iv);
        img_title.setImageResource(tabIcons[position]);
        return view;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
