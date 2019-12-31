package com.tiancaijiazu.app.fragments.outermostlayer.college_fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_details_img;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 *
 *  课程介绍
 */
public class Course_decFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;

    public static Course_decFragment getInstance() {
        return new Course_decFragment();
    }

    public Course_decFragment() {
        // Required empty public constructor
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_course_dec;
    }

    @Override
    protected void initData() {
        String description = PreUtils.getString("description", "");
        String[] split = description.split("[|]");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_details_img rlvAdapterDetailsImg = new RlvAdapter_details_img(split, getContext());
        mRecylerView.setAdapter(rlvAdapterDetailsImg);
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
