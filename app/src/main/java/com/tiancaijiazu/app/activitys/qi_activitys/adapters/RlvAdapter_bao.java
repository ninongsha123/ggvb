package com.tiancaijiazu.app.activitys.qi_activitys.adapters;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.qi_activitys.BaoActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.beans.BabyMessageBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/8/7/007.
 */

public class RlvAdapter_bao extends RecyclerView.Adapter {


    private BaoActivity mContext;
    private List<BabyMessageBean.ResultBean> mData;
    private onClickLisiter mLisiter;
    private onClickLisiterRedact mLisiterRedact;
    private App mApplication;

    public RlvAdapter_bao(List<BabyMessageBean.ResultBean> size, BaoActivity baoActivity) {
        this.mData = size;
        this.mContext = baoActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bao_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mEdBabyname.setText(mData.get(i).getName());
        holder.mEdBabybirth.setText(mData.get(i).getBirthday());
        int gender = mData.get(i).getGender();
        if (gender == 1) {
            holder.mEdSex.setText("男宝宝");
        } else if (gender == 2) {
            holder.mEdSex.setText("女宝宝");
        }
        Glide.with(mContext).load(mData.get(i).getAvatar()).into(holder.mBabyIv);
        int isDefault = mData.get(i).getIsDefault();
        if (isDefault == 0) {

        } else if (isDefault == 1) {
            holder.mDefau.setVisibility(View.VISIBLE);
        }
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiter != null) {
                    mLisiter.onClicker(view, i, mData);
                }
            }
        });
        holder.mCompile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiterRedact != null) {
                    mLisiterRedact.onClickerRedact(view, i, mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mData.size() >= 2) {
            return 2;
        } else {
            return mData.size();
        }
    }

    public void addData(List<BabyMessageBean.ResultBean> result) {
        if (mData != null) {
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.defau)
        ImageView mDefau;
        @BindView(R.id.baby_name)
        TextView mBabyName;
        @BindView(R.id.ed_babyname)
        TextView mEdBabyname;
        @BindView(R.id.line2)
        LinearLayout mLine2;
        @BindView(R.id.v_one)
        View mVOne;
        @BindView(R.id.baby_birth)
        TextView mBabyBirth;
        @BindView(R.id.ed_babybirth)
        TextView mEdBabybirth;
        @BindView(R.id.line3)
        LinearLayout mLine3;
        @BindView(R.id.v_two)
        View mVTwo;
        @BindView(R.id.baby_sex)
        TextView mBabySex;
        @BindView(R.id.ed_sex)
        TextView mEdSex;
        @BindView(R.id.line4)
        LinearLayout mLine4;
        @BindView(R.id.v_three)
        View mVThree;
        @BindView(R.id.compile)
        ImageView mCompile;
        @BindView(R.id.delete)
        ImageView mDelete;
        @BindView(R.id.cv)
        LCardView mCv;
        @BindView(R.id.baby_iv)
        CircleImageView mBabyIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<BabyMessageBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterRedact {
        void onClickerRedact(View view, int position, List<BabyMessageBean.ResultBean> mData);
    }

    public void setOnClickLisiterRedact(onClickLisiterRedact lisiterRedact) {
        this.mLisiterRedact = lisiterRedact;
    }
}
