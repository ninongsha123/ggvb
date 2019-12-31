package com.tiancaijiazu.app.activitys.views;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import com.tiancaijiazu.app.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyWebView extends WebView {
    private OnScrollChangeListener mOnScrollChangeListener;
    private int height = 540;

    public MyWebView(Context context) {
        this(context,null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle","attr","android"));
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

   /* public String stringByEvaluatingJavaScriptFromString(String script) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            try {
                Field mp = WebView.class.getDeclaredField("mProvider");
                mp.setAccessible(true);
                Object webViewObject = mp.get(this);
                Field wc = webViewObject.getClass().getDeclaredField("mWebViewCore");
                wc.setAccessible(true);
                Object webViewCore = wc.get(webViewObject);
                Field bf = webViewCore.getClass().getDeclaredField("mBrowserFrame");
                bf.setAccessible(true);
                Object browserFrame = bf.get(webViewCore);
                Method stringByEvaluatingJavaScriptFromString = browserFrame.getClass()
                        .getDeclaredMethod("stringByEvaluatingJavaScriptFromString",
                                String.class);
                stringByEvaluatingJavaScriptFromString.setAccessible(true);
                Object obj_value = stringByEvaluatingJavaScriptFromString.invoke(
                        browserFrame, script);
                return String.valueOf(obj_value);
            } catch (Exception e) {
                Log.e("!!!", "stringByEvaluatingJavaScriptFromString", e);
            }
            return null;
        } else {
            try {
                Field[] fields = WebView.class.getDeclaredFields();
                // 由webview取到webviewcore
                Field field_webviewcore = WebView.class.getDeclaredField("mWebViewCore");
                field_webviewcore.setAccessible(true);
                Object obj_webviewcore = field_webviewcore.get(this);
                // 由webviewcore取到BrowserFrame
                Field field_BrowserFrame = obj_webviewcore.getClass().getDeclaredField(
                        "mBrowserFrame");
                field_BrowserFrame.setAccessible(true);
                Object obj_frame = field_BrowserFrame.get(obj_webviewcore);
                // 获取BrowserFrame对象的stringByEvaluatingJavaScriptFromString方法
                Method method_stringByEvaluatingJavaScriptFromString = obj_frame.getClass()
                        .getMethod("stringByEvaluatingJavaScriptFromString", String.class);
                // 执行stringByEvaluatingJavaScriptFromString方法
                Object obj_value = method_stringByEvaluatingJavaScriptFromString.invoke(
                        obj_frame,
                        script);
                // 返回执行结果
                return String.valueOf(obj_value);
            } catch (Exception e) {
                Log.e("!!!", "stringByEvaluatingJavaScriptFromString", e);
            }
            return null;
        }
    }*/

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        // 当前webview的高度
        float webnow = getHeight() + getScrollY();
        Log.i("yx666", "onScrollChanged: "+getScrollY());
        if (getScrollY() > height) {
            //处于底端
            if(mOnScrollChangeListener!=null){
                mOnScrollChangeListener.onPageEnd(l, t, oldl, oldt);
            }
        } else if (getScrollY() == 0) {
            //处于顶端
            if(mOnScrollChangeListener!=null){
                mOnScrollChangeListener.onPageTop(l, t, oldl, oldt);
            }
        } else {
            if(mOnScrollChangeListener!=null){
                mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
            }
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
    }

    public interface OnScrollChangeListener {

        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt);

    }

}
