package com.tiancaijiazu.app.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2019/5/20/020.
 */
@Entity
public class ToKenDaoBean {
    @Id(autoincrement = true)
    Long id;

    String access_token;

    String refresh_token;

    String time;

    String validtime;

    @Generated(hash = 2044035062)
    public ToKenDaoBean(Long id, String access_token, String refresh_token,
            String time, String validtime) {
        this.id = id;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.time = time;
        this.validtime = validtime;
    }

    @Generated(hash = 321157001)
    public ToKenDaoBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValidtime() {
        return this.validtime;
    }

    public void setValidtime(String validtime) {
        this.validtime = validtime;
    }

    
}
