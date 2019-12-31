package com.tiancaijiazu.app.activitys.issue;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.datepicker.CustomDatePicker;
import com.tiancaijiazu.app.activitys.early.datepicker.DateFormatUtils;
import com.tiancaijiazu.app.activitys.issue.widget.CropImageView;
import com.tiancaijiazu.app.activitys.issue.widget.GlideImageLoader;
import com.tiancaijiazu.app.activitys.issue.widget.ImageGridActivity;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePicker;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePickerAdapter;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePreviewDelActivity;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.PublishArticleBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.IView;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.MediumBoldTextViewTitle;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MOPP1Activity extends BaseActivity<IView, Presenter<IView>> implements IView, ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.a)
    MediumBoldTextViewTitle mA;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.edit_data)
    EditText mEditData;
    @BindView(R.id.data_sum)
    TextView mDataSum;
    @BindView(R.id.line1)
    RelativeLayout mLine1;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.date)
    RelativeLayout mDate;
    @BindView(R.id.tv_who)
    TextView mTvWho;
    @BindView(R.id.backs)
    ImageView mBacks;
    @BindView(R.id.who)
    RelativeLayout mWho;
    @BindView(R.id.issue)
    ImageView mIssue;
    @BindView(R.id.line)
    RelativeLayout mLine;

    private int disPlay = 0;

    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePickerAdapter mPickerAdapter;
    private Intent mIntent;
    private HashMap<String, String> mMap = new HashMap<>();
    private PopupWindow mPopupWindow;
    private PopupWindow mPopupWindow1;
    private CustomDatePicker mDatePicker;

    @Override
    protected void initEventAndData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        initView();
        initDatePicker();
        initImagePicker();
        initRecy();
        initRlv();
        initEdit();
    }

    private void initView() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        mTvDate.setText(year + "-" + month + "-" + day);
    }

    private void initRecy() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFrlg = 0;
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    dragFrlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                return makeMovementFlags(dragFrlg, 0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //滑动事件  下面注释的代码，滑动后数据和条目错乱，被舍弃
//            Collections.swap(datas,viewHolder.getAdapterPosition(),target.getAdapterPosition());
//            ap.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());

                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mPickerAdapter.mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mPickerAdapter.mData, i, i - 1);
                    }
                }
                mPickerAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //侧滑删除可以使用；
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            /**
             * 长按选中Item的时候开始调用
             * 长按高亮
             * @param viewHolder
             * @param actionState
             */
            @SuppressLint("MissingPermission")
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                Log.i("123", "onSelectedChanged: " + actionState);
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
                    //获取系统震动服务//震动70毫秒
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(70);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }
            /**
             * 手指松开的时候还原高亮
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setBackgroundColor(0);
                mPickerAdapter.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
            }
        });
        helper.attachToRecyclerView(mRlv);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEdit() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        TextWatcher textWatcher1 = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDataSum.setText(s.toString().length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEditData.addTextChangedListener(textWatcher1);
        mEditData.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 解决scrollView中嵌套EditText导致不能上下滑动的问题
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });
    }

    private void initRlv() {
        selImageList = new ArrayList<>();
        mRlv.setLayoutManager(new GridLayoutManager(this, 3));
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        mPickerAdapter = new ImagePickerAdapter(this, imageItems, maxImgCount);
        mRlv.setAdapter(mPickerAdapter);
        mPickerAdapter.setOnItemClickListener(this);
        mPickerAdapter.setImages(selImageList);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("相册");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - 0);
                                Intent intent1 = new Intent(MOPP1Activity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
                                ArrayList<ImageItem> imageItems = new ArrayList<>();
                                for (int i = 0; i < mPickerAdapter.mData.size() - 1; i++) {
                                    imageItems.add(mPickerAdapter.mData.get(i));
                                }
                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            case 0: // 直接调起相机
                                /**
                                 * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                                 *
                                 * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                                 *
                                 * 如果实在有所需要，请直接下载源码引用。
                                 */
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(MOPP1Activity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_CAMERA);
                                break;
                            default:
                                break;
                        }
                    }
                }, names);
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                ArrayList<ImageItem> imageItems = new ArrayList<>();
                if (mPickerAdapter.isAdded) {
                    for (int i = 0; i < mPickerAdapter.mData.size() - 1; i++) {
                        imageItems.add(mPickerAdapter.mData.get(i));
                    }
                } else {
                    for (int i = 0; i < mPickerAdapter.mData.size(); i++) {
                        imageItems.add(mPickerAdapter.mData.get(i));
                    }
                }
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) imageItems);
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    Log.i("yx=", "onActivityResult: " + selImageList.size());
                    mPickerAdapter.setImages(selImageList);
                }
            } else if (data != null && requestCode == REQUEST_CODE_CAMERA) {
                selImageList.addAll(images);
                Log.i("yx=", "onActivityResult: " + selImageList.size());
                mPickerAdapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    mPickerAdapter.setImages(selImageList);
                    selImageList.clear();
                }
            }
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_mopp1;
    }


    @OnClick({R.id.iv_finis, R.id.issue, R.id.date, R.id.who})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.issue:
                if (TimeUtil.isInvalidClick(mIssue, 300))
                    return;
                if (selImageList.size() == 0) {
                    ToastUtils.showShortToast(mActivity, "请添加图片！");
                } else {
                    ArrayList<File> files = new ArrayList<>();
                    ArrayList<File> filess = new ArrayList<>();
                    for (int i = 0; i < mPickerAdapter.mData.size() - 1; i++) {
                        Log.d("DaXiao", "onViewClicked: " + new File(mPickerAdapter.mData.get(i).path).length());
                        files.add(new File(mPickerAdapter.mData.get(i).path));
                        Luban.with(MOPP1Activity.this)
                                .load(files.get(i))                                   // 传人要压缩的图片列表
                                .ignoreBy(0)                                          // 忽略不压缩图片的大小  // 设置压缩后文件存储位置
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {
                                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                        Log.d("Start", "onStart: " + "开始压缩");
                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        // TODO 压缩成功后调用，返回压缩后的图片文件
                                        Log.d("Start", "onSuccess压缩成功:" + file.length());
                                        filess.add(file);
                                        if (filess.size() == mPickerAdapter.mData.size() - 1) {
                                            toGit(filess);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        // TODO 当压缩过程出现问题时调用
                                    }
                                }).launch();    //启动压缩
                    }

                }
                break;
            case R.id.date:
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 0.5f;
                getWindow().setAttributes(attributes);
                pop();
                break;
            case R.id.who:
                WindowManager.LayoutParams attributes1 = getWindow().getAttributes();
                attributes1.alpha = 0.5f;
                getWindow().setAttributes(attributes1);
                showPop();
                break;
        }
    }

    private void toGit(ArrayList<File> filess) {
        String data = mEditData.getText().toString();
        String keyTime = mTvDate.getText().toString();
        HashMap<String, Object> map = new HashMap<>();
        map.put("detail", data);
        map.put("display", disPlay + "");
        map.put("keyTime", keyTime);
        map.put("files", filess);
        map.put("articleType", "3");
        map.put("subject", "");
        map.put("contentsId", "");
        map.put("courseId", "");
        map.put("title", "");
        mPresenter.getDataP(map, DifferentiateEnum.PUBLISHARTICLE);
        mIssue.setEnabled(false);
    }

    public void pop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_date_select, null);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);

        RelativeLayout zdy = inflate.findViewById(R.id.zdy);
        RelativeLayout qx = inflate.findViewById(R.id.qx);
        TextView time = inflate.findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        time.setText("现在时间" + year + "-" + month + "-" + day);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nowTime = TimeUtil.getNowThree();
                mTvDate.setText(nowTime);
                mPopupWindow.dismiss();
            }
        });
        zdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = mTvDate.getText().toString();
                String nowThree = TimeUtil.getNowThree();
                if ("".equals(time)) {
                    mDatePicker.show(nowThree);
                } else {
                    mDatePicker.show(time);
                }
                mPopupWindow.dismiss();
            }
        });

        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 1;
                getWindow().setAttributes(attributes);
            }
        });
        mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }

    private void showPop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_who_select, null);
        mPopupWindow1 = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        RelativeLayout quan = inflate.findViewById(R.id.quan);
        RelativeLayout friend = inflate.findViewById(R.id.friend);
        RelativeLayout just = inflate.findViewById(R.id.just);
        TextView quans = inflate.findViewById(R.id.tv_quan);
        TextView friends = inflate.findViewById(R.id.tv_friend);
        TextView justs = inflate.findViewById(R.id.tv_just);
        RelativeLayout guan = inflate.findViewById(R.id.guan);

        mPopupWindow1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams attributes = getWindow().getAttributes();
                attributes.alpha = 1;
                getWindow().setAttributes(attributes);
            }
        });
        quan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = quans.getText().toString();
                mTvWho.setText(s);
                disPlay = 0;
                mPopupWindow1.dismiss();
            }
        });
        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = friends.getText().toString();
                mTvWho.setText(s);
                disPlay = 1;
                mPopupWindow1.dismiss();
            }
        });
        just.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = justs.getText().toString();
                mTvWho.setText(s);
                disPlay = 2;
                mPopupWindow1.dismiss();
            }
        });
        guan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
        mPopupWindow1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
    }

    private void initDatePicker() {
        long beginTimestamp = DateFormatUtils.str2Long("2013-01-01", false);
        long endTimestamp = System.currentTimeMillis();
        // 通过时间戳初始化日期，毫秒级别
        mDatePicker = new CustomDatePicker(this, new CustomDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String s = DateFormatUtils.long2Str(timestamp, false);
                Log.d("bjg", "onTimeSelected: " + s);
                String nowThree = TimeUtil.getNowThree();
                boolean b = TimeUtil.compareTwoTime(s, nowThree);
                if (b) {
                    mTvDate.setText(s);
                    mTvDate.setTextColor(Color.parseColor("#999999"));
                } else {
                    Toast.makeText(mActivity, "您当前选择日期大于当天，请重新选择", Toast.LENGTH_SHORT).show();
                }
            }
        }, beginTimestamp, endTimestamp);
        mDatePicker.setTitle("自定义时间");
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
    public void showError(String error) {
        mIssue.setEnabled(true);
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case PUBLISHARTICLE:
                PublishArticleBean publishArticleBean = (PublishArticleBean) o;
                Log.i("yx", "show: " + publishArticleBean.getCode());
                //请求判断是否发布成功
                if (publishArticleBean.getCode().equals("0")) {
                    setResult(113, mIntent);
                    Toast.makeText(mActivity, "发布成功!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    mIssue.setEnabled(true);
                    Toast.makeText(mActivity, publishArticleBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
    }
}
