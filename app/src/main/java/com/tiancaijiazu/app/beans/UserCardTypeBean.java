package com.tiancaijiazu.app.beans;

public class UserCardTypeBean {
    /**
     * code : 0
     * msg : OK
     * result : {"cardType":3,"name":"半年卡","title":"天才家族早教课","summary":"来自清华斯坦福的家庭启蒙实践","slogan":"","class1":"能力提升主题课","classAmount1":120,"class2":"感统游戏","classAmount2":24,"class3":"延伸课","classAmount3":288,"weeks":26,"currentWeek":1,"classCount":32,"price":1298,"promoPrice":0.01,"babyBirthday":"2019-05-01","expiresIn":"2020-03-04 14:12:36.424"}
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
         * cardType : 3
         * name : 半年卡
         * title : 天才家族早教课
         * summary : 来自清华斯坦福的家庭启蒙实践
         * slogan :
         * class1 : 能力提升主题课
         * classAmount1 : 120
         * class2 : 感统游戏
         * classAmount2 : 24
         * class3 : 延伸课
         * classAmount3 : 288
         * weeks : 26
         * currentWeek : 1
         * classCount : 32
         * price : 1298.0
         * promoPrice : 0.01
         * babyBirthday : 2019-05-01
         * expiresIn : 2020-03-04 14:12:36.424
         */

        private int cardType;
        private String name;
        private String title;
        private String summary;
        private String slogan;
        private String class1;
        private int classAmount1;
        private String class2;
        private int classAmount2;
        private String class3;
        private int classAmount3;
        private int weeks;
        private int currentWeek;
        private int classCount;
        private double price;
        private double promoPrice;
        private String babyBirthday;
        private String expiresIn;

        public int getCardType() {
            return cardType;
        }

        public void setCardType(int cardType) {
            this.cardType = cardType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        public String getSlogan() {
            return slogan;
        }

        public void setSlogan(String slogan) {
            this.slogan = slogan;
        }

        public String getClass1() {
            return class1;
        }

        public void setClass1(String class1) {
            this.class1 = class1;
        }

        public int getClassAmount1() {
            return classAmount1;
        }

        public void setClassAmount1(int classAmount1) {
            this.classAmount1 = classAmount1;
        }

        public String getClass2() {
            return class2;
        }

        public void setClass2(String class2) {
            this.class2 = class2;
        }

        public int getClassAmount2() {
            return classAmount2;
        }

        public void setClassAmount2(int classAmount2) {
            this.classAmount2 = classAmount2;
        }

        public String getClass3() {
            return class3;
        }

        public void setClass3(String class3) {
            this.class3 = class3;
        }

        public int getClassAmount3() {
            return classAmount3;
        }

        public void setClassAmount3(int classAmount3) {
            this.classAmount3 = classAmount3;
        }

        public int getWeeks() {
            return weeks;
        }

        public void setWeeks(int weeks) {
            this.weeks = weeks;
        }

        public int getCurrentWeek() {
            return currentWeek;
        }

        public void setCurrentWeek(int currentWeek) {
            this.currentWeek = currentWeek;
        }

        public int getClassCount() {
            return classCount;
        }

        public void setClassCount(int classCount) {
            this.classCount = classCount;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getPromoPrice() {
            return promoPrice;
        }

        public void setPromoPrice(double promoPrice) {
            this.promoPrice = promoPrice;
        }

        public String getBabyBirthday() {
            return babyBirthday;
        }

        public void setBabyBirthday(String babyBirthday) {
            this.babyBirthday = babyBirthday;
        }

        public String getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(String expiresIn) {
            this.expiresIn = expiresIn;
        }
    }
}
