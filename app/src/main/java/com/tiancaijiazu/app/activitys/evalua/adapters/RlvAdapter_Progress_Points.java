package com.tiancaijiazu.app.activitys.evalua.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.EvaluationResultsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/19/019.
 */

public class RlvAdapter_Progress_Points extends RecyclerView.Adapter {

    private List<EvaluationResultsBean.ResultBean.IndicatorsListBean.OptionListBean> mData;

    public RlvAdapter_Progress_Points(List<EvaluationResultsBean.ResultBean.IndicatorsListBean.OptionListBean> optionList) {
        this.mData = optionList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_progress_points, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mName.setText(mData.get(i).getOptionValue());
        int score = mData.get(i).getScore();
        int totalScore = mData.get(i).getTotalScore();
        holder.mBi.setText(score+"/"+totalScore);
        float sum = (float) score / totalScore * 100;
        holder.mBottomProgress.setProgress((int) sum);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.bottom_progress)
        ProgressBar mBottomProgress;
        @BindView(R.id.bi)
        TextView mBi;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
