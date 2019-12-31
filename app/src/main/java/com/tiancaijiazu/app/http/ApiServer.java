package com.tiancaijiazu.app.http;

import com.tiancaijiazu.app.activitys.bean.AthomeBean;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.beans.*;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public interface ApiServer {
    /**
     * 刷新Token
     *
     * @param refresh_token
     * @return
     */
    @POST("account/refresh_token")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Call<ToKenBean> getRefreshToken(@Field("refresh_token") String refresh_token);

    /**
     * 获得验证码
     *
     * @param mobile
     * @return
     */
    @POST("account/send_code")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AuthCode> getAuthCode(@Field("mobile") String mobile ,@Field("referrerCode") String referrerCode);

    /**
     * 获取包含用户二维码的图片
     *
     * @param style
     * @return
     */
    @POST("account/qrcode")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<QrcodeBeans> getQrcode(@Field("style") int style);

    /**
     * 获取商品评价列表
     *
     * @param skuId
     * @return
     */
    @POST("Catalog/comment_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CommentListBeans> getCommentListBeans(@Field("skuId") String skuId);

    /**
     * 验证码登录
     *
     * @param mobile
     * @return
     */
    @POST("account/login_by_code")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<String> getToKenBean(@Field("mobile") String mobile, @Field("code") String code, @Field("referrerCode") int referrerCode);

    /**
     * 商品详情
     *
     * @param id
     * @return
     */
    @POST("item")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SpecificationBean> getSpecificationBean(@Field("id") String id);

    /**
     * 发布文章
     *
     * @param requestBodyMap
     * @param file
     * @return
     */
    @Multipart
    @POST("blog/post")
    Observable<PublishArticleBean> getPublishArticleBean(@PartMap Map<String, RequestBody> requestBodyMap, @Part List<MultipartBody.Part> file);

    /**
     * 成长日记列表
     *
     * @return
     */

    @POST("blog/diary_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<GroupListBean> getGroupListBean(@Field("page") int page);

    /**
     * 成长日记详情
     *
     * @return
     */

    @POST("blog/user_diary_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DiaryDetailBean> getDiaryDetailBean(@Field("userId") long userId,@Field("page") int page);
    /**
     * 成长日记修改可见范围
     *
     * @return
     */
    @POST("blog/diary_display_reset")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ArticleReportBeans> getModeifBean(@Field("articleId") String articleId,@Field("display") int display);

    /**
     * 文章列表
     *
     * @param page
     * @return
     */
    @POST("blog/list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ArticleLists> getArticleLists(@Field("page") int page,@Field("articleType") String articleType,@Field("orderByType") int orderByType);

    /**
     * 社区文章、课程记录内容举报
     *
     * @param summary
     * @return
     */
    @POST("blog/article_report")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ArticleReportBeans> getArticleReport(@Field("articleId") Long articleId, @Field("summary") String summary);

    /**
     * 文章内容
     *
     * @param id
     * @return
     */
    @POST("blog/article")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ArticleDatas> getArticleDatas(@Field("id") long id);

    /**
     * 点赞
     *
     * @param contentId
     * @param contentType
     * @return
     */
    @POST("blog/likes")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<LikeBean> getLikeBean(@Field("contentId") long contentId, @Field("contentType") String contentType);

    /**
     * 评论
     *
     * @param articleId
     * @param content
     * @param replyId
     * @return
     */
    @POST("blog/post_discuss")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CommentBean> getCommentBean(@Field("articleId") long articleId, @Field("content") String content, @Field("replyId") String replyId, @Field("discussId") String discussId); /**

    /
     * 保存、修改银行卡信息
     *
     * @param
     * @param
     * @param
     * @return
     */
    @POST("Finance/bankcard_modify")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<BankcardBeans> getBankcardBean(@Field("cardNo") String cardNo, @Field("bank") String bank, @Field("city") String city, @Field("openingBank") String openingBank, @Field("mobile") String mobile, @Field("name") String name, @Field("idNo") String idNo);

    /*
    *
    *获取用户的银行卡信息
    *
    * */
    @POST("Finance/bankcard_info")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<BankcardInfoBeans> getBankcardInfoBean();

    /**
     * 点赞列表
     *
     * @param articleId
     * @param page
     * @return
     */
    @POST("blog/likes_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<LikeListsBean> getLikeListsBean(@Field("articleId") long articleId, @Field("page") int page);

    /**
     * 一級评论列表
     *
     * @param articleId
     * @param page
     * @param size
     * @return
     */
    @POST("blog/discuss_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<OneCommentBean> getOneCommentBean(@Field("articleId") long articleId, @Field("page") int page, @Field("size") String size);

    /**
     * 二級评论列表
     *
     * @param articleId
     * @param page
     * @param size
     * @param replyId
     * @return
     */
    @POST("blog/discuss_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TwoCommentBean> getTwoCommentBean(@Field("articleId") long articleId, @Field("page") int page, @Field("size") String size, @Field("replyId") long replyId);

    /**
     * 删除评论
     *
     * @param discussId
     * @return
     */
    @POST("blog/delete_discuss")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DeleteCommentBean> getDeleteCommentBean(@Field("discussId") long discussId);

    /**
     * 关注
     *
     * @param followId
     * @return
     */
    @POST("blog/follow")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ConcernBean> getConcernBean(@Field("followId") String followId);

    /**
     * 收藏
     *
     * @param articleId
     * @return
     */
    @POST("blog/collect")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CollectBean> getCollectBean(@Field("articleId") String articleId);

    /**
     * 用户中心
     *
     * @param userId
     * @return
     */
    @POST("blog/user_home")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<PersonalDetailsBean> getPersonalDetailsBean(@Field("userId") String userId);

    /**
     * 它的关注列表
     *
     * @param userId
     * @param page
     * @return
     */
    @POST("blog/follow_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ItAttentionBean> getItAttentionBean(@Field("userId") String userId, @Field("page") int page);

    /**
     * 它的粉丝列表
     *
     * @param userId
     * @param page
     * @return
     */
    @POST("blog/fans_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<FansBean> getFansBean(@Field("userId") String userId, @Field("page") int page);

    /**
     * 话题列表
     *
     * @param page
     * @return
     */
    @POST("blog/subject_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TopicListsBean> getTopicListsBean(@Field("page") int page);

    /**
     * 话题内容页
     *
     * @param subjectId
     * @param page
     * @return
     */
    @POST("blog/subject_article_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TopicDataBean> getTopicDataBean(@Field("subjectId") String subjectId, @Field("page") int page, @Field("orderBy") int orderBy);

    /**
     * 搜索话题
     *
     * @param keyWord
     * @param page
     * @return
     */
    @POST("blog/subject_search")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TopicListsBean> getSouTopicListsBean(@Field("keyWord") String keyWord, @Field("page") int page);

    /**
     * 获取课程分类列表
     *
     * @param parentId
     * @return
     */
    @POST("study/catalog_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CollegeParentBean> getCollectsBean(@Field("parentId") String parentId);

    /**
     * 获取课程详情
     *
     * @param courseId
     * @return
     */
    @POST("study/course")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CollegeCourseBean> getCollegeCourseBean(@Field("courseId") String courseId);

    /**
     * 微信登录
     *
     * @param code
     * @return
     */
    @POST("account/login_by_weixin")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<String> getWXBean(@Field("code") String code);

    /**
     * 商品信息
     *
     * @return
     */
    @POST("Catalog/list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ShangpinBean> getShangpin(@Field("id") String id, @Field("page") int page);


    /**
     * 微信绑定手机号
     *
     * @param wxUnionid
     * @param mobile
     * @param code
     * @return
     */
    @POST("account/login_bind_weixin")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<WXbindBean> getWXbindBean(@Field("wxUnionid") String wxUnionid, @Field("mobile") String mobile, @Field("code") String code);

    /**
     * 购物车添加
     *
     * @param stockId
     * @param quantity
     * @return
     */
    @POST("basket/item_add")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<Add_carBean> getAdd_carBean(@Field("skuId") String skuId, @Field("stockId") String stockId, @Field("quantity") int quantity);

    /**
     * 购物车修改
     *
     * @param stokid
     * @param quantity
     * @return
     */
    @POST("basket/item_update")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<Update_carBean> getUpdate_carBean(@Field("stockId") String stokid, @Field("quantity") int quantity);

    /**
     * 购物车删除
     *
     * @param stokid
     * @return
     */
    @POST("basket/item_remove")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<Remove_carBean> getRemove_carBean(@Field("stockId") String stokid);

    /**
     * 购物车列表
     *
     * @param page
     * @return
     */
    @POST("basket/list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<Shopping_carBean> getShopping_carBean(@Field("page") int page);

    /**
     * 新增增加地址
     *
     * @param name
     * @param area
     * @param address
     * @param mobile
     * @param isDefault
     * @return
     */
    @POST("order/consignee_add")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DizhiBean> getDizhiBean(@Field("name") String name, @Field("area") String area, @Field("address") String address
            , @Field("mobile") String mobile, @Field("isDefault") int isDefault);

    /**
     * 取消商城订单
     *
     * @param orderId
     * @return
     */
    @POST("order/order_cancel")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<OrderCannelBeans> getOrderCannelBean(@Field("orderId") Long orderId);

    /**
     * 地址接口
     *
     * @param page
     * @return
     */
    @POST("order/consignee_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SiteBean> getSiteBean(@Field("page") int page);

    /**
     * 删除地址接口
     *
     * @param consigneeId
     * @return
     */
    @POST("order/consignee_remove")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DeleSiteBean> getDeleSiteBean(@Field("consigneeId") String consigneeId);

    /**
     * 设置默认地址
     *
     * @param consigneeId
     * @return
     */
    @POST("order/consignee_setdefault")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TacitlysiteBean> getTacitlysiteBean(@Field("consigneeId") String consigneeId);

    /**
     * 修改收货地址
     *
     * @param consigneeId
     * @param name
     * @param area
     * @param address
     * @param mobile
     * @param isDefault
     * @return
     */
    @POST("order/consignee_update")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<EditAddressBean> getEditAddressBean(@Field("consigneeId") String consigneeId, @Field("name") String name, @Field("area") String area, @Field("address") String address
            , @Field("mobile") String mobile, @Field("isDefault") String isDefault);

    /**
     * 订单列表
     *
     * @param status
     * @param page
     * @return
     */
    @POST("order/order_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<NonPaymentListBean> getNonPaymentListBean(@Field("status") String status, @Field("page") String page);

    /**
     * 创建订单
     *
     * @param requestBodyMap
     * @return
     */
    @Multipart
    @POST("order/order_create")
    Observable<String> getOrderFormBean(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 订单详情
     *
     * @param orderid
     * @return
     */
    @POST("order/order_detail")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<OrderdetailBean> getOrderdetailBean(@Field("orderid") String orderid);

    /**
     * 商城发起微信支付
     *
     * @param orderid
     * @return
     */
    @POST("pay/weixin")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<PayBean> getPayBean(@Field("orderid") String orderid,@Field("couponId") String couponId);

    /**
     * 商城发起支付宝支付
     *
     * @param orderid
     * @return
     */
    @POST("pay/alipay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AlipayBean> getAlipayBean(@Field("orderid") String orderid,@Field("couponId") String couponId);

    /**
     * 学院发起微信支付
     *
     * @param courseId
     * @return
     */
    @POST("pay/study_weixinpay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<PayBean> getPayCourseBean(@Field("courseId") String courseId,@Field("couponId") String couponId);

    /**
     * 学院发起支付宝支付
     *
     * @param courseId
     * @return
     */
    @POST("pay/study_alipay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AlipayBean> getAlipayCourseBean(@Field("courseId") String courseId,@Field("couponId") String couponId);

    /**
     * 确认学院课程不带商品订单
     *
     * @param orderid
     * @return
     */
    @POST("order/study_order_create")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CourseToBuyBean> getCourseToBuyBean(@Field("courseId") String orderid);

    /**
     * 确认学院课程带商品订单
     *
     * @param orderid
     * @return
     */
    @POST("order/study_order_create")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<String> getCourseToBuyBeanComm(@Field("courseId") String orderid, @Field("consigneeName") String consigneeName,
                                              @Field("consigneeMobile") String consigneeMobile, @Field("consigneeAddress") String consigneeAddress);

    /**
     * 用户信息
     *
     * @return
     */
    @POST("account/userinfo")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<UserInfoBean> getUserInfo();

    /**
     * 更改昵称
     *
     * @param name
     * @return
     */
    @POST("account/nickname_modify")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ChangeName> getNameInfo(@Field("nickname") String name);

    /**
     * 更改签名
     *
     * @param geqian
     * @return
     */
    @POST("account/summary_modify")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ChangeGeqian> getGeqianInfo(@Field("summary") String geqian);

    /**
     * 更换头像
     *
     * @param requestBody
     * @return
     */
    @POST("account/avatar_modify")
    Observable<UpBean> getUpBean(@Body RequestBody requestBody);

    /**
     * 修改性别
     *
     * @param gender
     * @return
     */
    @POST("account/gender_modify")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<UpBean> getUpsexBean(@Field("gender") int gender);

    /**
     * 修改地区
     *
     * @param country
     * @param province
     * @param city
     * @return
     */
    @POST("account/address_modify")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<UpBean> getUpAddressBean(@Field("country") String country, @Field("province") String province, @Field("city") String city);

    /**
     * 已购课程
     *
     * @param page
     * @return
     */
    @POST("order/study_order_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AlreadyBoughtCourse> getAlreadyBoughtCourse(@Field("page") int page);

    /**
     * 获取课程列表
     *
     * @param page
     * @param catalogId
     * @return
     */
    @POST("study/list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CourseListBean> getCourseListBean(@Field("page") int page, @Field("catalogId") String catalogId);

    /**
     * 商城类目列表
     *
     * @return
     */
    @POST("Catalog/catalog")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ShopBean> getShopBean(@Field("id") String id);

    /**
     * 宝宝信息列表
     *
     * @return
     */
    @POST("account/babycard_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<BabyMessageBean> getBabyMessageBean();

    /**
     * 添加宝宝信息
     *
     * @return
     */
    @Multipart
    @POST("account/babycard_add")
    /*@Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded*/
    Observable<AddBabyBean> getAddBabyBean(@PartMap Map<String, RequestBody> requestBodyMap, @Part MultipartBody.Part file);

    @POST("account/babycard_add")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AddBabyBean> getAddBabyBean1(@Field("name") String name,@Field("gender") int gender,@Field("birthday") String birthday,@Field("isDefault") int isDefault);
    /**
     * 删除宝宝卡
     *
     * @param babyId
     * @return
     */
    @POST("account/babycard_remove")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DeleteBabyBean> getDeleteBabyBean(@Field("babyId") String babyId);

    /**
     * 修改宝宝卡信息
     *
     * @param babyId
     * @param name
     * @param gender
     * @param birthday
     * @param isDefault
     * @return
     */
    @POST("account/babycard_update")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<UpBabyBean> getUpBabyBean(@Field("babyId") String babyId, @Field("name") String name, @Field("gender") int gender, @Field("birthday") String birthday, @Field("isDefault") int isDefault);
    @Multipart
    @POST("account/babycard_update")
    /*@Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded*/
    Observable<UpBabyBean> getUpBabyBeanPic(@PartMap Map<String, RequestBody> requestBodyMap, @Part MultipartBody.Part file);

    /**
     * 设置默认宝宝卡
     *
     * @param babyId
     * @return
     */
    @POST("account/babycard_setdefault")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SetBabyBean> getSetBabyBean(@Field("babyId") String babyId);

    /**
     * Home里Bannar
     *
     * @return
     */
    @POST("AppPart/home_banner")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<HomeBannerBean> getHomeBannerBean();

    /**
     * 首页短视频接口
     *
     * @return
     */
    @POST("AppPart/home_shortvideo")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<ShortVideoBean> getShortVideoBean();

    /**
     * 检查app最新版本
     *
     * @return
     */
    @POST("AppPart/app_check_version")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AppCheckVersion> getAppCheckBean();

    /**
     * 个人中心用户提交反馈
     *
     * @param summary
     * @return
     */
    @POST("AppPart/member_feedback")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<MemberFeedbackBeans> getMemberFeedbackBean(@Field("summary") String summary, @Field("contact") String contact);

    /**
     * 首页广告位接口
     *
     * @return
     */
    @POST("AppPart/home_shortvideo_ad")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AdPositionIdBean> getAdPositionIdBean();

    /**
     * 歌单列表
     *
     * @param page
     * @param size
     * @return
     */
    @POST("Media/radio_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SongListBean> getSongListBean(@Field("page") int page, @Field("size") int size);

    /**
     * 单曲详情
     *
     * @param musicId
     * @return
     */
    @POST("AppPart/home_radio")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SongBean> getSongBean(@Field("musicId") String musicId);

    /**
     * 获取歌单详情
     * @param musicId
     * @return
     */
    @POST("Media/music")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SongBean> getSongBeanDian(@Field("musicId") String musicId);

    /**
     * 首页早安音乐
     *
     * @return
     */
    @POST("AppPart/home_music_morning")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<SongBean> getMornSongBean();

    /**
     * 首页晚安音乐
     *
     * @return
     */
    @POST("AppPart/home_music_night")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<SongBean> getNightSongBean();


    /**
     * 早安页面
     *
     * @return
     */
    @POST("Media/music_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SongBeanss> getZaoAnBean(@Field("catalogId") String catalogId,@Field("page") int page);

    /**
     * 音乐列表
     *
     * @return
     */
    @POST("Media/song_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<MusicBean> getMusicBran(@Field("catalogId") String catalogId,@Field("page") int page);

    /**
     * 商城轮播图
     *
     * @return
     */
    @POST("AppPart/mall_home_banner")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AdPositionIdBean> getShopAdPositionIdBean();

    /**
     * 首页社区
     *
     * @return
     */
    @POST("AppPart/home_blog_hot")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<HomeBBSbean> getHomeBBSbean();

    /**
     * 测评问卷列表
     *
     * @return
     */
    @POST("Evaluation/subject_list_baby")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<BabyAgeBean> getBabyAgeBean();

    /**
     * 测评对应月龄题目
     *
     * @param subjectId
     * @return
     */
    @POST("Evaluation/subject_baby")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ReviewTheTopicBean> getReviewTheTopicBean(@Field("subjectId") String subjectId);

    /**
     * 获得我的收藏
     *
     * @param userid
     * @param page
     * @return
     */
    @POST("blog/user_collect_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<MyCollectListBean> getMyCollectListBean(@Field("userid") String userid, @Field("page") int page);

    /**
     * 获得我发布的文章
     *
     * @param userid
     * @param page
     * @return
     */
    @POST("blog/user_article_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<MyReleasedListBean> getMyReleasedListBean(@Field("userid") String userid, @Field("page") int page,@Field("articleType") String articleType);

    /**
     * 提交测评
     *
     * @param requestBodyMap
     * @return
     */
    @Multipart
    @POST("Evaluation/report_create")
    Observable<SubmitAppraisalBean> getSubmitAppraisalBean(@PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 宝宝测评结果
     *
     * @param reportId
     * @return
     */
    @POST("Evaluation/report")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<EvaluationResultsBean> getEvaluationResultsBean(@Field("reportId") String reportId);

    /**
     * 宝宝对应的测评列表
     *
     * @param babyId
     * @param page
     * @return
     */
    @POST("Evaluation/report_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ReviewedListBean> getReviewedListBean(@Field("babyId") String babyId, @Field("page") int page, @Field("size") int size);

    /**
     * 获得线下园所列表
     *
     * @param page
     * @return
     */
    @POST("study/company_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ClassGardenBean> getClassGardenBean(@Field("page") int page);

    /**
     * 园所详情
     *
     * @param companyId
     * @return
     */
    @POST("study/company")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<GardenDetailsByBean> getGardenDetailsByBean(@Field("companyId") String companyId);

    /**
     * 提交线下加盟申请
     *
     * @param userName
     * @return
     */
    @POST("study/application")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ToJoinBean> getToJoinBean(@Field("userName") String userName, @Field("userMobile") String userMobile, @Field("address") String address, @Field("summary") String summary);

    /**
     * 提交线下课堂预约申请
     *
     * @param babyName
     * @param userMobile
     * @param babyAge
     * @param courseId
     * @return
     */
    @POST("study/appointment")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ToJoinBean> getToJoinBabyBean(@Field("babyName") String babyName, @Field("userMobile") String userMobile, @Field("babyAge") String babyAge, @Field("courseId") String courseId);

    /**
     * 早教课程列表
     *
     * @param page
     * @return
     */
    @POST("study/athome_course_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<EarlyCourseListBean> getEarlyCourseListBean(@Field("page") int page);

    /**
     * 早教课程详情
     *
     * @param courseId
     * @return
     */
    @POST("study/athome_course")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<EarlyCourseBean> getEarlyCourseBean(@Field("courseId") String courseId);

    /**
     * 获取优惠券列表
     *
     * @param status
     * @param page
     * @return
     */
    @POST("Market/coupon_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CouponBean> getCouponBean(@Field("status") int status, @Field("page") int page);

    /**
     * 领取家庭早教课程专享优惠券
     *
     * @return
     */
    @POST("Market/get_coupon_only_athome")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AthomeBean> getAthomeBean();

    /**
     * 获取某使用场景下的有效优惠券数量
     *
     * @return
     */
    @POST("Market/valid_coupons")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ValidCouponBeans> getValidCouponsBean(@Field("tradeType") int status);

    /**
     * 会员级别列表
     *
     * @return
     */
    @POST("account/viplevel_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<VipListBean> getVipListBean();

    /**
     * 创建VIP会员升级订单
     *
     * @param vipLevel
     * @return
     */
    @POST("order/member_vip_order_create")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<VipOrderBean> getVipOrderBean(@Field("vipLevel") int vipLevel);

    /**
     * vip微信支付
     *
     * @param vipLevel
     * @return
     */
    @POST("pay/member_vip_weixinpay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<PayBean> getVipPayBean(@Field("vipLevel") int vipLevel);

    /**
     * 学卡微信支付
     *
     * @param cardType
     * @return
     */
    @POST("pay/study_card_weixinpay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<PayBean> getPaycardBean(@Field("cardType") int cardType,@Field("babyBirthday") String babyBirthday,@Field("couponId") String couponId);

    /**
     * vip支付宝支付
     *
     * @param vipLevel
     * @return
     */
    @POST("pay/member_vip_alipay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AlipayBean> getVipAlipayBean(@Field("vipLevel") int vipLevel);

    /**
     * 学卡支付宝支付
     *
     * @param cardType
     * @return
     */
    @POST("pay/study_card_alipay")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<AlipayBean> getAlicardpayBean(@Field("cardType") int cardType,@Field("babyBirthday") String babyBirthday,@Field("couponId") String couponId);

    /**
     * 商城广告_首页优选爆品
     *
     * @return
     */
    @POST("AppPart/mall_ad_1")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdOne();

    /**
     * 商城广告_首页自有品牌推荐
     *
     * @return
     */
    @POST("AppPart/mall_ad_2")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdTwo();

    /**
     * 商城广告_自有品牌_好物推荐
     *
     * @return
     */
    @POST("AppPart/mall_ad_3")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdThree();

    /**
     * 商城广告_自有品牌_顶部Banner
     *
     * @return
     */
    @POST("AppPart/mall_ad_6")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdSix();

    /**
     * 商城广告_母婴服饰_好物推荐
     *
     * @return
     */
    @POST("AppPart/mall_ad_4")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdFour();

    /**
     * 商城广告_儿童车床_好物推荐
     *
     * @return
     */
    @POST("AppPart/mall_ad_5")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdFive();

    /**
     * 商城广告_母婴服饰_顶部Banner
     *
     * @return
     */
    @POST("AppPart/mall_ad_7")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdSeven();

    /**
     * 商城广告_儿童车床_顶部Banner
     *
     * @return
     */
    @POST("AppPart/mall_ad_8")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdEight();

    /**
     * 商城广告_首页首屏小Banner
     *
     * @return
     */
    @POST("AppPart/mall_ad_9")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdNine();

    /**
     * 商城广告_详情页_为你推荐
     *
     * @return
     */
    @POST("AppPart/mall_ad_10")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdTen();

    /**
     * 商城广告_详情页_看了又看
     *
     * @return
     */
    @POST("AppPart/mall_ad_11")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getMallAdEleven();

    /**
     * 学院广告_首页短视频体验课
     *
     * @return
     */
    @POST("AppPart/study_ad_1")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getStudyAdOne();

    /**
     * 学院广告_首页短音频体验课
     *
     * @return
     */
    @POST("AppPart/study_ad_2")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBeanTwo> getStudyAdTwo();

    /**
     * 学院广告_早教学院新课速递
     *
     * @return
     */
    @POST("AppPart/study_ad_3")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getStudyAdThree();

    /**
     * 学院广告_早教学院好课推荐
     *
     * @return
     */
    @POST("AppPart/study_ad_4")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getStudyAdFour();

    /**
     * 学院广告_智慧学院精品推荐
     *
     * @return
     */
    @POST("AppPart/study_ad_5")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<MallAdBean> getStudyAdFive();

    /**
     * 精选话题列表，用于社区首页顶部导航
     *
     * @return
     */
    @POST("blog/subject_top")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<CommTopicSwitcherBean> getCommTopicSwitcherBean();

    /**
     * 获取已关注作者的文章列表
     *
     * @param page
     * @return
     */
    @POST("blog/article_list_follow")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ArticleLists> getAttentionBean(@Field("page") int page,@Field("articleType") String articleType);

    /**
     * 发布商品评价
     *
     * @param
     * @return
     */
    @Multipart
    @POST("Catalog/post_comment")
    Observable<Comment> getCommentsBean(@PartMap Map<String, RequestBody> requestBodyMap, @Part List<MultipartBody.Part> file);

    /**
     * 获取当前推送的课程详情
     *
     * @return
     */
    @POST("study/athome_current_course")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<FormalCurriculumBean> getFormalCurriculumBean();

    /**
     * 获取家庭早教学习卡列表
     *
     * @return
     */
    @POST("study/study_card_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<CardTypeBean> getCardTypeBean();

    /**
     * 获取APP首页每日小游戏列表
     *
     * @return
     */
    @POST("study/study_game_homepage")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<GamehomePageBeans> getGameHomePageBean();

    /**
     * 创建家庭早教学习卡订单
     *
     * @return
     */
    @POST("order/study_card_order_create")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<StudyCardOrderCreateBenas> getStudyCardOrderCreate(@Field("cardType") int cardType,@Field("babyBirthday") String babyBirthday);

    /**
     * 用户当前卡类型
     * @return
     */
    @POST("study/study_card_current")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<UserCardTypeBean> getUserCardTypeBean();

    /**
     * 提交课后反馈，获取推荐游戏
     * @param contentsId
     * @param feedbackIds
     * @return
     */
    @POST("study/study_feedback")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<SubmitBean> getSubmitBean(@Field("contentsId") String contentsId, @Field("feedbackIds") String feedbackIds);

    /**
     * 获取短视频分类列表
     *
     * @param catalogId
     * @return
     */
    @POST("AppPart/home_shortvideo_catalog")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<RespectivelyBean> getRespectivelyBean(@Field("catalogId") String catalogId);

    /**
     *home_shortvideo_list
     * @param catalogId
     * @param page
     * @return
     */
    @POST("AppPart/home_shortvideo_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<MinVideoBean> getMinVideoBean(@Field("catalogId") String catalogId, @Field("page") int page);

    /**
     * 获取用户的收入汇总信息
     * @return
     */
    @POST("Market/income_summary")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<IncomeBean> getIncomeBean();

    /**
     * 视频，领取试听卡页面专用
     * @return
     */
    @POST("study/study_card_try_video")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AuditionBean> getAuditionBean();

    /**
     * 获取用户的收入明细列表
     * @param page
     * @return
     */
    @POST("Market/income_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DetailBean> getDetailBean(@Field("page") int page,@Field("tradeType") int tradeType);

    /**
     * 获取我的团队成员列表
     * @return
     */
    @POST("account/myteam")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<TeamBean> getTeamBean(@Field("vipLevel") String vipLevel,@Field("page") int page);

    /**
     * H5地址列表，供APP调起H5模块专用
     * @return
     */
    @POST("AppPart/h5_urls")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<BringBean> getBringBean();

    /**
     * WEB收藏、取消收藏
     *
     * @param articleId
     * @param articleType
     * @param title
     * @param summary
     * @param pic
     * @param url
     * @return
     */
    @POST("Wiki/collect")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CollectBean> getWebCollectBean(@Field("articleId") String articleId, @Field("articleType") String articleType, @Field("title") String title, @Field("summary") String summary, @Field("pic") String pic, @Field("url") String url);

    /**
     * WEB验证文章是否已收藏
     * @param articleId
     * @return
     */
    @POST("Wiki/isCollected")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<IsCollectBean> getIsCollectBean(@Field("articleId") String articleId);

    /**
     * 获取用户已收藏的百科文章列表
     * @param articleType
     * @return
     */
    @POST("Wiki/collectList")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<WebCollectListBean> getWebCollectListBean(@Field("articleType") String articleType,@Field("page") int page);

    /**
     * 删除社区文章
     * @param articleId
     * @returnF
     */
    @POST("blog/delete_article")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<DeleArticelBean> getDeleArticelBean(@Field("articleId") String articleId);

    /**
     * 当前正在上课的总人数
     * @return
     */
    @POST("study/study_online_count")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<PeoSumBean> getPeoSumBean();

    /**
     * 当前领取试听卡的用户列表
     * @return
     */
    @POST("study/study_card_try_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<GetCardsBean> getGetCardsBean();

    /**
     * 收藏、取消收藏短视频
     * @param videoId
     * @return
     */
    @POST("AppPart/shortvideo_collect")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ShortCollectBean> getShortCollectBean(@Field("videoId") String videoId);

    /**
     * 检查是否已收藏短视频
     * @param videoId
     * @return
     */
    @POST("AppPart/shortvideo_isCollected")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ShortIsCollectBean> getShortIsCollectBean(@Field("videoId") String videoId);

    /**
     * 用户收藏的短视频列表
     * @param page
     * @return
     */
    @POST("AppPart/shortvideo_collect_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<ShortCollectListBean> getShortCollectListBean(@Field("page") int page);

    /**
     * 按宝宝生日获取当前问卷ID
     * @param babyId
     * @return
     */
    @POST("Evaluation/current_subject_baby")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<GetSubIDBean> getGetSubIDBean(@Field("babyId") String babyId);

    /**
     *
     * 免费获取“推广大使”身份标识
     */
    @POST("account/free_update_vip_level")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<UpgradepromotionBean> getPromotionamBassador();


    /**
     *
     * 用户可提现余额
     */
    @POST("available_amount")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    Observable<AmountBean> getAmount();
    /**
     *
     * 提现申请
     */
    @POST("cash_out_apply")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CashoutBean> getApply(@Field("cardId") String cardid,@Field("cash") String jine);

    /**
     *
     * 提现记录列表
     */
    @POST("cash_out_list")
    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    Observable<CashOutListBean> getDetail(@Field("page") String page);
}