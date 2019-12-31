package com.tiancaijiazu.app.fragments.outermostlayer.college_child;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.TryListenerActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.early.EarlyActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.PeoSumBean;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.college_adapters.RlvAdapter_early;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Early_educationFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.rvle)
    RecyclerView rvle;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.lll)
    LinearLayout mLll;
    @BindView(R.id.hao)
    TextView hao;
    @BindView(R.id.new1)
    TextView xin;
    @BindView(R.id.xiao)
    TextView xiao;
    @BindView(R.id.littlegenius1)
    RoundedImageView mLittlegenius1;
    @BindView(R.id.rl1)
    RelativeLayout mRl1;
    @BindView(R.id.into)
    RoundedImageView mInto;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.sum)
    TextView mSum;
    @BindView(R.id.bt)
    TextView mBt;
    private RlvAdapter_early mAdapter_early;
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        initUp();
                    }
                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);

                    }

                    break;
            }
            return false;
        }
    });

    public static Early_educationFragment newInstance() {
        Early_educationFragment fragment = new Early_educationFragment();
        return fragment;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_early_education;
    }

    @Override
    protected void initData() {
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADFOUR, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADTHREE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.PEOSUM, loadinglayout);
        loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.STUDYADFOUR, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.STUDYADTHREE, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
                mPresenter.getDataP1("", DifferentiateEnum.PEOSUM, loadinglayout);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        List<MallAdBean.ResultBean> resultBeans = new ArrayList<>();
        mAdapter_early = new RlvAdapter_early(getContext(), resultBeans);
        rvle.setLayoutManager(manager);
        rvle.setAdapter(mAdapter_early);

        mAdapter_early.setOnClickLisiter(new RlvAdapter_early.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MallAdBean.ResultBean> dataList) {
                String target = dataList.get(position).getTarget();
                String route = dataList.get(position).getRoute();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(getContext(), ClassListActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", dataList.get(position).getTitle());
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(getContext(), ShopActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", dataList.get(position).getTitle());
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title", dataList.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
        String catalogId = PreUtils.getString("collegetwo", "");
        //mPresenter.getDataP(catalogId,DifferentiateEnum.PARENTID);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //List<String> data = initDatas();
                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 1000);
            }
        });

        /*mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1000);
            }
        });*/

    }

    public void initUp() {
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADFOUR, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.STUDYADTHREE, loadinglayout);
        mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case STUDYADFOUR:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result = mallAdBean.getResult();
                mAdapter_early.addData(result);
                break;
            case STUDYADTHREE:
                MallAdBean mallAdBean1 = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean1.getResult();
                Glide.with(getContext()).load(result1.get(0).getPicUri()).into(mLittlegenius1);
                mTitle.setText(result1.get(0).getTitle());
                mRl1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String target = result1.get(0).getTarget();
                        String route = result1.get(0).getRoute();
                        if ("STUDY_COURSE".equals(route)) {
                            Intent intent = new Intent(getContext(), ClassListActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("MALL_SKU".equals(route)) {
                            Intent intent = new Intent(getContext(), ShopActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        } else if ("WEB".equals(route)) {
                            Intent intent = new Intent(getContext(), WebActivity.class);
                            intent.putExtra("target", target);
                            startActivity(intent);
                        }
                    }
                });
                break;
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                int cardType = userCardTypeBean.getResult().getCardType();
                mInto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cardType == -1) {
                            Intent intent = new Intent(getContext(), TryListenerActivity.class);
                            startActivityForResult(intent,23);
                        } else {

                            Intent intent = new Intent(getContext(), EarlyActivity.class);
                            startActivityForResult(intent,23);
                        }
                    }
                });
                mBt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (cardType == -1) {
                            Intent intent = new Intent(getContext(), TryListenerActivity.class);
                            startActivityForResult(intent,23);
                        } else {

                            Intent intent = new Intent(getContext(), EarlyActivity.class);
                            startActivityForResult(intent,23);
                        }
                    }
                });
                break;
            case PEOSUM:
                PeoSumBean peoSumBean = (PeoSumBean) o;
                int result2 = peoSumBean.getResult();
                mSum.setText(result2 + "");
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==23&&resultCode==24){
            mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE, loadinglayout);
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

}
