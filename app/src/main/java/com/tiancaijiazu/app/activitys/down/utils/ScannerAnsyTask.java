package com.tiancaijiazu.app.activitys.down.utils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.tiancaijiazu.app.activitys.down.beans.MediaBean;
import com.tiancaijiazu.app.utils.FileSizeUtil;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/20/020.
 */

public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<MediaBean>> {
    private List<MediaBean> mMediaInfoList = new ArrayList<MediaBean>(); // 媒体列表类

    private Activity mActivity; // 依附于某个Activity，因为AsyncTask要在UI线程中执行
    private Handler progressHandler ;

    public ScannerAnsyTask() {
        super();
    }

    public ScannerAnsyTask(Activity activity) {
        super();
        this.mActivity = activity;
    }

    @Override
    protected List<MediaBean> doInBackground(Void... params) {

        mMediaInfoList = getVideoFile(mMediaInfoList,mActivity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
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
    protected void onPostExecute(List<MediaBean> videoInfos) {
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
    private List<MediaBean> getVideoFile(final List<MediaBean> list, File file) {

        file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {

                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mp4") /*|| name.equalsIgnoreCase(".3gp") || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts") || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov") || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi") || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp") || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mkv") || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx") || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm") || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram") || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8") || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v") || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra") || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")*/) {
                        MediaBean video = new MediaBean();
                        file.getUsableSpace();
                        video.setName(file.getName());
                        video.setAbsolutePath(file.getAbsolutePath());
                        video.setFileSize(FileSizeUtil.getAutoFileOrFilesSize(file.getPath()));
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

    /**
     * 10M=10485760 b,小于10m的过滤掉 过滤视频文件
     *
     * @param videoInfos
     * @return
     */
    private List<MediaBean> filterVideo(List<MediaBean> videoInfos) {
        List<MediaBean> newVideos = new ArrayList<MediaBean>();
        for (MediaBean videoInfo : videoInfos) {
            File f = new File(videoInfo.getAbsolutePath());
            if (f.exists() && f.isFile() && f.length() > 10485760) {
                newVideos.add(videoInfo);
                Log.e("CJT", "ScannerAnsyTask---视频文件大小" + f.length());
            } else {
                Log.e("CJT", "ScannerAnsyTask---视频文件太小或者不存在");
            }
        }
        return newVideos;
    }

}
