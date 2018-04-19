package com.din.myhjc.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.din.myhjc.R;
import com.din.myhjc.broadcast.RegisterBroadcast;
import com.din.myhjc.databinding.ActivityRegisterBinding;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import me.leefeng.promptlibrary.PromptDialog;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding bind;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        bind = DataBindingUtil.setContentView(this, R.layout.activity_register);

        setSupportActionBar(bind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        promptDialog = new PromptDialog(this);
        bind.animalView.setOnAnimationEndListener(this::onEnd);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Button点击事件
     *
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register: {
                //  强制隐藏输入法
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(bind.content.getWindowToken(), 0);
                //  注册是否有网络广播
                new RegisterBroadcast().register(this);
                //  注册按钮监听事件
                String users, password, email, phoneNumber;
                users = bind.user.getText().toString();
                password = bind.password.getText().toString();
                email = bind.email.getText().toString();
                phoneNumber = bind.phone.getText().toString();

                //  获取输入框的输入内容
                if (!(users.equals("") && password.equals("") && email.equals("") && phoneNumber.equals(""))) {
                    BmobUser user = new BmobUser();
                    user.setUsername(users);
                    user.setPassword(password);
                    user.setMobilePhoneNumber(phoneNumber);
                    user.setEmail(email);
                    user.signUp(new SaveListener<BmobUser>() {
                        @Override
                        public void done(BmobUser bmobUser, BmobException e) {
                            if (e == null) {
                                //  登录转场动画
                                bind.animalView.startAnimation("SIGN UP");
                                bind.content.setVisibility(View.GONE);
                                bind.register.setVisibility(View.GONE);
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        promptDialog.showWarn("注册失败");
                                    }
                                }, 200);
                            }
                        }
                    });
                } else {
                    bind.register.setEnabled(false);
                }
            }
        }
    }

    public void onEnd() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    //  取注册是否有网络广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        new RegisterBroadcast().unregister(this);
    }
}
