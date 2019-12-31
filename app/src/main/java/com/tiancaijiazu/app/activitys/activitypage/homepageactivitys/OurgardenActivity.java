package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.CoordinateConverter;
import com.amap.api.location.DPoint;
import com.bumptech.glide.Glide;
import com.cocosw.bottomsheet.BottomSheet;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_information;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_ourteachers;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_shizijieshao;
import com.tiancaijiazu.app.activitys.brand.BrandIntroductionActivity;
import com.tiancaijiazu.app.activitys.offline.OfflineBookingActivity;
import com.tiancaijiazu.app.app.App;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.GardenDetailsByBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.FastBlurUtil;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.OpenLocalMapUtil;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.linwg.org.lib.LCardView;


/**
 *
 * 首页-线下课堂-园所列表详情页面
 */
public class OurgardenActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.recler1)
    RecyclerView rl;
    @BindView(R.id.recler2)
    RecyclerView rl2;
    @BindView(R.id.dianhua)
    ImageView dianhua;
    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.card)
    LCardView card;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    @BindView(R.id.lcard)
    LCardView mLcard;
    @BindView(R.id.nested)
    NestedScrollView mNested;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.checkbox)
    CheckBox mCheckbox;
    @BindView(R.id.share)
    ImageView mShare;
    @BindView(R.id.lcard1)
    LCardView mLcard1;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.rounded)
    RoundedImageView mRounded;
    @BindView(R.id.zuobiao)
    ImageView mZuobiao;
    @BindView(R.id.rl1)
    RelativeLayout mRl1;
    @BindView(R.id.juli)
    TextView mJuli;
    @BindView(R.id.rl2)
    RelativeLayout mRl2;
    @BindView(R.id.go)
    ImageView mGo;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.shizi)
    TextView shizi;
    @BindView(R.id.tuijian)
    TextView tuijian;
    @BindView(R.id.scale)
    TextView mScale;
    @BindView(R.id.yuansuo)
    TextView yuansuo;
    @BindView(R.id.pinpai)
    TextView pinpai;
    @BindView(R.id.site)
    TextView mSite;
    @BindView(R.id.businessHours)
    TextView mBusinessHours;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.summary)
    TextView mSummary;
    @BindView(R.id.jian)
    ImageView mJian;
    @BindView(R.id.title_name)
    MediumBoldTextViewTitle mTitleName;
    private int overallXScroll = 0;
    private PopupWindow mPopupWindow;
    private View inflate;
    private float height = 540;// 滑动开始变色的高,此高度是由广告轮播或其他首页view高度决定
    private RlvAdapter_information mRlvAdapterInformation;
    private RlvAdapter_ourteachers mAdapter;
    private RlvAdapter_shizijieshao mAdapter_shizijieshao;
    private String mPhone;
    private TextView mText;
    private GardenDetailsByBean.ResultBean mResult;
    /**
     * 当前位置
     */
    private static double[] START_LATLON;
    /**
     * 目的地
     */
    private static double[] DESTINATION_TA_LATLON;

    private String SNAME = "";

    private String DNAME = "";
    private String CITY = "";

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        String companyId = intent.getStringExtra("companyId");
        String endLatitude = intent.getStringExtra("endLatitude");
        double endlatitude = Double.parseDouble(endLatitude);
        String endLongitude = intent.getStringExtra("endLongitude");
        double endlongitude = Double.parseDouble(endLongitude);
        double startLatitude = intent.getDoubleExtra("startLatitude", 0);
        double startLongitude = intent.getDoubleExtra("startLongitude", 0);
        String city = intent.getStringExtra("city");
        String address = intent.getStringExtra("address");
        START_LATLON = new double[]{startLatitude, startLongitude};
        DESTINATION_TA_LATLON = new double[]{endlatitude, endlongitude};
        SNAME = address;
        CITY = city;
        initData();
        showPop();
        String juli = intent.getStringExtra("juli");
        mJuli.setText("距您" + juli);
        mPresenter.getDataP1(companyId, DifferentiateEnum.GARDENDETAILSBY, loadingLayout);
        loadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1(companyId, DifferentiateEnum.GARDENDETAILSBY, loadingLayout);
            }
        });
        //checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION);
    }

    @Override
    protected void onStart() {
        String hui = PreUtils.getString("hui", "");
        if ("ok".equals(hui)) {
            mNested.fullScroll(NestedScrollView.FOCUS_UP);

            PreUtils.putString("hui", "no");
        }
        super.onStart();
    }

    @SuppressLint("NewApi")
    private void initData() {
        List<GardenDetailsByBean.ResultBean.TeacherListBean> teacherListBeans = new ArrayList<>();
        mAdapter = new RlvAdapter_ourteachers(this, teacherListBeans);
        LinearLayoutManager manager = new LinearLayoutManager(OurgardenActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl.setLayoutManager(manager);
        rl.setAdapter(mAdapter);
        ArrayList<GardenDetailsByBean.ResultBean.CourseListBean> courseListBeans = new ArrayList<>();
        mAdapter_shizijieshao = new RlvAdapter_shizijieshao(this, courseListBeans);
        LinearLayoutManager manager1 = new LinearLayoutManager(OurgardenActivity.this);
        manager1.setOrientation(LinearLayoutManager.VERTICAL);
        rl2.setLayoutManager(manager1);
        rl2.setAdapter(mAdapter_shizijieshao);

        mAdapter_shizijieshao.setOnClickLisiter(new RlvAdapter_shizijieshao.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<GardenDetailsByBean.ResultBean.CourseListBean> mData) {
                Intent intent = new Intent(OurgardenActivity.this, OfflineBookingActivity.class);
                intent.putExtra("data", mData);
                intent.putExtra("yuan", mResult);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        initRecylerView();
        mNested.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int scrollY, int i2, int oldScrollY) {
                overallXScroll = overallXScroll + (scrollY - oldScrollY);// 累加y值 解决滑动一半y值为0
                if (overallXScroll <= 0) {
                    //未滑动时，设置透明度为0
                    mRelative.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
                    mTitleName.setAlpha(0);
                    mShare.setImageResource(R.mipmap.share_white);
                    mCheckbox.setBackgroundResource(R.drawable.select_collect_our);
                    ivFinis.setImageResource(R.mipmap.back_white);
                } else if (overallXScroll > 0 && overallXScroll <= height) { //确定一个渐变区域，背景颜色透明度渐变
                    //设置渐变比例
                    float scale = (float) overallXScroll / height;
                    float alpha = (255 * scale);
                    mRelative.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    mTitleName.setAlpha(alpha);
                } else {
                    //超过渐变区域，透明度都是满的
                    mRelative.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
                    mTitleName.setAlpha(1);
                    mShare.setImageResource(R.mipmap.share);
                    mCheckbox.setBackgroundResource(R.drawable.select_collect_top);
                    ivFinis.setImageResource(R.mipmap.rec_back);
                }
            }

        });

    }

    private void initRecylerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecylerView.setLayoutManager(gridLayoutManager);
        ArrayList<String> strings = new ArrayList<>();
        mRlvAdapterInformation = new RlvAdapter_information(strings);
        mRecylerView.setAdapter(mRlvAdapterInformation);
    }


    @Override
    public void showError(String error) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    private void showPop() {
        inflate = LayoutInflater.from(this).inflate(R.layout.playnumber, null);
        mPopupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        mPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        TextView cancle = inflate.findViewById(R.id.cancle);
        TextView play = inflate.findViewById(R.id.play);
        mText = inflate.findViewById(R.id.text);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call("tel:" + mPhone);
                mPopupWindow.dismiss();
            }
        });
    }

    /**
     * 判断是否有某项权限
     *
     * @param string_permission 权限
     * @param request_code      请求码
     * @return
     */
    public boolean checkReadPermission(String string_permission, int request_code) {
        boolean flag = false;
        if (ContextCompat.checkSelfPermission(this, string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(this, new String[]{string_permission}, request_code);
        }
        return flag;
    }

    /**
     * 检查权限后的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: //拨打电话
                if (permissions.length != 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {//失败
                    ToastUtils.showShortToast(this, "请允许拨号权限后再试");
                } else {//成功
                    call("tel:" + mPhone);
                }
                break;
        }
    }

    /**
     * 拨打电话（直接拨打）
     *
     * @param telPhone 电话
     */
    public void call(String telPhone) {
        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(telPhone));
            startActivity(intent);
        }
    }

    //设置背景透明度
    private void setBack() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = (float) 0.3; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @OnClick({R.id.dianhua, R.id.iv_finis, R.id.lcard, R.id.rl1, R.id.juli, R.id.jian})
    public void onViewClicked(View view) {
        if (TimeUtil.isInvalidClick(view, 300))
            return;
        switch (view.getId()) {
            case R.id.dianhua:
                mPopupWindow.showAtLocation(card, Gravity.CENTER, 0, 0);
                setBack();
                break;
            case R.id.iv_finis:
                finish();
                break;
            case R.id.lcard:
                Intent intent = new Intent(this, BrandIntroductionActivity.class);
                intent.putExtra("data", mResult);
                startActivity(intent);
                break;
            case R.id.rl1:
            case R.id.juli:
            case R.id.jian:
                openLocalMap(START_LATLON[0], START_LATLON[1], SNAME, CITY);
                break;
        }
    }

    /**
     * @param slat
     * @param slon
     * @param address 当前位置
     * @param city    所在城市
     */
    private void openLocalMap(double slat, double slon, String address, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled() && OpenLocalMapUtil.isGdMapInstalled()) {
            chooseOpenMap(slat, slon, address, city);
        } else {
            openBaiduMap(slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);

            if (!isOpened) {
                openGaoDeMap(slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME);
            }

            /*if(!isOpened){
                //打开网页地图
                openWebMap(slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);
            }*/
        }

    }

    /**
     * 如果两个地图都安装，提示选择
     *
     * @param slat
     * @param slon
     * @param address
     * @param city
     */
    private void chooseOpenMap(final double slat, final double slon, final String address, final String city) {
        BottomSheet.Builder builder = new BottomSheet
                .Builder(this, com.cocosw.bottomsheet.R.style.BottomSheet_Dialog)
                .title("请选择");
        builder.sheet(0, "百度地图").sheet(1, "高德地图")
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openBaiduMap(slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME, city);
                        } else if (which == 1) {
                            openGaoDeMap(slat, slon, address, DESTINATION_TA_LATLON[0], DESTINATION_TA_LATLON[1], DNAME);
                        }
                    }
                }).build().show();
    }

    private static String SRC = "thirdapp.navi.beiing.openlocalmapdemo";

    /**
     * 打开百度地图
     */
    private void openBaiduMap(double slat, double slon, String sname, double dlat, double dlon, String dname, String city) {
        if (OpenLocalMapUtil.isBaiduMapInstalled()) {
            try {
                String uri = OpenLocalMapUtil.getBaiduMapUri(String.valueOf(slat), String.valueOf(slon), sname,
                        String.valueOf(dlat), String.valueOf(dlon), dname, city, SRC);
                Intent intent = Intent.parseUri(uri, 0);
                startActivity(intent); //启动调用

                isOpened = true;
            } catch (Exception e) {
                isOpened = false;
                e.printStackTrace();
            }
        } else {
            isOpened = false;
        }
    }

    private static String APP_NAME = "OPenLocalMapDemo";

    private boolean isOpened;

    @Override
    protected void onResume() {
        super.onResume();
        isOpened = false;
    }

    /**
     * 打开高德地图
     */
    private void openGaoDeMap(double slat, double slon, String sname, double dlat, double dlon, String dname) {
        if (OpenLocalMapUtil.isGdMapInstalled()) {
            try {
                CoordinateConverter converter = new CoordinateConverter(this);
                converter.from(CoordinateConverter.CoordType.BAIDU);
                DPoint sPoint = null, dPoint = null;
                try {
                    converter.coord(new DPoint(slat, slon));
                    sPoint = converter.convert();
                    converter.coord(new DPoint(dlat, dlon));
                    dPoint = converter.convert();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (sPoint != null && dPoint != null) {
                    String uri = OpenLocalMapUtil.getGdMapUri(APP_NAME, String.valueOf(sPoint.getLatitude()), String.valueOf(sPoint.getLongitude()),
                            sname, String.valueOf(dPoint.getLatitude()), String.valueOf(dPoint.getLongitude()), dname);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.autonavi.minimap");
                    intent.setData(Uri.parse(uri));
                    startActivity(intent); //启动调用

                    isOpened = true;
                }
            } catch (Exception e) {
                isOpened = false;
                e.printStackTrace();
            }
        } else {
            isOpened = false;
        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_ourgarden;
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case GARDENDETAILSBY:
                GardenDetailsByBean gardenDetailsByBean = (GardenDetailsByBean) o;
                mResult = gardenDetailsByBean.getResult();
                Glide.with(this).load(mResult.getBanner()).into(mRounded);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //下面的这个方法必须在子线程中执行
                        final Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(mResult.getBanner(), 8);
                        //刷新ui必须在主线程中执行
                        App.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                mIvBack.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                mIvBack.setImageBitmap(blurBitmap2);
                            }
                        });
                    }
                }).start();
                mTitle.setText(mResult.getCompanyName());
                mTitleName.setText(mResult.getCompanyName());
                mScale.setText(mResult.getScale());
                mSite.setText(mResult.getAddress());
                DNAME = mResult.getAddress();
                mBusinessHours.setText("营业时间：" + mResult.getBusinessHours());
                mPhone = mResult.getPhone();
                mText.setText(mPhone);
                String services = mResult.getServices();
                String[] split = services.split(",");
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    list.add(split[i]);
                }
                mRlvAdapterInformation.addData(list);
                mSummary.setText(mResult.getSummary());
                List<GardenDetailsByBean.ResultBean.TeacherListBean> teacherList = mResult.getTeacherList();
                mAdapter.addData(teacherList);

                List<GardenDetailsByBean.ResultBean.CourseListBean> courseList = mResult.getCourseList();
                mAdapter_shizijieshao.addData(courseList);
                break;
        }
    }

}
