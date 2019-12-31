package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/13/013.
 */

public class SongListBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"musicId":37679379115020288,"title":"最新最新","picUri":"http://img.tiancaijiazu.com/2019/08/13/37679205676355584_360x360.png?360,360","description":"塞纳河畔 左岸的咖啡我手一杯 品尝你的美留下唇印的嘴","itemList":[]},{"musicId":37679082007302144,"title":"最热最热","picUri":"http://img.tiancaijiazu.com/2019/08/13/37678823940165632_360x360.png?360,360","description":"还记得多年前跟你手牵手你都害羞的不敢抬头只会傻傻的看着天上的星星","itemList":[]},{"musicId":37678774602567680,"title":"把孤独当做晚餐","picUri":"http://img.tiancaijiazu.com/2019/08/13/37678437778984960_360x360.png?360,360","description":"我多想拥抱你利落干脆我多想拥抱你没有亏欠我多想拥抱你最后笑中含着泪说一句再见","itemList":[]}]
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

    public static class ResultBean {
        /**
         * musicId : 37679379115020288
         * title : 最新最新
         * picUri : http://img.tiancaijiazu.com/2019/08/13/37679205676355584_360x360.png?360,360
         * description : 塞纳河畔 左岸的咖啡我手一杯 品尝你的美留下唇印的嘴
         * itemList : []
         */

        private long musicId;
        private String title;
        private String picUri;
        private String description;
        private List<?> itemList;

        public long getMusicId() {
            return musicId;
        }

        public void setMusicId(long musicId) {
            this.musicId = musicId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<?> getItemList() {
            return itemList;
        }

        public void setItemList(List<?> itemList) {
            this.itemList = itemList;
        }
    }
}
