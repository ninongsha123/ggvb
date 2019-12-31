package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.tiancaijiazu.app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/7/5/005.
 */

public class RlvAdapter_hori extends RecyclerView.Adapter {
    private ArrayList<Integer> mData;
    private int position = 0;
    private onClickLisiter mLisiter;

    public RlvAdapter_hori(ArrayList<Integer> drawables) {
        this.mData = drawables;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_official_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mCheckbox.setBackgroundResource(mData.get(i));
        if(position == i){
            holder.mCheckbox.setChecked(true);
            holder.mCheckbox.setClickable(false);
            holder.mCheckbox.setEnabled(false);
        }else {
            holder.mCheckbox.setChecked(false);
            holder.mCheckbox.setClickable(true);
            holder.mCheckbox.setEnabled(true);
        }
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiter!=null){
                    mLisiter.onClicker(view,i);
                }
               position = i;
               notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;

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
