package com.tiancaijiazu.app.utils.video.systems;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;


import com.tiancaijiazu.app.utils.video.JZMediaInterfaceList;
import com.tiancaijiazu.app.utils.video.JZMediaManagerList;
import com.tiancaijiazu.app.utils.video.JZVideoPlayerManagerList;



import java.lang.reflect.Method;
import java.util.Map;


/**
 * Created by wapchief on 2018/1/22.
 */

public class MyJZMediaSystemList extends JZMediaInterfaceList
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnVideoSizeChangedListener {

    public MediaPlayer mediaPlayer;
    //播放速度，默认1
    public float speeding=1f;


    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void prepare() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            if (dataSourceObjects.length > 1) {
                mediaPlayer.setLooping((boolean) dataSourceObjects[1]);
            }
            mediaPlayer.setOnPreparedListener(MyJZMediaSystemList.this);
            mediaPlayer.setOnCompletionListener(MyJZMediaSystemList.this);
            mediaPlayer.setOnBufferingUpdateListener(MyJZMediaSystemList.this);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.setOnSeekCompleteListener(MyJZMediaSystemList.this);
            mediaPlayer.setOnErrorListener(MyJZMediaSystemList.this);
            mediaPlayer.setOnInfoListener(MyJZMediaSystemList.this);
            mediaPlayer.setOnVideoSizeChangedListener(MyJZMediaSystemList.this);
//            mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(getSpeeding()));
            Class<MediaPlayer> clazz = MediaPlayer.class;
            Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
            if (dataSourceObjects.length > 2) {
                method.invoke(mediaPlayer, currentDataSource.toString(), dataSourceObjects[2]);
            } else {
                method.invoke(mediaPlayer, currentDataSource.toString(), null);
            }
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(long time) {
        mediaPlayer.seekTo((int) time);
    }

    @Override
    public void release() {
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    @Override
    public long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void setSurface(Surface surface) {
        mediaPlayer.setSurface(surface);
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {

    }

    /**视频初始化完成回调*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        //设置倍速,5.0以下不支持，会抛异常
        try {
            mediaPlayer.setPlaybackParams(new PlaybackParams().setSpeed(getSpeeding()));
        }catch (NoClassDefFoundError e){

        }
        //播放
        mediaPlayer.start();
        if (currentDataSource.toString().toLowerCase().contains("mp3")) {
            JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                        JZVideoPlayerManagerList.getCurrentJzvd().onPrepared();
                    }
                }
            });
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    JZVideoPlayerManagerList.getCurrentJzvd().onAutoCompletion();
                }
            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int percent) {
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    JZVideoPlayerManagerList.getCurrentJzvd().setBufferProgress(percent);
                }
            }
        });
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    JZVideoPlayerManagerList.getCurrentJzvd().onSeekComplete();
                }
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, final int what, final int extra) {
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    JZVideoPlayerManagerList.getCurrentJzvd().onError(what, extra);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, final int what, final int extra) {
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        JZVideoPlayerManagerList.getCurrentJzvd().onPrepared();
                    } else {
                        JZVideoPlayerManagerList.getCurrentJzvd().onInfo(what, extra);
                    }
                }
            }
        });
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        JZMediaManagerList.instance().currentVideoWidth = width;
        JZMediaManagerList.instance().currentVideoHeight = height;
        JZMediaManagerList.instance().mainThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (JZVideoPlayerManagerList.getCurrentJzvd() != null) {
                    JZVideoPlayerManagerList.getCurrentJzvd().onVideoSizeChanged();
                }
            }
        });
    }


    public float getSpeeding() {
        return speeding;
    }

    public void setSpeeding(float speeding) {
        this.speeding = speeding;
    }
}
