package com.zhuzai.homework.zhuzai.recommendPage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.messagePage.MassageAdpter;

public class RecommendPage extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_recommend_page, container, false);
        return view;
    }
}
