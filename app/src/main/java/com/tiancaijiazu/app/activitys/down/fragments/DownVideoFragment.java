package com.tiancaijiazu.app.activitys.down.fragments;


import android.app.DownloadManager;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.down.adapters.RlvAdapter_down_video;
import com.tiancaijiazu.app.activitys.down.beans.MediaBean;
import com.tiancaijiazu.app.activitys.down.utils.ScannerAnsyTask;
import com.tiancaijiazu.app.base.fragment.SimpleFragment;
import com.tiancaijiazu.app.utils.events.DownFinis;
import com.tiancaijiazu.app.utils.events.DownloadEvent;
import com.tiancaijiazu.app.utils.shopvideo.JZVideoPlayerShop;
import com.tiancaijiazu.app.utils.shopvideo.MyJZVideoPlayerStandardShop;
import com.tiancaijiazu.app.utils.video.JZVideoPlayerList;
import com.tiancaijiazu.app.utils.video.MyJZVideoPlayerStandardList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.JZUserAction;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownVideoFragment extends SimpleFragment {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.iv_not_video)
    ImageView mIvNotVideo;
    @BindView(R.id.jiaozi_player)
    MyJZVideoPlayerStandardShop mJiaoziPlayer;
    private List<MediaBean> mMediaBeans;
    private RlvAdapter_down_video mRlvAdapterDownVideo;

    public DownVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void modifyBtn4(String msg) {
        if ("all".equals(msg)) {
            mRlvAdapterDownVideo.addSelect(true);
        } else if ("delete".equals(msg)) {
            List<MediaBean> data = mRlvAdapterDownVideo.mData;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isIsbo()) {
                    File file = new File(mActivity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + data.get(i).getName());
                    file.delete();
                }
            }
            startScanTack();
        } else if ("biaoji".equals(msg)) {
            mRlvAdapterDownVideo.addStatus(true);
        } else if ("cancel".equals(msg)) {
            mRlvAdapterDownVideo.addStatus(false);
        }
    }
    /**
     * 返回
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventDownFinis(DownFinis event) {
        Log.i("sd", "onMessageEventDownFinis: ");
        boolean isbo = event.isIsbo();
        if(isbo){
            if (MyJZVideoPlayerStandardShop.b == 1) {
                MyJZVideoPlayerStandardShop.quitFullscreenOrTinyWindow();
            }
            MyJZVideoPlayerStandardShop.b = 0;
            mJiaoziPlayer.setVisibility(View.GONE);
        }
    }
    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_down_video;
    }

    /**
     * 设置屏幕方向
     */
    private void initPlayer() {
        JZVideoPlayerShop.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayerShop.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    protected void initData() {

        initRecylerView();
        initPlayer();
        startScanTack();
    }

    private void initRecylerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<MediaBean> mediaBeans = new ArrayList<>();
        mRlvAdapterDownVideo = new RlvAdapter_down_video(mediaBeans, getContext());
        mRecylerView.setAdapter(mRlvAdapterDownVideo);

        mRlvAdapterDownVideo.setOnClickLisiter(new RlvAdapter_down_video.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<MediaBean> mData, boolean isbo) {
                String absolutePath = mData.get(position).getAbsolutePath();
                mJiaoziPlayer.setVisibility(View.VISIBLE);

                mJiaoziPlayer.setUp(absolutePath, JZVideoPlayerList.SCREEN_WINDOW_NORMAL, "");
                mJiaoziPlayer.startVideo();
                mJiaoziPlayer.onEvent(JZUserAction.ON_ENTER_FULLSCREEN);
                mJiaoziPlayer.startWindowFullscreen();
                mJiaoziPlayer.fullscreenButton.setVisibility(View.GONE);
            }
        });
    }

    private void startScanTack() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    ScannerAnsyTask scannerAnsyTask = new ScannerAnsyTask(mActivity);
                    scannerAnsyTask.execute();
                    mMediaBeans = scannerAnsyTask.get();
                    mHandler.sendEmptyMessage(0x101);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x101) {
                if (mIvNotVideo != null) {
                    if (mMediaBeans.size() != 0) {
                        mIvNotVideo.setVisibility(View.GONE);
                        mRecylerView.setVisibility(View.VISIBLE);
                        mRlvAdapterDownVideo.addData(mMediaBeans);
                    } else {
                        mRecylerView.setVisibility(View.GONE);
                        mIvNotVideo.setVisibility(View.VISIBLE);
                    }
                }

            }
        }
    };

}
