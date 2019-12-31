package com.tiancaijiazu.app.activitys.parenting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.parenting.adapters.RlvAdapter_parenting_data;
import com.tiancaijiazu.app.activitys.parenting.pop_adapters.RlvAdapter_parenting_pop;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ParentingGuideActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.period)
    TextView mPeriod;
    @BindView(R.id.classify)
    TextView mClassify;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.huang_one)
    ImageView mHuangOne;
    @BindView(R.id.one)
    LinearLayout mOne;
    @BindView(R.id.huang_two)
    ImageView mHuangTwo;
    @BindView(R.id.two)
    LinearLayout mTwo;
    @BindView(R.id.line)
    LinearLayout mLine;

    private boolean isbo;
    private boolean isboo;
    private String mStr;
    private View mInflate;
    private PopupWindow mPopupWindow;
    private ArrayList<String> mList;
    private RlvAdapter_parenting_data mRlvAdapterParentingData;

    @Override
    protected void initEventAndData() {
        initSett();
        Intent intent = getIntent();
        mStr = intent.getStringExtra("str");
        mTitle.setText(mStr);
        mList = new ArrayList<>();
        mInflate = LayoutInflater.from(this).inflate(R.layout.divide_age_pop, null);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(false);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(null);
        //点击外部消失
        mPopupWindow.setOutsideTouchable(false);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                boolean showing = mPopupWindow.isShowing();
                if (showing == false) {
                    if(isbo){
                        mOne.setBackgroundResource(R.drawable.shape_parenting_one);
                        mHuangOne.setImageResource(R.mipmap.huang_jiao_top);
                        mPeriod.setTextColor(Color.parseColor("#FFCC41"));
                        isbo = false;
                    }
                    if(isboo){
                        mTwo.setBackgroundResource(R.drawable.shape_parenting_one);
                        mHuangTwo.setImageResource(R.mipmap.huang_jiao_top);
                        mClassify.setTextColor(Color.parseColor("#FFCC41"));
                        isboo = false;
                    }
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlvAdapterParentingData = new RlvAdapter_parenting_data(this);
        mRlv.setAdapter(mRlvAdapterParentingData);

        mRlvAdapterParentingData.setOnClickLisiter(new RlvAdapter_parenting_data.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                Intent intent1 = new Intent(ParentingGuideActivity.this,CanOrNotDetailsActivity.class);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_parenting_guide;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    //设置状态栏与状态栏字体颜色
    private void initSett() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    @OnClick({R.id.iv_finis, R.id.period, R.id.classify, R.id.one, R.id.two, R.id.huang_one, R.id.huang_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.one:
            case R.id.period:
            case R.id.huang_one:
                if (isbo) {
                    mOne.setBackgroundResource(R.drawable.shape_parenting_one);
                    mHuangOne.setImageResource(R.mipmap.huang_jiao_top);
                    mPeriod.setTextColor(Color.parseColor("#FFCC41"));
                    mPopupWindow.dismiss();
                    isbo = false;
                } else {
                    mOne.setBackgroundResource(R.drawable.shape_parenting_two);
                    mHuangOne.setImageResource(R.mipmap.huang_jiao_butom);
                    mPeriod.setTextColor(Color.parseColor("#FFFFFF"));
                    if(isboo){
                        mTwo.setBackgroundResource(R.drawable.shape_parenting_one);
                        mHuangTwo.setImageResource(R.mipmap.huang_jiao_top);
                        mClassify.setTextColor(Color.parseColor("#FFCC41"));
                        isboo = false;
                    }
                        mList.clear();
                        mList.add("全部时期");
                        mList.add("孕期");
                        mList.add("坐月子");
                        mList.add("哺乳期");
                        mList.add("婴儿");
                        initPopAge(mList);

                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                    isbo = true;
                }
                break;
            case R.id.two:
            case R.id.classify:
            case R.id.huang_two:
                if (isboo) {
                    mTwo.setBackgroundResource(R.drawable.shape_parenting_one);
                    mHuangTwo.setImageResource(R.mipmap.huang_jiao_top);
                    mClassify.setTextColor(Color.parseColor("#FFCC41"));
                    mPopupWindow.dismiss();
                    isboo = false;
                } else {
                    mTwo.setBackgroundResource(R.drawable.shape_parenting_two);
                    mHuangTwo.setImageResource(R.mipmap.huang_jiao_butom);
                    mClassify.setTextColor(Color.parseColor("#FFFFFF"));
                    if(isbo){
                        mOne.setBackgroundResource(R.drawable.shape_parenting_one);
                        mHuangOne.setImageResource(R.mipmap.huang_jiao_top);
                        mPeriod.setTextColor(Color.parseColor("#FFCC41"));
                        isbo = false;
                    }
                    if ("能不能吃".equals(mStr)) {
                        mList.clear();
                        mList.add("全部分类");
                        mList.add("主食");
                        mList.add("菜肴/汤");
                        mList.add("蔬菜菌类");
                        mList.add("肉/蛋类");
                        mList.add("水果");
                        mList.add("水产品");
                        mList.add("调味品");
                        mList.add("饮品甜品");
                        mList.add("零食小吃");
                        mList.add("豆/奶制品");
                        mList.add("坚果类");
                        mList.add("补品&草药");
                        initPopAge(mList);
                    }else {
                        mList.clear();
                        mList.add("全部分类");
                        mList.add("着装");
                        mList.add("口腔护理");
                        mList.add("居家生活");
                        mList.add("面部护理");
                        mList.add("家务");
                        mList.add("身体护理");
                        mList.add("防辐射");
                        mList.add("出行");
                        mList.add("出行");
                        initPopAge(mList);
                    }

                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                    isboo = true;
                }
                break;
        }
    }

    private void initPopAge(ArrayList<String> list) {

        RecyclerView rlvAge = mInflate.findViewById(R.id.rlv_age);
        TextView tv = mInflate.findViewById(R.id.tv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rlvAge.setLayoutManager(gridLayoutManager);
        String period = mPeriod.getText().toString();
        String classify = mClassify.getText().toString();
        RlvAdapter_parenting_pop rlvAdapterParentingPop = new RlvAdapter_parenting_pop(list,period,classify);
        rlvAge.setAdapter(rlvAdapterParentingPop);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        rlvAdapterParentingPop.setOnClickLisiter(new RlvAdapter_parenting_pop.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                    if(isbo){
                        mPeriod.setText(mData.get(position));
                        mPopupWindow.dismiss();
                    }
                    if(isboo){
                        mClassify.setText(mData.get(position));
                        mPopupWindow.dismiss();
                    }
                    if(!"全部时期".equals(mPeriod.getText().toString())&&!"全部分类".equals(mClassify.getText().toString())){
                        mRlvAdapterParentingData.addIsbo(true);
                    }else {
                        mRlvAdapterParentingData.addIsbo(false);
                    }
            }
        });
    }

}
