package com.tiancaijiazu.app.activitys.take.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_take_record;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;
import com.tiancaijiazu.app.utils.views.JZVideoPlayer;
import com.tiancaijiazu.app.utils.views.MyJZVideoPlayerStandard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_record_study extends RecyclerView.Adapter {


    private List<ArticleLists.ResultBean> mDataRec;
    private List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList;
    private TakeAClassActivity context;
    private ArrayList<Integer> mImages;
    public ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData;
    private int lastH;
    private int mIsGame;
    private onClickLisiterRecord mLisiterRecord;
    private onClickLisiterSubmit mLisiterSubmit;
    private onClickLisiterGameOne mLisiterGameOne;
    private onClickLisiterGameTwo mLisiterGameTwo;
    private onClickLisiterGameThree mLisiterGameThree;
    private boolean isbo;
    private onClickLisiterVideo mLisiterVideo;
    private onClickLisiterPicture mLisiterPicture;
    public RlvAdapter_take_record mRlvAdapterTakeRecord;

    public RlvAdapter_record_study(ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> contentsListBean, TakeAClassActivity takeAClassActivity, ArrayList<Integer> integers, int lastH, int isGame, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> gameList, List<ArticleLists.ResultBean> resultBeans) {
        this.context = takeAClassActivity;
        this.mData = contentsListBean;
        this.mImages = integers;
        this.lastH = lastH;
        this.mIsGame = isGame;
        this.mGameList = gameList;
        this.mDataRec = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_record_study, viewGroup, false);
            return new ViewHolder(inflate);
        } else if (i == 1) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_record_study_all, viewGroup, false);
            return new ViewHolderAll(inflate);
        } else if (i == 2) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_diversification_layout, viewGroup, false);
            return new ViewHolderMore(inflate);
        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.item_course_record_layout, viewGroup, false);
            return new ViewHolderRecordList(inflate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int viewType = getItemViewType(i);
        if (viewType == 0) {
            ViewHolder holder = (ViewHolder) viewHolder;
            if (i == mData.size() - 1) {
                if (holder.mLinear.getHeight() < lastH) {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.height = lastH;
                    holder.mLinear.setLayoutParams(params);
                }
            }
            holder.mTextStudy.setText(mData.get(i).getTitle());
            holder.mRela.setBackgroundResource(mImages.get(i));
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            holder.mRecyclerView.setLayoutManager(linearLayoutManager);
            String summary = mData.get(i).getSummary();
            String[] split = summary.split(",");
            int a = split.length;
            ArrayList<String> strings = new ArrayList<>();
            for (int j = 0; j < a; j++) {
                strings.add(split[j]);
            }
            RlvAdapter_partList_more rlvAdapter_partList_more = new RlvAdapter_partList_more(strings);
            holder.mRecyclerView.setAdapter(rlvAdapter_partList_more);
        } else if (viewType == 1) {
            ViewHolderAll holderAll = (ViewHolderAll) viewHolder;
            holderAll.mTextStudy.setText(mData.get(i).getTitle());
            holderAll.mRela1.setBackgroundResource(mImages.get(i));
            String detail = mData.get(i).getDetail();
            String pics = mData.get(i).getPics();
            String videoUri = mData.get(i).getVideoUri();
            if (!"".equals(detail) && "".equals(pics) && "".equals(videoUri)) {
                holderAll.mFrame.setVisibility(View.GONE);
                holderAll.mRecyler.setVisibility(View.GONE);
                String replace = detail.replace("\r\n", "");
                String[] split = replace.split("<hr>");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holderAll.mRecylerView.setLayoutManager(linearLayoutManager);
                RlvAdapter_Str_Paragraph rlvAdapterStrParagraph = new RlvAdapter_Str_Paragraph(split);
                holderAll.mRecylerView.setAdapter(rlvAdapterStrParagraph);
            } else if ("".equals(detail) && !"".equals(pics) && "".equals(videoUri)) {
                holderAll.mFrame.setVisibility(View.GONE);
                holderAll.mRecylerView.setVisibility(View.GONE);
                String[] split = pics.split("[|]");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holderAll.mRecyler.setLayoutManager(linearLayoutManager);
                RlvAdapter_Pics rlvAdapterPics = new RlvAdapter_Pics(split, context);
                holderAll.mRecyler.setAdapter(rlvAdapterPics);
            } else if ("".equals(detail) && "".equals(pics) && !"".equals(videoUri)) {
                holderAll.mRecylerView.setVisibility(View.GONE);
                holderAll.mRecyler.setVisibility(View.GONE);
                holderAll.mJiaoziPlayer.setUp(mData.get(i).getVideoUri(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                Glide.with(context).load(mData.get(i).getVideoCover()).into(holderAll.mJiaoziPlayer.thumbImageView);
                holderAll.mIvPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holderAll.mRela.setVisibility(View.GONE);
                        holderAll.mJiaoziPlayer.startVideo();
                        if (mLisiterVideo != null) {
                            mLisiterVideo.onClickerVideo(view, mData.get(i).getVideoUri(), mData.get(i).getTitle());
                        }
                    }
                });
                holderAll.mRela.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                holderAll.mRela.setVisibility(View.VISIBLE);
            } else if (!"".equals(detail) && !"".equals(pics) && "".equals(videoUri)) {
                holderAll.mFrame.setVisibility(View.GONE);
                String replace = detail.replace("\r\n", "");
                String[] split = replace.split("<hr>");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holderAll.mRecylerView.setLayoutManager(linearLayoutManager);
                RlvAdapter_Str_Paragraph rlvAdapterStrParagraph = new RlvAdapter_Str_Paragraph(split);
                holderAll.mRecylerView.setAdapter(rlvAdapterStrParagraph);
                String[] split1 = pics.split("[|]");
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(context);
                holderAll.mRecyler.setLayoutManager(linearLayoutManager1);
                RlvAdapter_Pics rlvAdapterPics = new RlvAdapter_Pics(split1, context);
                holderAll.mRecyler.setAdapter(rlvAdapterPics);
            } else if (!"".equals(detail) && "".equals(pics) && !"".equals(videoUri)) {
                holderAll.mRecyler.setVisibility(View.GONE);
                String replace = detail.replace("\r\n", "");
                String[] split = replace.split("<hr>");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holderAll.mRecylerView.setLayoutManager(linearLayoutManager);
                RlvAdapter_Str_Paragraph rlvAdapterStrParagraph = new RlvAdapter_Str_Paragraph(split);
                holderAll.mRecylerView.setAdapter(rlvAdapterStrParagraph);
                holderAll.mJiaoziPlayer.setUp(mData.get(i).getVideoUri(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                Glide.with(context).load(mData.get(i).getVideoCover()).into(holderAll.mJiaoziPlayer.thumbImageView);
                holderAll.mIvPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holderAll.mRela.setVisibility(View.GONE);
                        holderAll.mJiaoziPlayer.startVideo();
                        if (mLisiterVideo != null) {
                            mLisiterVideo.onClickerVideo(view, mData.get(i).getVideoUri(), mData.get(i).getTitle());
                        }
                    }
                });
                holderAll.mRela.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                holderAll.mRela.setVisibility(View.VISIBLE);
            } else if ("".equals(detail) && !"".equals(pics) && !"".equals(videoUri)) {
                holderAll.mRecylerView.setVisibility(View.GONE);
                String[] split = pics.split("[|]");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                holderAll.mRecyler.setLayoutManager(linearLayoutManager);
                RlvAdapter_Pics rlvAdapterPics = new RlvAdapter_Pics(split, context);
                holderAll.mRecyler.setAdapter(rlvAdapterPics);
                holderAll.mJiaoziPlayer.setUp(mData.get(i).getVideoUri(), JZVideoPlayer.SCREEN_WINDOW_NORMAL, "");
                Glide.with(context).load(mData.get(i).getVideoCover()).into(holderAll.mJiaoziPlayer.thumbImageView);
                holderAll.mIvPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holderAll.mRela.setVisibility(View.GONE);
                        holderAll.mJiaoziPlayer.startVideo();
                        if (mLisiterVideo != null) {
                            mLisiterVideo.onClickerVideo(view, mData.get(i).getVideoUri(), mData.get(i).getTitle());
                        }
                    }
                });
                holderAll.mRela.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                holderAll.mRela.setVisibility(View.VISIBLE);
            }

        } else if (viewType == 2) {
            ViewHolderMore viewHolderMore = (ViewHolderMore) viewHolder;
            if (mIsGame == 0) {
                if (mGameList.size() != 0) {
                    viewHolderMore.mSubmitFeedback.setVisibility(View.GONE);
                    viewHolderMore.mLine.setVisibility(View.VISIBLE);
                    Log.i("yx156", "addKui: ");
                    Log.i("yx145", "onBindViewHolder: " + mGameList.get(0).getTitle());
                    viewHolderMore.mGameNameOne.setText(mGameList.get(0).getTitle());
                    viewHolderMore.mGameNameTwo.setText(mGameList.get(1).getTitle());
                    viewHolderMore.mGameNameThree.setText(mGameList.get(1).getTitle());
                    Glide.with(context).load(mGameList.get(0).getPicUri()).into(viewHolderMore.mIvOne);
                    Glide.with(context).load(mGameList.get(1).getPicUri()).into(viewHolderMore.mIvTwo);
                    Glide.with(context).load(mGameList.get(1).getPicUri()).into(viewHolderMore.mIvThree);
                } else {
                    if (isbo) {

                    } else {
                        viewHolderMore.mSubmitFeedback.setVisibility(View.VISIBLE);
                        viewHolderMore.mSubmitFeedback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mLisiterSubmit != null) {
                                    mLisiterSubmit.onClickerSubmit(view, i);
                                }
                            }
                        });
                    }
                }
                viewHolderMore.mLineOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mLisiterGameOne != null) {
                            mLisiterGameOne.onClickerGameOne(view, i, mData, mGameList);
                        }
                    }
                });

                viewHolderMore.mLineTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mLisiterGameTwo != null) {
                            mLisiterGameTwo.onClickerGameTwo(view, i, mData, mGameList);
                        }
                    }
                });
                viewHolderMore.mLineThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mLisiterGameThree != null) {
                            mLisiterGameThree.onClickerGameThree(view, i, mData, mGameList);
                        }
                    }
                });
            } else if (mIsGame == 1) {
                viewHolderMore.mSubmitFeedback.setVisibility(View.GONE);
            }
        } else {
            ViewHolderRecordList holderRecordList = (ViewHolderRecordList) viewHolder;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            holderRecordList.mRecylerView.setLayoutManager(linearLayoutManager);
            mRlvAdapterTakeRecord = new RlvAdapter_take_record(mDataRec, context);
            holderRecordList.mRecylerView.setAdapter(mRlvAdapterTakeRecord);
            mRlvAdapterTakeRecord.setOnClickLisiterPicture(new RlvAdapter_take_record.onClickLisiterPicture() {
                @Override
                public void onClickerPicture(View view, int position, ArrayList<String> strings1) {
                    if (mLisiterPicture != null) {
                        mLisiterPicture.onClickerPicture(view, position, strings1);
                    }
                }
            });
            mRlvAdapterTakeRecord.setOnClickLisiterComment(new RlvAdapter_take_record.onClickLisiterComment() {
                @Override
                public void onClickerComment(View view, int position, List<ArticleLists.ResultBean> data) {
                    if (mLisiterRecord != null) {
                        mLisiterRecord.onClickerRecord(view, position, data);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 2) {
            return 0;
        } else if (position >= 2 && position < mData.size()) {
            return 1;
        } else if (position == mData.size()) {
            return 2;
        } else {
            return 3;
        }
    }

    public void addUp() {
        notifyDataSetChanged();
    }

    public void addKui(List<SubmitBean.ResultBean> result) {
        for (int i = 0; i < result.size(); i++) {
            mGameList.add(new FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean(result.get(i).getTitle(),
                    result.get(i).getSummary(), result.get(i).getPicUri(), result.get(i).getUrl()));
        }
        Log.i("yx156", "addKui: ");
        notifyItemChanged(mData.size());
    }

    public void addData(List<ArticleLists.ResultBean> result1, boolean b) {
        if (b) {
            if (mDataRec != null) {
                mDataRec.clear();
            }
        }

        this.mDataRec.addAll(result1);
        notifyItemChanged(3);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text_study)
        TextView mTextStudy;
        @BindView(R.id.rela)
        RelativeLayout mRela;
        @BindView(R.id.recylerView)
        RecyclerView mRecyclerView;
        @BindView(R.id.linear)
        LinearLayout mLinear;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderAll extends RecyclerView.ViewHolder {
        @BindView(R.id.jiaozi_player)
        MyJZVideoPlayerStandard mJiaoziPlayer;
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;
        @BindView(R.id.recyler)
        RecyclerView mRecyler;
        @BindView(R.id.text_study)
        TextView mTextStudy;
        @BindView(R.id.line)
        LinearLayout mLine;
        @BindView(R.id.iv_play)
        ImageView mIvPlay;
        @BindView(R.id.frame)
        FrameLayout mFrame;
        @BindView(R.id.rela)
        RelativeLayout mRela;
        @BindView(R.id.rela1)
        RelativeLayout mRela1;

        ViewHolderAll(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderMore extends RecyclerView.ViewHolder {
        @BindView(R.id.submit_feedback)
        ImageView mSubmitFeedback;
        @BindView(R.id.iv_one)
        ImageView mIvOne;
        @BindView(R.id.game_name_one)
        TextView mGameNameOne;
        @BindView(R.id.line_one)
        LinearLayout mLineOne;
        @BindView(R.id.iv_two)
        ImageView mIvTwo;
        @BindView(R.id.game_name_two)
        TextView mGameNameTwo;
        @BindView(R.id.line_two)
        LinearLayout mLineTwo;
        @BindView(R.id.iv_three)
        ImageView mIvThree;
        @BindView(R.id.game_name_three)
        TextView mGameNameThree;
        @BindView(R.id.line_three)
        LinearLayout mLineThree;
        @BindView(R.id.line)
        LinearLayout mLine;

        ViewHolderMore(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolderRecordList extends RecyclerView.ViewHolder {
        /*@BindView(R.id.note_taking)
        ImageView mNoteTaking;*/
        @BindView(R.id.recylerView)
        RecyclerView mRecylerView;

        ViewHolderRecordList(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiterRecord {
        void onClickerRecord(View view, int position, List<ArticleLists.ResultBean> mDataRec);
    }

    public void setOnClickLisiterRecord(onClickLisiterRecord lisiterRecord) {
        this.mLisiterRecord = lisiterRecord;
    }

    public interface onClickLisiterSubmit {
        void onClickerSubmit(View view, int position);
    }

    public void setOnClickLisiterSubmit(onClickLisiterSubmit lisiterSubmit) {
        this.mLisiterSubmit = lisiterSubmit;
    }

    public interface onClickLisiterGameOne {
        void onClickerGameOne(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList);
    }

    public void setOnClickLisiterGameOne(onClickLisiterGameOne lisiterGameOne) {
        this.mLisiterGameOne = lisiterGameOne;
    }

    public interface onClickLisiterGameTwo {
        void onClickerGameTwo(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList);
    }

    public void setOnClickLisiterGameTwo(onClickLisiterGameTwo lisiterGameTwo) {
        this.mLisiterGameTwo = lisiterGameTwo;
    }

    public interface onClickLisiterGameThree {
        void onClickerGameThree(View view, int position, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.PartListBean> mData, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> mGameList);
    }

    public void setOnClickLisiterGameThree(onClickLisiterGameThree lisiterGameThree) {
        this.mLisiterGameThree = lisiterGameThree;
    }

    public interface onClickLisiterVideo {
        void onClickerVideo(View view, String video, String title);
    }

    public void setOnClickLisiterVideo(onClickLisiterVideo lisiterVideo) {
        this.mLisiterVideo = lisiterVideo;
    }

    public interface onClickLisiterPicture {
        void onClickerPicture(View view, int position, ArrayList<String> strings1);
    }

    public void setOnClickLisiterPicture(onClickLisiterPicture lisiterComment) {
        this.mLisiterPicture = lisiterComment;
    }
}
