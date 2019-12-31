package com.tiancaijiazu.app.activitys.team;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_team;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.TeamBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.http.JmupPerssionMagent;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.utils.status.LoadingLayout;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.loadingLayout)
    LoadingLayout mLoadingLayout;
    private RlvAdapter_team mRlvAdapter_team;
    private int page = 1;

    private View mInflates;
    private PopupWindow mMPopupWindow;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:         //刷新加载
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishRefresh(true);
                        page = 1;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("vipLevel", mVipLevel);
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.TEAM, mLoadingLayout);
                    }

                    break;
                case 2:         //加载更多
                    if (mRefreshLayout != null) {
                        mRefreshLayout.finishLoadMore(true);
                        page++;
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("vipLevel", mVipLevel);
                        map.put("page", page);
                        mPresenter.getDataP1(map, DifferentiateEnum.TEAM, mLoadingLayout);
                    }

                    break;
            }
            return false;
        }
    });
    private String mVipLevel;
    private PopupWindow mPopupWindow;
    private View inflate;
    private TextView mText;
    public static final int REQUEST_CALL_PERMISSION = 10111; //拨号请求码
    private String mMobile;

    public TeamFragment() {
        // Required empty public constructor
    }

    public static TeamFragment getInstance(String vipLevel) {
        Bundle bundle = new Bundle();
        bundle.putString("vipLevel", vipLevel);
        TeamFragment teamFragment = new TeamFragment();
        teamFragment.setArguments(bundle);
        return teamFragment;
    }

    private void initView() {
        mRecylerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<TeamBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapter_team = new RlvAdapter_team(resultBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapter_team);

        mRlvAdapter_team.setOnClickLisiter(new RlvAdapter_team.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<TeamBean.ResultBean> mData) {
                mMobile = mData.get(position).getMobile();
                mText.setText(mMobile);
                mPopupWindow.showAtLocation(inflate, Gravity.CENTER,0,0);
                setBack();
            }
        });
    }
    //设置背景透明度
    private void setBack() {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = (float) 0.3; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }
    private void showPop() {
        inflate = LayoutInflater.from(getContext()).inflate(R.layout.playnumber, null);
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
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
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
                startCallPhone("tel:" + mMobile);
                mPopupWindow.dismiss();
            }
        });
    }


    //提示用户权限设置
    private void LocationSetting() {
        mInflates = LayoutInflater.from(getContext()).inflate(R.layout.layout_pop_setting_callow, null);
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

        mInflates.findViewById(R.id.go_ons).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JmupPerssionMagent.GoToSetting(getActivity());
                mMPopupWindow.dismiss();
            }
        });
        mMPopupWindow.showAtLocation(mInflates, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void startCallPhone(String phoneNumber) {
        //判断Android版本是否大于23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        REQUEST_CALL_PERMISSION);
            } else {
                call(phoneNumber);
            }
        } else {
            call(phoneNumber);
            // 检查是否获得了权限（Android6.0运行时权限）
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // 没有获得授权，申请授权
                if (this.shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                    // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                    // 弹窗需要解释为何需要该权限，再次请求授权
                    ToastUtils.showShortToast(getActivity(),"请授权");

                    // 帮跳转到该应用的设置界面，让用户手动授权
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                } else {
                    // 不需要解释为何需要该权限，直接请求授权
                    ActivityCompat.requestPermissions((Activity) getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CALL_PERMISSION);
                }
            } else {
                // 已经获得授权，可以打电话
                call(phoneNumber);
            }
        }

    }

    /**
     * 拨打电话（直接拨打）
     *
     * @param telPhone 电话
     */
    public void call(String telPhone) {
//        if (checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION)) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(telPhone));
            startActivity(intent);
//        }else {
//            LocationSetting();
//        }
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
        if (ContextCompat.checkSelfPermission(getContext(), string_permission) == PackageManager.PERMISSION_GRANTED) {//已有权限
            flag = true;
        } else {//申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{string_permission}, request_code);

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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 授权成功，继续打电话
                    call("tel:" + mMobile);
                } else {//成功
                    LocationSetting();
                }
                break;
        }


    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_team;
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        mVipLevel = bundle.getString("vipLevel");
        Log.i("vipLevel", "initData: " + mVipLevel);

        initView();
        HashMap<String, Object> map = new HashMap<>();
        map.put("vipLevel", mVipLevel);
        map.put("page", page);
        mPresenter.getDataP1(map, DifferentiateEnum.TEAM, mLoadingLayout);
        mLoadingLayout.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mPresenter.getDataP1("", DifferentiateEnum.TEAM, mLoadingLayout);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                Message message = new Message();
                message.what = 1;
                //message.obj = data ;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Message message = new Message();
                message.what = 2;
                mHandler.sendMessageDelayed(message, 1500);
            }
        });
        showPop();
        checkReadPermission(Manifest.permission.CALL_PHONE, REQUEST_CALL_PERMISSION);
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case TEAM:
                TeamBean teamBean = (TeamBean) o;
                List<TeamBean.ResultBean> result = teamBean.getResult();
                if (result.size() != 0) {
                    if (page == 1) {
                        mRlvAdapter_team.addData(result,true);
                    } else {
                        mRlvAdapter_team.addData(result,false);
                    }
                }

                break;
        }
    }

    @Override
    public void showError(String error) {

    }

}
