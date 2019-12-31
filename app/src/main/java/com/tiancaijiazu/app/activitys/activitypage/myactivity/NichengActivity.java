package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ChangeName;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  我的-设置-编辑资料-昵称-修改昵称
 *
 */

public class NichengActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    TextView ivFinis;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.edit_name)
    EditText editName;
    private String mBiao;
    private Intent mIntent;

    @Override
    protected void initEventAndData() {
        initClick();
        ScreenStatusUtil.setFillDip(this);
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_nicheng;
    }

    private void initClick() {
        mIntent = getIntent();
        mBiao = mIntent.getStringExtra("biao");
        if ("username".equals(mBiao)) {
            String name = mIntent.getStringExtra("name");
            //setEditTextHintSize(editName, name, 17);
            editName.setText(name);
            editName.setSelection(name.length());
        }else if("babyname".equals(mBiao)){
            String name = mIntent.getStringExtra("name");
            //setEditTextHintSize(editName, name, 17);
            editName.setText(name);
            editName.setSelection(name.length());
        }

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
            case CHANGENICHENG:
                ChangeName name = (ChangeName) o;
                String result = name.getResult();
                Log.i("yx555", "show: "+name);
                String code = name.getCode();
                String msg = name.getMsg();
                if("0".equals(code)){
                    ToastUtils.showShortToast(NichengActivity.this,result);
                    finish();
                }else {
                    ToastUtils.showShortToast(NichengActivity.this,msg);
                }

                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.iv_finis, R.id.ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.ok:
                if("username".equals(mBiao)){
                    if (editName.getText() != null) {
                        if (editName.getText().toString().length()>6){
                            Toast.makeText(mActivity, "宝宝小名最多六个字！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mIntent.putExtra("rename", editName.getText().toString());
                        setResult(02, mIntent);
                        String s = editName.getText().toString();
                        mPresenter.getDataP(s, DifferentiateEnum.CHANGENICHENG);
                        PreUtils.putString("userName", s);

                    }
                }else if("babyname".equals(mBiao)){
                    if (editName.getText().toString().length()>6){
                        Toast.makeText(mActivity, "宝宝小名最多六个字！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mIntent.putExtra("baby", editName.getText().toString());
                    setResult(26, mIntent);
                    finish();
                }
                break;
        }
    }
}
