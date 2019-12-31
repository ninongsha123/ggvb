package com.tiancaijiazu.app.activitys.offline.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.offline.BookingInfoActivity;
import com.tiancaijiazu.app.activitys.offline.adapters.RlvAdapter_curriculum;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.GardenDetailsByBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DestroyActivityUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.linwg.org.lib.LCardView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurriculumFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.line_make_on)
    LinearLayout mLineMakeOn;
    Unbinder unbinder;
    @BindView(R.id.courseType)
    TextView mCourseType;
    @BindView(R.id.month)
    TextView mMonth;
    @BindView(R.id.duration)
    TextView mDuration;
    Unbinder unbinder1;
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.rounded)
    RoundedImageView mRounded;
    Unbinder unbinder2;
    @BindView(R.id.cor)
    RoundedImageView mCor;
    @BindView(R.id.yuan_name)
    TextView mYuanName;
    @BindView(R.id.yuan_address)
    TextView mYuanAddress;
    @BindView(R.id.lcard)
    LCardView mLcard;
    private long mCourseId;

    public CurriculumFragment() {
        // Required empty public constructor
    }

    public static CurriculumFragment getInstance(String title, GardenDetailsByBean.ResultBean.CourseListBean data, GardenDetailsByBean.ResultBean result) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putSerializable("data", data);
        bundle.putSerializable("yuan", result);
        CurriculumFragment curriculumFragment = new CurriculumFragment();
        curriculumFragment.setArguments(bundle);
        return curriculumFragment;
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_curriculum;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        GardenDetailsByBean.ResultBean.CourseListBean data = (GardenDetailsByBean.ResultBean.CourseListBean) bundle.getSerializable("data");
        GardenDetailsByBean.ResultBean result = (GardenDetailsByBean.ResultBean) bundle.getSerializable("yuan");
        mYuanName.setText(result.getCompanyName());
        mYuanAddress.setText(result.getAddress());
        Glide.with(this).load(result.getLogoUri()).into(mCor);
        mCourseId = data.getCourseId();
        mTitle.setText(data.getTitle());
        mCourseType.setText(data.getCourseType());
        mMonth.setText(data.getMonthMin() + "-" + data.getMonthMax() + "个月");
        mDuration.setText(data.getDuration() + "分钟");
        mSummary.setText(data.getSummary());
        Glide.with(this).load(data.getCover()).into(mRounded);
        String pics = data.getPics();
        String[] split = pics.split("[|]");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_curriculum rlvAdapterCurriculum = new RlvAdapter_curriculum(split, getContext());
        mRecylerView.setAdapter(rlvAdapterCurriculum);
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


    @OnClick({R.id.line_make_on,R.id.lcard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.line_make_on:
                Intent intent = new Intent(getContext(), BookingInfoActivity.class);
                intent.putExtra("courseId", mCourseId + "");
                startActivity(intent);
                break;
            case R.id.lcard:
                PreUtils.putString("hui","ok");
                DestroyActivityUtil.destoryActivity("OfflineBookingActivity");
                break;
        }
    }

}
