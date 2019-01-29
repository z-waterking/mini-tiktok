package com.zhuzai.homework.zhuzai.mePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
<<<<<<< HEAD
import android.support.v7.app.AlertDialog;
=======
import android.util.Log;
>>>>>>> parent of 1a3b405... mePage后端对接，推荐列表完善
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.MainActivity;
import com.zhuzai.homework.zhuzai.R;

<<<<<<< HEAD
=======
import java.lang.annotation.Target;

>>>>>>> parent of 1a3b405... mePage后端对接，推荐列表完善
public class MePage extends BaseFragment {
    private Button edit_btn;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView mypage_name;
    private Button commit;
    private EditText name;
    private EditText fale;
    private EditText say;

<<<<<<< HEAD
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_me_page, container, false);
        edit_btn = view.findViewById(R.id.mypage_ziliao);
        mypage_name = view.findViewById(R.id.mypage_name);
        mPreferences = getActivity().getSharedPreferences("selfinformation", Context.MODE_PRIVATE);
        mypage_name.setText(mPreferences.getString("name",""));
        mEditor = mPreferences.edit();
        edit_btn.setOnClickListener(new View.OnClickListener() {
=======
    private ImageView imageView;
    private ImageView imageViewHead;

    //    public static MainActivity mainactivity;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_me_page, container, false);
        imageView=view.findViewById(R.id.me_page_bg);
        imageViewHead=view.findViewById(R.id.mypage_headshot);
        initView();
        return view;
    }
    private void initView() {
        imageView.setOnClickListener(new View.OnClickListener() {
>>>>>>> parent of 1a3b405... mePage后端对接，推荐列表完善
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

<<<<<<< HEAD

            }
        });

        return view;
=======
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                //    设置Title的图标
//                builder.setIcon(R.drawable.ic_launcher);
                //    设置Title的内容
                builder1.setTitle("改变头像");
                //    设置Content来显示一个信息
                builder1.setMessage("确定改变头像吗？");
                //    设置一个PositiveButton
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(":", "sss确定");
                        Resources res=getResources();
                        int count=(int)(1+Math.random()*(8-1+1));
                        String countString=String.valueOf(count);
                        Log.d("countString",countString);
                        String imgname = "mypage_headshot_a" + countString;
                        System.out.print("imgname"+imgname);
                        int Res=  res.getIdentifier(imgname,"drawable","com.zhuzai.homework.zhuzai");
                        Log.d("int", String.valueOf(Res));
                        Log.d("img",imgname);
                        imageViewHead.setImageDrawable(getResources().getDrawable(Res));
                    }});
                                             //    设置一个NegativeButton
                builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }});
                //    显示出该对话框
                builder1.show();
                }
        }
        );



>>>>>>> parent of 1a3b405... mePage后端对接，推荐列表完善
    }
}
