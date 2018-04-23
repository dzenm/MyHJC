package com.din.myhjc.utils;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 任意时间转星期
     *
     * @param weekDate
     * @return
     */
    public String DateToWeek(String weekDate) {
        String[] WEEK = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        String week = null;
        try {
            //-------- 设置日期格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            //-------- 获取Calendar实例
            Calendar calendar = Calendar.getInstance();
            //-------- 将时间设置为固定格式并赋值给date
            Date dates = simpleDateFormat.parse(weekDate);
            //-------- calendar的实例设置时间为date
            calendar.setTime(dates);
            //-------- 将日期转化为星期
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            //-------- 将日期传给字符串
            week = (WEEK[dayOfWeek - 1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 日期转时间戳
     *
     * @param s
     * @return
     */
    public long dateToLong(String s) {
        long l = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        try {
            Date date = simpleDateFormat.parse(s);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 当前时间戳转时间
     *
     * @return
     */
    public String DateToSimple() {
        //-------- 获取时间戳
        Date dateSimple = new Date();
        //-------- 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        //-------- 将时间戳转化为SimpleDateFormat格式
        String date = simpleDateFormat.format(dateSimple);
        //-------- calendar的实例设置时间为date
        return date;
    }

    /**
     * 任意时间戳转时间
     *
     * @param dates
     * @return
     */
    public String DateToSimple(String dates) {

        //  设置时间格式
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        //  字符串转long
        Long time = Long.parseLong(dates);
        //  格式化时间
        String returnTime = format.format(time);

        return returnTime;
    }

    /**
     * 设置时间为Date格式
     *
     * @return
     */
    public String SimpleToDate(String dates) {

        //-------- 设置日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
        //-------- 将时间戳转化为SimpleDateFormat格式
        String date = simpleDateFormat.format(dates);
        //-------- calendar的实例设置时间为date
        return date;
    }

    /**
     * 日期View获取PickerDialog的日期
     *
     * @param context
     */
    public void DateTimeDialog(final Context context, final TextView date, final TextView time) {

        Calendar calendarDate = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //--------monthOfYear 得到的月份会减1所以我们要加1
                String mMonth, mDay;

                if (monthOfYear < 10) {
                    mMonth = "0" + (monthOfYear + 1);
                } else {
                    mMonth = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth < 10) {
                    mDay = "0" + (dayOfMonth);
                } else {
                    mDay = String.valueOf(dayOfMonth);
                }
                date.setText(String.valueOf(year) + "-" + mMonth + "/" + mDay);
                Calendar calendarTime = Calendar.getInstance();
                new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String mHour, mMinute;
                        if (hourOfDay < 10) {
                            mHour = "0" + hourOfDay;
                        } else {
                            mHour = String.valueOf(hourOfDay);
                        }
                        if (minute < 10) {
                            mMinute = "0" + minute;
                        } else {
                            mMinute = String.valueOf(minute);
                        }
                        time.setText(mHour + ":" + mMinute);
                    }
                }, calendarTime.get(Calendar.HOUR_OF_DAY), calendarTime.get(Calendar.MINUTE), true).show();

            }

        }, calendarDate.get(Calendar.YEAR), calendarDate.get(Calendar.MONTH), calendarDate.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 日期View获取PickerDialog的日期
     *
     * @param context
     */
    public void dateDialog(Context context, final TextView date) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //--------monthOfYear 得到的月份会减1所以我们要加1
                String mMonth, mDay;
                if (monthOfYear < 10) {
                    mMonth = "0" + (monthOfYear + 1);
                } else {
                    mMonth = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth < 10) {
                    mDay = "0" + (dayOfMonth);
                } else {
                    mDay = String.valueOf(dayOfMonth);
                }
                date.setText(String.valueOf(year) + "-" + mMonth + "-" + mDay);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 日期View获取PickerDialog的日期
     *
     * @param context
     */

    public void dateDialog(Context context, TextView month, TextView day) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //--------monthOfYear 得到的月份会减1所以我们要加1
                String mMonth, mDay;
                if (monthOfYear < 10) {
                    mMonth = "0" + (monthOfYear + 1);
                } else {
                    mMonth = String.valueOf(monthOfYear + 1);
                }
                if (dayOfMonth < 10) {
                    mDay = "0" + (dayOfMonth);
                } else {
                    mDay = String.valueOf(dayOfMonth);
                }
                month.setText(mDay);
                day.setText("-" + mMonth + "月");
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间View获取PickerDialog的时间
     *
     * @param context
     * @param hourText
     * @param minuteText
     */
    public void timeDialog(Context context, final TextView hourText, final TextView minuteText) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hourText.setText(Integer.toString(hourOfDay));
                minuteText.setText(Integer.toString(minute));

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

    }


    /**
     * 获取该时间在该星期的第一天和最后一天日期
     *
     * @param data
     * @return
     * @throws ParseException
     */
    public String getFirstAndLastOfWeek(String data) throws ParseException {

        Calendar calendar = Calendar.getInstance();

        String todayDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String todayTime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data));
        int d = 0;
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            d = -6;
        } else {
            d = 2 - calendar.get(Calendar.DAY_OF_WEEK);
        }
        // 所在星期开始日期
        calendar.add(Calendar.DAY_OF_WEEK, d);
        String firstDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        // 所在星期结束日期
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        String lastDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
        return firstDate + "-" + lastDate;
    }
}