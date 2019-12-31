package com.tiancaijiazu.app.activitys.early.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.TheFormalCardActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_hori;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_weekly;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 *
 * 正式课
 */
public class FormalLessonsFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.tv_title_card)
    TextView mTvTitleCard;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.iv_age)
    LinearLayout mIvAge;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    @BindView(R.id.nested)
    NestedScrollView mNested;
    @BindView(R.id.rela_b)
    RelativeLayout mRelaB;
    @BindView(R.id.color_flag)
    TextView colorFlag;
    @BindView(R.id.tv_baby_age)
    TextView mTvBabyAge;
    @BindView(R.id.baby_age_iv)
    ImageView mBabyAgeIv;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.card_rela)
    RelativeLayout mCardRela;
    @BindView(R.id.formal_cards)
    Button formalCards;

    private RlvAdapter_weekly mRlvAdapterWeekly;
    private String color = "#FF00DEFF";
    private boolean isBottomShow = true;
    private int mTop;
    private int page = 1;

    public FormalLessonsFragment() {
        // Required empty public constructor
    }

    public static FormalLessonsFragment getInstance() {
        return new FormalLessonsFragment();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_formal_lessons;
    }

    @Override
    protected void initData() {
        initView();
        initRecyler();
        initNest();

        mNested.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                String chang = PreUtils.getString("chang", "");
                if ("yes".equals(chang)) {
                    mTop = mRecyler.getTop();
                    PreUtils.putString("chang", "no");
                }
            }
        });
        mPresenter.getDataP1(page, DifferentiateEnum.EARLYCOURSELIST, loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(page, DifferentiateEnum.EARLYCOURSELIST, loadingLayout);
            }
        });
    }

    private void initNest() {
        mNested.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY - oldScrollY > 0 && isBottomShow) {//下移隐藏
                    isBottomShow = false;
                    mRelaB.animate().translationY(mRelaB.getHeight());
                } else if (scrollY - oldScrollY < 0 && !isBottomShow) {//上移出现
                    isBottomShow = true;
                    mRelaB.animate().translationY(0);
                }
            }
        });
    }

    private void initRecyler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyler.setLayoutManager(linearLayoutManager);
        List<EarlyCourseListBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterWeekly = new RlvAdapter_weekly(resultBeans, color, getContext());
        mRecyler.setAdapter(mRlvAdapterWeekly);
    }

    private void initView() {
        ArrayList<Integer> drawables = new ArrayList<>();
        drawables.add(R.drawable.select_age_of_the_moon_one);
        drawables.add(R.drawable.select_age_of_the_moon_two);
        drawables.add(R.drawable.select_age_of_the_moon_three);
        drawables.add(R.drawable.select_age_of_the_moon_four);
        drawables.add(R.drawable.select_age_of_the_moon_five);
        drawables.add(R.drawable.select_age_of_the_moon_six);
        drawables.add(R.drawable.select_age_of_the_moon_seven);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_hori rlvAdapterHori = new RlvAdapter_hori(drawables);
        mRecylerView.setAdapter(rlvAdapterHori);

        rlvAdapterHori.setOnClickLisiter(new RlvAdapter_hori.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                switch (position) {
                    case 0:
                        mIvAge.setBackgroundResource(R.mipmap.age_6_8);
                        mRlvAdapterWeekly.addData("#FF00D7F6");
                        colorFlag.setTextColor(Color.parseColor("#FF00D7F6"));
                        colorFlag.setText("6-8月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_6_8);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_6_8);
                        break;
                    case 1:
                        mIvAge.setBackgroundResource(R.mipmap.age_8_10);
                        mRlvAdapterWeekly.addData("#FFFFC35A");
                        colorFlag.setTextColor(Color.parseColor("#FFFFC35A"));
                        colorFlag.setText("8-10月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_8_10);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_8_10);
                        break;
                    case 2:
                        mIvAge.setBackgroundResource(R.mipmap.age_10_14);
                        mRlvAdapterWeekly.addData("#FF00EDE5");
                        colorFlag.setTextColor(Color.parseColor("#FF00EDE5"));
                        colorFlag.setText("10-14月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_10_14);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_10_14);
                        break;
                    case 3:
                        mIvAge.setBackgroundResource(R.mipmap.age_14_18);
                        mRlvAdapterWeekly.addData("#FFFF8864");
                        colorFlag.setTextColor(Color.parseColor("#FFFF8864"));
                        colorFlag.setText("14-18月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_14_18);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_14_18);
                        break;
                    case 4:
                        mIvAge.setBackgroundResource(R.mipmap.age_18_24);
                        mRlvAdapterWeekly.addData("#FF00D7F6");
                        colorFlag.setTextColor(Color.parseColor("#FF00D7F6"));
                        colorFlag.setText("18-24月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_18_24);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_18_24);
                        break;
                    case 5:
                        mIvAge.setBackgroundResource(R.mipmap.age_24_30);
                        mRlvAdapterWeekly.addData("#FFFFC35A");
                        colorFlag.setTextColor(Color.parseColor("#FFFFC35A"));
                        colorFlag.setText("24-30月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_24_30);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_24_30);
                        break;
                    case 6:
                        mIvAge.setBackgroundResource(R.mipmap.age_10_14);
                        mRlvAdapterWeekly.addData("#FF00EDE5");
                        colorFlag.setTextColor(Color.parseColor("#FF00EDE5"));
                        colorFlag.setText("30-36月龄阶段");
                        mTvBabyAge.setText(R.string.baby_age_30_36);
                        mBabyAgeIv.setImageResource(R.mipmap.baby_age_30_36);
                        break;
                }
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case EARLYCOURSELIST:
                EarlyCourseListBean earlyCourseListBean = (EarlyCourseListBean) o;
                List<EarlyCourseListBean.ResultBean> result = earlyCourseListBean.getResult();
                Log.i("yx123", "show: " + result.size());
                mRlvAdapterWeekly.addDataU(result);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({ R.id.formal_cards,R.id.card_rela})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.formal_cards:
            case R.id.card_rela:
                initJuop();
                break;
        }
    }

    public void initJuop() {
        Intent intent = new Intent(getContext(), TheFormalCardActivity.class);
        startActivity(intent);
    }

}
