package com.tiancaijiazu.app.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/8/19/019.
 */
@Entity
public class TheTopicRecordBean {

    @Id(autoincrement = true)
    Long id;

    String contentId;

    String scoreSetting;

    String option;

    String babyId;

    String subjectId;

    int lenght;

    @Generated(hash = 1723642739)
    public TheTopicRecordBean(Long id, String contentId, String scoreSetting,
            String option, String babyId, String subjectId, int lenght) {
        this.id = id;
        this.contentId = contentId;
        this.scoreSetting = scoreSetting;
        this.option = option;
        this.babyId = babyId;
        this.subjectId = subjectId;
        this.lenght = lenght;
    }

    @Generated(hash = 327828831)
    public TheTopicRecordBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentId() {
        return this.contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getScoreSetting() {
        return this.scoreSetting;
    }

    public void setScoreSetting(String scoreSetting) {
        this.scoreSetting = scoreSetting;
    }

    public String getOption() {
        return this.option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getBabyId() {
        return this.babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public String getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getLenght() {
        return this.lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

  

    
}
