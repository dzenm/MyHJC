package com.din.myhjc.activities;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.din.myhjc.R;
import com.din.myhjc.broadcast.RegisterBroadcast;
import com.din.myhjc.fragment.FragmentUtils;
import com.din.myhjc.fragment.MainFragment;
import com.din.myhjc.fragment.Memo;
import com.din.myhjc.fragment.Notes;
import com.din.myhjc.utils.CirclePicture;
import com.din.myhjc.utils.FileUtil;
import com.din.myhjc.utils.PermissionUtils;
import com.din.myhjc.utils.PhotoUtil;
import com.din.myhjc.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 *   _____________________
 *  |________    _________|
 *           |  |
 *           |  |
 *           |  |
 *           |  |
 *       __  |  |
 *       \  \|  |
 *        \____/
 */
public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;
    private Notes notes;
    private Memo memo;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ImageView headphoto, background;
    private TextView username;
    private FragmentUtils fragmentUtils;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.removeStatusBar(this);        //移除StatusBar
        PermissionUtils.verifyPermissions(this, new int[]{22, 23});     //动态权限获取
        fetchUserInfo();    //Bmob信息同步
        new RegisterBroadcast().register(this);   //注册网络广播
        setContentView(R.layout.activity_main);
        initView();
    }

    //-------- 初始化操作
    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navView = (NavigationView) findViewById(R.id.navView);
        View headerView = navView.getHeaderView(0);
        headphoto = (ImageView) headerView.findViewById(R.id.headPhoto);
        username = (TextView) headerView.findViewById(R.id.username);
        background = (ImageView) headerView.findViewById(R.id.bg);

        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        background.setOnClickListener(this::onClick);
        username.setOnClickListener(this::onClick);

        navView.setItemIconTintList(null);  // 侧滑菜单图标设置为本来颜色
        background.setImageResource(R.drawable.guide_first);

        mainFragment = new MainFragment();
        notes = new Notes();
        memo = new Memo();
        List<Fragment> list = new ArrayList<>();
        list.add(mainFragment);
        list.add(notes);
        list.add(memo);
        fragmentUtils = new FragmentUtils(this, R.id.frameLayout, list);
        fragmentUtils.addFragment();

        //  显示Fragment
        String activitys = getIntent().getStringExtra("select_fragment");
        if (TextUtils.isEmpty(activitys)) {
            navView.getMenu().getItem(0).setChecked(true);
            fragmentUtils.showFragment(mainFragment);
        } else {
            if (activitys.equals("Notes")) {
                navView.getMenu().getItem(1).setChecked(true);
                fragmentUtils.showFragment(notes);
            } else if (activitys.equals("Memo")) {
                navView.getMenu().getItem(2).setChecked(true);
                fragmentUtils.showFragment(memo);
            }
        }
    }

    //  判断缓存账户信息是否正确
    private void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this, "请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });


    }

    //-------- 侧滑状态点击返回时关闭侧滑
    private long clickTime = 0;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            //获取第一次按键时间
            long currentTime = System.currentTimeMillis();
            //比较两次按键时间差
            if ((currentTime - clickTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                clickTime = currentTime;
            } else {
                //退出程序
                System.exit(0);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Bitmap bitmap = FileUtil.readPhoto("Photo", "headphoto");
        if (bitmap != null) {
            headphoto.setImageDrawable(new CirclePicture(bitmap));
        } else {
            headphoto.setImageResource(R.drawable.headphoto);
        }

        //  判断登录状态
        BmobUser currentUser = BmobUser.getCurrentUser();
        String user = null;
        if (currentUser == null) {
            user = "未登录";
        } else {
            user = (String) BmobUser.getObjectByKey("username");
        }
        username.setText(user);
    }

    //-------- 侧滑状态栏里按钮的点击事件
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            new Handler().postDelayed(() -> {
                drawerLayout.closeDrawers();
            }, 100);
        }
        switch (item.getItemId()) {
            case R.id.diary:
                fragmentUtils.showFragment(mainFragment);
                break;
            case R.id.note:
                fragmentUtils.showFragment(notes);
                break;
            case R.id.memo:
                fragmentUtils.showFragment(memo);
                break;
            case R.id.logout:
                new Handler().postDelayed(() -> {
                    BmobUser.logOut();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }, 300);
                break;
            case R.id.setting:
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                }, 300);
                break;
            default:
                break;
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                drawerLayout.closeDrawer(GravityCompat.START);
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }, 300);
                break;
            case R.id.bg:
                drawerLayout.closeDrawer(GravityCompat.START);
                PhotoUtil.selectPictureFromAlbum(this);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        new RegisterBroadcast().unregister(this);
    }
}