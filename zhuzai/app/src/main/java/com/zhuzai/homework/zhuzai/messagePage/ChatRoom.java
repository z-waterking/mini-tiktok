package com.zhuzai.homework.zhuzai.messagePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.Chat;
import com.zhuzai.homework.zhuzai.bean.FeedResponse;
import com.zhuzai.homework.zhuzai.messagePage.model.ChatMessage;
import com.zhuzai.homework.zhuzai.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoom extends AppCompatActivity {
    //聊天对象名称
    private TextView target_name;
    //聊天内容
    //TODO：recyclelist实现
    private TextView chat_content;
    //发送按钮
    private ImageView send_button;
    //待发送文本
    private EditText edit_view;
    private String chat_target;
    private RecyclerView recycleView;
    private ChatAdapter mAdapter;
    private String Icon;
    List<ChatMessage> messages = new ArrayList<ChatMessage>(
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chatroom);

        //取得传回来的参数
        Intent it = getIntent();
        chat_target = it.getStringExtra("chat_target");
        Icon = it.getStringExtra("chat_icon");
        System.out.println(chat_target);
        System.out.println(Icon);
        //设置聊天对象的内容
        target_name = findViewById(R.id.tv_with_name);
        target_name.setText("正在与" + chat_target + "聊天");


        //建立Recyclelist
        //定义recycleView
        recycleView = findViewById(R.id.chat_list);

        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);

        recycleView.setHasFixedSize(true);

        //创建Adapter,将数据传入
        mAdapter = new ChatAdapter(messages);
//
//        //取得文本框和按钮
        edit_view = findViewById(R.id.ed_say);

        send_button = findViewById(R.id.btn_send_info);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果有内容了
                String content = edit_view.getText().toString();
                if(content.equals("") == false)
                {
                    ChatMessage myMessage = new ChatMessage();
                    myMessage.setIsmyself(true);
                    myMessage.setIcon("TYPE_USER");
                    myMessage.setContent(content);
                    mAdapter.Add_Chat_Message(myMessage);
//                    ChatMessage targetMessage = new ChatMessage();
//                    targetMessage.setIsmyself(false);
//                    targetMessage.setIcon(Icon);
//                    targetMessage.setContent(content);
//                    mAdapter.Add_Chat_Message(targetMessage);
                    recycleView.setAdapter(mAdapter);
                    get_chat(content);
                    //清空编辑框
                    edit_view.setText("");
                    //定位recycle到底部
                    recycleView.smoothScrollToPosition(mAdapter.getItemCount()-1);
                }
            }
        });
    }

    public void get_chat(String content) {
        // if success, assign data to mFeeds and call mRv.getAdapter().notifyDataSetChanged()
        // don't forget to call resetRefreshBtn() after response received
        NetworkUtils.getResponseWithRetrofitAsync_Chat(new Callback<Chat>() {
            @Override public void onResponse(Call<Chat> call, Response<Chat> response) {
                //接收到返回值，开始进行处理。
                Chat chat = response.body();
                ChatMessage message = new ChatMessage();
                message.setContent(chat.getContent());
                message.setIcon(Icon);
                message.setIsmyself(false);
                mAdapter.Add_Chat_Message(message);
                recycleView.setAdapter(mAdapter);
            }

            @Override public void onFailure(Call<Chat> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        },  content);
    }

}
