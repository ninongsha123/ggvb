package com.tiancaijiazu.app.activitys.activitypage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.MyWebView;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.CollectBean;
import com.tiancaijiazu.app.beans.IsCollectBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.ToKenDaoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class WebActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    /*@BindView(R.id.webView)
    WebView mWebView;*/
    @BindView(R.id.title)
    TextView ntitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;
    MyWebView mWebView;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.collect)
    CheckBox mCollect;
    private List<String> urlList = new ArrayList<>(); // 记录访问的URL
    private List<String> titleList = new ArrayList<>(); // 记录访问的Title

    private String mUrl;
    private String mSummary;
    private String mPicture = "";
    private String mArticleId;
    private String mArticleType;
    private String mBiao;
    private Intent mIntent;
    private int mPosition;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String referrerCode = PreUtils.getString("referrerCode", "");
            String shareU = urlList.get(urlList.size() - 1);
            if (shareU.contains("?")) {
                shareU = shareU + "&referrerCode=" + referrerCode;
            } else {
                shareU = shareU + "?referrerCode=" + referrerCode;
            }
            Log.i("yx565", "handleMessage: "+shareU);
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl(shareU, bitmap, titleList.get(titleList.size() - 1), true,mSummary,true);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl(shareU, bitmap, titleList.get(titleList.size() - 1), false,mSummary,true);
                    break;
            }

        }
    };

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        String target = mIntent.getStringExtra("target");
        mBiao = mIntent.getStringExtra("biao");

        List<ToKenDaoBean> select = DataBaseMannger.getIntrance().select();
        String access_token = select.get(select.size() - 1).getAccess_token();
        String referrerCode = PreUtils.getString("referrerCode", "");
        if (target.contains("?")) {
            target = target + "&token=" + access_token+"&app="+"app"+"&referrerCode="+referrerCode;
        } else {
            target = target + "?token=" + access_token+"&app="+"app"+"&referrerCode="+referrerCode;
        }
        if("collect".equals(mBiao)){
            mPosition = mIntent.getIntExtra("position", 0);
            mShare.setVisibility(View.VISIBLE);
            urlList.add(target);
        }
        if("home".equals(mBiao)){
            urlList.add(target);
            mShare.setVisibility(View.VISIBLE);
        }
        addWeb(target);
        initPop();
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
            }
        });
        mCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = mCollect.isChecked();
                HashMap<String, String> map = new HashMap<>();
                map.put("summary",mSummary);
                map.put("pic",mPicture);
                map.put("articleId",mArticleId);
                map.put("articleType",mArticleType);
                map.put("url",urlList.get(urlList.size()-1));
                map.put("title",titleList.get(titleList.size()-1));
                if (checked) {
                    mPresenter.getDataP(map,DifferentiateEnum.WEBCOLLECT);
                }else {
                    mPresenter.getDataP(map,DifferentiateEnum.WEBCOLLECT);
                }
            }
        });
    }

    @SuppressLint({"JavascriptInterface", "ClickableViewAccessibility"})
    private void addWeb(String target) {
        mWebView = new MyWebView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mWebView.setLayoutParams(params);


        //使用webview显示html代码
//        webView.loadDataWithBaseURL(null,"<html><head><title> 欢迎您 </title></head>" +
//                "<body><h2>使用webview显示 html代码</h2></body></html>", "text/html" , "utf-8", null);


        WebSettings webSettings = mWebView.getSettings();


        /**
         * LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
         * LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
         * LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
         * LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
         */
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存，只从网络获取数据.

        //支持屏幕缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        //webSettings.setBlockNetworkImage(true);// 把图片加载放在最后来加载渲染
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSaveFormData(true);
        webSettings.setTextZoom(100);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);/// 支持通过JS打开新窗口
        //webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);//允许使用js
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");//添加js监听 这样html就能调用客户端
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(webViewClient);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        Log.i("yx333", "addWeb: " + target);
        mWebView.loadUrl(target);//加载url
        mContentPanel.addView(mWebView);
        /*mWebView.requestFocus(View.FOCUS_DOWN);
        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });*/
    }
    public class InJavaScriptLocalObj
    {
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void showSource(String html) {
            Log.i("yx777", "showSource: "+html);
            mSummary = html;
        }

        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void showDescription(String str) {
            Log.i("yx777", "showDescription: "+str);
            mPicture = str;
        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void showArticleId(String str) {
            Log.i("yx777", "showDescription: "+str);
            mArticleId = str;
            if(!"".equals(str)){
                mPresenter.getDataP(mArticleId,DifferentiateEnum.WEBISCOLLECT);
            }else {
                mCollect.setChecked(false);
            }
        }
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void showArticleType(String str) {
            Log.i("yx777", "showDescription: "+str);
            mArticleType = str;
        }
    }

    private View mInflate;
    private PopupWindow mPopupWindow1;
    private float height = 540;// 滑动开始变色的高,此高度是由广告轮播或其他首页view高度决定

    public void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(mPicture)){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    String referrerCode = PreUtils.getString("referrerCode", "");
                    String shareU = urlList.get(urlList.size() - 1);
                    if (shareU.contains("?")) {
                        shareU = shareU + "&referrerCode=" + referrerCode;
                    } else {
                        shareU = shareU + "?referrerCode=" + referrerCode;
                    }
                    WechatShareTool.shareToWXUrl(shareU, bitmap, titleList.get(titleList.size() - 1), true, mSummary, false);
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mPicture).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=1;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }


            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equals(mPicture)){
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    String referrerCode = PreUtils.getString("referrerCode", "");
                    String shareU = urlList.get(urlList.size() - 1);
                    if (shareU.contains("?")) {
                        shareU = shareU + "&referrerCode=" + referrerCode;
                    } else {
                        shareU = shareU + "?referrerCode=" + referrerCode;
                    }
                    WechatShareTool.shareToWXUrl(shareU, bitmap, titleList.get(titleList.size() - 1), false,mSummary,false);
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mPicture).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=2;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

            }
        });
        TextView quxiao = mInflate.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
    }
    @SuppressLint("NewApi")
    //WebViewClient主要帮助WebView处理各种通知、请求事件
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {//页面加载完成
            if(progressBar!=null){
                progressBar.setVisibility(View.GONE);
            }

            //super.onPageFinished(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
            if(progressBar!=null){
                progressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //Log.i("ansen","拦截url:"+url);
            /*if (url.equals("http://www.google.com/")) {
                //Toast.makeText(MainActivity.this,"国内不能访问google,拦截该url",Toast.LENGTH_LONG).show();
                return true;//表示我已经处理过了
            }
            return super.shouldOverrideUrlLoading(view, url);*/
            if (!urlList.contains(url)) {
                Log.i("yx666", "shouldOverrideUrlLoading: "+url);
                addWeb(url);
                urlList.add(url);
                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }

        }

    };
    //WebChromeClient主要辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度等
    private WebChromeClient webChromeClient = new WebChromeClient() {
        //不支持js的alert弹窗，需要自己监听然后通过dialog弹窗
        @Override
        public boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
            /*AlertDialog.Builder localBuilder = new AlertDialog.Builder(webView.getContext());
            localBuilder.setMessage(message).setPositiveButton("确定",null);
            localBuilder.setCancelable(false);
            localBuilder.create().show();*/

            //注意:
            //必须要这一句代码:result.confirm()表示:
            //处理结果为确定状态同时唤醒WebCore线程
            //否则不能继续点击按钮
            result.confirm();
            return true;
        }

        //获取网页标题
        @SuppressLint("NewApi")
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            Log.d("ansen", "网页标题:" + title);
            boolean httpUrl = isHttpUrl(title);
            view.loadUrl("javascript:window.local_obj.showSource("
                    +"document.getElementById('summary').innerHTML"
                    + ");");

            // 获取页面内容
            view.loadUrl("javascript:window.local_obj.showDescription("
                    + "document.getElementById('picture').innerHTML"
                    + ");");
            // 获取页面内容
            view.loadUrl("javascript:window.local_obj.showArticleId("
                    + "document.getElementById('articleId').innerHTML"
                    + ");");
            // 获取页面内容
            view.loadUrl("javascript:window.local_obj.showArticleType("
                    + "document.getElementById('articleType').innerHTML"
                    + ");");
            Log.i("yx333", title+"onReceivedTitle: " + httpUrl);
            if (httpUrl) {
                ntitle.setText("");
            } else if(!title.contains("EncyclopediaParenting")){
                if (!"文章查看".equals(title)) {
                    Log.i("yx333", "onReceivedTitle: " + title);
                    if (view.getUrl().contains("EncyclopediaParenting/content.html") || view.getUrl().contains("EncyclopediaParenting/CanDoArticle.html")) {
                        if(ntitle!=null){
                            ntitle.setText("");
                            titleList.add(title);
                            mCollect.setVisibility(View.VISIBLE);
                            mWebView.setOnScrollChangeListener(new MyWebView.OnScrollChangeListener() {
                                @Override
                                public void onPageEnd(int l, int t, int oldl, int oldt) {
                                    ntitle.setText(title);
                                }

                                @Override
                                public void onPageTop(int l, int t, int oldl, int oldt) {
                                    ntitle.setText("");
                                }

                                @Override
                                public void onScrollChanged(int l, int t, int oldl, int oldt) {

                                }
                            });
                        }


                    } else {
                        if(ntitle!=null){
                            ntitle.setText(title);
                            titleList.add(title);
                            mCollect.setVisibility(View.GONE);
                        }

                    }
                }

            }
        }

        //加载进度回调
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(progressBar!=null){
                progressBar.setProgress(newProgress);//设置进度值
            }
        }
    };

    /**
     * 判断字符串是否为URL
     *
     * @param urls 需要判断的String类型url
     * @return true:是URL；false:不是URL
     */
    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//对比
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case WEBCOLLECT:
                CollectBean collectBean = (CollectBean) o;
                String code = collectBean.getCode();
                if("0".equals(code)){
                    ToastUtils.showShortToast(WebActivity.this,collectBean.getResult());
                }
                break;
            case WEBISCOLLECT:
                IsCollectBean isCollectBean = (IsCollectBean) o;
                int result = isCollectBean.getResult();
                if(result == 0){
                    mCollect.setChecked(false);
                }else if(result == 1){
                    mCollect.setChecked(true);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick(R.id.iv_finis)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                int childCount = mContentPanel.getChildCount();
                Log.i("yx123", "onClick: " + childCount);
                if (childCount > 2) {
                    mContentPanel.removeViewAt(childCount - 1);
                    urlList.remove(urlList.size() - 1);
                    if (titleList.size() > 0) {
                        titleList.remove(titleList.size() - 1);
                    }
                    if (titleList.size() > 0) {
                        ntitle.setText(titleList.get(titleList.size() - 1));
                    }
                    if(!urlList.contains("EncyclopediaParenting/content.html")||!urlList.contains("EncyclopediaParenting/CanDoArticle.html")){
                        mCollect.setVisibility(View.GONE);
                    }
                    mPicture = "";
                    //DataCleanManager.clearAllCache(WebActivity.this);
                } else {
                    if("collect".equals(mBiao)){
                        boolean checked = mCollect.isChecked();
                        mIntent.putExtra("checked",checked);
                        mIntent.putExtra("position",mPosition);
                        setResult(25,mIntent);
                        finish();
                    }else {
                        super.onBackPressed();
                    }
                }
                break;
        }
    }

    /**
     * 重写onKeyDown，当浏览网页，WebView可以后退时执行后退操作。
     * false 执行安卓返回方法即webview返回上一页 true 表示h5处理返回事件，android端不再处理
     *
     * @return
     */
    // 返回处理时移除容器的最顶的视图（即当前页面视图）
    @Override
    public void onBackPressed() { // 点击返回按钮事件
        int childCount = mContentPanel.getChildCount();
        if (childCount > 2) {
            mContentPanel.removeViewAt(childCount - 1);
            urlList.remove(urlList.size() - 1);
            if (titleList.size() > 0) {
                titleList.remove(titleList.size() - 1);
            }
            if (titleList.size() > 0) {
                ntitle.setText(titleList.get(titleList.size() - 1));
            }
            if(!urlList.contains("EncyclopediaParenting/content.html")||!urlList.contains("EncyclopediaParenting/CanDoArticle.html")){
                mCollect.setVisibility(View.GONE);
            }
            mPicture = "";
        } else {
            if("collect".equals(mBiao)){
                boolean checked = mCollect.isChecked();
                mIntent.putExtra("checked",checked);
                mIntent.putExtra("position",mPosition);
                setResult(25,mIntent);
                finish();
            }else {
                finish();
            }
        }

    }

}
