package com.zhuzai.homework.zhuzai.mePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhuzai.homework.zhuzai.MainActivity;
import com.zhuzai.homework.zhuzai.R;

public class SelfInformation extends Activity {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Button commit;
    private EditText name;
    private EditText fale;
    private EditText say;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_information);
        commit = findViewById(R.id.commit);
        name = findViewById(R.id.name);
        say = findViewById(R.id.say);
        fale = findViewById(R.id.fale);
        mPreferences = getSharedPreferences("selfinformation", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        if(mPreferences.getString("name","").equals("")){}
        else name.setText(mPreferences.getString("name",""));
        if(mPreferences.getString("say","").equals("")){}
            else say.setText(mPreferences.getString("say",""));
        if(mPreferences.getString("fale","").equals("")){}
        else fale.setText(mPreferences.getString("fale",""));


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
                Intent intent =new Intent(SelfInformation.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("page", 3);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        });

    }


}
