package com.tiancaijiazu.app.activitys.activitypage.collegeactivitys;


import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.video.adapters.RlvAdapter_music_two_list;
import com.tiancaijiazu.app.activitys.video.bean.ContentsBean;
import com.tiancaijiazu.app.activitys.video.bean.VideoTwoExtractBean;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.VideoExtractBean;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.DownAudioBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

/**
 *
 * 学院-条目点击-课程目录-音频播放（购买页面）
 */

public class VideoListActivity extends BaseActivity<IView, Presenter<IView>> implements IView, OnPlayerEventListener {

    TimerTaskManager mTimerTask;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.speed)
    ImageView speed;
    @BindView(R.id.darft)
    ImageView darft;
    @BindView(R.id.really_sice)
    TextView reallySice;
    @BindView(R.id.no_money)
    TextView noMoney;
    @BindView(R.id.musiclist)
    ImageView musiclist;
    MediaSessionConnection mMediaSessionConnection;
    @BindView(R.id.title_music)
    TextView mTitleMusic;
    @BindView(R.id.l1near)
    RelativeLayout mL1near;
    @BindView(R.id.rl)
    RelativeLayout mRl;
    @BindView(R.id.circulation)
    CheckBox mCirculation;
    @BindView(R.id.linear)
    LinearLayout mLinear;
    @BindView(R.id.startTime)
    TextView mStartTime;
    @BindView(R.id.seek)
    SeekBar mSeek;
    @BindView(R.id.endTime)
    TextView mEndTime;
    @BindView(R.id.layout_time)
    RelativeLayout mLayoutTime;
    @BindView(R.id.last)
    ImageView mLast;
    @BindView(R.id.next)
    ImageView mNext;
    @BindView(R.id.pause)
    CheckBox mPause;
    @BindView(R.id.layout_bottom)
    RelativeLayout mLayoutBottom;
    @BindView(R.id.rl1)
    LinearLayout mRl1;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.music_down)
    ImageView mMusicDown;
    @BindView(R.id.gif_iv)
    GifImageView mGifIv;
    private int speed_flag = 1;
    private PopupWindow popupWindow;
    private View contentView;
    private TextView number;
    private MusicManager instance;
    private ArrayList<SongInfo> mSongInfos;
    private boolean isbo = true;
    private String mCourseId;
    private String mPicUri;
    private int mSize;
    private DownloadManager mDownloadManager;
    private CompleteReceiver completeReceiver;
    //下载任务
    String serviceString = Context.DOWNLOAD_SERVICE;
    long reference;
    private String mSongUrl = "";
    private String mSongName = "";
    private Animation mAnimation;
    private ArrayList<DownAudioBean> mDownAudioBeans;
    private boolean mBoolean;

    //下载的状态广播接收
    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //下载完成之后监听
            String action = intent.getAction();
            //下载完成的监听
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();


            }
            //点击通知栏，取消下载任务
            if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
                mDownloadManager.remove((Long) reference);
            }

        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);

        mDownloadManager = (DownloadManager) getBaseContext().getSystemService(serviceString);
        /** 注册下载监听的广播 **/
        completeReceiver = new CompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        /** register download success broadcast **/
        registerReceiver(completeReceiver,
                filter);

        PreUtils.putString("music", "kecheng");
        mTitleMusic.setSelected(true);
        Intent intent = getIntent();
        mCourseId = intent.getStringExtra("courseId");
        String mediaUri = intent.getStringExtra("mediaUri");
        mPicUri = intent.getStringExtra("picUri");
        String title = intent.getStringExtra("title");
        CollegeCourseBean.ResultBean result = (CollegeCourseBean.ResultBean) intent.getSerializableExtra("data");
        reallySice.setText("¥" + result.getCourseInfo().getPromoPrice() + "");
        noMoney.setText("¥" + result.getCourseInfo().getPrice() + "");
        noMoney.setAlpha(0.5f);
        //添加横线
        noMoney.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        int isBought = result.getCourseInfo().getIsBought();
        if (isBought == 0) {
            mRela.setVisibility(View.VISIBLE);
        } else {
            mRela.setVisibility(View.GONE);
        }
        List<CollegeCourseBean.ResultBean.ChapterListBean> chapterList = result.getChapterList();
        ArrayList<VideoExtractBean> videoExtractBeans = new ArrayList<>();
        ArrayList<VideoTwoExtractBean> videoTwoExtractBeans = new ArrayList<>();
        for (int j = 0; j < chapterList.size(); j++) {
            List<CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean> contentsList = chapterList.get(j).getContentsList();
            ArrayList<ContentsBean> contentsBeans = new ArrayList<>();
            for (int k = 0; k < contentsList.size(); k++) {
                int type = contentsList.get(k).getType();
                if (type == 2) {
                    //抽取出音频
                    CollegeCourseBean.ResultBean.ChapterListBean.ContentsListBean contentsListBean = contentsList.get(k);
                    int type1 = contentsListBean.getType();
                    int isFree = contentsListBean.getIsFree();
                    long chapterId = contentsListBean.getChapterId();
                    long contentsId = contentsListBean.getContentsId();
                    long courseId1 = contentsListBean.getCourseId();
                    String description = contentsListBean.getDescription();
                    int duration = contentsListBean.getDuration();
                    String mediaUri1 = contentsListBean.getMediaUri();
                    String picUri1 = contentsListBean.getPicUri();
                    String title1 = contentsListBean.getTitle();
                    contentsBeans.add(new ContentsBean(contentsId, title1, duration, chapterId, courseId1, type1, picUri1, mediaUri1, isFree, description));
                    videoExtractBeans.add(new VideoExtractBean(contentsId, title1, duration, chapterId, courseId1, type1, picUri1, mediaUri1, isFree, description));
                }
            }
            if (contentsBeans.size() != 0) {
                videoTwoExtractBeans.add(new VideoTwoExtractBean(chapterList.get(j).getTitle(), contentsBeans));
            }
        }

        mTitleMusic.setText(title);
        Glide.with(this).load(mPicUri).into(image);
        mMediaSessionConnection = MediaSessionConnection.getInstance();
        mTimerTask = new TimerTaskManager();
        instance = MusicManager.getInstance();
        mSongInfos = new ArrayList<>();
        mSize = videoExtractBeans.size();
        for (int i = 0; i < videoExtractBeans.size(); i++) {
            if (videoExtractBeans.get(i).getIsFree() == 1) {
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId((videoExtractBeans.get(i).getCourseId() + i) + "");
                songInfo.setSongUrl(videoExtractBeans.get(i).getMediaUri());
                Log.i("woyx", "initEventAndData: " + videoExtractBeans.get(i).getMediaUri());
                Log.i("woyx", "initEventAndData: " + videoExtractBeans.get(i).getCourseId());
                songInfo.setSongCover(videoExtractBeans.get(i).getPicUri());
                songInfo.setSongName(videoExtractBeans.get(i).getTitle());
                songInfo.setDescription(videoExtractBeans.get(i).getDescription());
                mSongInfos.add(songInfo);
            }
        }

        initMusicPlay();

        showPopwindow(videoTwoExtractBeans);
        mPause.setChecked(true);
        //mPresenter.getDataP(courseId, DifferentiateEnum.COURSEID);
        mTimerTask.startToUpdateProgress();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_video_list;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10) {
            //用户授予了权限
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授予权限成功", Toast.LENGTH_SHORT).show();
            } else {
                //权限被用户拒绝了，但是并没有选择不再提示，也就是说还可以继续申请
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    Toast.makeText(this, "请授予权限", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //连接音频服务
        mMediaSessionConnection.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //断开音频服务
        //mMediaSessionConnection.disconnect();
    }

    @Override
    public void onBackPressed() {
        //instance.stopMusic();
        //回收资源
        instance.removePlayerEventListener(this);
        //mTimerTask.removeUpdateProgressTask();
        //断开音频服务
        //mMediaSessionConnection.disconnect();
        super.onBackPressed();
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected void onDestroy() {
        if (completeReceiver != null) {
            unregisterReceiver(completeReceiver);
        }
        //instance.stopMusic();
        //回收资源
        instance.removePlayerEventListener(this);
        //mTimerTask.removeUpdateProgressTask();
        super.onDestroy();
    }


    SongInfo mSongInfo;
    @SuppressLint("SetTextI18n")
    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        if (songInfo == null) {
            return;
        }

        mSongInfo = songInfo;
        mSongName = songInfo.getSongName();
        String songCover = songInfo.getSongCover();
        mSongUrl = songInfo.getSongUrl();
        Log.i("woyx", "onMusicSwitch: " + mSongName);
        mTitleMusic.setText(mSongName);
        Glide.with(this).load(songCover).into(image);
    }

    @Override
    public void onPlayerStart() {
        instance.playMusic();
        image.startAnimation(mAnimation);
        mPause.setChecked(true);
        //开始更新进度条
        mTimerTask.startToUpdateProgress();
    }

    @Override
    public void onPlayerPause() {
        instance.pauseMusic();
        mPause.setChecked(false);
        image.clearAnimation();
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPlayerStop() {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        mSeek.setProgress(0);
        mStartTime.setText("00:00");
    }

    @Override
    public void onPlayCompletion(SongInfo songInfo) {
        //songInfo maybe null
        if (songInfo == null) {
            return;
        }
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @Override
    public void onBuffering() {

    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    public static String formatMusicTime(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((int) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case COURSEID:
              /*

                number.setText("内容列表(" + free_count_music + ")");*/
                break;
        }
    }


    private void initMusicPlay() {
        mAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        mAnimation.setInterpolator(lin);

        mPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    image.startAnimation(mAnimation);
                    if (isbo) {
                        for (int i = 0; i < mSongInfos.size(); i++) {
                            if (mPicUri.equals(mSongInfos.get(i).getSongCover())) {
                                instance.playMusic(mSongInfos, i);
                                break;
                            }
                        }
                        isbo = false;
                    } else {
                        instance.playMusic();
                    }
                } else {
                    image.clearAnimation();
                    instance.pauseMusic();
                }
            }
        });
        mLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.skipToPrevious();
                mPause.setChecked(true);
            }
        });
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.skipToNext();
                mPause.setChecked(true);
            }
        });

        //添加监听
        MusicManager.getInstance().addPlayerEventListener(this);
        //进度更新
        mTimerTask.setUpdateProgressTask(() -> {
            long position = instance.getPlayingPosition();
            long duration = instance.getDuration();
            long buffered = instance.getBufferedPosition();
            if (mSeek != null) {
                if (mSeek.getMax() != duration) {
                    mSeek.setMax((int) duration);
                }
                mSeek.setProgress((int) position);
                mSeek.setSecondaryProgress((int) buffered);
                mStartTime.setText(formatMusicTime(position));
                mEndTime.setText(formatMusicTime(duration));
            }

        });
        //进度条滑动
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                instance.seekTo(seekBar.getProgress());
            }
        });

        //设置播放状态
        mCirculation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    Toast.makeText(getApplicationContext(), "单曲循环", Toast.LENGTH_SHORT).show();
                    instance.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_ONE);
                } else {
                    Toast.makeText(getApplicationContext(), "顺序播放", Toast.LENGTH_SHORT).show();
                    instance.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_NONE);
                }
            }
        });
        //设置播放速度，每点击一次调快
        speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (speed_flag == 1) {
                    //设置成1.25倍速播放
                    instance.onDerailleur(true, (float) 1.25);
                    speed.setImageResource(R.mipmap.speed125);
                    speed_flag = 2;
                    Toast.makeText(VideoListActivity.this, "1.25倍速播放", Toast.LENGTH_SHORT).show();
                } else if (speed_flag == 2) {
                    instance.onDerailleur(true, (float) 1.5);
                    speed.setImageResource(R.mipmap.speed15);
                    speed_flag = 3;
                    Toast.makeText(VideoListActivity.this, "1.5倍速播放", Toast.LENGTH_SHORT).show();

                } else if (speed_flag == 3) {
                    instance.onDerailleur(false, (float) 1.0);
                    speed.setImageResource(R.mipmap.speed1);
                    speed_flag = 1;
                    Toast.makeText(VideoListActivity.this, "正常倍速播放", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //文稿
        darft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent in = new Intent(VideoListActivity.this, DarftActivity.class);
                int nowPlayingIndex = instance.getNowPlayingIndex();
                String description = mSongInfos.get(nowPlayingIndex).getDescription();
                in.putExtra("description", description);
                startActivity(in);
            }
        });

        //音乐列表
        musiclist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
            }
        });
    }


    @SuppressLint("NewApi")
    private void showPopwindow(ArrayList<VideoTwoExtractBean> videoTwoExtractBeans) {
        contentView = LayoutInflater.from(VideoListActivity.this).inflate(
                R.layout.music_list_video, null);

        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setFocusable(true);// 取得焦点
        popupWindow.setClippingEnabled(true);
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        number = contentView.findViewById(R.id.content_number);
        TextView tv = contentView.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        number.setText("内容列表(" + mSize + ")");
        RecyclerView recylerView = contentView.findViewById(R.id.recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoListActivity.this);
        recylerView.setLayoutManager(linearLayoutManager);

        RlvAdapter_music_two_list rlvAdapterMusicTwoList = new RlvAdapter_music_two_list(videoTwoExtractBeans, VideoListActivity.this);
        recylerView.setAdapter(rlvAdapterMusicTwoList);

        rlvAdapterMusicTwoList.setOnClickLisiter(new RlvAdapter_music_two_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ContentsBean> mData) {
                for (int i = 0; i < mSongInfos.size(); i++) {
                    if (mData.get(position).getPicUri().equals(mSongInfos.get(i).getSongCover())) {
                        instance.playMusic(mSongInfos, i);
                        break;
                    }
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.music_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                //断开音频服务
                /*instance.stopMusic();
                instance.removePlayerEventListener(VideoListActivity.this);
                mTimerTask.removeUpdateProgressTask();*/
                finish();
                break;
            case R.id.music_down:
                Log.i("yx555", "onViewClicked: "+mSongUrl);
                List<DownAudioBean> downAudioBeans = DataBaseMannger.getIntrance().selectDownAudio();
                for (int i = 0; i < downAudioBeans.size(); i++) {
                    String songName = downAudioBeans.get(i).getSongName();
                    if(songName.equals(mSongName)){
                        mBoolean = true;
                        break;
                    }
                }
                if(mBoolean){
                    ToastUtils.showShortToast(VideoListActivity.this,"已下载");
                }else {
                    if(!"".equals(mSongUrl)){
                        mDownAudioBeans = new ArrayList<>();
                        String path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath();
                        path = path + "/" + mSongName + ".mp3";
                        mDownAudioBeans.add(new DownAudioBean(null,mSongInfo.getSongId(),path,mSongInfo.getSongCover(),mSongInfo.getSongName(),mSongInfo.getDescription()));
                        DataBaseMannger.getIntrance().insertDownAudio(mDownAudioBeans);
                        Toast.makeText(this, "下载", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse(mSongUrl);
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        //通知栏的标题
                        request.setTitle("音频下载");
                        //显示通知栏的说明
                        request.setDescription("");
                        request.setVisibleInDownloadsUi(true);
                        //下载到那个文件夹下，以及命名
                        request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_MUSIC, mSongName + ".mp3");
                        //下载的唯一标识，可以用这个标识来控制这个下载的任务enqueue（）开始执行这个任务
                        reference = mDownloadManager.enqueue(request);
                    }
                }

                break;
        }
    }
}