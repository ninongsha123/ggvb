package com.tiancaijiazu.app.activitys.take;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.issue.widget.CropImageView;
import com.tiancaijiazu.app.activitys.issue.widget.GlideImageLoader;
import com.tiancaijiazu.app.activitys.issue.widget.ImageGridActivity;
import com.tiancaijiazu.app.activitys.issue.widget.ImageItem;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePicker;
import com.tiancaijiazu.app.activitys.issue.widget.ImagePreviewDelActivity;
import com.tiancaijiazu.app.activitys.issue.widget.SelectDialog;
import com.tiancaijiazu.app.activitys.take.adapters.RlvAdapter_Photo_Nine;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.PublishArticleBean;
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
import butterknife.OnClick;

public class NoteTakingActivity extends BaseActivity<IView, Presenter<IView>> implements IView, RlvAdapter_Photo_Nine.OnRecyclerViewItemClickListener {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.submit)
    TextView mSubmit; @BindView(R.id.a)
    TextView a;
    @BindView(R.id.relative)
    RelativeLayout mRelative;
    @BindView(R.id.edit_comm)
    EditText mEditComm;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.edit_sum)
    TextView mEditSum;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int REQUEST_CODE_CAMERA = 102;
    private RlvAdapter_Photo_Nine mRlvAdapterPhotoNine;
    private long contentsId;
    private long courseId;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        Intent intent = getIntent();
        contentsId = intent.getLongExtra("contentsId", 0);
        courseId = intent.getLongExtra("courseId", 0);
        initEdit();
        initRecylerView();
        initImagePicker();
        initDrag();
        mEditComm.setOnTouchListener(new View.OnTouchListener() {
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

    private void initDrag() {
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
                        Collections.swap(mRlvAdapterPhotoNine.mData, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mRlvAdapterPhotoNine.mData, i, i - 1);
                    }
                }
                mRlvAdapterPhotoNine.notifyItemMoved(fromPosition, toPosition);
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
                mRlvAdapterPhotoNine.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
            }
        });
        helper.attachToRecyclerView(mRecylerView);
    }

    private void initRecylerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        mRecylerView.setLayoutManager(gridLayoutManager);
        selImageList = new ArrayList<>();
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        mRlvAdapterPhotoNine = new RlvAdapter_Photo_Nine(imageItems, maxImgCount, this);
        mRecylerView.setAdapter(mRlvAdapterPhotoNine);
        mRlvAdapterPhotoNine.setOnItemClickListener(this);
        mRlvAdapterPhotoNine.setImages(selImageList);
    }

    private void initEdit() {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditSum.setText(s.toString().length() + "/2000");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mEditComm.addTextChangedListener(textWatcher);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_note_taking;
    }

    @Override
    public void showError(String error) {
        ToastUtils.showShortToast(NoteTakingActivity.this, error);
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum){
            case PUBLISHARTICLE:
                Intent intent = getIntent();
                PublishArticleBean publishArticleBean= (PublishArticleBean) o;
                String msg = publishArticleBean.getMsg();
                if (msg.equals("OK")){
                    setResult(200,intent);
                    ToastUtils.showShortToast(NoteTakingActivity.this,"发布成功");
                    finish();
                }else {
                    ToastUtils.showShortToast(NoteTakingActivity.this, msg);
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @OnClick({R.id.iv_finis, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
            case R.id.submit:
                String editData = mEditComm.getText().toString();
                ArrayList<File> files = new ArrayList<>();
                for (int i = 0; i < mRlvAdapterPhotoNine.mData.size()-1; i++) {
                    files.add(new File(mRlvAdapterPhotoNine.mData.get(i).path));
                }
                Log.d("bjg", "onViewClicked: "+files.size());
                if (editData.length() != 0&&files.size()!=0) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("title", "");
                    map.put("detail", editData);
                    map.put("files", files);
                    map.put("courseId", courseId+"");
                    map.put("contentsId",contentsId+"");
                    map.put("articleType", "2");
                    mPresenter.getDataP(map,DifferentiateEnum.PUBLISHARTICLE);
                }else if(editData.length() != 0&&files.size()==0){
                    ToastUtils.showShortToast(NoteTakingActivity.this,"请添加图片");
                }else if(editData.length()==0&&files.size()!=0){
                    ToastUtils.showShortToast(NoteTakingActivity.this,"请填写宝宝记录");
                }else {
                    ToastUtils.showShortToast(NoteTakingActivity.this,"请填写宝宝记录及添加图片！");
                }
                break;
        }
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
                                Intent intent1 = new Intent(NoteTakingActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
                                ArrayList<ImageItem> imageItems = new ArrayList<>();
                                for (int i = 0; i < mRlvAdapterPhotoNine.mData.size() - 1; i++) {
                                    imageItems.add(mRlvAdapterPhotoNine.mData.get(i));
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
                                Intent intent = new Intent(NoteTakingActivity.this, ImageGridActivity.class);
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
                if (mRlvAdapterPhotoNine.isAdded) {
                    for (int i = 0; i < mRlvAdapterPhotoNine.mData.size() - 1; i++) {
                        imageItems.add(mRlvAdapterPhotoNine.mData.get(i));
                    }
                } else {
                    for (int i = 0; i < mRlvAdapterPhotoNine.mData.size(); i++) {
                        imageItems.add(mRlvAdapterPhotoNine.mData.get(i));
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
                    mRlvAdapterPhotoNine.setImages(selImageList);
                }
            }else if (data != null && requestCode == REQUEST_CODE_CAMERA){
                selImageList.addAll(images);
                Log.i("yx=", "onActivityResult: " + selImageList.size());
                mRlvAdapterPhotoNine.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    mRlvAdapterPhotoNine.setImages(selImageList);
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
}
