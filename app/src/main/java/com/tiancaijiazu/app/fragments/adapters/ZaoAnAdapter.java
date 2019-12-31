package com.tiancaijiazu.app.fragments.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.SongBeanss;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ZaoAnAdapter extends RecyclerView.Adapter {


    private List<SongBeanss.ResultBean.ItemListBean> mData;
    private onClickLisiter mLisiter;

    private List<Boolean> isClicks=new ArrayList<>();


    public ZaoAnAdapter(List<SongBeanss.ResultBean.ItemListBean> data) {
        this.mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zao_wan_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder= (ViewHolder) viewHolder;
        holder.mMname.setText(mData.get(i).getTitle());
        String mediaUri = mData.get(i).getMediaUri();
        String[] split = mediaUri.split("\\?");
        if(split.length>1){
            int i1 = Integer.parseInt(split[1]);
            String time = TimeUtil.getTime(i1);
            holder.mMtime.setText("时长:"+time);
        }
        for (int j = 0; j < mData.size(); j++) {
            isClicks.add(false);
        }
        if (isClicks.get(i)){
            holder.mMname.setTextColor(Color.parseColor("#00DEFF"));
        }else {
            holder.mMname.setTextColor(Color.parseColor("#333333"));
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TimeUtil.isInvalidClick(view,2000)){
                    return;
                }
                for(int i = 0; i <isClicks.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(i,true);
                notifyDataSetChanged();
                mLisiter.onClicker(view,i,mData);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mname)
        TextView mMname;
        @BindView(R.id.mtime)
        TextView mMtime;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void addData (List<SongBeanss.ResultBean.ItemListBean> beans){
        mData.addAll(beans);
        notifyDataSetChanged();
    }


    public interface onClickLisiter {
        void onClicker(View view, int position, List<SongBeanss.ResultBean.ItemListBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
