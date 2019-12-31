package com.tiancaijiazu.app.activitys.early.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RlvAdapter_take_record extends RecyclerView.Adapter {
    public List<ArticleLists.ResultBean> mData;
    private TakeAClassActivity mContext;
    private onClickLisiterComment mLisiterComment;
    private onClickLisiterPicture mLisiterPicture;

    public RlvAdapter_take_record(List<ArticleLists.ResultBean> dataRec, TakeAClassActivity context) {
        this.mData = dataRec;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_record_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String picUri = mData.get(i).getCoverPics();
        String largePics = mData.get(i).getLargePics();
        String nowTime = TimeUtil.getNowTime();
        String babyBirthday = mData.get(i).getBabyBirthday();
        String age = TimeUtil.getAge(babyBirthday, nowTime);
        holder.mBabyAge.setText("宝宝 "+age);
        String study_contents_title = mData.get(i).getStudy_contents_title();
        holder.mStudyTitle.setText(study_contents_title);
        ArrayList<String> strings1 = new ArrayList<>();
        String[] split1 = largePics.split("[|]");
        int size = split1.length;
        for (int j = 0; j < size; j++) {
            strings1.add(split1[j]);
        }
        String[] split = picUri.split("[|]");
        ArrayList<String> strings = new ArrayList<>();
        int size1 = split.length;
        for (int j = 0; j < size1; j++) {
            strings.add(split[j]);
        }
        holder.mDetail.setText(mData.get(i).getDetail());
        holder.mDiscuss.setText(mData.get(i).getDiscuss() + "");
        holder.mLikes.setText(mData.get(i).getLikes() + "");
        String publishTime = mData.get(i).getPublishTime();
        long l = TimeUtil.dataOne(publishTime);
        String s = TimeUtil.QQFormatTime(l);
        holder.mPublishTime.setText(s);
        holder.mNickname.setText(mData.get(i).getNickname());
        Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mUserAvatar);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        holder.mRecylerView.setLayoutManager(gridLayoutManager);
        RlvAdapter_record_two rlvAdapterRecordTwo = new RlvAdapter_record_two(strings,strings1);
        holder.mRecylerView.setAdapter(rlvAdapterRecordTwo);
        rlvAdapterRecordTwo.setOnClickLisiterImage(new RlvAdapter_record_two.onClickLisiterImage() {
            @Override
            public void onClickerImage(View view, int position, ArrayList<String> mImage1) {
                if (mLisiterPicture != null) {
                    mLisiterPicture.onClickerPicture(view, position, mImage1);
                }
            }
        });

        if (mData.get(i).getIsLikes() != 0) {
            holder.mCheckLike.setChecked(true);
        } else {
            holder.mCheckLike.setChecked(false);
        }
        holder.mCheckLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.mCheckLike.isChecked();
                if(checked){
                    mData.get(i).setLikes(mData.get(i).getLikes()+1);
                    mData.get(i).setIsLikes(1);
                    holder.mLikes.setText(mData.get(i).getLikes()+ "");
                }else {
                    mData.get(i).setLikes(mData.get(i).getLikes()-1);
                    mData.get(i).setIsLikes(0);
                    if (mData.get(i).getLikes() != 0) {
                        holder.mLikes.setText(mData.get(i).getLikes() + "");
                    }else {
                        holder.mLikes.setText("");
                    }
                }
                /*if (mLisiterLike!=null){
                    mLisiterLike.onClickerLike(v,i,mData,mData1);
                }*/
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterComment!=null){
                    mLisiterComment.onClickerComment(view,i,mData);
                }
            }
        });
    }
    public interface onClickLisiterComment {
        void onClickerComment(View view, int position,List<ArticleLists.ResultBean> data);
    }

    public void setOnClickLisiterComment(onClickLisiterComment lisiterComment) {
        this.mLisiterComment = lisiterComment;
    }

    public interface onClickLisiterPicture {
        void onClickerPicture(View view, int position, ArrayList<String> strings1);
    }

    public void setOnClickLisiterPicture(onClickLisiterPicture lisiterComment) {
        this.mLisiterPicture = lisiterComment;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.userAvatar)
        CircleImageView mUserAvatar;
        @BindView(R.id.nickname)
        MediumBoldTextViewStandard mNickname;
        @BindView(R.id.baby_age)
        TextView mBabyAge;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;
        @BindView(R.id.detail)
        MediumBoldTextViewStandard mDetail;
        @BindView(R.id.all_text)
        TextView mAllText;
        @BindView(R.id.publishTime)
        TextView mPublishTime;
        @BindView(R.id.check_like)
        CheckBox mCheckLike;
        @BindView(R.id.likes)
        TextView mLikes;
        @BindView(R.id.discuss)
        TextView mDiscuss;
        @BindView(R.id.study_title)
        TextView mStudyTitle;
        @BindView(R.id.comment_line)
        LinearLayout mCommentLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
