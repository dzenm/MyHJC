package com.din.myhjc.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinzhenyan on 2018/3/29.
 */

public class FragmentUtils {
    private Activity activity;
    private Fragment fragment;
    private int ID;
    private List<Fragment> list = new ArrayList<>();

    public FragmentUtils(Activity activity, int ID, List<Fragment> list) {
        this.activity = activity;
        this.ID = ID;
        this.list = list;
    }
    public FragmentUtils(Fragment fragment, int ID, List<Fragment> list) {
        this.fragment = fragment;
        this.ID = ID;
        this.list = list;
    }

    public void addFragment() {
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            transaction.add(ID, list.get(i));
        }
        transaction.commit();
    }

    public void addChildFragment() {
        FragmentTransaction transaction = fragment.getChildFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            transaction.add(ID, list.get(i), String.valueOf(i));
        }
        transaction.commit();
    }


    public void showFragment(Fragment fragment) {
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            transaction.hide(list.get(i));
        }
        transaction.show(fragment);
        transaction.commit();
    }

    public void showChildFragment(Fragment currentFragment) {
        FragmentTransaction transaction = fragment.getChildFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            transaction.hide(list.get(i));
        }
        transaction.show(currentFragment);
        transaction.commit();
    }

}