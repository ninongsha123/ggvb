package com.tiancaijiazu.app.activitys.health_feeding.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HealthFeedingFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;

    public HealthFeedingFragment() {
        // Required empty public constructor
    }

    public static HealthFeedingFragment getInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        HealthFeedingFragment healthFeedingFragment = new HealthFeedingFragment();
        healthFeedingFragment.setArguments(bundle);
        return healthFeedingFragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_health_feeding;
    }

    @Override
    protected void initData() {
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_health_feeding rlvAdapterHealthFeeding = new RlvAdapter_health_feeding();
        mRecylerView.setAdapter(rlvAdapterHealthFeeding);

        rlvAdapterHealthFeeding.setOnClickLisiter(new RlvAdapter_health_feeding.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                Intent intent = new Intent(getContext(), HealthInformationActivity.class);
                startActivity(intent);
            }
        });*/
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
