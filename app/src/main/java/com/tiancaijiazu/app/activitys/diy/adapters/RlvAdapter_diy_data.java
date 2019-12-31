package com.tiancaijiazu.app.activitys.diy.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.diy.DiyActivity;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/10/010.
 */

public class RlvAdapter_diy_data extends RecyclerView.Adapter {
    private ArrayList<String> mData;
    private DiyActivity mContext;
    private int position = 0;

    public RlvAdapter_diy_data(DiyActivity diyActivity, ArrayList<String> list) {
        this.mContext = diyActivity;
        this.mData = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_diy_data, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        switch (position) {
            case 0:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_6_8);
                break;
            case 1:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_8_10);
                break;
            case 2:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_10_14);
                break;
            case 3:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_14_18);
                break;
            case 4:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_18_24);
                break;
            case 5:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_24_30);
                break;
            case 6:
                holder.mDiyIvAge.setImageResource(R.mipmap.diy_data_30_36);
                break;
        }
        int screenWidth = ScreenUtil.getInstance(mContext).getScreenWidth();
        int i1 = ScreenStatusUtil.setDp(64, mContext);
        int i2 = screenWidth - i1;
        int i3 = i2 / 3;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 20, 0, 10);
        if (holder.mFlow != null) {
            holder.mFlow.removeAllViews();
        }
        for (int j = 0; j < mData.size(); j++) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(layoutParams);
            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setText(mData.get(j));
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setGravity(Gravity.LEFT);
            if(j<mData.size()-4){
                textView.setWidth(i3);

            }else {

            }
            holder.mFlow.addView(textView, layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void addData(int position) {
        this.position = position;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.diy_iv_age)
        ImageView mDiyIvAge;
        @BindView(R.id.flow)
        FlowGroupView mFlow;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
