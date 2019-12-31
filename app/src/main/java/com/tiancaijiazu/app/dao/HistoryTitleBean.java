package com.tiancaijiazu.app.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/5/28/028.
 */
@Entity
public class HistoryTitleBean {

    @Id(autoincrement = true)
    Long id;

    String title;

    @Generated(hash = 106447201)
    public HistoryTitleBean(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Generated(hash = 943504402)
    public HistoryTitleBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
