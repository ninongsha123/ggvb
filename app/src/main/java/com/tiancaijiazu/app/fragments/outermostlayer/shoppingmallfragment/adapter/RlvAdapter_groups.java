package com.tiancaijiazu.app.fragments.outermostlayer.shoppingmallfragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.beans.ArticleLists;
import com.tiancaijiazu.app.beans.GroupListBean;
import com.tiancaijiazu.app.beans.TopicDataBean;
import com.tiancaijiazu.app.utils.ImageLoader;
import com.tiancaijiazu.app.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;
import www.linwg.org.lib.LCardView;

public class RlvAdapter_groups extends RecyclerView.Adapter  {
    public ArrayList<GroupListBean.ResultBean> mData;
    private Context mContext;
    private setOnClickData mLisiter2;
    private onClickLisiterPraise mLisiter;
    private static final int TYPE_ITEM_FOOTER = 1;
    private onClickLisiterUser mLisiterUser;

    private int pos;

    public RlvAdapter_groups(Context context, ArrayList<GroupListBean.ResultBean> resultBeans) {
        this.mContext = context;
        this.mData = resultBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_data_one, viewGroup, false);
        return new ViewHolder(inflate);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder holder = (ViewHolder) viewHolder;
        if (mData.size() != 0) {
            int isRecommend = mData.get(i).getIsRecommend();
            if (isRecommend == 0) {
                holder.mCommRec.setVisibility(View.GONE);
            } else if (isRecommend == 1) {
                holder.mCommRec.setVisibility(View.VISIBLE);
            }
            Glide.with(mContext).load(mData.get(i).getUserAvatar()).into(holder.mIvHead);
            String picUri = mData.get(i).getPicUri();
            String[] split = picUri.split("\\?");
            if (split.length > 1) {
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
            }

            //holder.mMasonryItemImg.setInitSize(i1,height1);
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
            holder.mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.mIvCollect.performClick();
                }
            });
        }
        holder.mIvHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiterUser != null) {
                    mLisiterUser.onClickerUser(view, i, mData);
                }
            }
        });
        holder.mTvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLisiterUser != null) {
                    mLisiterUser.onClickerUser(view, i, mData);
                }
            }
        });
        holder.mIvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.size() != 0) {
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
            }
        });

        holder.mLineTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLisiter2 != null) {
                    mLisiter2.onClickData(v, i, mData);
                }
            }
        });
    }
//        if(mTopicData.size()!=0){
//            int isRecommend = mTopicData.get(i).getIsRecommend();
//            if(isRecommend == 0){
//                holder.mCommRec.setVisibility(View.GONE);
//            }else if(isRecommend == 1){
//                holder.mCommRec.setVisibility(View.VISIBLE);
//            }
//            Glide.with(mContext).load(mTopicData.get(i).getUserAvatar()).into(holder.mIvHead);
//            String picUri = mTopicData.get(i).getPicUri();
//            String[] split = picUri.split("\\?");
//            Log.i("yx565", picUri+"onBindViewHolder: "+split.length);
//            if(split.length>1){
//                String[] split1 = split[1].split(",");
//                int width = Integer.parseInt(split1[0]);
//                int height = Integer.parseInt(split1[1]);
//                int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, mContext.getResources().getDisplayMetrics());
//                int i1 = (ScreenUtil.getInstance(mContext).getScreenWidth() - w) / 2;
//                //等比缩放
//                double ratio = (i1 * 1.0) / width;
//                int height1 = (int) (height * ratio);
//                ViewGroup.LayoutParams imageLayoutParams = holder.mMasonryItemImg.getLayoutParams();
//                //float scale = (float) mData.get(i).getHeight() / (float) mData.get(i).getWidth();
//                imageLayoutParams.width = i1;//获取实际展示的图片宽度
//                imageLayoutParams.height = height1;//获取最终图片高度
//                holder.mMasonryItemImg.setLayoutParams(imageLayoutParams);//应用高度到布局中
//            }
//
//            ImageLoader.load(mContext,
//                    mTopicData.get(i).getPicUri(), holder.mMasonryItemImg);
//            holder.mMasonryItemTitle.setText(mTopicData.get(i).getTitle());
//            holder.mTvName.setText(mTopicData.get(i).getNickname());
//            if (mTopicData.get(i).getLikes() != 0) {
//                holder.mLikeSum.setText(mTopicData.get(i).getLikes() + "");
//            } else {
//                holder.mLikeSum.setText("");
//            }
//            if (mTopicData.get(i).getIsLikes() != 0) {
//                holder.mIvCollect.setChecked(true);
//            } else {
//                holder.mIvCollect.setChecked(false);
//            }
//        }

/*    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int position = holder.getLayoutPosition();
            if (getItemViewType(position) == TYPE_ITEM_FOOTER) {
                params.setFullSpan(true);
            }
        }
    }*/

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<GroupListBean.ResultBean> data, boolean b) {
        if(b){
            if(mData!=null){
                mData.clear();
            }
            this.mData.addAll(data);
            notifyDataSetChanged();
        }else {
            this.mData.addAll(data);
            notifyItemRangeInserted(mData.size() - data.size(), data.size());
        }
    }
    public void addData1(List<GroupListBean.ResultBean> data,int s) {
        this.mData.addAll(data);
        notifyDataSetChanged();
        notifyItemMoved(s,0);
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.masonry_item_img)
        ImageView mMasonryItemImg;
        @BindView(R.id.masonry_item_title)
        TextView mMasonryItemTitle;
        @BindView(R.id.iv_head)
        CircleImageView mIvHead;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.like_sum)
        TextView mLikeSum;
        @BindView(R.id.iv_collect)
        CheckBox mIvCollect;
        @BindView(R.id.cardView)
        LCardView mCardView;
        @BindView(R.id.line_tu)
        LinearLayout mLineTu;
        @BindView(R.id.text)
        TextView mText;
        @BindView(R.id.comm_rec)
        ImageView mCommRec;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public interface setOnClickData {
        void onClickData(View view, int position, ArrayList<GroupListBean.ResultBean> mData);
    }

    public void onsetOnClickData(setOnClickData setOnClickData) {
        this.mLisiter2 = setOnClickData;
    }

    public interface onClickLisiterUser{
        void onClickerUser(View view,int position,ArrayList<GroupListBean.ResultBean> mData);
    }

    public void setOnClickLisiterUser(onClickLisiterUser lisiterUser){
        this.mLisiterUser = lisiterUser;
    }

    public interface onClickLisiterPraise {
        void onClickPraise(View view, int position, CheckBox mIvCollect, ArrayList<GroupListBean.ResultBean> mData);
    }

    public void setOnClickLisiterPraise(onClickLisiterPraise lisiterPraise) {
        this.mLisiter = lisiterPraise;
    }

}
