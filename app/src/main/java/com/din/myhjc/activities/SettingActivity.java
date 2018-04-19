package com.din.myhjc.activities;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ActivitySettingBinding;
import com.din.myhjc.utils.FileUtil;
import com.din.myhjc.utils.PhotoUtil;

import java.io.FileNotFoundException;

public class SettingActivity extends AppCompatActivity {

    private ActivitySettingBinding bind;
    private ListPreference color_note;
    private PreferenceScreen bg_note;
    private PreferenceScreen bg_diary;
    private PreferenceScreen data_output;
    private PreferenceScreen data_input;

    private SharedPreferences preferences;
    private SharedPreferences pref;
    private String flag;
    private static final String DATABASES = "DataBases";
    private static final String SHAREFERENCES = "com.din.myhjc_preferences";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_setting);
        //  Toolbar
        setSupportActionBar(bind.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        //  跳转SettingFragment
        getFragmentManager().beginTransaction().replace(R.id.frameLayout, new Settings()).commit();
    }

    /**
     * toolbar按钮点击事件
     *
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     */
    @SuppressLint("ValidFragment")
    public class Settings extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferce);

            color_note = (ListPreference) findPreference("color_note");
            bg_note = (PreferenceScreen) findPreference("bg_note");
            bg_diary = (PreferenceScreen) findPreference("bg_diary");
            data_output = (PreferenceScreen) findPreference("data_output");
            data_input = (PreferenceScreen) findPreference("data_input");

            color_note.setOnPreferenceChangeListener(this::onPreferenceChange);
            bg_note.setOnPreferenceClickListener(this::onPreferenceClick);
            bg_diary.setOnPreferenceClickListener(this::onPreferenceClick);
            data_output.setOnPreferenceClickListener(this::onPreferenceClick);
            data_input.setOnPreferenceClickListener(this::onPreferenceClick);
        }

        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
            preferences = preference.getSharedPreferences();
            if (preference == bg_note) {
                new PhotoUtil().selectPictureFromAlbum(getActivity());
                flag = "note";
            } else if (preference == bg_diary) {
                new PhotoUtil().selectPictureFromAlbum(getActivity());
                flag = "diary";
            } else if (preference == data_output) {
                new FileUtil().copyDBToSDcrad(DATABASES);
                Toast.makeText(SettingActivity.this, "导出成功", Toast.LENGTH_SHORT).show();
            } else if (preference == data_input) {
                new FileUtil().copySDcradToDB(DATABASES);
                Toast.makeText(SettingActivity.this, "导入成功", Toast.LENGTH_SHORT).show();
            }

            return true;
        }


        public boolean onPreferenceClick(Preference preference) {return false;}


        public boolean onPreferenceChange(Preference preference, Object newValue) {

            if (preference instanceof ListPreference) {
                //  把preference这个Preference强制转化为ListPreference类型
                ListPreference listPreference = (ListPreference) preference;
                //  获取ListPreference中的实体内容
                CharSequence[] entryValues = listPreference.getEntryValues();
                //  获取ListPreference中的实体内容的下标值
                int index = listPreference.findIndexOfValue((String) newValue);
                //  把listPreference中的摘要显示为当前ListPreference的实体内容中选择的那个项目
                preferences.edit().putString("color_note", String.valueOf(entryValues[index])).commit();
                pref = getSharedPreferences(SHAREFERENCES, 0);
                pref.edit().putBoolean("color_change", true).commit();
                Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }


    //  返回读取的照片
    private String path;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoUtil.PHOTOZOOM:
                    //  裁剪照片
                    path = FileUtil.getFolder("temp");
                    //  生成一个地址用于存放剪辑后的图片
                    if (!TextUtils.isEmpty(path)) {
                        Uri imageUri = PhotoUtil.getPathToUri(path);
                        if (flag == "note") {
                            PhotoUtil.startPhotoZooms(SettingActivity.this, data.getData(), 4, 3, imageUri);
                        } else if (flag == "diary") {
                            PhotoUtil.startPhotoZooms(SettingActivity.this, data.getData(), 9, 15, imageUri);
                        }
                    }
                    break;
                case PhotoUtil.PHOTORESOULT:
                    //  未压缩
                    Uri uri = data.getData();
                    ContentResolver resolver = getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                        if (flag == "note") {
                            new FileUtil().savePhoto(bitmap, "Photo", "NoteBackgroung");
                        } else if (flag == "diary") {
                            new FileUtil().savePhoto(bitmap, "Photo", "DiaryBackgroung");
                        }
                        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}