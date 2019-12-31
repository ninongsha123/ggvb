package com.tiancaijiazu.app.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.TeamBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.amap.api.maps.model.BitmapDescriptorFactory.getContext;

/**
 * Created by Administrator on 2019/4/26/026.
 */

public class RlvAdapter_group extends RecyclerView.Adapter {



    private List<TeamBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;

    public RlvAdapter_group(Context myTeamActivity) {
//        this.mData = resultBeans;
        this.mContext = myTeamActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_groups, parent, false);
            return new ViewHolder(inflate);
        } else {
            View inflate1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
            return new ViewHolder1(inflate1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType==0){
            ViewHolder holder1 = (ViewHolder) holder;
            holder1.mIjia.setEnabled(true);
        }else {
            ViewHolder1 holder2 = (ViewHolder1) holder;
            holder2.mTxt.setText("你无技术规范请问，很多我去和回复,快速导航和烦恼加快新技术俗话。苏北丢包办法把课本能否库房内，顶峰森林防火你违法哪...");
            holder2.mZanshu.setText("100");
            holder2.mPingshu.setText("100");
            holder2.mDian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPop();
                }
            });
        }
    }
        private void showPop() {
            mInflate = LayoutInflater.from(getContext()).inflate(R.layout.pop_black, null);
            mPopupWindow = new PopupWindow(mInflate,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopupWindow.setFocusable(true);// 取得焦点
            mPopupWindow.setClippingEnabled(false);
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            mPopupWindow.setOutsideTouchable(true);
            //设置可以点击
            mPopupWindow.setTouchable(true);
            //进入退出的动画，指定刚才定义的style
            mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
            TextView who = mInflate.findViewById(R.id.whok);
            TextView delete = mInflate.findViewById(R.id.deletes);
            who.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pop();
                }
            });
        }

//        holder1.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mLisiter != null) {
//                    mLisiter.onClicker(view, position, mData);
//                }
//            }
//        });
//        if (position == mData.size() - 1) {
////            holder1.mV.setVisibility(View.GONE);
//        }


    private void pop() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_who_select, null);
        mPopupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        RelativeLayout quan = inflate.findViewById(R.id.quan);
        RelativeLayout friend = inflate.findViewById(R.id.friend);
        RelativeLayout just = inflate.findViewById(R.id.just);
        TextView quans = inflate.findViewById(R.id.tv_quan);
        TextView friends = inflate.findViewById(R.id.tv_friend);
        TextView justs = inflate.findViewById(R.id.tv_just);
        RelativeLayout guan = inflate.findViewById(R.id.guan);

        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = quans.getText().toString();
                mPopupWindow1.dismiss();
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = friends.getText().toString();
                mPopupWindow1.dismiss();
            }
        });
        just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = justs.getText().toString();
                mPopupWindow1.dismiss();
            }
        });
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void addData(List<TeamBean.ResultBean> result, boolean b) {
        if (b) {
            if (mData != null) {
                mData.clear();
            }
        }

        this.mData.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ijia)
        ImageView mIjia;
        @BindView(R.id.line)
        RelativeLayout mLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.rlv_data)
        RecyclerView mRlvData;
        @BindView(R.id.txt)
        TextView mTxt;
        @BindView(R.id.zan)
        ImageView mZan;
        @BindView(R.id.zanshu)
        TextView mZanshu;
        @BindView(R.id.ijia)
        ImageView mIjia;
        @BindView(R.id.pingshu)
        TextView mPingshu;
        @BindView(R.id.dian)
        ImageView mDian;
        @BindView(R.id.line)
        RelativeLayout mLine;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter {
        void onClicker(View view, int position, List<TeamBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }
}
