package com.tiancaijiazu.app.activitys.qi_activitys;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.myactivity.NichengActivity;
import com.tiancaijiazu.app.activitys.early.datepicker.CustomDatePicker;
import com.tiancaijiazu.app.activitys.early.datepicker.DateFormatUtils;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.AddBabyBean;
import com.tiancaijiazu.app.beans.BabyMessageBean;
import com.tiancaijiazu.app.beans.UpBabyBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.Globals;
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
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class BaByAddActivity extends BaseActivity<IView, Presenter<IView>> implements IView, EasyPermissions.PermissionCallbacks  {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.baby_name)
    TextView mBabyName;
    @BindView(R.id.line_name)
    LinearLayout mLineName;
    @BindView(R.id.baby_birth)
    TextView mBabyBirth;
    @BindView(R.id.line_birth)
    LinearLayout mLineBirth;
    @BindView(R.id.baby_sex)
    TextView mBabySex;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.line_sex)
    LinearLayout mLineSex;
    @BindView(R.id.switc)
    Switch mSwitc;
    @BindView(R.id.baby_ok)
    ImageView mBabyOk;
    @BindView(R.id.baby_pic)
    CircleImageView mBabyPic;
    @BindView(R.id.line_zreo)
    LinearLayout mLineZreo;
    private PopupWindow mPopupWindow;
    private View mInflate;
    private int gender = 0;
    private int isDefault = 2;
    private Intent mIntent;
    private String mBianji;
    private long mBabyId;
    private int mSize;
    private CustomDatePicker mDatePicker;
    private String s;
    private File rootFile;
    private File captureFile;
    private File cropFile;
    private File mFile;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initDatePicker();
        mIntent = getIntent();
        mBianji = mIntent.getStringExtra("bianji");
        mSize = mIntent.getIntExtra("size", 2);
        if (mBianji != null) {
            List<BabyMessageBean.ResultBean> mData = (List<BabyMessageBean.ResultBean>) mIntent.getSerializableExtra("data");
            int position = mIntent.getIntExtra("position", 0);
            mBabyName.setText(mData.get(position).getName());
            mBabyBirth.setText(mData.get(position).getBirthday());
            Glide.with(this).load(mData.get(position).getAvatar()).into(mBabyPic);
            int gender = mData.get(position).getGender();
            if (gender == 1) {
                mBabySex.setText("男宝宝");
            } else if (gender == 2) {
                mBabySex.setText("女宝宝");
            }
            int isDefault = mData.get(position).getIsDefault();
            if (isDefault == 0) {
                mSwitc.setChecked(false);
            } else if (isDefault == 1) {
                mSwitc.setChecked(true);
            }
            mBabyId = mData.get(position).getBabyId();
        }
        initPop();
        rootFile = new File(Globals.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
    }

    private void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.item_baby_sex_layout, null);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPopupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        TextView tv = mInflate.findViewById(R.id.tv);
        TextView baby_man = mInflate.findViewById(R.id.baby_man);
        TextView baby_woman = mInflate.findViewById(R.id.baby_woman);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        baby_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBabySex.setText("男宝宝");
                gender = 1;
                mPopupWindow.dismiss();
            }
        });
        baby_woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBabySex.setText("女宝宝");
                gender = 2;
                mPopupWindow.dismiss();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_ba_by_add;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case ADDBABY:
                AddBabyBean addBabyBean = (AddBabyBean) o;
                String code = addBabyBean.getCode();
                if ("0".equals(code)) {
                    setResult(22, mIntent);
                    finish();
                }
                break;
            case UPBABY:
                UpBabyBean upBabyBean = (UpBabyBean) o;
                Log.i("yx123", "show: "+upBabyBean.getCode());
                if ("0".equals(upBabyBean.getCode())) {
                    setResult(22, mIntent);
                    finish();
                }
                Toast.makeText(mActivity, upBabyBean.getResult(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }


    @OnClick({R.id.iv_finis, R.id.line_name, R.id.line_birth, R.id.line_sex, R.id.baby_ok,R.id.line_zreo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.line_name:
                String s1 = mBabyName.getText().toString();
                Intent in = new Intent(BaByAddActivity.this, NichengActivity.class);
                in.putExtra("biao", "babyname");
                in.putExtra("name",s1);
                startActivityForResult(in, 25);
                break;
            case R.id.line_birth:
                hideInput();
                String time = mBabyBirth.getText().toString();
                Log.d("bjg", "onViewClicked: " + time);
                String nowThree = TimeUtil.getNowThree();
                if (time.equals("")) {
                    mDatePicker.show(nowThree);
                } else {
                    mDatePicker.show(time);
                }
                break;
            case R.id.line_sex:
                mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.baby_ok:
                if (TimeUtil.isInvalidClick(mBabyOk, 300))
                    return;
                String name = mBabyName.getText().toString();
                String birth = mBabyBirth.getText().toString();
                String s = mBabySex.getText().toString();
                if ("男宝宝".equals(s)) {
                    gender = 1;
                } else if ("女宝宝".equals(s)) {
                    gender = 2;
                }
                if (mSize == 0) {
                    isDefault = 1;
                } else {
                    boolean checked = mSwitc.isChecked();
                    if (checked) {
                        isDefault = 1;
                    } else {
                        isDefault = 0;
                    }
                }

                if (mBianji != null) {
                    if (name != null && birth != null && gender != 0 && isDefault != 2) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("babyId", mBabyId + "");
                        map.put("name", name);
                        map.put("gender", gender);
                        map.put("birthday", birth);
                        map.put("isDefault", isDefault);
                        map.put("files",mFile);
                        mPresenter.getDataP(map, DifferentiateEnum.UPBABY);
                    } else {
                        Toast.makeText(mActivity, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (name != null && birth != null && gender != 0 && isDefault != 2) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("name", name);
                        map.put("gender", gender);
                        map.put("birthday", birth);
                        map.put("isDefault", isDefault);
                        map.put("files",mFile);
                        mPresenter.getDataP(map, DifferentiateEnum.ADDBABY);
                    } else {
                        Toast.makeText(mActivity, "请填写完整信息", Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.line_zreo:
                if (EasyPermissions.hasPermissions(BaByAddActivity.this, PERMISSION_WRITE)) {
                    choosePhoto();
                } else {
                    EasyPermissions.requestPermissions(BaByAddActivity.this, "need camera permission", REQUEST_PERMISSION_WRITE, PERMISSION_WRITE);
                }//从系统相册选择
                break;
        }
    }
    //从相册选择
    private void choosePhoto() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_PERMISSION_WRITE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            takePhoto();
        } else if (requestCode == REQUEST_PERMISSION_WRITE) {
            choosePhoto();
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
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int REQUEST_PERMISSION_CAMERA = 0x001;
    private static final int REQUEST_PERMISSION_WRITE = 0x002;
    private static final int CROP_REQUEST_CODE = 0x003;

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
    /**
     * 隐藏键盘
     */
    protected void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (requestCode == 25 && resultCode == 26) {
            String baby = data.getStringExtra("baby");
            mBabyName.setText(baby);
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
                    mFile = getFile(bitmap);

                    mBabyPic.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2009-05-01", false);
        long endTimestamp = System.currentTimeMillis();
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                s = DateFormatUtils.long2Str(timestamp, false);
                Log.d("bjg", "onTimeSelected: " + s);
                String nowThree = TimeUtil.getNowThree();
                boolean b = TimeUtil.compareTwoTime(s, nowThree);
                if (b) {
                    mBabyBirth.setText(s);
                } else {
                    Toast.makeText(mActivity, "您当前选择日期大于当天，请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
        }, beginTimestamp, endTimestamp);
        mDatePicker.setTitle("选择宝宝生日");
        // 不允许点击屏幕或物理返回键关闭
        mDatePicker.setCancelable(false);
        // 不显示时和分
        mDatePicker.setCanShowPreciseTime(false);
        // 不允许循环滚动
        mDatePicker.setScrollLoop(false);
        // 不允许滚动动画
        mDatePicker.setCanShowAnim(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
