package com.tiancaijiazu.app.activitys.evalua.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.EvaluaBabyActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/8/17/017.
 */

public class RlvAdapter_evalua_baby extends RecyclerView.Adapter {
    private List<BabyMessageBean.ResultBean> mData;
    private EvaluaBabyActivity mContext;
    private onClickLisiter mLisiter;

    public RlvAdapter_evalua_baby(List<BabyMessageBean.ResultBean> resultBeans, EvaluaBabyActivity evaluaBabyActivity) {
        this.mData = resultBeans;
        this.mContext = evaluaBabyActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_select_baby, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int gender = mData.get(i).getGender();
        if(gender == 1){
            //holder.mMoren.setImageResource(R.mipmap.baby_pic);
            holder.mRela.setBackgroundResource(R.drawable.bg_baby_select_nan);
        }else if(gender == 2){
            //holder.mMoren.setImageResource(R.mipmap.baby_pic);
            holder.mRela.setBackgroundResource(R.drawable.bg_baby_select_nv);
        }
        if(mData.get(i).getAvatar().equals("")){
            holder.mMoren.setImageResource(R.mipmap.baby_pic);
        }else {
            Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mMoren);
        }
        holder.mBabyName.setText(mData.get(i).getName());
        String birthday = mData.get(i).getBirthday();
        String nowTime = TimeUtil.getNowTime();
        String age = TimeUtil.getAge(birthday, nowTime);
        holder.mBabyAge.setText(age);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mData.size()==0){
            return 0;
        }else if(mData.size()<2){
            return mData.size();
        }else {
            return 2;
        }
    }

    public void addData(List<BabyMessageBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.moren)
        CircleImageView mMoren;
        @BindView(R.id.head_back)
        FrameLayout mHeadBack;
        @BindView(R.id.baby_name)
        TextView mBabyName;
        @BindView(R.id.baby_age)
        TextView mBabyAge;
        @BindView(R.id.rela)
        RelativeLayout mRela;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<BabyMessageBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
