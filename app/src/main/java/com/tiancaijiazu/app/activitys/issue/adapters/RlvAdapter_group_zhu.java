package com.tiancaijiazu.app.activitys.issue.adapters;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.GroupActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.DiaryDetailBean;
import com.tiancaijiazu.app.beans.GroupListBean;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.beans.TopicListsBean;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.NineGridTestLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/22/022.
 */

public class RlvAdapter_group_zhu extends RecyclerView.Adapter {

    private  ArrayList<String> mUrl=new ArrayList<>();
    private ArrayList<DiaryDetailBean.ResultBean> mData;
    private onClickLisiter mLisiter;
    private onClickLisiterDian mLisiterDian;
    private onClickLisiterDianZan mLisiterDianZan;
    private onClickLisiterPing mLisiterPing;
    private GroupActivity mContext;
    private int mSize;

    public RlvAdapter_group_zhu(GroupActivity context,ArrayList<DiaryDetailBean.ResultBean> resultBeans) {
        this.mContext = context;
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_group, viewGroup, false);
        return new ViewHolder(inflate);
    }
// keyTime  2019-12-25
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        mUrl.clear();
        ViewHolder holder = (ViewHolder) viewHolder;
        String bage = PreUtils.getString("bage", "");
        String keyTime = mData.get(i).getKeyTime();
        String yue = keyTime.substring(5, 7);
        String date = keyTime.substring(8, 10);
        holder.mDate.setText(date);
        holder.mYue.setText(yue+"月");
        holder.mPingshu.setText(mData.get(i).getDiscuss()+"");
        holder.mZanshu.setText(mData.get(i).getLikes()+"");
        String nowTime = TimeUtil.getNowTime();
        String babyBirthday = mData.get(i).getBabyBirthday();
        if (babyBirthday.equals("")){
            holder.mAge.setText("他还没有添加宝宝");
        }else {
            String age = TimeUtil.getAge(babyBirthday, nowTime);
            holder.mAge.setText("宝宝" + age);
        }
        String picUri = mData.get(i).getLargePics();
        if (!picUri.equals("")) {
            String[] split = picUri.split("[|]");
            if (split.length>1) {
                for (int j = 0; j < split.length; j++) {
                    mUrl.add(split[j]);
                }
            }else {
                mUrl.add(picUri);
            }
        }
        holder.mRlvData.setUrlList(mUrl);
        holder.mTxt.setText(mData.get(i).getDetail());

        if (mData.get(i).getIsLikes()==1){
            holder.mZan.setChecked(true);
        }else {
            holder.mZan.setChecked(false);
        }

        holder.mIjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLisiterPing.onClickerPing(i,mData);
            }
        });
        holder.mZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.size() != 0) {
                    boolean checked = holder.mZan.isChecked();
                    if (checked) {
                        mData.get(i).setLikes(mData.get(i).getLikes() + 1);
                        mData.get(i).setIsLikes(1);
                        holder.mZanshu.setText(mData.get(i).getLikes() + "");
                    } else {
                        mData.get(i).setLikes(mData.get(i).getLikes() - 1);
                        mData.get(i).setIsLikes(0);
                        if (mData.get(i).getLikes() != 0) {
                            holder.mZanshu.setText(mData.get(i).getLikes() + "");
                        } else {
                            holder.mZanshu.setText("");
                        }
                    }
                }
                if (mLisiterDianZan!=null){
                    mLisiterDianZan.onClickerDianZan(v,i,holder.mZan,mData);
                }
            }
        });
        holder.mDian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLisiterDian.onClickerDian(view,i,mData);
            }
        });

        holder.mLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLisiter != null) {
                    mLisiter.onClicker(v, i, mData);
                }
            }
        });


        holder.mRlvData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String picUri = mData.get(i).getLargePics();
                if (!picUri.equals("")) {
                    String[] split = picUri.split("[|]");
                    if (split.length>1) {
                        for (int j = 0; j < split.length; j++) {
                            mUrl.add(split[j]);
                        }
                    }else {
                        mUrl.add(picUri);
                    }
                }
                App application = (App) mContext.getApplication();
                application.setUrl(mUrl);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<DiaryDetailBean.ResultBean> data, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
            this.mData.addAll(data);
            notifyDataSetChanged();
        }else {
            this.mData.addAll(data);
            notifyDataSetChanged();
        }
    }
    public void addSize(int size) {
        this.mSize=size;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date)
        TextView mDate;
        @BindView(R.id.yue)
        TextView mYue;
        @BindView(R.id.rg)
        RelativeLayout mRg;
        @BindView(R.id.age)
        TextView mAge;
        //        @BindView(R.id.rlv_data)
//        RecyclerView mRlvData;
        @BindView(R.id.rlv_data)
        NineGridTestLayout mRlvData;
        @BindView(R.id.txt)
        TextView mTxt;
        @BindView(R.id.zan)
        CheckBox mZan;
        @BindView(R.id.zanshu)
        TextView mZanshu;
        @BindView(R.id.ijia)
        ImageView mIjia;
        @BindView(R.id.pingshu)
        TextView mPingshu;
        @BindView(R.id.dian)
        ImageView mDian;
        @BindView(R.id.line)
        RelativeLayout mLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, ArrayList<DiaryDetailBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterDian {
        void onClickerDian(View view,int p,ArrayList<DiaryDetailBean.ResultBean> mData);
    }

    public void setOnClickLisiterDian(onClickLisiterDian lisiter) {
        this.mLisiterDian = lisiter;
    }

    public interface onClickLisiterDianZan {
        void onClickerDianZan(View view, int position, CheckBox mIvCollect, ArrayList<DiaryDetailBean.ResultBean> mData);
    }

    public void setOnClickLisiterDianZan(onClickLisiterDianZan lisiter) {
        this.mLisiterDianZan = lisiter;
    }

    public interface onClickLisiterPing {
        void onClickerPing(int pos,List<DiaryDetailBean.ResultBean> data);
    }

    public void setOnClickLisiterPing(onClickLisiterPing lisiter) {
        this.mLisiterPing = lisiter;
    }
}
