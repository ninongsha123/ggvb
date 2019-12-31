package com.tiancaijiazu.app.activitys.video.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/7/12/012.
 */

public class ParentBean {
    private String title;

    private ArrayList<SubBean> result;

    public ParentBean(String title, ArrayList<SubBean> result) {
        this.title = title;
        this.result = result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SubBean> getResult() {
        return result;
    }

    public void setResult(ArrayList<SubBean> result) {
        this.result = result;
    }

    public static class SubBean {
        private String data;

        private int tyle;

        public SubBean(String data, int tyle) {
            this.data = data;
            this.tyle = tyle;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public int getTyle() {
            return tyle;
        }

        public void setTyle(int tyle) {
            this.tyle = tyle;
        }
    }
}
