package com.tiancaijiazu.app.globals;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tiancaijiazu.app.app.App;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.tiancaijiazu.app.app.App.api;

/**
 * Created by Administrator on 2019/7/25/025.
 */

public class WechatShareTool {
    private static int toFriend = SendMessageToWX.Req.WXSceneSession;//会话
    private static int toFriendCircle = SendMessageToWX.Req.WXSceneTimeline;//朋友圈
    //SendMessageToWX.Req.WXSceneFavorite //分享到收藏

    /**
     * 微信文字分享
     * @param text
     * @param isNotToFriend 是否分享到朋友圈
     */
    public static void shareText(String text,boolean isNotToFriend){
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject(text);
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        shareToWX("text", msg, isNotToFriend);
    }

    /*
     * 保证字符串唯一
     */
    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    /**
     * 分享图片
     */
    public static void shareImage(Bitmap picturePath,boolean isNotToFriend){
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(picturePath);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(picturePath, 150, 150, true);
        picturePath.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        shareToWX("img", msg, isNotToFriend);
    }

    /**
     * 分享音乐
     * @param title
     * @param description
     * @param musicUrl
     */
    public void shareMusic(String title,String description,String musicUrl,String picturePath,boolean isNotToFriend){
        //初始化一个WXMusicObject，填写url
        WXMusicObject music = new WXMusicObject();
        music.musicUrl=musicUrl;

        Bitmap bmp = BitmapFactory.decodeFile(picturePath);
        WXMediaMessage msg = getWXMediaMessage(title, description, music, bmp);

        shareToWX("music",msg,isNotToFriend);
    }

    /**
     * 分享视频
     * @param title
     * @param description
     * @param viedoUrl
     */
    public static void shareVideo(String title, String description, String viedoUrl, Bitmap picturePath, boolean isNotToFriend){
        //初始化一个WXVideoObject，填写url
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = viedoUrl;

        //Bitmap bmp = BitmapFactory.decodeFile(picturePath);
        WXMediaMessage msg = getWXMediaMessage(title, description, video, picturePath);

        shareToWX("video",msg,isNotToFriend);
    }

    /*
     * 设置mediaMessage和图片缩略图
     */
    private static WXMediaMessage getWXMediaMessage(String title, String description, WXMediaMessage.IMediaObject media, Bitmap bmp){
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = media;
        msg.title = title;
        msg.description = description;

        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);//方法在最后面
        return msg;
    }

    /*
     * 分享
     */
    public static void shareToWX(String type,WXMediaMessage msg,boolean isNotToFriend){
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(type);
        req.message =msg;
        if(isNotToFriend)
            req.scene = toFriendCircle;
        else
            req.scene = toFriend;
        req.userOpenId = Globals.APP_ID;

        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    /*
     * shareUrl分享
     */
    public static void shareToWXUrl(String url,Bitmap picturePath,String name,boolean isNotToFriend,String description,boolean isbo) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl=url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title=name;
        if(isbo){
            wxMediaMessage.description = description;
        }else {
            wxMediaMessage.description = "天才家族育儿早教App学习做父母培养小天才!";
        }
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(picturePath, 100, 100, true);
        picturePath.recycle();
        wxMediaMessage.thumbData = bmpToByteArray(thumbBmp, true);//方法在最后面
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message=wxMediaMessage;
        req.transaction=WechatShareTool.buildTransaction("webpage");
        if(isNotToFriend)
            req.scene = toFriendCircle;
        else
            req.scene = toFriend;
        req.userOpenId = Globals.APP_ID;

        //调用api接口，发送数据到微信
        api.sendReq(req);

    }

    /*
        * 图片转换
        */
    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
