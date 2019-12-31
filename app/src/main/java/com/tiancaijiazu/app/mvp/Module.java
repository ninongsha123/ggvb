package com.tiancaijiazu.app.mvp;


import android.util.Log;

import com.tiancaijiazu.app.activitys.bean.AthomeBean;
import com.tiancaijiazu.app.activitys.bean.CouponBean;
import com.tiancaijiazu.app.base.module.HttpFinishCallBack;
import com.tiancaijiazu.app.beans.*;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.fragments.beans.CollegeParentBean;
import com.tiancaijiazu.app.http.BaseObserver;
import com.tiancaijiazu.app.http.HttpManager;
import com.tiancaijiazu.app.http.RxUtils;
import com.tiancaijiazu.app.jpush.Logger;
import com.tiancaijiazu.app.utils.HttpUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2019/4/22/022.
 */

public class Module {
    //接口继承回调接口
    public interface Callback<V> extends HttpFinishCallBack {
        //向P层传输数据
        void setData(V data, DifferentiateEnum differentiateEnum);
    }

    public void getData(final Callback Callback, Object obj, final DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            //验证码
            case REGISTER:
                break;
            case CODE:
                Callback.setAnimation();
                HashMap<String, String> stringStringHashMap2 = (HashMap<String, String>) obj;
                String phone = stringStringHashMap2.get("phone");
                String referrerCode2 = stringStringHashMap2.get("referrerCode");
                HttpManager.getInstance().getServer().getAuthCode(phone,referrerCode2)
                        .compose(RxUtils.<AuthCode>rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AuthCode>(Callback) {
                            @Override
                            public void onNext(AuthCode authCode) {
                                Callback.setData(authCode, differentiateEnum);
                                Callback.setHidoAnimation();
                            }
                        });
                break;
            //验证码登录
            case MIMALOFIN:
                break;
            case CODELOGIN:
                Callback.setAnimation();
                HashMap<String, Object> map = (HashMap<String, Object>) obj;
                String phone1 = (String) map.get("phone");
                String code = (String) map.get("code");
                int referrerCode = (int) map.get("referrerCode");
                HttpManager.getInstance().getServerStr().getToKenBean(phone1, code,referrerCode)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<String>(Callback) {
                            @Override
                            public void onNext(String s) {
                                Callback.setHidoAnimation();
                                Callback.setData(s, differentiateEnum);
                            }
                        });
                break;
            //商品规格
            case SPECIFICATIONOFGOODS:
                Callback.setAnimation();
                String id = (String) obj;
                HttpManager.getInstance().getServerCatalog().getSpecificationBean(id)
                        .compose(RxUtils.<SpecificationBean>rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SpecificationBean>(Callback) {
                            @Override
                            public void onNext(SpecificationBean specificationBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(specificationBean, differentiateEnum);
                            }
                        });
                break;
            //发布文章
            case PUBLISHARTICLE:
                Callback.setAnimation();
                HashMap<String, Object> map1 = (HashMap<String, Object>) obj;
                HttpManager.getInstance().getServer().getPublishArticleBean(HttpUtils.getReleaseTopic(map1), HttpUtils.getReleaseTopic1(map1))
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PublishArticleBean>(Callback) {
                            @Override
                            public void onNext(PublishArticleBean publishArticleBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(publishArticleBean, differentiateEnum);
                            }
                        });
//                HttpManager.getInstance().getServer().getPublishArticleBean(HttpUtils.getReleaseTopic(map1), HttpUtils.getReleaseTopic1(map1))
//                        .compose(RxUtils.rxOBserableSchedulerHelper())
//                        .subscribe(new Observer<PublishArticleBean>() {
//                            @Override
//                            public void onSubscribe(Disposable d) {
//
//                            }
//
//                            @Override
//                            public void onNext(PublishArticleBean publishArticleBean) {
//                                Log.d("onNext", "onNext: "+publishArticleBean.toString());
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.d("onError", "onError: "+e.getMessage());
//                            }
//
//                            @Override
//                            public void onComplete() {
//
//                            }
//                        });
                break;
            case GROUPLIST:
                Callback.setAnimation();
                HashMap<String, Object> mapi = (HashMap<String, Object>) obj;
                int page26 = (int) mapi.get("page");
                HttpManager.getInstance().getServer().getGroupListBean(page26)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<GroupListBean>(Callback) {
                            @Override
                            public void onNext(GroupListBean groupListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(groupListBean, differentiateEnum);
                            }
                        });
                break;
            case MODIFIER:
                Callback.setAnimation();
                HashMap<String, Object> mapk = (HashMap<String, Object>) obj;
                String articleId9 = (String) mapk.get("articleId");
                int display = (int) mapk.get("display");
                HttpManager.getInstance().getServer().getModeifBean(articleId9,display)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ArticleReportBeans>(Callback) {
                            @Override
                            public void onNext(ArticleReportBeans groupListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(groupListBean, differentiateEnum);
                            }
                        });
                break;
            case DIARYDEITL:
                Callback.setAnimation();
                HashMap<String, Object> mapdd = (HashMap<String, Object>) obj;
                long userId4 = (long) mapdd.get("userId");
                int pagesss = (int) mapdd.get("page");
                HttpManager.getInstance().getServer().getDiaryDetailBean(userId4,pagesss)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DiaryDetailBean>(Callback) {
                            @Override
                            public void onNext(DiaryDetailBean groupListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(groupListBean, differentiateEnum);
                            }
                        });
                break;
            case ARTICLELISTS:
                Callback.setAnimation();
                HashMap<String, Object> stringStringHashMap = (HashMap<String, Object>) obj;
                int page = (int) stringStringHashMap.get("page");
                String articleType = (String) stringStringHashMap.get("articleType");
                int orderByType = (int) stringStringHashMap.get("orderByType");
                HttpManager.getInstance().getServer().getArticleLists(page,articleType,orderByType)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ArticleLists>(Callback) {
                            @Override
                            public void onNext(ArticleLists articleLists) {
                                Callback.setHidoAnimation();
                                Callback.setData(articleLists, differentiateEnum);
                            }
                        });
                break;
            case ARTICLEDATAS:
                Callback.setAnimation();
                long articleId = (long) obj;
                HttpManager.getInstance().getServer().getArticleDatas(articleId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ArticleDatas>(Callback) {
                            @Override
                            public void onNext(ArticleDatas articleDatas) {
                                Callback.setHidoAnimation();
                                Callback.setData(articleDatas, differentiateEnum);
                            }
                        });
                break;
            case GIVEALIKE:
                HashMap<String, Object> map2 = (HashMap<String, Object>) obj;
                long articleIdLists = (long) map2.get("articleId");
                String contentType = (String) map2.get("contentType");
                Log.i("yx1456", articleIdLists+"getData: "+contentType);
                HttpManager.getInstance().getServer().getLikeBean(articleIdLists, contentType)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<LikeBean>(Callback) {
                            @Override
                            public void onNext(LikeBean likeBean) {
                                Callback.setData(likeBean, differentiateEnum);
                            }
                        });
                break;
            case COMMENT:
                Callback.setAnimation();
                HashMap<String, Object> map3 = (HashMap<String, Object>) obj;
                long article = (long) map3.get("article");
                String content = (String) map3.get("content");
                String replyId = (String) map3.get("replyId");
                String discussId = (String) map3.get("discussId");
                HttpManager.getInstance().getServer().getCommentBean(article, content, replyId, discussId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CommentBean>(Callback) {
                            @Override
                            public void onNext(CommentBean commentBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(commentBean, differentiateEnum);
                            }
                        });

                break;
            case LIKELISTS:
                Callback.setAnimation();
                HashMap<String, Object> map4 = (HashMap<String, Object>) obj;
                long articleId1 = (long) map4.get("articleId");
                int page1 = (int) map4.get("page");
                HttpManager.getInstance().getServer().getLikeListsBean(articleId1, page1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<LikeListsBean>(Callback) {
                            @Override
                            public void onNext(LikeListsBean likeListsBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(likeListsBean, differentiateEnum);
                            }
                        });
                break;
            case TWOCOMMENTLISTS:
                HashMap<String, Object> map5 = (HashMap<String, Object>) obj;
                long articleId2 = (long) map5.get("articleId");
                long replyId1 = (long) map5.get("replyId");
                int page2 = (int) map5.get("page");
                HttpManager.getInstance().getServer().getTwoCommentBean(articleId2, page2, "", replyId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TwoCommentBean>(Callback) {
                            @Override
                            public void onNext(TwoCommentBean twoCommentBean) {
                                Callback.setData(twoCommentBean, differentiateEnum);
                            }
                        });
                break;
            case ONECOMMENTLISTS:
                HashMap<String, Object> map6 = (HashMap<String, Object>) obj;
                long articleId3 = (long) map6.get("articleId");
                int page3 = (int) map6.get("page");
                HttpManager.getInstance().getServer().getOneCommentBean(articleId3, page3, "")
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<OneCommentBean>(Callback) {
                            @Override
                            public void onNext(OneCommentBean oneCommentBean) {
                                Callback.setData(oneCommentBean, differentiateEnum);
                            }
                        });
                break;
            case DELETECOMMENT:
                long discussId1 = (long) obj;
                HttpManager.getInstance().getServer().getDeleteCommentBean(discussId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DeleteCommentBean>(Callback) {
                            @Override
                            public void onNext(DeleteCommentBean deleteCommentBean) {
                                Callback.setData(deleteCommentBean, differentiateEnum);
                            }
                        });
                break;
            case CONCERN:
                String userId = (String) obj;
                HttpManager.getInstance().getServer().getConcernBean(userId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ConcernBean>(Callback) {
                            @Override
                            public void onNext(ConcernBean concernBean) {
                                Callback.setData(concernBean, differentiateEnum);
                            }
                        });
                break;
            case COLLECT:
                String articleId4 = (String) obj;
                HttpManager.getInstance().getServer().getCollectBean(articleId4)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CollectBean>(Callback) {
                            @Override
                            public void onNext(CollectBean collectBean) {
                                Callback.setData(collectBean, differentiateEnum);
                            }
                        });
                break;
            case USERCENTER:
                String userId1 = (String) obj;
                HttpManager.getInstance().getServer().getPersonalDetailsBean(userId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PersonalDetailsBean>(Callback) {
                            @Override
                            public void onNext(PersonalDetailsBean personalDetailsBean) {
                                Callback.setData(personalDetailsBean, differentiateEnum);
                            }
                        });
                break;
            case REFRESH:
                List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();

                /*HttpManager.getInstance().getServer().getRefreshToken(select.get(select.size() - 1).getRefresh_token())
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ToKenBean>(Callback) {
                            @Override
                            public void onNext(ToKenBean toKenBean) {
                                Callback.setData(toKenBean, differentiateEnum);
                            }
                        });*/
                break;
            case ITATTENTION:
                HashMap<String, Object> map7 = (HashMap<String, Object>) obj;
                String userId2 = (String) map7.get("userId");
                int page4 = (int) map7.get("page");

                HttpManager.getInstance().getServer().getItAttentionBean(userId2, page4)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ItAttentionBean>(Callback) {
                            @Override
                            public void onNext(ItAttentionBean itAttentionBean) {
                                Callback.setData(itAttentionBean, differentiateEnum);
                            }
                        });
                break;
            case ITFANS:
                HashMap<String, Object> map8 = (HashMap<String, Object>) obj;
                String userId3 = (String) map8.get("userId");
                int page5 = (int) map8.get("page");

                HttpManager.getInstance().getServer().getFansBean(userId3, page5)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<FansBean>(Callback) {
                            @Override
                            public void onNext(FansBean fansBean) {
                                Callback.setData(fansBean, differentiateEnum);
                            }
                        });
                break;
            case TOPICLISTS:
                int page6 = (int) obj;

                HttpManager.getInstance().getServer().getTopicListsBean(page6)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TopicListsBean>(Callback) {
                            @Override
                            public void onNext(TopicListsBean topicListsBean) {
                                Callback.setData(topicListsBean, differentiateEnum);
                            }
                        });
                break;
            case TOPICDATAS:
                HashMap<String, Object> map9 = (HashMap<String, Object>) obj;
                String subjectId = (String) map9.get("subjectId");
                int page7 = (int) map9.get("page");
                int orderBy = (int) map9.get("orderBy");
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getTopicDataBean(subjectId, page7, orderBy)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TopicDataBean>(Callback) {
                            @Override
                            public void onNext(TopicDataBean topicDataBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(topicDataBean, differentiateEnum);
                            }
                        });
                break;
            case SOUTOPICLIST:
                HashMap<String, Object> map10 = (HashMap<String, Object>) obj;
                String keyWord = (String) map10.get("keyWord");
                int page8 = (int) map10.get("page");

                HttpManager.getInstance().getServer().getSouTopicListsBean(keyWord, page8)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TopicListsBean>(Callback) {
                            @Override
                            public void onNext(TopicListsBean topicListsBean) {
                                Callback.setData(topicListsBean, differentiateEnum);
                            }
                        });
                break;
            //获取课程分类列表
            case PARENTID:
                String parentId = (String) obj;
                HttpManager.getInstance().getServer().getCollectsBean(parentId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CollegeParentBean>(Callback) {
                            @Override
                            public void onNext(CollegeParentBean collectBean) {
                                Callback.setData(collectBean, differentiateEnum);
                            }
                        });
                break;
            //获取课程详情
            case COURSEID:
                String courseid = (String) obj;
                HttpManager.getInstance().getServer().getCollegeCourseBean(courseid)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CollegeCourseBean>(Callback) {
                            @Override
                            public void onNext(CollegeCourseBean collegeCourseBean) {
                                Callback.setData(collegeCourseBean, differentiateEnum);
                            }
                        });
                break;
            case WXLOGIN:
                Callback.setAnimation();
                String code1 = (String) obj;
                //Log.i("yx123==", "getData: "+code1);
                HttpManager.getInstance().getServerWx().getWXBean(code1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<String>(Callback) {
                            @Override
                            public void onNext(String s) {
                                Callback.setHidoAnimation();
                                Callback.setData(s, differentiateEnum);
                            }
                        });
                break;
            case WXBINDING:
                HashMap<String, String> hashMap = (HashMap<String, String>) obj;
                String wxOpenid = hashMap.get("wxOpenid");
                String phone2 = hashMap.get("phone");
                String code2 = hashMap.get("code");
                HttpManager.getInstance().getServer().getWXbindBean(wxOpenid, phone2, code2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<WXbindBean>(Callback) {
                            @Override
                            public void onNext(WXbindBean wXbindBean) {
                                Callback.setData(wXbindBean, differentiateEnum);
                            }
                        });
                break;
            //购物车添加
            case ADDSHOPCAR:
                HashMap<String, Object> map12 = (HashMap<String, Object>) obj;
                String skuId = (String) map12.get("skuId");
                String stockId3 = (String) map12.get("stockId");
                int quantity1 = (int) map12.get("quantity");
                HttpManager.getInstance().getServer().getAdd_carBean(skuId, stockId3, quantity1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<Add_carBean>(Callback) {
                            @Override
                            public void onNext(Add_carBean add_carBean) {
                                Callback.setData(add_carBean, differentiateEnum);
                            }
                        });
                break;
            //购物车修改
            case UPDATESHOPCAT:
                HashMap<String, Object> map11 = (HashMap<String, Object>) obj;
                String stockId1 = (String) map11.get("stockId");
                int quantity = (int) map11.get("quantity");
                HttpManager.getInstance().getServer().getUpdate_carBean(stockId1, quantity)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<Update_carBean>(Callback) {
                            @Override
                            public void onNext(Update_carBean update_carBean) {
                                Callback.setData(update_carBean, differentiateEnum);
                            }
                        });
                break;
            //购物车删除
            case REMOVESHOPCAR:
                String stockId2 = (String) obj;
                HttpManager.getInstance().getServer().getRemove_carBean(stockId2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<Remove_carBean>(Callback) {
                            @Override
                            public void onNext(Remove_carBean remove_carBean) {
                                Callback.setData(remove_carBean, differentiateEnum);
                            }
                        });
                break;
            //购物车列表
            case SHOPPINGLIST:
                int pages = (int) obj;
                HttpManager.getInstance().getServer().getShopping_carBean(pages)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<Shopping_carBean>(Callback) {
                            @Override
                            public void onNext(Shopping_carBean shopping_carBean) {
                                Callback.setData(shopping_carBean, differentiateEnum);
                            }
                        });
                break;
            //商品列表
            case SHANGPINLIST:
                HashMap<String, Object> map17 = (HashMap<String, Object>) obj;
                String id1 = (String) map17.get("id");
                int page20 = (int) map17.get("page");
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getShangpin(id1, page20)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShangpinBean>(Callback) {
                            @Override
                            public void onNext(ShangpinBean shangpinBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(shangpinBean, differentiateEnum);
                            }
                        });

                break;
            //增加收货地址
            case ADDDIZHI:
                HashMap<String, Object> map13 = (HashMap<String, Object>) obj;
                String name = (String) map13.get("name");
                String area = (String) map13.get("area");
                String address = (String) map13.get("address");
                String mobile = (String) map13.get("mobile");
                int isDefault = (int) map13.get("isDefault");
                HttpManager.getInstance().getServer().getDizhiBean(name, area, address, mobile, isDefault)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DizhiBean>(Callback) {
                            @Override
                            public void onNext(DizhiBean dizhiBean) {
                                Callback.setData(dizhiBean, differentiateEnum);
                            }
                        });

                break;
            //收货地址列表
            case SITELIST:
                int page9 = (int) obj;
                HttpManager.getInstance().getServer().getSiteBean(page9)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SiteBean>(Callback) {
                            @Override
                            public void onNext(SiteBean siteBean) {
                                Callback.setData(siteBean, differentiateEnum);
                            }
                        });
                break;
            //删除地址
            case DELESITE:
                String consigneeId = (String) obj;
                HttpManager.getInstance().getServer().getDeleSiteBean(consigneeId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DeleSiteBean>(Callback) {
                            @Override
                            public void onNext(DeleSiteBean deleSiteBean) {
                                Callback.setData(deleSiteBean, differentiateEnum);
                            }
                        });
                break;
            //设置默认地址
            case TACITLYSITE:
                String consigneeId1 = (String) obj;
                HttpManager.getInstance().getServer().getTacitlysiteBean(consigneeId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TacitlysiteBean>(Callback) {
                            @Override
                            public void onNext(TacitlysiteBean tacitlysiteBean) {
                                Callback.setData(tacitlysiteBean, differentiateEnum);
                            }
                        });
                break;
            //修改地址
            case EDITADDRESS:
                HashMap<String, String> map14 = (HashMap<String, String>) obj;
                String consigneeId2 = map14.get("consigneeId");
                String name1 = map14.get("name");
                String area1 = map14.get("area");
                String address1 = map14.get("address");
                String mobile1 = map14.get("mobile");
                String isDefault1 = map14.get("isDefault");
                HttpManager.getInstance().getServer().getEditAddressBean(consigneeId2, name1, area1, address1, mobile1, isDefault1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<EditAddressBean>(Callback) {
                            @Override
                            public void onNext(EditAddressBean editAddressBean) {
                                Callback.setData(editAddressBean, differentiateEnum);
                            }
                        });
                break;
            case ORDERLIST:
                HashMap<String, String> map15 = (HashMap<String, String>) obj;
                String status = map15.get("status");
                String page10 = map15.get("page");
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getNonPaymentListBean(status, page10)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<NonPaymentListBean>(Callback) {
                            @Override
                            public void onNext(NonPaymentListBean nonPaymentListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(nonPaymentListBean, differentiateEnum);
                            }
                        });
                break;
            case ORDERFORM:
                HashMap<String, String> map16 = (HashMap<String, String>) obj;
                HttpManager.getInstance().getServerStr().getOrderFormBean(HttpUtils.getOrderForm(map16))
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<String>(Callback) {
                            @Override
                            public void onNext(String s) {
                                Callback.setData(s, differentiateEnum);
                            }
                        });
                break;
            case ORDERDETAIL:
                String orderid = (String) obj;
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getOrderdetailBean(orderid)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<OrderdetailBean>(Callback) {
                            @Override
                            public void onNext(OrderdetailBean orderdetailBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(orderdetailBean, differentiateEnum);
                            }
                        });
                break;
            case PAYBEAN:
                HashMap<String,String> stringHashMap = (HashMap<String, String>) obj;
                String orderid1 = stringHashMap.get("orderId");
                String couponId = stringHashMap.get("couponId");
                HttpManager.getInstance().getServer().getPayBean(orderid1,couponId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PayBean>(Callback) {
                            @Override
                            public void onNext(PayBean payBean) {
                                Callback.setData(payBean, differentiateEnum);
                            }
                        });
                break;
            case ALIPAY:
                HashMap<String,String> stringHashMap1 = (HashMap<String, String>) obj;
                String orderid2 = stringHashMap1.get("orderId");
                String couponId3 = stringHashMap1.get("couponId");
                HttpManager.getInstance().getServer().getAlipayBean(orderid2,couponId3)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AlipayBean>(Callback) {
                            @Override
                            public void onNext(AlipayBean alipayBean) {
                                Callback.setData(alipayBean, differentiateEnum);
                            }
                        });
                break;
            case COURSETOBUY:
                String courseId = (String) obj;
                HttpManager.getInstance().getServer().getCourseToBuyBean(courseId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CourseToBuyBean>(Callback) {
                            @Override
                            public void onNext(CourseToBuyBean courseToBuyBean) {
                                Callback.setData(courseToBuyBean, differentiateEnum);
                            }
                        });
                break;
            case ALIPAYCURSE:
                HashMap<String, Object> hashMap19 = (HashMap<String, Object>) obj;
                String couponId1 = (String) hashMap19.get("couponId");
                String courseId1 = (String) hashMap19.get("courseId");
                HttpManager.getInstance().getServer().getAlipayCourseBean(courseId1,couponId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AlipayBean>(Callback) {
                            @Override
                            public void onNext(AlipayBean alipayBean) {
                                Callback.setData(alipayBean, differentiateEnum);
                            }
                        });
                break;
            case PAYBEANCURSE:
                HashMap<String, String> hashMap18 = (HashMap<String, String>) obj;
                String courseId2 = (String) hashMap18.get("courseId");
                String couponId2 = (String) hashMap18.get("couponId");
                HttpManager.getInstance().getServer().getPayCourseBean(courseId2,couponId2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PayBean>(Callback) {
                            @Override
                            public void onNext(PayBean payBean) {
                                Callback.setData(payBean, differentiateEnum);
                            }
                        });
                break;
            case COURSETOBUYCOMM:
                HashMap<String, String> hashMap1 = (HashMap<String, String>) obj;
                String consigneeName = hashMap1.get("consigneeName");
                String consigneeMobile = hashMap1.get("consigneeMobile");
                String consigneeAddress = hashMap1.get("consigneeAddress");
                String courseId3 = hashMap1.get("courseId");
                HttpManager.getInstance().getServerStr().getCourseToBuyBeanComm(courseId3, consigneeName, consigneeMobile, consigneeAddress)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<String>(Callback) {
                            @Override
                            public void onNext(String s) {
                                Callback.setData(s, differentiateEnum);
                            }
                        });
                break;

            //更改用户昵称
            case CHANGENICHENG:
                String names = (String) obj;
                HttpManager.getInstance().getServer().getNameInfo(names)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ChangeName>(Callback) {
                            @Override
                            public void onNext(ChangeName changeName) {
                                Callback.setData(changeName, differentiateEnum);
                            }
                        });
                break;
            //更改个性签名
            case CHANGEGEQIAN:
                String geqian = (String) obj;
                HttpManager.getInstance().getServer().getGeqianInfo(geqian)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ChangeGeqian>(Callback) {
                            @Override
                            public void onNext(ChangeGeqian changeGeqian) {
                                Callback.setData(changeGeqian, differentiateEnum);
                            }
                        });
                break;
            case XIV:
                File file = (File) obj;
                HttpManager.getInstance().getServer().getUpBean(HttpUtils.getUploading(file))
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UpBean>(Callback) {
                            @Override
                            public void onNext(UpBean upBean) {
                                Callback.setData(upBean, differentiateEnum);
                            }
                        });
                break;
            case UPSEX:
                int gender = (int) obj;
                HttpManager.getInstance().getServer().getUpsexBean(gender)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UpBean>(Callback) {
                            @Override
                            public void onNext(UpBean upBean) {
                                Callback.setData(upBean, differentiateEnum);
                            }
                        });
                break;
            case UPADDRESS:
                HashMap<String, String> hashMap2 = (HashMap<String, String>) obj;
                String country = hashMap2.get("country");
                String province = hashMap2.get("province");
                String city = hashMap2.get("city");
                HttpManager.getInstance().getServer().getUpAddressBean(country, province, city)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UpBean>(Callback) {
                            @Override
                            public void onNext(UpBean upBean) {
                                Callback.setData(upBean, differentiateEnum);
                            }
                        });
                break;
            case ABC:
                Callback.setAnimation();
                int page11 = (int) obj;
                HttpManager.getInstance().getServer().getAlreadyBoughtCourse(page11)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AlreadyBoughtCourse>(Callback) {
                            @Override
                            public void onNext(AlreadyBoughtCourse alreadyBoughtCourse) {
                                Callback.setHidoAnimation();
                                Callback.setData(alreadyBoughtCourse, differentiateEnum);
                            }
                        });
                break;
            case COURSELIST:
                HashMap<String, Object> hashMap3 = (HashMap<String, Object>) obj;
                String catalogId = (String) hashMap3.get("catalogId");
                int page12 = (int) hashMap3.get("page");
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getCourseListBean(page12, catalogId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CourseListBean>(Callback) {
                            @Override
                            public void onNext(CourseListBean courseListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(courseListBean, differentiateEnum);
                            }
                        });
                break;
            case SHOPBEAN:
                String id2 = (String) obj;
                HttpManager.getInstance().getServer().getShopBean(id2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShopBean>(Callback) {
                            @Override
                            public void onNext(ShopBean shopBean) {
                                Callback.setData(shopBean, differentiateEnum);
                            }
                        });
                break;
            case BABYMESSAGELIST:
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getBabyMessageBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<BabyMessageBean>(Callback) {
                            @Override
                            public void onNext(BabyMessageBean babyMessageBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(babyMessageBean, differentiateEnum);
                            }
                        });
                break;
            case ADDBABY:
                Callback.setAnimation();
                HashMap<String, Object> hashMap4 = (HashMap<String, Object>) obj;
                File file13 = (File) hashMap4.get("files");
                if(file13!=null){
                    HttpManager.getInstance().getServer().getAddBabyBean(HttpUtils.getBabyMess(hashMap4),HttpUtils.getBabyPic(hashMap4))
                            .compose(RxUtils.rxOBserableSchedulerHelper())
                            .subscribe(new BaseObserver<AddBabyBean>(Callback) {
                                @Override
                                public void onNext(AddBabyBean addBabyBean) {
                                    Callback.setHidoAnimation();
                                    Callback.setData(addBabyBean, differentiateEnum);
                                }
                            });
                }else {
                    String name2 = (String) hashMap4.get("name");
                    int gender1 = (int) hashMap4.get("gender");
                    String birthday = (String) hashMap4.get("birthday");
                    int isDefault2 = (int) hashMap4.get("isDefault");
                    HttpManager.getInstance().getServer().getAddBabyBean1(name2,gender1,birthday,isDefault2)
                            .compose(RxUtils.rxOBserableSchedulerHelper())
                            .subscribe(new BaseObserver<AddBabyBean>(Callback) {
                                @Override
                                public void onNext(AddBabyBean addBabyBean) {
                                    Callback.setHidoAnimation();
                                    Callback.setData(addBabyBean, differentiateEnum);
                                }
                            });
                }

                break;
            case DELETEBABY:
                String babyId = (String) obj;
                HttpManager.getInstance().getServer().getDeleteBabyBean(babyId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DeleteBabyBean>(Callback) {
                            @Override
                            public void onNext(DeleteBabyBean deleteBabyBean) {
                                Callback.setData(deleteBabyBean, differentiateEnum);
                            }
                        });
                break;
            case UPBABY:
                HashMap<String, Object> hashMap5 = (HashMap<String, Object>) obj;
                String babyId1 = (String) hashMap5.get("babyId");
                String name3 = (String) hashMap5.get("name");
                int gender2 = (int) hashMap5.get("gender");
                String birthday1 = (String) hashMap5.get("birthday");
                int isDefault3 = (int) hashMap5.get("isDefault");
                File files = (File) hashMap5.get("files");
                if(files!=null){
                    Log.i("files", "getData: -------");
                    HttpManager.getInstance().getServer().getUpBabyBeanPic(HttpUtils.getBabyMess1(hashMap5),HttpUtils.getBabyPic(hashMap5))
                            .compose(RxUtils.rxOBserableSchedulerHelper())
                            .subscribe(new BaseObserver<UpBabyBean>(Callback) {
                                @Override
                                public void onNext(UpBabyBean upBabyBean) {
                                    Callback.setData(upBabyBean, differentiateEnum);
                                }
                            });
                }else {
                    Log.i("files", "getData: =======");
                    HttpManager.getInstance().getServer().getUpBabyBean(babyId1, name3, gender2, birthday1, isDefault3)
                            .compose(RxUtils.rxOBserableSchedulerHelper())
                            .subscribe(new BaseObserver<UpBabyBean>(Callback) {
                                @Override
                                public void onNext(UpBabyBean upBabyBean) {
                                    Callback.setData(upBabyBean, differentiateEnum);
                                }
                            });
                }

                break;
            case SETBABY:
                String babyId2 = (String) obj;
                HttpManager.getInstance().getServer().getSetBabyBean(babyId2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SetBabyBean>(Callback) {
                            @Override
                            public void onNext(SetBabyBean setBabyBean) {
                                Callback.setData(setBabyBean, differentiateEnum);
                            }
                        });
                break;
            case HOMEBANNAR:
                HttpManager.getInstance().getServer().getHomeBannerBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<HomeBannerBean>(Callback) {
                            @Override
                            public void onNext(HomeBannerBean homeBannerBean) {
                                Callback.setData(homeBannerBean, differentiateEnum);
                            }
                        });
                break;
            case SHORTVIDEO:
                HttpManager.getInstance().getServer().getShortVideoBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShortVideoBean>(Callback) {
                            @Override
                            public void onNext(ShortVideoBean shortVideoBean) {
                                Callback.setData(shortVideoBean, differentiateEnum);
                            }
                        });
                break;
            case ADPOSITION:
                HttpManager.getInstance().getServer().getAdPositionIdBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AdPositionIdBean>(Callback) {
                            @Override
                            public void onNext(AdPositionIdBean adPositionIdBean) {
                                Callback.setData(adPositionIdBean, differentiateEnum);
                            }
                        });
                break;
            case SONGLIST:
                Callback.setAnimation();
                HashMap<String, Integer> hashMap6 = (HashMap<String, Integer>) obj;
                int page14 = hashMap6.get("page");
                int size = hashMap6.get("size");
                HttpManager.getInstance().getServer().getSongListBean(page14, size)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongListBean>(Callback) {
                            @Override
                            public void onNext(SongListBean songListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(songListBean, differentiateEnum);
                            }
                        });
                break;
            case SONG:
                String musicId = (String) obj;
                HttpManager.getInstance().getServer().getSongBean(musicId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongBean>(Callback) {
                            @Override
                            public void onNext(SongBean songBean) {
                                Callback.setData(songBean, differentiateEnum);
                            }
                        });
                break;
            case SONGMORN:
                HttpManager.getInstance().getServer().getMornSongBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongBean>(Callback) {
                            @Override
                            public void onNext(SongBean songBean) {
                                Callback.setData(songBean, differentiateEnum);
                            }
                        });
                break;
            case SONGNIGHT:
                HttpManager.getInstance().getServer().getNightSongBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongBean>(Callback) {
                            @Override
                            public void onNext(SongBean songBean) {
                                Callback.setData(songBean, differentiateEnum);
                            }
                        });
                break;
            case SONGMORNS:
                HashMap<String, Object> mapzao = (HashMap<String, Object>) obj;
                String zaocatalogId = (String) mapzao.get("catalogId");
                int zaopage = (int) mapzao.get("page");
                HttpManager.getInstance().getServer().getZaoAnBean(zaocatalogId,zaopage)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongBeanss>(Callback) {
                            @Override
                            public void onNext(SongBeanss songBeanss) {
                                Callback.setData(songBeanss,differentiateEnum);
                            }
                        });
                break;
            case SONGMUSIC:
                HashMap<String, Object> musicmap = (HashMap<String, Object>) obj;
                String catalogIds = (String) musicmap.get("catalogId");
                int musicpage = (int) musicmap.get("page");
                HttpManager.getInstance().getServer().getMusicBran(catalogIds,musicpage)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MusicBean>(Callback) {
                            @Override
                            public void onNext(MusicBean musicBean) {
                                Callback.setData(musicBean,differentiateEnum);
                            }
                        });
                break;
            case SHOPBANNAR:
                HttpManager.getInstance().getServer().getShopAdPositionIdBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AdPositionIdBean>(Callback) {
                            @Override
                            public void onNext(AdPositionIdBean adPositionIdBean) {
                                Callback.setData(adPositionIdBean, differentiateEnum);
                            }
                        });
                break;
            case HOMEBSS:
                HttpManager.getInstance().getServer().getHomeBBSbean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<HomeBBSbean>(Callback) {
                            @Override
                            public void onNext(HomeBBSbean homeBBSbean) {
                                Callback.setData(homeBBSbean, differentiateEnum);
                            }
                        });
                break;
            case EVALUATIONLIST:
                HttpManager.getInstance().getServer().getBabyAgeBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<BabyAgeBean>(Callback) {
                            @Override
                            public void onNext(BabyAgeBean babyAgeBean) {
                                Callback.setData(babyAgeBean, differentiateEnum);
                            }
                        });
                break;
            case REVIEWTHETOPIC:
                String subjectId1 = (String) obj;
                HttpManager.getInstance().getServer().getReviewTheTopicBean(subjectId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ReviewTheTopicBean>(Callback) {
                            @Override
                            public void onNext(ReviewTheTopicBean reviewTheTopicBean) {
                                Callback.setData(reviewTheTopicBean, differentiateEnum);
                            }
                        });
                break;
            case MYCOLLECTLIST:
                Callback.setAnimation();
                HashMap<String, Object> hashMap7 = (HashMap<String, Object>) obj;
                String userid = (String) hashMap7.get("userid");
                int page15 = (int) hashMap7.get("page");
                HttpManager.getInstance().getServer().getMyCollectListBean(userid, page15)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MyCollectListBean>(Callback) {
                            @Override
                            public void onNext(MyCollectListBean myCollectListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(myCollectListBean, differentiateEnum);
                            }
                        });
                break;
            case MYRELEASEDLIST:
                Callback.setAnimation();
                HashMap<String, Object> hashMap8 = (HashMap<String, Object>) obj;
                String userid1 = (String) hashMap8.get("userid");
                String articleType1 = (String) hashMap8.get("articleType");
                int page16 = (int) hashMap8.get("page");
                HttpManager.getInstance().getServer().getMyReleasedListBean(userid1, page16,articleType1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MyReleasedListBean>(Callback) {
                            @Override
                            public void onNext(MyReleasedListBean myReleasedListBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(myReleasedListBean, differentiateEnum);
                            }
                        });
                break;
            case SUBMITAPPRAISAL:
                HashMap<String, String> hashMap9 = (HashMap<String, String>) obj;
                HttpManager.getInstance().getServer().getSubmitAppraisalBean(HttpUtils.getSubmitAppraisal(hashMap9))
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SubmitAppraisalBean>(Callback) {
                            @Override
                            public void onNext(SubmitAppraisalBean submitAppraisalBean) {
                                Callback.setData(submitAppraisalBean, differentiateEnum);
                            }
                        });

                break;
            case EVALUATIONRESULTS:
                String reportId = (String) obj;
                HttpManager.getInstance().getServer().getEvaluationResultsBean(reportId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<EvaluationResultsBean>(Callback) {
                            @Override
                            public void onNext(EvaluationResultsBean evaluationResultsBean) {
                                Callback.setData(evaluationResultsBean, differentiateEnum);
                            }
                        });
                break;
            case REVIEWEDLIST:
                HashMap<String, Object> hashMap10 = (HashMap<String, Object>) obj;
                String babyId3 = (String) hashMap10.get("babyId");
                int page17 = (int) hashMap10.get("page");
                int size1 = (int) hashMap10.get("size");
                HttpManager.getInstance().getServer().getReviewedListBean(babyId3, page17, size1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ReviewedListBean>(Callback) {
                            @Override
                            public void onNext(ReviewedListBean reviewedListBean) {
                                Callback.setData(reviewedListBean, differentiateEnum);
                            }
                        });
                break;
            case CLASSGARDENLIST:
                int page18 = (int) obj;
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getClassGardenBean(page18)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ClassGardenBean>(Callback) {
                            @Override
                            public void onNext(ClassGardenBean classGardenBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(classGardenBean, differentiateEnum);
                            }
                        });
                break;
            case GARDENDETAILSBY:
                String companyId = (String) obj;
                HttpManager.getInstance().getServer().getGardenDetailsByBean(companyId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<GardenDetailsByBean>(Callback) {
                            @Override
                            public void onNext(GardenDetailsByBean gardenDetailsByBean) {
                                Callback.setData(gardenDetailsByBean, differentiateEnum);
                            }
                        });
                break;
            case TOJOIN:
                HashMap<String, String> hashMap11 = (HashMap<String, String>) obj;
                String userName = hashMap11.get("userName");
                String userMobile = hashMap11.get("userMobile");
                String address2 = hashMap11.get("address");
                String summary = hashMap11.get("summary");
                HttpManager.getInstance().getServer().getToJoinBean(userName, userMobile, address2, summary)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ToJoinBean>(Callback) {
                            @Override
                            public void onNext(ToJoinBean toJoinBean) {
                                Callback.setData(toJoinBean, differentiateEnum);
                            }
                        });
                break;
            case TOJOINBABY:
                HashMap<String, String> hashMap12 = (HashMap<String, String>) obj;
                String babyName = hashMap12.get("babyName");
                String userMobile1 = hashMap12.get("userMobile");
                String babyAge = hashMap12.get("babyAge");
                String courseId4 = hashMap12.get("courseId");
                HttpManager.getInstance().getServer().getToJoinBabyBean(babyName, userMobile1, babyAge, courseId4)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ToJoinBean>(Callback) {
                            @Override
                            public void onNext(ToJoinBean toJoinBean) {
                                Callback.setData(toJoinBean, differentiateEnum);
                            }
                        });
                break;
            case EARLYCOURSELIST:
                int page19 = (int) obj;
                HttpManager.getInstance().getServer().getEarlyCourseListBean(page19)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<EarlyCourseListBean>(Callback) {
                            @Override
                            public void onNext(EarlyCourseListBean earlyCourseListBean) {
                                Callback.setData(earlyCourseListBean, differentiateEnum);
                            }
                        });
                break;
            case EARLYCOURSE:
                String courseId5 = (String) obj;
                HttpManager.getInstance().getServer().getEarlyCourseBean(courseId5)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<EarlyCourseBean>(Callback) {
                            @Override
                            public void onNext(EarlyCourseBean earlyCourseBean) {
                                Callback.setData(earlyCourseBean, differentiateEnum);
                            }
                        });
                break;
            case COUPONLIST:
                Callback.setAnimation();
                HashMap<String, Object> hashMap13 = (HashMap<String, Object>) obj;
                int status_c = (int) hashMap13.get("status");
                int page_c = (int) hashMap13.get("page");
                HttpManager.getInstance().getServer().getCouponBean(status_c, page_c)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CouponBean>(Callback) {
                            @Override
                            public void onNext(CouponBean couponBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(couponBean, differentiateEnum);
                            }
                        });

                break;
            /*case GETCOUPONONLYATHOME:
                HttpManager.getInstance().getServer().getAthomeBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AthomeBean>(Callback) {
                            @Override
                            public void onNext(AthomeBean athomeBean) {
                                Callback.setData(athomeBean, differentiateEnum);
                            }
                        });
                break;*/
            case VIPLIST:
                HttpManager.getInstance().getServer().getVipListBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<VipListBean>(Callback) {
                            @Override
                            public void onNext(VipListBean vipListBean) {

                                Callback.setData(vipListBean, differentiateEnum);
                            }
                        });
                break;
            case VIPORDER:
                int vipLevel = (int) obj;
                HttpManager.getInstance().getServer().getVipOrderBean(vipLevel)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<VipOrderBean>(Callback) {
                            @Override
                            public void onNext(VipOrderBean vipOrderBean) {
                                Callback.setData(vipOrderBean, differentiateEnum);

                            }
                        });

                break;
            case VIPWXPAY:
                int vipLevel1 = (int) obj;
                HttpManager.getInstance().getServer().getVipPayBean(vipLevel1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PayBean>(Callback) {
                            @Override
                            public void onNext(PayBean payBean) {
                                Callback.setData(payBean, differentiateEnum);
                            }
                        });

                break;
            case VIPALIPAY:
                int vipLevel2 = (int) obj;
                HttpManager.getInstance().getServer().getVipAlipayBean(vipLevel2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AlipayBean>(Callback) {
                            @Override
                            public void onNext(AlipayBean alipayBean) {
                                Callback.setData(alipayBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADONE:
                HttpManager.getInstance().getServer().getMallAdOne()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADTWO:
                HttpManager.getInstance().getServer().getMallAdTwo()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADTHREE:
                HttpManager.getInstance().getServer().getMallAdThree()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADSIX:
                HttpManager.getInstance().getServer().getMallAdSix()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADFOUR:
                HttpManager.getInstance().getServer().getMallAdFour()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADFIVE:
                HttpManager.getInstance().getServer().getMallAdFive()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADSEVEN:
                HttpManager.getInstance().getServer().getMallAdSeven()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADEIGHT:
                HttpManager.getInstance().getServer().getMallAdEight()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYADONE:
                HttpManager.getInstance().getServer().getStudyAdOne()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYADTWO:
                HttpManager.getInstance().getServer().getStudyAdTwo()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBeanTwo>(Callback) {
                            @Override
                            public void onNext(MallAdBeanTwo mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYADTHREE:
                HttpManager.getInstance().getServer().getStudyAdThree()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYADFOUR:
                HttpManager.getInstance().getServer().getStudyAdFour()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYADFIVE:
                HttpManager.getInstance().getServer().getStudyAdFive()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADNINE:
                HttpManager.getInstance().getServer().getMallAdNine()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case COMMTOPICSWITCHER:
                HttpManager.getInstance().getServer().getCommTopicSwitcherBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CommTopicSwitcherBean>(Callback) {
                            @Override
                            public void onNext(CommTopicSwitcherBean commTopicSwitcherBean) {
                                Callback.setData(commTopicSwitcherBean, differentiateEnum);
                            }
                        });
                break;
            case ARTICLELISTFOLLOW:
                HashMap<String, Object> stringObjectHashMap = (HashMap<String, Object>) obj;
                int page21 = (int) stringObjectHashMap.get("page");
                String articleType2 = (String) stringObjectHashMap.get("articleType");
                HttpManager.getInstance().getServer().getAttentionBean(page21,articleType2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ArticleLists>(Callback) {
                            @Override
                            public void onNext(ArticleLists articleLists) {
                                Callback.setData(articleLists, differentiateEnum);
                            }
                        });
                break;
            case MALLADTEN:
                HttpManager.getInstance().getServer().getMallAdTen()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case MALLADELEVEN:
                HttpManager.getInstance().getServer().getMallAdEleven()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MallAdBean>(Callback) {
                            @Override
                            public void onNext(MallAdBean mallAdBean) {
                                Callback.setData(mallAdBean, differentiateEnum);
                            }
                        });
                break;
            case POSTCOMMENT:
                HashMap<String, Object> hashMap14 = (HashMap<String, Object>) obj;
                HttpManager.getInstance().getServer().getCommentsBean(HttpUtils.getReleaseEvaluate(hashMap14), HttpUtils.getReleaseTopic1(hashMap14))
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<Comment>(Callback) {
                            @Override
                            public void onNext(Comment comment) {
                                Callback.setData(comment, differentiateEnum);
                            }
                        })
                ;
                break;
            case COMMENTLIST:
                String skuIdcomment= (String) obj;
                HttpManager.getInstance().getServer().getCommentListBeans(skuIdcomment)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CommentListBeans>(Callback) {
                            @Override
                            public void onNext(CommentListBeans commentListBeans) {
                                Callback.setData(commentListBeans,differentiateEnum);
                            }
                        });
                break;
            case FORMALCURRICULUM:
                HttpManager.getInstance().getServer().getFormalCurriculumBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<FormalCurriculumBean>(Callback) {
                            @Override
                            public void onNext(FormalCurriculumBean formalCurriculumBean) {
                                Callback.setData(formalCurriculumBean,differentiateEnum);
                            }
                        });
                break;
            case CARDTYPEBEAN:
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getCardTypeBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CardTypeBean>(Callback) {
                            @Override
                            public void onNext(CardTypeBean cardTypeBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(cardTypeBean, differentiateEnum);
                            }
                        });
                break;
            case STUDYCARDWEIXIN:
                HashMap<String, Object> hashMap15 = (HashMap<String, Object>) obj;
                int cardType2 = (int) hashMap15.get("cardType");
                String babyBirthday2 = (String) hashMap15.get("babyBirthday");
                String couponId4 = (String) hashMap15.get("couponId");
                HttpManager.getInstance().getServer().getPaycardBean(cardType2,babyBirthday2,couponId4)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PayBean>(Callback) {
                            @Override
                            public void onNext(PayBean payBean) {
                                Callback.setData(payBean,differentiateEnum);
                            }
                        });
                break;
            case STUDYCARDZHIFUBAO:
                HashMap<String, Object> hashMap16 = (HashMap<String, Object>) obj;
                int cardType1 = (int) hashMap16.get("cardType");
                String babyBirthday1 = (String) hashMap16.get("babyBirthday");
                String couponId5 = (String) hashMap16.get("couponId");
                HttpManager.getInstance().getServer().getAlicardpayBean(cardType1,babyBirthday1,couponId5)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AlipayBean>(Callback) {
                            @Override
                            public void onNext(AlipayBean alipayBean) {
                                Callback.setData(alipayBean,differentiateEnum);
                            }
                        });
                break;
            case STUDYCARDORDERCREATE:
                HashMap<String, Object> hashMap17 = (HashMap<String, Object>) obj;
                int cardType0 = (int) hashMap17.get("cardType");
                String babyBirthday0 = (String) hashMap17.get("babyBirthday");
                HttpManager.getInstance().getServer().getStudyCardOrderCreate(cardType0,babyBirthday0)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<StudyCardOrderCreateBenas>(Callback) {
                            @Override
                            public void onNext(StudyCardOrderCreateBenas studyCardOrderCreateBenas) {
                                Callback.setData(studyCardOrderCreateBenas,differentiateEnum);
                            }
                        });
                break;
            case VALIDCOUPONS:
                int tradeType= (int) obj;
                HttpManager.getInstance().getServer().getValidCouponsBean(tradeType)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ValidCouponBeans>(Callback) {
                            @Override
                            public void onNext(ValidCouponBeans validCouponBeans) {
                                Callback.setData(validCouponBeans,differentiateEnum);
                            }
                        });
                break;
            case USERCARDTYPE:
                HttpManager.getInstance().getServer().getUserCardTypeBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UserCardTypeBean>(Callback) {
                            @Override
                            public void onNext(UserCardTypeBean userCardTypeBean) {
                                Callback.setData(userCardTypeBean,differentiateEnum);
                            }
                        });
                break;
            case MEMBERFEEDBACK:
                HashMap<String,Object> map18= (HashMap<String, Object>) obj;
                String summary1 = (String) map18.get("summary");
                String contact = (String) map18.get("contact");
                HttpManager.getInstance().getServer().getMemberFeedbackBean(summary1,contact)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MemberFeedbackBeans>(Callback) {
                            @Override
                            public void onNext(MemberFeedbackBeans memberFeedbackBeans) {
                                Callback.setData(memberFeedbackBeans,differentiateEnum);
                            }
                        });
                break;
            case ARYICLEREPORT:
                HashMap<String,Object> map19= (HashMap<String, Object>) obj;
                Long articleId5 = (Long) map19.get("articleId");
                String summary2 = (String) map19.get("summary");
                HttpManager.getInstance().getServer().getArticleReport(articleId5,summary2)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ArticleReportBeans>(Callback) {
                            @Override
                            public void onNext(ArticleReportBeans articleReportBeans) {
                                Callback.setData(articleReportBeans,differentiateEnum);
                            }
                        });
                break;
            case SUBMIT:
                HashMap<String, String> map20 = (HashMap<String, String>) obj;
                String contentsId = map20.get("contentsId");
                String feedbackIds = map20.get("feedbackIds");
                HttpManager.getInstance().getServer().getSubmitBean(contentsId,feedbackIds)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SubmitBean>(Callback) {
                            @Override
                            public void onNext(SubmitBean submitBean) {
                                Callback.setData(submitBean,differentiateEnum);
                            }
                        });
                break;
            case ORDERCANNEL:
                Long orderId= (Long) obj;
                HttpManager.getInstance().getServer().getOrderCannelBean(orderId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<OrderCannelBeans>(Callback) {
                            @Override
                            public void onNext(OrderCannelBeans orderCannelBeans) {
                                Callback.setData(orderCannelBeans,differentiateEnum);
                            }
                        });
                break;
            case RESPECTIVELY:
                String catalogId1 = (String) obj;
                HttpManager.getInstance().getServer().getRespectivelyBean(catalogId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<RespectivelyBean>(Callback) {
                            @Override
                            public void onNext(RespectivelyBean respectivelyBean) {
                                Callback.setData(respectivelyBean,differentiateEnum);
                            }
                        });
                break;
            case MINVIDEO:
                HashMap<String, Object> hashMap20 = (HashMap<String, Object>) obj;
                String catalogId2 = (String) hashMap20.get("catalogId");
                int page13 = (int) hashMap20.get("page");
                HttpManager.getInstance().getServer().getMinVideoBean(catalogId2,page13)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<MinVideoBean>(Callback) {
                            @Override
                            public void onNext(MinVideoBean minVideoBean) {
                                Callback.setData(minVideoBean,differentiateEnum);
                            }
                        });
                break;
            case GAMEHOMEPAGE:
                HttpManager.getInstance().getServer().getGameHomePageBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<GamehomePageBeans>(Callback) {
                            @Override
                            public void onNext(GamehomePageBeans gamehomePageBeans) {
                                Callback.setData(gamehomePageBeans,differentiateEnum);
                            }
                        });
                break;
            case INCOME:
                HttpManager.getInstance().getServer().getIncomeBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<IncomeBean>(Callback) {
                            @Override
                            public void onNext(IncomeBean incomeBean) {
                                Callback.setData(incomeBean,differentiateEnum);
                            }
                        });
                break;
            case AUDITION:
                HttpManager.getInstance().getServer().getAuditionBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AuditionBean>(Callback) {
                            @Override
                            public void onNext(AuditionBean auditionBean) {
                                Callback.setData(auditionBean,differentiateEnum);
                            }
                        });
                break;
            case DETAIL:
                HashMap<String, Object> mapde = (HashMap<String, Object>) obj;
                Log.i("page22", "getData: "+mapde.get("page"));
                Log.i("tradeTypem", "getData: "+mapde.get("tradeType"));
                int page22 = (int) mapde.get("page");
                Integer tradeType1 = (Integer) mapde.get("tradeType");
                int i = tradeType1.intValue();
                Callback.setAnimation();
                HttpManager.getInstance().getServer().getDetailBean(page22,i)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DetailBean>(Callback) {
                            @Override
                            public void onNext(DetailBean detailBean) {
                                Callback.setHidoAnimation();
                                Callback.setData(detailBean, differentiateEnum);
                            }
                        });
                break;
            case TEAM:
                HashMap<String, Object> mapTeam = (HashMap<String, Object>) obj;
                String vipLevel3 = (String) mapTeam.get("vipLevel");
                int page23 = (int) mapTeam.get("page");
                HttpManager.getInstance().getServer().getTeamBean(vipLevel3,page23)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<TeamBean>(Callback) {
                            @Override
                            public void onNext(TeamBean teamBean) {
                                Callback.setData(teamBean,differentiateEnum);
                            }
                        });
                break;
            case APPCHECKVERSION:
                HttpManager.getInstance().getServer().getAppCheckBean().compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AppCheckVersion>(Callback) {
                            @Override
                            public void onNext(AppCheckVersion appCheckVersion) {
                                Callback.setData(appCheckVersion,differentiateEnum);
                            }
                        });
                break;
            case BRING:
                HttpManager.getInstance().getServer().getBringBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<BringBean>(Callback) {
                            @Override
                            public void onNext(BringBean bringBean) {
                                Callback.setData(bringBean,differentiateEnum);
                            }
                        });
                break;
            case QRCODE:
                int style= (int) obj;
                HttpManager.getInstance().getServer().getQrcode(style)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<QrcodeBeans>(Callback) {
                            @Override
                            public void onNext(QrcodeBeans qrcodeBeans) {
                                Callback.setData(qrcodeBeans,differentiateEnum);
                            }
                        });
                break;
            case DIANSONG:
                String musicId1 = (String) obj;
                HttpManager.getInstance().getServer().getSongBeanDian(musicId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<SongBean>(Callback) {
                            @Override
                            public void onNext(SongBean songBean) {
                                Callback.setData(songBean,differentiateEnum);
                            }
                        });
                break;
            case BANKCARDMODIFY:
                HashMap<String, Object> map21 = (HashMap<String, Object>) obj;
                String cardNo = (String) map21.get("cardNo");
                String bank = (String) map21.get("bank");
                String city1 = (String) map21.get("city");
                String openingBank = (String) map21.get("openingBank");
                String mobile2 = (String) map21.get("mobile");
                String name4 = (String) map21.get("name");
                String idNo = (String) map21.get("idNo");
                HttpManager.getInstance().getServer().getBankcardBean(cardNo,bank,city1,openingBank,mobile2,name4,idNo)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<BankcardBeans>(Callback) {
                            @Override
                            public void onNext(BankcardBeans bankcardBeans) {
                                Callback.setData(bankcardBeans,differentiateEnum);
                            }
                        });
                break;
            case BANKCARDINFO:
                HttpManager.getInstance().getServer().getBankcardInfoBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<BankcardInfoBeans>(Callback) {
                            @Override
                            public void onNext(BankcardInfoBeans bankcardInfoBeans) {
                                Callback.setData(bankcardInfoBeans,differentiateEnum);
                            }
                        });
                break;
            case WEBCOLLECT:
                HashMap<String, String> map22 = (HashMap<String, String>) obj;
                String articleId6 = map22.get("articleId");
                String articleType3 = map22.get("articleType");
                String title = map22.get("title");
                String summary3 = map22.get("summary");
                String pic = map22.get("pic");
                String url = map22.get("url");
                HttpManager.getInstance().getServer().getWebCollectBean(articleId6,articleType3,title,summary3,pic,url)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CollectBean>(Callback) {
                            @Override
                            public void onNext(CollectBean collectBean) {
                                Callback.setData(collectBean,differentiateEnum);
                            }
                        });
                break;
            case WEBISCOLLECT:
                String articleId7 = (String) obj;
                HttpManager.getInstance().getServer().getIsCollectBean(articleId7)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<IsCollectBean>(Callback) {
                            @Override
                            public void onNext(IsCollectBean isCollectBean) {
                                Callback.setData(isCollectBean,differentiateEnum);
                            }
                        });
                break;
            case WEBCOLLECTLIST:
                HashMap<String, Object> map23 = (HashMap<String, Object>) obj;
                int page24 = (int) map23.get("page");
                String articleType4 = (String) map23.get("articleType");
                HttpManager.getInstance().getServer().getWebCollectListBean(articleType4,page24)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<WebCollectListBean>(Callback) {
                            @Override
                            public void onNext(WebCollectListBean webCollectListBean) {
                                Callback.setData(webCollectListBean,differentiateEnum);
                            }
                        });
                break;
            case DELEARTICEL:
                String articleId8 = (String) obj;
                HttpManager.getInstance().getServer().getDeleArticelBean(articleId8)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<DeleArticelBean>(Callback) {
                            @Override
                            public void onNext(DeleArticelBean deleArticelBean) {
                                Callback.setData(deleArticelBean,differentiateEnum);
                            }
                        });
                break;
            case PEOSUM:
                HttpManager.getInstance().getServer().getPeoSumBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<PeoSumBean>(Callback) {
                            @Override
                            public void onNext(PeoSumBean peoSumBean) {
                                Callback.setData(peoSumBean,differentiateEnum);
                            }
                        });
                break;
            case GETCARDS:
                HttpManager.getInstance().getServer().getGetCardsBean()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<GetCardsBean>(Callback) {
                            @Override
                            public void onNext(GetCardsBean getCardsBean) {
                                Callback.setData(getCardsBean,differentiateEnum);
                            }
                        });
                break;
            case SHORTCOLLECT:
                String videoId = (String) obj;
                HttpManager.getInstance().getServer().getShortCollectBean(videoId)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShortCollectBean>(Callback) {
                            @Override
                            public void onNext(ShortCollectBean shortCollectBean) {
                                Callback.setData(shortCollectBean,differentiateEnum);
                            }
                        });
                break;
            case SHORTISCOLLECT:
                String videoId1 = (String) obj;
                HttpManager.getInstance().getServer().getShortIsCollectBean(videoId1)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShortIsCollectBean>(Callback) {
                            @Override
                            public void onNext(ShortIsCollectBean shortIsCollectBean) {
                                Callback.setData(shortIsCollectBean,differentiateEnum);
                            }
                        });
                break;
            case SHORTCOLLECTLIST:
                int page25 = (int) obj;
                HttpManager.getInstance().getServer().getShortCollectListBean(page25)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<ShortCollectListBean>(Callback) {
                            @Override
                            public void onNext(ShortCollectListBean shortCollectListBean) {
                                Callback.setData(shortCollectListBean,differentiateEnum );
                            }
                        });
                break;
            case GETSUBID:
                String baby = (String) obj;
                HttpManager.getInstance().getServer().getGetSubIDBean(baby)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<GetSubIDBean>(Callback) {
                            @Override
                            public void onNext(GetSubIDBean getSubIDBean) {
                                Callback.setData(getSubIDBean,differentiateEnum);
                            }
                        });
                break;

            //用户信息
            case USERINFO:

                HttpManager.getInstance().getServer().getUserInfo()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UserInfoBean>(Callback) {
                            @Override
                            public void onNext(UserInfoBean userInfoBean) {
                                Callback.setData(userInfoBean, differentiateEnum);
                            }
                        });

                break;

            case UPGRADE:
                HttpManager.getInstance().getServer().getPromotionamBassador()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<UpgradepromotionBean>(Callback) {
                            @Override
                            public void onNext(UpgradepromotionBean upgradepromotionBean) {
                                Callback.setData(upgradepromotionBean,differentiateEnum);
                            }
                        });

                break;
            case AMOUNT:
                HttpManager.getInstance().getTiXian().getAmount()
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<AmountBean>(Callback) {
                            @Override
                            public void onNext(AmountBean amountBean) {
                                Callback.setData(amountBean,differentiateEnum);
                            }
                        });

                break;
            case APPLY:
                HashMap<String, Object> hashMap21 = (HashMap<String, Object>) obj;
                String cardid = (String) hashMap21.get("cardId");
                String jine = (String) hashMap21.get("cash");
                HttpManager.getInstance().getTiXian().getApply(cardid,jine)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CashoutBean>(Callback) {
                            @Override
                            public void onNext(CashoutBean cashoutBean) {
                                Callback.setData(cashoutBean,differentiateEnum);
                            }
                        });

                break;
            case DetailList:
                String pageList= (String) obj;
                HttpManager.getInstance().getTiXian().getDetail(pageList)
                        .compose(RxUtils.rxOBserableSchedulerHelper())
                        .subscribe(new BaseObserver<CashOutListBean>(Callback) {
                            @Override
                            public void onNext(CashOutListBean cashOutListBean) {
                                Callback.setData(cashOutListBean,differentiateEnum);
                            }
                        });
                break;
        }
    }
}
