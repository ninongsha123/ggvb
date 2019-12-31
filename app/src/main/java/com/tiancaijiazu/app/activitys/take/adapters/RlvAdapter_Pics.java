package com.tiancaijiazu.app.activitys.take.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.take.TakeAClassActivity;
import com.tiancaijiazu.app.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RlvAdapter_Pics extends RecyclerView.Adapter {
    private String[] mData;
    private TakeAClassActivity mContext;

    public RlvAdapter_Pics(String[] split, TakeAClassActivity context) {
        this.mData = split;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pics_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String[] split = mData[i].split("\\?");
        String[] split1 = split[1].split(",");
        int width = Integer.parseInt(split1[0]);
        int height = Integer.parseInt(split1[1]);
        ViewGroup.LayoutParams layoutParams = holder.mPics.getLayoutParams();
        int w = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, mContext.getResources().getDisplayMetrics());
        int i1 = (ScreenUtil.getInstance(mContext).getScreenWidth() - w);
        //等比缩放
        double ratio = (i1 * 1.0) / width;
        int height1 = (int) (height * ratio);
        layoutParams.width = i1;
        layoutParams.height = height1;
        holder.mPics.setLayoutParams(layoutParams);
        Glide.with(mContext).load(mData[i]).into(holder.mPics);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.pics)
        ImageView mPics;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
