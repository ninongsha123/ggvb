package com.tiancaijiazu.app.activitys.activitypage.loginpages;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.tools.CharacterParserUtil;
import com.tiancaijiazu.app.tools.CountryComparator;
import com.tiancaijiazu.app.tools.CountrySortAdapter;
import com.tiancaijiazu.app.tools.CountrySortModel;
import com.tiancaijiazu.app.tools.GetCountryNameSort;
import com.tiancaijiazu.app.tools.SideBar;
import com.tiancaijiazu.app.utils.DrawableUtil;
import com.tiancaijiazu.app.utils.city.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 *
 *  登录页面-国家地区选择
 */
public class CountryActivity extends SimpleActivity {


    @BindView(R.id.country_et_search)
    EditText mCountryEtSearch;
    @BindView(R.id.country_lv_list)
    ListView mCountryLvList;
    @BindView(R.id.country_dialog)
    TextView mCountryDialog;
    @BindView(R.id.country_sidebar)
    SideBar mCountrySidebar;

    private List<CountrySortModel> mAllCountryList;

    private CountryComparator pinyinComparator;

    private GetCountryNameSort countryChangeUtil;

    private CharacterParserUtil characterParserUtil;


    private CountrySortAdapter adapter;
    private Intent mIntent;

    @Override
    protected void initEventAndData() {
        mIntent = getIntent();
        initView();

        setListener();

        getCountryList();
    }

    @Override
    protected int creatrLayoutId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_country;
    }

    /**
     * 初始化界面
     */
    private void initView() {

        mCountrySidebar.setTextView(mCountryDialog);

        mAllCountryList = new ArrayList<CountrySortModel>();
        pinyinComparator = new CountryComparator();
        countryChangeUtil = new GetCountryNameSort();
        characterParserUtil = new CharacterParserUtil();

        // 将联系人进行排序，按照A~Z的顺序
        Collections.sort(mAllCountryList, pinyinComparator);
        adapter = new CountrySortAdapter(this, mAllCountryList);
        mCountryLvList.setAdapter(adapter);

    }

    /****
     * 添加监听
     */
    private void setListener() {

        DrawableUtil onDrawableListener =  new DrawableUtil(mCountryEtSearch, new DrawableUtil.OnDrawableListener() {
            @Override
            public void onLeft(View v, Drawable left) {
                Log.i("yx", "onLeft: ");
            }

            @Override
            public void onRight(View v, Drawable right) {
                mCountryEtSearch.setText("");
                Collections.sort(mAllCountryList, pinyinComparator);
                adapter.updateListView(mAllCountryList);
            }
        });
        final Drawable drawable= getResources().getDrawable(R.mipmap.sou);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        final Drawable drawable1= getResources().getDrawable(R.mipmap.shan_sou);
        drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight());
        mCountryEtSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().length()!=0){
                    mCountryEtSearch.setCompoundDrawables(drawable,null,drawable1,null);
                }else {
                    mCountryEtSearch.setCompoundDrawables(drawable,null,null,null);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchContent = mCountryEtSearch.getText().toString();


                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(searchContent, mAllCountryList);

                    adapter.updateListView(fileterList);
                } else {
                    adapter.updateListView(mAllCountryList);
                }
                mCountryLvList.setSelection(0);
            }
        });

        // 右侧sideBar监听
        mCountrySidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mCountryLvList.setSelection(position);
                }
            }
        });

        mCountryLvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
                String countryName = null;
                String countryNumber = null;
                String searchContent = mCountryEtSearch.getText().toString();

                if (searchContent.length() > 0) {
                    // 按照输入内容进行匹配
                    ArrayList<CountrySortModel> fileterList = (ArrayList<CountrySortModel>) countryChangeUtil
                            .search(searchContent, mAllCountryList);
                    countryName = fileterList.get(position).countryName;
                    countryNumber = fileterList.get(position).countryNumber;
                } else {
                    // 点击后返回
                    countryName = mAllCountryList.get(position).countryName;
                    countryNumber = mAllCountryList.get(position).countryNumber;
                }
                Log.e("yx", "countryName: + " + countryName + "countryNumber: " + countryNumber);
                if("中国 ".equals(countryName)){
                    mIntent.putExtra("countryName", countryName);
                    mIntent.putExtra("countryNumber", countryNumber);
                    setResult(120, mIntent);
                    finish();
                }else {
                    ToastUtils.showShortToast(CountryActivity.this,"目前只支持中国手机号！");
                }


            }
        });
    }

    /**
     * 获取国家列表
     */
    private void getCountryList() {
        String[] countryList = getResources().getStringArray(R.array.country_code_list_ch);

        for (int i = 0, length = countryList.length; i < length; i++) {
            String[] country = countryList[i].split("\\*");

            String countryName = country[0];
            String countryNumber = country[1];
            String countrySortKey = characterParserUtil.getSelling(countryName);
            CountrySortModel countrySortModel = new CountrySortModel(countryName, countryNumber,
                    countrySortKey);
            String sortLetter = countryChangeUtil.getSortLetterBySortKey(countrySortKey);
            if (sortLetter == null) {
                sortLetter = countryChangeUtil.getSortLetterBySortKey(countryName);
            }

            countrySortModel.sortLetters = sortLetter;
            mAllCountryList.add(countrySortModel);
        }

        Collections.sort(mAllCountryList, pinyinComparator);
        adapter.updateListView(mAllCountryList);
        Log.e("yx", "changdu" + mAllCountryList.size());
    }
}
