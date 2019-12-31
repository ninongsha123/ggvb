package com.tiancaijiazu.app.activitys.past;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.past.adapters.RlvAdapter_past_title;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.EarlyCourseBean;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PastCourseActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    private int page = 1;
    private RlvAdapter_past_title mRlvAdapterPastTitle;
    private int mPosition;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecylerView();
        mPresenter.getDataP(page,DifferentiateEnum.EARLYCOURSELIST);
    }

    private void initRecylerView() {
        List<EarlyCourseListBean.ResultBean> resultBeans = new ArrayList<>();
        List<EarlyCourseBean.ResultBean.ChapterListBean> chapterListBeans = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        mRlvAdapterPastTitle = new RlvAdapter_past_title(resultBeans,this,chapterListBeans);
        mRecylerView.setAdapter(mRlvAdapterPastTitle);


        mRlvAdapterPastTitle.setOnClickLisiter(new RlvAdapter_past_title.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<EarlyCourseListBean.ResultBean> mData) {
                long courseId = mData.get(position).getCourseId();
                mPosition = position;
                mPresenter.getDataP(courseId + "", DifferentiateEnum.EARLYCOURSE);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_past_course;
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
                mRlvAdapterPastTitle.addData(result);
                break;
            case EARLYCOURSE:
                EarlyCourseBean earlyCourseBean = (EarlyCourseBean) o;
                List<EarlyCourseBean.ResultBean.ChapterListBean> chapterList = earlyCourseBean.getResult().getChapterList();
                mRlvAdapterPastTitle.addUpData(chapterList,mPosition);
                break;
        }
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
