package com.zhuzai.homework.zhuzai.mePage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.R;

public class MePage extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_me_page, container, false);
        return view;
    }
}
