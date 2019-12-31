package com.tiancaijiazu.app.activitys.early.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.beans.FormalCurriculumBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/1/001.
 */

public class ExpandableListViewAdapter_trial_class extends BaseExpandableListAdapter {
    // 定义一个Context
    private Context context;
    // 定义一个LayoutInflater
    private LayoutInflater mInflater;
    // 定义一个List来保存列表数据
    private List<FormalCurriculumBean.ResultBean.ChapterListBean> data_list = new ArrayList<>();
    private HolderView childrenView;

    // 定义一个构造方法
    public ExpandableListViewAdapter_trial_class(Context context, List<FormalCurriculumBean.ResultBean.ChapterListBean> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data_list = datas;
    }

    // 刷新数据
    public void flashData(List<FormalCurriculumBean.ResultBean.ChapterListBean> datas) {
        if (data_list != null) {
            data_list.clear();
        }
        this.data_list.addAll(datas);
        this.notifyDataSetChanged();
    }

    // 获取二级列表的内容
    @Override
    public Object getChild(int arg0, int arg1) {
        return data_list.get(arg0).getContentsList().get(arg1);
    }

    // 获取二级列表的ID
    @Override
    public long getChildId(int arg0, int arg1) {
        return arg1;
    }

    // 定义二级列表中的数据
    @Override
    public View getChildView(int arg0, int arg1, boolean arg2, View arg3, ViewGroup arg4) {
        if(data_list.get(arg0).getContentsList().size()!=0){
            if (data_list.get(arg0).getContentsList().get(arg1).getIsGame()==1) {
                if (arg3 == null) {
                    childrenView = new HolderView();
                    // 获取子视图的布局文件
                    arg3 = mInflater.inflate(R.layout.item_end_couple_back_layout, arg4, false);


                    // 这个函数是用来将holderview设置标签,相当于缓存在view当中
                    arg3.setTag(childrenView);
                } else {
                    childrenView = (HolderView) arg3.getTag();
                }
            } else {
                // 定义一个二级列表的视图类
                if (arg3 == null) {
                    childrenView = new HolderView();
                    // 获取子视图的布局文件
                    arg3 = mInflater.inflate(R.layout.trial_two_layout, arg4, false);
                    childrenView.iv_nice = (RoundedImageView) arg3.findViewById(R.id.iv_nice);
                    childrenView.data1 = (TextView) arg3.findViewById(R.id.data1);
                    childrenView.data2 = (FlowGroupView) arg3.findViewById(R.id.data2);

                    // 这个函数是用来将holderview设置标签,相当于缓存在view当中
                    arg3.setTag(childrenView);
                } else {
                    childrenView = (HolderView) arg3.getTag();
                }

                /**
                 * 设置相应控件的内容
                 */
                if (data_list.get(arg0).getContentsList().size() != 0) {
                    // 设置副标题上的文本信息
                    childrenView.data1.setText(data_list.get(arg0).getContentsList().get(arg1).getTitle());

                    String ability = data_list.get(arg0).getContentsList().get(arg1).getAbility();
                    Glide.with(context).load(data_list.get(arg0).getContentsList().get(arg1).getPicUri()).into(childrenView.iv_nice);
                    String[] split = ability.split(",");
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 10, 0);
                    if (childrenView.data2 != null) {
                        childrenView.data2.removeAllViews();
                    }
                    for (int i = 0; i < split.length; i++) {
                        TextView textView = new TextView(context);
                        textView.setLayoutParams(layoutParams);
                        textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        textView.setText(split[i]);
                        textView.setTextColor(Color.parseColor("#00DEFF"));
                        childrenView.data2.addView(textView, layoutParams);
                    }
                }
            }
        }else {
            if (arg3 == null) {
                childrenView = new HolderView();
                // 获取子视图的布局文件
                arg3 = mInflater.inflate(R.layout.item_ke_seven_day_layout, arg4, false);
                // 这个函数是用来将holderview设置标签,相当于缓存在view当中
                arg3.setTag(childrenView);
            } else {
                childrenView = (HolderView) arg3.getTag();
            }
        }

        return arg3;
    }
    private int calculateDpToPx(int padding_in_dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }
    // 保存二级列表的视图类
    private class HolderView {
        RoundedImageView iv_nice;
        TextView data1;
        FlowGroupView data2;

    }

    private class HolderViewOne{
        TextView tv_word;

    }

    // 获取二级列表的数量
    @Override
    public int getChildrenCount(int arg0) {
        return data_list.get(arg0).getContentsList().size();
    }

    // 获取一级列表的数据
    @Override
    public Object getGroup(int arg0) {
        return data_list.get(arg0);
    }

    // 获取一级列表的个数
    @Override
    public int getGroupCount() {
        return data_list.size();
    }

    // 获取一级列表的ID
    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    // 设置一级列表的view
    @Override
    public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
        HodlerViewFather hodlerViewFather;
        if (arg2 == null) {
            hodlerViewFather = new HodlerViewFather();
            arg2 = mInflater.inflate(R.layout.trial_one_layout, arg3, false);
            hodlerViewFather.title = (TextView) arg2.findViewById(R.id.alarm_clock_father_tv);
            // 新建一个TextView对象，用来显示一级标签上的大体描述的信息
            hodlerViewFather.group_state = (ImageView) arg2.findViewById(R.id.group_state);
            hodlerViewFather.mView = arg2.findViewById(R.id.v);
            arg2.setTag(hodlerViewFather);
        } else {
            hodlerViewFather = (HodlerViewFather) arg2.getTag();
        }

        if (arg1) {
            hodlerViewFather.group_state.setImageResource(R.mipmap.group_up);
        } else {
            hodlerViewFather.group_state.setImageResource(R.mipmap.group_down);
        }
        if (arg0 == data_list.size() - 1) {
            hodlerViewFather.mView.setVisibility(View.GONE);
        }
        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        hodlerViewFather.title.setText(data_list.get(arg0).getTitle());

        // 返回一个布局对象
        return arg2;
    }

    // 定义一个 一级列表的view类
    private class HodlerViewFather {
        TextView title;
        ImageView group_state;
        View mView;
    }

    /**
     * 指定位置相应的组视图
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 当选择子节点的时候，调用该方法(点击二级列表)
     */
    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}
