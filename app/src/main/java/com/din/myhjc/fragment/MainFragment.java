package com.din.myhjc.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ContentMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private ContentMainBinding bind;
    private FragmentUtils fragmentUtils;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bind = DataBindingUtil.inflate(inflater, R.layout.content_main, container, false);

        //-------- 设置toolbar,左上角添加三个横线按钮
        ((AppCompatActivity) getActivity()).setSupportActionBar(bind.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        DrawerLayout drawer_layout = (DrawerLayout) getActivity().findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, bind.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        return bind.getRoot();
    }

    public void onStart() {
        Diaries diaries = new Diaries();
        Tables tables = new Tables();
        List<Fragment> list = new ArrayList<>();
        list.add(diaries);
        list.add(tables);
        fragmentUtils = new FragmentUtils(this, R.id.frameLayout, list);
        fragmentUtils.addChildFragment();
        bind.title.setVisibility(View.GONE);
        fragmentUtils.showChildFragment(diaries);

        bind.changeType.setOnClickListener(v -> {
            if (bind.changeType.getText().toString().equals("周视图")) {
                fragmentUtils.showChildFragment(tables);
                bind.title.setVisibility(View.VISIBLE);
                bind.changeType.setText("日视图");
            } else if (bind.changeType.getText().toString().equals("日视图")) {
                fragmentUtils.showChildFragment(diaries);
                bind.title.setVisibility(View.GONE);
                bind.changeType.setText("周视图");
            }
        });
        super.onStart();
    }
}
