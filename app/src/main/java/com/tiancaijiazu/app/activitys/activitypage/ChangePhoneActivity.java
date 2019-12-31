package com.tiancaijiazu.app.activitys.activitypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;

public class ChangePhoneActivity extends SimpleActivity {


    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.phone)
    TextView phone; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.change)
    RelativeLayout change;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initClick();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String number = extras.getString("number");//获取到的真实手机号
        String s = number.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");//做处理的手机号
        phone.setText("+86  " + s);
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_change_phone;
    }



    private void initClick() {
        ivFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChangePhoneActivity.this, "暂时不能修改", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
