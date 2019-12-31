package com.tiancaijiazu.app.activitys.user_fragment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MyCollectListBean;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/6/4/004.
 */

public class RlvAdapter_userCollect extends RecyclerView.Adapter {
    public List<MyCollectListBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiter mLisiter;
    private onClickLisiterXin mLisiterXin;

    public RlvAdapter_userCollect(List<MyCollectListBean.ResultBean> collectList, Context context) {
        this.mData = collectList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_data_one, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        //viewHolder.setIsRecyclable(false);
        final ViewHolder holder = (ViewHolder) viewHolder;

        /*if (i < 2) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(dp2px(6), dp2px(11), dp2px(6), dp2px(5));//4个参数按顺序分别是左上右下
            holder.mCardView.setLayoutParams(layoutParams);
        }*/
        Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mIvHead);
        String picUri = mData.get(i).getPicUri();
        String[] split = picUri.split("\\?");
        String[] split1 = split[1].split(",");
        int width = Integer.parseInt(split1[0]);
        int height = Integer.parseInt(split1[1]);
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, mContext.getResources().getDisplayMetrics());
        int i1 = (ScreenUtil.getInstance(mContext).getScreenWidth() - w) / 2;
        //等比缩放
        double ratio = (i1 * 1.0) / width;
        int height1 = (int) (height * ratio);
        ViewGroup.LayoutParams imageLayoutParams = holder.mMasonryItemImg.getLayoutParams();
        //float scale = (float) mData.get(i).getHeight() / (float) mData.get(i).getWidth();
        imageLayoutParams.width = i1;//获取实际展示的图片宽度
        imageLayoutParams.height = height1;//获取最终图片高度
        holder.mMasonryItemImg.setLayoutParams(imageLayoutParams);//应用高度到布局中
        RequestOptions options = new RequestOptions();
        options.diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext).load(mData.get(i).getPicUri()).apply(options).into(holder.mMasonryItemImg);
        holder.mMasonryItemTitle.setText(mData.get(i).getTitle());
        holder.mTvName.setText(mData.get(i).getNickname());
        if (mData.get(i).getLikes() != 0) {
            holder.mLikeSum.setText(mData.get(i).getLikes() + "");
        } else {
            holder.mLikeSum.setText("");
        }

        if (mData.get(i).getIsLikes() != 0) {
            holder.mIvCollect.setChecked(true);
        } else {
            holder.mIvCollect.setChecked(false);
        }
        holder.mIvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = holder.mIvCollect.isChecked();
                if(checked){
                    mData.get(i).setLikes(mData.get(i).getLikes()+1);
                    mData.get(i).setIsLikes(1);
                    holder.mLikeSum.setText(mData.get(i).getLikes()+ "");
                }else {
                    mData.get(i).setLikes(mData.get(i).getLikes()-1);
                    mData.get(i).setIsLikes(0);
                    if (mData.get(i).getLikes() != 0) {
                        holder.mLikeSum.setText(mData.get(i).getLikes() + "");
                    }else {
                        holder.mLikeSum.setText("");
                    }
                }
                if(mLisiterXin!=null){
                    mLisiterXin.onClickerXin(view,i,mData);
                }
            }
        });
        holder.mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mIvCollect.performClick();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mLisiter!=null){
                    mLisiter.onClicker(v,i,mData);
                }
            }
        });

    }
    private int dp2px(float dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<MyCollectListBean.ResultBean> collectList, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
        }
        this.mData.addAll(collectList);
        if(b){
            notifyDataSetChanged();
        }else {
            notifyItemRangeInserted(mData.size() - collectList.size(), collectList.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.masonry_item_img)
        ImageView mMasonryItemImg;
        @BindView(R.id.masonry_item_title)
        TextView mMasonryItemTitle;
        @BindView(R.id.line_tu)
        LinearLayout mLineTu;
        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.iv_collect)
        CheckBox mIvCollect;
        @BindView(R.id.like_sum)
        TextView mLikeSum;
        @BindView(R.id.relative)
        LinearLayout mRelative;
        @BindView(R.id.cardView)
        LCardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface onClickLisiter{
        void onClicker(View view,int positon,List<MyCollectListBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiter = lisiter;
    }

    public interface onClickLisiterXin{
        void onClickerXin(View view,int position,List<MyCollectListBean.ResultBean> mData);
    }

    public void setOnClickLisiterXin(onClickLisiterXin lisiterXin){
        this.mLisiterXin = lisiterXin;
    }
}
