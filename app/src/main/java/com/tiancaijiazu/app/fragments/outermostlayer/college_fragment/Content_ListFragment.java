package com.tiancaijiazu.app.fragments.outermostlayer.college_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.DarftActivity;
import com.tiancaijiazu.app.activitys.activitypage.collegeactivitys.VideoListActivity;
import com.tiancaijiazu.app.activitys.adapters.RlvAdapter;
import com.tiancaijiazu.app.activitys.video.VideoActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.fragments.beans.CollegeCourseBean;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

import static com.tiancaijiazu.app.fragments.beans.CollegeCourseBean.ResultBean;

public class Content_ListFragment extends BaseFragment<IView, Presenter<IView>> implements IView {

    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    Unbinder unbinder;
    private String mediaUri;
    private ResultBean mResult;
    private RlvAdapter mRlvAdapter;
    private String mDescription;

    public static Content_ListFragment getInstance(String result) {
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        Content_ListFragment content_listFragment = new Content_ListFragment();
        content_listFragment.setArguments(bundle);
        return content_listFragment;
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void show(Object o, DifferentiateEnum differentiateEnum) {
        switch (differentiateEnum) {
            case COURSEID:
                CollegeCourseBean courseBean = (CollegeCourseBean) o;
                mResult = courseBean.getResult();
                List<ResultBean.ChapterListBean> chapterList = mResult.getChapterList();
                int isBought = mResult.getCourseInfo().getIsBought();
                mRlvAdapter.addData(chapterList,isBought);
                mDescription = mResult.getCourseInfo().getDescription();
                break;

        }
    }

    @Override
    protected Presenter<IView> createPresenter() {
        return new Presenter<>();
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_content__list;
    }

    @Override
    protected void initData() {
        initView();
    }


    // 初始化控件
    private void initView() {
        Bundle bundle = getArguments();
        String result = bundle.getString("result");
        mPresenter.getDataP(result, DifferentiateEnum.COURSEID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecylerView.setLayoutManager(linearLayoutManager);
        List<ResultBean.ChapterListBean> chapterList = new ArrayList<>();
        mRlvAdapter = new RlvAdapter(chapterList, getContext(), 5);
        mRecylerView.setAdapter(mRlvAdapter);

        mRlvAdapter.setOnClickLisiter(new RlvAdapter.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, List<ResultBean.ChapterListBean.ContentsListBean> mData) {
                if (mData.get(position).getIsFree() == 1) {
                    if (mData.get(position).getType() == 1) {
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        String mediaUri = mData.get(position).getMediaUri();
                        long courseId = mResult.getCourseInfo().getCourseId();
                        intent.putExtra("courseId", courseId + "");
                        intent.putExtra("mediaUri", mediaUri);
                        intent.putExtra("picUri", mData.get(position).getPicUri());
                        intent.putExtra("description", mData.get(position).getDescription());
                        intent.putExtra("name", mData.get(position).getTitle());
                        intent.putExtra("data", (Serializable) mResult);
                        startActivity(intent);
                        //Toast.makeText(getActivity(), "跳转视频界面", Toast.LENGTH_SHORT).show();
                    } else if (mData.get(position).getType() == 2) {
                        Intent in = new Intent(getContext(), VideoListActivity.class);
                        long courseId = mResult.getCourseInfo().getCourseId();
                        String mediaUri = mData.get(position).getMediaUri();
                        String picUri = mData.get(position).getPicUri();
                        String title = mData.get(position).getTitle();
                        in.putExtra("courseId", courseId + "");
                        in.putExtra("mediaUri", mediaUri);
                        in.putExtra("picUri", picUri);
                        in.putExtra("title", title);
                        in.putExtra("data", (Serializable) mResult);
                        startActivity(in);
                    }
                } else {
                    Toast.makeText(getContext(), "还未购买解锁该课程", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRlvAdapter.setOnClickLisiterTu(new RlvAdapter.onClickLisiterTu() {
            @Override
            public void onClickerTu(View view, int position, List<ResultBean.ChapterListBean.ContentsListBean> mData) {
                if (TimeUtil.isInvalidClick(view, 300))
                    return;
                if (mData.get(position).getIsFree() == 1) {
                    Intent in = new Intent(getContext(), DarftActivity.class);
                    in.putExtra("courseId", mData.get(position).getCourseId() + "");
                    in.putExtra("biao","wen");
                    in.putExtra("picUri", mData.get(position).getPicUri());
                    in.putExtra("mediaUri", mData.get(position).getMediaUri());
                    in.putExtra("title", mData.get(position).getTitle());
                    in.putExtra("description", mData.get(position).getDescription());
                    startActivity(in);
                } else {
                    Toast.makeText(mContext, "您还未购买该课程", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
