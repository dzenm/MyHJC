package com.din.myhjc.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.din.myhjc.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import me.leefeng.promptlibrary.PromptDialog;

/**
 * Created by dinzhenyan on 2018/4/21.
 */

public class DiyDialogUtils {

    private static String result = null;
    private PromptDialog promptDialog;


    //  自定义输入
    public static void getInputDialog(Activity activity, TextView textView, String title) {

        //  获取layout
        LayoutInflater locationInflater = activity.getLayoutInflater();
        View dialog = locationInflater.inflate(R.layout.dialog_input, null);
        //  获取控件ID
        ClearEditText editText = (ClearEditText) dialog.findViewById(R.id.locationText);
        TextView diyText = (TextView) dialog.findViewById(R.id.diyText);
        diyText.setText(title);
        //  弹出提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result = editText.getText().toString();
                textView.setText(result);
                dialog.cancel();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setView(dialog);
        builder.show();
    }

    //  自定义输入
    public void getInputDialog(Activity activity, TextView textView, String title, int which) {
        promptDialog = new PromptDialog(activity);
        //  获取layout
        LayoutInflater locationInflater = activity.getLayoutInflater();
        View dialog = locationInflater.inflate(R.layout.dialog_input, null);
        //  获取控件ID
        ClearEditText editText = (ClearEditText) dialog.findViewById(R.id.locationText);
        TextView diyText = (TextView) dialog.findViewById(R.id.diyText);
        diyText.setText(title);
        //  弹出提示框
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                result = editText.getText().toString();
                if (which == 1) {
                    updateInformation(result, "用户名修改成功", 1);
                } else if (which == 2) {
                    updateInformation(result, "邮箱修改成功", 2);
                } else if (which == 3) {
                    updateInformation(result, "手机号修改成功", 3);
                }
                textView.setText(result);
                dialog.cancel();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setView(dialog);
        builder.show();
    }

    public void updateInformation(String data, String info, int which) {
        BmobUser bmobUser = new BmobUser();
        if (which == 1) {
            bmobUser.setUsername(data);
        } else if (which == 2) {
            bmobUser.setEmail(data);
        } else if (which == 3) {
            bmobUser.setMobilePhoneNumber(data);
        }
        BmobUser currentUser = BmobUser.getCurrentUser();
        bmobUser.update(currentUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    promptDialog.showSuccess(info);
                } else {
                    promptDialog.showError(info + "\n" + e.getMessage());
                }
            }
        });
        result = "";
    }
}