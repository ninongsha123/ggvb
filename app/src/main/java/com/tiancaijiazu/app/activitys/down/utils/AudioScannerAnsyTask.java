package com.tiancaijiazu.app.activitys.down.utils;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.tiancaijiazu.app.activitys.down.beans.AudioBean;
import com.tiancaijiazu.app.utils.FileSizeUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/22/022.
 */

public class AudioScannerAnsyTask extends AsyncTask<Void, Integer, List<AudioBean>> {
    private List<AudioBean> mMediaInfoList = new ArrayList<AudioBean>(); // 媒体列表类

    private Activity mActivity; // 依附于某个Activity，因为AsyncTask要在UI线程中执行

    public AudioScannerAnsyTask() {
        super();
    }

    public AudioScannerAnsyTask(Activity activity) {
        super();
        this.mActivity = activity;
    }

    @Override
    protected List<AudioBean> doInBackground(Void... params) {

        mMediaInfoList = getVideoFile(mMediaInfoList,mActivity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC));
//		mMediaInfoList = filterVideo(mMediaInfoList); // 这里可以选择不过滤小文件
        Log.e("CJT", "最后的大小" + "ScannerAnsyTask---第一条数据--");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mMediaInfoList;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<AudioBean> videoInfos) {
        super.onPostExecute(videoInfos);
        Log.e("CJT", "最后的大小" + "ScannerAnsyTask---View.GONE--");
    }

    /**
     * 获取视频文件
     *
     * @param list
     * @param file
     * @return
     */
    private List<AudioBean> getVideoFile(final List<AudioBean> list, File file) {

        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {

                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp3") ) {
                        AudioBean video = new AudioBean();
                        file.getUsableSpace();
                        video.setName(file.getName());
                        video.setAbsolutePath(file.getAbsolutePath());
                        Log.i("yx456", "accept: "+file.getPath());
                        video.setFileSize(FileSizeUtil.getAutoFileOrFilesSize(file.getPath()));
                        video.setTime(getDuration(file.getPath()));
                        Log.e("CJT", "最后的大小" + "ScannerAnsyTask---视频名称--name--" + video.getAbsolutePath());
                        list.add(video);
                        return true;
                    }
                    // 判断是不是目录
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });

        return list;
    }

    //获取MP3文件的时长
    private String getDuration(String pt){
        String time = null;
        //Log.e(TAG, "getDuration: uri---"+Uri.parse(pt) );
        MediaPlayer mp = MediaPlayer.create(mActivity, Uri.parse(pt));
        if (mp != null){
            int duration = mp.getDuration();
            mp.release();
            duration /= 1000;
            if (duration < 60){
                time = "00:00:" + transform(duration);
            }else if (duration >= 60 && duration < 60 * 60){
                int min = duration / 60;
                time = "00:" + transform(min) + ":"+ transform(duration % 60);
            }else {
                int hour = duration / 60 / 60;
                int min = 0;
                int sec = 0;
                if (duration - 3600 >= 60){
                    min = (duration - 3600) / 60;
                    sec = (duration - 3600) % 60;
                }else if (duration - 3600 < 60){
                    min = 00;
                    sec = duration - 3600;
                }
                time = transform(hour) + ":" + transform(min) + ":" + transform(sec);
            }
        }
        if (time == null){
            time = "00:00:00";
        }
        return time;
    }

    private String transform(int i){
        if (i >= 10){
            return i + "";
        }else if(i < 10){
            return "0" + i;
        }
        return null;
    }

}
