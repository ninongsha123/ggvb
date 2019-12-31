package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

public class CommentListBeans implements Serializable {

    /**
     * code : 0
     * msg : OK
     * result : {"itemCount":7,"itemList":[{"nickname":"杨*~","avatar":"http://img.tiancaijiazu.com/avatar_default.png","anonymous":1,"star":1,"summary":"第三方付第三方地方地方","pics":"","commentTime":"2019-08-30 18:05:37.314"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"你的人生态度就是这样吧。我的人生就是说我是一种幸福。我们都在一起了。我的人生就是说我是一种幸福。我的人生轨迹是我们自己所追求真理是时间问题。我的人生轨迹是我们自己所做出的选择。我的人生态度就是这样一件事情就是这样一个样？！？！？！？！？！？！？！？！？！？在线支付方式免费送一","pics":"","commentTime":"2019-08-30 17:48:33.531"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"Bnnnnnnnnnnnnnnnnnnnnnnn","pics":"http://img.tiancaijiazu.com/2019/08/30/43954554496225280_750x0.jpg?563,375|http://img.tiancaijiazu.com/2019/08/30/43954554500419584_750x0.jpg?563,422|http://img.tiancaijiazu.com/2019/08/30/43954554504613888_750x0.jpg?563,374|http://img.tiancaijiazu.com/2019/08/30/43954554504613889_750x0.jpg?563,375|http://img.tiancaijiazu.com/2019/08/30/43954554504613890_750x0.jpg?562,843","commentTime":"2019-08-30 17:09:42.679"},{"nickname":"毛*","avatar":"http://img.tiancaijiazu.com/avatar_default.png","anonymous":1,"star":3,"summary":"你在哪里上班没有你在哪里上班","pics":"","commentTime":"2019-08-30 11:16:12.026"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":4,"summary":"我是匿名评论","pics":"http://img.tiancaijiazu.com/2019/08/29/43590850906820608_750x0.jpg?621,1104","commentTime":"2019-08-29 17:04:28.910"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":0,"summary":"兔兔兔轻轻松松","pics":"","commentTime":"2019-08-29 16:01:24.560"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"哈哈哈哈哈哈","pics":"http://img.tiancaijiazu.com/2019/08/29/43567185616048128_750x0.jpg?621,1104|http://img.tiancaijiazu.com/2019/08/29/43567185620242432_750x0.jpg?621,1104","commentTime":"2019-08-29 15:30:26.757"}]}
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
         * itemCount : 7
         * itemList : [{"nickname":"杨*~","avatar":"http://img.tiancaijiazu.com/avatar_default.png","anonymous":1,"star":1,"summary":"第三方付第三方地方地方","pics":"","commentTime":"2019-08-30 18:05:37.314"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"你的人生态度就是这样吧。我的人生就是说我是一种幸福。我们都在一起了。我的人生就是说我是一种幸福。我的人生轨迹是我们自己所追求真理是时间问题。我的人生轨迹是我们自己所做出的选择。我的人生态度就是这样一件事情就是这样一个样？！？！？！？！？！？！？！？！？！？在线支付方式免费送一","pics":"","commentTime":"2019-08-30 17:48:33.531"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"Bnnnnnnnnnnnnnnnnnnnnnnn","pics":"http://img.tiancaijiazu.com/2019/08/30/43954554496225280_750x0.jpg?563,375|http://img.tiancaijiazu.com/2019/08/30/43954554500419584_750x0.jpg?563,422|http://img.tiancaijiazu.com/2019/08/30/43954554504613888_750x0.jpg?563,374|http://img.tiancaijiazu.com/2019/08/30/43954554504613889_750x0.jpg?563,375|http://img.tiancaijiazu.com/2019/08/30/43954554504613890_750x0.jpg?562,843","commentTime":"2019-08-30 17:09:42.679"},{"nickname":"毛*","avatar":"http://img.tiancaijiazu.com/avatar_default.png","anonymous":1,"star":3,"summary":"你在哪里上班没有你在哪里上班","pics":"","commentTime":"2019-08-30 11:16:12.026"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":4,"summary":"我是匿名评论","pics":"http://img.tiancaijiazu.com/2019/08/29/43590850906820608_750x0.jpg?621,1104","commentTime":"2019-08-29 17:04:28.910"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":0,"summary":"兔兔兔轻轻松松","pics":"","commentTime":"2019-08-29 16:01:24.560"},{"nickname":"毛毛","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","anonymous":0,"star":5,"summary":"哈哈哈哈哈哈","pics":"http://img.tiancaijiazu.com/2019/08/29/43567185616048128_750x0.jpg?621,1104|http://img.tiancaijiazu.com/2019/08/29/43567185620242432_750x0.jpg?621,1104","commentTime":"2019-08-29 15:30:26.757"}]
         */

        private int itemCount;
        private List<ItemListBean> itemList;

        public int getItemCount() {
            return itemCount;
        }

        public void setItemCount(int itemCount) {
            this.itemCount = itemCount;
        }

        public List<ItemListBean> getItemList() {
            return itemList;
        }

        public void setItemList(List<ItemListBean> itemList) {
            this.itemList = itemList;
        }

        public static class ItemListBean implements Serializable {
            /**
             * nickname : 杨*~
             * avatar : http://img.tiancaijiazu.com/avatar_default.png
             * anonymous : 1
             * star : 1
             * summary : 第三方付第三方地方地方
             * pics :
             * commentTime : 2019-08-30 18:05:37.314
             */

            private String nickname;
            private String avatar;
            private int anonymous;
            private int star;
            private String summary;
            private String pics;
            private String commentTime;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getAnonymous() {
                return anonymous;
            }

            public void setAnonymous(int anonymous) {
                this.anonymous = anonymous;
            }

            public int getStar() {
                return star;
            }

            public void setStar(int star) {
                this.star = star;
            }

            public String getSummary() {
                return summary;
            }

            public void setSummary(String summary) {
                this.summary = summary;
            }

            public String getPics() {
                return pics;
            }

            public void setPics(String pics) {
                this.pics = pics;
            }

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }
        }
    }
}
