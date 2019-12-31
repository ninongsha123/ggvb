package com.tiancaijiazu.app.activitys.activitypage;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

public class TanchuActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    private String result;

    @Override
    protected void initEventAndData() {
        initSett();
        fistPopuwindow();
        ScreenStatusUtil.setFillDip(this);
        //mPresenter.getDataP(null, DifferentiateEnum.GETCOUPONONLYATHOME);

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_tanchu;
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            /*case GETCOUPONONLYATHOME:
                AthomeBean athomeBean = (AthomeBean) o;
                result = athomeBean.getResult();

                break;*/
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    //弹出广告界面
    private void fistPopuwindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View view = View.inflate(TanchuActivity.this, R.layout.first_popuwindow, null);
                            ImageView cancle = view.findViewById(R.id.cancle);
                            ImageView lingqu = view.findViewById(R.id.lingqu);
                            final PopupWindow mPopu = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            mPopu.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                            cancle.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPopu.dismiss();
                                }
                            });
                            lingqu.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(TanchuActivity.this, result, Toast.LENGTH_SHORT).show();
                                    mPopu.dismiss();
                                }
                            });
                            mPopu.setOutsideTouchable(false);//判断在外面点击是否有效
                            mPopu.setFocusable(true);
                            mPopu.showAsDropDown(view);
                            mPopu.isShowing();
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                    //| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
