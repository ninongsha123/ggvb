package com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RecDataVpAdapter;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;
import com.tiancaijiazu.app.activitys.topic.TopicCentrectivity;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.adapters.RecDataRlvAdapter;
import com.tiancaijiazu.app.adapters.RlvAdapter_Yd;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ArticleDatas;
import com.tiancaijiazu.app.beans.ArticleReportBeans;
import com.tiancaijiazu.app.beans.CollectBean;
import com.tiancaijiazu.app.beans.CommentBean;
import com.tiancaijiazu.app.beans.ConcernBean;
import com.tiancaijiazu.app.beans.DeleArticelBean;
import com.tiancaijiazu.app.beans.DeleteCommentBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.LikeListsBean;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.beans.TwoCommentBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.HOT_SPOTFragment;
import com.tiancaijiazu.app.homepagefragment.MyFragment;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.CustomFragmeLayout;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 * 家族圈-条目详情
 */

public class RecDataActivity extends BaseActivity<IView, Presenter<IView>> implements IView, RecDataRlvAdapter.onClickLisiter, RecDataRlvAdapter.onClickLisiterTwo {

    @BindView(R.id.nes)
    CustomFragmeLayout mNes;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    /*@BindView(R.id.edit_com)
    EditText mEditCom;*/
    @BindView(R.id.edit_comm)
    EditText mEditComm;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.iv_enter)
    ImageView mIvEnter;
    @BindView(R.id.tv_praise)
    TextView mTvPraise;
    @BindView(R.id.cir_three)
    CircleImageView mCirThree;
    @BindView(R.id.cir_two)
    CircleImageView mCirTwo;
    @BindView(R.id.cir_one)
    CircleImageView mCirOne;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.vp)
    ViewPager mViewpager;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.article)
    TextView mArticle;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.flow)
    FlowGroupView mFlow;
    @BindView(R.id.like_sum)
    TextView mLikeSum;
    /*@BindView(R.id.user_iv)
    CircleImageView mUserIv;*/
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.circle)
    CircleImageView mCircle;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.checkbox_xin)
    CheckBox mCheckboxXin;
    @BindView(R.id.checkbox_shou_chang)
    CheckBox mCheckboxShouChang;
    @BindView(R.id.xin_sum)
    TextView mXinSum;
    @BindView(R.id.concern)
    CheckBox mConcern;
    @BindView(R.id.collect_sum)
    TextView mCollectSum;
    @BindView(R.id.iv_discuss)
    ImageView mIvDiscuss;
    @BindView(R.id.xin_line)
    LinearLayout mXinLine;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.shou_chang_line)
    LinearLayout mShouChangLine;
    @BindView(R.id.comm_line)
    LinearLayout mCommLine;
    @BindView(R.id.tv_comm_sum)
    TextView mTvCommSum;
    @BindView(R.id.more_dian)
    ImageView mMoreDian;
    private RlvAdapter_Yd mRlvAdapterYd;
    private RecDataVpAdapter mDataVpAdapter;
    private long mArticleId;
    private RecDataRlvAdapter mRecDataRlvAdapter;
    private boolean isbo;
    private int mLikes;
    private int mDiscuss;
    private String mS1;
    private long mUserId;
    private HashMap<String, String> mMap = new HashMap<>();
    private int page = 1;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;
    private String mId;
    private int screenWidth;
    private int[] imgheights;
    private ArticleDatas.ResultBean mResult;
    private Intent mIntent;
    private int mPosition;
    private boolean isboo;
    private int mSize;
    private String mBiao;



    @Override
    protected void initEventAndData() {
        screenWidth = ScreenUtil.getInstance(this).getScreenWidth();
        mId = PreUtils.getString("userId", "");
        Log.i("userid", "initEventAndData: " + mId);
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        mPosition = mIntent.getIntExtra("position", 0);
        mArticleId = mIntent.getLongExtra("articleId", 0);
        mUserId = mIntent.getLongExtra("userId", 0);
        String userAvatar = mIntent.getStringExtra("userAvatar");
        String nickname = mIntent.getStringExtra("nickname");
        mName.setText(nickname);
        Glide.with(this).load(userAvatar).into(mCircle);
        Log.i("yx", "initEventAndData: " + mArticleId);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mPresenter.getDataP1(mArticleId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", mArticleId);
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
        //setEditTextHintSize(mEditCom, "说点什么...", 13);
        setEditTextHintSize(mEditComm, "说点什么...", 13);
        //mEditCom.setInputType(InputType.TYPE_NULL);
        mEditComm.setInputType(InputType.TYPE_NULL);
        ScreenStatusUtil.setFillDip(this);
        //mEditCom.setFocusable(false);
        mEditComm.setFocusable(false);

        mEditComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow();
            }
        });
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(mArticleId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void popWindow() {
        View inflate = LayoutInflater.from(RecDataActivity.this).inflate(R.layout.edit_comment, null);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setFocusable(true);// 取得焦点
        //popupWindow.setClippingEnabled(false);
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.showAtLocation(mRela, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideInput();
            }
        });
        EditText editCommtent = inflate.findViewById(R.id.edit_comment);
        TextView faSong = inflate.findViewById(R.id.fa_song);
        faSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                if (editCommtent.getText().toString().length() != 0) {
                    String s = editCommtent.getText().toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("article", mArticleId);
                    map.put("content", s);
                    map.put("replyId", "");
                    boolean tokenTime = TimeUtil.getTokenTime();
                    if (tokenTime) {

                    } else {
                        mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                        editCommtent.setText("");
                        hideInput();
                        mPopupWindow.dismiss();
                    }
                } else {
                    Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editCommtent.setFocusable(true);
        editCommtent.setFocusableInTouchMode(true);
        editCommtent.requestFocus();
        showSoft(editCommtent);
        editCommtent.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    //((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    if (editCommtent.getText().toString().length() != 0) {
                        String s = editCommtent.getText().toString();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("article", mArticleId);
                        map.put("content", s);
                        map.put("replyId", "");
                        boolean tokenTime = TimeUtil.getTokenTime();
                        if (tokenTime) {

                        } else {
                            mPresenter.getDataP(map, DifferentiateEnum.COMMENT);
                            editCommtent.setText("");
                            hideInput();
                            mPopupWindow.dismiss();
                        }
                    } else {
                        Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    private void initRecylerView(int size) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView.setLayoutManager(layoutManager);
        mRlvAdapterYd = new RlvAdapter_Yd(size);
        mRecylerView.setAdapter(mRlvAdapterYd);
    }

    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_rec_data;
    }




    @OnClick({R.id.back, R.id.iv_enter, R.id.tv_praise, R.id.share, R.id.checkbox_xin,
            R.id.concern, R.id.checkbox_shou_chang, R.id.circle, R.id.xin_line, R.id.shou_chang_line, R.id.comm_line,R.id.more_dian,R.id.name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                if ("community".equals(mBiao)) {
                    if (mResult != null) {
                        mIntent.putExtra("position", mPosition);
                        int likes = mResult.getIsLikes();
                        mIntent.putExtra("likes", likes);
                        mIntent.putExtra("size", mSize);
                        boolean checked = mCheckboxShouChang.isChecked();
                        mIntent.putExtra("checkec", checked);
                        setResult(35, mIntent);
                    }
                }
                finish();
                break;
            case R.id.iv_enter:
            case R.id.tv_praise:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                mLikes = mResult.getLikes();
                if (mLikes == 0) {
                    Toast.makeText(mActivity, "暂无点赞！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, LikeListActivity.class);
                    intent.putExtra("artivle", mArticleId);
                    startActivity(intent);
                }
                break;
            case R.id.share:

                break;
            case R.id.checkbox_xin:
            case R.id.xin_line:
                /*if (TimeUtil.isInvalidClick(view, 300))
                    return;*/
             /*   isboo = false;
                if (isboo) {

                    isboo = true;
                }*/
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mArticleId);
                map.put("contentType", "1");
                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadingLayout);
                break;
            case R.id.concern:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mS1.length() != 0) {
                    mPresenter.getDataP1(mS1, DifferentiateEnum.CONCERN, loadingLayout);
                }
                break;
            case R.id.checkbox_shou_chang:
            case R.id.shou_chang_line:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                mPresenter.getDataP1(mArticleId + "", DifferentiateEnum.COLLECT, loadingLayout);
                break;
            case R.id.circle:
            case R.id.name:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                Intent intent1 = new Intent(this, UserCenterActivity.class);
                intent1.putExtra("userId", mUserId);
                startActivity(intent1);
                break;
            case R.id.comm_line:
                mEditComm.performClick();
                break;
            case R.id.more_dian:
                List<String> names = new ArrayList<>();
                if (mId != null) {
                    if (mId.equals(mS1)) {
                        names.add("举报");
                        names.add("删除");
                    } else {
                        names.add("举报");
                    }
                }
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                Intent intent = new Intent(RecDataActivity.this, ReportPageActivity.class);
                                intent.putExtra("articleId",mArticleId);
                                startActivity(intent);
                                break;
                            case 1:
                                mPresenter.getDataP(mArticleId+"",DifferentiateEnum.DELEARTICEL);
                                break;
                        }
                    }
                },names);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if ("community".equals(mBiao)) {
            if (mResult != null) {
                mIntent.putExtra("position", mPosition);
                int likes = mResult.getLikes();
                mIntent.putExtra("likes", likes);
                mIntent.putExtra("size", mSize);
                boolean checked = mCheckboxShouChang.isChecked();
                mIntent.putExtra("checked", checked);
                setResult(35, mIntent);
            }
        }

        super.onBackPressed();
    }

    @Override
    public void showError(String error) {
        Log.i("yx", "showError: " + error);
    }
    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ARTICLEDATAS:
                ArticleDatas articleDatas = (ArticleDatas) o;
                mResult = articleDatas.getResult();
                if (mTitle != null) {
                    if(mId.equals(mResult.getUserId()+"")){
                        mConcern.setVisibility(View.GONE);
                    }
                    if (!"".equals(mResult.getTitle())) {
                        mTitle.setVisibility(View.VISIBLE);
                        mTitle.setText(mResult.getTitle());
                    }else {
                        mTitle.setVisibility(View.GONE);
                    }
                    try {
                        String detail = mResult.getDetail();
                        if (!"".equals(detail)) {
                            mArticle.setVisibility(View.VISIBLE);
                            mArticle.setText(URLDecoder.decode(detail, "UTF-8"));
                        }else {
                            mArticle.setVisibility(View.GONE);
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    mTvCommSum.setText(mResult.getDiscuss() + "");
                    String publishTime = mResult.getPublishTime();
                    long l = TimeUtil.dataOne(publishTime);
                    String s = TimeUtil.QQFormatTime(l);
                    Log.i("yx", "show: " + s);
                    mTime.setText(s);
                    mS1 = mResult.getUserId() + "";
                    if (mId != null) {
                        if (mId.equals(mS1)) {
                            //mConcern.setVisibility(View.GONE);
                        } else {
                            mConcern.setVisibility(View.VISIBLE);
                        }
                    }

                    if (mResult.getIsFollow() == 1) {
                        mConcern.setChecked(true);
                    } else {
                        mConcern.setChecked(false);
                    }
                    if (mResult.getIsCollect() == 1) {
                        mCheckboxShouChang.setChecked(true);
                    } else {
                        mCheckboxShouChang.setChecked(false);
                    }
                    ((SimpleItemAnimator) mRecylerView.getItemAnimator()).setSupportsChangeAnimations(false);
                    int collect = mResult.getCollect();
                    if (collect == 0) {
                        mCollectSum.setText("");
                    } else {
                        mCollectSum.setText(collect + "");
                    }
                    mLikes = mResult.getLikes();
                    if (mLikes == 0) {
                        mXinSum.setText("");
                    } else {
                        mXinSum.setText(mLikes + "");
                    }
                    if (mLikes == 0) {
                        mTvPraise.setText("赞");
                    } else {
                        mTvPraise.setText(mLikes + "赞");
                    }
                    int isLikes = mResult.getIsLikes();
                    if (isLikes == 0) {
                        mCheckboxXin.setChecked(false);
                    } else {
                        mCheckboxXin.setChecked(true);
                    }
                    mCheckboxXin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                mResult.setLikes(mResult.getLikes() + 1);
                                mResult.setIsLikes(1);
                                mXinSum.setText(mResult.getLikes() + "");
                                mTvPraise.setText(mResult.getLikes() + "赞");
                            } else {
                                mResult.setLikes(mResult.getLikes() - 1);
                                mResult.setIsLikes(0);
                                if (mResult.getLikes() != 0) {
                                    mXinSum.setText(mResult.getLikes() + "");
                                } else {
                                    mXinSum.setText("");
                                }
                                if (mLikes == 0) {
                                    mTvPraise.setText("赞");
                                } else {
                                    mTvPraise.setText(mResult.getLikes() + "赞");
                                }
                            }
                        }
                    });
                    List<ArticleDatas.ResultBean.SubjectListBean> subjectList = mResult.getSubjectList();
                    if (subjectList.size() != 0) {

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 0, 5, 0);
                        if (mFlow != null) {
                            mFlow.removeAllViews();
                        }
                        for (int i = 0; i < subjectList.size(); i++) {
                            TextView textView = new TextView(RecDataActivity.this);
                            textView.setLayoutParams(layoutParams);
                            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                            textView.setText("#  " + subjectList.get(i).getSubjectName());
                            textView.setBackgroundResource(R.drawable.bg_tab_bai);
                            textView.setTextColor(Color.parseColor("#00DEFF"));
                            initEvents(textView);
                            textView.setPadding(calculateDpToPx(10), calculateDpToPx(1), calculateDpToPx(10), calculateDpToPx(2));
                            mMap.put("#  " + subjectList.get(i).getSubjectName(), subjectList.get(i).getSubjectId() + "");
                            mFlow.addView(textView, i, layoutParams);
                        }
                    }
                    List<ArticleDatas.ResultBean.ImgListBean> imgList = mResult.getImgList();
                    Log.i("yx", "show: " + imgList.get(0).getImgUri());
                    if (imgList.size() == 1) {
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTitle.getLayoutParams();
                        int i = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
                        layoutParams.topMargin = i;//将默认的距离底部20dp，改为0
                        mTitle.setLayoutParams(layoutParams);
                    }
                    initViewPag(imgList);

                    List<ArticleDatas.ResultBean.DiscussListBean> discussList = mResult.getDiscussList();
                    Log.i("yx===", "show: " + discussList.size());
                    mLikeSum.setText("共 " + discussList.size() + " 条评论");
                    if (discussList.size() != 0) {
                        mIvDiscuss.setVisibility(View.GONE);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                        mRlv.setLayoutManager(layoutManager);
                        ((SimpleItemAnimator) mRlv.getItemAnimator()).setSupportsChangeAnimations(false);
                        long userId = mResult.getUserId();
                        mDiscuss = mResult.getDiscuss();
                        if (mDiscuss > 10) {
                            isbo = true;
                        } else {
                            isbo = false;
                        }
                        mRecDataRlvAdapter = new RecDataRlvAdapter(discussList, this, userId, isbo, mDiscuss);
                        mRlv.setAdapter(mRecDataRlvAdapter);
                        mRecDataRlvAdapter.setOnClickLisiter(this);
                        mRecDataRlvAdapter.setOnClickLisiterOne(new RecDataRlvAdapter.onClickLisiterOne() {
                            @Override
                            public void onClickerOne(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean> mData, CheckBox checkBox) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleId", mData.get(position).getDiscussId());
                                map.put("contentType", "2");
                                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadingLayout);
                            }
                        });

                        mRecDataRlvAdapter.setOnClickLisiterUserOne(new RecDataRlvAdapter.onClickLisiterUserOne() {
                            @Override
                            public void onClickerUserOne(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean> mData) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                long userId1 = mData.get(position).getUserId();
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", userId1);
                                startActivity(intent1);
                            }
                        });

                        mRecDataRlvAdapter.setOnClickLisiterUserTwo(new RecDataRlvAdapter.onClickLisiterUserTwo() {
                            @Override
                            public void onClickerUserTwo(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                long userId1 = mData.get(position).getUserId();
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", userId1);
                                startActivity(intent1);
                            }
                        });


                        mRecDataRlvAdapter.setOnClickZan(new RecDataRlvAdapter.onClickZan() {
                            @Override
                            public void onClickZan(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleId", mData.get(position).getDiscussId());
                                map.put("contentType", "2");
                                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadingLayout);
                            }
                        });
                        mRecDataRlvAdapter.setOnClickLisiterDi(new RecDataRlvAdapter.onClickLisiterDi() {
                            @Override
                            public void onClickDi(View view, int page) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleId", mArticleId);
                                map.put("page", page);

                                mPresenter.getDataP1(map, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                            }
                        });
                        mRecDataRlvAdapter.setOnClickLisiterTwoDi(new RecDataRlvAdapter.onClickLisiterTwoDi() {
                            @Override
                            public void onClickTwoDi(View view, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData, int page) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Log.i("yx123", "onClickTwoDi: " + page);
                                long replyId = mData.get(0).getReplyId();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("articleId", mArticleId);
                                map.put("replyId", replyId);
                                map.put("page", page);
                                mPresenter.getDataP1(map, DifferentiateEnum.TWOCOMMENTLISTS, loadingLayout);
                            }
                        });
                        mRecDataRlvAdapter.setOnClickLisiterTwo(this);
                    } else {
                        mIvDiscuss.setVisibility(View.VISIBLE);
                    }
                }


                break;
            case COMMENT:
                CommentBean commentBean = (CommentBean) o;
                mPresenter.getDataP1(mArticleId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                ToastUtils.showShortToast(mActivity, commentBean.getResult());

                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                ToastUtils.showShortToast(mActivity, likeBean.getResult());
                //mPresenter.getDataP(mArticleId, DifferentiateEnum.ARTICLEDATAS);
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mArticleId);
                map.put("page", page);
                mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);

                break;
            case TWOCOMMENTLISTS:
                TwoCommentBean twoCommentBean = (TwoCommentBean) o;
                Log.i("yx===", "show: " + twoCommentBean.getCode());
                List<TwoCommentBean.ResultBean> result1 = twoCommentBean.getResult();
                if (result1.size() != 0) {
                    mRecDataRlvAdapter.addTwoData(result1);
                }

                break;
            case ONECOMMENTLISTS:
                OneCommentBean oneCommentBean = (OneCommentBean) o;
                List<OneCommentBean.ResultBean> result2 = oneCommentBean.getResult();
                mRecDataRlvAdapter.addOneData(result2, mDiscuss);
                break;
            case DELETECOMMENT:
                DeleteCommentBean deleteCommentBean = (DeleteCommentBean) o;
                String result3 = deleteCommentBean.getResult();
                Toast.makeText(mActivity, "" + result3, Toast.LENGTH_SHORT).show();
                mPresenter.getDataP1(mArticleId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);

                break;
            case CONCERN:
                ConcernBean concernBean = (ConcernBean) o;
                String result4 = concernBean.getResult();
                String msg = concernBean.getMsg();
                if (result4.equals("关注成功")||result4.equals("已取消关注")){
                    ToastUtils.showShortToast(mActivity, result4);
                }
                if (result4.equals("")) {
                    ToastUtils.showShortToast(mActivity, msg);
                }
                break;
            case COLLECT:
                CollectBean collectBean = (CollectBean) o;
                String result5 = collectBean.getResult();
                ToastUtils.showShortToast(mActivity, result5);
                if (mPresenter != null) {
                    mPresenter.getDataP1(mArticleId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                }

                break;
            case LIKELISTS:
                LikeListsBean likeListsBean = (LikeListsBean) o;
                List<LikeListsBean.ResultBean> result6 = likeListsBean.getResult();
                mSize = result6.size();
                if (mXinSum != null) {
                    if (result6.size() == 0) {
                        mXinSum.setText("");
                        mTvPraise.setText("赞");
                        mCirOne.setVisibility(View.GONE);
                        mCirTwo.setVisibility(View.GONE);
                        mCirThree.setVisibility(View.GONE);
                    } else if (result6.size() == 1) {
                        mCirThree.setVisibility(View.VISIBLE);
                        Glide.with(RecDataActivity.this).load(result6.get(0).getAvatar()).into(mCirThree);
                        mCirThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(0).getUserId());
                                startActivity(intent1);
                            }
                        });
                    } else if (result6.size() == 2) {
                        mCirTwo.setVisibility(View.VISIBLE);
                        mCirThree.setVisibility(View.VISIBLE);
                        Glide.with(RecDataActivity.this).load(result6.get(1).getAvatar()).into(mCirThree);
                        Glide.with(RecDataActivity.this).load(result6.get(0).getAvatar()).into(mCirTwo);
                        mCirThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(1).getUserId());
                                startActivity(intent1);
                            }
                        });
                        mCirTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(0).getUserId());
                                startActivity(intent1);
                            }
                        });
                    } else {
                        mCirOne.setVisibility(View.VISIBLE);
                        mCirTwo.setVisibility(View.VISIBLE);
                        mCirThree.setVisibility(View.VISIBLE);
                        Glide.with(RecDataActivity.this).load(result6.get(2).getAvatar()).into(mCirThree);
                        Glide.with(RecDataActivity.this).load(result6.get(1).getAvatar()).into(mCirTwo);
                        Glide.with(RecDataActivity.this).load(result6.get(0).getAvatar()).into(mCirOne);
                        mXinSum.setText(result6.size() + "");
                        mTvPraise.setText(result6.size() + "赞");
                        mCirThree.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(2).getUserId());
                                startActivity(intent1);
                            }
                        });
                        mCirTwo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(1).getUserId());
                                startActivity(intent1);
                            }
                        });
                        mCirOne.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (TimeUtil.isInvalidClick(view, 300))
                                    return;
                                Intent intent1 = new Intent(RecDataActivity.this, UserCenterActivity.class);
                                intent1.putExtra("userId", result6.get(0).getUserId());
                                startActivity(intent1);
                            }
                        });
                    }
                }

                break;
            case ARYICLEREPORT:
                hideInput();
                ArticleReportBeans articleReportBeans = (ArticleReportBeans) o;
                String result = articleReportBeans.getResult();
                if (result.equalsIgnoreCase("提交成功")) {
                    Toast.makeText(RecDataActivity.this, result, Toast.LENGTH_SHORT).show();
                }
                break;
            case DELEARTICEL:
                DeleArticelBean deleArticelBean = (DeleArticelBean) o;
                String result7 = deleArticelBean.getResult();
                ToastUtils.showShortToast(RecDataActivity.this,result7);
                mIntent.putExtra("position",mPosition);
                setResult(33,mIntent);
                finish();
                break;
        }

    }

    public void initViewPag(List<ArticleDatas.ResultBean.ImgListBean> imgList) {
        mViewpager.setOffscreenPageLimit(imgList.size());
        Glide.with(this).load(imgList.get(0).getImgUri()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                float scale = (float) resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                int defaultheight = (int) (scale * screenWidth);
                initViewPager(defaultheight, imgList);
            }
        });
    } //获取第一张图片高度后，给viewpager设置adapter

    private void initViewPager(final int defaultheight, List<ArticleDatas.ResultBean.ImgListBean> imgList) {
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < imgList.size(); i++) {
            strings.add(imgList.get(i).getImgUri());
        }
        //Toast.makeText(MainActivity.this, "图片为空", Toast.LENGTH_LONG).show();
        //mDataVpAdapter = new RecDataVpAdapter(imgList, this,screenWidth);
        ViewGroup.LayoutParams params = mViewpager.getLayoutParams();
        params.height = defaultheight;
        mViewpager.setLayoutParams(params);
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                if (imgheights == null || imgheights.length != imgList.size()) {
                    imgheights = null;
                    imgheights = new int[imgList.size()];
                }
                return imgList.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                final ImageView imageView = new ImageView(RecDataActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getApplicationContext()).load(imgList.get(position).getImgUri()).into(new ImageViewTarget<Drawable>(imageView) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        if (resource != null) {
                            float scale = (float) resource.getIntrinsicHeight() / resource.getIntrinsicWidth();
                            imgheights[position] = (int) (scale * screenWidth);
                            imageView.setImageDrawable(resource);
                        } else {
                            //Toast.makeText(MainActivity.this, "图片为空", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(RecDataActivity.this, LookPhotoActivity.class);
                        intent.putStringArrayListExtra("images", strings);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == imgheights.length - 1) {
                    return;
                } //计算ViewPager现在应该的高度,heights[]表示页面高度的数组。
                int height = (int) ((imgheights[position] == 0 ? defaultheight : imgheights[position]) * (1 - positionOffset) + (imgheights[position + 1] == 0 ? defaultheight : imgheights[position + 1]) * positionOffset);
                //为ViewPager设置高度
                ViewGroup.LayoutParams params = mViewpager.getLayoutParams();
                params.height = height;
                mViewpager.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                mRlvAdapterYd.addUi(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (imgList.size() >= 2) {
            initRecylerView(imgList.size());
        }
    }

    private void initEvents(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                Intent intent = new Intent(RecDataActivity.this, TopicCentrectivity.class);
                String s = mMap.get(textView.getText().toString());
                intent.putExtra("subjectId", s);
                startActivity(intent);
            }
        });
    }


    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onClicker(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean> mData) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        long userId = mData.get(position).getUserId();
        List<String> names = new ArrayList<>();
        if (mId != null) {
            if (mId.equals("" + userId)) {
                names.add("回复");
                names.add("举报");
                names.add("删除");
            } else {
                names.add("回复");
                names.add("举报");
            }
        }
        long articleId = mData.get(position).getArticleId();
        long replyId = mData.get(position).getDiscussId();
        Log.i("yx", "onClicker: " + replyId);
        showDialog(new SelectDialog.SelectDialogListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        View inflate = LayoutInflater.from(RecDataActivity.this).inflate(R.layout.edit_comment, null);
                        mPopupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                        mPopupWindow1.setFocusable(true);// 取得焦点
                        //popupWindow.setClippingEnabled(false);
                        mPopupWindow1.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                        //点击外部消失
                        mPopupWindow1.setOutsideTouchable(true);
                        //设置可以点击
                        mPopupWindow1.setTouchable(true);
                        //进入退出的动画，指定刚才定义的style
                        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
                        mPopupWindow1.showAtLocation(mRela, Gravity.BOTTOM, 0, 0);
                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                hideInput();
                            }
                        });
                        EditText editCommtent = inflate.findViewById(R.id.edit_comment);
                        editCommtent.setFocusable(true);
                        editCommtent.setFocusableInTouchMode(true);
                        editCommtent.requestFocus();
                        TextView faSong = inflate.findViewById(R.id.fa_song);
                        faSong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TimeUtil.isInvalidClick(v, 300))
                                    return;
                                if (editCommtent.getText().toString().length() != 0) {
                                    String s = editCommtent.getText().toString();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("article", articleId);
                                    map.put("content", s);
                                    map.put("replyId", replyId + "");
                                    map.put("discussId", "");
                                    boolean tokenTime = TimeUtil.getTokenTime();
                                    if (tokenTime) {

                                    } else {
                                        mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                                        editCommtent.setText("");
                                        hideInput();
                                        mPopupWindow1.dismiss();
                                    }
                                } else {
                                    Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        showSoft(editCommtent);
                        editCommtent.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                    //((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    if (editCommtent.getText().toString().length() != 0) {
                                        String s = editCommtent.getText().toString();
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("article", articleId);
                                        map.put("content", s);
                                        map.put("replyId", replyId + "");
                                        map.put("discussId", "");
                                        boolean tokenTime = TimeUtil.getTokenTime();
                                        if (tokenTime) {

                                        } else {
                                            mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                                            editCommtent.setText("");
                                            hideInput();
                                            mPopupWindow1.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
                        });
                        Toast.makeText(RecDataActivity.this, "回复", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        /*View inflate1 = LayoutInflater.from(RecDataActivity.this).inflate(R.layout.edit_comment, null);
                        mPopupWindow1 = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                        mPopupWindow1.setFocusable(true);// 取得焦点
                        //popupWindow.setClippingEnabled(false);
                        mPopupWindow1.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                        //点击外部消失
                        mPopupWindow1.setOutsideTouchable(true);
                        //设置可以点击
                        mPopupWindow1.setTouchable(true);
                        //进入退出的动画，指定刚才定义的style
                        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
                        mPopupWindow1.showAtLocation(mRela, Gravity.BOTTOM, 0, 0);
                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                hideInput();
                            }
                        });
                        EditText editCommtent1 = inflate1.findViewById(R.id.edit_comment);
                        editCommtent1.setFocusable(true);
                        editCommtent1.setFocusableInTouchMode(true);
                        editCommtent1.requestFocus();
                        TextView faSong1 = inflate1.findViewById(R.id.fa_song);
                        faSong1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TimeUtil.isInvalidClick(v, 300))
                                    return;
                                if (editCommtent1.getText().toString().length() != 0) {
                                    String s = editCommtent1.getText().toString();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("articleId", articleId);
                                    map.put("summary", s);
                                    boolean tokenTime = TimeUtil.getTokenTime();
                                    if (tokenTime) {

                                    } else {
                                        mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                        editCommtent1.setText("");
                                        hideInput();
                                        mPopupWindow1.dismiss();
                                    }
                                } else {
                                    Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        showSoft(editCommtent1);
                        editCommtent1.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                    //((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    if (editCommtent1.getText().toString().length() != 0) {
                                        String s = editCommtent1.getText().toString();
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("articleId", articleId);
                                        map.put("summary", s);
                                        boolean tokenTime = TimeUtil.getTokenTime();
                                        if (tokenTime) {

                                        } else {
                                            mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                            editCommtent1.setText("");
                                            hideInput();
                                            mPopupWindow1.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
                        });*/
                        Intent intent = new Intent(RecDataActivity.this,ReportPageActivity.class);
                        intent.putExtra("articleId",mArticleId);
                        startActivity(intent);
                        break;
                    case 2:
                        mPresenter.getDataP1(replyId, DifferentiateEnum.DELETECOMMENT, loadingLayout);
                        break;
                }
            }
        }, names);
    }

    private void showSoft(EditText editCommtent) {
        InputMethodManager inputMethodManager = (InputMethodManager) editCommtent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private boolean isSoftShowing() { //获取当屏幕内容的高度
        int screenHeight = this.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        this.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        // 选取screenHeight*2/3进行判断 Log.i(TAG,"screenHigh: " + screenHeight + " rectViewBom : " + rect.bottom );
        return screenHeight * 2 / 3 > rect.bottom;
    }

    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }

    @Override
    public void onClickerTwo(View view, int position, List<ArticleDatas.ResultBean.DiscussListBean.SubDiscussBean> mData) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        long userId = mData.get(position).getUserId();
        List<String> names = new ArrayList<>();
        if (mId != null) {
            if (mId.equals("" + userId)) {
                names.add("回复");
                names.add("举报");
                names.add("删除");
            } else {
                names.add("回复");
                names.add("举报");
            }
        }
        long articleId = mData.get(position).getArticleId();
        long replyId = mData.get(position).getReplyId();
        long discussId = mData.get(position).getDiscussId();
        String userNickname = mData.get(position).getUserNickname();
        Log.i("yx", articleId + "onClicker: " + replyId);
        showDialog(new SelectDialog.SelectDialogListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        View inflate = LayoutInflater.from(RecDataActivity.this).inflate(R.layout.edit_comment, null);
                        final PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                        popupWindow.setFocusable(true);// 取得焦点
                        //popupWindow.setClippingEnabled(false);
                        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        //点击外部消失
                        popupWindow.setOutsideTouchable(true);
                        //设置可以点击
                        popupWindow.setTouchable(true);
                        //进入退出的动画，指定刚才定义的style
                        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
                        popupWindow.showAtLocation(mRela, Gravity.BOTTOM, 0, 0);
                        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                hideInput();
                            }
                        });
                        EditText editCommtent = inflate.findViewById(R.id.edit_comment);
                        editCommtent.setFocusable(true);
                        editCommtent.setFocusableInTouchMode(true);
                        editCommtent.requestFocus();
                        TextView faSong = inflate.findViewById(R.id.fa_song);
                        faSong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TimeUtil.isInvalidClick(v, 300))
                                    return;
                                if (editCommtent.getText().toString().length() != 0) {
                                    String s = editCommtent.getText().toString();
                                    HashMap<String, Object> map = new HashMap<>();
                                    Log.i("yx", articleId + "onKey: " + replyId);
                                    map.put("article", articleId);
                                    map.put("content", s);
                                    map.put("replyId", replyId + "");
                                    map.put("discussId", discussId + "");
                                    boolean tokenTime = TimeUtil.getTokenTime();
                                    if (tokenTime) {

                                    } else {
                                        mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                                        editCommtent.setText("");
                                        hideInput();
                                        popupWindow.dismiss();
                                    }
                                } else {
                                    Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        showSoft(editCommtent);
                        editCommtent.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                    //((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    if (editCommtent.getText().toString().length() != 0) {
                                        String s = editCommtent.getText().toString();
                                        HashMap<String, Object> map = new HashMap<>();
                                        Log.i("yx", articleId + "onKey: " + replyId);
                                        map.put("article", articleId);
                                        map.put("content", s);
                                        map.put("replyId", replyId + "");
                                        map.put("discussId", discussId + "");
                                        boolean tokenTime = TimeUtil.getTokenTime();
                                        if (tokenTime) {

                                        } else {
                                            mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                                            editCommtent.setText("");
                                            hideInput();
                                            popupWindow.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
                        });
                        Toast.makeText(RecDataActivity.this, "回复", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                       /* View inflate1 = LayoutInflater.from(RecDataActivity.this).inflate(R.layout.edit_comment, null);
                        mPopupWindow1 = new PopupWindow(inflate1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
                        mPopupWindow1.setFocusable(true);// 取得焦点
                        //popupWindow.setClippingEnabled(false);
                        mPopupWindow1.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mPopupWindow1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
                        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
                        //点击外部消失
                        mPopupWindow1.setOutsideTouchable(true);
                        //设置可以点击
                        mPopupWindow1.setTouchable(true);
                        //进入退出的动画，指定刚才定义的style
                        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);
                        mPopupWindow1.showAtLocation(mRela, Gravity.BOTTOM, 0, 0);
                        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                hideInput();
                            }
                        });
                        EditText editCommtent1 = inflate1.findViewById(R.id.edit_comment);
                        editCommtent1.setFocusable(true);
                        editCommtent1.setFocusableInTouchMode(true);
                        editCommtent1.requestFocus();
                        TextView faSong1 = inflate1.findViewById(R.id.fa_song);
                        faSong1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (TimeUtil.isInvalidClick(v, 300))
                                    return;
                                if (editCommtent1.getText().toString().length() != 0) {
                                    String s = editCommtent1.getText().toString();
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("articleId", articleId);
                                    map.put("summary", s);
                                    boolean tokenTime = TimeUtil.getTokenTime();
                                    if (tokenTime) {

                                    } else {
                                        mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                        editCommtent1.setText("");
                                        hideInput();
                                        mPopupWindow1.dismiss();
                                    }
                                } else {
                                    Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        showSoft(editCommtent1);
                        editCommtent1.setOnKeyListener(new View.OnKeyListener() {
                            @Override
                            public boolean onKey(View v, int keyCode, KeyEvent event) {
                                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                    //((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                                    if (editCommtent1.getText().toString().length() != 0) {
                                        String s = editCommtent1.getText().toString();
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("articleId", articleId);
                                        map.put("summary", s);
                                        boolean tokenTime = TimeUtil.getTokenTime();
                                        if (tokenTime) {

                                        } else {
                                            mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                            editCommtent1.setText("");
                                            hideInput();
                                            mPopupWindow1.dismiss();
                                        }
                                    } else {
                                        Toast.makeText(mActivity, "请输入内容！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                return false;
                            }
                        });*/
                        Intent intent = new Intent(RecDataActivity.this,ReportPageActivity.class);
                        intent.putExtra("articleId",mArticleId);
                        startActivity(intent);
                        break;
                    case 2:
                        mPresenter.getDataP1(discussId, DifferentiateEnum.DELETECOMMENT, loadingLayout);
                        break;
                }
            }
        }, names);
    }

}
