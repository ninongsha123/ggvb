package com.tiancaijiazu.app.activitys.parenting.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.parenting.ParentingGuideActivity;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/18/018.
 */

public class RlvAdapter_parenting_data extends RecyclerView.Adapter {
    private ParentingGuideActivity mContext;
    private boolean mIsbo;
    private onClickLisiter mLisiter;

    public RlvAdapter_parenting_data(ParentingGuideActivity parentingGuideActivity) {
        this.mContext = parentingGuideActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.parenting_data_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        if(mIsbo){
            holder.mLineCan.setVisibility(View.GONE);
            holder.mLineEatFour.setVisibility(View.GONE);
            holder.mLineEatLess.setVisibility(View.GONE);
            holder.mLineNotCan.setVisibility(View.GONE);
            holder.mMaxIv.setVisibility(View.VISIBLE);
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, mContext.getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mJieshao.getLayoutParams();
            layoutParams.rightMargin = right;
            holder.mJieshao.setLayoutParams(layoutParams);
        }else {
            holder.mLineNotCan.setVisibility(View.VISIBLE);
            holder.mLineEatLess.setVisibility(View.VISIBLE);
            holder.mLineEatFour.setVisibility(View.VISIBLE);
            holder.mLineCan.setVisibility(View.VISIBLE);
            holder.mMaxIv.setVisibility(View.GONE);
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, mContext.getResources().getDisplayMetrics());
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mJieshao.getLayoutParams();
            layoutParams.rightMargin = right;
            holder.mJieshao.setLayoutParams(layoutParams);
        }
        if(i==9){
            holder.mV.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker(v,i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public void addIsbo(boolean b) {
        this.mIsbo = b;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.mice_iv)
        NiceImageView mMiceIv;
        @BindView(R.id.name_title)
        TextView mNameTitle;
        @BindView(R.id.jieshao)
        TextView mJieshao;
        @BindView(R.id.can_iv)
        ImageView mCanIv;
        @BindView(R.id.line_can)
        LinearLayout mLineCan;
        @BindView(R.id.can_not_iv)
        ImageView mCanNotIv;
        @BindView(R.id.line_not_can)
        LinearLayout mLineNotCan;
        @BindView(R.id.eat_less_iv)
        ImageView mEatLessIv;
        @BindView(R.id.line_eat_less)
        LinearLayout mLineEatLess;
        @BindView(R.id.eat_four_iv)
        ImageView mEatFourIv;
        @BindView(R.id.max_iv)
        ImageView mMaxIv;
        @BindView(R.id.line_eat_four)
        LinearLayout mLineEatFour;
        @BindView(R.id.v)
        View mV;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
