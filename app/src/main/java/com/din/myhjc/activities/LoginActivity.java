package com.din.myhjc.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.din.myhjc.R;
import com.din.myhjc.broadcast.RegisterBroadcast;
import com.din.myhjc.databinding.ActivityLoginBinding;
import com.din.myhjc.utils.StatusBarUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import me.leefeng.promptlibrary.PromptDialog;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityLoginBinding bind;
    private RegisterBroadcast broadcast;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------- 在加载布局文件前判断是否登陆过
        BmobUser currentUser = BmobUser.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        //  去除statusBar
        StatusBarUtils.removeStatusBar(this);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_login);

        //  记住账户密码
        SharedPreferences preferences = getSharedPreferences("user_info", 0);
        String user = preferences.getString("username", "");
        String password = preferences.getString("password", "");
        if (user != null && password != null) {
            bind.user.setText(user);
            bind.password.setText(password);
        }
        /*
         *  设置ToolBar，隐藏ActionBar
         */
        setSupportActionBar(bind.toolbar);

        /*
         * 去除Toolbar自有的Title，在布局中使用app:title=""是不会起作用的
         * 还是会显示ActionBar的标题
         */
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bind.headPhoto.setOnClickListener(this);
        bind.animalView.setOnAnimationEndListener(this::onEnd);
        broadcast = new RegisterBroadcast();
        promptDialog = new PromptDialog(this);
    }

    //-------- 侧滑状态点击返回时关闭侧滑 --------------------------
    private long pressedTime = 0;

    @Override
    public void onBackPressed() {
        //获取第一次按键时间
        long nowTime = System.currentTimeMillis();
        //比较两次按键时间差
        if ((nowTime - pressedTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            pressedTime = nowTime;
        } else {
            //退出程序
            System.exit(0);
        }
    }


    /**
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                //  强制隐藏输入法
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(bind.content.getWindowToken(), 0);
                broadcast.register(this);
                String username, password;
                username = bind.user.getText().toString();
                password = bind.password.getText().toString();
                if (bind.user.length() > 0 && bind.password.length() > 0) {
                    BmobUser bmobUser = new BmobUser();
                    bmobUser.loginByAccount(username, password, new LogInListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                //  登录转场动画
                                bind.animalView.startAnimation("LOGIN UP");
                                bind.content.setVisibility(View.GONE);
                                bind.register.setVisibility(View.GONE);
                                bind.login.setVisibility(View.GONE);
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        promptDialog.showError("账号或密码有误");
                                    }
                                }, 500);
                            }
                        }
                    });
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            promptDialog.showWarn("请输入账号和密码");
                            bind.user.setShakeAnimation();
                            bind.password.setShakeAnimation();
                        }
                    }, 200);
                }
                break;
            case R.id.register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    public void onEnd() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}