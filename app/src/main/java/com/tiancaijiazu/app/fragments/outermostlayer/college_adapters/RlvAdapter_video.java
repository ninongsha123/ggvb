package com.tiancaijiazu.app.fragments.outermostlayer.college_adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MallAdBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/5/005.
 */

public class RlvAdapter_video extends RecyclerView.Adapter<RlvAdapter_video.MyViewHolder> {
    private Context mContext;
    private List<MallAdBean.ResultBean> dataList;
    private LayoutInflater layoutInflater;
    private onClickLisiter mLisiter;

    public RlvAdapter_video(Context context, List<MallAdBean.ResultBean> dataList) {
        this.dataList = dataList;
        layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.parent_video, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*if(position==0){
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) holder.mRela.getLayoutParams();
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, mContext.getResources().getDisplayMetrics());
            layoutParams.leftMargin = left;
            holder.mRela.setLayoutParams(layoutParams);
        }*/
        Glide.with(mContext).load(dataList.get(position).getPicUri()).into(holder.mImage);
        holder.mTitle.setText(dataList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,dataList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(List<MallAdBean.ResultBean> result1) {
        if(dataList!=null){
            dataList.clear();
        }

        this.dataList.addAll(result1);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        RoundedImageView mImage;
        @BindView(R.id.rl)
        RelativeLayout mRl;
        @BindView(R.id.rela)
        RelativeLayout mRela;
        @BindView(R.id.title)
        TextView mTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<MallAdBean.ResultBean> dataList);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
