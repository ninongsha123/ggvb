package com.tiancaijiazu.app.fragments.outermostlayer.expandable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;

import java.util.ArrayList;

public class ExpListViewAdapter extends BaseExpandableListAdapter {
    // 定义一个Context
    private Context context;
    // 定义一个LayoutInflater
    private LayoutInflater mInflater;
    // 定义一个List来保存列表数据
    private ArrayList<FatherData> data_list = new ArrayList<>();
    private HolderView childrenView;

    // 定义一个构造方法
    public ExpListViewAdapter(Context context, ArrayList<FatherData> datas) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.data_list = datas;
    }

    // 刷新数据
    public void flashData(ArrayList<FatherData> datas) {
        this.data_list = datas;
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
        // 定义一个二级列表的视图类
        if (arg3 == null) {
            childrenView = new HolderView();
            // 获取子视图的布局文件
            arg3 = mInflater.inflate(R.layout.activity_main_children_two, arg4, false);
            childrenView.title = (TextView) arg3.findViewById(R.id.title);
            childrenView.free = (TextView) arg3.findViewById(R.id.free);
            childrenView.lock = (ImageView) arg3.findViewById(R.id.lock);
            // 这个函数是用来将holderview设置标签,相当于缓存在view当中
            arg3.setTag(childrenView);
        } else {
            childrenView = (HolderView) arg3.getTag();
        }

        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        childrenView.title.setText(data_list.get(arg0).getContentsList().get(arg1).getTitle());
        // 设置副标题上的文本信息
        //判断课程是否免费

        if (data_list.get(arg0).getContentsList().get(arg1).getIsFree() == 1) {

            childrenView.free.setText("免费试听");

            childrenView.lock.setVisibility(View.GONE);

        } else {
            childrenView.free.setText("");
            childrenView.lock.setVisibility(View.VISIBLE);

        }

        //判断是音频还是视频
        //点击设置正在播放状态

        return arg3;
    }

    // 保存二级列表的视图类
    private class HolderView {
        TextView title;
        TextView free;
        ImageView lock;
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
            arg2 = mInflater.inflate(R.layout.item_father, arg3, false);
            hodlerViewFather.titlev = (TextView) arg2.findViewById(R.id.alarm_clock_father_tv);
            // 新建一个TextView对象，用来显示一级标签上的大体描述的信息
            hodlerViewFather.group_state = (ImageView) arg2.findViewById(R.id.group_state);
            arg2.setTag(hodlerViewFather);
        } else {
            hodlerViewFather = (HodlerViewFather) arg2.getTag();
        }

        if (arg1) {
            hodlerViewFather.group_state.setImageResource(R.mipmap.group_up);
        } else {
            hodlerViewFather.group_state.setImageResource(R.mipmap.group_down);
        }
        /**
         * 设置相应控件的内容
         */
        // 设置标题上的文本信息
        hodlerViewFather.titlev.setText(data_list.get(arg0).getTitle());

        // 返回一个布局对象
        return arg2;
    }

    // 定义一个 一级列表的view类
    private class HodlerViewFather {
        TextView titlev;
        ImageView group_state;
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
