package com.tiancaijiazu.app.activitys.test;

import android.os.Bundle;
import android.widget.ImageView;

import com.lzx.starrysky.manager.MediaSessionConnection;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.manager.OnPlayerEventListener;
import com.lzx.starrysky.utils.TimerTaskManager;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends BaseActivity<IView, Presenter<IView>> implements IView {

    @BindView(R.id.iv)
    ImageView mIv;
    private MusicManager instance;
    private MediaSessionConnection mMediaSessionConnection;
    private TimerTaskManager mTimerTask;

    @Override
    protected void initEventAndData() {
        mMediaSessionConnection = MediaSessionConnection.getInstance();

        mTimerTask = new TimerTaskManager();
        instance = MusicManager.getInstance();
        /*Intent intent = getIntent();
        String picture = intent.getStringExtra("picture");
        Glide.with(this).load(picture).into(mIv);
        //添加监听
        List<SongInfo> playList = MusicManager.getInstance().getPlayList();
        Log.i("ffff", "initEventAndData: " + playList.size());*/
        CopyOnWriteArrayList<OnPlayerEventListener> playerEventListeners = instance.getPlayerEventListeners();
        OnPlayerEventListener onPlayerEventListener = playerEventListeners.get(0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        //连接音频服务
        //mMediaSessionConnection.connect();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO:OnCreate Method has been created, run ButterKnife again to generate code
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }
}
