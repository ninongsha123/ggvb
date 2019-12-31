package com.tiancaijiazu.app.activitys.activitypage.collegeactivitys;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.early.datepicker.DateFormatUtils;
import com.tiancaijiazu.app.activitys.shopping_activity.datapickerclock.CustomLookDatePicker;
import com.tiancaijiazu.app.activitys.shopping_activity.datapickerclock.LookDateFormatUtils;
import com.tiancaijiazu.app.base.activity.SimpleActivity;
import com.tiancaijiazu.app.utils.PreUtils;
import com.tiancaijiazu.app.utils.ScreenStatusUtil;
import com.tiancaijiazu.app.utils.TimeUtil;
import com.tiancaijiazu.app.utils.receiver.AlarmReceiver;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *  学院-早教学院-设置闹钟
 *
 */

public class ClockActivity extends SimpleActivity {

    @BindView(R.id.time1)
    TextView time1;
    @BindView(R.id.clear1)
    ImageView clear1;
    @BindView(R.id.time2)
    TextView time2;
    @BindView(R.id.clear2)
    ImageView clear2;
    @BindView(R.id.time3)
    TextView time3;
    @BindView(R.id.clear3)
    ImageView clear3;
    @BindView(R.id.time4)
    TextView time4;
    @BindView(R.id.clear4)
    ImageView clear4;
    @BindView(R.id.time5)
    TextView time5;
    @BindView(R.id.clear5)
    ImageView clear5;
    @BindView(R.id.time6)
    TextView time6;
    @BindView(R.id.clear6)
    ImageView clear6;
    @BindView(R.id.time7)
    TextView time7;
    @BindView(R.id.clear7)
    ImageView clear7;
    @BindView(R.id.rlout)
    RelativeLayout rlout;
    @BindView(R.id.r7)
    RelativeLayout r7;
    @BindView(R.id.iv_finis)
    ImageView ivFinis;
    @BindView(R.id.week_one)
    TextView mWeekOne;
    @BindView(R.id.week_two)
    TextView mWeekTwo;  @BindView(R.id.a)
    TextView a;
    @BindView(R.id.week_there)
    TextView mWeekThere;
    @BindView(R.id.week_four)
    TextView mWeekFour;
    @BindView(R.id.week_five)
    TextView mWeekFive;
    @BindView(R.id.week_six)
    TextView mWeekSix;
    @BindView(R.id.week_seven)
    TextView mWeekSeven;

    private PopupWindow mPopupWindow;
    private View inflate;
    private int h;
    private int m;
    private int hour;
    private int minute;
    private ImageView ok;
    private Calendar c = Calendar.getInstance();
    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h
    private CustomLookDatePicker mLookDatePicker;
    private String time;
    @Override
    protected void initEventAndData() {

        //沉浸式状态栏
        ScreenStatusUtil.setFillDip(this);
        //获取当前时间
        getTime();
        //闹钟弹窗
        showPop();
        String week_one = PreUtils.getString("week_one", "");
        Log.d("bjg", "initEventAndData: "+week_one);
        String week_two = PreUtils.getString("week_two", "");
        String week_there = PreUtils.getString("week_there", "");
        String week_four = PreUtils.getString("week_four", "");
        String week_five = PreUtils.getString("week_five", "");
        String week_six = PreUtils.getString("week_six", "");
        String week_seven = PreUtils.getString("week_seven", "");
        if (!week_one.equalsIgnoreCase("")){
            time1.setText(week_one);
            clear1.setImageResource(R.mipmap.clear_ok);
        }else {
            time1.setText("-- : --");
        }if (!week_two.equalsIgnoreCase("")){
            time2.setText(week_two);
            clear2.setImageResource(R.mipmap.clear_ok);
        }else {
            time2.setText("-- : --");
        }if (!week_there.equalsIgnoreCase("")){
            time3.setText(week_there);
            clear3.setImageResource(R.mipmap.clear_ok);
        }else {
            time3.setText("-- : --");
        }if (!week_four.equalsIgnoreCase("")){
            time4.setText(week_four);
            clear4.setImageResource(R.mipmap.clear_ok);
        }else {
            time4.setText("-- : --");
        }if (!week_five.equalsIgnoreCase("")){
            time5.setText(week_five);
            clear5.setImageResource(R.mipmap.clear_ok);
        }else {
            time5.setText("-- : --");
        }if (!week_six.equalsIgnoreCase("")){
            time6.setText(week_six);
            clear6.setImageResource(R.mipmap.clear_ok);
        }else {
            time6.setText("-- : --");
        }if (!week_seven.equalsIgnoreCase("")){
            time7.setText(week_seven);
            clear7.setImageResource(R.mipmap.clear_ok);
        }else {
            time7.setText("-- : --");
        }








    }

    @Override
    protected int creatrLayoutId() {
        return R.layout.activity_clock;
    }


    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgcolor) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgcolor;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);

    }

    @OnClick({R.id.time1, R.id.clear1, R.id.time2, R.id.clear2, R.id.time3, R.id.clear3, R.id.time4, R.id.clear4, R.id.time5, R.id.clear5, R.id.time6, R.id.clear6, R.id.time7, R.id.clear7, R.id.iv_finis})
    public void onViewClicked(View view) {
        String nowFore = TimeUtil.getNowFore();
        String[] split = nowFore.split(":");
        String s = split[0];
        String s1 = split[1];
        switch (view.getId()) {
            case R.id.time1:
                initTimerPicker(1);
                Log.d("bjg", "onViewClicked: " + nowFore);
                String text = time1.getText().toString();
                if (text != null) {
                    mLookDatePicker.show(text);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time1, clear1, 1);
                break;
            case R.id.clear1:
                time1.setText("-- : --");
                clear1.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 1,1, "闹钟响了", 0);
                PreUtils.putString("week_one","");
                cancleClock();
                break;
            case R.id.time2:
                initTimerPicker(2);
                String text2 = time2.getText().toString();
                if (text2 != null) {
                    mLookDatePicker.show(text2);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time2, clear2, 2);
                break;
            case R.id.clear2:
                time2.setText("-- : --");
                clear2.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 2,2, "闹钟响了", 0);
                PreUtils.putString("week_two","");
                cancleClock();
                break;
            case R.id.time3:
                initTimerPicker(3);
                String text3 = time3.getText().toString();
                if (text3 != null) {
                    mLookDatePicker.show(text3);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time3, clear3, 3);
                break;
            case R.id.clear3:
                time3.setText("-- : --");
                clear3.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 3,3, "闹钟响了", 0);
                PreUtils.putString("week_there","");
                cancleClock();
                break;
            case R.id.time4:
                initTimerPicker(4);
                String text4 = time4.getText().toString();
                if (text4 != null) {
                    mLookDatePicker.show(text4);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time4, clear4, 4);
                break;
            case R.id.clear4:
                time4.setText("-- : --");
                clear4.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 4,4, "闹钟响了", 0);
                PreUtils.putString("week_four","");
                cancleClock();
                break;
            case R.id.time5:
                initTimerPicker(5);
                String text5 = time5.getText().toString();
                if (text5 != null) {
                    mLookDatePicker.show(text5);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time5, clear5, 5);
                break;
            case R.id.clear5:
                time5.setText("-- : --");
                clear5.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 5,5, "闹钟响了", 0);
                PreUtils.putString("week_five","");
                cancleClock();
                break;
            case R.id.time6:
                initTimerPicker(6);
                String text6 = time6.getText().toString();
                if (text6 != null) {
                    mLookDatePicker.show(text6);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time6, clear6, 6);
                break;
            case R.id.clear6:
                time6.setText("-- : --");
                clear6.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 6,6, "闹钟响了", 0);
                PreUtils.putString("week_six","");
                cancleClock();
                break;
            case R.id.time7:
                initTimerPicker(7);
                String text7 = time7.getText().toString();
                if (text7 != null) {
                    mLookDatePicker.show(text7);
                } else {
                    mLookDatePicker.show(nowFore);
                }
                Ok_Click(time7, clear7, 7);
                break;
            case R.id.clear7:
                time7.setText("-- : --");
                clear7.setImageResource(R.color.colorWhite);
                AlarmManagerUtil.setAlarm(ClockActivity.this, 0, Integer.parseInt(s), Integer.parseInt(s1), 7,7, "闹钟响了", 0);
                PreUtils.putString("week_seven","");
                cancleClock();
                break;
            case R.id.iv_finis:
                finish();
                break;

        }
    }

    private void initTimerPicker(int random) {
        String beginTime = "2018-10-17 00:00";
        String endTime = DateFormatUtils.long2Str(System.currentTimeMillis(), true);
        // 通过日期字符串初始化日期，格式请用：yyyy-MM-dd HH:mm
        mLookDatePicker = new CustomLookDatePicker(this, new CustomLookDatePicker.Callback() {
            @Override
            public void onTimeSelected(long timestamp) {
                String s = LookDateFormatUtils.long2Str(timestamp, true);
                String[] split = s.split(" ");
                String s1 = split[0];
                String s2 = split[1];
                String[] split1 = s2.split(":");
                String s3 = split1[0];
                String s4 = split1[1];
                time=s2;
                if ("1".equalsIgnoreCase(random + "")) {
                    Log.d("bjg", "onTime" + s1 + "...." + s2);
                    time1.setText(s2);
                    clear1.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 1,1, "闹钟响了", 0);
                    PreUtils.putString("week_one",s2);
                } else if ("2".equalsIgnoreCase(random + "")) {
                    time2.setText(s2);
                    clear2.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 2,2, "闹钟响了", 0);
                    PreUtils.putString("week_two",s2);
                } else if ("3".equalsIgnoreCase(random + "")) {
                    time3.setText(s2);
                    clear3.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 3,3, "闹钟响了", 0);
                    PreUtils.putString("week_there",s2);
                } else if ("4".equalsIgnoreCase(random + "")) {
                    time4.setText(s2);
                    clear4.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 4,4, "闹钟响了", 0);
                    PreUtils.putString("week_four",s2);
                } else if ("5".equalsIgnoreCase(random + "")) {
                    time5.setText(s2);
                    clear5.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 5,5, "闹钟响了", 0);
                    PreUtils.putString("week_five",s2);
                } else if ("6".equalsIgnoreCase(random + "")) {
                    time6.setText(s2);
                    clear6.setImageResource(R.mipmap.clear_ok);
                    Log.i("asdf",  Integer.parseInt(s4)+"onTimeSelected: "+Integer.parseInt(s3));
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 6,6, "闹钟响了", 0);
                    PreUtils.putString("week_six",s2);
                } else if ("7".equalsIgnoreCase(random + "")) {
                    time7.setText(s2);
                    clear7.setImageResource(R.mipmap.clear_ok);
                    AlarmManagerUtil.setAlarm(ClockActivity.this, 2, Integer.parseInt(s3), Integer.parseInt(s4), 7,7, "闹钟响了", 0);
                    PreUtils.putString("week_seven",s2);
                }
            }
        }, beginTime, endTime);
        // 允许点击屏幕或物理返回键关闭
        mLookDatePicker.setCancelable(true);
        // 显示时和分
        mLookDatePicker.setCanShowPreciseTime(true);
        // 允许循环滚动
        mLookDatePicker.setScrollLoop(true);
        // 允许滚动动画
        mLookDatePicker.setCanShowAnim(false);
    }

    private void showPop() {
        inflate = LayoutInflater.from(this).inflate(R.layout.clock, null);
        mPopupWindow = new PopupWindow();
        mPopupWindow.setWidth(ActionBar.LayoutParams.MATCH_PARENT);
        mPopupWindow.setHeight(ActionBar.LayoutParams.MATCH_PARENT);
        mPopupWindow.setContentView(inflate);
        mPopupWindow.setOutsideTouchable(true);
        //设置背景透明才能显示
        ok = inflate.findViewById(R.id.ok);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
    }

    //获取当前小时和分钟
    private void getTime() {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
    }

    //七天点击事件
    private void Ok_Click(TextView time, ImageView clear, int random) {
        ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (m <= 9) {
                    if (h == 0 && m == 0) {
                        if (minute <= 9) {
                            time.setText(hour + "" + ":" + "0" + minute + "");
                        }
                        if (hour <= 9) {
                            time.setText("0" + hour + "" + ":" + minute + "");
                        }
                        if (minute <= 9 && hour <= 9) {
                            time.setText("0" + hour + "" + ":" + "0" + minute + "");
                        }
                        if (minute > 9 && hour > 9) {
                            time.setText(hour + "" + ":" + minute + "");
                        }
                    } else {
                        time.setText(h + "" + ":" + "0" + m + "");
                    }

                }
                if (h <= 9) {
                    if (h == 0 && m == 0) {
                        if (minute <= 9) {
                            time.setText(hour + "" + ":" + "0" + minute + "");
                        }
                        if (hour <= 9) {
                            time.setText("0" + hour + "" + ":" + minute + "");
                        }
                        if (minute <= 9 && hour <= 9) {
                            time.setText("0" + hour + "" + ":" + "0" + minute + "");
                        }
                        if (minute > 9 && hour > 9) {
                            time.setText(hour + "" + ":" + minute + "");
                        }
                    } else {
                        time.setText("0" + h + "" + ":" + m + "");
                    }
                }
                if (m <= 9 && h <= 9) {
                    if (h == 0 && m == 0) {
                        if (minute <= 9) {
                            time.setText(hour + "" + ":" + "0" + minute + "");
                        }
                        if (hour <= 9) {
                            time.setText("0" + hour + "" + ":" + minute + "");
                        }
                        if (minute <= 9 && hour <= 9) {
                            time.setText("0" + hour + "" + ":" + "0" + minute + "");
                        }
                        if (minute > 9 && hour > 9) {
                            time.setText(hour + "" + ":" + minute + "");
                        }
                    } else {
                        time.setText("0" + h + "" + ":" + "0" + m + "");
                    }
                }
                if (m > 9 && h > 9) {
                    if (h == 0 && m == 0) {
                        if (minute <= 9) {
                            time.setText(hour + "" + ":" + "0" + minute + "");
                        }
                        if (hour <= 9) {
                            time.setText("0" + hour + "" + ":" + minute + "");
                        }
                        if (minute <= 9 && hour <= 9) {
                            time.setText("0" + hour + "" + ":" + "0" + minute + "");
                        }
                        if (minute > 9 && hour > 9) {
                            time.setText(hour + "" + ":" + minute + "");
                        }
                    } else {
                        time.setText(h + "" + ":" + m + "");
                    }
                }
                clear.setImageResource(R.mipmap.clear_ok);
                mPopupWindow.dismiss();
                Intent intent = new Intent(ClockActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(ClockActivity.this, random, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager) ClockActivity.this
                        .getSystemService(Context.ALARM_SERVICE);
                Log.e("侧试", random + "=================");
                switch (random) {
                    case 1:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 0);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 2:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 3:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 2);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 4:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 3);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 5:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 4);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 6:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 5);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                    case 7:
                        if (c.getTimeInMillis() > c.getTimeInMillis()) {
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 6);
                            c.set(Calendar.HOUR_OF_DAY, h);
                            c.set(Calendar.MINUTE, m);
                            c.set(Calendar.SECOND, 0);
                            c.set(Calendar.MILLISECOND, 0);
                        }
                        break;
                }
                am.setWindow(AlarmManager.RTC, c.getTimeInMillis(),
                        INTERVAL, sender);
            }
        });
    }

    private void cancleClock() {
        Intent intent = new Intent(ClockActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ClockActivity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}