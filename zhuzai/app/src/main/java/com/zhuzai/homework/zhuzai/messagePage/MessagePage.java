package com.zhuzai.homework.zhuzai.messagePage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by admin on 2019/1/19.
 */

public class MessagePage extends BaseFragment implements MassageAdpter.ListItemClickListener{

    private RecyclerView mMassageLiestView;
    private MassageAdpter messageApter;
    private List<Message> messages;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_massage_page, container, false);
        mMassageLiestView= view.findViewById(R.id.massage_listview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mMassageLiestView.setLayoutManager(layoutManager);
        mMassageLiestView.setHasFixedSize(true);

        try {

            InputStream assetInput = getResources().getAssets().open("data.xml");
            messages = PullParser.pull2xml(assetInput);
            messageApter = new MassageAdpter(messages,this);
            mMassageLiestView.setAdapter(messageApter);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return view;
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(getContext(), ChatRoom.class);
        intent.putExtra("chat_icon",messages.get(clickedItemIndex).getIcon());
        intent.putExtra("chat_target", messages.get(clickedItemIndex).getTitle());
        startActivity(intent);
 }
}
