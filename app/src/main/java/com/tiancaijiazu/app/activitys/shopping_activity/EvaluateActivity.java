package com.tiancaijiazu.app.activitys.shopping_activity;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.widget.CropImageView;
import com.tiancaijiazu.app.activitys.issue.widget.GlideImageLoader;
import com.tiancaijiazu.app.activitys.issue.widget.ImageGridActivity;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePicker;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePreviewDelActivity;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.ImagePickerEvaluateAdapter;
import com.tiancaijiazu.app.activitys.shopping_activity.adapter.RlvAdapter_evaluate;
import com.tiancaijiazu.app.activitys.shopping_activity.beans.EvaluateBoolean;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.Comment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
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

public class EvaluateActivity extends BaseActivity<IView, Presenter<IView>> implements IView, ImagePickerEvaluateAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.publish)
    TextView mPublish;  @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.round_picture)
    RoundedImageView mRoundPicture;
    @BindView(R.id.rlv)
    RecyclerView mRlv;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.rlv_picture)
    RecyclerView mRlvPicture;
    @BindView(R.id.anonymity_picture)
    CheckBox mAnonymityPicture;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    private List<EvaluateBoolean> evaluateBooleans = new ArrayList<>();
    private int maxImgCount = 8;               //允许选择图片最大数
    private ImagePickerEvaluateAdapter imagePickerEvaluateAdapter;
    private ArrayList<ImageItem> selImageList;
    private String orderId;
    private RlvAdapter_evaluate rlvAdapter_evaluate;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        initImagePicker();
        initRecy();
        Intent intent = getIntent();
        String picture = intent.getStringExtra("picture");
        orderId = intent.getStringExtra("orderId");
        Glide.with(this).load(picture).into(mRoundPicture);
        evaluateBooleans.add(new EvaluateBoolean(false));
        evaluateBooleans.add(new EvaluateBoolean(false));
        evaluateBooleans.add(new EvaluateBoolean(false));
        evaluateBooleans.add(new EvaluateBoolean(false));
        evaluateBooleans.add(new EvaluateBoolean(false));
        rlvAdapter_evaluate = new RlvAdapter_evaluate(evaluateBooleans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRlv.setLayoutManager(linearLayoutManager);
        mRlv.setAdapter(rlvAdapter_evaluate);
        rlvAdapter_evaluate.setOnClickListener(new RlvAdapter_evaluate.setOnItemClick() {
            @Override
            public void setOnItemClickListener(View v, int position, List<EvaluateBoolean> mBoolean) {
                for (int i = 0; i < mBoolean.size(); i++) {
                    if (i <= position) {
                        mBoolean.get(i).setmBoolean(true);
                    } else {
                        mBoolean.get(i).setmBoolean(false);
                    }
                }
                rlvAdapter_evaluate.addUp();
            }
        });
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = charSequence.toString().length();
                if (length != 0) {
                    mEditText.setCompoundDrawables(null, null, null, null);
                    ViewGroup.LayoutParams layoutParams = mEditText.getLayoutParams();
                    int i3 = ScreenStatusUtil.setDp(230, EvaluateActivity.this);
                    layoutParams.height = i3;
                    mEditText.setLayoutParams(layoutParams);
                    mEditText.setGravity(Gravity.TOP);
                    int i4 = ScreenStatusUtil.setDp(17, EvaluateActivity.this);
                    int i5 = ScreenStatusUtil.setDp(12, EvaluateActivity.this);
                    mEditText.setPadding(i4, i5, 0, 0);
                } else {
                    final Drawable drawable = getResources().getDrawable(R.mipmap.write_evaluate);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mEditText.setCompoundDrawables(drawable, null, null, null);
                    ViewGroup.LayoutParams layoutParams = mEditText.getLayoutParams();
                    int i3 = ScreenStatusUtil.setDp(42, EvaluateActivity.this);
                    layoutParams.height = i3;
                    mEditText.setLayoutParams(layoutParams);
                    mEditText.setGravity(Gravity.CENTER_VERTICAL);
                    int i4 = ScreenStatusUtil.setDp(7, EvaluateActivity.this);
                    mEditText.setPadding(i4, 0, 0, 0);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mEditText.addTextChangedListener(textWatcher);
        mEditText.setOnTouchListener(new View.OnTouchListener() {
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
        initRlv();
    }

    private void initRlv() {
        selImageList = new ArrayList<>();
        mRlvPicture.setLayoutManager(new GridLayoutManager(this, 4));
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        imagePickerEvaluateAdapter = new ImagePickerEvaluateAdapter(this, imageItems, maxImgCount);
        mRlvPicture.setAdapter(imagePickerEvaluateAdapter);
        imagePickerEvaluateAdapter.setOnItemClickListener(this);
        imagePickerEvaluateAdapter.setImages(selImageList);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case POSTCOMMENT:
                Comment comment = (Comment) o;
                String result = comment.getResult();
                String code = comment.getCode();
                if("0".equals(code)){
                    ToastUtils.showShortToast(mActivity, result);
                    if ("提交成功".equals(result)) {
                        finish();
                    }
                }else{
                    ToastUtils.showShortToast(EvaluateActivity.this,comment.getMsg());
                }

                break;
        }
    }

    @Override
    public void showError(String error) {

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
                        Collections.swap(imagePickerEvaluateAdapter.mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(imagePickerEvaluateAdapter.mData, i, i - 1);
                    }
                }
                imagePickerEvaluateAdapter.notifyItemMoved(fromPosition, toPosition);
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
                imagePickerEvaluateAdapter.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
            }
        });
        helper.attachToRecyclerView(mRlvPicture);
    }

    private int anonymous = 0;
    private int star=0;
    @OnClick({R.id.iv_finis, R.id.publish, R.id.anonymity_picture, R.id.rela})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.publish:
                String s = mEditText.getText().toString();
                boolean checked = mAnonymityPicture.isChecked();
                if (checked) {
                    anonymous=1;
                }else {
                    anonymous=0;
                }
                ArrayList<File> files = new ArrayList<>();
                for (int i = 0; i < imagePickerEvaluateAdapter.mData.size()-1; i++) {
                    files.add(new File(imagePickerEvaluateAdapter.mData.get(i).path));
                }
                List<EvaluateBoolean> mBoolean = rlvAdapter_evaluate.mBoolean;
                for (int i = 0; i < mBoolean.size(); i++) {
                    Boolean aBoolean = mBoolean.get(i).getmBoolean();
                    if (aBoolean){
                        star=(i+1);
                    }
                }
                if(s.length()!=0&&star!=0){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("orderId", orderId);
                    map.put("summary", s);
                    map.put("anonymous", anonymous+"");
                    map.put("star", star+"");
                    map.put("files", files);
                    mPresenter.getDataP(map, DifferentiateEnum.POSTCOMMENT);
                }else if(s.length()!=0&&star==0){
                    ToastUtils.showShortToast(EvaluateActivity.this,"请点击选择星评！");
                }else if(s.length()==0&&star!=0){
                    ToastUtils.showShortToast(EvaluateActivity.this,"请填写评价！");
                }else {
                    ToastUtils.showShortToast(EvaluateActivity.this,"请选择星评及填写评价！");
                }

                break;
            case R.id.anonymity_picture:
                break;
            case R.id.rela:
                mEditText.setFocusable(true);
                mEditText.setFocusableInTouchMode(true);//设置触摸聚焦
                mEditText.requestFocus();//请求焦点
                mEditText.findFocus();//获取焦点
                //mEditText.performClick();
                showSoft(mEditText);
                break;
            default:
                break;
        }
    }
    //弹出软键盘
    private void showSoft(EditText editCommtent) {
        InputMethodManager inputMethodManager = (InputMethodManager) editCommtent.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
                                Intent intent1 = new Intent(EvaluateActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
                                ArrayList<ImageItem> imageItems = new ArrayList<>();
                                for (int i = 0; i < imagePickerEvaluateAdapter.mData.size() - 1; i++) {
                                    imageItems.add(imagePickerEvaluateAdapter.mData.get(i));
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
                                Intent intent = new Intent(EvaluateActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
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
                if (imagePickerEvaluateAdapter.isAdded) {
                    for (int i = 0; i < imagePickerEvaluateAdapter.mData.size() - 1; i++) {
                        imageItems.add(imagePickerEvaluateAdapter.mData.get(i));
                    }
                } else {
                    for (int i = 0; i < imagePickerEvaluateAdapter.mData.size(); i++) {
                        imageItems.add(imagePickerEvaluateAdapter.mData.get(i));
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
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    Log.i("yx=", "onActivityResult: " + selImageList.size());
                    imagePickerEvaluateAdapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    imagePickerEvaluateAdapter.setImages(selImageList);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
