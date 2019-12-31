package com.tiancaijiazu.app.activitys.diy;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.diy.adapters.RlvAdapter_diy_age_moon;
import com.tiancaijiazu.app.activitys.diy.adapters.RlvAdapter_diy_data;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiyActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    private RlvAdapter_diy_data mRlvAdapterDiyData;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecylerView();
        initRecyler();
    }

    private void initRecyler() {
        ArrayList<String> list = new ArrayList<>();
        list.add("· 丝带x4卷");
        list.add("· 闪粉x1瓶");
        list.add("· 气泡膜x1个");
        list.add("· 儿童粗针x1根");
        list.add("· 彩色纸胶带x1卷");
        list.add("· 长气球x1个");
        list.add("· 仿真小乌龟x2只");
        list.add("· 仿真小鱼x2条");
        list.add("· 扣子x30颗");
        list.add("· 羽毛x10根左右");
        list.add("· 绒球x60颗");
        list.add("· 扭扭棒x3根");
        list.add("· 海绵块x1块");
        list.add("· 皱纹纸x4张左右");
        list.add("· 不织布x1张");
        list.add("· 密封袋x8只左右");
        list.add("· A3卡纸x1张");
        list.add("· 纱巾x2条");
        list.add("· 纸杯x2个");
        list.add("· 磁力棒x1根");
        list.add("· 玻璃纸x2张");
        list.add("· 纸盘子x1个");
        list.add("· 雪梨纸x1张");
        list.add("· 课程定制印刷品x5张左右");
        list.add("· 不织布-蓝色小鱼x10个左右");
        list.add("· 不织布-胡萝卜x10根左右 ");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyler.setLayoutManager(linearLayoutManager);
        mRlvAdapterDiyData = new RlvAdapter_diy_data(this,list);
        mRecyler.setAdapter(mRlvAdapterDiyData);
    }

    private void initRecylerView() {
        ArrayList<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.select_age_one);
        drawables.add(R.drawable.select_age_two);
        drawables.add(R.drawable.select_age_three);
        drawables.add(R.drawable.select_age_four);
        drawables.add(R.drawable.select_age_five);
        drawables.add(R.drawable.select_age_six);
        drawables.add(R.drawable.select_age_seven);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_diy_age_moon rlvAdapterDiyAgeMoon = new RlvAdapter_diy_age_moon(drawables);
        mRecylerView.setAdapter(rlvAdapterDiyAgeMoon);

        rlvAdapterDiyAgeMoon.setOnClickLisiter(new RlvAdapter_diy_age_moon.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                mRlvAdapterDiyData.addData(position);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_diy;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;

        }
    }
}
