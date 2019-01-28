package com.zhuzai.homework.zhuzai.mePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.R;

public class MePage extends BaseFragment {
    private Button edit_btn;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView mypage_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_me_page, container, false);
        edit_btn = view.findViewById(R.id.mypage_ziliao);
        mypage_name = view.findViewById(R.id.mypage_name);
        mPreferences = getActivity().getSharedPreferences("selfinformation", Context.MODE_PRIVATE);
        mypage_name.setText(mPreferences.getString("name",""));
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SelfInformation.class));

            }
        });

        return view;
    }
}
