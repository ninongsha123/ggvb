package com.tiancaijiazu.app.activitys.past.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.past.PastCourseActivity;
import com.tiancaijiazu.app.beans.EarlyCourseBean;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/7/24/024.
 */

public class RlvAdapter_past_title extends RecyclerView.Adapter {
    private List<EarlyCourseBean.ResultBean.ChapterListBean> mStr;
    private List<EarlyCourseListBean.ResultBean> mData;
    private PastCourseActivity mContext;
    private onClickLisiter mLisiter;
    private ArrayList<RlvAdapter_past_data> mList = new ArrayList<>();

    public RlvAdapter_past_title(List<EarlyCourseListBean.ResultBean> list, PastCourseActivity pastCourseActivity, List<EarlyCourseBean.ResultBean.ChapterListBean> strings) {
        this.mData = list;
        this.mContext = pastCourseActivity;
        this.mStr = strings;
        if(mList!=null){
            mList.clear();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_past_title_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mNameTitle.setText(mData.get(i).getTitle());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        holder.mRecyler.setLayoutManager(linearLayoutManager);
        RlvAdapter_past_data rlvAdapterPastData = new RlvAdapter_past_data(mStr);
        mList.add(rlvAdapterPastData);
        holder.mRecyler.setAdapter(rlvAdapterPastData);
        holder.mLcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mCheckbox.isChecked();
                if(checked){
                    holder.mRecyler.setVisibility(View.GONE);
                    holder.mCheckbox.setChecked(false);
                }else {
                    if(mLisiter!=null){
                        mLisiter.onClicker(view,i,mData);
                    }
                    holder.mRecyler.setVisibility(View.VISIBLE);
                    holder.mCheckbox.setChecked(true);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<EarlyCourseListBean.ResultBean> result) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    public void addUpData(List<EarlyCourseBean.ResultBean.ChapterListBean> chapterList, int position) {
        mList.get(position).addData(chapterList);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.name_title)
        TextView mNameTitle;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.recyler)
        RecyclerView mRecyler;
        @BindView(R.id.lcard)
        LCardView mLcard;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int position,List<EarlyCourseListBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
