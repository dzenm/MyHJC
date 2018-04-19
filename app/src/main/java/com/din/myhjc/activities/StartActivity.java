package com.din.myhjc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ActivityStartBinding;

import java.util.Timer;
import java.util.TimerTask;


public class StartActivity extends AppCompatActivity {


    private ActivityStartBinding bind;
    //跳转时间间隔
    private int LENGTH = 3;
    private Timer timer;
    private SharedPreferences setting;
    private Boolean user_first;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_start);

        //  设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*
         *      使用Java代码动态加载图片设置全屏背景。这种方案的原理是
         *      根据显示屏幕的大小对图片进行缩放，从而对屏幕尺寸进行适配
         */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guide), size.x, size.y, true);
        bind.guides.setImageBitmap(bmp);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    LENGTH--;
                    bind.start.setText(LENGTH + " 秒");
                    if (LENGTH == 0) {
                        timer.cancel();
                        chooseActivity();
                    }
                }
            });
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        //  记录是否第一次启动
        timer = new Timer();
        setting = getSharedPreferences("com.dinzhenyan.myhjc", 0);
        user_first = setting.getBoolean("FirstStart", true);
        timer.schedule(timerTask, 1000, 1000);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                timer.cancel();
                chooseActivity();
                break;
            default:
                break;
        }
    }

    private void chooseActivity() {
        if (user_first) {
            setting.edit().putBoolean("FirstStart", false).commit();
            startAvtivity(GuideActivity.class);
        } else {
            startAvtivity(LoginActivity.class);
        }
    }

    public void startAvtivity(Class<?> cls) {
        startActivity(new Intent(StartActivity.this, cls));
        finish();
    }
}