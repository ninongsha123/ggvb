package com.tiancaijiazu.app.rongim.imactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.rongim.PhoneInfo;
import com.tiancaijiazu.app.utils.PreUtils;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class ConversationActivity extends FragmentActivity {


    private String mTargetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation);
        ImageView back = findViewById(R.id.back);
        TextView faSong = findViewById(R.id.fa_song);
        Intent intent = getIntent();
        mTargetId = intent.getData().getQueryParameter("targetId");
        String rongyun = PreUtils.getString("rongyun", "");
        if("shop".equals(rongyun)){
            faSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage();
                }
            });
        }else if("my".equals(rongyun)){
            faSong.setVisibility(View.GONE);
        }

        setStatusBarColor(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void sendMessage() {
        PhoneInfo info = new PhoneInfo();
        String shopname = PreUtils.getString("shopname", "");
        String shoppicuri = PreUtils.getString("shoppicuri", "");
        String shopprice = PreUtils.getString("shopprice", "");
        String shopShare = PreUtils.getString("shopShare", "");
        info.setTitle(shopname);
        info.setContent("价格：¥"+shopprice);
        info.setUrl(shopShare);
        info.setRemoteurl(shoppicuri);
        Message message = Message.obtain(mTargetId, Conversation.ConversationType.CUSTOMER_SERVICE,info);


        RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback() {
            @Override //表示消息添加到本地数据库
            public void onAttached(Message message) {
                Log.i("yx555", "onAttached: ");
            }

            @Override//消息发送成功
            public void onSuccess(Message message) {
                Log.i("yx555", "onSuccess: ");
            }

            @Override //消息发送失败
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                Log.i("yx555", "onError: ");
            }
        });
    }
    public void setStatusBarColor(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.setStatusBarColor(Color.parseColor("#FFFFFF"));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    @Override
    public void onBackPressed() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        if (!fragment.onBackPressed()) {
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

}