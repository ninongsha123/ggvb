package com.tiancaijiazu.app.activitys.bean;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class AnswerBean {

    String contentId;
    String score;

    public AnswerBean(String contentId, String score) {
        this.contentId = contentId;
        this.score = score;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
