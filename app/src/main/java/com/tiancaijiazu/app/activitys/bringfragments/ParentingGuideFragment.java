package com.tiancaijiazu.app.activitys.bringfragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_Five;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_Four;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_One;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_Six;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_Three;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ParentingGuideAdapter_Two;
import com.tiancaijiazu.app.activitys.health_feeding.HealthFeedingActivity;
import com.tiancaijiazu.app.activitys.parenting.ParentingGuideActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * A simple {@link Fragment} subclass.
 *
 * 育儿指南
 */
public class ParentingGuideFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.towards)
    ImageView mTowards;
    @BindView(R.id.recylerView1)
    RecyclerView mRecylerView1;
    @BindView(R.id.recylerView2)
    RecyclerView mRecylerView2;
    @BindView(R.id.recylerView3)
    RecyclerView mRecylerView3;
    @BindView(R.id.recylerView4)
    RecyclerView mRecylerView4;
    @BindView(R.id.line_bao_age)
    LinearLayout mLineBaoAge;
    @BindView(R.id.recylerView5)
    RecyclerView mRecylerView5;
    @BindView(R.id.recylerView6)
    RecyclerView mRecylerView6;

    public ParentingGuideFragment() {
        // Required empty public constructor
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_parenting_guide;
    }

    @Override
    protected void initData() {
        initRlvOne();
        initRlvTwo();
        initRlvThree();
        initRlvFour();
        initRlvFive();
        initRlvSix();
        mLineBaoAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConstellationPicker();
            }
        });
    }

    private void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        SinglePicker<String> picker = new SinglePicker<String>(getActivity(),
                isChinese ? new String[]{
                        "0-1岁", "1-3岁", "3-6岁"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries"
                });
        picker.setCanLoop(false);//不禁用循环
        picker.setTopBackgroundColor(0xFFF7F7F7);
        picker.setTopHeight(40);
        //picker.setTopLineColor(0xFF33B5E5);
        picker.setTopLineHeight(0);
        picker.setTitleText(isChinese ? "选择宝宝月龄" : "Please pick");
        picker.setTitleTextColor(0xFF333333);
        picker.setTitleTextSize(15);
        picker.setCancelTextColor(0xFF333333);
        picker.setCancelTextSize(15);
        picker.setSubmitTextColor(0xFF00DEFF);
        picker.setSubmitTextSize(15);//00deff 确定  333333 取消
        picker.setSelectedTextColor(0xFF00DEFF);
        picker.setUnSelectedTextColor(0xFF999999);
        picker.setWheelModeEnable(false);
        picker.setHeight(480);
        LineConfig config = new LineConfig();
        config.setColor(0xFFEBEBEB);//线颜色
        config.setAlpha(120);//线透明度
        //config.setRatio(1);//线比率
        picker.setLineConfig(config);
        int screenWidth = ScreenUtil.getInstance(getContext()).getScreenWidth();
        picker.setItemWidth(screenWidth);
        picker.setBackgroundColor(0xFFFFFFFF);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        //picker.setSelectedIndex(1);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                Toast.makeText(mContext, "" + item, Toast.LENGTH_SHORT).show();
            }
        });
        picker.show();
    }

    private void initRlvSix() {
        ArrayList<String> list = new ArrayList<>();
        list.add("出行安全");
        list.add("行为运动");
        list.add("身体护理");
        list.add("休闲娱乐");
        list.add("穿戴讲究");
        list.add("居家生活");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView6.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_Six parentingGuideAdapterSix = new ParentingGuideAdapter_Six(list);
        mRecylerView6.setAdapter(parentingGuideAdapterSix);
        parentingGuideAdapterSix.setOnClickLisiter(new ParentingGuideAdapter_Six.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                initTiao("能不能做");
            }
        });
    }

    private void initRlvFive() {
        ArrayList<String> list = new ArrayList<>();
        list.add("主食");
        list.add("菜肴/汤");
        list.add("蔬菜菌类");
        list.add("肉/蛋类");
        list.add("水果");
        list.add("水产品");
        list.add("调味品");
        list.add("饮品/甜品");
        list.add("零食小吃类");
        list.add("豆/奶制品");
        list.add("坚果类");
        list.add("补品&草药");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView5.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_Five parentingGuideAdapterFive = new ParentingGuideAdapter_Five(list);
        mRecylerView5.setAdapter(parentingGuideAdapterFive);
        parentingGuideAdapterFive.setOnClickLisiter(new ParentingGuideAdapter_Five.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                initTiao("能不能吃");
            }
        });
    }

    public void initTiao(String str){
        Intent intent = new Intent(getContext(), ParentingGuideActivity.class);
        intent.putExtra("str",str);
        startActivity(intent);
    }

    private void initRlvFour() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView4.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_Four parentingGuideAdapterFour = new ParentingGuideAdapter_Four();
        mRecylerView4.setAdapter(parentingGuideAdapterFour);
        parentingGuideAdapterFour.setOnClickLisiter(new ParentingGuideAdapter_Four.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                Toast.makeText(mContext, ""+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRlvThree() {
        ArrayList<String> list = new ArrayList<>();
        list.add("黄疸");
        list.add("肠绞痛");
        list.add("感冒发烧");
        list.add("咳嗽");
        list.add("腹泻");
        list.add("皮肤问题");
        list.add("尿便问题");
        list.add("耳鼻喉眼");
        list.add("免疫力");
        list.add("便秘");
        list.add("积食");
        list.add("过敏");
        list.add("肠道健康");
        list.add("手足口");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView3.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_Three parentingGuideAdapterThree = new ParentingGuideAdapter_Three(list);
        mRecylerView3.setAdapter(parentingGuideAdapterThree);
        parentingGuideAdapterThree.setOnClickLisiter(new ParentingGuideAdapter_Three.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                Toast.makeText(mContext, "" + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRlvTwo() {
        ArrayList<String> list = new ArrayList<>();
        list.add("婴儿安全");
        list.add("日常穿用");
        list.add("洗澡");
        list.add("牙齿护理");
        list.add("蚊虫叮咬");
        list.add("头部知识");
        list.add("私处护理");
        list.add("安抚奶嘴");
        list.add("尿不湿");
        list.add("尿不湿");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView2.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_Two parentingGuideAdapterTwo = new ParentingGuideAdapter_Two(list);
        mRecylerView2.setAdapter(parentingGuideAdapterTwo);
        parentingGuideAdapterTwo.setOnClickLisiter(new ParentingGuideAdapter_Two.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                Toast.makeText(mContext, "" + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRlvOne() {
        ArrayList<String> list = new ArrayList<>();
        list.add("第一口奶");
        list.add("关于母乳");
        list.add("关于奶粉");
        list.add("母乳储存");
        list.add("断奶");
        list.add("营养宝典");
        list.add("喝水营养");
        list.add("如厕训练");
        list.add("挑食厌食");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        mRecylerView1.setLayoutManager(gridLayoutManager);
        ParentingGuideAdapter_One parentingGuideAdapterOne = new ParentingGuideAdapter_One(list);
        mRecylerView1.setAdapter(parentingGuideAdapterOne);
        parentingGuideAdapterOne.setOnClickLisiter(new ParentingGuideAdapter_One.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                Intent intent = new Intent(getContext(), HealthFeedingActivity.class);
                intent.putExtra("list",list);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}
