package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/8/8/008.
 */

public class BabyMessageBean implements Serializable{
    /**
     * code : 0
     * msg : OK
     * result : [{"babyId":35622937704927232,"name":"大宝","gender":1,"birthday":"2000-07-01","isDefault":1}]
     */

    private String code;
    private String msg;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * babyId : 35622937704927232
         * name : 大宝
         * gender : 1
         * birthday : 2000-07-01
         * isDefault : 1
         */

        private long babyId;
        private String name;
        private int gender;
        private String birthday;
        private String avatar;
        private int isDefault;


        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getBabyId() {
            return babyId;
        }

        public void setBabyId(long babyId) {
            this.babyId = babyId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getIsDefault() {
            return isDefault;
        }

        public void setIsDefault(int isDefault) {
            this.isDefault = isDefault;
        }
    }
}
