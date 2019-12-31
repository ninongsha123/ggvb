package com.tiancaijiazu.app.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tiancaijiazu.app.activitys.activitypage.loginpages.FirstActivity;
import com.tiancaijiazu.app.beans.AccessToken;
import com.tiancaijiazu.app.beans.WXUserInfo;
import com.tiancaijiazu.app.globals.Globals;
import com.tiancaijiazu.app.utils.PreUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.tiancaijiazu.app.app.App.api;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1; //登录
    private static final int RETURN_MSG_TYPE_SHARE = 2; //分享
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Globals.APP_ID, true);
        api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        //微信响应的回调
        //我们只对code做处理
        // 这个code与errorCode不一样,有很大的区别,一定要注意
        int errorCode = baseResp.errCode;
        int type = baseResp.getType();
        Log.i("yx123", "onResp: "+errorCode);
        switch (errorCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(type == RETURN_MSG_TYPE_LOGIN ){
                    //用户同意
                    //这才是真正需要的code
                    String code = ((SendAuth.Resp) baseResp).code;

                    //这个是eventbus把code传出去,再做处理
                     Log.i("yx123", "onResp: "+code);
                    Intent intent = new Intent(this, FirstActivity.class);
                    intent.putExtra("code",code);
                    PreUtils.putString("code",code);
                    PreUtils.putString("first","ok");
                    startActivity(intent);

                }else if(type == RETURN_MSG_TYPE_SHARE){
                    //Toast.makeText(this, "微信分享成功", Toast.LENGTH_SHORT).show();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝
                Toast.makeText(this, "拒绝授权微信登录", Toast.LENGTH_SHORT).show();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                String message = "";
                if(type == RETURN_MSG_TYPE_LOGIN){
                    message = "取消了微信登录";
                }else if(type == RETURN_MSG_TYPE_SHARE){
                    message = "取消了微信分享";
                }

                Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }
        //Log.d(TAG, "onResp: "+baseResp.errStr);
        //这个页面一般会显示空白页,直接跳回第三方登录页即可
        finish();
    }
    /**
     * @param code 根据code再去获取AccessToken
     */
    private void getAccessToken(String code) {
        //        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //Post方式也可以...
                RequestBody body = new FormBody.Builder()
                        .add("appid", Globals.APP_ID)
                        .add("secret", Globals.APPSECRET)
                        .add("code", code)
                        .add("grant_type", "authorization_code")
                        .build();
//        url += "?appid=" + "替换为你的appid" + "&secret=xxxxxxxx"
//                + "&code=" + code + "&grant_type=authorization_code";
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                AccessToken accessToken = JSONObject.parseObject(json, new TypeReference<AccessToken>() {
                });
                getUserInfo(accessToken.getAccess_token(), accessToken.getOpenid());
            }
        });
    }

    /**
     * @param access_token 根据access_token再去获取UserInfo
     */
    private void getUserInfo(String access_token, String openid) {
        //        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
        String url = "https://api.weixin.qq.com/sns/userinfo";
        OkHttpClient mOkHttpClient = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("access_token", access_token)
                .add("openid", openid)
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finish();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                WXUserInfo wxUserInfo = JSONObject.parseObject(json, new TypeReference<WXUserInfo>() {
                });
                ///< 发送广播到登录界面，把数据带过去; 可用EventBus
                finish();
            }
        });
    }
}
