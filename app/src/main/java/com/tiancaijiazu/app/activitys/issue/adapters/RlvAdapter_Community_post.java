package com.tiancaijiazu.app.activitys.issue.adapters;

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
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.MyReleasedListBean;
import com.tiancaijiazu.app.utils.ImageLoader;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import www.linwg.org.lib.LCardView;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class RlvAdapter_Community_post extends RecyclerView.Adapter {
    public ArrayList<MyReleasedListBean.ResultBean> mData;
    private Context mContext;
    private onClickLisiterPraise mLisiter;
    private onClickLisiter mLisiterJ;
    private onClickLisiterUser mLisiterUser;

    public RlvAdapter_Community_post(ArrayList<MyReleasedListBean.ResultBean> resultBeans, Context context) {
        this.mData = resultBeans;
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
        ViewHolder holder = (ViewHolder) viewHolder;
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

        ImageLoader.load(mContext,
                mData.get(i).getPicUri(), holder.mMasonryItemImg);
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
            public void onClick(View v) {
                boolean checked = holder.mIvCollect.isChecked();
                if (checked) {
                    mData.get(i).setLikes(mData.get(i).getLikes() + 1);
                    mData.get(i).setIsLikes(1);
                    holder.mLikeSum.setText(mData.get(i).getLikes() + "");
                } else {
                    mData.get(i).setLikes(mData.get(i).getLikes() - 1);
                    mData.get(i).setIsLikes(0);
                    if (mData.get(i).getLikes() != 0) {
                        holder.mLikeSum.setText(mData.get(i).getLikes() + "");
                    } else {
                        holder.mLikeSum.setText("");
                    }
                }
                if (mLisiter != null) {
                    mLisiter.onClickPraise(v, i, holder.mIvCollect, mData);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterJ!=null){
                    mLisiterJ.onClickLisiter(view,i,mData);
                }
            }
        });

        holder.mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLisiterUser!=null){
                    mLisiterUser.onClickLisiter(view,i,mData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<MyReleasedListBean.ResultBean> result, boolean b) {
        if (b) {
            if (mData != null) {
                mData.clear();
            }
        }

        this.mData.addAll(result);
        if(b){
            notifyDataSetChanged();
        }else {
            notifyItemRangeInserted(mData.size() - result.size(), result.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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

    public interface onClickLisiterPraise {
        void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<MyReleasedListBean.ResultBean> mData);
    }

    public void setOnClickLisiterPraise(onClickLisiterPraise lisiterPraise) {
        this.mLisiter = lisiterPraise;
    }

    public interface onClickLisiter{
        void onClickLisiter(View view,int position,ArrayList<MyReleasedListBean.ResultBean> mData);
    }

    public void setOnClickLisiter(onClickLisiter lisiter){
        this.mLisiterJ = lisiter;
    }

    public interface onClickLisiterUser{
        void onClickLisiter(View view,int position,ArrayList<MyReleasedListBean.ResultBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser){
        this.mLisiterUser = lisiterUser;
    }
}
