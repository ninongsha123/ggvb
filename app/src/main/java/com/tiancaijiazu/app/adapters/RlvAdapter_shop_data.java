package com.tiancaijiazu.app.adapters;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_Color;
import com.tiancaijiazu.app.activitys.adapters.VpAdapter_img;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_commentlist;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.ShopAdapter_three;
import com.tiancaijiazu.app.beans.CommentListBeans;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.SpecificationBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/5/17/017.
 */

public class RlvAdapter_shop_data extends RecyclerView.Adapter {
    private SpecificationBean mData;
    private ShopActivity mContext;
    private SetOnClickLisiter mLisiter;
    private boolean isbo = true;
    private String mName;
    private int mSum;
    private boolean isboo = false;
    private int height = 0;
    private List<MallAdBean.ResultBean> mDataTen = new ArrayList<>();
    private List<MallAdBean.ResultBean> mDataEleven = new ArrayList<>();
    private List<CommentListBeans.ResultBean.ItemListBean> mDataCommentList = new ArrayList<>();
    private int vpo = 0;
    private onClickLisiterWai mLisiterWai;
    private onClickLisiterNei mLisiterNei;
    private int mCountComment;
    private onClickLisiterComment mLisiterCommet;
    private onClickLisiterLookPicture mLisiterPicture;

    public RlvAdapter_shop_data(ShopActivity shopActivity, SpecificationBean specificationBeans) {
        this.mContext = shopActivity;
        this.mData = specificationBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_data_four, viewGroup, false);
            return new ViewHolder_Four(inflate);

        } else if (i == 1) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_data_one, viewGroup, false);
            return new ViewHolder_One(inflate);

        } else if (i == 2) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_data_two, viewGroup, false);
            return new ViewHolder_Two(inflate);

        } else {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_data_three, viewGroup, false);
            return new ViewHolder_Three(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder_Four holderFour = (ViewHolder_Four) viewHolder;
            LinearLayout line0 = holderFour.mLine0;
            Log.i("yx123", "onBindViewHolder: " + height);
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            holderFour.mRlv.setLayoutManager(layoutManager);
            RlvAdapter_shop rlvAdapterShop = new RlvAdapter_shop(mDataTen);
            holderFour.mRlv.setAdapter(rlvAdapterShop);
            if (isboo) {
                holderFour.mSpecification.setText(mName + "/" + mSum);
            }
            holderFour.mSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLisiter != null) {
                        mLisiter.onClickSelect(v, i);
                    }
                }
            });
            rlvAdapterShop.setOnItemClickListener(new RlvAdapter_shop.setOnItemClick() {
                @Override
                public void setOnItemClickListener(View view, int position, List<MallAdBean.ResultBean> mData) {
                    if (mLisiterWai != null) {
                        mLisiterWai.onClickerWai(view, position, mData);
                    }
                }
            });
            if (!mData.getResult().getVideo().equals("")) {
                holderFour.mVideoPlay.setVisibility(View.VISIBLE);
                String[] split = mData.getResult().getVideo().split("\\?");
                String time = TimeUtil.getTime(Integer.parseInt(split[1]));
                holderFour.mVideoPlay.setText(time);
            } else {
                holderFour.mVideoPlay.setVisibility(View.GONE);
            }

            if (isbo) {
                String banner = mData.getResult().getBanner();
                String[] split = banner.split("[|]");

                VpAdapter_img vpAdapterImg = new VpAdapter_img(mContext, split, vpo, holderFour.mVideoPlay);
                holderFour.mVp.setAdapter(vpAdapterImg);
                holderFour.mVp.setOffscreenPageLimit(split.length);
                holderFour.mPageNumber.setText("1");
                holderFour.mPaginationBack.setText("/" + split.length);
                holderFour.mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        vpo = i;
                        holderFour.mPageNumber.setText((i + 1) + "");
                        holderFour.mPaginationBack.setText("/" + split.length);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });
                holderFour.mVideoPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holderFour.mVideoPlay.setVisibility(View.GONE);
                        vpAdapterImg.addVideo(mData.getResult().getVideo(), vpo);
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                holderFour.mRlvYanse.setLayoutManager(linearLayoutManager);
                List<SpecificationBean.ResultBean.SkuListBean> skuList = mData.getResult().getSkuList();
                holderFour.mColorSum.setText(skuList.size() + "色可选");
                long skuId = mData.getResult().getSkuId();
                RlvAdapter_Color rlvAdapterColor = new RlvAdapter_Color(skuList, mContext, skuId);
                holderFour.mRlvYanse.setAdapter(rlvAdapterColor);
                String name = mData.getResult().getName();
                holderFour.mCommodityName.setText(name);
                /*String detail = result.getDetail();
                holderFour.mTv.setText(detail);*/
                double price = mData.getResult().getPrice();
                holderFour.mPrice.setText(price + "");
            }
            getMeasureHeight(line0, viewType);
        } else if (viewType == 1) {
            ViewHolder_One holderOne = (ViewHolder_One) viewHolder;
            LinearLayout line1 = holderOne.mLine1;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            holderOne.mRecyclerView.setLayoutManager(linearLayoutManager);
            linearLayoutManager.setAutoMeasureEnabled(true);
            String appDescription = mData.getResult().getAppDescription();
            String[] split = appDescription.split("[|]");
            RlvAdapter_details_img rlvAdapterDetailsImg = new RlvAdapter_details_img(split, mContext);
            holderOne.mRecyclerView.setAdapter(rlvAdapterDetailsImg);
            getMeasureHeight(line1, viewType);
        } else if (viewType == 2) {
            ViewHolder_Two holderTwo = (ViewHolder_Two) viewHolder;
            if (mCountComment == 0) {
                holderTwo.look_at_all.setVisibility(View.GONE);
                holderTwo.mUsrTou.setVisibility(View.GONE);
                holderTwo.rlv_commentlist.setVisibility(View.GONE);
                holderTwo.tv_summary.setVisibility(View.GONE);
                holderTwo.user_nickname.setVisibility(View.GONE);
                holderTwo.tv_commentTime.setVisibility(View.GONE);
                holderTwo.tv_flase.setVisibility(View.VISIBLE);
            } else {
                holderTwo.user_nickname.setText(mDataCommentList.get(0).getNickname());
                Glide.with(mContext).load(mDataCommentList.get(0).getAvatar()).into(holderTwo.mUsrTou);
                holderTwo.tv_summary.setText(mDataCommentList.get(0).getSummary());
                String commentTime = mDataCommentList.get(0).getCommentTime();
                long l = TimeUtil.dataOne(commentTime);
                String s = TimeUtil.QQFormatTime(l);
                holderTwo.tv_commentTime.setText(s);
                holderTwo.look_at_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mLisiterCommet!=null){
                            mLisiterCommet.onClickerComment(v,i,mDataCommentList);
                        }
                    }
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                holderTwo.rlv_commentlist.setLayoutManager(linearLayoutManager);
                Log.i("bjg", "onBindViewHolder: "+i);
                String pics = mDataCommentList.get(0).getPics();
                String[] split = pics.split("[|]");
                ArrayList<String> strings = new ArrayList<>();
                if(!split[0].equals("")){
                    for (int j = 0; j < split.length; j++) {
                        strings.add(split[j]);
                    }
                }
                RlvAdapter_commentlist rlvAdapter_commentlist = new RlvAdapter_commentlist(strings);
                holderTwo.rlv_commentlist.setAdapter(rlvAdapter_commentlist);
                LinearLayout line2 = holderTwo.line2;
                getMeasureHeight(line2, viewType);
                rlvAdapter_commentlist.setOnItemLickListener(new RlvAdapter_commentlist.setOnItemClickListener() {
                    @Override
                    public void setOnItemClick(View v, int position, ArrayList<String> mDataImage) {
                        if (mLisiterPicture!=null){
                            mLisiterPicture.onClickerLookPicture(v,i,strings);
                        }
                    }
                });
            }

        } else {
            ViewHolder_Three holderThree = (ViewHolder_Three) viewHolder;
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            holderThree.mRlv.setLayoutManager(gridLayoutManager);
            ShopAdapter_three shopAdapterThree = new ShopAdapter_three(mDataEleven);
            holderThree.mRlv.setAdapter(shopAdapterThree);
            LinearLayout line3 = holderThree.mLine3;
            getMeasureHeight(line3, viewType);
            shopAdapterThree.setOnItemClickListener(new RlvAdapter_shop.setOnItemClick() {
                @Override
                public void setOnItemClickListener(View view, int position, List<MallAdBean.ResultBean> mData) {
                    if (mLisiterNei != null) {
                        mLisiterNei.onClickerNei(view, position, mData);
                    }
                }
            });
        }
    }

    /**
     * 获取每个item的高度
     *
     * @param view item的跟布局
     * @param type 用于判断是那个item的高度
     */
    public void getMeasureHeight(final View view, final int type) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (listener != null) {
                    if (type == 1 || type == 2) {
                        if (height != 0) {
                            height += view.getHeight();
                            listener.setOnItemHeightListener(height, type);
                        } else {
                            height = view.getHeight();
                        }
                    } else {
                        listener.setOnItemHeightListener(view.getHeight(), type);
                    }

                }
            }
        });
    }

    public void setDataTen(List<MallAdBean.ResultBean> result) {
        if (mDataTen != null) {
            mDataTen.clear();
        }
        this.mDataTen.addAll(result);
        notifyItemChanged(0);
    }

    public void setDataEleven(List<MallAdBean.ResultBean> result1) {
        if (mDataEleven != null) {
            mDataEleven.clear();
        }
        this.mDataEleven.addAll(result1);
        notifyItemInserted(3);
    }

    public void setDataCommentList(List<CommentListBeans.ResultBean.ItemListBean> itemList, int itemCount) {
        if (mDataCommentList != null) {
            mDataCommentList.clear();
        }
        this.mCountComment = itemCount;
        this.mDataCommentList.addAll(itemList);
        notifyItemInserted(2);
    }

    public interface OnItemHeightListener {
        void setOnItemHeightListener(int height, int type);
    }

    private OnItemHeightListener listener;

    public void setListener(OnItemHeightListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void addData(SpecificationBean specificationBean) {
        this.mData = specificationBean;
        isbo = true;
        //notifyItemChanged(0);
        notifyDataSetChanged();
    }

    public void addStr(String title, int sum) {
        this.mName = title;
        this.mSum = sum;
        isboo = true;
        //notifyItemChanged(0);
        notifyDataSetChanged();
    }

    class ViewHolder_One extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.line1)
        LinearLayout mLine1;
        @BindView(R.id.recylerView)
        RecyclerView mRecyclerView;

        ViewHolder_One(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position == 1) {
            return 1;
        } else if (position == 2) {
            return 2;
        } else {
            return 3;
        }
    }

    class ViewHolder_Two extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_commentTime)
        TextView tv_commentTime;
        @BindView(R.id.usr_avatar)
        CircleImageView mUsrTou;
        @BindView(R.id.tv_summary)
        TextView tv_summary;  @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.rlv_commentlist)
        RecyclerView rlv_commentlist;
        @BindView(R.id.user_nickname)
        TextView user_nickname;
        @BindView(R.id.tv_flase)
        TextView tv_flase;
        @BindView(R.id.look_at_all)
        LinearLayout look_at_all;
        @BindView(R.id.line2)
        LinearLayout line2;

        ViewHolder_Two(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder_Three extends RecyclerView.ViewHolder {
        @BindView(R.id.rlv)
        RecyclerView mRlv;   @BindView(R.id.tv)
        TextView tv;
        @BindView(R.id.line3)
        LinearLayout mLine3;

        ViewHolder_Three(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder_Four extends RecyclerView.ViewHolder {
        @BindView(R.id.fu)
        TextView mFu;
        @BindView(R.id.tv)
        TextView mTv;
        @BindView(R.id.color_sum)
        TextView mColorSum;
        @BindView(R.id.price)
        TextView mPrice;
        @BindView(R.id.sales_quantity)
        TextView mSalesQuantity;
        @BindView(R.id.rlv)
        RecyclerView mRlv;
        @BindView(R.id.rlv_yanse)
        RecyclerView mRlvYanse;
        @BindView(R.id.select)
        LinearLayout mSelect;
        @BindView(R.id.vp)
        ViewPager mVp;
        @BindView(R.id.commodity_name)
        TextView mCommodityName;
        @BindView(R.id.specification)
        TextView mSpecification;
        @BindView(R.id.video_play)
        TextView mVideoPlay;
        @BindView(R.id.page_number)
        TextView mPageNumber;
        @BindView(R.id.pagination_back)
        TextView mPaginationBack;@BindView(R.id.onlyyou)
        TextView onlyyou;
        @BindView(R.id.line0)
        LinearLayout mLine0;

        ViewHolder_Four(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface SetOnClickLisiter {
        void onClickSelect(View view, int position);
    }

    public void onSetOnClickLisiter(SetOnClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterWai {
        void onClickerWai(View view, int position, List<MallAdBean.ResultBean> mData);
    }

    public void setOnClickLisiterWai(onClickLisiterWai lisiter) {
        this.mLisiterWai = lisiter;
    }

    public interface onClickLisiterNei {
        void onClickerNei(View view, int position, List<MallAdBean.ResultBean> mData);
    }

    public void setOnClickLisiterNei(onClickLisiterNei lisiter) {
        this.mLisiterNei = lisiter;
    }

    public interface onClickLisiterComment {
        void onClickerComment(View view, int position,List<CommentListBeans.ResultBean.ItemListBean> mDataCommentList );
    }

    public void setOnClickLisiterComment(onClickLisiterComment lisiter) {
        this.mLisiterCommet = lisiter;
    }
    public interface onClickLisiterLookPicture {
        void onClickerLookPicture(View view, int position,ArrayList<String> mData );
    }

    public void setOnClickLisiterLookPicture(onClickLisiterLookPicture lisiter) {
        this.mLisiterPicture = lisiter;
    }
}
