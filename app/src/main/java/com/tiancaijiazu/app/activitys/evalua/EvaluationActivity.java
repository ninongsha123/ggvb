package com.tiancaijiazu.app.activitys.evalua;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TimeUtils;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bean.AnswerBean;
import com.tiancaijiazu.app.activitys.evalua.adapters.RlvAdapter_but;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.ReviewTheTopicBean;
import com.tiancaijiazu.app.beans.SubmitAppraisalBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.TheTopicRecordBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluationActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.bottom_progress)
    ProgressBar mBottomProgress;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.name_title)
    TextView mNameTitle;
    @BindView(R.id.name_branch)
    TextView mNameBranch;
    @BindView(R.id.topic_sum)
    TextView mTopicSum;
    @BindView(R.id.back)
    LinearLayout mBack;
    @BindView(R.id.tit)
    TextView mTit;
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
    private List<BabyMessageBean.ResultBean> mResult;
    private boolean isbo;
    //使用map来对题目进行分类，以模块名称为key
    HashMap<String,List<ReviewTheTopicBean.ResultBean.ContentListBean>> hashMap = new HashMap<>();
    private ArrayList<Object> mObjects1 = new ArrayList<>();
    private ArrayList<Object> mObjects2 = new ArrayList<>();
    private ArrayList<Object> mObjects3 = new ArrayList<>();
    private ArrayList<Object> mObjects4 = new ArrayList<>();
    private ArrayList<Object> mObjects5 = new ArrayList<>();
    private String mText2;

    @Override
    protected void initEventAndData() {
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

    //退出测评
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


    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<String> arrayList = new ArrayList<>();
        mRlvAdapterBut = new RlvAdapter_but(arrayList);
        mRecylerView.setAdapter(mRlvAdapterBut);
        mRlvAdapterBut.setOnClickLisiter(new RlvAdapter_but.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                TextView textViews = view.findViewById(R.id.tv_but);
                Drawable drawables = getResources().getDrawable(R.drawable.baby_answer_chang);
                Drawable drawable = getResources().getDrawable(R.drawable.answer_back);
//                Drawable drawables = getResources().getDrawable(R.drawable.button_change_color);
//                if (TimeUtil.isInvalidClick(textViews, 800)) {
//                    if (textViews.isPressed()) {
//                        textViews.setBackground(drawable);
//                    }
//                    return;
//                }
//                    textViews.setBackground(drawables);
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
                        initShuaXin(sum);
                    } else {//测评完成
                        mPopupWindow1.showAtLocation(mInflate1, Gravity.BOTTOM, 0, 0);
                    }
            }
        });
    }

    private void initShuaXin(int sum) {
        //选项
        String scoreSetting = mContentList.get(sum).getScoreSetting();
        //总题数
        mTopicSum.setText("总" + mContentList.size() + "题");
        //标题
        mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());



        ReviewTheTopicBean.ResultBean.ContentListBean contentListBean = mContentList.get(sum);

        if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")){
            int i = mObjects1.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";

        }
        if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")){
            int i = mObjects4.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")){
            int i = mObjects3.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")){
            int i = mObjects5.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")){
            int i = mObjects2.indexOf(contentListBean);
            mText2 = "(" + (i + 1) + "/" + mObjects2.size() + ")";
        }

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
        String[] split = scoreSetting.split("[|]");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            strings.add(split[i]);
        }
        mRlvAdapterBut.addData(strings, "");
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_evaluation;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {

            case REVIEWTHETOPIC:
                ReviewTheTopicBean reviewTheTopicBean = (ReviewTheTopicBean) o;
                ReviewTheTopicBean.ResultBean result = reviewTheTopicBean.getResult();
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

                for (int i=0;i<mContentList.size();i++){
                    ReviewTheTopicBean.ResultBean.ContentListBean contentListBean = mContentList.get(i);

                    if (contentListBean.getIndicatorsValue().equals("社交能力")){
                        mObjects1.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("语言能力")){
                        mObjects2.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("运动能力")){
                        mObjects3.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("动手能力")){
                        mObjects4.add(contentListBean);
                    }
                    if (contentListBean.getIndicatorsValue().equals("学习能力")){
                        mObjects5.add(contentListBean);
                    }

                }

                mTopicSum.setText("总" + mContentList.size() + "题");
                //标题
                mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());


                ReviewTheTopicBean.ResultBean.ContentListBean contentListBeans = mContentList.get(sum);

                if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")){
                    int i = mObjects1.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")){
                    int i = mObjects4.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")){
                    int i = mObjects3.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")){
                    int i = mObjects5.indexOf(contentListBeans);
                    mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
                }
                if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")){
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
                    Intent intent = new Intent(EvaluationActivity.this, TestReportActivity.class);
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
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.iv_finis, R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                List<TheTopicRecordBean> theTopicRecordBeans1 = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
                if (sum < mContentList.size() && theTopicRecordBeans1.size() != 0) {
                    mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                } else {
                    finish();
                }
                break;
            case R.id.back:
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
                break;
        }
    }

    @Override
    public void onBackPressed() {
        List<TheTopicRecordBean> theTopicRecordBeans1 = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
        if (sum < mContentList.size() && theTopicRecordBeans1.size() != 0) {
            mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
        } else {
            finish();
        }
    }

    private void initShuaXinBack(int sum) {
        //答案选择
        String scoreSetting = mContentList.get(sum).getScoreSetting();
        //题目总数量
        mTopicSum.setText("总" + mContentList.size() + "题");
        //标题
        mNameTitle.setText(mContentList.get(sum).getIndicatorsValue());

        ReviewTheTopicBean.ResultBean.ContentListBean contentListBeans = mContentList.get(sum);

        if (mContentList.get(sum).getIndicatorsValue().equals("社交能力")){
            int i = mObjects1.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects1.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("动手能力")){
            int i = mObjects4.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects4.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("运动能力")){
            int i = mObjects3.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects3.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("学习能力")){
            int i = mObjects5.indexOf(contentListBeans);
            mText2 = "(" + (i + 1) + "/" + mObjects5.size() + ")";
        }
        if (mContentList.get(sum).getIndicatorsValue().equals("语言能力")){
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
        String[] split = scoreSetting.split("[|]");
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            strings.add(split[i]);
        }


        List<TheTopicRecordBean> theTopicRecordBeans = DataBaseMannger.getIntrance().selectTheTopicRecordBeanTiao(mBabyId, mSubjectId);
        int size = theTopicRecordBeans.size();
        String scoreSetting1 = theTopicRecordBeans.get(size - 1).getScoreSetting();
        mRlvAdapterBut.addData(strings, scoreSetting1);
    }

}
