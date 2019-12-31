package com.tiancaijiazu.app.activitys.evalua.adapters;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.evalua.TestReportActivity;
import com.tiancaijiazu.app.beans.EvaluationResultsBean;

import java.util.List;

/**
 * Created by Administrator on 2019/8/19/019.
 */

public class LCardAdapter extends PagerAdapter {
    private List<EvaluationResultsBean.ResultBean.IndicatorsListBean> mData;
    private TestReportActivity mContext;

    public LCardAdapter(List<EvaluationResultsBean.ResultBean.IndicatorsListBean> indicatorsList, TestReportActivity testReportActivity) {
        this.mData = indicatorsList;
        this.mContext = testReportActivity;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;// 过滤和缓存的作用
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {//子View显示
        View view = View.inflate(container.getContext(), R.layout.text_report_view_pager, null);
        RecyclerView recylerView = view.findViewById(R.id.recylerView);
        ImageView iv_ev = view.findViewById(R.id.iv_ev);
        TextView title = view.findViewById(R.id.title);
        TextView score = view.findViewById(R.id.score);
        TextView tv_data = view.findViewById(R.id.tv_data);
        TextView evaluate = view.findViewById(R.id.evaluate);
        String level = mData.get(position).getLevel();
        int per = mData.get(position).getPer();
        if(0<=per&&per<70){
            iv_ev.setImageResource(R.mipmap.not_qualified);
        }else if(per>=70&&per<90){
            iv_ev.setImageResource(R.mipmap.normal);
        }else {
            iv_ev.setImageResource(R.mipmap.excellent);
        }
        evaluate.setText(level);
        title.setText(mData.get(position).getIndicatorsValue());
        score.setText("得"+mData.get(position).getScore()+"分/共"+mData.get(position).getTotalScore()+"分");
        tv_data.setText(mData.get(position).getReview());
        List<EvaluationResultsBean.ResultBean.IndicatorsListBean.OptionListBean> optionList = mData.get(position).getOptionList();
        initRecylerView(recylerView,optionList);
        container.addView(view);//添加到父控件
        return view;
    }

    private void initRecyler(RecyclerView recyler) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyler.setLayoutManager(linearLayoutManager);
        RlvAdapter_Str_suggest rlvAdapterStrSuggest = new RlvAdapter_Str_suggest();
        recyler.setAdapter(rlvAdapterStrSuggest);
    }

    private void initRecylerView(RecyclerView recylerView, List<EvaluationResultsBean.ResultBean.IndicatorsListBean.OptionListBean> optionList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_Progress_Points rlvAdapterProgressPoints = new RlvAdapter_Progress_Points(optionList);
        recylerView.setAdapter(rlvAdapterProgressPoints);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//从viewpager中移除掉
    }

    public void addData(List<EvaluationResultsBean.ResultBean.IndicatorsListBean> indicatorsList) {
        if(mData!=null){
            mData.clear();
        }
        this.mData.addAll(indicatorsList);
        notifyDataSetChanged();
    }
}
