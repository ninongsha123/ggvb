package com.tiancaijiazu.app.beans;

import java.util.List;

public class TeamBean {
    /**
     * code : 0
     * msg : OK
     * result : [{"userid":46206206578135040,"mobile":"13693502567","avatar":"http://img.tiancaijiazu.com/avatar_default.png","teamVip":0,"teamReferrer":0},{"userid":45809173476282368,"mobile":"15966863102","avatar":"http://img.tiancaijiazu.com/avatar_default.png","teamVip":0,"teamReferrer":0},{"userid":43316093493841920,"mobile":"17706399777","avatar":"http://img.tiancaijiazu.com/avatar_default.png","teamVip":0,"teamReferrer":0},{"userid":43114236725039104,"mobile":"15648550590","avatar":"http://img.tiancaijiazu.com/2019/08/30/43841610571714560_148x148.jpg?148,148","teamVip":0,"teamReferrer":0},{"userid":42956569780031488,"mobile":"15898806551","avatar":"http://img.tiancaijiazu.com/avatar_default.png","teamVip":0,"teamReferrer":0},{"userid":27689654396522496,"mobile":"18053216777","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/2mMhyEbKBJDqnozNiaU7JkjB7xeJlFcYfwNLXB69yJ4NBWGfHvEb4QD0vhpcJNRL7ichkk3EBQ4RSSficvicoRxmrg/132","teamVip":0,"teamReferrer":0},{"userid":19589408000970752,"mobile":"15811125060","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eponV6arREt1hulia2OMwOzJZD5zRVa3V6BD7zD8sSpsHWXhEjsLAiaky4zWOeOcxZIpdavpaprnQSg/132","teamVip":0,"teamReferrer":0},{"userid":19571089252421632,"mobile":"18863971888","avatar":"http://img.tiancaijiazu.com/2019/07/30/32548871817269248_148x148.jpg?148,148","teamVip":0,"teamReferrer":0},{"userid":18471807514447872,"mobile":"18333958136","avatar":"http://img.tiancaijiazu.com/avatar_default.png","teamVip":0,"teamReferrer":0},{"userid":10571612466319360,"mobile":"17326856737","avatar":"http://img.tiancaijiazu.com/2019/06/28/21075046344822784_148x148.jpg?148,148","teamVip":0,"teamReferrer":0},{"userid":10558706915872768,"mobile":"17600639317","avatar":"http://img.tiancaijiazu.com/2019/08/23/41329534108307456_148x148.jpg?148,148","teamVip":0,"teamReferrer":0},{"userid":9569814792245248,"mobile":"13240811187","avatar":"http://img.tiancaijiazu.com/2019/09/06/46484711723372544_148x148.jpg?148,148","teamVip":0,"teamReferrer":0},{"userid":9567974524588032,"mobile":"15010565227","avatar":"http://img.tiancaijiazu.com/2019/07/02/22627667404787712_148x148.jpg?148,148","teamVip":0,"teamReferrer":0}]
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
         * userid : 46206206578135040
         * mobile : 13693502567
         * avatar : http://img.tiancaijiazu.com/avatar_default.png
         * teamVip : 0
         * teamReferrer : 0
         */

        private long userid;
        private String mobile;
        private String avatar;
        private String nickname;
        private String signup;

        public String getSignup() {
            return signup;
        }

        public void setSignup(String signup) {
            this.signup = signup;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        private int teamVip;
        private int teamReferrer;
        private int referrerCode;
        private int vipLevel;
        private String vipTitle;
        private int  team_level_11;
        private int  team_level_21;
        private int  team_level_31;

        public int getTeam_level_11() {
            return team_level_11;
        }

        public void setTeam_level_11(int team_level_11) {
            this.team_level_11 = team_level_11;
        }

        public int getTeam_level_21() {
            return team_level_21;
        }

        public void setTeam_level_21(int team_level_21) {
            this.team_level_21 = team_level_21;
        }

        public int getTeam_level_31() {
            return team_level_31;
        }

        public void setTeam_level_31(int team_level_31) {
            this.team_level_31 = team_level_31;
        }

        public int getVipLevel() {
            return vipLevel;
        }

        public void setVipLevel(int vipLevel) {
            this.vipLevel = vipLevel;
        }

        public String getVipTitle() {
            return vipTitle;
        }

        public void setVipTitle(String vipTitle) {
            this.vipTitle = vipTitle;
        }

        public int getReferrerCode() {
            return referrerCode;
        }

        public void setReferrerCode(int referrerCode) {
            this.referrerCode = referrerCode;
        }

        public long getUserid() {
            return userid;
        }

        public void setUserid(long userid) {
            this.userid = userid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getTeamVip() {
            return teamVip;
        }

        public void setTeamVip(int teamVip) {
            this.teamVip = teamVip;
        }

        public int getTeamReferrer() {
            return teamReferrer;
        }

        public void setTeamReferrer(int teamReferrer) {
            this.teamReferrer = teamReferrer;
        }
    }
}
