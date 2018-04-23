package com.din.myhjc.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.din.myhjc.R;
import com.din.myhjc.databases.DataNote;
import com.din.myhjc.databinding.ActivityAddNoteBinding;

import org.litepal.crud.DataSupport;

import java.util.List;

import me.leefeng.promptlibrary.PromptDialog;


public class AddNoteActivity extends AppCompatActivity {

    private String Id, TAG;
    private ActivityAddNoteBinding bind;
    private PromptDialog promptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = DataBindingUtil.setContentView(this, R.layout.activity_add_note);
        init();
    }

    private void init() {
        setSupportActionBar(bind.toolbar);
        getSupportActionBar().setTitle("");
        bind.back.setOnClickListener(this::onClick);
        bind.confirm.setOnClickListener(this::onClick);
        promptDialog = new PromptDialog(this);
        TAG = getIntent().getStringExtra("TAG");
        if (TAG.equals("UPDATE")) {
            initUpdate();
        }
    }

    private void initUpdate() {
        bind.textTitle.setText("修改");
        Id = getIntent().getStringExtra("id");
        List<DataNote> list = DataSupport.where("id=?", Id).find(DataNote.class);
        for (DataNote dataNote : list) {
            bind.title.setText(dataNote.getTitle());
            bind.content.setText(dataNote.getContent());
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm:

                if (bind.title.getText().toString().length() > 1 && bind.content.getText().toString().length() > 1) {

                    DataNote dataNote = new DataNote();
                    dataNote.setTitle(bind.title.getText().toString());
                    dataNote.setContent(bind.content.getText().toString());
                    dataNote.setTime(String.valueOf(System.currentTimeMillis()));
                    if (!TAG.equals("UPDATE")) {
                        dataNote.save();
                        new PromptDialog(AddNoteActivity.this).showSuccess("添加成功");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                                intent.putExtra("select_fragment", "Notes");
                                startActivity(intent);
                            }
                        },300);
                    } else {
                        dataNote.updateAll();
                        new PromptDialog(AddNoteActivity.this).showSuccess("修改成功");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                                intent.putExtra("select_fragment", "Notes");
                                startActivity(intent);
                            }
                        },500);
                    }
                } else {
                    promptDialog.showWarn("请输入完整的信息");
                }
                break;
            default:
                break;
        }
    }
}
