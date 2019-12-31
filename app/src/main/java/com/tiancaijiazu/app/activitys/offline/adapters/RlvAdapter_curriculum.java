package com.tiancaijiazu.app.activitys.offline.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.utils.ImageLoader;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.ScreenUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class RlvAdapter_curriculum extends RecyclerView.Adapter {
    private String[] mData;
    private Context mContext;

    public RlvAdapter_curriculum(String[] split, Context context) {
        this.mData = split;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_img_layout, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        String[] split = mData[i].split("\\?");
        if(split.length>1){
            String[] split1 = split[1].split(",");
            ViewGroup.LayoutParams layoutParams = holder.mIv.getLayoutParams();
            int screenWidth = ScreenUtil.getInstance(mContext).getScreenWidth();
            Log.i("yx456", "onBindViewHolder: "+screenWidth);
            int i1 = ScreenStatusUtil.setDp(62, mContext);
            int width = screenWidth - i1;
            //if(Integer.parseInt(split1[0])>screenWidth){
                //等比缩放
                double ratio = (width * 1.0) / Integer.parseInt(split1[0]);
                int height1 = (int) (Integer.parseInt(split1[1]) * ratio);
                layoutParams.width = width;
                layoutParams.height = height1;
            /*}else{
                layoutParams.width = Integer.parseInt(split1[0]);
                layoutParams.height = Integer.parseInt(split1[1]);
            }*/
            holder.mIv.setLayoutParams(layoutParams);
            ImageLoader.load(mContext,
                    mData[i], holder.mIv);
        }

    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv)
        ImageView mIv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
