package com.tiancaijiazu.app.rongim;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShopActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Message;

import static com.tiancaijiazu.app.app.App.getAppContext;

/**
 * Created by longShun on 2017/2/24.
 * desc新建一个消息类继承 IContainerItemProvider.MessageProvider 类，实现对应接口方法，
 * 1.注意开头的注解！
 * 2.注意泛型！
 */
@ProviderTag(
        messageContent = PhoneInfo.class,
        showReadState = true
)
public class RedPackageItemProvider extends IContainerItemProvider.MessageProvider<PhoneInfo> {
    private Context mContext;
    public RedPackageItemProvider() {
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        mContext = context;
        //这就是展示在会话界面的自定义的消息的布局
        View view = LayoutInflater.from(context).inflate(R.layout.item_customize_message, null);
        ViewHolder holder = new ViewHolder();
        holder.remoteurl = (ImageView) view.findViewById(R.id.remoteurl);
        holder.content = (TextView) view.findViewById(R.id.content);
        holder.title = (TextView) view.findViewById(R.id.title);
        holder.message = (RelativeLayout) view.findViewById(R.id.message);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, int i, PhoneInfo customizeMessage, UIMessage message) {
        //根据需求，适配数据
        ViewHolder holder = (ViewHolder) view.getTag();

        if (message.getMessageDirection() == Message.MessageDirection.SEND) {//消息方向，自己发送的
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }
        //AndroidEmoji.ensure((Spannable) holder.message.getText());//显示消息中的 Emoji 表情。
        //holder.tvTitle.setText(redPackageMessage.getTitle());
        Glide.with(App.getApplication()).load(customizeMessage.getRemoteurl()).into(holder.remoteurl);

        holder.content.setText(customizeMessage.getContent());

        holder.title.setText(customizeMessage.getTitle());
    }

    @Override
    public Spannable getContentSummary(PhoneInfo phoneInfo) {
        return new SpannableString(phoneInfo.getContent());
    }

    @Override
    public void onItemClick(View view, int i, PhoneInfo phoneInfo, UIMessage uiMessage) {
        Intent intent = new Intent(mContext, ShopActivity.class);
        String shopBiao = PreUtils.getString("shopBiao", "");
        if ("target".equals(shopBiao)) {
            String tar = PreUtils.getString("tar", "");
            intent.putExtra("target", tar);
        } else if ("id".equals(shopBiao)) {
            String shopId = PreUtils.getString("shopId", "");
            intent.putExtra("id", shopId);
        }
        mContext.startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int i, PhoneInfo phoneInfo, UIMessage uiMessage) {

    }

    private static class ViewHolder {
        ImageView remoteurl;
        TextView content, title;
        RelativeLayout message;
    }
}
