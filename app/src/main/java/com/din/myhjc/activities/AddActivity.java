package com.din.myhjc.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.din.myhjc.R;
import com.din.myhjc.adapter.SingleAdapter;
import com.din.myhjc.content.Single;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.databinding.ActivityAddBinding;
import com.din.myhjc.utils.ClearEditText;
import com.din.myhjc.utils.DateUtil;

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
        //  使 contentView 延伸到 statusBar
        Window window = getWindow();
        //  状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //  状态栏着色
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);
        init();
    }

    private void init() {
        //  设置为toolbar,去掉标题
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
        startLocation();
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
                startLocation();
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
                getInputDialog(bind.weather, "自定义天气");
                break;
            case R.id.mood:
                getInputDialog(bind.mood, "自定义心情");
                break;
            case R.id.location:
                getInputDialog(bind.location, "自定义地点");
                break;
            default:
                break;
        }
        return true;
    }

    //  自定义输入
    private void getInputDialog(TextView textView, String title) {
        //  获取layout
        LayoutInflater locationInflater = getLayoutInflater();
        View dialog = locationInflater.inflate(R.layout.dialog_input, null);
        //  获取控件ID
        ClearEditText editText = (ClearEditText) dialog.findViewById(R.id.locationText);
        editText.setText(textView.getText().toString());
        TextView diyText = (TextView) dialog.findViewById(R.id.diyText);
        diyText.setText(title);
        //  弹出提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textView.setText(editText.getText().toString());
            }
        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setView(dialog);
        builder.show();
    }

    //  页面跳转
    private void startMainActivity() {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    //  获取天气
//    private void showWeather(){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();
//                    Request request = new Request.Builder().url(url).build();
//                    Response response = client.newCall(request).execute();
//                    String result = response.body().string();
//                    getWeather(result);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
//
//    private void getWeather(final String data){
//             try {
//                    //  将所有数据放在一个jsonObject中
//                    JSONObject jsonObject = new JSONObject(data);
//                    //  取出为results的key
//                    String results = jsonObject.getString("results");
//                    //  该key对应的值是一个数组,因此使用数组存储
//                    JSONArray jsonArray = new JSONArray(results);
//                    //  取出json数组中json对象为0的下标
//                    JSONObject second = jsonArray.getJSONObject(0);
//                    //  取出key为now的值
//                    JSONObject now = second.getJSONObject("now");
//                    //  取出key为text的值
//                    String text = now.getString("text");
//                    bind.weather.setText(text);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });
//    }


    //  定位
    private String city = null, direction = null;

    private void startLocation() {
        AMapLocationClient client = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setNeedAddress(true);
        option.setMockEnable(true);
        option.setHttpTimeOut(10000);
        option.setLocationCacheEnable(false);
        AMapLocationListener listener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        city = aMapLocation.getCity();
                        direction = aMapLocation.getDistrict();
                        for (int i = 0; i < city.length(); i++) {
                            if (city.substring(i, i + 1).equals("市")) {
                                city = city.substring(0, i);
                                bind.location.setText(direction);
//                                url = "https://api.seniverse.com/v3/weather/now.json?key=zbzp8471lvyfrz1e&location=" + city + "&language=zh-Hans&unit=c";
//                                showWeather();
                                break;
                            }
                        }
                    } else {
                        bind.location.setText("定位失败");
                        Toast.makeText(AddActivity.this, "错误提示 : " + aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
        if (client != null) {
            client.setLocationOption(option);
            client.setLocationListener(listener);
            client.startLocation();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                client.onDestroy();
            }
        }, 10000);
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
        adapter.setOnItemClickListener(new SingleAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
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
            }
        });
    }
}