package com.din.myhjc.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.databinding.ContentMemoBinding;

public class Memo extends Fragment {

    private ContentMemoBinding bind;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.content_memo, container,false);
        return bind.getRoot();
    }
}
