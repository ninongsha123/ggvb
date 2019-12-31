package com.tiancaijiazu.app.rongim;

import android.os.Parcel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;


@MessageTag(value = "JX:ProductInfo" ,flag = MessageTag.ISPERSISTED| MessageTag.ISCOUNTED)
public class PhoneInfo extends MessageContent {

    //商品标题
    private String title;
    //商品钱*规格
    private String content;
    //商品url
    private String url;
    //商品图片
    private String remoteurl;



    public static PhoneInfo setPhoneData(String title, String content, String url, String remoteurl){
        PhoneInfo info =new PhoneInfo();
        info.title = title;
        info.content = content;
        info.url = url;
        info.remoteurl = remoteurl;
        return info;
    }

    @Override
    public byte[] encode() {
        JSONObject object =new JSONObject();
        object.put("title", title);
        object.put("content", content);
        object.put("url", url);
        object.put("remoteurl", remoteurl);


        try {
            return object.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PhoneInfo() {
    }

    public PhoneInfo(byte[] data) {
        super(data);
        String jsonStr=null;
        try {
            jsonStr =new String(data,"UTF-8");
            JSONObject object = JSON.parseObject(jsonStr);

           /* setUserName(object.getString("userName"));
            setPhoneNum(object.getString("phoneNum"));*/
            setTitle(object.getString("title"));
            setContent(object.getString("content"));
            setUrl(object.getString("url"));
            setRemoteurl(object.getString("remoteurl"));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*ParcelUtils.writeToParcel(dest, userName);
        ParcelUtils.writeToParcel(dest,phoneNum);*/

        ParcelUtils.writeToParcel(dest, title);
        ParcelUtils.writeToParcel(dest, content);
        ParcelUtils.writeToParcel(dest, url);
        ParcelUtils.writeToParcel(dest, remoteurl);
    }

    public static final Creator<PhoneInfo> CREATOR = new Creator<PhoneInfo>() {

        @Override
        public PhoneInfo createFromParcel(Parcel source) {
            return new PhoneInfo(source);
        }

        @Override
        public PhoneInfo[] newArray(int size) {
            return new PhoneInfo[size];
        }
    };


    public PhoneInfo(Parcel parcel){

       /* userName = ParcelUtils.readFromParcel(parcel);
        phoneNum = ParcelUtils.readFromParcel(parcel);*/
        title = ParcelUtils.readFromParcel(parcel);
        content = ParcelUtils.readFromParcel(parcel);
        url = ParcelUtils.readFromParcel(parcel);
        remoteurl = ParcelUtils.readFromParcel(parcel);
    }




    @Override
    public int describeContents() {
        return 0;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRemoteurl() {
        return remoteurl;
    }

    public void setRemoteurl(String remoteurl) {
        this.remoteurl = remoteurl;
    }
}
