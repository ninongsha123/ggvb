package com.tiancaijiazu.app.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2019/5/28/028.
 */

public class HttpUtils {
    /**
     * 发布文章内容
     *
     * @param map
     * @return
     */
    public static Map<String, RequestBody> getReleaseTopic(Map<String, Object> map) {

        String title = (String) map.get("title");
        String detail = (String) map.get("detail");
        String display = (String) map.get("display");
        String keyTime = (String) map.get("keyTime");
        String subject = (String) map.get("subject");
        String articleType = (String) map.get("articleType");
        String courseId = (String) map.get("courseId");
        String contentsId = (String) map.get("contentsId");

        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"e8bbadbd51c44a139c789fb1ef062b94");

        RequestBody requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), articleType);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("articleType", requestBody4);
        //requestBodyMap.put("userId",requestBody);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), detail);
        requestBodyMap.put("detail", requestBody2);
            if (keyTime!=null) {
                RequestBody requestBodys = RequestBody.create(MediaType.parse("multipart/form-data"), keyTime);
                requestBodyMap.put("keyTime", requestBodys);
            }
        if (display!=null) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), display);
            requestBodyMap.put("display", requestBody);
        }
        if (subject!=null) {
            RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), subject);
            requestBodyMap.put("subject", requestBody3);
        }
        if (title!=null) {
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), title);
            requestBodyMap.put("title", requestBody1);
        }
        if (courseId!=null) {
            RequestBody requestBody5 = RequestBody.create(MediaType.parse("multipart/form-data"), courseId);
            requestBodyMap.put("courseId", requestBody5);
            RequestBody requestBody6 = RequestBody.create(MediaType.parse("multipart/form-data"), contentsId);
            requestBodyMap.put("contentsId", requestBody6);
        }

        return requestBodyMap;
    }

    /**
     * 发布评价内容
     *
     * @param map
     * @return
     */
    public static Map<String, RequestBody> getReleaseEvaluate(Map<String, Object> map) {

        String orderId = (String) map.get("orderId");
        String anonymous = (String) map.get("anonymous");
        String summary = (String) map.get("summary");
        String star = (String) map.get("star");
        //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),"e8bbadbd51c44a139c789fb1ef062b94");
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), orderId);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), anonymous);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), star);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), summary);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        //requestBodyMap.put("userId",requestBody);
        requestBodyMap.put("orderId", requestBody1);
        requestBodyMap.put("anonymous", requestBody2);
        requestBodyMap.put("star", requestBody3);
        requestBodyMap.put("summary", requestBody4);
        return requestBodyMap;
    }

    /**
     * 添加宝宝卡信息
     *
     * @param map
     * @return
     */
    public static Map<String, RequestBody> getBabyMess(Map<String, Object> map) {
        String name2 = (String) map.get("name");
        int gender1 = (int) map.get("gender");
        String birthday = (String) map.get("birthday");
        int isDefault2 = (int) map.get("isDefault");
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), name2);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), gender1 + "");
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), birthday);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), isDefault2 + "");


        requestBodyMap.put("name", requestBody1);
        requestBodyMap.put("gender", requestBody2);
        requestBodyMap.put("birthday", requestBody3);
        requestBodyMap.put("isDefault", requestBody4);
        return requestBodyMap;
    }

    public static Map<String, RequestBody> getBabyMess1(Map<String, Object> map) {
        String babyId1 = (String) map.get("babyId");
        String name2 = (String) map.get("name");
        int gender1 = (int) map.get("gender");
        String birthday = (String) map.get("birthday");
        int isDefault2 = (int) map.get("isDefault");
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), babyId1);
        requestBodyMap.put("babyId", requestBody);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), name2);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), gender1 + "");
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), birthday);
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), isDefault2 + "");


        requestBodyMap.put("name", requestBody1);
        requestBodyMap.put("gender", requestBody2);
        requestBodyMap.put("birthday", requestBody3);
        requestBodyMap.put("isDefault", requestBody4);
        return requestBodyMap;
    }

    /**
     * 宝宝头像
     *
     * @param map
     * @return
     */
    public static MultipartBody.Part getBabyPic(Map<String, Object> map) {

        File file = (File) map.get("files");
        // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
        //Log.i("101010", "getReleaseTopic1: "+file.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        return part;
    }

    /**
     * 发布文章图片
     *
     * @param map
     * @return
     */
    public static List<MultipartBody.Part> getReleaseTopic1(Map<String, Object> map) {

        List<File> fileList = (List<File>) map.get("files");

        List<MultipartBody.Part> parts = new ArrayList<>(fileList.size());
        for (File file : fileList) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            //Log.i("101010", "getReleaseTopic1: "+file.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }

    /**
     * 提交订单
     *
     * @param map
     * @return
     */
    public static Map<String, RequestBody> getOrderForm(HashMap<String, String> map) {

        String consigneeName = (String) map.get("consigneeName");
        String consigneeMobile = (String) map.get("consigneeMobile");
        String consigneeAddress = (String) map.get("consigneeAddress");
        String items = (String) map.get("items");

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), consigneeName);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), consigneeMobile);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), consigneeAddress);
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), items);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("consigneeName", requestBody);
        requestBodyMap.put("consigneeMobile", requestBody1);
        requestBodyMap.put("consigneeAddress", requestBody2);
        requestBodyMap.put("items", requestBody3);

        return requestBodyMap;
    }

    /**
     * 修改头像
     *
     * @param file
     * @return
     */
    public static RequestBody getUploading(File file) {
        RequestBody requestBody = null;
        if (file.getName() != null) {
            requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                    .build();
        }
        return requestBody;
    }

    /**
     * 提交订单
     *
     * @param map
     * @return
     */
    public static Map<String, RequestBody> getSubmitAppraisal(HashMap<String, String> map) {

        String babyId = (String) map.get("babyId");
        String subjectId = (String) map.get("subjectId");
        String contents = (String) map.get("contents");
        Log.i("yx159", "getSubmitAppraisal: "+babyId);
        Log.i("yx159", "getSubmitAppraisal: "+subjectId);
        Log.i("yx159", "getSubmitAppraisal: "+contents);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), babyId);
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), subjectId);
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), contents);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("babyId", requestBody);
        requestBodyMap.put("subjectId", requestBody1);
        requestBodyMap.put("contents", requestBody2);

        return requestBodyMap;
    }
}
