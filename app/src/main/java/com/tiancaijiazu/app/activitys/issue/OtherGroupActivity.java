package com.tiancaijiazu.app.activitys.issue;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.adapters.RlvAdapter_group_other;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CommentBean;
import com.tiancaijiazu.app.beans.DiaryDetailBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//其他人日记详情
public class OtherGroupActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finid)
    ImageView mIvFinid;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.rls)
    RelativeLayout mRls;
    @BindView(R.id.rlv_img)
    RecyclerView mRlvImg;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private PopupWindow mPopupWindow;
    private long mArticleId;
    private long mUserId;
    private int page = 1;
    private int pingP = 1;
    private RlvAdapter_group_other mRlvAdapter_group_zhu;
    private List<DiaryDetailBean.ResultBean> mResult;

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        mArticleId = intent.getLongExtra("articleId", 0);
        mUserId = intent.getLongExtra("userId", 0);
        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", mUserId);
        map.put("page", page);
        mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
        mIvFinid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initRlv();

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", mUserId);
                map.put("page", page);
                mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);

            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                HashMap<String, Object> map = new HashMap<>();
                map.put("userId", mUserId);
                map.put("page", page);
                mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
            }
        });

    }

    private void initRlv() {
        mRlvImg.setLayoutManager(new LinearLayoutManager(OtherGroupActivity.this));
        ArrayList<DiaryDetailBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapter_group_zhu = new RlvAdapter_group_other(this, resultBeans);
        mRlvImg.setAdapter(mRlvAdapter_group_zhu);

        mRlvAdapter_group_zhu.setOnClickLisiterDianZan(new RlvAdapter_group_other.onClickLisiterDianZan() {
            @Override
            public void onClickerDianZan(View view, int position, CheckBox mIvCollect, ArrayList<DiaryDetailBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.size() != 0) {
                    long articleId = mData.get(position).getArticleId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("contentType", "1");
                    mPresenter.getDataP(map, DifferentiateEnum.GIVEALIKE);
                }
            }
        });

        mRlvAdapter_group_zhu.setOnClickLisiterPing(new RlvAdapter_group_other.onClickLisiterPing() {
            @Override
            public void onClickerPing(int p, ArrayList<DiaryDetailBean.ResultBean> mData) {
                pingP = p;
                popWindow(mData.get(p).getArticleId());
            }
        });
        mRlvAdapter_group_zhu.setOnClickLisiter(new RlvAdapter_group_other.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<DiaryDetailBean.ResultBean> mData) {
                Intent intent = new Intent(OtherGroupActivity.this, GroupDeitlsActivity.class);
                DiaryDetailBean.ResultBean bean = mData.get(position);
                intent.putExtra("articleId", mData.get(position).getArticleId());
                intent.putExtra("userId", mData.get(position).getUserId());
                intent.putExtra("userAvatar", bean.getUserAvatar());
                intent.putExtra("coverPics", bean.getLargePics());
                intent.putExtra("keyTime", bean.getKeyTime());
                intent.putExtra("nickname", bean.getNickname());
                intent.putExtra("detail", bean.getDetail());
                intent.putExtra("discuss", bean.getDiscuss());
                intent.putExtra("likes", bean.getLikes());
                intent.putExtra("birthday", bean.getBabyBirthday());
                startActivity(intent);
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void popWindow(long articleId) {
        View inflate = LayoutInflater.from(OtherGroupActivity.this).inflate(R.layout.edit_comment, null);
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
        mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
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
                    map.put("article", articleId);
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
                        map.put("article", articleId);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void showSoft(EditText editCommtent) {
        InputMethodManager inputMethodManager = (InputMethodManager) editCommtent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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


    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_other_group;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DIARYDEITL:
                DiaryDetailBean diaryDetailBean = (DiaryDetailBean) o;
                mResult = diaryDetailBean.getResult();
                if (mResult.size() > 0) {
                    if (diaryDetailBean.getCode().equals("0")) {
                        mRefreshLayout.finishRefresh(true);
                        mRefreshLayout.finishLoadMore(true);
                        if (mResult.size() != 0) {
                            if (page == 1) {
                                mRlvAdapter_group_zhu.addData(mResult, true);
                            } else {
                                mRlvAdapter_group_zhu.addData(mResult, false);
                            }
                        } else {
                            mRlvAdapter_group_zhu.addData(mResult, false);
                        }
                    } else {
                        Toast.makeText(mActivity, diaryDetailBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }else if (diaryDetailBean.getCode().equals("0")) {
                    mRefreshLayout.finishLoadMore(true);
                }
                break;
            case COMMENT:
                CommentBean commentBean = (CommentBean) o;
                if (commentBean.getCode().equals("0")) {
                    Toast.makeText(mActivity, "发表成功", Toast.LENGTH_SHORT).show();
                    int discuss = mResult.get(pingP).getDiscuss();
                    mResult.get(pingP).setDiscuss(discuss+1);
                    mRlvAdapter_group_zhu.addData(mResult,true);
                } else {
                    Toast.makeText(mActivity, commentBean.getResult(), Toast.LENGTH_SHORT).show();
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
