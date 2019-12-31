package com.tiancaijiazu.app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.widget.TextView;

import com.tiancaijiazu.app.utils.city.ToastUtils;

public class CopyButtonLibraryDanJi {
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Context context;
    private String textView;

    public CopyButtonLibraryDanJi(Context context, String textView) {
        this.context = context;
        this.textView = textView;
    }

    public void init() {
        myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String text;
        text = textView;

        myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
        ToastUtils.showShortToast(context,"已复制");
    }
}
