package com.tiancaijiazu.app.rongim.imactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.common.RongWebView;
import io.rong.imkit.RongBaseActivity;
import io.rong.imkit.tools.RongWebviewActivity;


public class RongWebActivity extends Activity {

    @BindView(R.id.rc_web_progressbar)
    ProgressBar mRcWebProgressbar;

    private String url = "https://web.jiaxincloud.com/gray/jiaxin-phone.html?id=mwnvbwk3d2d1zg&appName=kllx278&appChannel=10001";
    private TextView mWebViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rong_web);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.i("yx126", "onCreate: "+url);
        mWebViewTitle = findViewById(R.id.rc_action_bar_title);
    }

}
