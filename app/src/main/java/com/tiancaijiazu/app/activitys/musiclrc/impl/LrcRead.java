package com.tiancaijiazu.app.activitys.musiclrc.impl;

import android.util.Log;

import com.tiancaijiazu.app.activitys.musiclrc.LyricContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/14/014.
 */

public class LrcRead {
    private List<LyricContent> LyricList;// 歌词内容列表 （包括歌词和时间）
    private LyricContent mLyricContent;// 歌词内容  （包括歌词和时间）

    public LrcRead() {
        mLyricContent = new LyricContent();//歌词内容和时间的实体类对象
        LyricList = new ArrayList<LyricContent>();//歌词内容和时间是实体类对象列表
    }

    /**
     * 解析lrc文件
     *
     * @param file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void Read(String file) throws FileNotFoundException, IOException {
        String Lrc_data = "";
        BufferedReader mBufferedReader = null;
        InputStreamReader mInputStreamReader = null;
        File mFile = new File(file);// /mnt/sdcard/我不知道爱是什么.lrc
        FileInputStream mFileInputStream = new FileInputStream(mFile);
        mInputStreamReader = new InputStreamReader(mFileInputStream, "UTF-8");
        mBufferedReader = new BufferedReader(mInputStreamReader);

        while ((Lrc_data = mBufferedReader.readLine()) != null) {//[ti:我不知道爱是什么] ar:艾怡良]
            if (Lrc_data.contains("[") && Lrc_data.contains("]")) {
                Lrc_data = Lrc_data.replace("[", "");// ti:我不知道爱是什么]
                Lrc_data = Lrc_data.replace("]", "@");// ti:我不知道爱是什么@
                String splitLrc_data[] = Lrc_data.split("@");// [00:00.00, 我爱歌词网 www.5ilrc.com]split是去掉@并在此处用逗号分隔成两个字符串。最后放到一个数组里。
                if (splitLrc_data.length > 1) {
                    mLyricContent.setLyric(splitLrc_data[1]);// [00:00.00, 我爱歌词网 www.5ilrc.com],取数组里面的第2个数据作为歌词。
                    int LyricTime = TimeStr(splitLrc_data[0]);// 取数组里面的第1个数据，放到TimeStr里都转成秒为单位后出来作为歌词时间。0 400 9490 12490 15860 15860 35560
                    mLyricContent.setLyricTime(LyricTime);
                    LyricList.add(mLyricContent);
                    mLyricContent = new LyricContent();
                }
                if (splitLrc_data.length == 1) {

                    if (!splitLrc_data[0].contains("ti") && !splitLrc_data[0].contains("ar") && !splitLrc_data[0].contains("al") && !splitLrc_data[0].contains("by")) {
                        mLyricContent.setLyric("");// [00:00.00, 我爱歌词网 www.5ilrc.com],取数组里面的第2个数据作为歌词。
                        int LyricTime = TimeStr(splitLrc_data[0]);// 取数组里面的第1个数据，放到TimeStr里都转成秒为单位后出来作为歌词时间。0 400 9490 12490 15860 15860 35560
                        mLyricContent.setLyricTime(LyricTime);
                        LyricList.add(mLyricContent);
                        mLyricContent = new LyricContent();
                    }

                }
            }

        }
        mBufferedReader.close();
        mInputStreamReader.close();
    }

    public int TimeStr(String timeStr) {// 00:40.57
        timeStr = timeStr.replace(":", ".");//00.40.57
        timeStr = timeStr.replace(".", "@");//00@40@57
        String timeData[] = timeStr.split("@");//[00, 40, 57]
        //Log.i("yx159", "TimeStr: "+timeData[0].length());
        int minute = Integer.parseInt(timeData[0]);//数组里的第1个数据是分0
        int second = Integer.parseInt(timeData[1]);//数组里的第2个数据是秒40
        int millisecond = Integer.parseInt(timeData[2]);//数组里的第3个数据是秒57
        int currentTime = (minute * 60 + second) * 1000 + millisecond * 10;//40000+570=40570
        return currentTime;
    }

    public List<LyricContent> GetLyricContent() {
        return LyricList;
    }

}
