package com.tiancaijiazu.app.activitys.english;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.english.adapters.RlvAdapter_english_class;
import com.tiancaijiazu.app.activitys.english.adapters.RlvAdapter_song_data;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnglishClassActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecylerView();
        initRecyler();
    }

    private void initRecyler() {
        ArrayList<String> list = new ArrayList<>();
        list.add("I like to sing when the rain is falling,chua lalala,chua lalala. ");
        list.add("我喜欢在下雨天唱歌，刷啦啦啦，刷啦啦啦。");
        list.add("I like to sing when the rain is falling,chua lalala,chua lalala. ");
        list.add("我喜欢在下雨天唱歌，刷啦啦啦，刷啦啦啦。");
        list.add("I like to sing when the rain is falling,chua lalala,chua lalala. ");
        list.add(" 我喜欢在下雨天跺脚，刷啦啦啦，刷啦啦啦。");
        list.add("I like to sing when the rain is falling,chua lalala,chua lalala. ");
        list.add(" 我喜欢在下雨天跺脚，刷啦啦啦，刷啦啦啦。");
        list.add("I like to jump when the sun is shining,chua lalala,chua lalala. ");
        list.add(" 我喜欢在晴天跳来跳去，刷啦啦啦，刷啦啦啦。");
        list.add("I like to jump when the sun is shining,chua lalala,chua lalala. ");
        list.add(" 我喜欢在晴天跳来跳去，刷啦啦啦，刷啦啦啦。");
        list.add("I like to jump when the sun is shining,chua lalala,chua lalala. ");
        list.add("我喜欢在天放晴后听摇滚。 ");
        list.add("I like to jump when the sun is shining,chua lalala,chua lalala. ");
        list.add("我喜欢在天放晴后听摇滚。 ");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyler.setLayoutManager(linearLayoutManager);
        RlvAdapter_song_data rlvAdapterSongData = new RlvAdapter_song_data(list);
        mRecyler.setAdapter(rlvAdapterSongData);
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_english_class rlvAdapterEnglishClass = new RlvAdapter_english_class();
        mRecylerView.setAdapter(rlvAdapterEnglishClass);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_english_class;
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

    @OnClick({R.id.iv_finis, R.id.share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.share:
                break;
        }
    }
}
