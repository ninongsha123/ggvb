package com.tiancaijiazu.app.utils;

import android.util.Log;

import com.tiancaijiazu.app.beans.ArticleDatas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/31/031.
 */

public class UtilsArrays {


    public static ArrayList<ArticleDatas.ResultBean.DiscussListBean> getOneLists(List<ArticleDatas.ResultBean.DiscussListBean> discussList) {
        ArrayList<ArticleDatas.ResultBean.DiscussListBean> discussListBeans = new ArrayList<>();
        for (int i = 0; i < discussList.size(); i++) {
            long s = discussList.get(i).getReplyId();
            Log.i("yx===", "getOneLists: "+s);
            String s1 = s+"";
            Log.i("yx===", "getOneLists: "+s1);
            if(s1.equals("0")){
                discussListBeans.add(discussList.get(i));
            }
        }
        return discussListBeans;
    }


    public static ArrayList<ArticleDatas.ResultBean.DiscussListBean> getTwoLists(List<ArticleDatas.ResultBean.DiscussListBean> discussList) {
        ArrayList<ArticleDatas.ResultBean.DiscussListBean> discussListBeans = new ArrayList<>();
        for (int i = 0; i < discussList.size(); i++) {
            String s = discussList.get(i).getReplyId() + "";
            Log.i("yx===", "getOneLists: "+s);
            if(!s.equals("0")){
                discussListBeans.add(discussList.get(i));
            }
        }
        return discussListBeans;
    }
}
