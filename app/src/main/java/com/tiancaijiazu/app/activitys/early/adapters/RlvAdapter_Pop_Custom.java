package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 试听课-判断popupwindow逻辑
 */
public class RlvAdapter_Pop_Custom extends RecyclerView.Adapter {
    public List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> mData;
    private Context mContext;
    private onClickLisiterSelect mLisiterSelect;
    private boolean isbo;

    public RlvAdapter_Pop_Custom(List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans, Context context) {
        this.mData = feedbackListBeans;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_custom_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTvTitle.setText(mData.get(i).getTitle());
        holder.mData.setText(mData.get(i).getSummary());
        holder.mCheckbox.setChecked(false);
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mCheckbox.isChecked();
                Log.i("select", "onClick: "+checked);
                if (i < mData.size() - 1) {
                    if (mData.get(mData.size() - 1).isIsbo()) {
                        Toast.makeText(mContext, "该选项不能与最后的选择共同选择", Toast.LENGTH_SHORT).show();
                        holder.mCheckbox.setChecked(false);
                        mData.get(i).setIsbo(false);
                    } else {
                        if(checked){
                            holder.mCheckbox.setChecked(true);
                            mData.get(i).setIsbo(true);
                        }else {
                            holder.mCheckbox.setChecked(false);
                            mData.get(i).setIsbo(false);
                        }
                    }
                }else {
                    for (int j = 0; j < mData.size()-1; j++) {
                        if(mData.get(j).isIsbo()){
                            isbo = true;
                            break;
                        }else {
                            isbo = false;
                        }
                    }

                    if(isbo){
                        Toast.makeText(mContext, "该选项不能与其他的选择共同选择", Toast.LENGTH_SHORT).show();
                        holder.mCheckbox.setChecked(false);
                        mData.get(i).setIsbo(false);
                    }else {
                        if(checked){
                            holder.mCheckbox.setChecked(true);
                            mData.get(i).setIsbo(true);
                        }else {
                            holder.mCheckbox.setChecked(false);
                            mData.get(i).setIsbo(false);
                        }
                    }
                }
                /*if (mLisiterSelect != null) {
                    mLisiterSelect.onClickerSelect(view, i);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackList) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(feedbackList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.data)
        TextView mData;
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterSelect{
        void onClickerSelect(View view,int position);
    }

    public void setOnClickLisiterSelect(onClickLisiterSelect lisiterSelect){
        this.mLisiterSelect = lisiterSelect;
    }
}
