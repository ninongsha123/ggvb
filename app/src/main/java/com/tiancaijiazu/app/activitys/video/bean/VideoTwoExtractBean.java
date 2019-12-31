package com.tiancaijiazu.app.activitys.video.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class VideoTwoExtractBean {
    private String title;
    private ArrayList<ContentsBean> contentsList;


    public VideoTwoExtractBean(String title, ArrayList<ContentsBean> contentsList) {
        this.title = title;
        this.contentsList = contentsList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ContentsBean> getContentsList() {
        return contentsList;
    }

    public void setContentsList(ArrayList<ContentsBean> contentsList) {
        this.contentsList = contentsList;
    }
}
