package com.din.myhjc.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.activities.AddActivity;
import com.din.myhjc.adapter.DiaryAdapter;
import com.din.myhjc.adapter.StickyItemDecoration;
import com.din.myhjc.broadcast.RegisterBroadcast;
import com.din.myhjc.content.ListDiary;
import com.din.myhjc.databases.DataDiary;
import com.din.myhjc.databinding.DiaryFragmentBinding;
import com.din.myhjc.utils.DateUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Diaries extends Fragment {

    private DiaryFragmentBinding bind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = DataBindingUtil.inflate(inflater, R.layout.diary_fragment, container, false);
        //  下拉刷新颜色和刷新处理事件
        bind.swipeRefersh.setColorSchemeResources(R.color.MaterialCycn);
        bind.swipeRefersh.setOnRefreshListener(() -> {
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
            DiaryAdapter adapter = new DiaryAdapter(getData(), getActivity());
            bind.recyclerView.addItemDecoration(new StickyItemDecoration());
            bind.recyclerView.setAdapter(adapter);
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
            getActivity().runOnUiThread(() -> {
                bind.swipeRefersh.setRefreshing(false);
            });
        }).start();
    }

    private List<ListDiary> getData() {
        List<ListDiary> list = new ArrayList<>();
        String temp = null;
        List<DataDiary> dataDiaries = DataSupport.order("datetime").find(DataDiary.class);
        for (DataDiary dataDiary : dataDiaries) {
            String dateTime = new DateUtil().dateToSimple(String.valueOf(dataDiary.getDatetime()));
            if (!dateTime.substring(0, 10).equals(temp)) {
                list.add(new ListDiary(dateTime.substring(0, 4), dateTime.substring(5, 7), dateTime.substring(8, 10)));
                temp = dateTime.substring(0, 10);
            }
            list.add(new ListDiary(dataDiary.getId(),
                    dataDiary.getContents(),
                    dataDiary.getWeek(),
                    dataDiary.getWeather()));
        }
        list.add(new ListDiary("END"));
        return list;
    }
}