package com.tiancaijiazu.app.activitys.bringfragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ConsistingOfAdapter_Five;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ConsistingOfAdapter_Four;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ConsistingOfAdapter_One;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ConsistingOfAdapter_Three;
import com.tiancaijiazu.app.activitys.bringfragments.adapters.ConsistingOfAdapter_Two;
import com.tiancaijiazu.app.activitys.bringfragments.souactivitys.SouActivity;
import com.tiancaijiazu.app.activitys.consisting.ConsistingActivity;
import com.tiancaijiazu.app.base.fragment.BaseFragment;
import com.tiancaijiazu.app.beans.ConsistingOfBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 *
 *  辅食大全
 */
public class ConsistingOfFragment extends BaseFragment<IView, Presenter<IView>> implements IView {


    @BindView(R.id.edit_sou)
    EditText mEditSou;

    Unbinder unbinder;
    @BindView(R.id.biao_ji_one)
    ImageView mBiaoJiOne;
    @BindView(R.id.title1)
    TextView mTitle1;
    @BindView(R.id.heng1)
    LinearLayout mHeng1;
    @BindView(R.id.recylerView1)
    RecyclerView mRecylerView1;
    @BindView(R.id.rela1)
    RelativeLayout mRela1;
    @BindView(R.id.biao_ji_two)
    ImageView mBiaoJiTwo;
    @BindView(R.id.title2)
    TextView mTitle2;
    @BindView(R.id.heng2)
    LinearLayout mHeng2;
    @BindView(R.id.recylerView2)
    RecyclerView mRecylerView2;
    @BindView(R.id.rela2)
    RelativeLayout mRela2;
    @BindView(R.id.biao_ji_three)
    ImageView mBiaoJiThree;
    @BindView(R.id.title3)
    TextView mTitle3;
    @BindView(R.id.heng3)
    LinearLayout mHeng3;
    @BindView(R.id.recylerView3)
    RecyclerView mRecylerView3;
    @BindView(R.id.rela3)
    RelativeLayout mRela3;
    @BindView(R.id.biao_ji_five)
    ImageView mBiaoJiFive;
    @BindView(R.id.title5)
    TextView mTitle5;
    @BindView(R.id.heng5)
    LinearLayout mHeng5;
    @BindView(R.id.recylerView5)
    RecyclerView mRecylerView5;
    @BindView(R.id.rela5)
    RelativeLayout mRela5;
    @BindView(R.id.biao_ji_four)
    ImageView mBiaoJiFour;
    @BindView(R.id.title4)
    TextView mTitle4;
    @BindView(R.id.heng4)
    LinearLayout mHeng4;
    @BindView(R.id.recylerView4)
    RecyclerView mRecylerView4;
    @BindView(R.id.rela4)
    RelativeLayout mRela4;
    Unbinder unbinder1;

    @Override
    protected int creatrLayoutId() {
        return R.layout.fragment_consisting_of;
    }

    @Override
    protected void initData() {
        mEditSou.setInputType(InputType.TYPE_NULL);
        initRlvOne();
        initRlvTwo();
        initRlvThree();
        initRlvFour();
        initRlvFive();
    }

    private void initRlvFive() {
        ArrayList<ConsistingOfBean> rlv_five = new ArrayList<>();
        rlv_five.add(new ConsistingOfBean("果蔬泥", R.mipmap.food_one));
        rlv_five.add(new ConsistingOfBean("肉泥", R.mipmap.food_two));
        rlv_five.add(new ConsistingOfBean("蛋黄泥", R.mipmap.food_three));
        rlv_five.add(new ConsistingOfBean("面", R.mipmap.food_four));
        rlv_five.add(new ConsistingOfBean("饭", R.mipmap.food_five));
        rlv_five.add(new ConsistingOfBean("粥", R.mipmap.food_six));
        rlv_five.add(new ConsistingOfBean("蒸", R.mipmap.food_seven));
        rlv_five.add(new ConsistingOfBean("烘焙", R.mipmap.food_eight));
        rlv_five.add(new ConsistingOfBean("煮", R.mipmap.food_nine));
        rlv_five.add(new ConsistingOfBean("煎", R.mipmap.food_ten));
        rlv_five.add(new ConsistingOfBean("零食", R.mipmap.food_eleven));
        rlv_five.add(new ConsistingOfBean("果汁", R.mipmap.food_twelve));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerView5.setLayoutManager(gridLayoutManager);
        ConsistingOfAdapter_Five consistingOfAdapterFive = new ConsistingOfAdapter_Five(rlv_five, mContext);
        mRecylerView5.setAdapter(consistingOfAdapterFive);
        consistingOfAdapterFive.setOnClickLisiter(new ConsistingOfAdapter_Five.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ConsistingOfBean> mData) {
                initIntent(mData.get(position).getTitle());
            }
        });
    }

    private void initRlvFour() {
        ArrayList<ConsistingOfBean> rlv_four = new ArrayList<>();
        rlv_four.add(new ConsistingOfBean("温性", null));
        rlv_four.add(new ConsistingOfBean("热性", null));
        rlv_four.add(new ConsistingOfBean("寒性", null));
        rlv_four.add(new ConsistingOfBean("寒性", null));
        rlv_four.add(new ConsistingOfBean("平性", null));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerView4.setLayoutManager(gridLayoutManager);
        ConsistingOfAdapter_Four consistingOfAdapterFour = new ConsistingOfAdapter_Four(rlv_four);
        mRecylerView4.setAdapter(consistingOfAdapterFour);
        consistingOfAdapterFour.setOnClickLisiter(new ConsistingOfAdapter_Four.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ConsistingOfBean> mData) {
                initIntent(mData.get(position).getTitle());
            }
        });
    }

    private void initRlvThree() {
        ArrayList<ConsistingOfBean> rlv_three = new ArrayList<>();
        rlv_three.add(new ConsistingOfBean("发烧", null));
        rlv_three.add(new ConsistingOfBean("咳嗽", null));
        rlv_three.add(new ConsistingOfBean("感冒", null));
        rlv_three.add(new ConsistingOfBean("腹泻", null));
        rlv_three.add(new ConsistingOfBean("便秘", null));
        rlv_three.add(new ConsistingOfBean("维生素", null));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerView3.setLayoutManager(gridLayoutManager);
        ConsistingOfAdapter_Three consistingOfAdapterThree = new ConsistingOfAdapter_Three(rlv_three);
        mRecylerView3.setAdapter(consistingOfAdapterThree);
        consistingOfAdapterThree.setOnClickLisiter(new ConsistingOfAdapter_Three.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ConsistingOfBean> mData) {
                initIntent(mData.get(position).getTitle());
            }
        });
    }

    private void initRlvTwo() {
        ArrayList<ConsistingOfBean> rlv_two = new ArrayList<>();
        rlv_two.add(new ConsistingOfBean("补铁", null));
        rlv_two.add(new ConsistingOfBean("补钙", null));
        rlv_two.add(new ConsistingOfBean("补锌", null));
        rlv_two.add(new ConsistingOfBean("补碘", null));
        rlv_two.add(new ConsistingOfBean("蛋白质", null));
        rlv_two.add(new ConsistingOfBean("维生素", null));
        rlv_two.add(new ConsistingOfBean("DHA", null));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerView2.setLayoutManager(gridLayoutManager);
        ConsistingOfAdapter_Two consistingOfAdapterTwo = new ConsistingOfAdapter_Two(rlv_two);
        mRecylerView2.setAdapter(consistingOfAdapterTwo);
        consistingOfAdapterTwo.setOnClickLisiter(new ConsistingOfAdapter_Two.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ConsistingOfBean> mData) {
                initIntent(mData.get(position).getTitle());
            }
        });
    }

    private void initRlvOne() {
        ArrayList<ConsistingOfBean> rlv_one = new ArrayList<>();
        rlv_one.add(new ConsistingOfBean("6月龄", null));
        rlv_one.add(new ConsistingOfBean("7月龄", null));
        rlv_one.add(new ConsistingOfBean("8月龄", null));
        rlv_one.add(new ConsistingOfBean("9月龄", null));
        rlv_one.add(new ConsistingOfBean("10月龄", null));
        rlv_one.add(new ConsistingOfBean("11月龄", null));
        rlv_one.add(new ConsistingOfBean("12-15月龄", null));
        rlv_one.add(new ConsistingOfBean("15-18月龄", null));
        rlv_one.add(new ConsistingOfBean("18-24月龄", null));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        mRecylerView1.setLayoutManager(gridLayoutManager);
        ConsistingOfAdapter_One consistingOfAdapterOne = new ConsistingOfAdapter_One(rlv_one);
        mRecylerView1.setAdapter(consistingOfAdapterOne);
        consistingOfAdapterOne.setOnClickLisiter(new ConsistingOfAdapter_One.onClickLisiter() {
            @Override
            public void onClicker(View view, int position, ArrayList<ConsistingOfBean> mData) {
                initIntent(mData.get(position).getTitle());
            }
        });
    }

    public void initIntent(String str){
        Intent intent = new Intent(getContext(), ConsistingActivity.class);
        intent.putExtra("str",str);
        startActivity(intent);
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


    @OnClick(R.id.edit_sou)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), SouActivity.class);
        startActivity(intent);
    }
}
