package com.din.myhjc.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ActivityProfileBinding;
import com.din.myhjc.utils.CirclePicture;
import com.din.myhjc.utils.DiyDialogUtils;
import com.din.myhjc.utils.FileUtil;
import com.din.myhjc.utils.PermissionUtils;
import com.din.myhjc.utils.PhotoUtil;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.leefeng.promptlibrary.PromptDialog;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        PermissionUtils.verifyPermissions(this, new int[]{22, 23});     //动态权限获取
        setSupportActionBar(bind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getUserInfo();
    }

    public void getUserInfo() {
        BmobUser bmobUser = BmobUser.getCurrentUser();
        String username = bmobUser.getUsername();
        String email = bmobUser.getEmail();
        String phone = bmobUser.getMobilePhoneNumber();
        bind.userText.setText(username);
        bind.emailText.setText(email);
        bind.phoneText.setText(phone);

        Bitmap bitmap = FileUtil.readPhoto("Photo", "headphoto");
        if (bitmap != null) {
            bind.headPhoto.setImageDrawable(new CirclePicture(bitmap));
        } else {
            bind.headPhoto.setImageResource(R.drawable.headphoto);
        }
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.headPhoto:
                PhotoUtil.selectPictureFromAlbum(this);
                break;
            case R.id.username:
                new DiyDialogUtils().getInputDialog(this, bind.userText, "请输入新用户名", 1);
                break;
            case R.id.email:
                new DiyDialogUtils().getInputDialog(this, bind.emailText, "请输入新邮箱", 2);
                break;
            case R.id.phone:
                new DiyDialogUtils().getInputDialog(this, bind.phoneText, "请输入新手机号", 3);
                break;
            case R.id.changePassword:
//                DiyDialogUtils.getInputDialog(this, "请输入新的密码");
                break;
            default:
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = null;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.PHOTOGRAPH:   //  拍照
                    File picture = null;
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
                        }
                    } else {
                        picture = new File(this.getFilesDir() + PhotoUtil.imageName);
                        if (!picture.exists()) {
                            picture = new File(this.getFilesDir() + PhotoUtil.imageName);
                        }
                    }
                    path = FileUtil.getAppFolder("temp");
                    // 生成一个地址用于存放剪辑后的图片
                    if (!TextUtils.isEmpty(path)) {
                        Uri graphUri = PhotoUtil.getPathToUri(path);
                        PhotoUtil.startPhotoZoom(this, Uri.fromFile(picture), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, graphUri);
                    }
                    break;
                case PhotoUtil.PHOTOZOOM:       //  裁剪照片
                    path = FileUtil.getAppFolder("temp");
                    //  生成一个地址用于存放剪辑后的图片
                    if (!TextUtils.isEmpty(path)) {
                        Uri imageUri = PhotoUtil.getPathToUri(path);
                        PhotoUtil.startPhotoZoom(this, data.getData(), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
                    }
                    break;
                case PhotoUtil.PHOTORESOULT:
                    //  在这里处理剪辑结果。能够获取缩略图，获取剪辑图片的地址。得到这些信息能够选则用于上传图片等等操作
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        Bitmap bitmap = PhotoUtil.convertToBitmap(this, imageUri);   //将图片转化为缩略图
                        FileUtil.savePhoto(bitmap, "Photo", "headphoto");   //保存裁剪后的图片
                        PhotoUtil.deleteUriBitmap(this, imageUri);  //删除裁剪之后的图片
                        bind.headPhoto.setImageDrawable(new CirclePicture(bitmap));
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
}