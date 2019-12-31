package com.tiancaijiazu.app.activitys.early;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.calendar.CalendarDay;
import com.tiancaijiazu.app.activitys.calendar.MaterialCalendarView;
import com.tiancaijiazu.app.activitys.calendar.OnDateLongClickListener;
import com.tiancaijiazu.app.activitys.calendar.OnDateSelectedListener;
import com.tiancaijiazu.app.activitys.calendar.OnMonthChangedListener;
import com.tiancaijiazu.app.activitys.calendar.decorators.EventDecorator;
import com.tiancaijiazu.app.base.activity.BaseActivity;
import com.tiancaijiazu.app.differentiateenum.DifferentiateEnum;
import com.tiancaijiazu.app.mvp.Presenter;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.mvp.IView;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClockOnActivity extends BaseActivity<IView, Presenter<IView>> implements IView ,OnDateSelectedListener, OnMonthChangedListener, OnDateLongClickListener {


    @BindView(R.id.iv_finis)
    ImageView mIvFinis;
    @BindView(R.id.title_name)
    TextView mTitleName;
    @BindView(R.id.calendarView)
    MaterialCalendarView mCalendarView;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    @Override
    protected void initEventAndData() {
        ScreenStatusUtil.setFillDip(this);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnDateLongClickListener(this);
        mCalendarView.setOnMonthChangedListener(this);
        AddDecorator();
    }
    private void AddDecorator() {

        LocalDate temp = LocalDate.now().minusDays(12);
        final ArrayList<CalendarDay> dates = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final CalendarDay day = CalendarDay.from(temp);

            dates.add(day);
            Log.i("yx", "doInBackground: " + dates.get(i).getDay());
            if(i==2){
                temp = temp.plusDays(2);
            }else {
                temp = temp.plusDays(1);
            }
        }
        //增加有红点标志
        mCalendarView.addDecorator(new EventDecorator(Color.parseColor("#00DEFF"), dates));
    }
    @Override
    public void onDateSelected(
            @NonNull MaterialCalendarView widget,
            @NonNull CalendarDay date,
            boolean selected) {

    }

    @Override
    public void onDateLongClick(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date) {
        final String text = String.format("%s is available", FORMATTER.format(date.getDate()));
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        //noinspection ConstantConditions

    }
    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_clock_on;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.iv_finis})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_finis:
                finish();
                break;
        }
    }
}
