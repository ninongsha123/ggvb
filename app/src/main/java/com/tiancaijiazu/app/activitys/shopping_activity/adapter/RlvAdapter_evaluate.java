package com.tiancaijiazu.app.activitys.shopping_activity.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.beans.EvaluateBoolean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_evaluate extends RecyclerView.Adapter {

    public List<EvaluateBoolean> mBoolean;
    private Context context;
    private setOnItemClick listener;
    private int mPosition = 0;

    public RlvAdapter_evaluate(List<EvaluateBoolean> evaluateBooleans) {
        this.mBoolean=evaluateBooleans;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.evaluate_item_layout, null, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        Boolean aBoolean = mBoolean.get(i).getmBoolean();
        if (aBoolean){
            holder.mStartEvaluate.setChecked(true);
        }else {
            holder.mStartEvaluate.setChecked(false);
        }
        holder.mStartEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.setOnItemClickListener(v,i,mBoolean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBoolean.size();
    }

    public void addUp() {
        notifyDataSetChanged();
    }

    static
    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.start_evaluate)
        CheckBox mStartEvaluate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface setOnItemClick{
        void setOnItemClickListener(View v,int position,List<EvaluateBoolean> mBoolean);
    }
    public void setOnClickListener(setOnItemClick listener){
        this.listener=listener;
    }
}
