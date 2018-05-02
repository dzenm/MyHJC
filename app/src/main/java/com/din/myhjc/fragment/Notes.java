package com.din.myhjc.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.activities.AddNoteActivity;
import com.din.myhjc.adapter.NoteAdapter;
import com.din.myhjc.content.ListNote;
import com.din.myhjc.databases.DataNote;
import com.din.myhjc.databinding.NoteFragmentBinding;
import com.din.myhjc.utils.FileUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Notes extends Fragment {

    private NoteFragmentBinding bind;
    private List<ListNote> list;
    private static final String SHAREFERENCES = "com.din.myhjc_preferences";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = DataBindingUtil.inflate(inflater, R.layout.note_fragment, container, false);

        //  设置toolbar,去除标题
        ((AppCompatActivity) getActivity()).setSupportActionBar(bind.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        DrawerLayout drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, bind.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        //  为RecyclerView设置适配器和布局
        list = new ArrayList<>();
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bind.recyclerView.setAdapter(new NoteAdapter(getActivity(), list));
        init();

        bind.collapsing.setTitle("笔记");
        bind.floatButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddNoteActivity.class);
            intent.putExtra("TAG", "AddNoteActivity");
            startActivity(intent);
        });

        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        //  设置背景图片适应屏幕
        Bitmap bgBitmap = new FileUtil().readPhoto("Photo", "NoteBackgroung");
        if (bgBitmap != null) {
            bind.imageView.setImageBitmap(bgBitmap);
        } else {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guide_first), size.x, size.y, true);
            bind.imageView.setImageBitmap(bitmap);
        }

        SharedPreferences pref = getActivity().getSharedPreferences(SHAREFERENCES, 0);
        boolean isChange = pref.getBoolean("color_change", false);
        if (isChange) {
            String color = pref.getString("color_note", "");
            bind.note.setBackgroundColor(Color.parseColor(color));
        }
    }

    private void init() {
        List<DataNote> dataNotes = DataSupport.order("time desc").find(DataNote.class);
        for (DataNote dataNote : dataNotes) {
            list.add(new ListNote(dataNote.getId(),
                    dataNote.getTitle(),
                    dataNote.getContent()));
        }
    }
}