package com.tiancaijiazu.app.activitys.feedback;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.MemberFeedbackBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackCenterActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.submit)
    TextView mSubmit;
    @BindView(R.id.a)
    MediumBoldTextViewTitle a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.ed_feedback)
    EditText mEdFeedback;
    @BindView(R.id.ed_contact)
    EditText mEdContact;
    @BindView(R.id.text_sum)
    TextView mTextSum;
    private int num = 0;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        int length = mEdFeedback.getText().toString().length();
        if(length!=20){
            mTextSum.setText(length+"/1000");
        }else {
            mTextSum.setText(length+"/1000");
        }
        mEdFeedback.addTextChangedListener(new TextWatcher() {

            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                if(number==0){
                    mSubmit.setTextColor(Color.parseColor("#BBBBBB"));
                    mSubmit.setClickable(false);
                    mSubmit.setEnabled(false);
                }else {
                    mSubmit.setTextColor(Color.parseColor("#00DEFF"));
                    mSubmit.setClickable(true);
                    mSubmit.setEnabled(true);
                }
                if (number != 1000) {
                    mTextSum.setText(number + "/1000");
                } else {
                    mTextSum.setText(number + "/1000");
                }
                selectionStart = mEdFeedback.getSelectionStart();
                selectionEnd = mEdFeedback.getSelectionEnd();
                if (temp.length() > 1000) {
                    s.delete(selectionStart - 1, selectionEnd);
                    mEdFeedback.setInputType(InputType.TYPE_NULL); // 禁止输入（不弹出输入法）
                    int tempSelection = selectionEnd;
                    mEdFeedback.setText(s);
                    mEdFeedback.setSelection(tempSelection);//设置光标在最后
                    Toast.makeText(FeedbackCenterActivity.this, "最多输入1000个字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mEdContact.getText().toString();
                String s1 = mEdFeedback.getText().toString();
                if (s.length()==0&&s1.length()==0){
                    ToastUtils.showShortToast(FeedbackCenterActivity.this,"请填写反馈和联系方式");
                }else {
                    if (s.length()==0){
                        ToastUtils.showShortToast(FeedbackCenterActivity.this,"请填写联系方式");
                    }else if (s1.length()==0){
                        ToastUtils.showShortToast(FeedbackCenterActivity.this,"请填写反馈/建议");
                    }else {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("summary",s1);
                        map.put("contact",s);
                        mPresenter.getDataP(map,DifferentiateEnum.MEMBERFEEDBACK);
                    }
                }
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_feedback_center;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum){
            case MEMBERFEEDBACK:
                MemberFeedbackBeans memberFeedbackBeans= (MemberFeedbackBeans) o;
                String result = memberFeedbackBeans.getResult();
                if (result.equalsIgnoreCase("提交成功")){
                    Toast.makeText(mActivity, "提交成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }
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

    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
