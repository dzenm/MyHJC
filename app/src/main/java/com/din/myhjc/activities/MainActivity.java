package com.din.myhjc.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.din.myhjc.R;
import com.din.myhjc.fragment.FragmentUtils;
import com.din.myhjc.fragment.MainFragment;
import com.din.myhjc.fragment.Memo;
import com.din.myhjc.fragment.Notes;
import com.din.myhjc.utils.CirclePicture;
import com.din.myhjc.utils.FileUtil;
import com.din.myhjc.utils.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * _____________________
 * |________    _________|
 * |  |
 * |  |
 * |  |
 * |  |
 * __  |  |
 * \  \|  |
 * \____/
 */
public class MainActivity extends AppCompatActivity {


    private MainFragment mainFragment;
    private Notes notes;
    private Memo memo;
    private FrameLayout frameLayout;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ImageView headphoto;
    private FragmentUtils fragmentUtils;

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
        fetchUserInfo();
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        init();
    }


    //-------- 初始化操作
    private void init() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        //  侧滑栏里按钮监听事件
        navView = (NavigationView) findViewById(R.id.navView);
        navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        //  侧滑头部
        View headerView = navView.getHeaderView(0);
        headphoto = (ImageView) headerView.findViewById(R.id.imageView);
        TextView username = (TextView) headerView.findViewById(R.id.username);
        TextView emailText = (TextView) headerView.findViewById(R.id.email);

        //  头像读取
        Bitmap bitmap = new FileUtil().readPhoto("Photo", "HeadPhoto");
        if (bitmap != null) {
            headphoto.setImageDrawable(new CirclePicture(bitmap));
        } else {
            headphoto.setImageResource(R.drawable.headphoto);
        }
        headphoto.setOnClickListener(this::onClick);

        //  判断登录状态
        BmobUser currentUser = BmobUser.getCurrentUser();
        if (currentUser == null) {
            emailText.setText("");
            username.setText("未登录");
            username.setOnClickListener(this::onClick);
        } else {
            String user, email, password;
            user = (String) BmobUser.getObjectByKey("username");
            email = (String) BmobUser.getObjectByKey("email");
            password = (String) BmobUser.getObjectByKey("password");
            SharedPreferences preferences = getSharedPreferences("user_info", 0);
            SharedPreferences.Editor editor = preferences.edit();
            if (preferences.getString("username", "") == null) {
                editor.putString("username", user);
                editor.putString("password", password);
                editor.commit();
            }
            username.setText(user);
            emailText.setText(email);
        }

        //  初始化Fragment和切换
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


    //-------- 侧滑状态栏里按钮的点击事件
    public boolean onNavigationItemSelected(MenuItem item) {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawerLayout.closeDrawers();
                }
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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BmobUser.logOut();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }, 500);
                break;
            case R.id.setting:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    }
                }, 500);
                break;
            default:
                break;
        }
        return true;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.username:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.imageView:
                PhotoUtil photoUtil = new PhotoUtil();
                photoUtil.selectPictureFromAlbum(this);
                break;
            default:
                break;
        }
    }


    //  返回读取的照片
    private String path;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.PHOTOGRAPH:
                    //  拍照
                    File picture = null;
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        }
                    } else {
                        picture = new File(this.getFilesDir() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(MainActivity.this.getFilesDir() + PhotoUtil.imageName);
                        }
                    }
                    path = FileUtil.getFolder("temp");
                    // 生成一个地址用于存放剪辑后的图片
                    if (!TextUtils.isEmpty(path)) {
                        Uri graphUri = PhotoUtil.getPathToUri(path);
                        PhotoUtil.startPhotoZoom(MainActivity.this, Uri.fromFile(picture), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, graphUri);
                    }
                    break;
                case PhotoUtil.PHOTOZOOM:
                    //  裁剪照片
                    path = FileUtil.getFolder("temp");
                    //  生成一个地址用于存放剪辑后的图片
                    if (!TextUtils.isEmpty(path)) {
                        Uri imageUri = PhotoUtil.getPathToUri(path);
                        PhotoUtil.startPhotoZoom(MainActivity.this, data.getData(), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
                    }
                    break;
                case PhotoUtil.PHOTORESOULT:
                    //  在这里处理剪辑结果。能够获取缩略图，获取剪辑图片的地址。得到这些信息能够选则用于上传图片等等操作

                    Uri imageUri = data.getData();
                    //    头像压缩存储
                    if (imageUri != null) {
                        String[] filePathColumns = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(imageUri, filePathColumns, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
                        String imagePath = cursor.getString(columnIndex);
                        cursor.close();
                        //  设置参数
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true; // 只获取图片的大小信息，而不是将整张图片载入在内存中，避免内存溢出
                        BitmapFactory.decodeFile(imagePath, options);
                        int height = options.outHeight;
                        int width = options.outWidth;
                        int inSampleSize = 2; // 默认像素压缩比例，压缩为原图的1/2
                        int minLen = Math.min(height, width); // 原图的最小边长
                        if (minLen > 100) { // 如果原始图像的最小边长大于100dp（此处单位我认为是dp，而非px）
                            float ratio = (float) minLen / 100.0f; // 计算像素压缩比例
                            inSampleSize = (int) ratio;
                        }
                        options.inJustDecodeBounds = false; // 计算好压缩比例后，这次可以去加载原图了
                        options.inSampleSize = inSampleSize; // 设置为刚才计算的压缩比例
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); // 解码文件
                        headphoto.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        FileUtil.savePhoto(bitmap, "Photo", "HeadPhoto");
                        headphoto.setImageDrawable(new CirclePicture(bitmap));
                    }

//                    ContentResolver resolver = getContentResolver();
//                    try {
//                        Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
//                        FileUtil.savePhoto(bitmap, "Photo", "HeadPhoto");
//                        headphoto.setImageDrawable(new CirclePicture(bitmap));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                    break;
                default:
                    break;

            }
        }

    }

    //  动态获取权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //  检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}