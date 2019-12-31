package com.tiancaijiazu.app.activitys.consisting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.consisting.adapters.RlvAdapter_hor_title;
import com.tiancaijiazu.app.activitys.consisting.pop_adapters.RlvAdapter_age_pop;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsistingActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.divide_age_select)
    CheckBox mDivideAgeSelect;
    @BindView(R.id.divide_nutrition_select)
    CheckBox mDivideNutritionSelect;
    @BindView(R.id.divide_get_ill_select)
    CheckBox mDivideGetIllSelect;
    @BindView(R.id.divide_fruits_select)
    CheckBox mDivideFruitsSelect;
    @BindView(R.id.divide_assisted_food_select)
    CheckBox mDivideAssistedFoodSelect;
    @BindView(R.id.recylerView1)
    RecyclerView mRecylerView1;
    @BindView(R.id.recylerView2)
    RecyclerView mRecylerView2;
    @BindView(R.id.line)
    LinearLayout mLine;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.tv_delete)
    TextView mTvDelete; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.hor_line)
    LinearLayout mHorLine;
    private ArrayList<String> mList;
    private PopupWindow mPopupWindow;
    private RlvAdapter_age_pop mRlvAdapterAgePop;
    private RlvAdapter_hor_title mRlvAdapter_hor_title;
    private View mInflate;

    @Override
    protected void initEventAndData() {
        initSett();
        Intent intent = getIntent();
        String str = intent.getStringExtra("str");
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
                    mDivideAgeSelect.setChecked(false);
                    mDivideNutritionSelect.setChecked(false);
                    mDivideGetIllSelect.setChecked(false);
                    mDivideFruitsSelect.setChecked(false);
                    mDivideAssistedFoodSelect.setChecked(false);
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecylerView1.setLayoutManager(linearLayoutManager);
        ArrayList<String> list = new ArrayList<>();
        list.add(str);
        mRlvAdapter_hor_title = new RlvAdapter_hor_title(list);
        mRecylerView1.setAdapter(mRlvAdapter_hor_title);

        mRlvAdapter_hor_title.setOnClickLisiter(new RlvAdapter_hor_title.onClickLisiter() {
            @Override
            public void onClicker(View view, int position,ArrayList<String> mData) {
                if(mData.size()!=0){
                    mHorLine.setVisibility(View.VISIBLE);
                }else {
                    mHorLine.setVisibility(View.GONE);
                }
            }
        });

        if(mRlvAdapter_hor_title.mData.size()!=0){
            mHorLine.setVisibility(View.VISIBLE);
        }else {
            mHorLine.setVisibility(View.GONE);
        }

        /*LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        mRecylerView2.setLayoutManager(linearLayoutManager1);
        RlvAdapter_consis_list rlvAdapterConsisList = new RlvAdapter_consis_list();
        mRecylerView2.setAdapter(rlvAdapterConsisList);*/
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

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_consisting;
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


    @OnClick({R.id.divide_age_select, R.id.divide_nutrition_select, R.id.divide_get_ill_select, R.id.divide_fruits_select, R.id.divide_assisted_food_select, R.id.iv_finis, R.id.tv_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.divide_age_select:
                mDivideNutritionSelect.setChecked(false);
                mDivideGetIllSelect.setChecked(false);
                mDivideFruitsSelect.setChecked(false);
                mDivideAssistedFoodSelect.setChecked(false);
                boolean checked = mDivideAgeSelect.isChecked();
                Log.i("yx123", "onViewClicked: " + checked);
                if (checked) {
                    mList.clear();
                    mList.add("6月龄");
                    mList.add("7月龄");
                    mList.add("8月龄");
                    mList.add("9月龄");
                    mList.add("10月龄");
                    mList.add("11月龄");
                    mList.add("12-15月龄");
                    mList.add("15-18月龄");
                    mList.add("15-18月龄");
                    initPopAge(mList);
                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                } else {
                    mPopupWindow.dismiss();
                }

                break;
            case R.id.divide_nutrition_select:
                mDivideAgeSelect.setChecked(false);
                mDivideGetIllSelect.setChecked(false);
                mDivideFruitsSelect.setChecked(false);
                mDivideAssistedFoodSelect.setChecked(false);
                mList.clear();
                mList.add("补铁");
                mList.add("补钙");
                mList.add("补锌");
                mList.add("补碘");
                mList.add("蛋白质");
                mList.add("维生素");
                mList.add("DHA");

                initPopAge(mList);
                boolean checked1 = mDivideNutritionSelect.isChecked();
                if (checked1) {

                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                } else {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.divide_get_ill_select:
                mDivideAgeSelect.setChecked(false);
                mDivideNutritionSelect.setChecked(false);
                mDivideFruitsSelect.setChecked(false);
                mDivideAssistedFoodSelect.setChecked(false);
                mList.clear();
                mList.add("发烧");
                mList.add("咳嗽");
                mList.add("感冒");
                mList.add("腹泻");
                mList.add("便秘");
                mList.add("脾胃虚");

                initPopAge(mList);
                boolean checked2 = mDivideGetIllSelect.isChecked();
                if (checked2) {

                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                } else {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.divide_fruits_select:
                mDivideAgeSelect.setChecked(false);
                mDivideNutritionSelect.setChecked(false);
                mDivideGetIllSelect.setChecked(false);
                mDivideAssistedFoodSelect.setChecked(false);
                mList.clear();
                mList.add("温性");
                mList.add("热性");
                mList.add("寒性");
                mList.add("凉性");
                mList.add("平性");

                initPopAge(mList);
                boolean checked3 = mDivideFruitsSelect.isChecked();
                if (checked3) {

                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                } else {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.divide_assisted_food_select:
                mDivideAgeSelect.setChecked(false);
                mDivideNutritionSelect.setChecked(false);
                mDivideGetIllSelect.setChecked(false);
                mDivideFruitsSelect.setChecked(false);
                mList.clear();
                mList.add("果蔬泥");
                mList.add("肉泥");
                mList.add("蛋黄泥");
                mList.add("面");
                mList.add("饭");
                mList.add("粥");
                mList.add("蒸");
                mList.add("烘焙");
                mList.add("煮");
                mList.add("煎");
                mList.add("零食");
                mList.add("果汁");
                initPopAge(mList);
                boolean checked4 = mDivideAssistedFoodSelect.isChecked();
                if (checked4) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        int[] point = new int[2];
                        mLine.getLocationInWindow(point);
                        mPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.NO_GRAVITY, 0, point[1] + mLine.getHeight());
                    } else {
                        mPopupWindow.showAsDropDown(mLine);
                    }
                } else {
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.tv_delete:
                mRlvAdapter_hor_title.mData.clear();
                mRlvAdapter_hor_title.notifyDataSetChanged();
                mHorLine.setVisibility(View.GONE);
                break;
        }
    }

    private void initPopAge(ArrayList<String> list) {

        RecyclerView rlvAge = mInflate.findViewById(R.id.rlv_age);
        TextView tv = mInflate.findViewById(R.id.tv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rlvAge.setLayoutManager(gridLayoutManager);
        mRlvAdapterAgePop = new RlvAdapter_age_pop(list, mRlvAdapter_hor_title.mData);
        rlvAge.setAdapter(mRlvAdapterAgePop);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        mRlvAdapterAgePop.setOnClickLisiter(new RlvAdapter_age_pop.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<String> mData) {
                ArrayList<String> data = mRlvAdapter_hor_title.mData;
                for (int i = 0; i < data.size(); i++) {
                    for (int j = 0; j < mData.size(); j++) {
                        if (data.get(i).equals(mData.get(j))) {
                            data.remove(i);
                            break;
                        }
                    }
                }
                mRlvAdapter_hor_title.addData(mData.get(position));
                mPopupWindow.dismiss();
                mHorLine.setVisibility(View.VISIBLE);
                //Toast.makeText(mActivity, "" + mData.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
