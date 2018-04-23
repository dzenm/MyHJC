package com.din.myhjc.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.activities.AddActivity;
import com.din.myhjc.adapter.DiaryAdapter;
import com.din.myhjc.broadcast.RegisterBroadcast;
import com.din.myhjc.content.ListDiary;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.databinding.ContentDiaryBinding;
import com.din.myhjc.utils.DateUtil;
import com.din.myhjc.utils.FileUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Diaries extends Fragment {

    private ContentDiaryBinding bind;
    private List<ListDiary> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = DataBindingUtil.inflate(inflater, R.layout.content_diary, container, false);
        //  下拉刷新颜色和刷新处理事件
        bind.swipeRefersh.setColorSchemeResources(R.color.MaterialCycn);
        bind.swipeRefersh.setOnRefreshListener(()->{
            swipe_Refersh();
            new RegisterBroadcast().register(getActivity());
        });
        bind.floatButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddActivity.class);
            intent.putExtra("TAG", "AddActivity");
            startActivity(intent);
        });
        return bind.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
//        //  背景设置
//        Bitmap bgBitmap = new FileUtil().readPhoto("Photo", "DiaryBackgroung");
//        if (bgBitmap != null) {
//            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), bgBitmap);
//            bind.diary.setBackground(drawable);
//        } else {
//            bind.diary.setBackgroundResource(R.drawable.diary);
//        }
        if (getUserVisibleHint()) {
            //-------- RecyclerView设置Adapter和布局
            bind.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            DiaryAdapter adapter = new DiaryAdapter(list, getActivity());
            bind.recyclerView.setAdapter(adapter);
            init();
        }
    }

    //  下拉刷新进行的操作
    private void swipe_Refersh() {
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bind.swipeRefersh.setRefreshing(false);
                }
            });
        }).start();
    }

    private void init() {
        List<DataDiary> dataDiaries = DataSupport.order("datetime desc").find(DataDiary.class);
        for (DataDiary dataDiary : dataDiaries) {
            String dateTime = new DateUtil().DateToSimple(String.valueOf(dataDiary.getDatetime()));
            list.add(new ListDiary(dataDiary.getId(),
                    dateTime.substring(0, 4),
                    "-" + dateTime.substring(5, 7) + "月",
                    dateTime.substring(8, 10),
                    dataDiary.getContents(),
                    dataDiary.getWeek(),
                    dataDiary.getWeather()));
        }
    }
}