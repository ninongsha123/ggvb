package com.tiancaijiazu.app.activitys.early;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.diy.RenewActivity;
import com.tiancaijiazu.app.activitys.early.adapters.ExpandableListViewAdapter_trial_class;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Course_List;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_Pop_Custom;
import com.tiancaijiazu.app.activitys.past.PastCourseActivity;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.EarlyCourseBean;
import com.tiancaijiazu.app.beans.EarlyCourseListBean;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExclusiveCourseActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.baby_birth)
    TextView mBabyBirth;
    @BindView(R.id.birth)
    TextView mBirth;
    @BindView(R.id.curriculum)
    TextView mCurriculum;
    @BindView(R.id.jindubei)
    ImageView mJindubei;
    @BindView(R.id.degree)
    TextView mDegree;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.bell_line)
    LinearLayout mBellLine;
    @BindView(R.id.line3)
    LinearLayout mLine3;
    @BindView(R.id.renewal)
    ImageView mRenewal;
    @BindView(R.id.tv_one)
    TextView mTvOne;
    @BindView(R.id.tv_two)
    TextView mTvTwo;
    @BindView(R.id.tv_three)
    TextView mTvThree;
    @BindView(R.id.iv_four)
    ImageView mIvFour;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.bottom_progress)
    ProgressBar mBottomProgress;
    @BindView(R.id.prog)
    RelativeLayout mProg;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    private int page = 1;
    private ExpandableListViewAdapter_trial_class mExpandableListViewAdapterTrialClass;
    private RlvAdapter_Course_List mRlvAdapterCourseList;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private RlvAdapter_Pop_Custom mRlvAdapterPopCustom;
    private int mPosition;
    private int mWai;
    private TextView mText;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        String card = intent.getStringExtra("card");
        String userName = PreUtils.getString("userName", "");
        Log.i("userName", "initEventAndData: " + userName);
        mTitleName.setText(userName + "的专属早教课");
        if ("1".equals(card)) {
            mRela.setBackgroundResource(R.mipmap.wellcard);
            mTitle.setText("早教学院无忧卡");
            mTitle.setTextColor(Color.parseColor("#AD6405"));
            mBabyBirth.setTextColor(Color.parseColor("#AD6405"));
            mCurriculum.setTextColor(Color.parseColor("#AD6405"));
            mDegree.setTextColor(Color.parseColor("#AD6405"));
        } else if ("2".equals(card)) {
            mRela.setBackgroundResource(R.mipmap.annual_card);
            mTitle.setText("早教学院年卡");
            mTitle.setTextColor(Color.parseColor("#D52B21"));
            mBabyBirth.setTextColor(Color.parseColor("#D52B21"));
            mCurriculum.setTextColor(Color.parseColor("#D52B21"));
            mDegree.setTextColor(Color.parseColor("#D52B21"));
        } else if ("3".equals(card)) {
            mRela.setBackgroundResource(R.mipmap.half_a_year_card);
            mTitle.setText("早教学院半年卡");
            mTitle.setTextColor(Color.parseColor("#01A093"));
            mBabyBirth.setTextColor(Color.parseColor("#01A093"));
            mCurriculum.setTextColor(Color.parseColor("#01A093"));
            mDegree.setTextColor(Color.parseColor("#01A093"));
        }
        boolean checked = mCheckbox.isChecked();
        if (checked == true) {
            mCheckbox.setClickable(false);
            mCheckbox.setEnabled(false);
        }
        //initView();
        initRecylerView();
        initPop();
        //mPresenter.getDataP(page,DifferentiateEnum.EARLYCOURSELIST);
        mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM,loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.FORMALCURRICULUM,loadingLayout);
            }
        });
        float i = (float) 29 / 53 * 100;
        mBottomProgress.setProgress((int) i);
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<FormalCurriculumBean.ResultBean.ChapterListBean> chapterListBeans = new ArrayList<>();
        mRlvAdapterCourseList = new RlvAdapter_Course_List(chapterListBeans, this);
        mRecylerView.setAdapter(mRlvAdapterCourseList);

        mRlvAdapterCourseList.setOnClickLisiterCustom(new RlvAdapter_Course_List.onClickLisiterCustom() {
            @Override
            public void onClickerCustom(View view, int position, List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean> mData, int wai) {
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackList = mData.get(position).getFeedbackList();
                String ability = mData.get(0).getAbility();
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
                Intent intent = new Intent(ExclusiveCourseActivity.this, TakeAClassActivity.class);
                FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean = data.get(position);
                String title = mData.get(wai).getTitle();
                List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackList = data.get(data.size() - 1).getFeedbackList();
                intent.putExtra("data", contentsListBean);
                intent.putExtra("title", title);
                intent.putExtra("game", (Serializable) feedbackList);
                startActivity(intent);
            }
        });
    }

    private void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_game_select, null);
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(linearLayoutManager);
        List<FormalCurriculumBean.ResultBean.ChapterListBean.ContentsListBean.FeedbackListBean> feedbackListBeans = new ArrayList<>();
        mRlvAdapterPopCustom = new RlvAdapter_Pop_Custom(feedbackListBeans, this);
        recylerView.setAdapter(mRlvAdapterPopCustom);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mRlvAdapterCourseList.addSubmit(mWai,mPosition, result1);
                mPopupWindow.dismiss();
            }
        });
    }
    /*private void initView(List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList) {

        mExpandableListViewAdapterTrialClass = new ExpandableListViewAdapter_trial_class(this, chapterList);
        mExpandable.setAdapter(mExpandableListViewAdapterTrialClass);
        ListUtils.setListViewHeightBasedOnChildren(mExpandable);
        //取消左侧下拉图标
        mExpandable.setGroupIndicator(null);

        mExpandable.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                *//**
     * 计算group下的子项的高度
     *//*
                ListUtils.setExpandedListViewHeightBasedOnChildren(mExpandable,
                        i);
                // 更新group每一项的高度
                ListUtils.setListViewHeightBasedOnChildren(
                        mExpandable);
            }
        });

        mExpandable.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                *//**
     * 计算group下的每一个子项的高度，然后收缩
     *//*
                ListUtils.setCollapseListViewHeightBasedOnChildren(mExpandable,
                        i);
                */

    /**
     * 重新评估group的高度
     *//*
                ListUtils.setListViewHeightBasedOnChildren(
                        mExpandable);
            }
        });

        mExpandable.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Intent intent = new Intent(ExclusiveCourseActivity.this, TakeAClassActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }
*/
    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_exclusive_course;
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
                List<FormalCurriculumBean.ResultBean.ChapterListBean> chapterList1 = result2.getChapterList();
                mRlvAdapterCourseList.addData(chapterList1);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_finis, R.id.renewal, R.id.tv_one, R.id.tv_two, R.id.tv_three, R.id.iv_four, R.id.checkbox, R.id.line})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            /*case R.id.diy_line:
                Intent intent = new Intent(this, DiyActivity.class);
                startActivity(intent);
                break;
            case R.id.clock:
                Intent intent1 = new Intent(ExclusiveCourseActivity.this, ClockActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.renewal:
                Intent intent2 = new Intent(this, RenewActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_one:
            case R.id.tv_two:
            case R.id.tv_three:
            case R.id.iv_four:
                Intent intent3 = new Intent(this, ClockOnActivity.class);
                startActivity(intent3);
                break;
            case R.id.checkbox:
                boolean checked = mCheckbox.isChecked();
                if (checked == true) {
                    mCheckbox.setEnabled(false);
                    mCheckbox.setClickable(false);
                }
                break;
            case R.id.line:
                Intent intent4 = new Intent(this, PastCourseActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
