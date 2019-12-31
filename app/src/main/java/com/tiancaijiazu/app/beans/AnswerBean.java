package com.tiancaijiazu.app.beans;

/**
 * Created by Administrator on 2019/8/19/019.
 */

public class AnswerBean {
    String contentId;
    String indicatorsValue;
    String title;
    String selectName;
    String selectGrade;

    public AnswerBean(String contentId, String indicatorsValue, String title, String selectName, String selectGrade) {
        this.contentId = contentId;
        this.indicatorsValue = indicatorsValue;
        this.title = title;
        this.selectName = selectName;
        this.selectGrade = selectGrade;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getIndicatorsValue() {
        return indicatorsValue;
    }

    public void setIndicatorsValue(String indicatorsValue) {
        this.indicatorsValue = indicatorsValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectName() {
        return selectName;
    }

    public void setSelectName(String selectName) {
        this.selectName = selectName;
    }

    public String getSelectGrade() {
        return selectGrade;
    }

    public void setSelectGrade(String selectGrade) {
        this.selectGrade = selectGrade;
    }
}
