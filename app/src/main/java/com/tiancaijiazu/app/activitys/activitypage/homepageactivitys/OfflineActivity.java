package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.DistanceItem;
import com.amap.api.services.route.DistanceResult;
import com.amap.api.services.route.DistanceSearch;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_offline;
import com.tiancaijiazu.app.activitys.apply_for.ApplyForActivity;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.ClassGardenBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.http.JmupPerssionMagent;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 *
 * 首页-线下课堂
 */
public class OfflineActivity extends BaseActivity<IView, Presenter<IView>> implements IView, DistanceSearch.OnDistanceSearchListener {


    private static final String TAG = "OfflineActivity";
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.apply_for_joining)
    ImageView mApplyForJoining;
    @BindView(R.id.a)
    TextView a;
    private int page = 1;
    private RlvAdapter_offline mRlvAdapterOffline;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int PERMISSON_REQUESTCODE = 0;
    private View mInflates;
    private PopupWindow mMPopupWindow;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE
    };
    /**
     * 判断是否需要检测，防止不停的弹框
     */
    private boolean isNeedCheck = true;
    private boolean isShow = true;
    private double mLatitude;
    private double mLongitude;
    private List<ClassGardenBean.ResultBean> mResult;
    private String mAddress;
    private String mCity;

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        //for (循环变量类型 循环变量名称 : 要被遍历的对象)
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (AndPermission.hasAlwaysDeniedPermission(this, Permission.Group.LOCATION)){
//            // 打开权限设置页
//            //
//            LocationSetting();
//
//            return;
//        }
//    }

    /**
     * 检查权限
     *
     * @param
     * @since 2.5.0
     */
    private void checkPermissions(String... permissions) {
        //获取权限列表
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            //list.toarray将集合转化为数组
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }


    /**
     * 检测是否说有的权限都已经授权
     *
     * @param grantResults
     * @return
     * @since 2.5.0
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 处理请求权限的响应，当用户对请求权限的dialog做出响应之后,系统会回调该函数(Activity或者Fragment中重写)
     * @param requestCode 申请权限对应的requestCode
     * @param permissions 权限列表
     * @param paramArrayOfInt 权限列表对应的返回值，判断permissions里面的每个权限是否申请成功
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                isNeedCheck = false;
                LocationSetting();
                Log.i("wwww",2+"");
            }else {
                startLocaion();
                Log.i("sss",1+"");
            }
            isShow=false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
            startLocaion();
        }else {
            startLocaion();
        }
    }

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initRecylerView();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }
        startLocaion();
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<ClassGardenBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterOffline = new RlvAdapter_offline(resultBeans, this);
        mRecylerView.setAdapter(mRlvAdapterOffline);
        mRlvAdapterOffline.setOnClick(new RlvAdapter_offline.onClick() {
            @Override
            public void onclick(View view, int position, ArrayList<ClassGardenBean.ResultBean> mData) {
                long companyId = mData.get(position).getCompanyId();
                String latitude = mData.get(position).getLatitude();
                String longitude = mData.get(position).getLongitude();
                Intent in = new Intent(OfflineActivity.this, OurgardenActivity.class);
                in.putExtra("companyId", companyId + "");
                in.putExtra("juli", mData.get(position).getJuli());
                in.putExtra("endLatitude", latitude);
                in.putExtra("endLongitude", longitude);
                in.putExtra("startLatitude", mLatitude);
                in.putExtra("startLongitude", mLongitude);
                in.putExtra("city", mCity);
                in.putExtra("address", mAddress);
                startActivity(in);
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_offline;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case CLASSGARDENLIST:
                ClassGardenBean classGardenBean = (ClassGardenBean) o;
                mResult = classGardenBean.getResult();
                //mRlvAdapterOffline.addData(mResult);
                DistanceSearch distanceSearch = new DistanceSearch(this);
                distanceSearch.setDistanceSearchListener(this);
                DistanceSearch.DistanceQuery distanceQuery = new DistanceSearch.DistanceQuery();
                LatLonPoint dest = new LatLonPoint(mLatitude, mLongitude);
                List<LatLonPoint> latLonPoints = new ArrayList<LatLonPoint>();
                for (int i = 0; i < mResult.size(); i++) {
                    LatLonPoint start = new LatLonPoint(Double.parseDouble(mResult.get(i).getLatitude()), Double.parseDouble(mResult.get(i).getLongitude()));
                    //设置起点和终点，其中起点支持多个
                    latLonPoints.add(start);
                }
                distanceQuery.setOrigins(latLonPoints);
                distanceQuery.setDestination(dest);
                //设置测量方式，支持直线和驾车
                distanceQuery.setType(DistanceSearch.TYPE_DRIVING_DISTANCE);
                distanceSearch.calculateRouteDistanceAsyn(distanceQuery);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.apply_for_joining})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.apply_for_joining:
                Intent intent = new Intent(this, ApplyForActivity.class);
                startActivity(intent);
                break;
        }
    }

    //提示用户权限设置
    private void LocationSetting() {
        mInflates = LayoutInflater.from(this).inflate(R.layout.pop_setting_location, null);
        mMPopupWindow = new PopupWindow(mInflates, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mMPopupWindow.setFocusable(true);// 取得焦点
        mMPopupWindow.setClippingEnabled(false);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mMPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mMPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mMPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mMPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        mInflates.findViewById(R.id.location_kowent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // checkPermissions(needPermissions);
                mMPopupWindow.dismiss();
                JmupPerssionMagent.GoToSetting(OfflineActivity.this);
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                mMPopupWindow.showAtLocation(mInflates, Gravity.BOTTOM, 0, 0);
//            }
//        },1000);
    }


    @Override
    public void onDistanceSearched(DistanceResult distanceResult, int i) {
        Log.i("yx111", "onDistanceSearched: " + i);
        if (i == 1000) {
            List<DistanceItem> distanceResults = distanceResult.getDistanceResults();
            int size = distanceResults.size();
            for (int j = 0; j < size; j++) {
                float distance = distanceResults.get(j).getDistance();
                String s = distanceFormat(distance);
                Log.i(TAG, "onDistanceSearched: " + s);
                mResult.get(j).setJuli(s);
                mResult.get(j).setOrder(distance);
            }
            Collections.sort(mResult, new Comparator<ClassGardenBean.ResultBean>() {
                @Override
                public int compare(ClassGardenBean.ResultBean resultBean, ClassGardenBean.ResultBean t1) {
                    BigDecimal data1 = new BigDecimal(resultBean.getOrder());
                    BigDecimal data2 = new BigDecimal(t1.getOrder());
                    return data1.compareTo(data2);
                }
            });
            mRlvAdapterOffline.addData(mResult);
        }

    }

    /**
     * 距离只保留两位小数
     *
     * @param distance 以米为单位
     * @return
     */
    public static String distanceFormat(double distance) {
        String str;
        double value = distance;
        if (distance >= 1000) {
            value = value / 1000;
            str = "km";
        } else {
            str = "m";
        }
        return String.format("%s%s", (int) value, str);
    }

    public void startLocaion() {
        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    mLatitude = amapLocation.getLatitude();
                    mLongitude = amapLocation.getLongitude();
                    mAddress = amapLocation.getAddress();
                    mCity = amapLocation.getCity();
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                    mPresenter.getDataP(page, DifferentiateEnum.CLASSGARDENLIST);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }



}
