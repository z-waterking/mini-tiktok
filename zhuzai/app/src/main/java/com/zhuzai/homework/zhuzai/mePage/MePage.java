package com.zhuzai.homework.zhuzai.mePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.MainActivity;
import com.zhuzai.homework.zhuzai.R;

public class    MePage extends BaseFragment {
    private Button edit_btn;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView mypage_name;
    private Button commit;
    private EditText name;
    private EditText fale;
    private EditText say;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_me_page, container, false);
        edit_btn = view.findViewById(R.id.mypage_ziliao);
        mypage_name = view.findViewById(R.id.mypage_name);
        mPreferences = getActivity().getSharedPreferences("selfinformation", Context.MODE_PRIVATE);
        mypage_name.setText(mPreferences.getString("name",""));
        mEditor = mPreferences.edit();
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(getActivity());
                View view1 = View.inflate(getContext(), R.layout.activity_self_information, null);
                commit = view1.findViewById(R.id.commit);
                name = view1.findViewById(R.id.name);
                say = view1.findViewById(R.id.say);
                fale = view1.findViewById(R.id.fale);
                if(mPreferences.getString("name","").equals("")){}
                else name.setText(mPreferences.getString("name",""));
                if(mPreferences.getString("say","").equals("")){}
                else say.setText(mPreferences.getString("say",""));
                if(mPreferences.getString("fale","").equals("")){}
                else fale.setText(mPreferences.getString("fale",""));
                alertDialog7.setTitle("编辑信息")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(view1)
                        .create();
                final AlertDialog show = alertDialog7.show();

                commit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(fale.getText().toString().equals("")){}
                        else mEditor.putString("fale",fale.getText().toString());
                        if(name.getText().toString().equals("")){}
                        else mEditor.putString("name",name.getText().toString());
                        if(say.getText().toString().equals("")){}
                        else mEditor.putString("say",say.getText().toString());
                        mEditor.commit();
                        show.dismiss();
                    }
                });


            }
        });

        return view;
    }
}
