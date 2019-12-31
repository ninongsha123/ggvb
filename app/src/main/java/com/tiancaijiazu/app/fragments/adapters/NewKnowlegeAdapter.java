package com.tiancaijiazu.app.fragments.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.KnowlegePlayActivity;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.SubheadTwoActivity;
import com.tiancaijiazu.app.beans.MusicBean;
import com.tiancaijiazu.app.beans.SongBean;
import com.tiancaijiazu.app.beans.SongBeanss;
import com.tiancaijiazu.app.beans.SongListBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Administrator on 2019/5/14/014.
 */

public class NewKnowlegeAdapter extends RecyclerView.Adapter {


    private List<MusicBean.ResultBean> mData1;
    private SubheadTwoActivity mContext;
    public OnItemClickListener itemClickListener;
    private List<Boolean> isClicks=new ArrayList<>();

    public NewKnowlegeAdapter(List<MusicBean.ResultBean> resultBeans, SubheadTwoActivity subheadTwoActivity) {
        this.mData1 = resultBeans;
        this.mContext = subheadTwoActivity;
    }


    public void addData(List<MusicBean.ResultBean> result2, boolean b) {
        if (b) {
            if (mData1 != null) {
                mData1.clear();
            }
        }

        this.mData1.addAll(result2);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, List<MusicBean.ResultBean> mData);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.zao_wan_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mMname.setText(mData1.get(i).getTitle());
        String mediaUri = mData1.get(i).getMediaUri();
        String[] split = mediaUri.split("\\?");
        if(split.length>1){
            int i1 = Integer.parseInt(split[1]);
            String time = TimeUtil.getTime(i1);
            holder.mMtime.setText("时长:"+time);
        }

        for (int j = 0; j < mData1.size(); j++) {
            isClicks.add(false);
        }
        if (isClicks.get(i)){
            holder.mMname.setTextColor(Color.parseColor("#00DEFF"));
        }else {
            holder.mMname.setTextColor(Color.parseColor("#333333"));
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v,2000)){
                    return;
                }
                for(int i = 0; i <isClicks.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(i,true);
                notifyDataSetChanged();

                itemClickListener.onItemClick(v, i, mData1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData1.size();
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
}
