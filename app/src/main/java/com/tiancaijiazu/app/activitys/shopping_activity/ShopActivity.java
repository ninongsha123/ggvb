package com.tiancaijiazu.app.activitys.shopping_activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.ClassListActivity;
import com.tiancaijiazu.app.activitys.activitypage.LordActivity;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_selet;
import com.tiancaijiazu.app.activitys.issue.PhotoChaActivity;
import com.tiancaijiazu.app.adapters.RlvAdapter_shop_data;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.Add_carBean;
import com.tiancaijiazu.app.beans.AtOnceBean;
import com.tiancaijiazu.app.beans.CommentListBeans;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.SpecificationBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.shopvideo.MyJZVideoPlayerStandardShop;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.CSCustomServiceInfo;
import io.rong.imlib.model.UserInfo;

import static com.tiancaijiazu.app.activitys.activitypage.LordActivity.fragment_now;

public class ShopActivity extends BaseActivity<IView, Presenter<IView>> implements IView, RongIM.UserInfoProvider {


    @BindView(R.id.shop)
    LinearLayout mShop;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.comm)
    TextView mComm;@BindView(R.id.a)
    TextView a;
    @BindView(R.id.comm_di)
    TextView mCommDi;
    @BindView(R.id.in_detail)
    TextView mInDetail;
    @BindView(R.id.in_detail_di)
    TextView mInDetailDi;
    @BindView(R.id.evaluate)
    TextView mEvaluate;
    @BindView(R.id.evaluate_di)
    TextView mEvaluateDi;
    @BindView(R.id.recommend)
    TextView mRecommend;
    @BindView(R.id.recommend_di)
    TextView mRecommendDi;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.one)
    RelativeLayout mOne;
    @BindView(R.id.two)
    RelativeLayout mTwo;
    @BindView(R.id.three)
    RelativeLayout mThree;
    @BindView(R.id.four)
    RelativeLayout mFour;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.add_car)
    ImageView addCar;
    @BindView(R.id.to_top)
    ImageView mToTop;
    @BindView(R.id.sto)
    LinearLayout mSto;
    @BindView(R.id.service)
    LinearLayout mService; @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.show)
    ImageView mShow;
    private PopupWindow mPopupWindows;
    private PopupWindow popupWindow;
    private View contentView;
    private RlvAdapter_shop_data mRlvAdapterShopData;
    private RlvAdapter_selet mRlvAdapterSelet;
    private TextView mPromoPrice;
    private RoundedImageView mCommPicTure;
    private SpecificationBean mSpecificationBean;
    private TextView mYuanJie;
    private TextView mTvKuCun;
    private String result;
    private ImageView rlv_ok;
    private int mSum;
    private boolean isbo = true;
    private int he;
    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    //记录目标项位置
    private int mToPosition;
    private String mOptionIds1;
    private View mInflate1;
    private long mSkuId;
    private String mPicUri;
    private double mPromoPrice1;
    private String mName;
    private int mFirstVisibleItemPosition;
    private LinearLayout mClickStandard;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String shareUrl = mSpecificationBean.getResult().getShareUrl();
            String name = mSpecificationBean.getResult().getName();
            String referrerCode = PreUtils.getString("referrerCode", "");
            if (shareUrl.contains("?")) {
                shareUrl = shareUrl + "&referrerCode=" + referrerCode;
            } else {
                shareUrl = shareUrl + "?referrerCode=" + referrerCode;
            }
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, true,"",false);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl(shareUrl, bitmap, name, false,"",false);
                    break;
            }
        }
    };
    private double mPromoPrice2;
    private View mInflate;
    private PopupWindow mPopupWindow1;
    private String mUserName;
    private String mAvatar;
    private int biao = 100;
    private View mInflated;

    /**
     * 滑动到指定位置
     */
    private void smoothMoveToPosition(RecyclerView mRecyclerView, final int position) {
        // 第一个可见位置
        int firstItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = mRecyclerView.getChildLayoutPosition(mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1));
        if (position < firstItem) {
            // 第一种可能:跳转位置在第一个可见位置之前，使用smoothScrollToPosition
            mRecyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 第二种可能:跳转位置在第一个可见位置之后，最后一个可见项之前
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < mRecyclerView.getChildCount()) {
                int top = mRecyclerView.getChildAt(movePosition).getTop();
                // smoothScrollToPosition 不会有效果，此时调用smoothScrollBy来滑动到指定位置
                int i = ScreenStatusUtil.setDp(40, this);
                //top = top - i;
                mRecyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 第三种可能:跳转位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            mRecyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
    private void connectRongServer(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            //token1参数报错
            @Override
            public void onTokenIncorrect() {
                Log.e("rongyun", "参数错误");
                //Toast.makeText(getContext(), "token1参数报错", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String s) {
                Log.e("rongyun", "成功");
                //Toast.makeText(getContext(), "连接成功 ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("rongyun", "失败");
                //Toast.makeText(getContext(), errorCode.getValue() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstStart(getApplicationContext())) {
            showHint();
        }
    }

    public static boolean isFirstStart(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(
                "SHARE_APP_TAG", 0);
        Boolean isFirst = preferences.getBoolean("FIRSTStart", true);
        if (isFirst) {// 第一次
            preferences.edit().putBoolean("FIRSTStart", false).commit();
            return true;
        } else {
            return false;
        }
    }


    @Override
    protected void initEventAndData() {
        initSett();
        //initRecyLerView();
        showPopwindow();
        Intent intent = getIntent();
        //id = intent.getIntExtra("id", 0);
        String target = intent.getStringExtra("target");
        Log.i("yxshop", "initEventAndData: " + target);
        if (target != null) {
            PreUtils.putString("shopBiao","target");
            PreUtils.putString("tar",target);
            mPresenter.getDataP1(target, DifferentiateEnum.SPECIFICATIONOFGOODS,loadinglayout);
            loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(target, DifferentiateEnum.SPECIFICATIONOFGOODS,loadinglayout);
                }
            });
        } else {
            PreUtils.putString("shopBiao","id");
            String id = intent.getStringExtra("id");
            PreUtils.putString("shopId",id);
            Log.i("yxshop", "initEventAndData: " + id);
            mPresenter.getDataP1(id, DifferentiateEnum.SPECIFICATIONOFGOODS,loadinglayout);
            loadinglayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mPresenter.getDataP1(id, DifferentiateEnum.SPECIFICATIONOFGOODS,loadinglayout);
                }
            });
        }
        String rongcloudToken = PreUtils.getString("rongcloudToken", "");
        //connectRongServer(rongcloudToken);
        initPop();

    }


    private void showHint() {
        mInflated = LayoutInflater.from(ShopActivity.this).inflate(R.layout.layout_shop_hint, null);
        mPopupWindows = new PopupWindow(mInflated,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindows.setFocusable(true);// 取得焦点
        mPopupWindows.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindows.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindows.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindows.setAnimationStyle(R.style.popwin_anim_style);

        Button my_locations = mInflated.findViewById(R.id.my_locations);

        my_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindows.dismiss();
            }
        });
        mPopupWindows.showAtLocation(mIvFinis, Gravity.BOTTOM, 0, 0);
    }


    private void initRecyLerView(SpecificationBean specificationBean) {
        final WrapContentLinearLayoutManager layoutManager = new WrapContentLinearLayoutManager(this);
        mRecylerView.setLayoutManager(layoutManager);
        ((DefaultItemAnimator) mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRlvAdapterShopData = new RlvAdapter_shop_data(this, specificationBean);
        mRecylerView.setAdapter(mRlvAdapterShopData);
        mRecylerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (mShouldScroll && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mShouldScroll = false;
                    if (mRecylerView != null) {
                        smoothMoveToPosition(mRecylerView, mToPosition);
                    }
                    Log.i("biaoji", "onScrollStateChanged: " + mToPosition);
                }

                if (mFirstVisibleItemPosition == 0) {
                    if (mCommDi != null && mInDetailDi != null && mEvaluateDi != null && mRecommendDi != null && mComm != null && mInDetail != null && mEvaluate != null && mRecommend != null) {
                        mToTop.setVisibility(View.GONE);
                        mCommDi.setVisibility(View.VISIBLE);
                        mInDetailDi.setVisibility(View.GONE);
                        mEvaluateDi.setVisibility(View.GONE);
                        mRecommendDi.setVisibility(View.GONE);
                        mComm.setTextColor(Color.parseColor("#FF0663"));
                        mInDetail.setTextColor(Color.parseColor("#666666"));
                        mEvaluate.setTextColor(Color.parseColor("#666666"));
                        mRecommend.setTextColor(Color.parseColor("#666666"));
                    }

                } else if (mFirstVisibleItemPosition == 1) {
                    if (mCommDi != null && mInDetailDi != null && mEvaluateDi != null && mRecommendDi != null && mComm != null && mInDetail != null && mEvaluate != null && mRecommend != null) {
                        mToTop.setVisibility(View.VISIBLE);
                        mCommDi.setVisibility(View.GONE);
                        mInDetailDi.setVisibility(View.VISIBLE);
                        mEvaluateDi.setVisibility(View.GONE);
                        mRecommendDi.setVisibility(View.GONE);
                        mComm.setTextColor(Color.parseColor("#666666"));
                        mInDetail.setTextColor(Color.parseColor("#FF0663"));
                        mEvaluate.setTextColor(Color.parseColor("#666666"));
                        mRecommend.setTextColor(Color.parseColor("#666666"));
                    }

                } else if (mFirstVisibleItemPosition == 2) {
                    if (mCommDi != null && mInDetailDi != null && mEvaluateDi != null && mRecommendDi != null && mComm != null && mInDetail != null && mEvaluate != null && mRecommend != null) {
                        mToTop.setVisibility(View.VISIBLE);
                        mCommDi.setVisibility(View.GONE);
                        mInDetailDi.setVisibility(View.GONE);
                        mEvaluateDi.setVisibility(View.VISIBLE);
                        mRecommendDi.setVisibility(View.GONE);
                        mComm.setTextColor(Color.parseColor("#666666"));
                        mInDetail.setTextColor(Color.parseColor("#666666"));
                        mEvaluate.setTextColor(Color.parseColor("#FF0663"));
                        mRecommend.setTextColor(Color.parseColor("#666666"));
                    }

                } else {
                    if (mCommDi != null && mInDetailDi != null && mEvaluateDi != null && mRecommendDi != null && mComm != null && mInDetail != null && mEvaluate != null && mRecommend != null) {
                        mToTop.setVisibility(View.VISIBLE);
                        mCommDi.setVisibility(View.GONE);
                        mInDetailDi.setVisibility(View.GONE);
                        mEvaluateDi.setVisibility(View.GONE);
                        mRecommendDi.setVisibility(View.VISIBLE);
                        mComm.setTextColor(Color.parseColor("#666666"));
                        mInDetail.setTextColor(Color.parseColor("#666666"));
                        mEvaluate.setTextColor(Color.parseColor("#666666"));
                        mRecommend.setTextColor(Color.parseColor("#FF0663"));
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                he += dy;
                Log.i("yx", "onScrolled: " + he);

                /*if (dy > 0) {
                    mLin.setVisibility(View.VISIBLE);

                }
                if (mRecylerView.canScrollVertically(-1)) {

                } else {
                    //滑动到顶部
                    mLin.setVisibility(View.GONE);
                }*/

            }
        });

        mRlvAdapterShopData.onSetOnClickLisiter(new RlvAdapter_shop_data.SetOnClickLisiter() {
            @Override
            public void onClickSelect(View view, int position) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                //mPresenter.getDataP(id, DifferentiateEnum.SPECIFICATIONOFGOODS);
                mClickStandard.setVisibility(View.VISIBLE);
                rlv_ok.setVisibility(View.GONE);
                biao = 100;
                popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            }
        });

        mRlvAdapterShopData.setOnClickLisiterWai(new RlvAdapter_shop_data.onClickLisiterWai() {
            @Override
            public void onClickerWai(View view, int position, List<MallAdBean.ResultBean> mData) {
                String route = mData.get(position).getRoute();
                String target = mData.get(position).getTarget();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title",mData.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
        mRlvAdapterShopData.setOnClickLisiterNei(new RlvAdapter_shop_data.onClickLisiterNei() {
            @Override
            public void onClickerNei(View view, int position, List<MallAdBean.ResultBean> mData) {
                String route = mData.get(position).getRoute();
                String target = mData.get(position).getTarget();
                if ("STUDY_COURSE".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, ClassListActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("MALL_SKU".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, ShopActivity.class);
                    intent.putExtra("target", target);
                    startActivity(intent);
                } else if ("WEB".equals(route)) {
                    Intent intent = new Intent(ShopActivity.this, WebActivity.class);
                    intent.putExtra("target", target);
                    intent.putExtra("title",mData.get(position).getTitle());
                    startActivity(intent);
                }
            }
        });
        mRlvAdapterShopData.setOnClickLisiterComment(new RlvAdapter_shop_data.onClickLisiterComment() {
            @Override
            public void onClickerComment(View view, int position, List<CommentListBeans.ResultBean.ItemListBean> mDataCommentList) {
                Intent intent = new Intent(ShopActivity.this, CommentListActivity.class);
                intent.putExtra("data", (Serializable) mDataCommentList);
                startActivity(intent);
            }
        });
        mRlvAdapterShopData.setOnClickLisiterLookPicture(new RlvAdapter_shop_data.onClickLisiterLookPicture() {
            @Override
            public void onClickerLookPicture(View view, int position, ArrayList<String> mData) {
                //查看大图
                Intent intent = new Intent(ShopActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", mData);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    private void showPopwindow() {
        contentView = LayoutInflater.from(ShopActivity.this).inflate(
                R.layout.specifications_popupwindow, null);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        //找ID
        mPromoPrice = contentView.findViewById(R.id.promo_price);
        mCommPicTure = contentView.findViewById(R.id.comm_picture);
        mCommPicTure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, PhotoChaActivity.class);
                intent.putExtra("image",mPicUri);
                startActivity(intent);
            }
        });
        RelativeLayout rela = contentView.findViewById(R.id.rela);
        mYuanJie = contentView.findViewById(R.id.yuan_jia);
        //添加横线
        mYuanJie.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTvKuCun = contentView.findViewById(R.id.tv_kucun);

        //确定按钮
        rlv_ok = contentView.findViewById(R.id.iv_ok);
        TextView guige = contentView.findViewById(R.id.guige);
        rlv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                mSum = mRlvAdapterSelet.sum;
                if (mRlvAdapterSelet.mRlvAdapterGuige.mTitle != null && mSum != 0) {
                    mRlvAdapterShopData.addStr(mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mSum);
                    if(biao == 1){
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("skuId", mSkuId + "");
                        map.put("stockId", mRlvAdapterSelet.mRlvAdapterGuige.mStockId + "");
                        map.put("quantity", mRlvAdapterSelet.sum);
                        mPresenter.getDataP(map, DifferentiateEnum.ADDSHOPCAR);
                    }else if(biao == 2){
                        AtOnceBean atOnceBean = new AtOnceBean(mName, mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mPicUri, mPromoPrice1, mRlvAdapterSelet.sum, mRlvAdapterSelet.mRlvAdapterGuige.mStockId, mSkuId);

                        Intent intent1 = new Intent(ShopActivity.this, POActivity.class);
                        intent1.putExtra("data", atOnceBean);
                        intent1.putExtra("biao", "2");
                        startActivity(intent1);
                    }
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(mActivity, "请选择完整规格", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout relative = contentView.findViewById(R.id.relative);
        mClickStandard = contentView.findViewById(R.id.click_standard);
        ImageView addToCart = contentView.findViewById(R.id.add_to_cart);
        ImageView buyImmediately = contentView.findViewById(R.id.buy_immediately);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mRlvAdapterSelet.mRlvAdapterGuige.mTitle != null && mRlvAdapterSelet.sum != 0) {
                    mRlvAdapterShopData.addStr(mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mRlvAdapterSelet.sum);
                    popupWindow.dismiss();
                }
                if (mRlvAdapterSelet.sum != 0 && mSkuId != 0&&mRlvAdapterSelet.mRlvAdapterGuige.mStockId!=0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("skuId", mSkuId + "");
                    map.put("stockId", mRlvAdapterSelet.mRlvAdapterGuige.mStockId + "");
                    map.put("quantity", mRlvAdapterSelet.sum);
                    mPresenter.getDataP(map, DifferentiateEnum.ADDSHOPCAR);
                } else {
                    if (popupWindow != null) {
                        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                    } else {

                    }
                    Toast.makeText(mActivity, "请选择规格", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buyImmediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mRlvAdapterSelet.mRlvAdapterGuige.mTitle != null && mRlvAdapterSelet.sum != 0) {
                    mRlvAdapterShopData.addStr(mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mRlvAdapterSelet.sum);
                    popupWindow.dismiss();
                }
                if (mRlvAdapterSelet.sum != 0 && mPicUri != null && mPromoPrice1 != 0) {
                    AtOnceBean atOnceBean = new AtOnceBean(mName, mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mPicUri, mPromoPrice1, mRlvAdapterSelet.sum, mRlvAdapterSelet.mRlvAdapterGuige.mStockId, mSkuId);
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getName());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getColour());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getImgurl());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getPrice());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getSum());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getStockId());

                    Intent intent1 = new Intent(ShopActivity.this, POActivity.class);
                    intent1.putExtra("data", atOnceBean);
                    intent1.putExtra("biao", "2");
                    startActivity(intent1);
                } else {
                    if (popupWindow != null) {
                        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                    } else {

                    }
                    Toast.makeText(mActivity, "请选择规格", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final ImageView cancle = contentView.findViewById(R.id.pop_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        RecyclerView rlv = contentView.findViewById(R.id.rlv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rlv.setLayoutManager(linearLayoutManager);
        SpecificationBean specificationBean = new SpecificationBean();
        mRlvAdapterSelet = new RlvAdapter_selet(this, specificationBean);
        rlv.setAdapter(mRlvAdapterSelet);

        mRlvAdapterSelet.setOnClickLisiter(new RlvAdapter_selet.onClickLisiter() {
            @Override
            public void onClicker(long stockId, double price, double promoPrice, int stock, String optionIds) {
                Log.i("yx456", "onClicker: ----" + optionIds);
                Log.i("yx456", "onClicker: ====" + mOptionIds1);
                if (stock <= 5) {
                    mTvKuCun.setVisibility(View.VISIBLE);
                    mTvKuCun.setText("(库存剩余" + stock + ")");
                }
                guige.setText(mRlvAdapterSelet.mRlvAdapterGuige.mTitle);
                if (mOptionIds1 == null) {
                    mPromoPrice1 = promoPrice;
                    mPromoPrice.setText("¥"+promoPrice);
                    mYuanJie.setText("¥" + price);
                    mOptionIds1 = optionIds;
                    //String[] split = optionIds.split(",");
                    List<SpecificationBean.ResultBean.SkuListBean> skuList = mRlvAdapterSelet.mData.getResult().getSkuList();
                    for (int i = 0; i < skuList.size(); i++) {
                        if (optionIds.contains(skuList.get(i).getKeyOptionId() + "")) {
                            //Log.i("yx777", split[0]+"onClicker: "+skuList.get(i).getKeyOptionId());
                            mPresenter.getDataP(skuList.get(i).getSkuId() + "", DifferentiateEnum.SPECIFICATIONOFGOODS);
                            break;
                        }
                    }
                } else {
                    if (mOptionIds1.equals(optionIds)) {

                    } else {
                        mPromoPrice1 = promoPrice;
                        mPromoPrice.setText("¥"+promoPrice);
                        mYuanJie.setText("¥" + price);
                        mOptionIds1 = optionIds;
                        //String[] split = optionIds.split(",");
                        List<SpecificationBean.ResultBean.SkuListBean> skuList = mRlvAdapterSelet.mData.getResult().getSkuList();
                        for (int i = 0; i < skuList.size(); i++) {
                            if (optionIds.contains(skuList.get(i).getKeyOptionId() + "")) {
                                //Log.i("yx777", split[0]+"onClicker:--- "+skuList.get(i).getKeyOptionId());
                                mPresenter.getDataP(skuList.get(i).getSkuId() + "", DifferentiateEnum.SPECIFICATIONOFGOODS);
                                break;
                            }
                        }
                    }
                }

            }
        });

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_shop;
    }

    //设置状态栏与状态栏字体颜色
    private void initSett() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public void initPop(){
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareUrl = mSpecificationBean.getResult().getShareUrl();
                String picUri = mSpecificationBean.getResult().getPicUri();
                if (shareUrl != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(picUri).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=1;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String shareUrl = mSpecificationBean.getResult().getShareUrl();
                String picUri = mSpecificationBean.getResult().getPicUri();
                String name = mSpecificationBean.getResult().getName();
                if (shareUrl != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(picUri).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=2;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        });
        TextView quxiao = mInflate.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
    }
    @OnClick({R.id.iv_finis, R.id.shop, R.id.one, R.id.two, R.id.three, R.id.four, R.id.add_car, R.id.show, R.id.to_top, R.id.share,R.id.sto,R.id.service})
    public void onViewClicked(View view) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        switch (view.getId()) {
            case R.id.iv_finis:

                finish();
                break;
            case R.id.share:
                mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.shop:
                Intent intent = new Intent(this, ShoppingCarActivity.class);
                startActivity(intent);
                break;
            case R.id.one:
                mRecylerView.smoothScrollToPosition(0);
                mCommDi.setVisibility(View.VISIBLE);
                mInDetailDi.setVisibility(View.GONE);
                mEvaluateDi.setVisibility(View.GONE);
                mRecommendDi.setVisibility(View.GONE);
                mComm.setTextColor(Color.parseColor("#FF0663"));
                mInDetail.setTextColor(Color.parseColor("#666666"));
                mEvaluate.setTextColor(Color.parseColor("#666666"));
                mRecommend.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.two:
                // mRecylerView.smoothScrollToPosition(1);
                smoothMoveToPosition(mRecylerView, 1);
                mCommDi.setVisibility(View.GONE);
                mInDetailDi.setVisibility(View.VISIBLE);
                mEvaluateDi.setVisibility(View.GONE);
                mRecommendDi.setVisibility(View.GONE);
                mComm.setTextColor(Color.parseColor("#666666"));
                mInDetail.setTextColor(Color.parseColor("#FF0663"));
                mEvaluate.setTextColor(Color.parseColor("#666666"));
                mRecommend.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.three:
                //mRecylerView.smoothScrollToPosition(2);
                smoothMoveToPosition(mRecylerView, 2);
                mCommDi.setVisibility(View.GONE);
                mInDetailDi.setVisibility(View.GONE);
                mEvaluateDi.setVisibility(View.VISIBLE);
                mRecommendDi.setVisibility(View.GONE);
                mComm.setTextColor(Color.parseColor("#666666"));
                mInDetail.setTextColor(Color.parseColor("#666666"));
                mEvaluate.setTextColor(Color.parseColor("#FF0663"));
                mRecommend.setTextColor(Color.parseColor("#666666"));
                break;
            case R.id.four:
                //mRecylerView.smoothScrollToPosition(3);
                smoothMoveToPosition(mRecylerView, 3);
                mCommDi.setVisibility(View.GONE);
                mInDetailDi.setVisibility(View.GONE);
                mEvaluateDi.setVisibility(View.GONE);
                mRecommendDi.setVisibility(View.VISIBLE);
                mComm.setTextColor(Color.parseColor("#666666"));
                mInDetail.setTextColor(Color.parseColor("#666666"));
                mEvaluate.setTextColor(Color.parseColor("#666666"));
                mRecommend.setTextColor(Color.parseColor("#FF0663"));
                break;
            case R.id.add_car:
                mClickStandard.setVisibility(View.GONE);
                rlv_ok.setVisibility(View.VISIBLE);
                biao = 1;
                if (mSum != 0 && mSkuId != 0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("skuId", mSkuId + "");
                    map.put("stockId", mRlvAdapterSelet.mRlvAdapterGuige.mStockId + "");
                    map.put("quantity", mSum);
                    mPresenter.getDataP(map, DifferentiateEnum.ADDSHOPCAR);
                } else {
                    if (popupWindow != null) {
                        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                    } else {

                    }
                    Toast.makeText(mActivity, "请选择规格", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.show:
                mClickStandard.setVisibility(View.GONE);
                rlv_ok.setVisibility(View.VISIBLE);
                biao = 2;
                if (mSum != 0 && mPicUri != null && mPromoPrice1 != 0) {
                    AtOnceBean atOnceBean = new AtOnceBean(mName, mRlvAdapterSelet.mRlvAdapterGuige.mTitle, mPicUri, mPromoPrice1, mSum, mRlvAdapterSelet.mRlvAdapterGuige.mStockId, mSkuId);
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getName());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getColour());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getImgurl());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getPrice());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getSum());
                    Log.i("yx123", "onViewClicked: ==========" + atOnceBean.getStockId());

                    Intent intent1 = new Intent(ShopActivity.this, POActivity.class);
                    intent1.putExtra("data", atOnceBean);
                    intent1.putExtra("biao", "2");
                    startActivity(intent1);
                } else {

                    if (popupWindow != null) {
                        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                    } else {

                    }
                    Toast.makeText(mActivity, "请选择规格", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.to_top:
                mRecylerView.smoothScrollToPosition(0);
                break;
            case R.id.sto:
                PreUtils.putString("lord","ok");
                Intent intent1 = new Intent(this, LordActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.service:
                mUserName = PreUtils.getString("userName", "");
                mAvatar = PreUtils.getString("avatar", "");
                String userId = PreUtils.getString("userId", "");
                CSCustomServiceInfo.Builder csBuilder = new CSCustomServiceInfo.Builder();
                CSCustomServiceInfo csInfo = csBuilder.nickName("融云").name(mUserName).portraitUrl(mAvatar).
                        referrer("10001").build();
                PreUtils.putString("rongyun","shop");
                PreUtils.putString("shopname",mName);
                PreUtils.putString("shoppicuri",mPicUri);
                PreUtils.putString("shopprice",mPromoPrice2+"");
                String rongUser = PreUtils.getString("rongUser", "");
                RongIM.setUserInfoProvider(this, true);
                RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(rongUser+"", mUserName, Uri.parse(mAvatar)));
                /**
                 * 启动客户服聊天界面。
                 * @param context           应用上下文。
                 * @param customerServiceId 要与之聊天的客服 Id。
                 * @param title             聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
                 * @param customServiceInfo 当前使用客服者的用户信息。{@link io.rong.imlib.model.CSCustomServiceInfo}
                 */
                RongIM.getInstance().startCustomerServiceChat(this, "service", "在线客服", csInfo);
                break;
        }
    }

    /**
     * 隐藏显示fragment
     *
     * @param from 需要隐藏的fragment
     * @param to   需要显示的fragment
     */
    public void switchFragment(Fragment from, Fragment to) {
        if (to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded()) {
            if (from == null) {
                transaction.add(R.id.fragment_group, to).show(to).commit();
            } else {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.fragment_group, to).show(to).commitAllowingStateLoss();
            }
        } else {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;
    }
    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SPECIFICATIONOFGOODS:
                mSpecificationBean = (SpecificationBean) o;
                if (mSpecificationBean.getResult().getShareUrl() != null) {
                    share.setVisibility(View.VISIBLE);
                } else {
                    share.setVisibility(View.GONE);
                }
                String shareUrl = mSpecificationBean.getResult().getPreviewUrl();
                PreUtils.putString("shopShare",shareUrl);
                mName = mSpecificationBean.getResult().getName();
                mPromoPrice2 = mSpecificationBean.getResult().getPromoPrice();
                mSkuId = mSpecificationBean.getResult().getSkuId();
                mYuanJie.setText("¥" + mSpecificationBean.getResult().getPrice());
                mPromoPrice.setText("¥" + mSpecificationBean.getResult().getPromoPrice());
                Log.d("bjg", "show: " + mSkuId);
                if (isbo) {
                    initRecyLerView(mSpecificationBean);
                    isbo = false;
                } else {
                    mRlvAdapterShopData.addData(mSpecificationBean);
                }
                mRlvAdapterSelet.addData(mSpecificationBean, mOptionIds1);
                mPicUri = mSpecificationBean.getResult().getPicUri();
                Glide.with(this).load(mPicUri).into(mCommPicTure);
                mPresenter.getDataP("", DifferentiateEnum.MALLADTEN);
                mPresenter.getDataP("", DifferentiateEnum.MALLADELEVEN);
                mPresenter.getDataP(mSkuId + "", DifferentiateEnum.COMMENTLIST);
                break;
            case ADDSHOPCAR:
                Add_carBean add_carBean = (Add_carBean) o;
                result = add_carBean.getResult();
                if (result.equalsIgnoreCase("添加成功")) {
                    Toast toast = new Toast(ShopActivity.this);
                    View inflate = LayoutInflater.from(this).inflate(R.layout.gouwuche, null, false);
                    toast.setView(inflate);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Toast.makeText(ShopActivity.this, result, Toast.LENGTH_SHORT).show();
                }

                break;
            case MALLADTEN:
                MallAdBean mallAdBean = (MallAdBean) o;
                List<MallAdBean.ResultBean> result = mallAdBean.getResult();
                mRlvAdapterShopData.setDataTen(result);
                break;
            case MALLADELEVEN:
                MallAdBean mallAdBean1 = (MallAdBean) o;
                List<MallAdBean.ResultBean> result1 = mallAdBean1.getResult();
                mRlvAdapterShopData.setDataEleven(result1);
                break;
            case COMMENTLIST:
                CommentListBeans commentListBeans = (CommentListBeans) o;
                CommentListBeans.ResultBean result2 = commentListBeans.getResult();
                List<CommentListBeans.ResultBean.ItemListBean> itemList = result2.getItemList();
                int itemCount = result2.getItemCount();
                mRlvAdapterShopData.setDataCommentList(itemList, itemCount);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (MyJZVideoPlayerStandardShop.a == 1) {
                MyJZVideoPlayerStandardShop.backButton.performClick();
                MyJZVideoPlayerStandardShop.a = 0;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public UserInfo getUserInfo(String s) {
        return  new UserInfo(s, mUserName, Uri.parse(mAvatar));
    }
}
