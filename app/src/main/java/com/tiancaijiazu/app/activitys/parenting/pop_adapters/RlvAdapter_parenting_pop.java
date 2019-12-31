package com.tiancaijiazu.app.activitys.parenting.pop_adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/6/17/017.
 */

public class RlvAdapter_parenting_pop extends RecyclerView.Adapter {
    private String one;
    private String two;
    private ArrayList<String> mData;
    private onClickLisiter mLisiter;

    public RlvAdapter_parenting_pop(ArrayList<String> strings, String period, String classify) {
        this.mData = strings;
        this.one = period;
        this.two = classify;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pop_divide_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTv.setText(mData.get(i));
        if(mData.get(i).equals(one)||mData.get(i).equals(two)){
            holder.mTv.setTextColor(Color.parseColor("#4080FC"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker(v,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(ArrayList<String> list) {
        if(mData!=null){
            mData.clear();
        }
        mData.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv)
        TextView mTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view, int position, ArrayList<String> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }
}
