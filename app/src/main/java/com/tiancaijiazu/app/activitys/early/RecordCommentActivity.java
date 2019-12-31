package com.tiancaijiazu.app.activitys.early;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.LikeListActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.ReportPageActivity;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_like_user;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_record_comment;
import com.tiancaijiazu.app.activitys.early.adapters.RlvAdapter_record_two;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.activitys.shopping_activity.LookPhotoActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ArticleDatas;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.ArticleReportBeans;
import com.tiancaijiazu.app.beans.CommentBean;
import com.tiancaijiazu.app.beans.ConcernBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.beans.LikeListsBean;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RecordCommentActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.iv_enter)
    ImageView iv_enter;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.recyler)
    RecyclerView mRecyler;
    @BindView(R.id.recylerView_comment)
    RecyclerView mRecylerViewComment;
    @BindView(R.id.avatar)
    CircleImageView mAvatar;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.detail)
    TextView mDetail;
    @BindView(R.id.publishTime)
    TextView mPublishTime;
    @BindView(R.id.discussListsize)
    TextView mDiscussListsize;
    @BindView(R.id.edit_comm)
    EditText mEditComm;
    @BindView(R.id.checkbox_shou_chang)
    CheckBox mCheckboxShouChang;
    @BindView(R.id.record_concern)
    CheckBox record_concern;
    @BindView(R.id.collect_sum)
    TextView mCollectSum;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.discusssize)
    TextView mDiscusssize;
    @BindView(R.id.sum_like)
    TextView mSumLike;
    @BindView(R.id.baby_age)
    TextView mBabyAge;
    @BindView(R.id.more_dian)
    ImageView mMoreDian;
    private RlvAdapter_record_two rlvAdapterRecordTwo;
    private int page = 1;
    private RlvAdapter_like_user mRlvAdapterLikeUser;
    private String userId1 = "";
    private RlvAdapter_record_comment mRlvAdapterRecordComment;
    private PopupWindow mPopupWindow;
    private long mId;
    private ArticleDatas.ResultBean mResult;
    private List<LikeListsBean.ResultBean> result1;
    private String userId;
    private int discuss;
    private int sum;
    private PopupWindow mPopupWindow1;
    private long mUserId;
    private boolean isbo;
    private Intent mIntent;
    private int mPosition;
    private int mLikes;

    @Override
    protected void initEventAndData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mIntent = getIntent();
        String biao = mIntent.getStringExtra("biao");
        userId = PreUtils.getString("userId", "");
        mPosition = mIntent.getIntExtra("position", 0);
        if ("one".equals(biao)) {
            ArticleLists.ResultBean resultBean = (ArticleLists.ResultBean) mIntent.getSerializableExtra("data");
            Glide.with(this).load(resultBean.getUserAvatar()).into(mAvatar);
            mNickname.setText(resultBean.getNickname());
            String publishTime = resultBean.getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            mPublishTime.setText(s);
            userId1 = resultBean.getUserId() + "";
            mUserId = resultBean.getUserId();
            if (this.userId.equals(userId1)) {
                record_concern.setVisibility(View.GONE);
            }
            String nowTime = TimeUtil.getNowTime();
            String babyBirthday = resultBean.getBabyBirthday();
            String age = TimeUtil.getAge(babyBirthday, nowTime);
            mBabyAge.setText("宝宝 " + age);
        } else if ("two".equals(biao)) {
            MyReleasedListBean.ResultBean resultBean = (MyReleasedListBean.ResultBean) mIntent.getSerializableExtra("data");
            Glide.with(this).load(resultBean.getUserAvatar()).into(mAvatar);
            mNickname.setText(resultBean.getNickname());
            String publishTime = resultBean.getPublishTime();
            long l = TimeUtil.dataOne(publishTime);
            String s = TimeUtil.QQFormatTime(l);
            mPublishTime.setText(s);
            userId1 = resultBean.getUserId() + "";
            if (userId.equals(userId1)) {
                record_concern.setVisibility(View.GONE);
            }
            String nowTime = TimeUtil.getNowTime();
            String babyBirthday = resultBean.getBabyBirthday();
            String age = TimeUtil.getAge(babyBirthday, nowTime);
            Log.i("yx1456", babyBirthday + "initEven" + userId + "tAndData: " + age);
            mBabyAge.setText("宝宝 " + age);
        }
        mId = mIntent.getLongExtra("id", 0);
        Log.d("bjg", "initEventAndData: " + mId);
        initRecylerViewComment();
        initRecyler();
        initRecylerView();
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", mId);
        map.put("page", 1);
        mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
        mPresenter.getDataP1(mId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);

        record_concern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDataP1(userId1 + "", DifferentiateEnum.CONCERN, loadingLayout);
            }
        });
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
                mPresenter.getDataP1(mId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                mPresenter.getDataP1(userId1 + "", DifferentiateEnum.CONCERN, loadingLayout);
            }
        });
        setEditTextHintSize(mEditComm, "说点什么...", 13);
        mEditComm.setInputType(InputType.TYPE_NULL);
        ScreenStatusUtil.setFillDip(this);
        mEditComm.setFocusable(false);
        mEditComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void popWindow() {
        View inflate = LayoutInflater.from(RecordCommentActivity.this).inflate(R.layout.edit_comment, null);
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
        mPopupWindow.showAtLocation(mAvatar, Gravity.BOTTOM, 0, 0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                hideInput();
            }
        });
        EditText editCommtent = inflate.findViewById(R.id.edit_comment);
        TextView faSong = inflate.findViewById(R.id.fa_song);
        TextView config_hidden = inflate.findViewById(R.id.config_hidden);
        config_hidden.requestFocus();
        faSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                if (editCommtent.getText().toString().length() != 0) {
                    String s = editCommtent.getText().toString();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("article", mId);
                    map.put("content", s);
                    map.put("replyId", "");
                    boolean tokenTime = TimeUtil.getTokenTime();
                    if (tokenTime) {

                    } else {
                        mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                            }
                        });
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
                        map.put("article", mId);
                        map.put("content", s);
                        map.put("replyId", "");
                        boolean tokenTime = TimeUtil.getTokenTime();
                        if (tokenTime) {

                        } else {
                            mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                @Override
                                public void onReload(View v) {
                                    mPresenter.getDataP1(map, DifferentiateEnum.COMMENT, loadingLayout);
                                }
                            });
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

    private void initRecylerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecylerView.setLayoutManager(gridLayoutManager);
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> strings1 = new ArrayList<>();
        rlvAdapterRecordTwo = new RlvAdapter_record_two(strings, strings1);
        mRecylerView.setAdapter(rlvAdapterRecordTwo);
        rlvAdapterRecordTwo.setOnClickLisiterImage(new RlvAdapter_record_two.onClickLisiterImage() {
            @Override
            public void onClickerImage(View view, int position, ArrayList<String> mImage1) {
                //查看大图
                Intent intent = new Intent(RecordCommentActivity.this, LookPhotoActivity.class);
                intent.putStringArrayListExtra("images", mImage1);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    private void initRecyler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyler.setLayoutManager(linearLayoutManager);
        List<LikeListsBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterLikeUser = new RlvAdapter_like_user(resultBeans, this);
        mRecyler.setAdapter(mRlvAdapterLikeUser);
        mRlvAdapterLikeUser.setOnItemClickListener(new RlvAdapter_like_user.setOnItemClick() {
            @Override
            public void setOnItemClickListener(View v, int position, List<LikeListsBean.ResultBean> mData) {
                Intent intent1 = new Intent(RecordCommentActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", mData.get(position).getUserId());
                startActivity(intent1);
            }
        });
    }

    private void initRecylerViewComment() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerViewComment.setLayoutManager(linearLayoutManager);
        List<OneCommentBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterRecordComment = new RlvAdapter_record_comment(resultBeans, this, discuss);
        mRecylerViewComment.setAdapter(mRlvAdapterRecordComment);
        mRlvAdapterRecordComment.setOnClickLisiterUserOne(new RlvAdapter_record_comment.onClickLisiterCirUser() {
            @Override
            public void onClickerUserOne(View view, int position, List<OneCommentBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                long userId1 = mData.get(position).getUserId();
                Intent intent1 = new Intent(RecordCommentActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", userId1);
                startActivity(intent1);
            }
        });
        mRlvAdapterRecordComment.setOnClickLisiterItem(new RlvAdapter_record_comment.onClickLisiterItem() {
            @Override
            public void onClickerItem(View view, int position, List<OneCommentBean.ResultBean> mData) {
                showReport(view, mData, position);
            }
        });
        mRlvAdapterRecordComment.setOnClickLisiterDi(new RlvAdapter_record_comment.onClickLisiterDi() {
            @Override
            public void onClickDi(View view, int pag) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                page++;
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mId);
                map.put("page", page);
                mPresenter.getDataP1(map, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                    }
                });
            }
        });
    }

    private void showReport(View view, List<OneCommentBean.ResultBean> mData, int position) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        long userId = mData.get(position).getUserId();
        List<String> names = new ArrayList<>();
        String s = String.valueOf(mId);
        if (s != null) {
            if (s.equals("" + userId)) {
                names.add("举报");
            } else {
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
                        Intent intent = new Intent(RecordCommentActivity.this, ReportPageActivity.class);
                        intent.putExtra("articleId",mId);
                        startActivity(intent);
                        /*View inflate1 = LayoutInflater.from(RecordCommentActivity.this).inflate(R.layout.edit_comment, null);
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
                        mPopupWindow1.showAtLocation(mAvatar, Gravity.BOTTOM, 0, 0);
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
                        TextView config_hidden = inflate1.findViewById(R.id.config_hidden);
                        config_hidden.requestFocus();
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
                                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                            @Override
                                            public void onReload(View v) {
                                                mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                            }
                                        });
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
                                            loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                                                @Override
                                                public void onReload(View v) {
                                                    mPresenter.getDataP1(map, DifferentiateEnum.ARYICLEREPORT, loadingLayout);
                                                }
                                            });
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
                        break;
                }
            }
        }, names);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_record_comment;
    }

    @Override
    public void showError(String error) {

    }


    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ARTICLEDATAS:
                ArticleDatas articleDatas = (ArticleDatas) o;
                ArticleDatas.ResultBean result = articleDatas.getResult();
                mDetail.setText(result.getDetail());
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("articleId", mId);
                map2.put("page", page);
                mPresenter.getDataP1(map2, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map2, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                    }
                });
                int isFollow = result.getIsFollow();
                if (isFollow == 0) {
                    record_concern.setChecked(false);
                } else if (isFollow == 1) {
                    record_concern.setChecked(true);
                }
                mLikes = result.getLikes();
                if (mLikes != 0) {
                    mCollectSum.setText(mLikes + "");
                } else {
                    mCollectSum.setText("");
                }
                int isLikes = result.getIsLikes();
                if (isLikes == 0) {
                    mCheckboxShouChang.setChecked(false);
                } else {
                    mCheckboxShouChang.setChecked(true);
                }
                mCheckboxShouChang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = mCheckboxShouChang.isChecked();
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("articleId", mId);
                        map.put("contentType", "1");
                        mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadingLayout);
                        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                            @Override
                            public void onReload(View v) {
                                mPresenter.getDataP1(map, DifferentiateEnum.GIVEALIKE, loadingLayout);
                            }
                        });
                        if (checked) {

                            result.setLikes(result.getLikes() + 1);
                            result.setIsLikes(1);
                            mCollectSum.setText(result.getLikes() + "");
                        } else {

                            result.setLikes(result.getLikes() - 1);
                            result.setIsLikes(0);
                            if (result.getLikes() != 0) {
                                mCollectSum.setText(result.getLikes() + "");
                            } else {
                                mCollectSum.setText("");
                            }
                        }
                    }
                });
                String coverPics = result.getCoverPics();
                String largePics = result.getLargePics();
                String[] split = coverPics.split("[|]");
                String[] split1 = largePics.split("[|]");
                ArrayList<String> strings = new ArrayList<>();
                ArrayList<String> strings1 = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    strings.add(split[i]);
                }
                for (int i = 0; i < split1.length; i++) {
                    strings1.add(split1[i]);
                }
                rlvAdapterRecordTwo.addData(strings, strings1);
                discuss = result.getDiscuss();
                mDiscussListsize.setText("共 " + discuss + "" + " 条评论");
                mDiscusssize.setText(discuss + "");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                mRecylerViewComment.setLayoutManager(linearLayoutManager);

                break;
            case LIKELISTS:
                LikeListsBean likeListsBean = (LikeListsBean) o;
                result1 = likeListsBean.getResult();
                int size = result1.size();
                mRlvAdapterLikeUser.addData(result1);
                if (size != 0) {
                    mSumLike.setText(size + "赞");
                } else {
                    mSumLike.setText("赞");
                }
                break;
            case CONCERN:
                ConcernBean concernBean = (ConcernBean) o;
                String result2 = concernBean.getResult();
                Toast.makeText(mActivity, result2, Toast.LENGTH_SHORT).show();
                break;
            case ONECOMMENTLISTS:
                OneCommentBean oneCommentBean = (OneCommentBean) o;
                List<OneCommentBean.ResultBean> result3 = oneCommentBean.getResult();
                if (discuss >= mRlvAdapterRecordComment.mData.size()) {
                    isbo = true;
                } else {
                    isbo = false;
                }
                if (page == 1) {
                    mRlvAdapterRecordComment.addData(result3, discuss, isbo, true);
                } else {
                    mRlvAdapterRecordComment.addData(result3, discuss, isbo, false);
                }
                break;
            case COMMENT:
                CommentBean commentBean = (CommentBean) o;
                mRlvAdapterRecordComment.mData.clear();
                page = 1;
                mPresenter.getDataP1(mId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("articleId", mId);
                map1.put("page", page);
                mPresenter.getDataP1(map1, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map1, DifferentiateEnum.ONECOMMENTLISTS, loadingLayout);
                        mPresenter.getDataP1(mId, DifferentiateEnum.ARTICLEDATAS, loadingLayout);
                    }
                });
                Toast.makeText(mActivity, "" + commentBean.getResult(), Toast.LENGTH_SHORT).show();
                break;
            case ARYICLEREPORT:
                hideInput();
                ArticleReportBeans articleReportBeans = (ArticleReportBeans) o;
                String result1 = articleReportBeans.getResult();
                if (result1.equalsIgnoreCase("提交成功")) {
                    Toast.makeText(RecordCommentActivity.this, result1, Toast.LENGTH_SHORT).show();
                }
                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                String result4 = likeBean.getResult();
                Toast.makeText(RecordCommentActivity.this, result4, Toast.LENGTH_SHORT).show();
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", mId);
                map.put("page", 1);
                mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
                loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        mPresenter.getDataP1(map, DifferentiateEnum.LIKELISTS, loadingLayout);
                    }
                });
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean checked = mCheckboxShouChang.isChecked();
        String s = mCollectSum.getText().toString();
        mIntent.putExtra("position",mPosition);
        mIntent.putExtra("checked",checked);
        mIntent.putExtra("likes",s);
        mIntent.putExtra("discuss",discuss);
        setResult(17, mIntent);
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.iv_finis, R.id.title_name, R.id.sum_like, R.id.iv_enter, R.id.avatar,R.id.more_dian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                boolean checked = mCheckboxShouChang.isChecked();
                String s = mCollectSum.getText().toString();
                mIntent.putExtra("position",mPosition);
                mIntent.putExtra("checked",checked);
                mIntent.putExtra("likes",s);
                mIntent.putExtra("discuss",discuss);
                setResult(17, mIntent);
                finish();
                break;
            case R.id.title_name:
                break;
            case R.id.avatar:
                Intent intent1 = new Intent(RecordCommentActivity.this, UserCenterActivity.class);
                intent1.putExtra("userId", mUserId);
                startActivity(intent1);
                break;
            case R.id.sum_like:
            case R.id.iv_enter:
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                int size = result1.size();
                if (size == 0) {
                    Toast.makeText(mActivity, "暂无点赞！", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, LikeListActivity.class);
                    intent.putExtra("artivle", mId);
                    startActivity(intent);
                }
                break;
            case R.id.more_dian:
                List<String> names = new ArrayList<>();
                names.add("举报");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                Intent intent = new Intent(RecordCommentActivity.this,ReportPageActivity.class);
                                intent.putExtra("articleId",mId);
                                startActivity(intent);
                                break;
                            case 1:
                                break;
                        }
                    }
                },names);
                break;
        }
    }


    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
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

    private void showSoft(EditText editCommtent) {
        InputMethodManager inputMethodManager = (InputMethodManager) editCommtent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
