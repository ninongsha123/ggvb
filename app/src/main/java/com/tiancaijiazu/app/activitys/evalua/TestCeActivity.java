package com.tiancaijiazu.app.activitys.evalua;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
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

import com.google.gson.Gson;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.AnswerBean;
import com.tiancaijiazu.app.activitys.evalua.adapters.RlvAdapter_but;
import com.tiancaijiazu.app.activitys.evalua.adapters.Testadapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.ReviewTheTopicBean;
import com.tiancaijiazu.app.beans.SubmitAppraisalBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.TheTopicRecordBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.VerticalViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCeActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    LinearLayout mLineOnes;
    RelativeLayout mLineOne;
    LinearLayout mLineTwos;
    RelativeLayout mLineTwo;
    LinearLayout mLineThrees;
    RelativeLayout mLineThree;
    LinearLayout mLineFours;
    RelativeLayout mLineFour;
    LinearLayout mLine2;
    LinearLayout mLineFives;

    RelativeLayout mLineFive;
    private RlvAdapter_but mRlvAdapterBut;
    private int sum = 0;
    private List<ReviewTheTopicBean.ResultBean.ContentListBean> mContentList;
    private PopupWindow mPopupWindow;
    private View mInflate;
    private View mInflate1;
    private PopupWindow mPopupWindow1;
    private String mBabyId;
    private String pan = "";
    private String mSubjectId;
    ImageView mIvFinis;
    RelativeLayout mRelative;
    TextView mTitle;
    ProgressBar mBottomProgress;
    RecyclerView mRecylerView;
    TextView mNameTitle;
    TextView mNameBranch;
    TextView mTopicSum;
    LinearLayout mBack;
    TextView mTit;

    //2
    ImageView mIvFiniss;
    RelativeLayout mRelatives;
    ImageView mShang;
    RelativeLayout mColorStudy;
    TextView mStudy;
    TextView mStudyFen;
    TextView mStudyState;
    RelativeLayout mColorSocial;
    TextView mSocial;
    TextView mSocialFen;
    TextView mSocialState;
    RelativeLayout mLine1;
    RelativeLayout mColorLanguage;
    TextView mLanguage;
    TextView mLanguageFen;
    TextView mLanguageState;
    RelativeLayout mColorPerceive;
    RelativeLayout mColorExercise;
    TextView mPerceive;
    TextView mPerceiveFen;
    TextView mPerceiveState;
    TextView mExercise;
    TextView mExerciseFen;
    TextView mExerciseState;

    private List<BabyMessageBean.ResultBean> mResult;

    HashMap<String, List<ReviewTheTopicBean.ResultBean.ContentListBean>> hashMap = new HashMap<>();
    private ArrayList<Object> mObjects1 = new ArrayList<>();
    private ArrayList<Object> mObjects2 = new ArrayList<>();
    private ArrayList<Object> mObjects3 = new ArrayList<>();
    private ArrayList<Object> mObjects4 = new ArrayList<>();
    private ArrayList<Object> mObjects5 = new ArrayList<>();
    private String mText2;

    @BindView(R.id.viewp)
    VerticalViewPager mViewp;


    private ArrayList<View> mViews;
    private ImageView mNextPage;
    private View mView;
    private View mView1;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEdit;
    private int mPosition=0;
    private int mIn0Score;
    private int mIn1Score;
    private int mIn2Score;
    private int mIn3Score;
    private int mIn4Score;

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void initEventAndData() {
        initviewpager();
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        mSubjectId = intent.getStringExtra("subjectId");
        mBabyId = intent.getStringExtra("babyId");
        mResult = (List<BabyMessageBean.ResultBean>) intent.getSerializableExtra("data");
        mPresenter.getDataP(mSubjectId, DifferentiateEnum.REVIEWTHETOPIC);
        initPopOne();
        initPopTwo();
        initRecylerView();
    }

    private void initShua() {
        List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
        String s2 = mNameTitle.getText().toString();
        int one = mSp.getInt("one", 0);
        int two = mSp.getInt("two", 0);
        int three = mSp.getInt("three", 0);
        int four = mSp.getInt("four", 0);
        int five = mSp.getInt("five", 0);
        if (s2.equals("社交能力")) {
//                mColorExercise.setBackground(getResources().getDrawable(R.drawable.bg_lan));
//                mLineFive.setBackground(getResources().getDrawable(R.drawable.bg_lans));
//                mLineFives.setBackground(getResources().getDrawable(R.mipmap.yesd));
//                mExercise.setTextColor(Color.parseColor("#333333"));
                mPerceiveState.setTextColor(Color.parseColor("#333333"));
                mPerceiveFen.setTextColor(Color.parseColor("#00DEFF"));
                mColorPerceive.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineFour.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineFours.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mPerceive.setTextColor(Color.parseColor("#333333"));
                mColorLanguage.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineThree.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineThrees.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mLanguage.setTextColor(Color.parseColor("#333333"));
                mSocialState.setTextColor(Color.parseColor("#333333"));
                mColorSocial.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineTwo.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineTwos.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mSocial.setTextColor(Color.parseColor("#333333"));
                mStudyState.setTextColor(Color.parseColor("#333333"));
                mStudyFen.setTextColor(Color.parseColor("#00DEFF"));
                mColorStudy.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineOne.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineOnes.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mStudy.setTextColor(Color.parseColor("#333333"));
                mSocialFen.setTextColor(Color.parseColor("#00DEFF"));
                mLanguageFen.setTextColor(Color.parseColor("#00DEFF"));
                mLanguageState.setTextColor(Color.parseColor("#333333"));
                mStudyState.setText("已完成");
                mSocialState.setText("已完成");
                mLanguageState.setText("已完成");
                mPerceiveState.setText("已完成");
            mSocialFen.setText(two+"分/共"+mIn1Score+"分");
            mStudyFen.setText(one+"分/共"+mIn0Score+"分");
            mLanguageFen.setText(three+"分/共"+mIn2Score+"分");
            mPerceiveFen.setText(four+"分/共"+mIn3Score+"分");
                mExerciseFen.setText(five+"分/共"+mIn4Score+"分");
            }
            if (s2.equals("动手能力")) {
//                mColorSocial.setBackground(getResources().getDrawable(R.drawable.bg_lan));
//                mLineTwo.setBackground(getResources().getDrawable(R.drawable.bg_lans));
//                mLineTwos.setBackground(getResources().getDrawable(R.mipmap.yesd));
//                mSocial.setTextColor(Color.parseColor("#333333"));
                mStudyState.setTextColor(Color.parseColor("#333333"));
                mStudyFen.setTextColor(Color.parseColor("#00DEFF"));
                mColorStudy.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineOne.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineOnes.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mStudy.setTextColor(Color.parseColor("#333333"));
                mSocialFen.setText(two+"分/共"+mIn1Score+"分");
                mStudyFen.setText(one+"分/共"+mIn0Score+"分");
                mStudyState.setText("已完成");
            }
            if (s2.equals("运动能力")) {
                mStudyFen.setText(one+"分/共100分");
            }
            if (s2.equals("学习能力")) {
//                mColorLanguage.setBackground(getResources().getDrawable(R.drawable.bg_lan));
//                mLineThree.setBackground(getResources().getDrawable(R.drawable.bg_lans));
//                mLineThrees.setBackground(getResources().getDrawable(R.mipmap.yesd));
//                mLanguage.setTextColor(Color.parseColor("#333333"));
                mSocialState.setTextColor(Color.parseColor("#333333"));
                mColorSocial.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineTwo.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineTwos.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mSocial.setTextColor(Color.parseColor("#333333"));
                mStudyState.setTextColor(Color.parseColor("#333333"));
                mStudyFen.setTextColor(Color.parseColor("#00DEFF"));
                mColorStudy.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineOne.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineOnes.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mStudy.setTextColor(Color.parseColor("#333333"));
                mSocialFen.setTextColor(Color.parseColor("#00DEFF"));
                mSocialFen.setText(two+"分/共"+mIn1Score+"分");
                mStudyFen.setText(one+"分/共"+mIn0Score+"分");
                mLanguageFen.setText(three+"分/共"+mIn2Score+"分");
                mSocialState.setText("已完成");
                mStudyState.setText("已完成");
            }
            if (s2.equals("语言能力")) {
//                mColorPerceive.setBackground(getResources().getDrawable(R.drawable.bg_lan));
//                mLineFour.setBackground(getResources().getDrawable(R.drawable.bg_lans));
//                mLineFours.setBackground(getResources().getDrawable(R.mipmap.yesd));
//                mPerceive.setTextColor(Color.parseColor("#333333"));
                mColorLanguage.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineThree.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineThrees.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mLanguage.setTextColor(Color.parseColor("#333333"));
                mSocialState.setTextColor(Color.parseColor("#333333"));
                mColorSocial.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineTwo.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineTwos.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mSocial.setTextColor(Color.parseColor("#333333"));
                mStudyState.setTextColor(Color.parseColor("#333333"));
                mStudyFen.setTextColor(Color.parseColor("#00DEFF"));
                mColorStudy.setBackground(getResources().getDrawable(R.drawable.bg_lan));
                mLineOne.setBackground(getResources().getDrawable(R.drawable.bg_lans));
                mLineOnes.setBackground(getResources().getDrawable(R.mipmap.yesd));
                mStudy.setTextColor(Color.parseColor("#333333"));
                mSocialFen.setTextColor(Color.parseColor("#00DEFF"));
                mLanguageFen.setTextColor(Color.parseColor("#00DEFF"));
                mLanguageState.setTextColor(Color.parseColor("#333333"));
                mStudyState.setText("已完成");
                mSocialState.setText("已完成");
                mLanguageState.setText("已完成");
                mSocialFen.setText(two+"分/共"+mIn1Score+"分");
                mStudyFen.setText(one+"分/共"+mIn0Score+"分");
                mLanguageFen.setText(three+"分/共"+mIn2Score+"分");
                mPerceiveFen.setText(four+"分/共"+mIn3Score+"分");
            }
    }
    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<String> arrayList = new ArrayList<>();
        mRlvAdapterBut = new RlvAdapter_but(arrayList);
        mRecylerView.setAdapter(mRlvAdapterBut);
        mRlvAdapterBut.setOnClickLisiter(new RlvAdapter_but.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                mPosition = position;
                TextView textViews = view.findViewById(R.id.tv_but);
                Drawable drawables = getResources().getDrawable(R.drawable.baby_answer_chang);
                Drawable drawable = getResources().getDrawable(R.drawable.answer_back);
                if (TimeUtil.isInvalidClick(view, 800)) {
                    return;
                }
                CountDownTimer countDownTimer = new CountDownTimer(600, 600) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        textViews.setBackground(drawables);
                        view.setEnabled(false);
                    }

                    @Override
                    public void onFinish() {
                        textViews.setBackground(drawable);
                        view.setEnabled(true);
                        cancel();
                    }
                };
                countDownTimer.start();
                String s = mData.get(position);
                String[] split = s.split(",");
                ArrayList<TheTopicRecordBean> theTopicRecordBeans = new ArrayList<>();
                theTopicRecordBeans.add(new TheTopicRecordBean(null, mContentList.get(sum).getContentId() + "", split[0], split[1], mBabyId, mSubjectId, mContentList.size()));
                DataBaseMannger.getIntrance().insertTheTopicRecord(theTopicRecordBeans);
                sum += 1;
                if (sum != 0) {
                    mBack.setVisibility(View.VISIBLE);
                }
                if (sum < mContentList.size()) { //正在测评
                    initShuaXin(sum, position);
                } else {//测评完成
                    mEdit.clear();
                    mEdit.commit();
                    mPopupWindow1.showAtLocation(mInflate1, Gravity.BOTTOM, 0, 0);
                }
            }
        });
    }

    private void initShuaXin(int sum,int pos) {
        int one = mSp.getInt("one", 0);
        int two = mSp.getInt("two", 0);
        int three = mSp.getInt("three", 0);
        int four = mSp.getInt("four", 0);
        int five = mSp.getInt("five", 0);
        //选项
        String scoreSetting = mContentList.get(sum).getScoreSetting();
        //总题数
        mTopicSum.setText("总" + mContentList.size() + "题");
        //标题
        mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());

        String[] split = scoreSetting.split("[|]");
        String s = split[pos];

        ReviewTheTopicBean.ResultBean.ContentListBean contentListBean = mContentList.get(sum);

        if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            five+=i1;
            mExerciseFen.setText(five + "分/共"+mIn4Score+"分");
//            mColorExercise.setBackground(getResources().getDrawable(R.drawable.bg_lan));
//            mLineFive.setBackground(getResources().getDrawable(R.drawable.bg_lans));
//            mLineFives.setBackground(getResources().getDrawable(R.mipmap.yesd));
//            mExercise.setTextColor(Color.parseColor("#333333"));
            mColorPerceive.setBackground(getResources().getDrawable(R.drawable.bg_lan));
            mLineFour.setBackground(getResources().getDrawable(R.drawable.bg_lans));
            mLineFours.setBackground(getResources().getDrawable(R.mipmap.yesd));
            mPerceive.setTextColor(Color.parseColor("#333333"));
            mPerceiveState.setTextColor(Color.parseColor("#333333"));
            mPerceiveState.setText("已完成");
            mPerceiveFen.setTextColor(Color.parseColor("#00DEFF"));
//            mExerciseFen.setTextColor(Color.parseColor("#00DEFF"));
            int i = mObjects1.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";
            mEdit.putInt("five",five);
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")) {
                String[] split1 = s.split(",");
                String s1 = split1[1];
                int i1 = Integer.parseInt(s1);
                two += i1;
                mSocialFen.setText(two + "分/共"+mIn1Score+"分");
                mColorStudy.setBackground(getResources().getDrawable(R.drawable.bg_lan));
            mLineOne.setBackground(getResources().getDrawable(R.drawable.bg_lans));
            mLineOnes.setBackground(getResources().getDrawable(R.mipmap.yesd));
            mStudy.setTextColor(Color.parseColor("#333333"));
            mStudyState.setTextColor(Color.parseColor("#333333"));
            mStudyState.setText("已完成");
            mStudyFen.setTextColor(Color.parseColor("#00DEFF"));
            int i = mObjects4.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
            mEdit.putInt("two",two);
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")) {
                String[] split1 = s.split(",");
                String s1 = split1[1];
                int i1 = Integer.parseInt(s1);
                one += i1;
                mStudyFen.setText(one + "分/共"+mIn0Score+"分");
            int i = mObjects3.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
            mEdit.putInt("one",one);
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")) {
                String[] split1 = s.split(",");
                String s1 = split1[1];
                int i1 = Integer.parseInt(s1);
                three+=i1;
                mLanguageFen.setText(three + "分/共"+mIn1Score+"分");
            mColorSocial.setBackground(getResources().getDrawable(R.drawable.bg_lan));
            mLineTwo.setBackground(getResources().getDrawable(R.drawable.bg_lans));
            mLineTwos.setBackground(getResources().getDrawable(R.mipmap.yesd));
            mSocial.setTextColor(Color.parseColor("#333333"));
            mSocialState.setTextColor(Color.parseColor("#333333"));
            mSocialState.setText("已完成");
            mSocialFen.setTextColor(Color.parseColor("#00DEFF"));
            int i = mObjects5.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
            mEdit.putInt("three",three);
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")) {
                String[] split1 = s.split(",");
                String s1 = split1[1];
                int i1 = Integer.parseInt(s1);
                four += i1;
                mPerceiveFen.setText(four + "分共"+mIn2Score+"分");
            mColorLanguage.setBackground(getResources().getDrawable(R.drawable.bg_lan));
            mLineThree.setBackground(getResources().getDrawable(R.drawable.bg_lans));
            mLineThrees.setBackground(getResources().getDrawable(R.mipmap.yesd));
            mLanguage.setTextColor(Color.parseColor("#333333"));
            mLanguageState.setTextColor(Color.parseColor("#333333"));
            mLanguageState.setText("已完成");
            mLanguageFen.setTextColor(Color.parseColor("#00DEFF"));
            int i = mObjects2.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects2.size() + ")";
            mEdit.putInt("four",four);
        }
           mEdit.commit();
        //小标题
        String optionValue = mContentList.get(sum).getOptionValue();
        String text1 = optionValue + "  ";
        Spannable sp = new SpannableString(text1 + mText2);
        sp.setSpan(new AbsoluteSizeSpan(16, true), 0, text1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //SPAN_INCLUSIVE_INCLUSIVE -包含start和end所在的端点
        sp.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text1.length() + mText2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNameBranch.setText(sp);

        //题目
        mTitle.setText(mContentList.get(sum).getTitle());

        float progress = (float) (sum + 1) / mContentList.size() * 100;

        mBottomProgress.setProgress((int) progress);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            strings.add(split[i]);
        }
        mRlvAdapterBut.addData(strings, "");
    }
    //测评完毕
    private void initPopTwo() {
        mInflate1 = LayoutInflater.from(this).inflate(R.layout.pop_done_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        mPopupWindow1.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        TextView look = mInflate1.findViewById(R.id.look);

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                ArrayList<AnswerBean> answerBeans = new ArrayList<>();
                for (int i = 0; i < theTopicRecordBeans.size(); i++) {
                    answerBeans.add(new AnswerBean(theTopicRecordBeans.get(i).getContentId(), theTopicRecordBeans.get(i).getOption()));
                }
                Gson gson = new Gson();
                String s = gson.toJson(answerBeans);
                Log.i("yx456", "onClick: " + theTopicRecordBeans.get(0).getSubjectId());
                HashMap<String, String> map = new HashMap<>();
                map.put("babyId", theTopicRecordBeans.get(0).getBabyId());
                map.put("subjectId", theTopicRecordBeans.get(0).getSubjectId());
                map.put("contents", s);
                mPresenter.getDataP(map, DifferentiateEnum.SUBMITAPPRAISAL);
            }
        });
    }

    private void initPopOne() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_midway_layout, null);
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

        TextView tv = mInflate.findViewById(R.id.tv);
        TextView goOn = mInflate.findViewById(R.id.go_on);
        TextView quit = mInflate.findViewById(R.id.quit);
        /*tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });*/
        goOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
}
    private void initviewpager() {
        mView = LayoutInflater.from(this).inflate(R.layout.activity_evaluation, null, false);
        mView1 = LayoutInflater.from(this).inflate(R.layout.layout_capacitys, null, false);
        mViews = new ArrayList<>();
        mViews.add(mView);
        mViews.add(mView1);
        Testadapter myadapter = new Testadapter(this, mViews);
        mViewp.setAdapter(myadapter);
        initView(mView, mView1);
        initviews();

        mNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewp.setCurrentItem(1);
            }
        });
        mIvFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TheTopicRecordBean> theTopicRecordBeans1 = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                if (sum < mContentList.size() && theTopicRecordBeans1.size() != 0) {
                    mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                } else {
                    finish();
                }
            }
        });
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!"".equals(mRlvAdapterBut.scoreSetting)) {
                    List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                    TheTopicRecordBean theTopicRecordBean = theTopicRecordBeans.get(theTopicRecordBeans.size() - 1);
                    DataBaseMannger.getIntrance().deleteTheTopicRecordBean(theTopicRecordBean);
                }
                sum -= 1;
                if (sum == 0) {
                    mBack.setVisibility(View.GONE);
                }
                initShuaXinBack(sum);
            }
        });
    }

    private void initShuaXinBack(int sum) {
        int one = mSp.getInt("one", 0);
        int two = mSp.getInt("two", 0);
        int three = mSp.getInt("three", 0);
        int four = mSp.getInt("four", 0);
        int five = mSp.getInt("five", 0);

        //答案选择
        String scoreSetting = mContentList.get(sum).getScoreSetting();
        //题目总数量
        mTopicSum.setText("总" + mContentList.size() + "题");
        //标题
        mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());

        ReviewTheTopicBean.ResultBean.ContentListBean contentListBeans = mContentList.get(sum);

        String[] split = scoreSetting.split("[|]");
        String s = split[mPosition];

        if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            int d=five-i1;
            mExerciseFen.setText(d+"分/共"+mIn4Score+"分");
            mEdit.putInt("five",d);
            int i = mObjects1.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            int d=two-i1;
            mSocialFen.setText(d+"分/共"+mIn1Score+"分");
            mEdit.putInt("two",d);
            int i = mObjects4.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            int d=one-i1;
            mStudyFen.setText(d+"分/共"+mIn0Score+"分");
            mEdit.putInt("one",d);
            int i = mObjects3.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            int d=three-i1;
            mLanguageFen.setText(d+"分/共"+mIn2Score+"分");
            mEdit.putInt("three",d);
            int i = mObjects5.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")) {
            String[] split1 = s.split(",");
            String s1 = split1[1];
            int i1 = Integer.parseInt(s1);
            int d=four-i1;
            mPerceiveFen.setText(d+"分/共"+mIn3Score+"分");
            mEdit.putInt("four",d);
            int i = mObjects2.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects2.size() + ")";
        }
        //小标题
        String optionValue = mContentList.get(sum).getOptionValue();
        String text1 = optionValue + "  ";
        //   String text2 = "(" + (sum + 1) + "/" + mContentList.size() + ")";
        Spannable sp = new SpannableString(text1 + mText2);
        sp.setSpan(new AbsoluteSizeSpan(16, true), 0, text1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //SPAN_INCLUSIVE_INCLUSIVE -包含start和end所在的端点
        sp.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text1.length() + mText2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mNameBranch.setText(sp);

        //题目
        mTitle.setText(mContentList.get(sum).getTitle());

        float progress = (float) (sum + 1) / mContentList.size() * 100;
        mBottomProgress.setProgress((int) progress);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            strings.add(split[i]);
        }
        List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
        int size = theTopicRecordBeans.size();
        String scoreSetting1 = theTopicRecordBeans.get(size - 1).getScoreSetting();
        mRlvAdapterBut.addData(strings, scoreSetting1);
    }

    private void initView(View inflate, View inflate1) {
        mNextPage = inflate.findViewById(R.id.nextPage);
        mIvFinis = inflate.findViewById(R.id.iv_finis);
        mRelative = inflate.findViewById(R.id.relative);
        mTitle = inflate.findViewById(R.id.title);
        mBottomProgress = inflate.findViewById(R.id.bottom_progress);
        mRecylerView = inflate.findViewById(R.id.recylerView);
        mNameTitle = inflate.findViewById(R.id.name_title);
        mNameBranch = inflate.findViewById(R.id.name_branch);
        mTopicSum = inflate.findViewById(R.id.topic_sum);
        mBack = inflate.findViewById(R.id.back);
        mTit = inflate.findViewById(R.id.tit);


        mShang = inflate1.findViewById(R.id.shang);
        mColorStudy = inflate1.findViewById(R.id.color_study);
        mStudy = inflate1.findViewById(R.id.study);
        mStudyFen = inflate1.findViewById(R.id.study_fen);
        mStudyState = inflate1.findViewById(R.id.study_state);
        mColorSocial = inflate1.findViewById(R.id.color_social);
        mSocial = inflate1.findViewById(R.id.social);
        mSocialFen = inflate1.findViewById(R.id.social_fen);
        mSocialState = inflate1.findViewById(R.id.social_state);
        mColorLanguage = inflate1.findViewById(R.id.color_language);
        mLanguage = inflate1.findViewById(R.id.language);
        mLanguageFen = inflate1.findViewById(R.id.language_fen);
        mLanguageState = inflate1.findViewById(R.id.language_state);
        mColorPerceive = inflate1.findViewById(R.id.color_perceive);
        mPerceive = inflate1.findViewById(R.id.perceive);
        mPerceiveFen = inflate1.findViewById(R.id.perceive_fen);
        mPerceiveState = inflate1.findViewById(R.id.perceive_state);
        mExercise = inflate1.findViewById(R.id.exercise);
        mExerciseFen = inflate1.findViewById(R.id.exercise_fen);
        mExerciseState = inflate1.findViewById(R.id.exercise_state);
        mColorExercise = inflate1.findViewById(R.id.color_exercise);
        mLineOne = inflate1.findViewById(R.id.lineOne);
        mLineOnes = inflate1.findViewById(R.id.lineOnes);
        mLineTwo = inflate1.findViewById(R.id.lineTwo);
        mLineTwos = inflate1.findViewById(R.id.lineTwos);
        mLineThree = inflate1.findViewById(R.id.lineThree);
        mLineThrees = inflate1.findViewById(R.id.lineThrees);
        mLineFour = inflate1.findViewById(R.id.lineFour);
        mLineFours = inflate1.findViewById(R.id.lineFours);
        mLineFive = inflate1.findViewById(R.id.lineFive);
        mLineFives = inflate1.findViewById(R.id.lineFives);
    }


    private void initviews() {
        mShang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewp.setCurrentItem(0);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_test_ce;
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case REVIEWTHETOPIC:
                ReviewTheTopicBean reviewTheTopicBean = (ReviewTheTopicBean) o;
                ReviewTheTopicBean.ResultBean result = reviewTheTopicBean.getResult();
                ReviewTheTopicBean.ResultBean.IndicatorsListBean in0 = result.getIndicatorsList().get(0);
                ReviewTheTopicBean.ResultBean.IndicatorsListBean in1 = result.getIndicatorsList().get(1);
                ReviewTheTopicBean.ResultBean.IndicatorsListBean in2 = result.getIndicatorsList().get(2);
                ReviewTheTopicBean.ResultBean.IndicatorsListBean in3 = result.getIndicatorsList().get(3);
                ReviewTheTopicBean.ResultBean.IndicatorsListBean in4 = result.getIndicatorsList().get(4);
                mIn0Score = in0.getTotalScore();
                mIn1Score = in1.getTotalScore();
                mIn2Score = in2.getTotalScore();
                mIn3Score = in3.getTotalScore();
                mIn4Score = in4.getTotalScore();
                mStudyFen.setText("共"+mIn0Score+"分");
                mSocialFen.setText("共"+mIn1Score+"分");
                mLanguageFen.setText("共"+mIn2Score+"分");
                mPerceiveFen.setText("共"+mIn3Score+"分");
                mExerciseFen.setText("共"+mIn4Score+"分");

                mStudy.setText(in0.getName());
                mSocial.setText(in1.getName());
                mLanguage.setText(in2.getName());
                mPerceive.setText(in3.getName());
                mExercise.setText(in4.getName());

                mContentList = result.getContentList();
                List<TheTopicRecordBean> theTopicRecordBeans1 = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                if (theTopicRecordBeans1.size() == 0) {
                    sum = 0;
                } else {
                    if (mContentList.size() == theTopicRecordBeans1.get(0).getLenght()) {
                        sum = theTopicRecordBeans1.size();
                        mBack.setVisibility(View.VISIBLE);
                    } else {
                        for (int i = 0; i < theTopicRecordBeans1.size(); i++) {
                            DataBaseMannger.getIntrance().deleteTheTopicRecordBean(theTopicRecordBeans1.get(i));
                        }
                        sum = 0;
                    }
                }
                String scoreSetting = "";
                if (sum == mContentList.size()) {
                    if (!"".equals(mRlvAdapterBut.scoreSetting)) {
                        List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                        TheTopicRecordBean theTopicRecordBean = theTopicRecordBeans.get(theTopicRecordBeans.size() - 1);
                        DataBaseMannger.getIntrance().deleteTheTopicRecordBean(theTopicRecordBean);
                    }

                    sum -= 1;
                    if (sum == 0) {
                        mBack.setVisibility(View.GONE);
                    }
                    //initShuaXinBack(sum);
                    scoreSetting = mContentList.get(sum).getScoreSetting();
                } else {
                    scoreSetting = mContentList.get(sum).getScoreSetting();
                }

                for (int i = 0; i < mContentList.size(); i++) {
                    ReviewTheTopicBean.ResultBean.ContentListBean contentListBean = mContentList.get(i);

                    if (contentListBean.getIndicatorsValue().equals("社交能力")) {
                        mObjects1.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("语言能力")) {
                        mObjects2.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("运动能力")) {
                        mObjects3.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("动手能力")) {
                        mObjects4.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("学习能力")) {
                        mObjects5.add(contentListBean);
                    }

                }

                mTopicSum.setText("总" + mContentList.size() + "题");
                //标题
                mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());


                ReviewTheTopicBean.ResultBean.ContentListBean contentListBeans = mContentList.get(sum);

                if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")) {
                    int i = mObjects1.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")) {
                    int i = mObjects4.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")) {
                    int i = mObjects3.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")) {
                    int i = mObjects5.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")) {
                    int i = mObjects2.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects2.size() + ")";
                }

                //小标题
                String optionValue = mContentList.get(sum).getOptionValue();
                String text1 = optionValue + " ";
                //  mText2 = "(" + (sum + 1) + "/" + mContentList.size() + ")";
                Spannable sp = new SpannableString(text1 + mText2);
                sp.setSpan(new AbsoluteSizeSpan(16, true), 0, text1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); //SPAN_INCLUSIVE_INCLUSIVE -包含start和end所在的端点
                sp.setSpan(new AbsoluteSizeSpan(14, true), text1.length(), text1.length() + mText2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                mNameBranch.setText(sp);

                //题目
                mTitle.setText(mContentList.get(sum).getTitle());

                float progress = (float) (sum + 1) / mContentList.size() * 100;
                mBottomProgress.setProgress((int) progress);
                String[] split = scoreSetting.split("[|]");
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    strings.add(split[i]);
                }
                mRlvAdapterBut.addData(strings, "");
                mSp = getSharedPreferences(result.getSubjectId()+"", MODE_PRIVATE);
                mEdit = mSp.edit();
                initShua();
                break;
            case SUBMITAPPRAISAL:
                SubmitAppraisalBean submitAppraisalBean = (SubmitAppraisalBean) o;
                String code = submitAppraisalBean.getCode();
                Log.i("yx456", "show: " + code);
                if (code.equals("0")) {
                    List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                    for (int i = 0; i < theTopicRecordBeans.size(); i++) {
                        DataBaseMannger.getIntrance().deleteTheTopicRecordBean(theTopicRecordBeans.get(i));
                    }
                    Intent intent = new Intent(TestCeActivity.this, TestReportActivity.class);
                    intent.putExtra("reportId", submitAppraisalBean.getResult() + "");
                    intent.putExtra("babyId", mBabyId);
                    intent.putExtra("data", (Serializable) mResult);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
