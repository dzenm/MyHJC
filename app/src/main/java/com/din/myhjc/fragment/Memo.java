package com.din.myhjc.fragment;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.din.myhjc.R;
import com.din.myhjc.databinding.MemoFragmentBinding;

public class Memo extends Fragment {

    private MemoFragmentBinding bind;

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.memo_fragment, container,false);
        return bind.getRoot();
    }
}
