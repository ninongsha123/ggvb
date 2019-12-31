package com.tiancaijiazu.app.fragments.outermostlayer.college_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MallAdBean;
import com.tiancaijiazu.app.beans.MallAdBeanTwo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RlvAdapter_music extends RecyclerView.Adapter<RlvAdapter_music.ViewHolder> {
    private Context mContext;
    private List<MallAdBeanTwo.ResultBean> mList;
    private onClickLisiter mLisiter;

    public RlvAdapter_music(Context context, List<MallAdBeanTwo.ResultBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.catalogue_data, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        viewHolder.titletext.setText(mList.get(position).getTitle());
       /* if(position == mList.size()-1){
            viewHolder.mV.setVisibility(View.GONE);
        }*/
        Glide.with(mContext).load(mList.get(position).getPicUri()).into(viewHolder.headset);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,position,mList);
                }
            }
        });
    }

    // 获取颜色值
    public int showColor(int resId) {
        return mContext.getResources().getColor(resId);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void addData(List<MallAdBeanTwo.ResultBean> result2) {
        if(mList!=null){
            mList.clear();
        }

        this.mList.addAll(result2);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headset)
        ImageView headset;
        @BindView(R.id.title_text)
        TextView titletext;
       /* @BindView(R.id.v)
        View mV;*/
        @BindView(R.id.rl)
        RelativeLayout mRl;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<MallAdBeanTwo.ResultBean> mList);
    }
    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
