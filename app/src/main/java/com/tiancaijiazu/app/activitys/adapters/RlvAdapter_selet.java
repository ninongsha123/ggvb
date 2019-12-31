package com.tiancaijiazu.app.activitys.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.beans.SpecificationBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/24/024.
 */

public class RlvAdapter_selet extends RecyclerView.Adapter {
    private ShopActivity mContext;
    public SpecificationBean mData;
    private boolean isbo = false;
    private boolean isboo = true;
    public String name;
    public String name1;
    public int sum = 1;
    private int position;
    private setOnClickListerName mLisiter;
    private setOnClickLisiterCiCun mLisiterCi;
    private onClickLisiter mLisiterGuige;
    public RlvAdapter_guige mRlvAdapterGuige;

    public RlvAdapter_selet(ShopActivity shopActivity, SpecificationBean specificationBean) {
        this.mContext = shopActivity;
        this.mData = specificationBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_one, viewGroup, false);
            return new ViewHolder_one(inflate);
        } else{
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_three, viewGroup, false);
            return new ViewHolder_three(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
       if (viewType == 0) {
            ViewHolder_one holderOne = (ViewHolder_one) viewHolder;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            holderOne.mRecylerView.setLayoutManager(linearLayoutManager);
            List<SpecificationBean.ResultBean.SellPropertiesBean> sellProperties = mData.getResult().getSellProperties();
            List<SpecificationBean.ResultBean.StockListBean> stockList = mData.getResult().getStockList();

            mRlvAdapterGuige = new RlvAdapter_guige(mContext,sellProperties,stockList);
            holderOne.mRecylerView.setAdapter(mRlvAdapterGuige);
            mRlvAdapterGuige.setOnClickLisiter(new RlvAdapter_guige.onClickLisiter() {
                @Override
                public void onClicker(long stockId, double price, double promoPrice, int stock,String optionIds) {
                    Log.i("yx520", "onClicker: "+price);
                    if(mLisiterGuige!=null){
                        mLisiterGuige.onClicker(stockId,price,promoPrice,stock,optionIds);
                    }
                }
            });

        } else {
            ViewHolder_three holderThree = (ViewHolder_three) viewHolder;
            holderThree.mIvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sum++;
                    holderThree.mTvNumber.setText(sum + "");
                    holderThree.mIvLess.setClickable(true);
                    holderThree.mIvLess.setImageResource(R.mipmap.guige_less_clickable);
                }
            });
            holderThree.mIvLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sum--;
                    if (sum == 1) {
                        holderThree.mIvLess.setClickable(false);
                        holderThree.mIvLess.setImageResource(R.mipmap.guige_less);
                    }
                    holderThree.mTvNumber.setText(sum + "");
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    private int calculateDpToPx(int padding_in_dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }
    public interface onClickLisiter{
        void onClicker(long stockId,double price,double promoPrice,int stock,String optionIds);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiterGuige = lisiter;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    public void addData(SpecificationBean specificationBean, String optionIds1) {
        if(optionIds1!=null){
            String[] split = optionIds1.split(",");
            List<SpecificationBean.ResultBean.SellPropertiesBean> sellProperties = specificationBean.getResult().getSellProperties();
            for (int i = 0; i < sellProperties.size(); i++) {
                List<SpecificationBean.ResultBean.SellPropertiesBean.OptionListBean> optionList = sellProperties.get(i).getOptionList();
                for (int j = 0; j < optionList.size(); j++) {
                    if(split[i].equals(optionList.get(j).getOptionsId()+"")){
                        optionList.get(j).setIsbo(true);
                    }
                }
            }
        }

        this.mData = specificationBean;

        notifyDataSetChanged();
    }

    class ViewHolder_one extends RecyclerView.ViewHolder {
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolder_one(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    class ViewHolder_three extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_less)
        ImageView mIvLess;
        @BindView(R.id.tv_number)
        TextView mTvNumber;
        @BindView(R.id.iv_add)
        ImageView mIvAdd;

        ViewHolder_three(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface setOnClickLisiterCiCun {
        void onClickCiCun(View view, int potionsid);
    }

    public void onSetClickLisiterCiCun(setOnClickLisiterCiCun lisiterCiCun) {
        this.mLisiterCi = lisiterCiCun;
    }


    public interface setOnClickListerName {
        void onClickName(View v, String name1);
    }

    public void onSetClickListerName(setOnClickListerName listerName) {
        this.mLisiter = listerName;
    }
}

