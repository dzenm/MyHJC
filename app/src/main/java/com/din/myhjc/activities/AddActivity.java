package com.din.myhjc.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Size;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.din.myhjc.R;
import com.din.myhjc.adapter.SingleAdapter;
import com.din.myhjc.content.Single;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.databinding.ActivityAddBinding;
import com.din.myhjc.utils.DateUtil;
import com.din.myhjc.utils.DiyDialogUtils;
import com.din.myhjc.utils.LocationUtils;
import com.din.myhjc.utils.StatusBarUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;

public class AddActivity extends AppCompatActivity {

    private String TAG, Id, week;
    private DateUtil dateUtil;
    private ActivityAddBinding bind;
    private boolean focus = false;
    private PromptDialog promptDialog;
    private List<Single> list;
    private SingleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(AddActivity.this, R.layout.activity_add);
        StatusBarUtils.removeStatusBar(this);           //  去除statusBar
        init();
    }

    private void init() {
        //  设置为toolbar
        setSupportActionBar(bind.toolbar);
        getSupportActionBar().setTitle("");
        //  初始化
        dateUtil = new DateUtil();
        promptDialog = new PromptDialog(this);

        //  判断是从添加按钮进来还是更新按钮
        TAG = getIntent().getStringExtra("TAG");
        if (TAG.equals("AddActivity")) {
            initAdd();
        } else {
            initUpdate();
        }

        bind.back.setOnClickListener(this::onClick);
        bind.confirm.setOnClickListener(this::onClick);
        bind.date.setOnClickListener(this::onClick);
        bind.weather.setOnClickListener(this::onClick);
        bind.mood.setOnClickListener(this::onClick);
        bind.location.setOnClickListener(this::onClick);

        bind.weather.setOnLongClickListener(this::onLongClick);
        bind.mood.setOnLongClickListener(this::onLongClick);
        bind.location.setOnLongClickListener(this::onLongClick);
    }

    //  添加界面初始化
    private void initAdd() {
        //  设置当前时间的日期
        bind.date.setText(dateUtil.DateToSimple().substring(0, 10));
        bind.location.setText("定位中...");
        new LocationUtils().startLocation(this, bind.location);
    }

    //  更新界面初始化
    private void initUpdate() {
        //  更新页面ToolBar按钮和文本设置
        bind.confirm.setImageResource(R.drawable.btn_add);
        //-------从主页面点击之后接收ID，从litepal读取该行数据显示
        Id = getIntent().getStringExtra("id");
        List<DataDiary> datas = DataSupport.where("id=?", Id).find(DataDiary.class);
        String dateTime;
        for (DataDiary data : datas) {
            dateTime = new DateUtil().DateToSimple(String.valueOf(data.getDatetime()));
            bind.content.setText(data.getContents());
            bind.date.setText(dateTime.substring(0, 10));
            bind.weather.setText(data.getWeather());
            bind.mood.setText(data.getMood());
            bind.location.setText(data.getLocation());
        }
        //  EditText失去焦点
        bind.content.setFocusable(false);
        bind.content.setFocusableInTouchMode(false);
    }

    @Override
    public void onBackPressed() {
        if (focus) {
            bind.content.setFocusable(false);
            bind.content.setFocusableInTouchMode(false);
            bind.confirm.setImageResource(R.drawable.btn_add);
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(bind.content.getWindowToken(), 0);
            saveLocalData().updateAll("id=?", Id);
            focus = false;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                //  如果此时文本框可以获取焦点,则返回按钮去除焦点,否则点击返回按钮回到上一个界面
                if (focus) {
                    bind.content.setFocusable(false);
                    bind.content.setFocusableInTouchMode(false);
                    bind.confirm.setImageResource(R.drawable.btn_add);
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(bind.content.getWindowToken(), 0);
                    saveLocalData().updateAll("id=?", Id);
                    focus = false;
                } else {
                    finish();
                }
                break;
            case R.id.confirm:
                if (!bind.content.getText().toString().equals("") && !bind.mood.getText().toString().equals("")) {
                    //  判断是添加内容还是更新内容
                    if (TAG.equals("AddActivity")) {
                        saveLocalData().save();
                        promptDialog.showSuccess("添加成功");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startMainActivity();
                            }
                        }, 500);
                    } else {
                        if (focus) {
                            saveLocalData().updateAll("id=?", Id);
                            promptDialog.showSuccess("更新成功");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startMainActivity();
                                }
                            }, 500);
                        } else {
                            //  如果此时文本框没有获取焦点,则点击之后,EditText获得焦点
                            bind.content.setFocusable(true);
                            bind.content.setFocusableInTouchMode(true);
                            bind.content.requestFocus();
                            bind.content.setSelection(bind.content.getText().toString().length());
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).showSoftInput(bind.content, 0);
                            bind.confirm.setImageResource(R.drawable.btn_confirm);
                            focus = true;
                        }
                    }
                } else {
                    promptDialog.showWarn("请输入完整的信息");
                }
                break;
            case R.id.date:
                //  日期时间选择
                dateUtil.dateDialog(this, bind.date);
                break;
            case R.id.weather:
                initData("weather");
                break;
            case R.id.mood:
                initData("mood");
                break;
            case R.id.location:
                new LocationUtils().startLocation(this, bind.location);
                break;
            default:
                break;
        }
    }

    //  本地数据
    private DataDiary saveLocalData() {
        //  本地数据
        week = bind.date.getText().toString();
        String time = Calendar.HOUR_OF_DAY + ":" + Calendar.MINUTE;

        DataDiary dataDiary = new DataDiary();
        dataDiary.setContents(bind.content.getText().toString());
        dataDiary.setDatetime(new DateUtil().dateToLong(bind.date.getText().toString() + time));
        dataDiary.setWeek(new DateUtil().DateToWeek(week));
        dataDiary.setWeather(bind.weather.getText().toString());
        dataDiary.setMood(bind.mood.getText().toString());
        dataDiary.setLocation(bind.location.getText().toString());
        return dataDiary;
    }

    //  长按事件
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.weather:
                DiyDialogUtils.getInputDialog(this, bind.weather, "自定义天气");
                break;
            case R.id.mood:
                DiyDialogUtils.getInputDialog(this, bind.mood, "自定义心情");
                break;
            case R.id.location:
                DiyDialogUtils.getInputDialog(this, bind.location, "自定义地点");
                break;
            default:
                break;
        }
        return true;
    }

    //  页面跳转
    private void startMainActivity() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private int[] id = null;
    private int[] moodID = new int[]{
            R.drawable.mood_00, R.drawable.mood_01, R.drawable.mood_02, R.drawable.mood_03, R.drawable.mood_04,
            R.drawable.mood_10, R.drawable.mood_11, R.drawable.mood_12, R.drawable.mood_13, R.drawable.mood_14,
            R.drawable.mood_20, R.drawable.mood_21
    };
    private int[] weatherID = new int[]{
            R.drawable.weather_00, R.drawable.weather_01, R.drawable.weather_02, R.drawable.weather_13, R.drawable.weather_23,
            R.drawable.weather_10, R.drawable.weather_11, R.drawable.weather_12, R.drawable.weather_13, R.drawable.weather_14,
            R.drawable.weather_20, R.drawable.weather_21, R.drawable.weather_22, R.drawable.weather_23, R.drawable.weather_24};

    private String[] text = null;
    private String[] moodText = new String[]{
            "开心", "伤心", "笑哭", "受伤", "流汗",
            "震惊", "眨眼", "天使", "思考", "懵逼",
            "疲惫", "难过"
    };
    private String[] weatherText = new String[]{
            "晴天", "多云", "阴天", "扬尘", "雾霾",
            "小雨", "中雨", "大雨", "大风", "雷阵雨",
            "小雪", "中雪", "大雪", "冰雹", "雨夹雪"
    };
    private int number = 0;
    private String flag = null;

    /**
     * 使用recyclerView制作滑动的选择器
     *
     * @param data
     */
    private void initData(String data) {
        bind.recyclerView.setVisibility(View.VISIBLE);
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        if (data.equals("mood")) {
            id = moodID;
            text = moodText;
        } else if (data.equals("weather")) {
            id = weatherID;
            text = weatherText;
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        //  心情和天气的交替效果
        if (number == 0) {
            //  第一次点击时,弹出具体的内容,并记录点击按钮的flag
            initChoose(id, text, data);
            flag = data;
        } else {
            //  如果没有点击具体内容,会记录为第二次点击,判断第二次点击时flag的值是否和第一次flag的值相同
            if (!flag.equals(data)) {
                //  如果不同,刷新弹出的具体内容。并记录本次点击的flag,再去判断下一次的点击
                list.clear();
                adapter.notifyDataSetChanged();
                initChoose(id, text, data);
                flag = data;
            } else {
                //  如果相同,弹出的具体内容会隐藏。同时清空并刷新recyclerView
                number = 0;
                list.clear();
                adapter.notifyDataSetChanged();
                bind.recyclerView.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 显示RecyclerView的数据和点击事件
     *
     * @param id
     * @param text
     * @param data
     */
    private void initChoose(int[] id, String[] text, String data) {
        for (int i = 0; i < id.length; i++) {
            list.add(new Single(id[i], text[i]));
        }
        if (adapter == null) {
            adapter = new SingleAdapter(list);
        }
        bind.recyclerView.setAdapter(adapter);
        number++;
        adapter.setOnItemClickListener(position -> {
            Single single = list.get(position);
            Drawable drawable = getResources().getDrawable(single.getImageId()); //获取图片
            drawable.setBounds(0, 0, 0, 0);  //设置图片参数
            if (data.equals("mood")) {
                //设置到哪个控件的位置()
                bind.mood.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
                bind.mood.setText(single.getText());
            } else if (data.equals("weather")) {
                //设置到哪个控件的位置()
                bind.weather.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
                bind.weather.setText(single.getText());
            }
            //  如果点击了具体的内容,弹出的具体内容会隐藏。同时清空并刷新recyclerView
            number = 0;
            list.clear();
            adapter.notifyDataSetChanged();
            bind.recyclerView.setVisibility(View.INVISIBLE);
        });
    }
}