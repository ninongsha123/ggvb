package com.tiancaijiazu.app.beans;

import java.util.List;

/**
 * Created by Administrator on 2019/8/13/013.
 */

public class SongBean {
    /**
     * code : 0
     * msg : OK
     * result : {"musicId":37679379115020288,"title":"最新最新","picUri":"http://img.tiancaijiazu.com/2019/08/13/37679205676355584_360x360.png?360,360","description":"塞纳河畔 左岸的咖啡我手一杯 品尝你的美留下唇印的嘴","itemList":[{"itemId":37681880425959424,"musicId":37679379115020288,"title":"明天你好","picUri":"http://img.tiancaijiazu.com/2019/08/13/37681356901322752_360x360.jpg?360,360","lyricUri":"http://img.tiancaijiazu.com/2019/08/13/37686282176040960.lrc?0","mediaUri":"http://img.tiancaijiazu.com/2019/08/13/37686311192236032.mp3?229","description":"告白气球告白气球告白气球告白气球告白气球告白气球"}]}
     */

    private String code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * musicId : 37679379115020288
         * title : 最新最新
         * picUri : http://img.tiancaijiazu.com/2019/08/13/37679205676355584_360x360.png?360,360
         * description : 塞纳河畔 左岸的咖啡我手一杯 品尝你的美留下唇印的嘴
         * itemList : [{"itemId":37681880425959424,"musicId":37679379115020288,"title":"明天你好","picUri":"http://img.tiancaijiazu.com/2019/08/13/37681356901322752_360x360.jpg?360,360","lyricUri":"http://img.tiancaijiazu.com/2019/08/13/37686282176040960.lrc?0","mediaUri":"http://img.tiancaijiazu.com/2019/08/13/37686311192236032.mp3?229","description":"告白气球告白气球告白气球告白气球告白气球告白气球"}]
         */

        private long musicId;
        private String title;
        private String picUri;
        private String description;
        private List<ItemListBean> itemList;

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

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean {
            /**
             * itemId : 37681880425959424
             * musicId : 37679379115020288
             * title : 明天你好
             * picUri : http://img.tiancaijiazu.com/2019/08/13/37681356901322752_360x360.jpg?360,360
             * lyricUri : http://img.tiancaijiazu.com/2019/08/13/37686282176040960.lrc?0
             * mediaUri : http://img.tiancaijiazu.com/2019/08/13/37686311192236032.mp3?229
             * description : 告白气球告白气球告白气球告白气球告白气球告白气球
             */

            private long itemId;
            private long musicId;
            private String title;
            private String picUri;
            private String lyricUri;
            private String mediaUri;
            private String description;

            public long getItemId() {
                return itemId;
            }

            public void setItemId(long itemId) {
                this.itemId = itemId;
            }

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

            public String getLyricUri() {
                return lyricUri;
            }

            public void setLyricUri(String lyricUri) {
                this.lyricUri = lyricUri;
            }

            public String getMediaUri() {
                return mediaUri;
            }

            public void setMediaUri(String mediaUri) {
                this.mediaUri = mediaUri;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
