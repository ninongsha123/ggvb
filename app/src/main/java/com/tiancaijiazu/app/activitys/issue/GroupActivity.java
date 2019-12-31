package com.tiancaijiazu.app.activitys.issue;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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
import com.tiancaijiazu.app.activitys.issue.adapters.RlvAdapter_group_zhu;
import com.tiancaijiazu.app.adapters.ImageAdapter;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ArticleReportBeans;
import com.tiancaijiazu.app.beans.CommentBean;
import com.tiancaijiazu.app.beans.DeleArticelBean;
import com.tiancaijiazu.app.beans.DiaryDetailBean;
import com.tiancaijiazu.app.beans.LikeBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的日记详情
public class GroupActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finid)
    ImageView mIvFinid;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.rls)
    RelativeLayout mRls;
    @BindView(R.id.vs)
    View mVs;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.yue)
    TextView mYue;
    @BindView(R.id.rg)
    RelativeLayout mRg;
    @BindView(R.id.age)
    TextView mAge;
    @BindView(R.id.ijia)
    ImageView mIjia;
    @BindView(R.id.lines)
    RelativeLayout mLines;
    @BindView(R.id.rlv_img)
    RecyclerView mRlvImg;
    @BindView(R.id.lie)
    RelativeLayout mLie;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindows;
    private PopupWindow mPopupWindow1;
    private ImageAdapter mImageAdapter;
    private ArrayList<String> mList;
    private long mUserId;
    private int page = 1;
    private RlvAdapter_group_zhu mRlvAdapter_group_zhu;
    private long mArticleId1;
    private Intent mIntent;
    private List<DiaryDetailBean.ResultBean> mResult;
    private int pingP;


    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void initEventAndData() {
        mIntent = getIntent();
        String nowTime = TimeUtil.getNowTime();
        String yue = nowTime.substring(5, 7);
        String date = nowTime.substring(8, 10);
        mDate.setText(date);
        mYue.setText(yue+"月");
        mUserId = mIntent.getLongExtra("userId", 0);
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
        ArrayList<DiaryDetailBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvImg.setLayoutManager(new LinearLayoutManager(GroupActivity.this));
        mRlvAdapter_group_zhu = new RlvAdapter_group_zhu(this, resultBeans);
        mRlvImg.setAdapter(mRlvAdapter_group_zhu);

        mLines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(GroupActivity.this, MOPP1Activity.class),110);
            }
        });

        mRlvAdapter_group_zhu.setOnClickLisiterDian(new RlvAdapter_group_zhu.onClickLisiterDian() {
            @Override
            public void onClickerDian(View view, int p, ArrayList<DiaryDetailBean.ResultBean> mData) {
                showPop(view, mData.get(p).getArticleId() + "");
            }
        });
        mRlvAdapter_group_zhu.setOnClickLisiterDianZan(new RlvAdapter_group_zhu.onClickLisiterDianZan() {
            @Override
            public void onClickerDianZan(View view, int position, CheckBox mIvCollect, ArrayList<DiaryDetailBean.ResultBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.size() != 0) {
                    mArticleId1 = mData.get(position).getArticleId();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", mArticleId1);
                    map.put("contentType", "1");
                    mPresenter.getDataP(map, DifferentiateEnum.GIVEALIKE);
                }
            }
        });

        mRlvAdapter_group_zhu.setOnClickLisiterPing(new RlvAdapter_group_zhu.onClickLisiterPing() {
            @Override
            public void onClickerPing(int p, List<DiaryDetailBean.ResultBean> data) {
                pingP=p;
                popWindow(data.get(p).getArticleId());
            }
        });

        mRlvAdapter_group_zhu.setOnClickLisiter(new RlvAdapter_group_zhu.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<DiaryDetailBean.ResultBean> mData) {
                Intent intent = new Intent(GroupActivity.this, GroupDeitlsActivity.class);
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
                startActivityForResult(intent,105);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
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

    @SuppressLint("WrongConstant")
    private void popWindow(long articleId) {
        View inflate = LayoutInflater.from(GroupActivity.this).inflate(R.layout.edit_comment, null);
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

    private void showPop(View view, String articleId) {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_black, null);
        mPopupWindows = new PopupWindow(mInflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindows.setFocusable(true);// 取得焦点
        mPopupWindows.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindows.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindows.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindows.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindows.setAnimationStyle(R.style.popwin_anim_style);
        TextView who = mInflate.findViewById(R.id.whok);
        TextView delete = mInflate.findViewById(R.id.deletes);
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindows.dismiss();
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f; //0.0-1.0
                getWindow().setAttributes(lp);
                pop(articleId);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDataP(articleId, DifferentiateEnum.DELEARTICEL);
                mPopupWindows.dismiss();
            }
        });
        mPopupWindows.showAsDropDown(view, 0, -80, Gravity.CENTER);
    }

    private int display = 0;
    private void pop(String articleId) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_who_select, null);
        mPopupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        RelativeLayout quan = inflate.findViewById(R.id.quan);
        RelativeLayout friend = inflate.findViewById(R.id.friend);
        RelativeLayout just = inflate.findViewById(R.id.just);

        RelativeLayout guan = inflate.findViewById(R.id.guan);

        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1; //0.0-1.0
                getWindow().setAttributes(lp);
            }
        });

        quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display = 0;
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                map.put("display", display);
                mPresenter.getDataP(map, DifferentiateEnum.MODIFIER);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display = 1;
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                map.put("display", display);
                mPresenter.getDataP(map, DifferentiateEnum.MODIFIER);
            }
        });
        just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display = 2;
                HashMap<String, Object> map = new HashMap<>();
                map.put("articleId", articleId);
                map.put("display", display);
                mPresenter.getDataP(map, DifferentiateEnum.MODIFIER);
            }
        });
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });

        mPopupWindow1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }
    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_group;
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case MODIFIER:
                ArticleReportBeans articleReportBeans = (ArticleReportBeans) o;
                if (articleReportBeans.getCode().equals("0")) {
                    Toast.makeText(mActivity, "修改成功", Toast.LENGTH_SHORT).show();
                    mPopupWindow1.dismiss();
                } else {
                    Toast.makeText(mActivity, articleReportBeans.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
            case DIARYDEITL:
                DiaryDetailBean diaryDetailBean = (DiaryDetailBean) o;
                mResult = diaryDetailBean.getResult();
                if (mResult.size()>0) {
                    String nowTime = TimeUtil.getNowTime();
                    String age = TimeUtil.getAge(mResult.get(0).getBabyBirthday(), nowTime);
//                    String bage = PreUtils.getString("bage", "");
                    mAge.setText("宝宝" + age);
                    if (diaryDetailBean.getCode().equals("0")) {
                        mRefreshLayout.finishLoadMore(true);
                        mRefreshLayout.finishRefresh(true);
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
                }else {
                    mRefreshLayout.finishLoadMore(true);
                    mRlvAdapter_group_zhu.notifyDataSetChanged();
                }
                break;
            case DELEARTICEL:
                DeleArticelBean deleArticelBean = (DeleArticelBean) o;
                if (deleArticelBean.getCode().equals("0")) {
                    Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("userId", mUserId);
                    map.put("page", page);
                    mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
                } else {
                    Toast.makeText(mActivity, deleArticelBean.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
            case COMMENT:
                CommentBean commentBean = (CommentBean) o;
                if (commentBean.getCode().equals("0")) {
                    Toast.makeText(mActivity, "发表成功", Toast.LENGTH_SHORT).show();
                    int discuss = mResult.get(pingP).getDiscuss();
                    mResult.get(pingP).setDiscuss(discuss+1);
                    mRlvAdapter_group_zhu.addData(mResult,true);
                    hideInput();
                } else {
                    Toast.makeText(mActivity, commentBean.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
            case GIVEALIKE:
                LikeBean likeBean = (LikeBean) o;
                Toast.makeText(mActivity, likeBean.getResult(), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==110&&resultCode==113){
            page=1;
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", mUserId);
            map.put("page", page);
            mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
        }else if (requestCode==105&&resultCode==205){
            page=1;
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", mUserId);
            map.put("page", page);
            mPresenter.getDataP(map, DifferentiateEnum.DIARYDEITL);
        }
    }
}
