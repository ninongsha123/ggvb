package com.tiancaijiazu.app.activitys.musiclrc.impl;

import com.tiancaijiazu.app.utils.cache.FileMusicUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2019/8/14/014.
 */

public class HttpDownloader {
    private URL url = null;


    public String downloadBaseFile(String urlStr){
        StringBuffer sb = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try{
            //创建一个url对象
            url = new URL(urlStr);
            //通過url对象，创建一个HttpURLConnection对象（连接）
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            //通过HttpURLConnection对象，得到InputStream
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            //使用io流读取文件
            while ((line = reader.readLine()) != null){
                sb.append(line+"\r\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (reader != null){
                    reader.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public int downloadAllFile(String urlStr,String path, String fileName){
        InputStream inputStream = null;
        try {
            FileMusicUtils fileUtils = new FileMusicUtils();
            if (fileUtils.isFileExist(path + fileName)){
                return 1;
            } else {
                inputStream = getInputStream(urlStr);
                File file = fileUtils.write2SDFromInput(path, fileName, inputStream);
                if ( file == null ){
                    return -1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            }catch (Exception ioe){
                ioe.printStackTrace();
            }
        }
        return 0;
    }

    private InputStream getInputStream(String urlStr)
            throws MalformedURLException,IOException {
        url = new URL(urlStr);
        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        return inputStream;
    }
}
