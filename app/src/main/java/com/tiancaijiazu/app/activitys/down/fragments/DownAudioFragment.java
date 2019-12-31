package com.tiancaijiazu.app.activitys.down.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.homepageactivitys.KnowlegePlayActivity;
import com.tiancaijiazu.app.activitys.down.adapters.RlvAdapter_audio_down;
import com.tiancaijiazu.app.activitys.down.beans.AudioBean;
import com.tiancaijiazu.app.activitys.down.utils.AudioScannerAnsyTask;
import com.tiancaijiazu.app.base.fragment.SimpleFragment;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.DownAudioBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownAudioFragment extends SimpleFragment {


    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;
    @BindView(R.id.iv_not_music)
    ImageView mIvNotMusic;
    Unbinder unbinder1;
    private List<AudioBean> mAudioBeans;
    private RlvAdapter_audio_down mRlvAdapterAudioDown;

    public DownAudioFragment() {
        // Required empty public constructor
    }


    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_down_audio;
    }

    @Override
    protected void initData() {
        List<DownAudioBean> downAudioBeans = DataBaseMannger.getIntrance().selectDownAudio();
        Log.i("yx111", "initData: "+downAudioBeans.size());
        initRecylerView(downAudioBeans);
        startScanTack();

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
            mRlvAdapterAudioDown.addSelect(true);
        } else if ("delete".equals(msg)) {
            List<AudioBean> data = mRlvAdapterAudioDown.mData;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isIsbo()) {
                    Long id = mRlvAdapterAudioDown.mDataDown.get(i).getId();
                    DataBaseMannger.getIntrance().deleteDownAudio(new DownAudioBean(id,null,null,null,null,null));
                    File file = new File(mActivity.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getPath() + "/" + data.get(i).getName());
                    file.delete();
                }
            }
            startScanTack();
        } else if ("biaoji".equals(msg)) {
            mRlvAdapterAudioDown.addStatus(true);
        } else if ("cancel".equals(msg)) {
            mRlvAdapterAudioDown.addStatus(false);
        }
    }

    private void initRecylerView(List<DownAudioBean> downAudioBeans) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        ArrayList<AudioBean> audioBeans = new ArrayList<>();
        mRlvAdapterAudioDown = new RlvAdapter_audio_down(audioBeans, getContext(),downAudioBeans);
        mRecylerView.setAdapter(mRlvAdapterAudioDown);

        mRlvAdapterAudioDown.setOnClickLisiter(new RlvAdapter_audio_down.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<AudioBean> mData, boolean isbo,List<DownAudioBean> mDataDown) {
                String absolutePath = mData.get(position).getAbsolutePath();
                Intent intent = new Intent(getContext(),KnowlegePlayActivity.class);
                intent.putExtra("biao","down");
                intent.putExtra("position",position);
                intent.putExtra("audio", (Serializable) mDataDown);
                startActivity(intent);
            }
        });
    }

    private void startScanTack() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    AudioScannerAnsyTask scannerAnsyTask = new AudioScannerAnsyTask(mActivity);
                    scannerAnsyTask.execute();
                    mAudioBeans = scannerAnsyTask.get();
                   // if (mAudioBeans != null && mAudioBeans.size() > 0) {
                        mHandler.sendEmptyMessage(0x101);
                    //}
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x101) {
                if(mIvNotMusic!=null){
                    if(mAudioBeans.size()!=0){
                        mIvNotMusic.setVisibility(View.GONE);
                        mRecylerView.setVisibility(View.VISIBLE);
                        List<DownAudioBean> downAudioBeans = DataBaseMannger.getIntrance().selectDownAudio();
                        mRlvAdapterAudioDown.addData(mAudioBeans,downAudioBeans);
                    }else {
                        mIvNotMusic.setVisibility(View.VISIBLE);
                        mRecylerView.setVisibility(View.GONE);
                    }
                }

            }
        }
    };

}
