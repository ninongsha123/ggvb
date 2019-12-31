package com.tiancaijiazu.app.activitys.shopping_activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_comm_list;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CommentListBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentListActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;  @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.rlv)
    RecyclerView mRlv;


    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        List<CommentListBeans.ResultBean.ItemListBean> data = (List<CommentListBeans.ResultBean.ItemListBean>) getIntent().getSerializableExtra("data");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        RlvAdapter_comm_list rlvAdapterCommList = new RlvAdapter_comm_list(this, data);
        mRlv.setAdapter(rlvAdapterCommList);
        rlvAdapterCommList.setOItemClickListener(new RlvAdapter_comm_list.setOnItemClick() {
            @Override
            public void setOnItemClickListener(View v, int position, ArrayList<String> mData) {
                //查看大图
                Intent intent = new Intent(CommentListActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images",mData);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    public void showError(String error) {

    }


    @OnClick({R.id.iv_finis})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
