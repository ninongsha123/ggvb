package com.tiancaijiazu.app.activitys.issue;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
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

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.ParentingEncyclopediaactivitys.UserCenterActivity;
import com.tiancaijiazu.app.adapters.DiscussDataAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CommentBean;
import com.tiancaijiazu.app.beans.DeleArticelBean;
import com.tiancaijiazu.app.beans.OneCommentBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.status.NineGridTestLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupDeitlsActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv_finid)
    ImageView mIvFinid;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.rls)
    RelativeLayout mRls;
    @BindView(R.id.img)
    CircleImageView mImg;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.age)
    TextView mAge;
    @BindView(R.id.dian)
    ImageView mDian;
    @BindView(R.id.tre)
    RelativeLayout mTre;
    @BindView(R.id.rlv_data)
    NineGridTestLayout mRlvData;
    @BindView(R.id.txt)
    TextView mTxt;
    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.zanshu)
    TextView mZanshu;
    @BindView(R.id.sf)
    RelativeLayout mSf;
    @BindView(R.id.vs)
    View mVs;
    @BindView(R.id.comment)
    TextView mComment;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv_discuss)
    ImageView mIvDiscuss;
    @BindView(R.id.line)
    RelativeLayout mLine;
    @BindView(R.id.edit_comm)
    EditText mEditComm;
    @BindView(R.id.rel)
    RelativeLayout mRel;
    private Intent mIntent;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;

    private ArrayList<String> mUrl = new ArrayList<>();
    private long mArticleId;
    private String mNickname;
    private String mDetail;
    private int mDiscuss;
    private String mUserAvatar;
    private String mId;
    private long mUserId;
    private String mKeyTime;
    private int mLikes;
    private String mPicUri;
    private int page=1;
    private DiscussDataAdapter mAdapter;

    @Override
    protected void initEventAndData() {
        mUrl.clear();
        mId = PreUtils.getString("userId", "");
        mIntent = getIntent();
        mUserId = mIntent.getLongExtra("userId", 0);
        mArticleId = mIntent.getLongExtra("articleId", 0);
        mNickname = mIntent.getStringExtra("nickname");
        mKeyTime = mIntent.getStringExtra("keyTime");
        mPicUri = mIntent.getStringExtra("coverPics");
        mUserAvatar = mIntent.getStringExtra("userAvatar");
        mDetail = mIntent.getStringExtra("detail");
        mDiscuss = mIntent.getIntExtra("discuss", 0);
        mLikes = mIntent.getIntExtra("likes", 0);


        HashMap<String, Object> map = new HashMap<>();
        map.put("articleId", mArticleId);
        map.put("page", page);
        mPresenter.getDataP(map, DifferentiateEnum.ONECOMMENTLISTS);

        initView();
        initRlv();
        mIvFinid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mDian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(view);
            }
        });

        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GroupDeitlsActivity.this, UserCenterActivity.class);
                intent.putExtra("userId", mUserId);
                startActivity(intent);
            }
        });
    }

    private void initRlv() {
        ArrayList<OneCommentBean.ResultBean> oneCommentBeans = new ArrayList<>();
        mAdapter = new DiscussDataAdapter(this,oneCommentBeans);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mRlv.setAdapter(mAdapter);
    }

    private void initView() {
       String birthday = mIntent.getStringExtra("birthday");
        if (mDiscuss != 0) {
            mIvDiscuss.setVisibility(View.GONE);
        } else {
            mIvDiscuss.setVisibility(View.VISIBLE);
        }
        //用户头像
        Glide.with(GroupDeitlsActivity.this).load(mUserAvatar).into(mImg);
        mName.setText(mNickname);
        mTxt.setText(mDetail);
        long l = Long.parseLong(mId);
        if (l == mUserId) {
            String bage = PreUtils.getString("bage", "");
            mAge.setText(bage);
            mDian.setVisibility(View.VISIBLE);
        } else {
            String nowTime = TimeUtil.getNowTime();
            if (birthday.equals("")){
                mAge.setText("他还没有添加宝宝");
            }else {
                String age = TimeUtil.getAge(birthday, nowTime);
                mAge.setText("宝宝" + age);
            }
            mDian.setVisibility(View.GONE);
        }
        //发布时间
        mDate.setText(mKeyTime);

        //发布图片集合
        if (!mPicUri.equals("")) {
            String[] split = mPicUri.split("[|]");
            if (split.length>1) {
                for (int j = 0; j < split.length; j++) {
                    mUrl.add(split[j]);
                }
            }else {
                mUrl.add(mPicUri);
            }
        }
        mRlvData.setUrlList(mUrl);

        //点赞数
        mZanshu.setText(mLikes + "");
        //评论数
        mComment.setText("共 " + mDiscuss + " 条评论");
    }

    @OnClick({R.id.rel, R.id.scrov})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel:
                String s = mEditComm.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(mActivity, "请输入内容!", Toast.LENGTH_SHORT).show();
                } else {
                    hideInput();
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("article", mArticleId);
                    map.put("content", s);
                    map.put("replyId", "");
                    mPresenter.getDataP(map, DifferentiateEnum.COMMENT);
                    mEditComm.setText("");
                }
                break;
            case R.id.scrov:
                hideInput();
                break;
        }
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
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (GroupDeitlsActivity.this.getCurrentFocus() != null) {
                if (GroupDeitlsActivity.this.getCurrentFocus().getWindowToken() != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(GroupDeitlsActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    mEditComm.setCursorVisible(false);// 内容清空后将编辑框1的光标隐藏，提升用户的体验度
                }
            }
        }
        return super.onTouchEvent(event);
    }


    private void showPop(View view) {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_black, null);
        mPopupWindow = new PopupWindow(mInflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
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
        TextView who = mInflate.findViewById(R.id.whok);
        TextView delete = mInflate.findViewById(R.id.deletes);
        who.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f; //0.0-1.0
                getWindow().setAttributes(lp);
                pop();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDataP(mArticleId+"", DifferentiateEnum.DELEARTICEL);
                setResult(205);
                finish();
            }
        });
        mPopupWindow.showAsDropDown(view, 0, -85, Gravity.CENTER);
    }

    private void pop() {
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
//                String s = quans.getText().toString();
                mPopupWindow1.dismiss();
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = friends.getText().toString();
                mPopupWindow1.dismiss();
            }
        });
        just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = justs.getText().toString();
                mPopupWindow1.dismiss();
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
        return R.layout.activity_group_deitls;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DELEARTICEL:
                DeleArticelBean deleArticelBean = (DeleArticelBean) o;
                if (deleArticelBean.getCode().equals("0")) {
                    Toast.makeText(mActivity, "删除成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, deleArticelBean.getResult(), Toast.LENGTH_SHORT).show();
                }
                break;
            case ONECOMMENTLISTS:
                OneCommentBean oneCommentBean = (OneCommentBean) o;
                List<OneCommentBean.ResultBean> result = oneCommentBean.getResult();
                mAdapter.addData(result);
                break;
            case COMMENT:
                CommentBean commentBean = (CommentBean) o;
                if (commentBean.getCode().equals("0")){
                    Toast.makeText(mActivity, "发表成功", Toast.LENGTH_SHORT).show();
                    mIvDiscuss.setVisibility(View.GONE);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("articleId", mArticleId);
                    map.put("page", page);
                    mPresenter.getDataP(map, DifferentiateEnum.ONECOMMENTLISTS);
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
