package com.tiancaijiazu.app.activitys.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.beans.SpecificationBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/1/001.
 */

public class RlvAdapter_guige extends RecyclerView.Adapter {
    private List<SpecificationBean.ResultBean.StockListBean> mStockList;
    private ShopActivity mContext;
    private List<SpecificationBean.ResultBean.SellPropertiesBean> mData;
    private ArrayList<String> mList = new ArrayList<>();
    private ArrayList<String> mName = new ArrayList<>();
    private String str = "";
    private String title = "";
    private onClickLisiter mLisiter;
    public long mStockId;
    private String mSubstring;
    public String mTitle;

    public RlvAdapter_guige(ShopActivity context, List<SpecificationBean.ResultBean.SellPropertiesBean> sellProperties, List<SpecificationBean.ResultBean.StockListBean> stockList) {
        this.mContext = context;
        this.mData = sellProperties;
        this.mStockList = stockList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_two, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mGuige.setText(mData.get(i).getName());
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 10, calculateDpToPx(10), 15);
        if (holder.mFlow != null) {
            holder.mFlow.removeAllViews();
        }
        List<SpecificationBean.ResultBean.SellPropertiesBean.OptionListBean> optionList = mData.get(i).getOptionList();
        for (int j = 0; j < optionList.size(); j++) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(layoutParams1);
            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText(optionList.get(j).getName());
            if (optionList.get(j).isIsbo()) {
                textView.setBackgroundResource(R.drawable.guege_shape_strue);
                textView.setTextColor(Color.parseColor("#FF0663"));
            } else {
                textView.setBackgroundResource(R.drawable.guege_shape);
                textView.setTextColor(Color.parseColor("#333333"));
            }
            initEvents(textView);
            textView.setPadding(calculateDpToPx(10), calculateDpToPx(6), calculateDpToPx(10), calculateDpToPx(6));
            holder.mFlow.addView(textView, j, layoutParams1);
        }
        if(i == 0){
            mList.clear();
            mName.clear();
        }
            for (int k = 0; k < optionList.size(); k++) {
                boolean isbo = optionList.get(k).isIsbo();
                if(isbo){
                    long optionsId = optionList.get(k).getOptionsId();
                    mList.add(optionsId+"");
                    mName.add(optionList.get(k).getName());
                }
            }
            if(i == mData.size()-1){
            if(mList.size()==mData.size()){
                str = "";
                title = "";
                for (int j = 0; j < mList.size(); j++) {
                    if(mList.size()==1){
                        title = mName.get(j);
                    }else if(mList.size()>1){
                        title += mName.get(j)+"/";
                    }
                    if(mList.size()==1){
                        str = mList.get(j);
                    }else if(mList.size()>1){
                        str += mList.get(j)+",";
                    }
                }
                Log.i("yx520",  mList.size()+"onBindViewHolder: "+str);
                if(mName.size()==1){
                    mTitle = title;
                }else {
                    mTitle = title.substring(0, title.length()-1);
                }
                if(mList.size()==1){
                    mSubstring = str;
                }else {
                    mSubstring = str.substring(0, str.length()-1);
                }

                Log.i("yx520", "onBindViewHolder: "+ mSubstring);
                for (int j = 0; j < mStockList.size(); j++) {
                    if(mStockList.get(j).getOptionIds().equals(mSubstring)){
                        mStockId = mStockList.get(j).getStockId();
                        if(mLisiter!=null){
                            mLisiter.onClicker(mStockList.get(j).getStockId(),mStockList.get(j).getPrice(),mStockList.get(j).getPromoPrice(),mStockList.get(j).getStock()
                            ,mStockList.get(j).getOptionIds());
                        }
                    }
                }
            }
        }
    }
    private int calculateDpToPx(int padding_in_dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }
    /**
     * 为每个view 添加点击事件
     */
    private void initEvents(final TextView tv) {

        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, tv.getText().toString(), Toast.LENGTH_SHORT).show();
                for (int i = 0; i < mData.size(); i++) {
                    for (int j = 0; j < mData.get(i).getOptionList().size(); j++) {
                        if(mData.get(i).getOptionList().get(j).getName().equals(tv.getText().toString())){

                            for (int k = 0; k < mData.get(i).getOptionList().size(); k++) {
                                if(mData.get(i).getOptionList().get(k).getName().equals(tv.getText().toString())){
                                    mData.get(i).getOptionList().get(k).setIsbo(true);
                                }else {
                                    mData.get(i).getOptionList().get(k).setIsbo(false);
                                }
                            }
                            break;
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.guige)
        TextView mGuige;
        @BindView(R.id.flow)
        FlowGroupView mFlow;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface onClickLisiter{
        void onClicker(long stockId,double price,double promoPrice,int stock,String optionIds);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
