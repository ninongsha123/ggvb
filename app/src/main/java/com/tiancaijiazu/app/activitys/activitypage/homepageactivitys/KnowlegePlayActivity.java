package com.tiancaijiazu.app.activitys.activitypage.homepageactivitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter_audio_list;
import com.tiancaijiazu.app.activitys.musiclrc.LrcView;
import com.tiancaijiazu.app.activitys.musiclrc.LyricContent;
import com.tiancaijiazu.app.activitys.musiclrc.impl.HttpDownloader;
import com.tiancaijiazu.app.activitys.musiclrc.impl.LrcRead;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.beans.SongBean;
import com.tiancaijiazu.app.dao.DownAudioBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.cache.FileMusicUtils;
import com.tiancaijiazu.app.mvp.IView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *  首页-家族小电台-音乐播放界面
 *
 */

public class KnowlegePlayActivity extends BaseActivity<IView, Presenter<IView>> implements IView, OnPlayerEventListener {

    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.disk)
    RelativeLayout disk;
    @BindView(R.id.geci_text)
    LrcView geciText;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.seek)
    SeekBar mSeekBar;
    @BindView(R.id.endTime)
    TextView endTime;
    @BindView(R.id.layout_time)
    RelativeLayout layoutTime;
    @BindView(R.id.bofang)
    CheckBox bofang;
    @BindView(R.id.last)
    ImageView last;
    @BindView(R.id.next)
    ImageView next;
    @BindView(R.id.liebiao)
    ImageView liebiao;
    @BindView(R.id.circulation)
    CheckBox circulation;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.an_jian)
    RelativeLayout mAnJian;
    @BindView(R.id.title)
    TextView mTitle;

    private MusicManager instance;
    private MediaSessionConnection mMediaSessionConnection;
    private TimerTaskManager mTimerTask;
    private boolean isbo = true;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    LrcRead lrcRead = new LrcRead();
                    try {
                        lrcRead.Read(Environment.getExternalStorageDirectory() + "/Music/lrc");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    List<LyricContent> lyricContents = lrcRead.GetLyricContent();
                    if (!KnowlegePlayActivity.this.isFinishing()) {
                        RequestOptions options = new RequestOptions();
                        options.dontAnimate().placeholder(image.getDrawable());
                        Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(position).getSongCover()).apply(options).into(image);
                        Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(position).getSongCover()).apply(options).into(mIvBack);
                    }
                    geciText.setmLrcList(lyricContents);
                    break;
                case 2:
                    if (!KnowlegePlayActivity.this.isFinishing()) {
                        RequestOptions options = new RequestOptions();
                        options.dontAnimate().placeholder(image.getDrawable());
                        Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(position).getSongCover()).apply(options).into(image);
                        Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(position).getSongCover()).apply(options).into(mIvBack);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    private ArrayList<SongInfo> mSongInfos;
    private int mIndex = 0;
    private FileMusicUtils mFileMusicUtils;
    private int position = 0;
    private String mNowPlayingSongId;
    private String mBiao;
    private PopupWindow mPopupWindow;
    private RlvAdapter_audio_list mRlvAdapterAudioList;
    private View mInflate;
    private int mPosition;

    @Override
    protected void initEventAndData() {
        //沉浸式状态栏
        initSett();
        mFileMusicUtils = new FileMusicUtils();
        /*boolean fileExist = mFileMusicUtils.isFileExist(Environment.getExternalStorageDirectory() + "/Music/lrc");
        if (fileExist) {
            // 找到文件所在的路径并删除该文件
            File file = new File(Environment.getExternalStorageDirectory(), "/Music/lrc");
            file.delete();
        }*/
        mMediaSessionConnection = MediaSessionConnection.getInstance();
        initPop();
        mSongInfos = new ArrayList<>();
        mTimerTask = new TimerTaskManager();
        instance = MusicManager.getInstance();
        Intent intent = getIntent();
        mBiao = intent.getStringExtra("biao");
        String musicId = intent.getStringExtra("musicId");

        //音乐播放逻辑
        initPlay();
        //点击事件
        initClick();
        if ("home".equals(mBiao)) {
            mIndex = 100;
            String songName = intent.getStringExtra("songName");
            mTitle.setText(songName);
            int nowPlayingIndex = instance.getNowPlayingIndex();
            mRlvAdapterAudioList.addPos(nowPlayingIndex);
            List<SongInfo> playList = instance.getPlayList();
            mSongInfos.addAll(playList);
            new MyThread(1, nowPlayingIndex).start();
            boolean playing = instance.isPlaying();
            if (playing) {
                bofang.setChecked(true);
            } else {
                isbo = false;
            }
            mTimerTask.startToUpdateProgress();
            mRlvAdapterAudioList.addData(mSongInfos);
        } else if("down".equals(mBiao)){
            List<DownAudioBean> mDataDown = (List<DownAudioBean>) intent.getSerializableExtra("audio");
            mPosition = intent.getIntExtra("position", 0);
            mIndex = 100;
            String songName = mDataDown.get(mPosition).getSongName();
            mTitle.setText(songName);
            mRlvAdapterAudioList.addPos(mPosition);
            List<SongInfo> songInfos = new ArrayList<>();
            for (int i = 0; i < mDataDown.size(); i++) {
                SongInfo songInfo = new SongInfo();
                songInfo.setSongId(mDataDown.get(i).getSongId());
                songInfo.setSongName(mDataDown.get(i).getSongName());
                songInfo.setSongCover(mDataDown.get(i).getSongCover());
                songInfo.setSongUrl(mDataDown.get(i).getSongUrl());
                songInfo.setDescription(mDataDown.get(i).getDescription());
                songInfos.add(songInfo);
            }
            mSongInfos.addAll(songInfos);
            //new MyThread(1, nowPlayingIndex).start();

            bofang.setChecked(true);
            RequestOptions options = new RequestOptions();
            options.dontAnimate().placeholder(image.getDrawable());
            Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(mPosition).getSongCover()).apply(options).into(image);
            Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(mPosition).getSongCover()).apply(options).into(mIvBack);
            mTimerTask.startToUpdateProgress();
            mRlvAdapterAudioList.addData(mSongInfos);
        }else {
            Log.i("yx159", "initEventAndData: " + musicId);

            mPresenter.getDataP(musicId + "", DifferentiateEnum.DIANSONG);
        }
    }

    private void initPop() {
        mInflate = LayoutInflater.from(this).inflate(R.layout.item_audiolist_layout, null);
        mPopupWindow = new PopupWindow(mInflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, false);
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

        TextView tv = mInflate.findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
            }
        });

        RecyclerView recylerview = mInflate.findViewById(R.id.recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recylerview.setLayoutManager(linearLayoutManager);
        List<SongInfo> songInfos = new ArrayList<>();
        mRlvAdapterAudioList = new RlvAdapter_audio_list(songInfos, this);
        recylerview.setAdapter(mRlvAdapterAudioList);


        mRlvAdapterAudioList.setOnClickLisiter(new RlvAdapter_audio_list.onClickLisiter() {
            @Override
            public void onClicker(View view, int position) {
                instance.playMusic(mSongInfos, position);
                if("down".equals(mBiao)){

                }else {
                    new MyThread(1, position).start();
                }
                bofang.setChecked(true);
            }
        });
    }

    class MyThread extends Thread {

        private int index;
        private int flag = 0;

        public MyThread(int flag, int index) {
            this.flag = flag;
            this.index = index;
            position = index;
        }

        @Override
        public void run() {
            if (1 == flag) { // 基本文件下载
                boolean fileExist = mFileMusicUtils.isFileExist(Environment.getExternalStorageDirectory() + "/music/lrc");
                if (fileExist) {
                    // 找到文件所在的路径并删除该文件
                    File file = new File(Environment.getExternalStorageDirectory(), "/music/lrc");
                    file.delete();
                }

                HttpDownloader httpDownloader = new HttpDownloader();
                String downloadUrl = mSongInfos.get(index).getDownloadUrl();
                Log.i("yx456", "run: " + downloadUrl);
                if (!"".equals(downloadUrl)) {
                    String contents = httpDownloader.downloadBaseFile(downloadUrl);
                    Log.i("fff", "run: " + mSongInfos.get(index).getDownloadUrl());
                    try {
                        File file = new File(Environment.getExternalStorageDirectory(),
                                "/music/lrc");
                        //第二个参数意义是说是否以append方式添加内容
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                        bw.write(contents);
                        bw.flush();
                        bw.close();
                        System.out.println("写入成功");
                        Message msg = Message.obtain();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    Message msg = Message.obtain();
                    msg.what = 2;
                    handler.sendMessage(msg);
                }

            }

        }
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_knowlege_play;
    }

    //设置状态栏与状态栏字体颜色
    private void initSett() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR//设置显示为白色背景，黑色字体
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
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
                    Toast.makeText(this, "没有获取到权限", Toast.LENGTH_SHORT).show();
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
        super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        if (songInfo == null) {
            return;
        }
        int nowPlayingIndex = instance.getNowPlayingIndex();
        String songName = songInfo.getSongName();
        mTitle.setText(songName);
        Log.i("ffff", "onMusicSwitch: " + nowPlayingIndex);
        if("".equals(mBiao)){
            RequestOptions options = new RequestOptions();
            options.dontAnimate().placeholder(image.getDrawable());
            Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(nowPlayingIndex).getSongCover()).apply(options).into(image);
            Glide.with(KnowlegePlayActivity.this).load(mSongInfos.get(nowPlayingIndex).getSongCover()).apply(options).into(mIvBack);
            mRlvAdapterAudioList.addPos(nowPlayingIndex);
        }else {
            if (mIndex != nowPlayingIndex) {
                mIndex = nowPlayingIndex;
                mRlvAdapterAudioList.addPos(nowPlayingIndex);
                new MyThread(1, nowPlayingIndex).start();
                Log.i("yx", "onMusicSwitch: " + nowPlayingIndex);
            }
        }

    }

    @Override
    public void onPlayerStart() {
        //开始更新进度条
        mTimerTask.startToUpdateProgress();
    }

    @Override
    public void onPlayerPause() {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPlayerStop() {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        mSeekBar.setProgress(0);
        startTime.setText("00:00");
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

    @Override
    protected void onDestroy() {
        instance.removePlayerEventListener(this);
        super.onDestroy();
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


    //音乐播放逻辑
    private void initPlay() {

        final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        bofang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreUtils.putString("music", "know");
                if ("home".equals(mBiao)) {

                    if (isChecked == true) {
                        if (isbo) {
                            isbo = false;
                        } else {
                            instance.playMusic();
                        }
                        image.startAnimation(animation);
                    } else {
                        instance.pauseMusic();
                        image.clearAnimation();
                    }

                } else if("down".equals(mBiao)){
                    if (isChecked == true) {
                        if (isbo) {
                            if (mSongInfos.size() != 0) {
                                instance.playMusic(mSongInfos, mPosition);
                                mTimerTask.startToUpdateProgress();
                                isbo = false;
                            }
                        } else {
                            instance.playMusic();
                        }
                        image.startAnimation(animation);
                    } else {
                        if (mSongInfos.size() != 0) {
                            instance.pauseMusic();
                        }
                        image.clearAnimation();
                    }
                }else {
                    if (isChecked == true) {
                        if (isbo) {
                            if (mSongInfos.size() != 0) {
                                instance.playMusic(mSongInfos, mIndex);
                                mTimerTask.startToUpdateProgress();
                                isbo = false;
                            }
                        } else {
                            instance.playMusic();
                        }
                        image.startAnimation(animation);
                    } else {
                        if (mSongInfos.size() != 0) {
                            instance.pauseMusic();
                        }
                        image.clearAnimation();
                    }
                }

            }
        });
        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.skipToPrevious();
                bofang.setChecked(true);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("home".equals(mBiao)) {
                    instance.skipToNext();
                    bofang.setChecked(true);
                } else {
                    if (isbo) {
                        if (mSongInfos.size() > 1) {
                            new MyThread(1, 1).start();
                            mIndex = 1;
                            mTimerTask.startToUpdateProgress();
                        }
                    } else {
                        instance.skipToNext();
                    }
                    bofang.setChecked(true);
                }
                bofang.setChecked(true);
            }
        });
        //添加监听
        MusicManager.getInstance().addPlayerEventListener(this);
        //进度更新
        if (mTimerTask != null) {
            mTimerTask.setUpdateProgressTask(() -> {
                long position = instance.getPlayingPosition();
                long duration = instance.getDuration();
                long buffered = instance.getBufferedPosition();
                if (mSeekBar != null && startTime != null && endTime != null) {

                    if (mSeekBar.getMax() != duration) {
                        mSeekBar.setMax((int) duration);
                    }
                    geciText.setIndex(lrcIndex());
                    mSeekBar.setProgress((int) position);
                    mSeekBar.setSecondaryProgress((int) buffered);
                    startTime.setText(formatMusicTime(position));
                    endTime.setText(formatMusicTime(duration));
                } else {
                    mSeekBar = findViewById(R.id.seek);
                    geciText = findViewById(R.id.geci_text);
                    startTime = findViewById(R.id.startTime);
                    endTime = findViewById(R.id.endTime);
                    if (mSeekBar.getMax() != duration) {
                        mSeekBar.setMax((int) duration);
                    }
                    geciText.setIndex(lrcIndex());
                    mSeekBar.setProgress((int) position);
                    mSeekBar.setSecondaryProgress((int) buffered);
                    startTime.setText(formatMusicTime(position));
                    endTime.setText(formatMusicTime(duration));
                }

            });
        }

        //进度条滑动
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        circulation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        //列表
        liebiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.showAtLocation(mInflate, Gravity.BOTTOM, 0, 0);
            }
        });

    }

    //点击事件
    private void initClick() {
        ivFinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case DIANSONG:
                SongBean songBean = (SongBean) o;
                List<SongBean.ResultBean.ItemListBean> itemList = songBean.getResult().getItemList();
                if (mSongInfos != null) {
                    mSongInfos.clear();
                }

                mTitle.setText(itemList.get(0).getTitle());
                for (int i = 0; i < itemList.size(); i++) {
                    SongInfo s2 = new SongInfo();
                    s2.setSongId(itemList.get(i).getItemId() + "");
                    s2.setSongUrl(itemList.get(i).getMediaUri());
                    s2.setSongCover(itemList.get(i).getPicUri());
                    s2.setSongName(itemList.get(i).getTitle());
                    s2.setDownloadUrl(itemList.get(i).getLyricUri());
                    mSongInfos.add(s2);
                }
                if (mSongInfos.size() == itemList.size()) {
                    new MyThread(1, 0).start();
                }
                mRlvAdapterAudioList.addData(mSongInfos);
                break;
        }
    }

    long currentTime;
    long duration;
    int index;

    /**
     * 根据时间获取歌词显示的索引值
     *
     * @return
     */
    public int lrcIndex() {
        if (instance.isPlaying()) {
            currentTime = instance.getPlayingPosition();
            duration = instance.getDuration();
        }
        if (currentTime < duration) {
            for (int i = 0; i < geciText.mLrcList.size(); i++) {
                if (i < geciText.mLrcList.size() - 1) {
                    if (currentTime < geciText.mLrcList.get(i).getLyricTime() && i == 0) {
                        index = i;
                    }
                    if (currentTime > geciText.mLrcList.get(i).getLyricTime()
                            && currentTime < geciText.mLrcList.get(i + 1).getLyricTime()) {
                        index = i;
                    }
                }
                if (i == geciText.mLrcList.size() - 1
                        && currentTime > geciText.mLrcList.get(i).getLyricTime()) {
                    index = i;
                }
            }
        }
        return index;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }
}