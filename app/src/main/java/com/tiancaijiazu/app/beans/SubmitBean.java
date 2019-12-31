package com.tiancaijiazu.app.beans;

import java.util.List;

public class SubmitBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"title":"秋天户外树叶游戏","summary":"秋天，树叶纷纷扬扬落下，微凉的空气里满是树叶的清香。 在户外带着孩子玩的时候，除了捡树叶，不知道玩些什么的话，不妨看看小编为大家准备的四款树叶游戏，让它们带你和孩子尽享秋日欢愉吧~","picUri":"http://img.tiancaijiazu.com/2019/09/02/45009887805181952_290x180.png?290,180","url":"http://game.study.tiancaijiazu.com/item?id=45009976745398272"},{"title":"秋天户外树叶游戏","summary":"秋天，树叶纷纷扬扬落下，微凉的空气里满是树叶的清香。 在户外带着孩子玩的时候，除了捡树叶，不知道玩些什么的话，不妨看看小编为大家准备的四款树叶游戏，让它们带你和孩子尽享秋日欢愉吧~","picUri":"http://img.tiancaijiazu.com/2019/09/02/45009887805181952_290x180.png?290,180","url":"http://game.study.tiancaijiazu.com/item?id=45009976745398272"}]
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
         * title : 秋天户外树叶游戏
         * summary : 秋天，树叶纷纷扬扬落下，微凉的空气里满是树叶的清香。 在户外带着孩子玩的时候，除了捡树叶，不知道玩些什么的话，不妨看看小编为大家准备的四款树叶游戏，让它们带你和孩子尽享秋日欢愉吧~
         * picUri : http://img.tiancaijiazu.com/2019/09/02/45009887805181952_290x180.png?290,180
         * url : http://game.study.tiancaijiazu.com/item?id=45009976745398272
         */

        private String title;
        private String summary;
        private String picUri;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPicUri() {
            return picUri;
        }

        public void setPicUri(String picUri) {
            this.picUri = picUri;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
