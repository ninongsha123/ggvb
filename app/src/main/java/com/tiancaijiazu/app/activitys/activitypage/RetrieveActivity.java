package com.tiancaijiazu.app.activitys.activitypage;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_bg;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class RetrieveActivity extends SimpleActivity {

    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.code_edit)
    EditText mCodeEdit;
    @BindView(R.id.code_tv)
    TextView mCodeTv;
    @BindView(R.id.pass_edit)
    EditText mPassEdit;
    @BindView(R.id.line3)
    LinearLayout mLine3;
    @BindView(R.id.login)
    Button mLogin;
    private RlvAdapter_bg mRlvAdapterBg;
    private int size;
    @Override
    protected void initEventAndData() {
        setEditTextHintSize(mCodeEdit, "输入短信验证码", 16);
        setEditTextHintSize(mPassEdit, "输入密码", 16);
        ScreenStatusUtil.setFillDip(this);
        initRlv();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_retrieve;
    }
    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }
    private void initRlv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(layoutManager);
        mRlvAdapterBg = new RlvAdapter_bg();
        mRlv.setAdapter(mRlvAdapterBg);

        mCodeEdit.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            //当键盘弹出隐藏的时候会 调用此方法。
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //获取当前界面可视部分
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                //Log.d("Keyboard Size", "Size: " + heightDifference);

                if (heightDifference > 200) {
                    int hei = heightDifference - size;
                    mRlvAdapterBg.addUi(hei);
                    mRlv.scrollToPosition(mRlvAdapterBg.getItemCount() - 1);
                    mLine3.setVisibility(View.VISIBLE);
                    mLogin.setVisibility(View.VISIBLE);
//                    mEditPhone.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    size = heightDifference;
                    mRlvAdapterBg.addUi(0);
                }
            }
        });
    }



    @OnClick({R.id.iv_back, R.id.code_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.code_tv:
                /** 倒计时60秒，一次1秒 */
                CountDownTimer timer = new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        // TODO Auto-generated method stub
                        mCodeTv.setText( millisUntilFinished / 1000 + "s");
                        mCodeTv.setClickable(false);
                    }

                    @Override
                    public void onFinish() {
                        mCodeTv.setText("重新发送");
                        mCodeTv.setClickable(true);
                    }
                }.start();
                break;
        }
    }
}
