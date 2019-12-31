package com.tiancaijiazu.app.activitys.activitypage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shortvideo.base.BaseRecAdapter;
import com.tiancaijiazu.app.activitys.shortvideo.base.BaseRecViewHolder;
import com.tiancaijiazu.app.activitys.shortvideo.widget.MyVideoPlayer;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.MinVideoBean;
import com.tiancaijiazu.app.beans.ShortCollectBean;
import com.tiancaijiazu.app.beans.ShortCollectListBean;
import com.tiancaijiazu.app.beans.ShortIsCollectBean;
import com.tiancaijiazu.app.beans.ShortVideoBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.globals.WechatShareTool;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class ShortVideoActivity extends BaseActivity<IView,Presenter<IView>> implements IView{

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;

    private ListVideoAdapter mAdapter;
    private ViewPagerLayoutManager mLayoutManager;
    private PagerSnapHelper mSnapHelper;
    private List<ShortVideoBean.ResultBean.ItemListBean> urlList;
    private int page = 1;
    private int mPosition;
    private ArrayList<MinVideoBean.ResultBean> mMData;
    private List<ShortVideoBean.ResultBean.ItemListBean> mMItemList3;
    private ListVideoAdapterTwo mListVideoAdapterTwo;
    private String mLargePicUri = "";
    private String mVideoUri;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            String referrerCode = PreUtils.getString("referrerCode", "");
            switch (msg.arg1) {
                case 1:
                    WechatShareTool.shareToWXUrl("http://m.h5.tiancaijiazu.com/invite/vivdoshare.html"+"?videoUrl="+mVideoUri+"&picUrl="+mLargePicUri+"&title="+mTitle+"&desc="+mSummary+"&referrerCode="+referrerCode, bitmap, mTitle, true,mSummary,true);
                    break;
                case 2:
                    WechatShareTool.shareToWXUrl("http://m.h5.tiancaijiazu.com/invite/vivdoshare.html"+"?videoUrl="+mVideoUri+"&picUrl="+mLargePicUri+"&title="+mTitle+"&desc="+mSummary+"&referrerCode="+referrerCode, bitmap, mTitle, false,mSummary,true);
                    break;
            }

        }
    };
    private String mTitle;
    private String mSummary;
    private String mBiao;
    private ListVideoAdapterCollect mListVideoAdapterCollect;
    private List<ShortCollectListBean.ResultBean> mMData1;

    @Override
    protected void initEventAndData() {
        initSett(this);
        initView();
        addListener();
    }
    //设置状态栏与状态栏字体颜色
    public static void initSett(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            //| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_short_video;
    }
    private void initView() {
        urlList = new ArrayList<>();
        Intent intent = getIntent();
        mPosition = intent.getIntExtra("position", 0);
        mBiao = intent.getStringExtra("biao");
        if("lei".equals(mBiao)){
            mMData = (ArrayList<MinVideoBean.ResultBean>) intent.getSerializableExtra("data");
            //mAdapter.setNewData(mMData);
            mSnapHelper = new PagerSnapHelper();
       /* if(mRecyclerView.getOnFlingListener() == null){
            mRecyclerView.setOnFlingListener(null);
            mSnapHelper.attachToRecyclerView(mRecyclerView);
        }*/

            mLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mAdapter = new ListVideoAdapter(mMData,0);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            if(mRecyclerView!=null){
                mRecyclerView.scrollToPosition(mPosition);
            }
            mPresenter.getDataP(mMData.get(mPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
        }else if("home".equals(mBiao)){
            mMItemList3 = (List<ShortVideoBean.ResultBean.ItemListBean>) intent.getSerializableExtra("data");
            mSnapHelper = new PagerSnapHelper();
       /* if(mRecyclerView.getOnFlingListener() == null){
            mRecyclerView.setOnFlingListener(null);
            mSnapHelper.attachToRecyclerView(mRecyclerView);
        }*/

            mLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mListVideoAdapterTwo = new ListVideoAdapterTwo(mMItemList3,0);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mListVideoAdapterTwo);

            if(mRecyclerView!=null){
                mRecyclerView.scrollToPosition(mPosition);
            }
            mPresenter.getDataP(mMItemList3.get(mPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
        }else if("collect".equals(mBiao)){
            mMData1 = (List<ShortCollectListBean.ResultBean>) intent.getSerializableExtra("data");
            mSnapHelper = new PagerSnapHelper();
            mLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mListVideoAdapterCollect = new ListVideoAdapterCollect(mMData1,0);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mListVideoAdapterCollect);

            if(mRecyclerView!=null){
                mRecyclerView.scrollToPosition(mPosition);
            }
            mPresenter.getDataP(mMData1.get(mPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
        }
        initPop();
    }
    private void addListener() {

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = mSnapHelper.findSnapView(mLayoutManager);
                        JZVideoPlayer.releaseAllVideos();
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof VideoViewHolder) {
                            ((VideoViewHolder) viewHolder).mp_video.startVideo();
                        }
                        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                        Log.i("yxVideo", "onScrollStateChanged: "+firstVisibleItemPosition);
                        if("lei".equals(mBiao)){
                            mPresenter.getDataP(mMData.get(firstVisibleItemPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
                        }else if("home".equals(mBiao)){
                            mPresenter.getDataP(mMItemList3.get(firstVisibleItemPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
                        }else if("collect".equals(mBiao)){
                            mPresenter.getDataP(mMData1.get(firstVisibleItemPosition).getVideoId()+"",DifferentiateEnum.SHORTISCOLLECT);
                        }

                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        Log.i("yxVideo", "onScrollStateChanged: +拖动");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动:
                        Log.i("yxVideo", "onScrollStateChanged: 惯性滑动");
                        break;
                }

            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case SHORTISCOLLECT:
                ShortIsCollectBean shortIsCollectBean = (ShortIsCollectBean) o;
                int result = shortIsCollectBean.getResult();
                if("lei".equals(mBiao)){
                    mAdapter.addUp(result);
                }else if("home".equals(mBiao)){
                    Log.i("yxVideo", "show: "+result);
                    mListVideoAdapterTwo.addUp(result);
                }else if("collect".equals(mBiao)){
                    mListVideoAdapterCollect.addUp(result);
                }
                break;
            case SHORTCOLLECT:
                ShortCollectBean shortCollectBean = (ShortCollectBean) o;
                String code = shortCollectBean.getCode();
                if("0".equals(code)){
                    ToastUtils.showShortToast(ShortVideoActivity.this,shortCollectBean.getResult());
                }
                break;
        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    class ListVideoAdapterCollect extends BaseRecAdapter<ShortCollectListBean.ResultBean, VideoViewHolder> {

        private int mIsCollect;

        public ListVideoAdapterCollect(List<ShortCollectListBean.ResultBean> list, int  b) {
            super(list);
            this.mIsCollect = b;
        }

        @Override
        public void onHolder(VideoViewHolder holder, ShortCollectListBean.ResultBean bean, int position) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.mp_video.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
            holder.mp_video.setUp(bean.getVideoUri(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            holder.mp_video.iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            if(mIsCollect ==0){
                Log.i("yxVideo", "onHolder: "+mIsCollect);
                holder.collect.setChecked(false);
            }else{
                holder.collect.setChecked(true);
            }
            holder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getDataP(bean.getVideoId()+"",DifferentiateEnum.SHORTCOLLECT);
                }
            });
            holder.mp_video.mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("sdf", "onClick: "+"asd");
                    /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    WechatShareTool.shareVideo("测试title","测试副标题",bean.getVideoUri(),bitmap,false);*/
                    mLargePicUri = bean.getLargePicUri();
                    Log.i("", "onClick: "+mLargePicUri);
                    mVideoUri = bean.getVideoUri();
                    mTitle = bean.getTitle();
                    mSummary = bean.getSummary();
                    mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                }
            });
            if (position == mPosition) {
                holder.mp_video.startVideo();
            }
            holder.mp_video.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(bean.getLargePicUri()).into(holder.mp_video.thumbImageView);
            holder.tv_title.setText(bean.getTitle());
        }

        @Override
        public VideoViewHolder onCreateHolder() {
            return new VideoViewHolder(getViewByRes(R.layout.item_page2));

        }

        public void addUp(int b) {
            this.mIsCollect = b;
            notifyDataSetChanged();
        }
    }


    class ListVideoAdapterTwo extends BaseRecAdapter<ShortVideoBean.ResultBean.ItemListBean, VideoViewHolder> {

        private int mIsCollect;

        public ListVideoAdapterTwo(List<ShortVideoBean.ResultBean.ItemListBean> list, int  b) {
            super(list);
            this.mIsCollect = b;
        }

        @Override
        public void onHolder(VideoViewHolder holder, ShortVideoBean.ResultBean.ItemListBean bean, int position) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.mp_video.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
            holder.mp_video.setUp(bean.getVideoUri(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            holder.mp_video.iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            if(mIsCollect ==0){
                Log.i("yxVideo", "onHolder: "+mIsCollect);
                holder.collect.setChecked(false);
            }else{
                holder.collect.setChecked(true);
            }
            holder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getDataP(bean.getVideoId()+"",DifferentiateEnum.SHORTCOLLECT);
                }
            });
            holder.mp_video.mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("sdf", "onClick: "+"asd");
                    /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    WechatShareTool.shareVideo("测试title","测试副标题",bean.getVideoUri(),bitmap,false);*/
                    mLargePicUri = bean.getLargePicUri();
                    Log.i("", "onClick: "+mLargePicUri);
                    mVideoUri = bean.getVideoUri();
                    mTitle = bean.getTitle();
                    mSummary = bean.getSummary();
                    mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                }
            });
            if (position == mPosition) {
                holder.mp_video.startVideo();
            }
            holder.mp_video.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(bean.getLargePicUri()).into(holder.mp_video.thumbImageView);
            holder.tv_title.setText(bean.getTitle());
        }

        @Override
        public VideoViewHolder onCreateHolder() {
            return new VideoViewHolder(getViewByRes(R.layout.item_page2));

        }

        public void addUp(int b) {
            this.mIsCollect = b;
            notifyDataSetChanged();
        }
    }



    class ListVideoAdapter extends BaseRecAdapter<MinVideoBean.ResultBean, VideoViewHolder> {

        private int mIsCollect;

        public ListVideoAdapter(ArrayList<MinVideoBean.ResultBean> list, int b) {
            super(list);
            this.mIsCollect = b;
        }

        @Override
        public void onHolder(VideoViewHolder holder, MinVideoBean.ResultBean bean, int position) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.mp_video.setVideoImageDisplayType(JZVideoPlayer.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
            holder.mp_video.setUp(bean.getVideoUri(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            holder.mp_video.iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            Log.i("yxVideo", "onHolder: "+bean.getVideoId());
            if(mIsCollect ==0){
                Log.i("yxVideo", "onHolder: "+mIsCollect);
                holder.collect.setChecked(false);
            }else{
                holder.collect.setChecked(true);
            }
            holder.collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.getDataP(bean.getVideoId()+"",DifferentiateEnum.SHORTCOLLECT);
                }
            });
            holder.mp_video.mShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("sdf", "onClick: "+"asd");
                    /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.geniusfamily);
                    WechatShareTool.shareVideo("测试title","测试副标题",bean.getVideoUri(),bitmap,false);*/
                    mLargePicUri = bean.getLargePicUri();
                    mVideoUri = bean.getVideoUri();
                    mTitle = bean.getTitle();
                    mSummary = bean.getSummary();
                    mPopupWindow1.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
                }
            });
            if (position == mPosition) {
                holder.mp_video.startVideo();
            }
            holder.mp_video.thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(context).load(bean.getLargePicUri()).into(holder.mp_video.thumbImageView);
            holder.tv_title.setText(bean.getTitle());
        }

        @Override
        public VideoViewHolder onCreateHolder() {
            return new VideoViewHolder(getViewByRes(R.layout.item_page2));

        }

        public void addUp(int result) {
            this.mIsCollect = result;
            notifyDataSetChanged();
        }
    }

    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootView;
        public MyVideoPlayer mp_video;
        public TextView tv_title;
        public CheckBox collect;

        public VideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.mp_video);
            this.tv_title = rootView.findViewById(R.id.tv_title);
            this.collect = rootView.findViewById(R.id.collect);
        }

    }
    private View mInflate;
    private PopupWindow mPopupWindow1;

    public void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.pop_share_layout, null);
        mPopupWindow1 = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow1.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        mPopupWindow1.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        mPopupWindow1.setOutsideTouchable(true);
        //设置可以点击
        mPopupWindow1.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        mPopupWindow1.setAnimationStyle(R.style.popwin_anim_style);

        ImageView shareWxFriend = mInflate.findViewById(R.id.share_wx_friend);
        shareWxFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(mLargePicUri)){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mLargePicUri).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=1;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

            }
        });
        ImageView shareWx = mInflate.findViewById(R.id.share_wx);
        shareWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!"".equals(mLargePicUri)){
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                Bitmap thumb = BitmapFactory.decodeStream(new URL(mLargePicUri).openStream());
                                Message obtain = Message.obtain();
                                obtain.obj = thumb;
                                obtain.arg1=2;
                                handler.sendMessage(obtain);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                }

                }

        });
        TextView quxiao = mInflate.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow1.dismiss();
            }
        });
    }
}
