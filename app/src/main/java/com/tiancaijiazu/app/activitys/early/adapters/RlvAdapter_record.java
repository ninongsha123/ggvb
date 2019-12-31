package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
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
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2019/8/26/026.
 */

public class RlvAdapter_record extends RecyclerView.Adapter {
    public List<ArticleLists.ResultBean> mData;
    private Context mContext;
    private onClickLisiterComment mLisiterComment;
    public List<MyReleasedListBean.ResultBean> mData1;
    private onClickLisiterPicture mLisiterPicture;
    private onClickLisiterLike mLisiterLike;
    /**
     * 最多展示3行。
     */
    private static final int LINES = 3;

    public RlvAdapter_record(Context context, List<ArticleLists.ResultBean> resultBeans, List<MyReleasedListBean.ResultBean> resultBeans1) {
        this.mContext = context;
        this.mData = resultBeans;
        this.mData1 = resultBeans1;
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
        if (mData.size() != 0) {
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
                holder.check_like.setChecked(true);
            } else {
                holder.check_like.setChecked(false);
            }
            holder.check_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = holder.check_like.isChecked();
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
                    if (mLisiterLike!=null){
                        mLisiterLike.onClickerLike(v,i,mData,mData1);
                    }
                }
            });
        }

        if (mData1.size() != 0) {
            String picUri = mData1.get(i).getCoverPics();
            String largePics = mData1.get(i).getLargePics();
            String[] split = picUri.split("[|]");
            String[] split1 = largePics.split("[|]");
            ArrayList<String> strings = new ArrayList<>();
            ArrayList<String> strings1 = new ArrayList<>();
            for (int j = 0; j < split.length; j++) {
                strings.add(split[j]);
            }
            for (int q = 0; q < split1.length; q++) {
                strings1.add(split1[q]);
            }
            String nowTime = TimeUtil.getNowTime();
            String babyBirthday = mData1.get(i).getBabyBirthday();
            String age = TimeUtil.getAge(babyBirthday, nowTime);
            holder.mBabyAge.setText("宝宝 "+age);
            holder.mDetail.setText(mData1.get(i).getDetail());
            holder.mDiscuss.setText(mData1.get(i).getDiscuss() + "");
            holder.mLikes.setText(mData1.get(i).getLikes() + "");
            String publishTime = mData1.get(i).getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            holder.mPublishTime.setText(s);
            holder.mNickname.setText(mData1.get(i).getNickname());
            Glide.with(mContext).load(mData1.get(i).getUserAvatar()).into(holder.mUserAvatar);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            holder.mRecylerView.setLayoutManager(gridLayoutManager);
            RlvAdapter_record_two rlvAdapterRecordTwo = new RlvAdapter_record_two(strings,strings1);
            holder.mRecylerView.setAdapter(rlvAdapterRecordTwo);
            rlvAdapterRecordTwo.setOnClickLisiterImage(new RlvAdapter_record_two.onClickLisiterImage() {
                @Override
                public void onClickerImage(View view, int position, ArrayList<String> mImage1) {
                    if (mLisiterPicture != null) {
                        mLisiterPicture.onClickerPicture(view, i, mImage1);
                    }
                }
            });
            if (mData1.get(i).getIsLikes() != 0) {
                holder.check_like.setChecked(true);
            } else {
                holder.check_like.setChecked(false);
            }
            holder.check_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = holder.check_like.isChecked();
                    if(checked){
                        mData1.get(i).setLikes(mData1.get(i).getLikes()+1);
                        mData1.get(i).setIsLikes(1);
                        holder.mLikes.setText(mData1.get(i).getLikes()+ "");
                    }else {
                        mData1.get(i).setLikes(mData1.get(i).getLikes()-1);
                        mData1.get(i).setIsLikes(0);
                        if (mData1.get(i).getLikes() != 0) {
                            holder.mLikes.setText(mData1.get(i).getLikes() + "");
                        }else {
                            holder.mLikes.setText("");
                        }
                    }
                    if (mLisiterLike!=null){
                        mLisiterLike.onClickerLike(v,i,mData,mData1);
                    }
                }
            });
        }

        //int maxLines = holder.mDetail.getLayout().getLineCount();
       /* if(holder.mDetail.getLayout() == null){
            holder.mDetail.post(new Runnable() {
                @Override
                public void run() {
                    int maxLines = holder.mDetail.getLayout().getLineCount();
                    if (maxLines > 3) {
                        holder.mDetail.setMaxLines(3);
                        holder.all_text.setVisibility(View.VISIBLE);
                    } else {
                        holder.all_text.setVisibility(View.GONE);
                    }
                    holder.all_text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int maxLines1 = holder.mDetail.getMaxLines();
                            if(maxLines1 == 3){
                                holder.mDetail.setMaxLines(maxLines);
                            }else {
                                holder.mDetail.setMaxLines(3);
                            }
                        }
                    });
                }
            });
        }else {
            int maxLines = holder.mDetail.getLayout().getLineCount();
            if (maxLines > 3) {
                holder.mDetail.setMaxLines(3);
                holder.all_text.setVisibility(View.VISIBLE);
            } else {
                holder.all_text.setVisibility(View.GONE);
            }
            holder.all_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int maxLines1 = holder.mDetail.getMaxLines();
                    if(maxLines1 == 3){
                        holder.mDetail.setMaxLines(maxLines);
                    }else {
                        holder.mDetail.setMaxLines(3);
                    }
                }
            });
        }*/



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiterComment != null) {
                    mLisiterComment.onClickerComment(view, i,mData,mData1);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mData.size() != 0) {
            return mData.size();
        } else {
            return mData1.size();
        }
    }

    public void addData(List<ArticleLists.ResultBean> result, boolean b) {
        if (mData1 != null) {
            mData1.clear();
        }
        if (b) {
            if (mData != null) {
                mData.clear();
            }
            this.mData.addAll(result);
        } else {
            this.mData.addAll(result);
        }
        notifyDataSetChanged();
    }

    public void addData1(List<MyReleasedListBean.ResultBean> result1, boolean b) {
        if (mData != null) {
            mData.clear();
        }
        if (b) {
            if (mData1 != null) {
                mData1.clear();
            }
            this.mData1.addAll(result1);
        } else {
            this.mData1.addAll(result1);
        }
        notifyDataSetChanged();
    }


    public interface onClickLisiterComment {
        void onClickerComment(View view, int position,List<ArticleLists.ResultBean> mData,List<MyReleasedListBean.ResultBean> mData1);
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

    public interface onClickLisiterLike {
        void onClickerLike(View view, int position,List<ArticleLists.ResultBean> mData,List<MyReleasedListBean.ResultBean> mData1);
    }

    public void setOnClickLisiterLike(onClickLisiterLike lisiterComment) {
        this.mLisiterLike = lisiterComment;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.userAvatar)
        CircleImageView mUserAvatar;
        @BindView(R.id.nickname)
        TextView mNickname;
        @BindView(R.id.all_text)
        TextView all_text;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;
        @BindView(R.id.detail)
        TextView mDetail;
        @BindView(R.id.publishTime)
        TextView mPublishTime;
        @BindView(R.id.likes)
        TextView mLikes;
        @BindView(R.id.discuss)
        TextView mDiscuss;
        @BindView(R.id.baby_age)
        TextView mBabyAge;
        @BindView(R.id.check_like)
        CheckBox check_like;
        @BindView(R.id.comment_line)
        LinearLayout mCommentLine;
        @BindView(R.id.study_title)
        TextView mStudyTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
