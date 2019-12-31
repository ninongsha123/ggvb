package com.tiancaijiazu.app.activitys.activitypage.loginpages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.CopyButtonLibraryDanJi;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.DonwloadSaveImg;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  登录页面-新用户如何获取验证码
 *
 */
public class GetCodeActivity extends SimpleActivity {

    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title)
    MediumBoldTextViewTitle mTitle;
    @BindView(R.id.code_title)
    TextView code_title;
    @BindView(R.id.iv)
    ImageView mIv;
    private String string="方法一：<br>" +
        "联系身边已是天才家族早教线上园的园长获取推荐码。<br><br>" +
        "方法二：<br>" +
        "添加天才家族官方微信客服获取推荐码：<br>" +
        "1、点击自动复制微信号：<font color = \"#00DEFF\">"+"A15315510555 "+"</font>"+"打开微信添加好友，添加成功后发送您所在城市的具体位置，客服会根据您所在位置发送推荐码。<br>" +
        "2、点击下方微信二维码自动截图，打开微信——右上角”扫一扫“即可添加好友，添加成功后发送您所在城市的具体位置，客服会根据您所在位置发送推荐码。";
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        code_title.setText(Html.fromHtml(string));
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_get_code;
    }


    @OnClick({R.id.iv_finis,R.id.code_title,R.id.iv})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.code_title:
                CopyButtonLibraryDanJi copyButtonLibrary = new CopyButtonLibraryDanJi(this,"A15315510555");
                copyButtonLibrary.init();
                break;
            case R.id.iv:
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.erwima);
                DonwloadSaveImg.donwloadImg1(GetCodeActivity.this,bitmap);
                break;
        }
    }
}
