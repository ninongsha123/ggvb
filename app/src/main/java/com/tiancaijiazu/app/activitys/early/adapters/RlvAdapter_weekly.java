package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/7/5/005.
 */

public class RlvAdapter_weekly extends RecyclerView.Adapter {
    private Context mContext;
    private String mColor;
    private List<EarlyCourseListBean.ResultBean> mData;
    private onClickLisiter mLisiter;

    public RlvAdapter_weekly( List<EarlyCourseListBean.ResultBean> beanShis, String color, Context context) {
        this.mData = beanShis;
        this.mColor = color;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weekly_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitle.setText(mData.get(i).getTitle());
        holder.mTitle.setTextColor(Color.parseColor(mColor));
        holder.mDataTv.setText(mData.get(i).getSummary());
       /* LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<BeanShi> beanShis = new ArrayList<>();
        RlvAdapter_inside rlvAdapterInside = new RlvAdapter_inside(beanShis);
        holder.mRecylerView.setAdapter(rlvAdapterInside);
        holder.mDropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mDropDown.isChecked();
                if (checked) {
                    rlvAdapterInside.addData(mDataShi);
                }else {
                    rlvAdapterInside.claerData();
                    if(mLisiter!=null){
                        mLisiter.onClicker(view,i);
                    }
                }
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(String s) {
        this.mColor = s;
        notifyDataSetChanged();
    }

    public void addDataU(List<EarlyCourseListBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }

        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.lcard)
        LCardView mLcard;
        @BindView(R.id.data)
        TextView mDataTv;

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
