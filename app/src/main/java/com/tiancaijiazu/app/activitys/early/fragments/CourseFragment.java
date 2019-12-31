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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.WebActivity;
import com.tiancaijiazu.app.activitys.diy.RenewActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Course_List;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Pop_Custom;
import com.tiancaijiazu.app.activitys.past.PastCourseActivity;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.beans.SubmitBean;
import com.tiancaijiazu.app.beans.UserCardTypeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewStandard;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.renewal)
    RelativeLayout mRenewal;
    @BindView(R.id.baby_birth)
    TextView mBabyBirth;
    @BindView(R.id.birth)
    TextView mBirth;
    @BindView(R.id.curriculum)
    TextView mCurriculum;
    @BindView(R.id.bottom_progress)
    ProgressBar mBottomProgress;
    @BindView(R.id.degree)
    TextView mDegree;
    @BindView(R.id.rela)
    LinearLayout mRela;
    @BindView(R.id.bell_line)
    LinearLayout mBellLine;
    @BindView(R.id.line3)
    LinearLayout mLine3;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.year_age)
    TextView mYearAge;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.summary)
    TextView mSummary;
    Unbinder unbinder;
    @BindView(R.id.week_title)
    MediumBoldTextViewStandard mWeekTitle;
    @BindView(R.id.week_data)
    TextView mWeekData;
    Unbinder unbinder1;
    private RlvAdapter_Course_List mRlvAdapterCourseList;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private RlvAdapter_Pop_Custom mRlvAdapterPopCustom;
    private int mPosition;
    private int mWai;
    private TextView mText;
    private FormalCurriculumBean.ResultBean.CourseInfoBean courseInfo;
    private String expiresIn;
    private String babyBirthday;
    private int cardType;
    private String mContentsId;

    public CourseFragment() {
        // Required empty public constructor
    }

    public static CourseFragment getInstance() {
        return new CourseFragment();
    }

    @Override
    public void onStart() {
        String cards = PreUtils.getString("cards", "");
        if ("ok".equals(cards)) {
            Log.i("asdf", "onStart: ---------------");
            initData();
        }
        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 12) {
            initData();
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_course;
    }

    @Override
    protected void initData() {

        initRecylerView();
        initPop();
        mPresenter.getDataP("", DifferentiateEnum.FORMALCURRICULUM);
        mPresenter.getDataP("", DifferentiateEnum.USERCARDTYPE);

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
                String ability = mData.get(0).getAbility();
                mContentsId = mData.get(mData.size() - 1).getContentsId() + "";
                Log.i("yx156", "onClickerCustom: " + mContentsId);
                String replace = ability.replace(",", " ");
                String[] split = ability.split(",");
                String textStr = "今天的课程主要锻炼宝宝 <font color='#00DEFF'>" + replace + "</font> " + split.length + "项能力，请反馈宝宝的上课情况，为宝宝定制最合适的扩展游戏";
                mText.setText(Html.fromHtml(textStr));
                mPosition = position;
                mWai = wai;
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
                Log.i("yx156", "onClickerCourse: " + mContentsId);
                intent.putExtra("data", contentsListBean);
                intent.putExtra("title", title);
                intent.putExtra("game", (Serializable) feedbackList);
                intent.putExtra("contentsId", mContentsId);
                intent.putExtra("game1", (Serializable) gameList);
                startActivityForResult(intent, 11);
            }
        });


        mRlvAdapterCourseList.setOnClickLisiterGameOne(new RlvAdapter_Course_List.onClickLisiterGameOne() {
            @Override
            public void onClickerGameOne(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, List<SubmitBean.ResultBean> mDataGame) {

                if (mDataGame.size() != 0) {
                    String url = mDataGame.get(0).getUrl();
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", mDataGame.get(0).getTitle());
                    intent.putExtra("target", url);
                    intent.putExtra("biao", "home");
                    startActivity(intent);
                } else {
                    if (mData.size() != 0) {
                        String url = mData.get(position).getGameList().get(0).getUrl();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("title", mData.get(position).getGameList().get(0).getTitle());
                        intent.putExtra("target", url);
                        intent.putExtra("biao", "home");
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
                    Intent intent = new Intent(getContext(), WebActivity.class);
                    intent.putExtra("title", mDataGame.get(1).getTitle());
                    intent.putExtra("target", url);
                    intent.putExtra("biao", "home");
                    startActivity(intent);
                } else {
                    if (mData.size() != 0) {
                        String url = mData.get(position).getGameList().get(1).getUrl();
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra("target", url);
                        intent.putExtra("title", mData.get(position).getGameList().get(1).getTitle());
                        intent.putExtra("biao", "home");
                        startActivity(intent);
                    }
                }
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
        ImageView submit = mInflate.findViewById(R.id.submit);
        mText = mInflate.findViewById(R.id.text);
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
                mPresenter.getDataP(map, DifferentiateEnum.SUBMIT);
                //mRlvAdapterCourseList.addSubmit(mWai, mPosition);
                mRlvAdapterPopCustom.mData.clear();
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case FORMALCURRICULUM:
                FormalCurriculumBean formalCurriculumBean = (FormalCurriculumBean) o;
                FormalCurriculumBean.ResultBean result2 = formalCurriculumBean.getResult();
                courseInfo = result2.getCourseInfo();
                List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1 = result2.getChapterList();
                mRlvAdapterCourseList.addData(chapterList1);
                mYearAge.setText(result2.getCourseInfo().getMonthMin() + "-" + result2.getCourseInfo().getMonthMax() + "月龄");
                mWeekTitle.setText(result2.getCourseInfo().getTitle());
                mWeekData.setText(result2.getCourseInfo().getSummary());
                break;
            case USERCARDTYPE:
                UserCardTypeBean userCardTypeBean = (UserCardTypeBean) o;
                UserCardTypeBean.ResultBean result = userCardTypeBean.getResult();
                babyBirthday = result.getBabyBirthday();
                int currentWeek = result.getCurrentWeek();
                int weeks = result.getWeeks();
                String name = result.getName();
                mTitle.setText(name);
                mBirth.setText(babyBirthday);
                expiresIn = result.getExpiresIn();
                mDegree.setText("已推送 " + currentWeek + " 周" + "/" + "共 " + weeks + " 周");
                float i = (float) currentWeek / weeks * 100;
                mBottomProgress.setProgress((int) i);
                cardType = result.getCardType();
                mSummary.setText(result.getSummary());
                if (cardType == 1) {
                    mRela.setBackgroundResource(R.mipmap.wellcard);

                } else if (cardType == 2) {
                    mRela.setBackgroundResource(R.mipmap.annual_card);

                } else if (cardType == 3) {
                    mRela.setBackgroundResource(R.mipmap.half_a_year_card);

                }
                int week = TimeUtil.getWeek();
                String nowTime = TimeUtil.getNowTime();
                long l = TimeUtil.dataOne(nowTime);
                Date date = new Date();
                date.setTime(l);
                Date nextDay = TimeUtil.getNextDay(date, (8 - week));
                String dateStr = TimeUtil.getDateStr(nextDay);
                boolean b = TimeUtil.compareTwoTime(nowTime, result.getExpiresIn());
                if (b) {
                    mTime.setText(dateStr + "推送下周课程");
                    mBellLine.setVisibility(View.VISIBLE);
                } else {
                    mBellLine.setVisibility(View.GONE);
                }
                break;
            case SUBMIT:
                SubmitBean submitBean = (SubmitBean) o;
                List<SubmitBean.ResultBean> result1 = submitBean.getResult();
                mRlvAdapterCourseList.addSubmit(mWai, mPosition, result1);
                break;
        }
    }

    @OnClick({R.id.renewal, R.id.line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.renewal:
                PreUtils.putString("renew", "ok");
                Intent intent2 = new Intent(getContext(), RenewActivity.class);
                intent2.putExtra("time", expiresIn);
                intent2.putExtra("cardType", cardType);
                intent2.putExtra("babyBirthday", babyBirthday);
                startActivity(intent2);
                break;
            /*case R.id.tv_one:
            case R.id.tv_two:
            case R.id.tv_three:
            case R.id.iv_four:
                Intent intent3 = new Intent(getContext(), ClockOnActivity.class);
                startActivity(intent3);
                break;*/

            case R.id.line:
                Intent intent4 = new Intent(getContext(), PastCourseActivity.class);
                startActivity(intent4);
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

}
