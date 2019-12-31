package com.tiancaijiazu.app.pickerview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.addapp.pickers.adapter.ArrayWheelAdapter;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnLinkageListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;
import cn.addapp.pickers.picker.LinkagePicker;
import cn.addapp.pickers.util.LogUtils;
import cn.addapp.pickers.widget.WheelListView;
import cn.addapp.pickers.widget.WheelView;

public class AddressPicker  extends LinkagePicker{
    private OnLinkageListener onLinkageListener;
    private OnMoreWheelListener onMoreWheelListener;
    private boolean hideProvince = false;
    private boolean hideCounty = false;
    private ArrayList<Province> provinces = new ArrayList();

    public AddressPicker(Activity activity, ArrayList<Province> provinces) {
        super(activity, new cn.addapp.pickers.picker.AddressPicker.AddressProvider(provinces));
        this.provinces = provinces;
    }

    public void setSelectedItem(String province, String city, String county) {
        super.setSelectedItem(province, city, county);
    }

    public Province getSelectedProvince() {
        return (Province)this.provinces.get(this.selectedFirstIndex);
    }

    public City getSelectedCity() {
        return (City)this.getSelectedProvince().getCities().get(this.selectedSecondIndex);
    }

    public County getSelectedCounty() {
        return (County)this.getSelectedCity().getCounties().get(this.selectedThirdIndex);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setOnMoreWheelListener(OnMoreWheelListener onMoreWheelListener) {
        this.onMoreWheelListener = onMoreWheelListener;
    }

    public void setOnLinkageListener(OnLinkageListener listener) {
        this.onLinkageListener = listener;
    }

    @SuppressLint("WrongConstant")
    @NonNull
    protected View makeCenterView() {
        if (null == this.provider) {
            throw new IllegalArgumentException("please set address provider before make view");
        } else {
            if (this.hideCounty) {
                this.hideProvince = false;
            }

            int[] widths = this.getColumnWidths(this.hideProvince || this.hideCounty);
            int provinceWidth = widths[0];
            int cityWidth = widths[1];
            int countyWidth = widths[2];
            if (this.hideProvince) {
                provinceWidth = 0;
                cityWidth = widths[0];
                countyWidth = widths[1];
            }

            LinearLayout layout = new LinearLayout(this.activity);
            layout.setOrientation(0);
            layout.setGravity(17);
            if (this.wheelModeEnable) {
                WheelView provinceView = new WheelView(this.activity);
                provinceView.setCanLoop(this.canLoop);
                provinceView.setLayoutParams(new LinearLayout.LayoutParams(provinceWidth, -2));
                provinceView.setTextSize((float)this.textSize);
                provinceView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                provinceView.setUnSelectedTextColor(this.textColorNormal);
                provinceView.setLineConfig(this.lineConfig);
                provinceView.setAdapter(new ArrayWheelAdapter(this.provider.provideFirstData()));
                provinceView.setCurrentItem(this.selectedFirstIndex);
                if (this.hideProvince) {
                    provinceView.setVisibility(View.GONE);
                }

                layout.addView(provinceView);
                final WheelView cityView = new WheelView(this.activity);
                cityView.setCanLoop(this.canLoop);
                cityView.setTextSize((float)this.textSize);
                cityView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                cityView.setUnSelectedTextColor(this.textColorNormal);
                cityView.setLineConfig(this.lineConfig);
                cityView.setAdapter(new ArrayWheelAdapter(this.provider.provideSecondData(this.selectedFirstIndex)));
                cityView.setCurrentItem(this.selectedSecondIndex);
                cityView.setLayoutParams(new LinearLayout.LayoutParams(cityWidth, -2));
                layout.addView(cityView);
                final WheelView countyView = new WheelView(this.activity);
                countyView.setCanLoop(this.canLoop);
                countyView.setTextSize((float)this.textSize);
                countyView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                countyView.setUnSelectedTextColor(this.textColorNormal);
                countyView.setLineConfig(this.lineConfig);
                countyView.setAdapter(new ArrayWheelAdapter(this.provider.provideThirdData(this.selectedFirstIndex, this.selectedSecondIndex)));
                countyView.setCurrentItem(this.selectedThirdIndex);
                countyView.setLayoutParams(new LinearLayout.LayoutParams(countyWidth, -2));
                if (this.hideCounty) {
                    countyView.setVisibility(View.GONE);
                }

                layout.addView(countyView);
                provinceView.setOnItemPickListener(new OnItemPickListener<String>() {
                    public void onItemPicked(int index, String item) {
                        selectedFirstItem = item;
                        selectedFirstIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onFirstWheeled(selectedFirstIndex, selectedFirstItem);
                        }

                        LogUtils.verbose(this, "change cities after province wheeled");
                        selectedSecondIndex = 0;
                        selectedThirdIndex = 0;
                        List<String> cities = provider.provideSecondData(selectedFirstIndex);
                        if (cities.size() > 0) {
                            cityView.setAdapter(new ArrayWheelAdapter(cities));
                            cityView.setCurrentItem(selectedSecondIndex);
                        } else {
                            cityView.setAdapter(new ArrayWheelAdapter(new ArrayList()));
                        }

                        List<String> counties = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                        if (counties.size() > 0) {
                            countyView.setAdapter(new ArrayWheelAdapter(counties));
                            countyView.setCurrentItem(selectedThirdIndex);
                        } else {
                            countyView.setAdapter(new ArrayWheelAdapter(new ArrayList()));
                        }

                    }
                });
                cityView.setOnItemPickListener(new OnItemPickListener<String>() {
                    public void onItemPicked(int index, String item) {
                        selectedSecondItem = item;
                        selectedSecondIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onSecondWheeled(selectedSecondIndex,selectedSecondItem);
                        }

                        LogUtils.verbose(this, "change counties after city wheeled");
                        selectedThirdIndex = 0;
                        List<String> counties = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                        if (counties.size() > 0) {
                            countyView.setAdapter(new ArrayWheelAdapter(counties));
                            countyView.setCurrentItem(selectedThirdIndex);
                        } else {
                            countyView.setAdapter(new ArrayWheelAdapter(new ArrayList()));
                        }

                    }
                });
                countyView.setOnItemPickListener(new OnItemPickListener<String>() {
                    public void onItemPicked(int index, String item) {
                        selectedThirdItem = item;
                        selectedThirdIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onThirdWheeled(selectedThirdIndex, selectedThirdItem);
                        }

                    }
                });
            } else {
                WheelListView provinceView = new WheelListView(this.activity);
                provinceView.setCanLoop(this.canLoop);
                provinceView.setLayoutParams(new LinearLayout.LayoutParams(provinceWidth, -2));
                provinceView.setTextSize(this.textSize);
                provinceView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                provinceView.setUnSelectedTextColor(this.textColorNormal);
                provinceView.setLineConfig(this.lineConfig);
                provinceView.setOffset(this.offset);
                layout.addView(provinceView);
                if (this.hideProvince) {
                    provinceView.setVisibility(View.GONE);
                }

                final WheelListView cityView = new WheelListView(this.activity);
                cityView.setCanLoop(this.canLoop);
                cityView.setLayoutParams(new LinearLayout.LayoutParams(cityWidth, -2));
                cityView.setTextSize(this.textSize);
                cityView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                cityView.setUnSelectedTextColor(this.textColorNormal);
                cityView.setLineConfig(this.lineConfig);
                cityView.setOffset(this.offset);
                layout.addView(cityView);
                final WheelListView countyView = new WheelListView(this.activity);
                countyView.setCanLoop(this.canLoop);
                countyView.setLayoutParams(new LinearLayout.LayoutParams(countyWidth, -2));
                countyView.setTextSize(this.textSize);
                countyView.setSelectedTextColor(Color.parseColor("#00DEFF"));
                countyView.setUnSelectedTextColor(this.textColorNormal);
                countyView.setLineConfig(this.lineConfig);
                countyView.setOffset(this.offset);
                layout.addView(countyView);
                if (this.hideCounty) {
                    countyView.setVisibility(View.GONE);
                }

                provinceView.setItems(this.provider.provideFirstData(), this.selectedFirstIndex);
                provinceView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
                    public void onItemSelected(int index, String item) {
                        selectedFirstItem = item;
                        selectedFirstIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onFirstWheeled(selectedFirstIndex, selectedFirstItem);
                        }

                        LogUtils.verbose(this, "change cities after province wheeled");
                        selectedSecondIndex = 0;
                        selectedThirdIndex = 0;
                        List<String> cities = provider.provideSecondData(selectedFirstIndex);
                        if (cities.size() > 0) {
                            cityView.setItems(cities, selectedSecondIndex);
                        } else {
                            cityView.setItems(new ArrayList());
                        }

                        List<String> counties = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                        if (counties.size() > 0) {
                            countyView.setItems(counties, selectedThirdIndex);
                        } else {
                            countyView.setItems(new ArrayList());
                        }

                    }
                });
                cityView.setItems(this.provider.provideSecondData(this.selectedFirstIndex), this.selectedSecondIndex);
                cityView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
                    public void onItemSelected(int index, String item) {
                        selectedSecondItem = item;
                        selectedSecondIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onSecondWheeled(selectedSecondIndex, selectedSecondItem);
                        }

                        LogUtils.verbose(this, "change counties after city wheeled");
                        selectedThirdIndex = 0;
                        List<String> counties = provider.provideThirdData(selectedFirstIndex, selectedSecondIndex);
                        if (counties.size() > 0) {
                            countyView.setItems(counties, selectedThirdIndex);
                        } else {
                            countyView.setItems(new ArrayList());
                        }

                    }
                });
                countyView.setItems(this.provider.provideThirdData(this.selectedFirstIndex, this.selectedSecondIndex), this.selectedThirdIndex);
                countyView.setOnWheelChangeListener(new WheelListView.OnWheelChangeListener() {
                    public void onItemSelected(int index, String item) {
                        selectedThirdItem = item;
                        selectedThirdIndex = index;
                        if (onMoreWheelListener != null) {
                            onMoreWheelListener.onThirdWheeled(selectedThirdIndex,selectedThirdItem);
                        }

                    }
                });
            }

            return layout;
        }
    }

    public void onSubmit() {
        if (this.onLinkageListener != null) {
            Province province = this.getSelectedProvince();
            City city = this.getSelectedCity();
            County county = null;
            if (!this.hideCounty) {
                county = this.getSelectedCounty();
            }

            this.onLinkageListener.onAddressPicked(province, city, county);
        }

    }

    public static class AddressProvider implements DataProvider {
        private List<String> firstList = new ArrayList();
        private List<List<String>> secondList = new ArrayList();
        private List<List<List<String>>> thirdList = new ArrayList();

        public AddressProvider(List<Province> provinces) {
            this.parseData(provinces);
        }

        public boolean isOnlyTwo() {
            return this.thirdList.size() == 0;
        }

        public List<String> provideFirstData() {
            return this.firstList;
        }

        public List<String> provideSecondData(int firstIndex) {
            return (List)this.secondList.get(firstIndex);
        }

        public List<String> provideThirdData(int firstIndex, int secondIndex) {
            return (List)((List)this.thirdList.get(firstIndex)).get(secondIndex);
        }

        private void parseData(List<Province> data) {
            int provinceSize = data.size();

            for(int x = 0; x < provinceSize; ++x) {
                Province pro = (Province)data.get(x);
                this.firstList.add(pro.getAreaName());
                List<City> cities = pro.getCities();
                List<String> xCities = new ArrayList();
                List<List<String>> xCounties = new ArrayList();
                int citySize = cities.size();

                for(int y = 0; y < citySize; ++y) {
                    City cit = (City)cities.get(y);
                    cit.setProvinceId(pro.getAreaId());
                    xCities.add(cit.getAreaName());
                    List<County> counties = cit.getCounties();
                    ArrayList<String> yCounties = new ArrayList();
                    int countySize = counties.size();
                    if (countySize == 0) {
                        yCounties.add(cit.getAreaName());
                    } else {
                        for(int z = 0; z < countySize; ++z) {
                            County cou = (County)counties.get(z);
                            cou.setCityId(cit.getAreaId());
                            yCounties.add(cou.getAreaName());
                        }
                    }

                    xCounties.add(yCounties);
                }

                this.secondList.add(xCities);
                this.thirdList.add(xCounties);
            }

        }
    }
}
