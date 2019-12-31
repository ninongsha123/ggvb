package com.tiancaijiazu.app.activitys.bringfragments.souactivitys;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.views.FlowGroupView;
import com.tiancaijiazu.app.activitys.views.FlowGroupView_history;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.dao.DataBaseMannger;
import com.tiancaijiazu.app.dao.HistoryTitleBean;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.DrawableUtil;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SouActivity extends BaseActivity<IView, Presenter<IView>> implements IView {


    @BindView(R.id.edit_sou)
    EditText mEditSou;
    @BindView(R.id.rela)
    RelativeLayout mRela;
    @BindView(R.id.flow)
    FlowGroupView_history mFlow;
    @BindView(R.id.flow_view)
    FlowGroupView mFlowView;
    @BindView(R.id.empty)
    LinearLayout mEmpty;
    @BindView(R.id.recylerView)
    RecyclerView mRecylerView;
    @BindView(R.id.line1)
    LinearLayout mLine1;
    @BindView(R.id.v)
    View mV;
    @BindView(R.id.cancel)
    TextView mCancel;
    private ArrayList<HistoryTitleBean> mTitleBeans;

    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mEditSou.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mEditSou.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SouActivity.this);
        mRecylerView.setLayoutManager(linearLayoutManager);
        SouAdapter_data souAdapterData = new SouAdapter_data();
        mRecylerView.setAdapter(souAdapterData);
        List<HistoryTitleBean> historyTitleBeans = DataBaseMannger.getIntrance().selectHistoryTitle();
        if (historyTitleBeans.size() == 0) {
            mRela.setVisibility(View.GONE);
        } else {
            mRela.setVisibility(View.VISIBLE);
            initSou(historyTitleBeans);
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("蔬菜");
        list.add("水果");
        list.add("辅食这样吃");
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(0, 10, 10, 15);
        if (mFlowView != null) {
            mFlowView.removeAllViews();
        }
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams1);
            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setText(list.get(i));
            textView.setBackgroundResource(R.drawable.sou_shape);
            textView.setTextColor(Color.parseColor("#666666"));
            initEvents(textView);
            textView.setPadding(calculateDpToPx(17), calculateDpToPx(6), calculateDpToPx(17), calculateDpToPx(6));
            mFlowView.addView(textView, i, layoutParams1);
        }
        DrawableUtil onDrawableListener = new DrawableUtil(mEditSou, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                Log.i("yx", "onLeft: ");
            }

            @Override
            public void onRight(View v, Drawable right) {
                Log.i("yx", "onRight: ");
                mEditSou.setText("");
            }
        });
        mEditSou.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final Drawable drawable = getResources().getDrawable(R.mipmap.edit_shan);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                final Drawable drawable1 = getResources().getDrawable(R.mipmap.sou);
                drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
                if (s.toString().length() == 0) {
                    mEditSou.setCompoundDrawables(drawable1, null, null, null);
                    mRecylerView.setVisibility(View.GONE);
                    mLine1.setVisibility(View.VISIBLE);
                    mV.setVisibility(View.GONE);
                } else {
                    mEditSou.setCompoundDrawables(drawable1, null, drawable, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditSou.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) mEditSou.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(
                                    getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    mTitleBeans = new ArrayList<>();
                    String s = v.getText().toString();
                    List<HistoryTitleBean> historyTitleBeans1 = DataBaseMannger.getIntrance().selectHistoryTitle();
                    for (int i = 0; i < historyTitleBeans1.size(); i++) {
                        if(s.equals(historyTitleBeans1.get(i).getTitle())){
                            Long id = historyTitleBeans1.get(i).getId();
                            DataBaseMannger.getIntrance().deleteHistoryTitle(new HistoryTitleBean(id,s));
                        }
                    }

                    mTitleBeans.add(new HistoryTitleBean(null, s));
                    DataBaseMannger.getIntrance().insertHistoryTitle(mTitleBeans);
                    List<HistoryTitleBean> historyTitleBeans = DataBaseMannger.getIntrance().selectHistoryTitle();
                    initSou(historyTitleBeans);
                    mRela.setVisibility(View.VISIBLE);
                    mLine1.setVisibility(View.GONE);
                    mRecylerView.setVisibility(View.VISIBLE);
                    mV.setVisibility(View.VISIBLE);

                    return true;
                }
                return false;
            }
        });


    }

    private void initSou(List<HistoryTitleBean> historyTitleBeans) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 10, 10, 15);
        if (mFlow != null) {
            mFlow.removeAllViews();
        }
        int j = 0;
        for (int i = historyTitleBeans.size() - 1; i >= 0; i--) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(layoutParams);
            textView.setLineSpacing(1.2f, 1.2f);//设置行间距
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setText(historyTitleBeans.get(i).getTitle());
            textView.setBackgroundResource(R.drawable.sou_shape);
            textView.setTextColor(Color.parseColor("#666666"));
            initEvents(textView);
            textView.setPadding(calculateDpToPx(17), calculateDpToPx(6), calculateDpToPx(17), calculateDpToPx(6));
            mFlow.addView(textView, j, layoutParams);
            j++;
        }
    }

    private int calculateDpToPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }

    /**
     * 为每个view 添加点击事件
     */
    private void initEvents(final TextView tv) {
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SouActivity.this, tv.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_sou;
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



    @OnClick({R.id.cancel, R.id.empty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.empty:
                DataBaseMannger.getIntrance().deleteHistoryTitleAll();
                List<HistoryTitleBean> historyTitleBeans = DataBaseMannger.getIntrance().selectHistoryTitle();
                if (historyTitleBeans.size() == 0) {
                    mRela.setVisibility(View.GONE);
                    initSou(historyTitleBeans);
                }
                break;
        }
    }
}
