package com.tiancaijiazu.app.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/3/003.
 */

public class PersonalDetailsBean implements Serializable{


    /**
     * code : 0
     * msg : OK
     * result : {"userId":9567974524588032,"follow":8,"fans":8,"likes":39,"article":18,"nickname":"杨旭","avatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","avatarLarge":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_750x750.jpg?750,750","gender":1,"summary":"加油！","isFollow":1,"articleList":[{"articleId":37461830788059136,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/08/12/37461829643014144_332x0.jpg?332,443","title":"记录了","publishTime":"2019-08-12 19:09:56.467","likes":1,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"哈哈骨头燃气热水器","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":37458367639326720,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/08/12/37458362845237248_332x0.jpg?332,443","title":"标题","publishTime":"2019-08-12 18:56:10.788","likes":2,"collect":1,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071936812388352,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071935642177536_332x0.jpg?332,443","title":"阿尔维斯","publishTime":"2019-07-06 20:23:16.971","likes":1,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"这边","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071925567459328,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071924393054208_332x0.jpg?332,443","title":"阿尔维斯","publishTime":"2019-07-06 20:23:14.290","likes":3,"collect":1,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"这边","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071685468721152,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/","nickname":"","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071684231401472_332x0.png?332,443","title":"V5咯了","publishTime":"2019-07-06 20:22:17.046","likes":2,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"嗯路路通","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071558775574528,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071557731192832_332x0.jpg?332,443","title":"啊路","publishTime":"2019-07-06 20:21:46.840","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"睡了","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071357096660992,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071353233707008_332x0.jpg?200,200","title":"莫咯","publishTime":"2019-07-06 20:20:58.756","likes":1,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"嗯呀","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":23291548821229568,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/04/23291544996024320_332x0.jpg?332,443","title":"8卡","publishTime":"2019-07-04 16:42:17.975","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"啊路","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22249765442031616,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22249765194567680_332x0.jpg?332,332","title":"呃呃呃","publishTime":"2019-07-01 19:42:37.462","likes":1,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"啦啦啦啦","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22247955406917632,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22247953909551104_332x0.jpg?332,443","title":"啊啊啊啊","publishTime":"2019-07-01 19:35:25.916","likes":2,"collect":0,"discuss":0,"isLikes":1,"coverPics":"","largePics":"","detail":"阿巴巴","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22228656281751552,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22228655996538880_332x0.jpg?332,332","title":"ghj","publishTime":"2019-07-01 18:18:44.646","likes":1,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"tyi","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22227876799713280,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22227876531277824_332x0.jpg?332,332","title":"yyyyy","publishTime":"2019-07-01 18:15:38.803","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"fttt","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22227253912014848,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22227253249314816_332x0.jpg?332,332","title":"hhh","publishTime":"2019-07-01 18:13:10.296","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"uuuu","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22187310334480384,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22187309155880960_332x0.jpg?332,443","title":"gghhhjj","publishTime":"2019-07-01 15:34:27.004","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"huhvvv","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22181544940670976,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22181544701595648_332x0.jpg?332,711","title":"悲哀的","publishTime":"2019-07-01 15:11:32.427","likes":1,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"丽丽","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22179163414859776,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22179162538250240_332x0.jpg?332,590","title":"国家科技奖","publishTime":"2019-07-01 15:02:04.627","likes":0,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"出来聚聚up","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":21468390849908736,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/06/29/21468389314793472_332x0.jpg?332,443","title":"哈哈哈啦啦啦","publishTime":"2019-06-29 15:57:43.243","likes":2,"collect":1,"discuss":3,"isLikes":0,"coverPics":"","largePics":"","detail":"恐龙妹","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":21450768511012864,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/06/29/21450767340802048_332x0.jpg?200,200","title":"哟哟","publishTime":"2019-06-29 14:47:41.749","likes":1,"collect":0,"discuss":2,"isLikes":0,"coverPics":"","largePics":"","detail":"我我我","babyBirthday":"2018-05-01","isRecommend":0}],"collectList":[{"articleId":58790092281614336,"userId":888,"userAvatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Yx0WxvW5Y4GMYSyC44esQppVfSqd4E3ic6nFk8icOX1FFKL3K6dxxeION7yaL3SjJJDaNbJDjctw2beb6D9yicBfw/132","nickname":"洪峰","picUri":"http://img.tiancaijiazu.com/2019/10/10/58790092025761792_332x0.jpg?332,443","title":"大兴机场打卡","publishTime":"2019-10-10 15:40:50.267","likes":2,"collect":3,"discuss":9,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":1},{"articleId":54141528410558464,"userId":10571612466319360,"userAvatar":"http://img.tiancaijiazu.com/2019/09/27/54140931032616960_148x148.jpg?148,148","nickname":"今天晚上的天气预报预计明天","picUri":"http://img.tiancaijiazu.com/2019/09/27/54141527907241984_332x0.jpg?332,249","title":"猫咪和狗狗一对好朋友","publishTime":"2019-09-27 19:49:06.274","likes":0,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0},{"articleId":48000587752673280,"userId":43114236725039104,"userAvatar":"http://img.tiancaijiazu.com/2019/09/19/51219428473442304_148x148.jpg?148,148","nickname":"王宇","picUri":"http://img.tiancaijiazu.com/2019/09/10/48000586691514368_332x0.png?332,590","title":"aj","publishTime":"2019-09-10 21:07:11.928","likes":2,"collect":1,"discuss":0,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0},{"articleId":46136084886851584,"userId":45834811239174144,"userAvatar":"http://img.tiancaijiazu.com/2019/09/05/46170404657696768_148x148.jpg?148,148","nickname":"巴卡拉卡","picUri":"http://img.tiancaijiazu.com/2019/09/05/46136083238490112_332x0.jpg?332,121","title":"弟弟就到家地产附近的酒店介绍的酒店就到家附近的家小激动","publishTime":"2019-09-05 17:38:19.804","likes":4,"collect":2,"discuss":21,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0}],"babyList":[{"babyId":59905491119247360,"name":"几","gender":2,"birthday":"2018-10-14","avatar":"http://img.tiancaijiazu.com/2019/10/13/59905491119247360_148x148.jpg?148,148","isDefault":1}]}
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
         * userId : 9567974524588032
         * follow : 8
         * fans : 8
         * likes : 39
         * article : 18
         * nickname : 杨旭
         * avatar : http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148
         * avatarLarge : http://img.tiancaijiazu.com/2019/10/14/60180070924423168_750x750.jpg?750,750
         * gender : 1
         * summary : 加油！
         * isFollow : 1
         * articleList : [{"articleId":37461830788059136,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/08/12/37461829643014144_332x0.jpg?332,443","title":"记录了","publishTime":"2019-08-12 19:09:56.467","likes":1,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"哈哈骨头燃气热水器","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":37458367639326720,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/08/12/37458362845237248_332x0.jpg?332,443","title":"标题","publishTime":"2019-08-12 18:56:10.788","likes":2,"collect":1,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。内容。。。。。。。。","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071936812388352,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071935642177536_332x0.jpg?332,443","title":"阿尔维斯","publishTime":"2019-07-06 20:23:16.971","likes":1,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"这边","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071925567459328,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071924393054208_332x0.jpg?332,443","title":"阿尔维斯","publishTime":"2019-07-06 20:23:14.290","likes":3,"collect":1,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"这边","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071685468721152,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/","nickname":"","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071684231401472_332x0.png?332,443","title":"V5咯了","publishTime":"2019-07-06 20:22:17.046","likes":2,"collect":0,"discuss":1,"isLikes":1,"coverPics":"","largePics":"","detail":"嗯路路通","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071558775574528,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071557731192832_332x0.jpg?332,443","title":"啊路","publishTime":"2019-07-06 20:21:46.840","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"睡了","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":24071357096660992,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/06/24071353233707008_332x0.jpg?200,200","title":"莫咯","publishTime":"2019-07-06 20:20:58.756","likes":1,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"嗯呀","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":23291548821229568,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/04/23291544996024320_332x0.jpg?332,443","title":"8卡","publishTime":"2019-07-04 16:42:17.975","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"啊路","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22249765442031616,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22249765194567680_332x0.jpg?332,332","title":"呃呃呃","publishTime":"2019-07-01 19:42:37.462","likes":1,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"啦啦啦啦","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22247955406917632,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22247953909551104_332x0.jpg?332,443","title":"啊啊啊啊","publishTime":"2019-07-01 19:35:25.916","likes":2,"collect":0,"discuss":0,"isLikes":1,"coverPics":"","largePics":"","detail":"阿巴巴","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22228656281751552,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22228655996538880_332x0.jpg?332,332","title":"ghj","publishTime":"2019-07-01 18:18:44.646","likes":1,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"tyi","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22227876799713280,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22227876531277824_332x0.jpg?332,332","title":"yyyyy","publishTime":"2019-07-01 18:15:38.803","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"fttt","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22227253912014848,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22227253249314816_332x0.jpg?332,332","title":"hhh","publishTime":"2019-07-01 18:13:10.296","likes":0,"collect":0,"discuss":1,"isLikes":0,"coverPics":"","largePics":"","detail":"uuuu","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22187310334480384,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22187309155880960_332x0.jpg?332,443","title":"gghhhjj","publishTime":"2019-07-01 15:34:27.004","likes":0,"collect":0,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"huhvvv","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22181544940670976,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22181544701595648_332x0.jpg?332,711","title":"悲哀的","publishTime":"2019-07-01 15:11:32.427","likes":1,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"丽丽","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":22179163414859776,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/07/01/22179162538250240_332x0.jpg?332,590","title":"国家科技奖","publishTime":"2019-07-01 15:02:04.627","likes":0,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"出来聚聚up","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":21468390849908736,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/06/29/21468389314793472_332x0.jpg?332,443","title":"哈哈哈啦啦啦","publishTime":"2019-06-29 15:57:43.243","likes":2,"collect":1,"discuss":3,"isLikes":0,"coverPics":"","largePics":"","detail":"恐龙妹","babyBirthday":"2018-05-01","isRecommend":0},{"articleId":21450768511012864,"userId":9567974524588032,"userAvatar":"http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148","nickname":"杨旭","picUri":"http://img.tiancaijiazu.com/2019/06/29/21450767340802048_332x0.jpg?200,200","title":"哟哟","publishTime":"2019-06-29 14:47:41.749","likes":1,"collect":0,"discuss":2,"isLikes":0,"coverPics":"","largePics":"","detail":"我我我","babyBirthday":"2018-05-01","isRecommend":0}]
         * collectList : [{"articleId":58790092281614336,"userId":888,"userAvatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Yx0WxvW5Y4GMYSyC44esQppVfSqd4E3ic6nFk8icOX1FFKL3K6dxxeION7yaL3SjJJDaNbJDjctw2beb6D9yicBfw/132","nickname":"洪峰","picUri":"http://img.tiancaijiazu.com/2019/10/10/58790092025761792_332x0.jpg?332,443","title":"大兴机场打卡","publishTime":"2019-10-10 15:40:50.267","likes":2,"collect":3,"discuss":9,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":1},{"articleId":54141528410558464,"userId":10571612466319360,"userAvatar":"http://img.tiancaijiazu.com/2019/09/27/54140931032616960_148x148.jpg?148,148","nickname":"今天晚上的天气预报预计明天","picUri":"http://img.tiancaijiazu.com/2019/09/27/54141527907241984_332x0.jpg?332,249","title":"猫咪和狗狗一对好朋友","publishTime":"2019-09-27 19:49:06.274","likes":0,"collect":1,"discuss":0,"isLikes":0,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0},{"articleId":48000587752673280,"userId":43114236725039104,"userAvatar":"http://img.tiancaijiazu.com/2019/09/19/51219428473442304_148x148.jpg?148,148","nickname":"王宇","picUri":"http://img.tiancaijiazu.com/2019/09/10/48000586691514368_332x0.png?332,590","title":"aj","publishTime":"2019-09-10 21:07:11.928","likes":2,"collect":1,"discuss":0,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0},{"articleId":46136084886851584,"userId":45834811239174144,"userAvatar":"http://img.tiancaijiazu.com/2019/09/05/46170404657696768_148x148.jpg?148,148","nickname":"巴卡拉卡","picUri":"http://img.tiancaijiazu.com/2019/09/05/46136083238490112_332x0.jpg?332,121","title":"弟弟就到家地产附近的酒店介绍的酒店就到家附近的家小激动","publishTime":"2019-09-05 17:38:19.804","likes":4,"collect":2,"discuss":21,"isLikes":1,"coverPics":"","largePics":"","detail":"","babyBirthday":"","isRecommend":0}]
         * babyList : [{"babyId":59905491119247360,"name":"几","gender":2,"birthday":"2018-10-14","avatar":"http://img.tiancaijiazu.com/2019/10/13/59905491119247360_148x148.jpg?148,148","isDefault":1}]
         */

        private long userId;
        private int follow;
        private int fans;
        private int likes;
        private int article;
        private String nickname;
        private String avatar;
        private String avatarLarge;
        private int gender;
        private String summary;
        private int isFollow;
        private List<ArticleListBean> articleList;
        private List<CollectListBean> collectList;
        private List<BabyListBean> babyList;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getArticle() {
            return article;
        }

        public void setArticle(int article) {
            this.article = article;
        }

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

        public String getAvatarLarge() {
            return avatarLarge;
        }

        public void setAvatarLarge(String avatarLarge) {
            this.avatarLarge = avatarLarge;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public List<ArticleListBean> getArticleList() {
            return articleList;
        }

        public void setArticleList(List<ArticleListBean> articleList) {
            this.articleList = articleList;
        }

        public List<CollectListBean> getCollectList() {
            return collectList;
        }

        public void setCollectList(List<CollectListBean> collectList) {
            this.collectList = collectList;
        }

        public List<BabyListBean> getBabyList() {
            return babyList;
        }

        public void setBabyList(List<BabyListBean> babyList) {
            this.babyList = babyList;
        }

        public static class ArticleListBean implements Serializable{
            /**
             * articleId : 37461830788059136
             * userId : 9567974524588032
             * userAvatar : http://img.tiancaijiazu.com/2019/10/14/60180070924423168_148x148.jpg?148,148
             * nickname : 杨旭
             * picUri : http://img.tiancaijiazu.com/2019/08/12/37461829643014144_332x0.jpg?332,443
             * title : 记录了
             * publishTime : 2019-08-12 19:09:56.467
             * likes : 1
             * collect : 0
             * discuss : 1
             * isLikes : 1
             * coverPics :
             * largePics :
             * detail : 哈哈骨头燃气热水器
             * babyBirthday : 2018-05-01
             * isRecommend : 0
             */

            private long articleId;
            private long userId;
            private String userAvatar;
            private String nickname;
            private String picUri;
            private String title;
            private String publishTime;
            private int likes;
            private int collect;
            private int discuss;
            private int isLikes;
            private String coverPics;
            private String largePics;
            private String detail;
            private String babyBirthday;
            private int isRecommend;

            public long getArticleId() {
                return articleId;
            }

            public void setArticleId(long articleId) {
                this.articleId = articleId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public int getCollect() {
                return collect;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            public int getDiscuss() {
                return discuss;
            }

            public void setDiscuss(int discuss) {
                this.discuss = discuss;
            }

            public int getIsLikes() {
                return isLikes;
            }

            public void setIsLikes(int isLikes) {
                this.isLikes = isLikes;
            }

            public String getCoverPics() {
                return coverPics;
            }

            public void setCoverPics(String coverPics) {
                this.coverPics = coverPics;
            }

            public String getLargePics() {
                return largePics;
            }

            public void setLargePics(String largePics) {
                this.largePics = largePics;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getBabyBirthday() {
                return babyBirthday;
            }

            public void setBabyBirthday(String babyBirthday) {
                this.babyBirthday = babyBirthday;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }
        }

        public static class CollectListBean implements Serializable{
            /**
             * articleId : 58790092281614336
             * userId : 888
             * userAvatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Yx0WxvW5Y4GMYSyC44esQppVfSqd4E3ic6nFk8icOX1FFKL3K6dxxeION7yaL3SjJJDaNbJDjctw2beb6D9yicBfw/132
             * nickname : 洪峰
             * picUri : http://img.tiancaijiazu.com/2019/10/10/58790092025761792_332x0.jpg?332,443
             * title : 大兴机场打卡
             * publishTime : 2019-10-10 15:40:50.267
             * likes : 2
             * collect : 3
             * discuss : 9
             * isLikes : 1
             * coverPics :
             * largePics :
             * detail :
             * babyBirthday :
             * isRecommend : 1
             */

            private long articleId;
            private long userId;
            private String userAvatar;
            private String nickname;
            private String picUri;
            private String title;
            private String publishTime;
            private int likes;
            private int collect;
            private int discuss;
            private int isLikes;
            private String coverPics;
            private String largePics;
            private String detail;
            private String babyBirthday;
            private int isRecommend;

            public long getArticleId() {
                return articleId;
            }

            public void setArticleId(long articleId) {
                this.articleId = articleId;
            }

            public long getUserId() {
                return userId;
            }

            public void setUserId(long userId) {
                this.userId = userId;
            }

            public String getUserAvatar() {
                return userAvatar;
            }

            public void setUserAvatar(String userAvatar) {
                this.userAvatar = userAvatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPicUri() {
                return picUri;
            }

            public void setPicUri(String picUri) {
                this.picUri = picUri;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(String publishTime) {
                this.publishTime = publishTime;
            }

            public int getLikes() {
                return likes;
            }

            public void setLikes(int likes) {
                this.likes = likes;
            }

            public int getCollect() {
                return collect;
            }

            public void setCollect(int collect) {
                this.collect = collect;
            }

            public int getDiscuss() {
                return discuss;
            }

            public void setDiscuss(int discuss) {
                this.discuss = discuss;
            }

            public int getIsLikes() {
                return isLikes;
            }

            public void setIsLikes(int isLikes) {
                this.isLikes = isLikes;
            }

            public String getCoverPics() {
                return coverPics;
            }

            public void setCoverPics(String coverPics) {
                this.coverPics = coverPics;
            }

            public String getLargePics() {
                return largePics;
            }

            public void setLargePics(String largePics) {
                this.largePics = largePics;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getBabyBirthday() {
                return babyBirthday;
            }

            public void setBabyBirthday(String babyBirthday) {
                this.babyBirthday = babyBirthday;
            }

            public int getIsRecommend() {
                return isRecommend;
            }

            public void setIsRecommend(int isRecommend) {
                this.isRecommend = isRecommend;
            }
        }

        public static class BabyListBean implements Serializable{
            /**
             * babyId : 59905491119247360
             * name : 几
             * gender : 2
             * birthday : 2018-10-14
             * avatar : http://img.tiancaijiazu.com/2019/10/13/59905491119247360_148x148.jpg?148,148
             * isDefault : 1
             */

            private long babyId;
            private String name;
            private int gender;
            private String birthday;
            private String avatar;
            private int isDefault;

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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }
        }
    }
}
