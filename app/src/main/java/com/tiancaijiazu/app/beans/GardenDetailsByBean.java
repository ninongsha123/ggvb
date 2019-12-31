package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class GardenDetailsByBean implements Serializable{
    /**
     * code : 0
     * msg : OK
     * result : {"companyId":41036119596470272,"companyName":"河北省邢台市早教园","logoUri":"http://img.tiancaijiazu.com/2019/08/22/41035503440629760_190x190.png?190,190","scale":"早教","address":"河北省邢台市桥东区开元北路256-1号","longitude":"37.08524350","latitude":"114.51405500","phone":"17326856737","businessHours":"周一至周五 10:00-18:30","services":"0元体验,免费Wi-Fi,主题活动,小班教学,家长休息区","summary":" 金湖县，是江苏省淮安市下辖县，位于江苏省中部，因境内白马湖、宝应湖、高邮湖三湖环绕，由敬爱的周恩来总理定名：\u201c金湖\u201d，象征资源丰富，日出斗金。","pics":"http://img.tiancaijiazu.com/2019/08/22/41042904537501696_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042944807014400_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042946379878400_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947206156288_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947382317056_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947428454400_248x176.png?248,176","certificate":"http://img.tiancaijiazu.com/2019/08/22/41042921901920256_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042971084328960_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042971143049216_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042972258734080_248x176.png?248,176","teacherList":[{"teacherId":41042279439405056,"companyId":41036119596470272,"title":"天天老师","avatar":"http://img.tiancaijiazu.com/2019/08/22/41042269826060288_190x190.png?190,190","teachingAge":2}],"courseList":[{"courseId":41042602568585216,"companyId":41036119596470272,"title":"母乳喂养多久最好","cover":"http://img.tiancaijiazu.com/2019/08/22/41042391955804160_190x190.png?190,190","banner":"http://img.tiancaijiazu.com/2019/08/22/41042420552568832_190x190.png?190,190","courseType":"综合类","monthMin":0,"monthMax":6,"duration":3,"summary":"新生婴儿喂养主要有母乳喂养、人工喂养、混合喂养等方式。其中，母乳喂养指的是用母亲的奶水喂养，是新生儿喂养的最佳方法；人工喂养则是指当母亲因为各种原因而不能进行哺乳时，可采用牛、羊等动物乳制成的婴儿奶粉或其他代乳品进行人工喂养；混合喂养指的是当母亲奶水不足，不能够多次喂养时，配合其他代乳品如牛奶、羊奶等来进行喂养。","pics":"http://img.tiancaijiazu.com/2019/08/22/41042587049660416_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587104186368_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587188072448_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587653640192_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590602235904_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590577070080_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590593847296_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042592829411328_248x176.png?248,176"}]}
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

    public static class ResultBean implements Serializable{
        /**
         * companyId : 41036119596470272
         * companyName : 河北省邢台市早教园
         * logoUri : http://img.tiancaijiazu.com/2019/08/22/41035503440629760_190x190.png?190,190
         * scale : 早教
         * address : 河北省邢台市桥东区开元北路256-1号
         * longitude : 37.08524350
         * latitude : 114.51405500
         * phone : 17326856737
         * businessHours : 周一至周五 10:00-18:30
         * services : 0元体验,免费Wi-Fi,主题活动,小班教学,家长休息区
         * summary :  金湖县，是江苏省淮安市下辖县，位于江苏省中部，因境内白马湖、宝应湖、高邮湖三湖环绕，由敬爱的周恩来总理定名：“金湖”，象征资源丰富，日出斗金。
         * pics : http://img.tiancaijiazu.com/2019/08/22/41042904537501696_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042944807014400_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042946379878400_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947206156288_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947382317056_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042947428454400_248x176.png?248,176
         * certificate : http://img.tiancaijiazu.com/2019/08/22/41042921901920256_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042971084328960_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042971143049216_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042972258734080_248x176.png?248,176
         * teacherList : [{"teacherId":41042279439405056,"companyId":41036119596470272,"title":"天天老师","avatar":"http://img.tiancaijiazu.com/2019/08/22/41042269826060288_190x190.png?190,190","teachingAge":2}]
         * courseList : [{"courseId":41042602568585216,"companyId":41036119596470272,"title":"母乳喂养多久最好","cover":"http://img.tiancaijiazu.com/2019/08/22/41042391955804160_190x190.png?190,190","banner":"http://img.tiancaijiazu.com/2019/08/22/41042420552568832_190x190.png?190,190","courseType":"综合类","monthMin":0,"monthMax":6,"duration":3,"summary":"新生婴儿喂养主要有母乳喂养、人工喂养、混合喂养等方式。其中，母乳喂养指的是用母亲的奶水喂养，是新生儿喂养的最佳方法；人工喂养则是指当母亲因为各种原因而不能进行哺乳时，可采用牛、羊等动物乳制成的婴儿奶粉或其他代乳品进行人工喂养；混合喂养指的是当母亲奶水不足，不能够多次喂养时，配合其他代乳品如牛奶、羊奶等来进行喂养。","pics":"http://img.tiancaijiazu.com/2019/08/22/41042587049660416_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587104186368_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587188072448_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587653640192_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590602235904_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590577070080_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590593847296_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042592829411328_248x176.png?248,176"}]
         */

        private long companyId;
        private String companyName;
        private String logoUri;
        private String scale;
        private String address;
        private String longitude;
        private String latitude;
        private String banner;
        private String phone;
        private String businessHours;
        private String services;
        private String summary;
        private String pics;
        private String certificate;
        private List<TeacherListBean> teacherList;
        private List<CourseListBean> courseList;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public long getCompanyId() {
            return companyId;
        }

        public void setCompanyId(long companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getLogoUri() {
            return logoUri;
        }

        public void setLogoUri(String logoUri) {
            this.logoUri = logoUri;
        }

        public String getScale() {
            return scale;
        }

        public void setScale(String scale) {
            this.scale = scale;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getBusinessHours() {
            return businessHours;
        }

        public void setBusinessHours(String businessHours) {
            this.businessHours = businessHours;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
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

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public List<TeacherListBean> getTeacherList() {
            return teacherList;
        }

        public void setTeacherList(List<TeacherListBean> teacherList) {
            this.teacherList = teacherList;
        }

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public static class TeacherListBean implements Serializable{
            /**
             * teacherId : 41042279439405056
             * companyId : 41036119596470272
             * title : 天天老师
             * avatar : http://img.tiancaijiazu.com/2019/08/22/41042269826060288_190x190.png?190,190
             * teachingAge : 2
             */

            private long teacherId;
            private long companyId;
            private String title;
            private String avatar;
            private int teachingAge;
            private String level;

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public long getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(long teacherId) {
                this.teacherId = teacherId;
            }

            public long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(long companyId) {
                this.companyId = companyId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getTeachingAge() {
                return teachingAge;
            }

            public void setTeachingAge(int teachingAge) {
                this.teachingAge = teachingAge;
            }
        }

        public static class CourseListBean implements Serializable{
            /**
             * courseId : 41042602568585216
             * companyId : 41036119596470272
             * title : 母乳喂养多久最好
             * cover : http://img.tiancaijiazu.com/2019/08/22/41042391955804160_190x190.png?190,190
             * banner : http://img.tiancaijiazu.com/2019/08/22/41042420552568832_190x190.png?190,190
             * courseType : 综合类
             * monthMin : 0
             * monthMax : 6
             * duration : 3
             * summary : 新生婴儿喂养主要有母乳喂养、人工喂养、混合喂养等方式。其中，母乳喂养指的是用母亲的奶水喂养，是新生儿喂养的最佳方法；人工喂养则是指当母亲因为各种原因而不能进行哺乳时，可采用牛、羊等动物乳制成的婴儿奶粉或其他代乳品进行人工喂养；混合喂养指的是当母亲奶水不足，不能够多次喂养时，配合其他代乳品如牛奶、羊奶等来进行喂养。
             * pics : http://img.tiancaijiazu.com/2019/08/22/41042587049660416_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587104186368_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587188072448_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042587653640192_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590602235904_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590577070080_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042590593847296_248x176.png?248,176|http://img.tiancaijiazu.com/2019/08/22/41042592829411328_248x176.png?248,176
             */

            private long courseId;
            private long companyId;
            private String title;
            private String cover;
            private String banner;
            private String courseType;
            private int monthMin;
            private int monthMax;
            private int duration;
            private String summary;
            private String pics;

            public long getCourseId() {
                return courseId;
            }

            public void setCourseId(long courseId) {
                this.courseId = courseId;
            }

            public long getCompanyId() {
                return companyId;
            }

            public void setCompanyId(long companyId) {
                this.companyId = companyId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getBanner() {
                return banner;
            }

            public void setBanner(String banner) {
                this.banner = banner;
            }

            public String getCourseType() {
                return courseType;
            }

            public void setCourseType(String courseType) {
                this.courseType = courseType;
            }

            public int getMonthMin() {
                return monthMin;
            }

            public void setMonthMin(int monthMin) {
                this.monthMin = monthMin;
            }

            public int getMonthMax() {
                return monthMax;
            }

            public void setMonthMax(int monthMax) {
                this.monthMax = monthMax;
            }

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
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
        }
    }
}
