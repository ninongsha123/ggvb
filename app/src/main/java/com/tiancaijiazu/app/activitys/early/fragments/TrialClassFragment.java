package com.tiancaijiazu.app.activitys.early.fragments;


import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Course_List;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Pop_Custom;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.CardTypeBean;
import com.tiancaijiazu.app.beans.EarlyCourseBean;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 *
 * 试听课
 */
public class TrialClassFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.ka_name)
    TextView mKaName;
    @BindView(R.id.birth_mess)
    LinearLayout mBirthMess;
    @BindView(R.id.title)
    TextView mTitle;
    /*@BindView(R.id.expandable)
    ExpandableListView mExpandable;*/
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.card_name)
    TextView mCardName;
    @BindView(R.id.try_listener)
    RelativeLayout try_listener;
    /*    @BindView(R.id.line)
        LinearLayout mLine;*/
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.theme_name)
    TextView mThemeName;
    @BindView(R.id.theme_data)
    TextView mThemeData;
    @BindView(R.id.iv_get)
    ImageView mIvGet;
    @BindView(R.id.birth)
    TextView mBirth;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.baby_age)
    TextView mBabyAge;
    private RlvAdapter_Course_List mRlvAdapterCourseList;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private RlvAdapter_Pop_Custom mRlvAdapterPopCustom;
    private int mPosition;
    private int mWai;
    private String mAbility;
    private TextView mText;
    private String mContentsId;

    public TrialClassFragment() {
        // Required empty public constructor
    }

    public static TrialClassFragment getInstance() {
        return new TrialClassFragment();
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_trial_class;
    }

    @Override
    protected void initData() {
        initRecylerView();
        initPop();
        mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.CARDTYPEBEAN,loadingLayout);
        mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.USERCARDTYPE,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.CARDTYPEBEAN,loadingLayout);
                mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM,loadingLayout);
            }
        });
    }

    private void initPop() {
        mInflate = LayoutInflater.from(getContext()).inflate(R.layout.pop_game_select, null);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        RecyclerView recylerView = mInflate.findViewById(R.id.recylerView);
        TextView tv = mInflate.findViewById(R.id.tv);
        mText = mInflate.findViewById(R.id.text);
        ImageView submit = mInflate.findViewById(R.id.submit);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recylerView.setLayoutManager(linearLayoutManager);
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans = new ArrayList<>();
        mRlvAdapterPopCustom = new RlvAdapter_Pop_Custom(feedbackListBeans, getContext());
        recylerView.setAdapter(mRlvAdapterPopCustom);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mRlvAdapterCourseList.addSubmit(mWai, mPosition, result1);
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans = mRlvAdapterPopCustom.mData;
                String feedbackIds = "";
                for (int i = 0; i < feedbackListBeans.size(); i++) {
                    if (feedbackListBeans.get(i).isIsbo()) {
                        feedbackIds += feedbackListBeans.get(i).getFeedbackId() + ",";
                    }
                }
                HashMap<String, String> map = new HashMap<>();
                map.put("contentsId", mContentsId);
                map.put("feedbackIds", feedbackIds);
                Log.i("yx156", mContentsId + "onClick: " + feedbackIds);
                mPresenter.getDataP1(map, DifferentiateEnum.SUBMIT,loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map, DifferentiateEnum.SUBMIT,loadingLayout);
                    }
                });
                //mRlvAdapterCourseList.addSubmit(mWai, mPosition);
                mRlvAdapterPopCustom.mData.clear();
                mPopupWindow.dismiss();
            }
        });
    }
    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> chapterListBeans = new ArrayList<>();
        mRlvAdapterCourseList = new RlvAdapter_Course_List(chapterListBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapterCourseList);

        mRlvAdapterCourseList.setOnClickLisiterCustom(new RlvAdapter_Course_List.onClickLisiterCustom() {
            @Override
            public void onClickerCustom(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, int wai) {
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackList = mData.get(position).getFeedbackList();
                mAbility = mData.get(0).getAbility();
                mContentsId = mData.get(mData.size() - 1).getContentsId() + "";
                String replace = mAbility.replace(",", " ");
                String[] split = mAbility.split(",");
                String textStr = "今天的课程主要锻炼宝宝 <font color='#00DEFF'>" + replace + "</font> " + split.length + "项能力，请反馈宝宝的上课情况，为宝宝定制最合适的扩展游戏";
                mText.setText(Html.fromHtml(textStr));
                mPosition = position;
                mWai = wai;
                int size = feedbackList.size();
                for (int j = 0; j < size; j++) {
                    feedbackList.get(j).setIsbo(false);
                }
                mRlvAdapterPopCustom.addData(feedbackList);
                mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
            }
        });

        mRlvAdapterCourseList.setOnClickLisiterCourse(new RlvAdapter_Course_List.onClickLisiterCourse() {
            @Override
            public void onClickerCourse(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> data, int wai, ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> mData) {
                Intent intent = new Intent(getContext(), TakeAClassActivity.class);
                FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean = data.get(position);
                String title = mData.get(wai).getTitle();
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackList = data.get(data.size() - 1).getFeedbackList();
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.GameListBean> gameList = data.get(data.size() - 1).getGameList();
                mContentsId = data.get(data.size() - 1).getContentsId() + "";
                intent.putExtra("data", contentsListBean);
                intent.putExtra("title", title);
                intent.putExtra("game", (Serializable) feedbackList);
                intent.putExtra("contentsId", mContentsId);
                intent.putExtra("game1", (Serializable) gameList);
                startActivity(intent);
            }
        });

        mRlvAdapterCourseList.setOnClickLisiterGameOne(new RlvAdapter_Course_List.onClickLisiterGameOne() {
            @Override
            public void onClickerGameOne(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, List<SubmitBean.ResultBean> mDataGame) {

                if (mDataGame.size() != 0) {
                    String url = mDataGame.get(0).getUrl();
                    String title = mDataGame.get(0).getTitle();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", url);
                    intent.putExtra("title", title);
                    intent.putExtra("biao","home");
                    startActivity(intent);
                } else {
                    if (mData.size() != 0) {
                        String url = mData.get(position).getGameList().get(0).getUrl();
                        String title = mData.get(position).getGameList().get(0).getTitle();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", url);
                        intent.putExtra("title", title);
                        intent.putExtra("biao","home");
                        startActivity(intent);
                    }
                }
            }
        });

        mRlvAdapterCourseList.setOnClickLisiterGameTwo(new RlvAdapter_Course_List.onClickLisiterGameTwo() {
            @Override
            public void onClickerGameTwo(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, List<SubmitBean.ResultBean> mDataGame) {

                if (mDataGame.size() != 0) {
                    String url = mDataGame.get(1).getUrl();
                    String title = mDataGame.get(1).getTitle();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("target", url);
                    intent.putExtra("title", title);
                    intent.putExtra("biao","home");
                    startActivity(intent);
                } else {
                    if (mData.size() != 0) {
                        String url = mData.get(position).getGameList().get(1).getUrl();
                        String title = mData.get(position).getGameList().get(1).getTitle();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("biao","home");
                        intent.putExtra("target", url);
                        intent.putExtra("title", title);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case EARLYCOURSELIST:
                EarlyCourseListBean earlyCourseListBean = (EarlyCourseListBean) o;
                List<EarlyCourseListBean.ResultBean> result = earlyCourseListBean.getResult();
                long courseId = result.get(0).getCourseId();
                Log.i("yx111", "show: " + courseId);
                mPresenter.getDataP1(courseId + "", DifferentiateEnum.EARLYCOURSE,loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(courseId + "", DifferentiateEnum.EARLYCOURSE,loadingLayout);
                    }
                });
                break;
            case EARLYCOURSE:
                EarlyCourseBean earlyCourseBean = (EarlyCourseBean) o;
                EarlyCourseBean.ResultBean result1 = earlyCourseBean.getResult();
                List<EarlyCourseBean.ResultBean.ChapterListBean> chapterList = result1.getChapterList();
                //initView(chapterList);
                break;
            case FORMALCURRICULUM:
                FormalCurriculumBean formalCurriculumBean = (FormalCurriculumBean) o;
                FormalCurriculumBean.ResultBean result2 = formalCurriculumBean.getResult();
                mBabyAge.setText(result2.getCourseInfo().getMonthMin() + "-" + result2.getCourseInfo().getMonthMax() + "月龄");
                List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1 = result2.getChapterList();
                    mThemeName.setText(result2.getCourseInfo().getTitle());
                    mThemeData.setText(result2.getCourseInfo().getSummary());
                mRlvAdapterCourseList.addData(chapterList1);
                break;
            case CARDTYPEBEAN:
                CardTypeBean cardTypeBean = (CardTypeBean) o;
                List<CardTypeBean.ResultBean> result3 = cardTypeBean.getResult();
                for (int i = 0; i < result3.size(); i++) {
                    if (result3.get(i).getCardType() == 0) {
                        mKaName.setText(result3.get(i).getTitle());
                        mCardName.setText(result3.get(i).getName());
                        mSummary.setText(result3.get(i).getSummary()+"\n"+result3.get(i).getSlogan());
                        break;
                    }
                }
                break;
            case SUBMIT:
                SubmitBean submitBean = (SubmitBean) o;
                List<SubmitBean.ResultBean> result4 = submitBean.getResult();
                mRlvAdapterCourseList.addSubmit(mWai, mPosition, result4);
                break;
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                mBirth.setText(userCardTypeBean.getResult().getBabyBirthday());

                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


}
