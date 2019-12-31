package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ChangeGeqian;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;

/**
 *
 * 我的-编辑资料-个性签名
 */

public class GeqianActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    TextView ivFinis;
    @BindView(R.id.ok)
    TextView ok; @BindView(R.id.a)
    MediumBoldTextViewTitle a;
    @BindView(R.id.edit_geqian)
    EditText editGeqian;

    int num = 20;//限制的最大字数
    @BindView(R.id.number)
    TextView numbero;

    @Override
    protected void initEventAndData() {
        initClick();
        ScreenStatusUtil.setFillDip(this);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_geqian;
    }

    private void initClick() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String geqian = extras.getString("geqian");
        if (geqian.equals("未填写")) {
            //setEditTextHintSize(editGeqian, "有趣的个人介绍会吸引更多的粉丝哦", 17);
        } else {
            //setEditTextHintSize(editGeqian, geqian, 17);
            editGeqian.setText(geqian);
            editGeqian.setSelection(geqian.length());
        }

        ivFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editGeqian.getText() != null) {
                    Intent intent1 = new Intent();
                    intent1.putExtra("regeqian", editGeqian.getText().toString());
                    setResult(04, intent1);
                    String s = editGeqian.getText().toString();
                    mPresenter.getDataP(s, DifferentiateEnum.CHANGEGEQIAN);
                }
            }
        });
        int length = editGeqian.getText().toString().length();
        int i = num - length;
        if(i!=20){
            numbero.setText(i+"/20");
        }else {
            numbero.setText(i+"");
        }
        editGeqian.addTextChangedListener(new TextWatcher() {

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
                int number = num - s.length();
                if(number!=20){
                    numbero.setText(number+"/20");
                }else {
                    numbero.setText(number+"");
                }
                selectionStart = editGeqian.getSelectionStart();
                selectionEnd = editGeqian.getSelectionEnd();
                if (temp.length() > num) {
                    s.delete(selectionStart - 1, selectionEnd);
                    editGeqian.setInputType(InputType.TYPE_NULL); // 禁止输入（不弹出输入法）
                    int tempSelection = selectionEnd;
                    numbero.setText(s);
                    editGeqian.setSelection(tempSelection);//设置光标在最后
                    Toast.makeText(GeqianActivity.this, "最多输入二十个字！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NewApi")
    public static void setEditTextHintSize(EditText editText, String hintText, int size) {
        SpannableString ss = new SpannableString(hintText);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        editText.setHint(new SpannedString(ss));
    }



    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CHANGEGEQIAN:
                ChangeGeqian geqian = (ChangeGeqian) o;
                String result = geqian.getResult();
                String code = geqian.getCode();
                String msg = geqian.getMsg();
                if("0".equals(code)){
                    ToastUtils.showShortToast(GeqianActivity.this,result);
                    finish();
                }else {
                    ToastUtils.showShortToast(GeqianActivity.this,msg);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}
