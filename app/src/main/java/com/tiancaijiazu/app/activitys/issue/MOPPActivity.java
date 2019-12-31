package com.tiancaijiazu.app.activitys.issue;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.adapters.RlvAdapter_pop;
import com.tiancaijiazu.app.activitys.issue.widget.CropImageView;
import com.tiancaijiazu.app.activitys.issue.widget.GlideImageLoader;
import com.tiancaijiazu.app.activitys.issue.widget.ImageGridActivity;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePicker;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePickerAdapter;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePreviewDelActivity;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.activitys.topic.TopicCentrectivity;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.PublishArticleBean;
import com.tiancaijiazu.app.beans.TopicListsBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DrawableTextUtil;
import com.tiancaijiazu.app.utils.DrawableUtil;
import com.tiancaijiazu.app.utils.PhotoUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MOPPActivity extends BaseActivity<IView, Presenter<IView>> implements IView, ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_CAMERA = 102;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.relative)
    RelativeLayout mRela;
    @BindView(R.id.title_topic)
    EditText mTitle;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.title_sum)
    TextView mTitleSum;
    @BindView(R.id.a)
    TextView a;
    @BindView(R.id.issue)
    ImageView mIssue;
    @BindView(R.id.edit_data)
    EditText mEditData;
    @BindView(R.id.data_sum)
    TextView mDataSum;
    @BindView(R.id.line)
    RelativeLayout mLine;
    @BindView(R.id.lin)
    LinearLayout mLin;
    @BindView(R.id.flow)
    FlowGroupView mFlow;
    @BindView(R.id.line1)
    LinearLayout mLine1;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.tv)
    TextView mTv;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private ImagePickerAdapter mPickerAdapter;
    private Intent mIntent;
    private int page = 1;
    private RlvAdapter_pop mRlvAdapterPop;
    private int sum = 1;
    private ArrayList<TextView> textList = new ArrayList<>();
    private TextView mTuijian;
    private TextView mXin_topic;
    private EditText mEditSou;
    private HashMap<String, String> mMap = new HashMap<>();
    private PopupWindow mPopupWindow;
    private boolean isbo = true;

    @Override
    protected void initEventAndData() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ScreenStatusUtil.setFillDip(this);
        mIntent = getIntent();
        initImagePicker();
        initRecy();
        initRlv();
        initEdit();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 10, 0);
        TextView textView = new TextView(MOPPActivity.this);
        textView.setLayoutParams(layoutParams);
        textView.setLineSpacing(1.2f, 1.2f);//设置行间距
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setText("# 添加话题");
        textView.setBackgroundResource(R.drawable.shape_issue_button);
        textView.setTextColor(Color.parseColor("#00DEFF"));
        initEvents(textView);
        textView.setPadding(calculateDpToPx(10), calculateDpToPx(3), calculateDpToPx(10), calculateDpToPx(3));
        mFlow.addView(textView, layoutParams);
    }

    private void initEvents(TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals("# 添加话题")) {
                    pop();
                } else {
                    Intent intent = new Intent(MOPPActivity.this, TopicCentrectivity.class);
                    String s = mMap.get(textView.getText().toString());
                    intent.putExtra("subjectId", s);
                    startActivity(intent);
                }
            }
        });
    }

    public void pop() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.pop_topic, null);
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
        mPopupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow.setAnimationStyle(R.style.popwin_anim_style);
        mPopupWindow.showAtLocation(mLine, Gravity.BOTTOM, 0, 0);
        TextView tv = inflate.findViewById(R.id.tv);
        ImageView iv_finid = inflate.findViewById(R.id.iv_finis);
        RecyclerView rlv = inflate.findViewById(R.id.rlv);
        mTuijian = inflate.findViewById(R.id.tuijian);
        mXin_topic = inflate.findViewById(R.id.xin_topic);
        mEditSou = inflate.findViewById(R.id.edit_sou);
        final Drawable drawable = getResources().getDrawable(R.mipmap.sou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        final Drawable drawable1 = getResources().getDrawable(R.mipmap.shan_sou);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() != 0) {
                    page = 1;
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("keyWord", s.toString());
                    map.put("page", page);
                    mPresenter.getDataP(map, DifferentiateEnum.SOUTOPICLIST);
                    mEditSou.setCompoundDrawables(drawable, null, drawable1, null);
                } else {
                    page = 1;
                    mPresenter.getDataP(page, DifferentiateEnum.TOPICLISTS);
                    mXin_topic.setText("");
                    mTuijian.setText("推荐话题");
                    mEditSou.setCompoundDrawables(drawable, null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mEditSou.addTextChangedListener(textWatcher);
        DrawableUtil onDrawableListener = new DrawableUtil(mEditSou, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                Log.i("yx", "onLeft: ");
            }

            @Override
            public void onRight(View v, Drawable right) {
                Log.i("yx", "onRight: ");
                mEditSou.setText("");
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        iv_finid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });
        mPresenter.getDataP(page, DifferentiateEnum.TOPICLISTS);
        rlv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<TopicListsBean.ResultBean> resultBeans = new ArrayList<>();
        mRlvAdapterPop = new RlvAdapter_pop(resultBeans);
        rlv.setAdapter(mRlvAdapterPop);
        //点击列表添加话题
        mRlvAdapterPop.setOnClickLisiter(new RlvAdapter_pop.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<TopicListsBean.ResultBean> mData) {
                Log.i("yx123", "onClicker: ==============");
                //每次点击都先将isbo赋值为ture
                isbo = true;
                //判断话题是否已添加,如已添加吐司提示并isbo赋值为false
                for (int i = 0; i < textList.size(); i++) {
                    if (textList.get(i).getText().toString().contains(mData.get(position).getSubjectName())) {
                        ToastUtils.showShortToast(mActivity, "话题已选择");
                        isbo = false;
                        break;
                    }
                }
                if (isbo) {
                    //记录添加个数
                    if (sum <= 3) {
                        sum++;
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 10, 10, 0);
                        TextView textView = new TextView(MOPPActivity.this);
                        textView.setLayoutParams(layoutParams);
                        textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                        textView.setText("# " + mData.get(position).getSubjectName());
                        textView.setBackgroundResource(R.drawable.shape_issue_text);
                        textView.setTextColor(Color.parseColor("#00DEFF"));
                        final Drawable drawable = getResources().getDrawable(R.mipmap.fin_flow);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                        textView.setCompoundDrawables(null, null, drawable, null);
                        textView.setGravity(Gravity.CENTER);
                        initEvents(textView);
                        textView.setPadding(calculateDpToPx(10), calculateDpToPx(0), calculateDpToPx(5), calculateDpToPx(0));
                        mFlow.addView(textView, layoutParams);
                        mTv.setVisibility(View.GONE);
                        mMap.put("# " + mData.get(position).getSubjectName(), mData.get(position).getSubjectId() + "");
                        textList.add(textView);
                        DrawableTextUtil onDrawableListener = new DrawableTextUtil(textView, new DrawableTextUtil.OnDrawableListener() {

                            @Override
                            public void onLeft(View v, Drawable left) {

                            }

                            @Override
                            public void onRight(View v, Drawable right) {
                                mFlow.removeView(v);
                                textList.remove(v);
                                sum--;
                                Log.i("yx456", "onRight: --"+sum);
                                if(sum == 1){
                                    mTv.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        mPopupWindow.dismiss();
                    } else {
                        ToastUtils.showShortToast(mActivity, "最多添加三个话题");
                        mPopupWindow.dismiss();
                    }
                }
            }
        });
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
                //Log.i("yx", "onTextChanged: "+s.toString());
                mTitleSum.setText(s.toString().length() + "/30");

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
        mTitle.addTextChangedListener(textWatcher);
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
                                Intent intent1 = new Intent(MOPPActivity.this, ImageGridActivity.class);
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
                                Intent intent = new Intent(MOPPActivity.this, ImageGridActivity.class);
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
            //添加图片返回
            images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    Log.i("yx=", "onActivityResult: " + selImageList.size());
                    mPickerAdapter.setImages(selImageList);
                }
            }else if (data != null && requestCode == REQUEST_CODE_CAMERA){
                selImageList.addAll(images);
                Log.i("yx=", "onActivityResult: " + selImageList.size());
                mPickerAdapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    mPickerAdapter.setImages(selImageList);
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
        return R.layout.activity_mopp;
    }

    private File mFile;
    @OnClick({R.id.iv_finis, R.id.issue})
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
                }else {
                    ArrayList<File> files = new ArrayList<>();
                    ArrayList<File> filess = new ArrayList<>();
                    for (int i = 0; i < mPickerAdapter.mData.size() - 1; i++) {
                        Log.d("DaXiao", "onViewClicked: "+new File(mPickerAdapter.mData.get(i).path).length());
                        files.add(new File(mPickerAdapter.mData.get(i).path));
                                Luban.with(MOPPActivity.this)
                                        .load(files.get(i))                                   // 传人要压缩的图片列表
                                        .ignoreBy(0)                                  // 忽略不压缩图片的大小  // 设置压缩后文件存储位置
                                        .setCompressListener(new OnCompressListener() {
                                            @Override
                                            public void onStart() {
                                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                                Log.d("Start", "onStart: "+"开始压缩");
                                            }
                                            @Override
                                            public void onSuccess(File file) {
                                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                                Log.d("Start", "onSuccess压缩成功:"+ file.length());
                                                filess.add(file);
                                                if (filess.size()==mPickerAdapter.mData.size() - 1) {
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
        }
    }
    private void toGit(ArrayList<File> filess) {
        String title = mTitle.getText().toString();
        String data = mEditData.getText().toString();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < textList.size(); i++) {
            String s = textList.get(i).getText().toString();
            Log.d("Mops", "onViewClicked: " + s);
            String substring;
            if (s.length() > 4) {
                substring = s.substring(s.length() - 4, s.length());
            } else {
                substring = s.substring(s.length() - 2, s.length());
            }
            if (i == textList.size() - 1) {
                stringBuffer.append(substring);
            } else {
                stringBuffer.append(substring + ",");
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        //判断是否有要发布话题标题，如果没有就只发布文章与图片
        if (title.length() == 0) {
            map.put("title", "");
            map.put("detail", data);
            map.put("files", filess);
            map.put("subject", stringBuffer.toString());
            map.put("articleType", "1");
        } else if (textList.size() == 0) {
            map.put("title", title);
            map.put("detail", data);
            map.put("files", filess);
            map.put("subject", stringBuffer.toString());
            map.put("articleType", "1");
        } else {
            map.put("title", title);
            map.put("detail", data);
            map.put("files", filess);
            map.put("articleType", "1");
            String str = "";
            for (int i = 0; i < textList.size(); i++) {
                String s = textList.get(i).getText().toString();
                String substring = s.substring(2, s.length());
                if (i < textList.size() - 1) {
                    str += substring + ",";
                } else {
                    str += substring;
                }
            }
            map.put("subject", str);
        }
        mPresenter.getDataP(map, DifferentiateEnum.PUBLISHARTICLE);
        mIssue.setEnabled(false);
    }

    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

    @Override
    public void showError(String error) {
        mIssue.setEnabled(true);
        Toast.makeText(mActivity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case TOPICLISTS:
                TopicListsBean topicListsBean = (TopicListsBean) o;
                List<TopicListsBean.ResultBean> result = topicListsBean.getResult();
                if (result.size() != 0 && mRlvAdapterPop != null) {
                    mRlvAdapterPop.addData(result);
                }
                break;
            case PUBLISHARTICLE:
                PublishArticleBean publishArticleBean = (PublishArticleBean) o;
                Log.i("yx", "show: " + publishArticleBean.getCode());
                //请求判断是否发布成功
                if (publishArticleBean.getCode().equals("0")) {
                    setResult(112, mIntent);
                    Toast.makeText(mActivity, "发布成功!", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    mIssue.setEnabled(true);
                    Toast.makeText(mActivity, publishArticleBean.getMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
            case SOUTOPICLIST:
                TopicListsBean topicListsBean1 = (TopicListsBean) o;
                List<TopicListsBean.ResultBean> result1 = topicListsBean1.getResult();
                if (result1.size() != 0 && mRlvAdapterPop != null) {
                    mRlvAdapterPop.addData(result1);
                    mXin_topic.setText("");
                    mTuijian.setText("推荐话题");
                } else {
                    mRlvAdapterPop.addData(result1);
                    mXin_topic.setText("#" + mEditSou.getText().toString());
                    mTuijian.setText("没有想参与的话题？创建新话题：");
                    //自定义话题添加
                    mXin_topic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //每次点击都先将isbo赋值为true
                            isbo = true;
                            //判断是否包含自定义话题如果包含吐司提示并赋值为false
                            for (int i = 0; i < textList.size(); i++) {
                                if (textList.get(i).getText().toString().contains(mXin_topic.getText().toString())) {
                                    ToastUtils.showShortToast(mActivity, "已创建新话题！");
                                    isbo = false;
                                    break;
                                }
                            }

                            if (isbo) {
                                if (sum <= 3) {
                                    sum++;
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, 10, 10, 0);
                                    TextView textView = new TextView(MOPPActivity.this);
                                    textView.setLayoutParams(layoutParams);
                                    textView.setLineSpacing(1.2f, 1.2f);//设置行间距
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                    textView.setText(mXin_topic.getText().toString());
                                    textView.setBackgroundResource(R.drawable.shape_issue_text);
                                    textView.setTextColor(Color.parseColor("#00DEFF"));
                                    final Drawable drawable = getResources().getDrawable(R.mipmap.fin_flow);
                                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                                    textView.setCompoundDrawables(null, null, drawable, null);
                                    textView.setGravity(Gravity.CENTER);
                                    initEvents(textView);
                                    textView.setPadding(calculateDpToPx(10), calculateDpToPx(0), calculateDpToPx(5), calculateDpToPx(0));
                                    mFlow.addView(textView, layoutParams);
                                    mTv.setVisibility(View.GONE);
                                    textList.add(textView);
                                    DrawableTextUtil onDrawableListener = new DrawableTextUtil(textView, new DrawableTextUtil.OnDrawableListener() {

                                        @Override
                                        public void onLeft(View v, Drawable left) {

                                        }

                                        @Override
                                        public void onRight(View v, Drawable right) {
                                            mFlow.removeView(v);
                                            textList.remove(v);
                                            sum--;
                                            Log.i("yx456", "onRight: =="+sum);
                                            if(sum == 1){
                                                mTv.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                    mPopupWindow.dismiss();
                                } else {
                                    ToastUtils.showShortToast(mActivity, "最多添加三个话题");
                                    mPopupWindow.dismiss();
                                }
                            }
                        }
                    });
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
