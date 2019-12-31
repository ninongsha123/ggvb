package com.tiancaijiazu.app.activitys.activitypage.myactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.UpBean;
import com.tiancaijiazu.app.beans.UserInfoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.Globals;
import com.tiancaijiazu.app.pickerview.AddressPickTask;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 *  编辑用户个人信息
 *
 */

public class BianjiziliaoActivity extends BaseActivity<IView, Presenter<IView>> implements IView, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.sex)
    RelativeLayout sex;
    @BindView(R.id.changzhudi)
    RelativeLayout changzhudi;
    @BindView(R.id.selectsex)
    TextView selectsex;
    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.diqu)
    TextView diqu;
    @BindView(R.id.nicheng)
    RelativeLayout nicheng;
    @BindView(R.id.geqian)
    RelativeLayout geqian;
    @BindView(R.id.name)
    TextView name; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.qianming)
    TextView qianming;
    @BindView(R.id.head)
    RelativeLayout head;
    @BindView(R.id.touxiang)
    CircleImageView touxiang;
    @BindView(R.id.line)
    LinearLayout mLine;
    private PopupWindow mPopupWindow;


    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CAMERA = 0x001;
    private static final int REQUEST_PERMISSION_WRITE = 0x002;
    private static final int CROP_REQUEST_CODE = 0x003;
    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;
    //三级联动
    private boolean isProvinceCyclic = true;
    private boolean isCityCyclic = true;
    private boolean isDistrictCyclic = true;
    private boolean isShowGAT = true;
    private PopupWindow popupWindow;
    private View contentView;

    private TextView nan;
    private TextView nv;
    private String rename;
    private String sss;
    private View inflate;
    private Intent mIntent;
    private int select_sex = 0;
    private UserInfoBean.ResultBean mResultBean;
    private ImageView mSelectNv;
    private ImageView mSelectNan;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initClick();
        //选择男女
        showPopwindow();
        //showPop_Of_head();

        //mPresenter.getDataP(null, DifferentiateEnum.USERINFO);
        mIntent = getIntent();
        mResultBean = (UserInfoBean.ResultBean) mIntent.getSerializableExtra("data");
        if (mResultBean != null) {
            initView(mResultBean);
        }
        String path = Environment.getExternalStorageDirectory().getPath();
        Log.i("yx123", "initEventAndData: " + path);

        rootFile = new File(Globals.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }

    private void initView(UserInfoBean.ResultBean resultBean) {
        String nickname = resultBean.getNickname();
        if (!nickname.equals("")){
            name.setText(nickname);
        }else {
            name.setText("未填写");
            name.setTextColor(Color.parseColor("#BBBBBB"));
        }

        if (resultBean.getGender() == 0) {
            selectsex.setText("未选择");
            selectsex.setTextColor(Color.parseColor("#BBBBBB"));
        } else if (resultBean.getGender() == 1) {
            select_sex = 1;
            selectsex.setText("男性");
            selectsex.setTextColor(Color.parseColor("#333333"));
        } else {
            if (resultBean.getGender() == 2) {
                select_sex = 2;
                selectsex.setText("女性");
                selectsex.setTextColor(Color.parseColor("#333333"));
            }
        }
        if (resultBean.getProvince() != null && resultBean.getCity() != null) {
            diqu.setText(resultBean.getProvince() + "-" + resultBean.getCity()+"-"+resultBean.getCountry());
            diqu.setTextColor(Color.parseColor("#333333"));
            if (diqu.getText().toString().equals("-")) {
                diqu.setText("未选择");
                diqu.setTextColor(Color.parseColor("#BBBBBB"));
            }
        }
        Glide.with(BianjiziliaoActivity.this).load(resultBean.getAvatar()).into(touxiang);
        if (!resultBean.getSummary().equals("")) {
            qianming.setText(resultBean.getSummary());
            qianming.setTextColor(Color.parseColor("#333333"));
        } else {
            qianming.setText("未填写");
            qianming.setTextColor(Color.parseColor("#BBBBBB"));
        }
    }

    private void initClick() {
        //性别选择
        sex.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(mResultBean.getGender() == 1){
                    mSelectNv.setVisibility(View.GONE);
                    mSelectNan.setVisibility(View.VISIBLE);
                }else if(mResultBean.getGender() == 2){
                    mSelectNv.setVisibility(View.VISIBLE);
                    mSelectNan.setVisibility(View.GONE);
                }
                popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            }
        });
        //常住地选择
        changzhudi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //wheel();
                onAddressPicker();
            }
        });
        //返回按钮
        ivFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(120, mIntent);
                finish();
            }
        });
        //修改昵称
        nicheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                Intent in = new Intent(BianjiziliaoActivity.this, NichengActivity.class);
                CharSequence text = name.getText();
                in.putExtra("biao","username");
                in.putExtra("name", text);
                startActivityForResult(in, 01);
            }
        });
        //修改个签
        geqian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeUtil.isInvalidClick(v, 300))
                    return;
                Intent in = new Intent(BianjiziliaoActivity.this, GeqianActivity.class);
                in.putExtra("geqian", qianming.getText().toString());
                startActivityForResult(in, 05);
            }
        });
        //修改头像
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(BianjiziliaoActivity.this, PERMISSION_WRITE)) {
                    choosePhoto();
                } else {
                    EasyPermissions.requestPermissions(BianjiziliaoActivity.this, "need camera permission", REQUEST_PERMISSION_WRITE, PERMISSION_WRITE);
                }//从系统相册选择
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_bianjiziliao;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setResult(120, mIntent);
        return super.onKeyDown(keyCode, event);
    }


    @SuppressLint("NewApi")
    private void showPopwindow() {
        contentView = LayoutInflater.from(BianjiziliaoActivity.this).inflate(
                R.layout.select_sex, null);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);


        TextView cancle = contentView.findViewById(R.id.quxiao);
        RelativeLayout nanxing = contentView.findViewById(R.id.nanxing);
        RelativeLayout nvxing = contentView.findViewById(R.id.nvxing);
        nan = contentView.findViewById(R.id.nan);
        nv = contentView.findViewById(R.id.nv);
        TextView wanChang = contentView.findViewById(R.id.wan_cheng);
        TextView tv = contentView.findViewById(R.id.tv);
        mSelectNv = contentView.findViewById(R.id.select_nv);
        mSelectNan = contentView.findViewById(R.id.select_nan);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        wanChang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(select_sex==0){
                    Toast.makeText(mActivity, "请选择性别", Toast.LENGTH_SHORT).show();
                }else {
                    if(select_sex==1){
                        selectsex.setText("男性");
                        selectsex.setTextColor(Color.parseColor("#333333"));
                    }else {
                        selectsex.setText("女性");
                        selectsex.setTextColor(Color.parseColor("#333333"));
                    }
                    mPresenter.getDataP(select_sex, DifferentiateEnum.UPSEX);
                    popupWindow.dismiss();
                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        nanxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectNan.setVisibility(View.VISIBLE);
                mSelectNv.setVisibility(View.GONE);
                select_sex = 1;
            }
        });
        nvxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectNan.setVisibility(View.GONE);
                mSelectNv.setVisibility(View.VISIBLE);
                select_sex = 2;
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01 && resultCode == 02) {
            Bundle extras = data.getExtras();
            rename = extras.getString("rename");
            name.setText(rename);
        }
        if (requestCode == 05 && resultCode == 04) {
            Bundle extras = data.getExtras();
            String regeqian = extras.getString("regeqian");
            qianming.setText(regeqian);
            qianming.setTextColor(Color.parseColor("#333333"));
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PERMISSION_CAMERA:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(this, getPackageName(), captureFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(captureFile));
                    }
                    break;
                case REQUEST_PERMISSION_WRITE:
                    cropPhoto(data.getData());
                    break;
                case CROP_REQUEST_CODE:
                    saveImage(cropFile.getAbsolutePath());
                    Bitmap bitmap = BitmapFactory.decodeFile(cropFile.getAbsolutePath());
                    File file = getFile(bitmap);
                    mPresenter.getDataP(file, DifferentiateEnum.XIV);
                    touxiang.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    }

    public File getFile(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(Environment.getExternalStorageDirectory() + "/temp.jpg");
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param path
     * @return
     */
    public String saveImage(String path) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        try {
            FileOutputStream fos = new FileOutputStream(cropFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return cropFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        cropFile = new File(rootFile, "avatar.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    //更换头像的
    private void showPop_Of_head() {
        inflate = LayoutInflater.from(this).inflate(R.layout.set_icon_pop, null);
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

        TextView selectPhoto = inflate.findViewById(R.id.tvSelectPhoto);
        TextView takePhoto = inflate.findViewById(R.id.tvTakePhoto);
        TextView cancel = inflate.findViewById(R.id.tvCancel);
        selectPhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case USERINFO:
                UserInfoBean bean = (UserInfoBean) o;
                UserInfoBean.ResultBean result = bean.getResult();

                break;
            case XIV:
                UpBean upBean = (UpBean) o;
                Toast.makeText(mActivity, "" + upBean.getResult(), Toast.LENGTH_SHORT).show();
                break;
            case UPSEX:
                UpBean upBean1 = (UpBean) o;
                Toast.makeText(mActivity, "" + upBean1.getResult(), Toast.LENGTH_SHORT).show();
                mResultBean.setGender(select_sex);
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTakePhoto:
                if (EasyPermissions.hasPermissions(this, PERMISSION_CAMERA, PERMISSION_WRITE)) {
                    takePhoto();
                } else {
                    EasyPermissions.requestPermissions(BianjiziliaoActivity.this, "need camera permission", REQUEST_PERMISSION_CAMERA, PERMISSION_CAMERA, PERMISSION_WRITE);
                }
                break;
            case R.id.tvSelectPhoto:
                if (EasyPermissions.hasPermissions(this, PERMISSION_WRITE)) {
                    choosePhoto();
                } else {
                    EasyPermissions.requestPermissions(this, "need camera permission", REQUEST_PERMISSION_WRITE, PERMISSION_WRITE);
                }//从系统相册选择
                break;
            case R.id.tvCancel:
                mPopupWindow.dismiss();
                break;

        }
    }

    //拍照
    private void takePhoto() {
        //用于保存调用相机拍照后所生成的文件
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        captureFile = new File(rootFile, "temp.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本 如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName(), captureFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureFile));
        }
        startActivityForResult(intent, REQUEST_PERMISSION_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    //从相册选择
    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PERMISSION_WRITE);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            takePhoto();
        } else if (requestCode == REQUEST_PERMISSION_WRITE) {
            choosePhoto();
        }
    }

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                Toast.makeText(mActivity, "数据初始化失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                HashMap<String, String> map = new HashMap<>();
                if (county == null) {
                    Toast.makeText(mActivity, province.getAreaName() + city.getAreaName(), Toast.LENGTH_SHORT).show();
                    diqu.setText(province.getAreaName() + "-" + city.getAreaName());
                    map.put("country", "中国");
                    map.put("province", province.getAreaName());
                    map.put("city", city.getAreaName());
                    mPresenter.getDataP(map, DifferentiateEnum.UPADDRESS);
                } else {
                    Toast.makeText(mActivity, province.getAreaName() + city.getAreaName() + county.getAreaName(), Toast.LENGTH_SHORT).show();
                    diqu.setText(province.getAreaName() + "-" + city.getAreaName()+"-"+county.getAreaName());
                    map.put("country", county.getAreaName());
                    map.put("province", province.getAreaName());
                    map.put("city", city.getAreaName());
                    mPresenter.getDataP(map, DifferentiateEnum.UPADDRESS);
                }


                diqu.setTextColor(Color.parseColor("#333333"));
            }
        });
        task.execute("北京市", "北京市", "海淀区");
    }

}
