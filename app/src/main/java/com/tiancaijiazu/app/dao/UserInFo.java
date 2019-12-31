package com.tiancaijiazu.app.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserInFo {
    @Id(autoincrement = true)
    private Long id;
    private String userId;
    @Generated(hash = 843347030)
    public UserInFo(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }
    @Generated(hash = 480046334)
    public UserInFo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
