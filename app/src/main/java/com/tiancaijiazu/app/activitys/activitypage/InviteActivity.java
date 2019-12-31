package com.tiancaijiazu.app.activitys.activitypage;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.PosterVpAdapter;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.QrcodeBeans;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DonwloadSaveImg;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class InviteActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    TextView mA;
    @BindView(R.id.top)
    RelativeLayout mTop;
    @BindView(R.id.invite_wact)
    ImageView mInviteWact;
    @BindView(R.id.invite_friender)
    ImageView mInviteFriender;
    @BindView(R.id.invite_down)
    ImageView mInviteDown;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.page_number)
    TextView mPageNumber;
    @BindView(R.id.pagination_back)
    TextView mPaginationBack;
    private String imageUrl;
    private String mImageUrl;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    Bitmap bitmap = (Bitmap) msg.obj;
                    WechatShareTool.shareImage(bitmap, true);
                    break;
                case 2:
                    Bitmap bitmap1 = (Bitmap) msg.obj;
                    WechatShareTool.shareImage(bitmap1, false);
                    break;
            }

        }
    };

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initView();
        mPresenter.getDataP(2, DifferentiateEnum.QRCODE);

    }


    private void initView() {

    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_invite;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case QRCODE:
                QrcodeBeans qrcodeBeans = (QrcodeBeans) o;
                mImageUrl = qrcodeBeans.getResult();
                //Glide.with(this).load(imageUrl).into(mImageUrl);

                String[] split = mImageUrl.split("[|]");
                List<String> strings = Arrays.asList(split);
                imageUrl = strings.get(0);
                mPageNumber.setText("1");
                mPaginationBack.setText("/"+strings.size());
                PosterVpAdapter posterVpAdapter = new PosterVpAdapter(strings, this);
                mViewPager.setAdapter(posterVpAdapter);
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }
                    @Override
                    public void onPageSelected(int i) {
                        Log.i("yxPoster", "onPageSelected: " + i);
                        mPageNumber.setText(""+(i+1));
                        imageUrl = strings.get(i);
                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {
                    }
                });
                break;
        }
    }

    @Override
    public void showError(String error) {
    }
    @OnClick({R.id.iv_finis, R.id.invite_wact, R.id.invite_friender, R.id.invite_down})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.invite_wact:
                if (imageUrl != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1 = 2;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                break;
            case R.id.invite_friender:
                if (imageUrl != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1 = 1;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }
                break;
            case R.id.invite_down:
                DonwloadSaveImg.donwloadImg(InviteActivity.this, imageUrl);//iPath
                break;
        }
    }

}
