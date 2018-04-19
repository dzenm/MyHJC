package com.din.myhjc.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cn.bmob.v3.Bmob;

/**
 * Created by dinzhenyan on 2018/4/16.
 */

public class SplashActivity extends Activity {

    private SharedPreferences setting;
    private Boolean user_first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  比目云初始化,任何地方用到Bmob之前,都必须初始化,否则会报错
        Bmob.initialize(this, "40ccc268b75322bdfd1bc8f46f7de3d2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //  记录是否第一次启动
        setting = getSharedPreferences("com.dinzhenyan.myhjc", 0);
        user_first = setting.getBoolean("FirstStart", true);
        chooseActivity();
    }

    //  按需选择
    private void chooseActivity() {
        if (user_first) {
            setting.edit().putBoolean("FirstStart", false).commit();
            startAvtivity(GuideActivity.class);
        } else {
            startAvtivity(LoginActivity.class);
        }
    }

    //  选择启动的activity
    public void startAvtivity(Class<?> cls) {
        startActivity(new Intent(SplashActivity.this, cls));
        finish();
    }
}