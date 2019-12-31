package com.tiancaijiazu.app.activitys.qi_activitys;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.qi_activitys.adapters.RlvAdapter_bao;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.DeleteBabyBean;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class BaoActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.add_baby)
    ImageView mAddBaby;
    @BindView(R.id.rl)
    LinearLayout mRl;
    @BindView(R.id.tv_data)
    TextView mTvData;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.wan_cheng)
    ImageView mWanCheng;
    private Intent mIntent;
    private String mHome;
    private Long mId;
    private int size = 0;
    private RlvAdapter_bao mRlvAdapterBao;
    private int mSize;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        mHome = mIntent.getStringExtra("biao");
        initRecyclerView();
        mPresenter.getDataP1(null, DifferentiateEnum.USERINFO,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(null, DifferentiateEnum.USERINFO,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<BabyMessageBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterBao = new RlvAdapter_bao(resultBeans,this);
        mRecylerView.setAdapter(mRlvAdapterBao);

        mRlvAdapterBao.setOnClickLisiter(new RlvAdapter_bao.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<BabyMessageBean.ResultBean> mData) {
                long babyId = mData.get(position).getBabyId();
                mPresenter.getDataP1(babyId + "", DifferentiateEnum.DELETEBABY,loadingLayout);
            }
        });
        mRlvAdapterBao.setOnClickLisiterRedact(new RlvAdapter_bao.onClickLisiterRedact() {
            @Override
            public void onClickerRedact(View view, int position, List<BabyMessageBean.ResultBean> mData) {
                Intent intent = new Intent(BaoActivity.this, BaByAddActivity.class);
                intent.putExtra("data", (Serializable) mData);
                intent.putExtra("position", position);
                intent.putExtra("bianji", "bianji");
                intent.putExtra("size", mSize);
                startActivityForResult(intent, 21);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 21 && resultCode == 22) {
            mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
        }
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_bao;
    }

    public void inTiao() {
        if (mHome != null) {
            setResult(124, mIntent);
            finish();
        } else {
            Intent intent = new Intent(this, LordActivity.class);
            startActivity(intent);
            finish();
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
                Log.i("userid", "show: " + userid);
                PreUtils.putString("userId", userid + "");
                PreUtils.putString("userName", nickname);
                PreUtils.putString("avatar", avatar);
                break;
            case BABYMESSAGELIST:
                BabyMessageBean babyMessageBean = (BabyMessageBean) o;
                List<BabyMessageBean.ResultBean> result = babyMessageBean.getResult();
                mSize = result.size();
                if (mHome != null) {
                    if (result.size() != 0) {
                        mWanCheng.setVisibility(View.GONE);
                    }
                    if (result.size() == 0) {
                        mRl.setVisibility(View.VISIBLE);
                    }
                    if (result.size() == 1) {
                        mRl.setVisibility(View.VISIBLE);
                    }
                    if (result.size() >= 2) {
                        mRl.setVisibility(View.GONE);
                        mRlvAdapterBao.addData(result);
                    } else {
                        mRlvAdapterBao.addData(result);
                    }
                } else {
                    if (result.size() != 0) {
                        inTiao();
                    }
                }
                break;
            case DELETEBABY:
                DeleteBabyBean deleteBabyBean = (DeleteBabyBean) o;
                if (deleteBabyBean.getCode().equals("0")) {
                    mPresenter.getDataP1("", DifferentiateEnum.BABYMESSAGELIST,loadingLayout);
                    Toast.makeText(mActivity, deleteBabyBean.getResult(), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.rl, R.id.wan_cheng, R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl:
                Intent intent = new Intent(BaoActivity.this, BaByAddActivity.class);
                startActivityForResult(intent, 21);
                break;
            case R.id.wan_cheng:
            case R.id.iv_finis:
                inTiao();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        inTiao();
    }
}
