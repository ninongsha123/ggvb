package com.tiancaijiazu.app.activitys.activitypage.collegeactivitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.model.SongInfo;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.adapters.RlvAdapter_details_img;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 学院-详情-课程目录-试听（条目最右边图片点击进入）
 */
public class DarftActivity extends BaseActivity<IView, Presenter<IView>> implements IView, OnPlayerEventListener {
    TimerTaskManager mTimerTask;
    MediaSessionConnection mMediaSessionConnection;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.startTime)
    TextView startTime;
    @BindView(R.id.seek)
    SeekBar seek;
    @BindView(R.id.endtime)
    TextView endtime;
    @BindView(R.id.pasue)
    CheckBox pasue;
    @BindView(R.id.l1near)
    RelativeLayout mL1near;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    private MusicManager mInstance;
    private ArrayList<SongInfo> mSongInfos;
    private String mBiao;

    @Override
    public void initEventAndData() {
        mTimerTask = new TimerTaskManager();
        mMediaSessionConnection = MediaSessionConnection.getInstance();
        mInstance = MusicManager.getInstance();
        //设置沉浸
        ScreenStatusUtil.setFillDip(this);
        initMusicPlay();
        Intent intent = getIntent();
        mBiao = intent.getStringExtra("biao");
        mSongInfos = new ArrayList<>();
        if ("wen".equals(mBiao)) {
            String courseId = intent.getStringExtra("courseId");
            String mediaUri = intent.getStringExtra("mediaUri");
            String picUri = intent.getStringExtra("picUri");
            String title = intent.getStringExtra("title");

            SongInfo songInfo = new SongInfo();
            songInfo.setSongId(courseId);
            songInfo.setSongUrl(mediaUri);
            songInfo.setSongCover(picUri);
            songInfo.setSongName(title);
            mSongInfos.add(songInfo);


        } else {
            List<SongInfo> playList = mInstance.getPlayList();
            mSongInfos.addAll(playList);
            mTimerTask.startToUpdateProgress();
        }
        String description = intent.getStringExtra("description");
        String[] split = description.split("[|]");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        RlvAdapter_details_img rlvAdapterDetailsImg = new RlvAdapter_details_img(split, this);
        mRecylerView.setAdapter(rlvAdapterDetailsImg);
        pasue.setChecked(true);
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_darft;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
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
        //连接音频服务
        mMediaSessionConnection.connect();
        super.onStart();
    }

    @Override
    protected void onDestroy() {

        //回收资源
        mInstance.removePlayerEventListener(this);
        if ("wen".equals(mBiao)) {
            mInstance.stopMusic();
            mTimerTask.removeUpdateProgressTask();
        }
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        //断开音频服务
        // mMediaSessionConnection.disconnect();
    }

    @Override
    public void onBackPressed() {
        if ("wen".equals(mBiao)) {
            MusicManager.getInstance().stopMusic();
            mTimerTask.removeUpdateProgressTask();
        }
        //回收资源
        MusicManager.getInstance().removePlayerEventListener(this);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onMusicSwitch(SongInfo songInfo) {
        if (songInfo == null) {
            return;
        }
    }

    @Override
    public void onPlayerStart() {
        pasue.setChecked(true);
        //开始更新进度条
        mTimerTask.startToUpdateProgress();
    }

    @Override
    public void onPlayerPause() {
        pasue.setChecked(false);
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPlayerStop() {
        //停止更新进度条
        mTimerTask.stopToUpdateProgress();
        seek.setProgress(0);
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

    private void initMusicPlay() {

        pasue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if ("wen".equals(mBiao)) {
                        mInstance.playMusic(mSongInfos, 0);
                    } else {
                        mInstance.playMusic();
                    }
                } else {
                    mInstance.pauseMusic();
                }
            }
        });

        //添加监听
        MusicManager.getInstance().addPlayerEventListener(this);
        //进度更新
        mTimerTask.setUpdateProgressTask(() -> {
            long position = MusicManager.getInstance().getPlayingPosition();
            long duration = MusicManager.getInstance().getDuration();
            long buffered = MusicManager.getInstance().getBufferedPosition();
            if (seek != null && startTime != null && endtime != null) {
                if (seek.getMax() != duration) {
                    seek.setMax((int) duration);
                }
                seek.setProgress((int) position);
                seek.setSecondaryProgress((int) buffered);
                startTime.setText(formatMusicTime(position));
                endtime.setText(formatMusicTime(duration));
            }

        });
        //进度条滑动
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                MusicManager.getInstance().seekTo(seekBar.getProgress());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //断开音频服务
                //mInstance.stopMusic();
                mInstance.removePlayerEventListener(DarftActivity.this);
                //mTimerTask.removeUpdateProgressTask();
                finish();
            }
        });
    }
}