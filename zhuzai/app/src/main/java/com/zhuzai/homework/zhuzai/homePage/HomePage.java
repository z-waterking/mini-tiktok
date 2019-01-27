package com.zhuzai.homework.zhuzai.homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.MainActivity;
import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.Feed;
import com.zhuzai.homework.zhuzai.bean.FeedResponse;
import com.zhuzai.homework.zhuzai.homePage.adapter.MyAdapter;
import com.zhuzai.homework.zhuzai.messagePage.MassageAdpter;
import com.zhuzai.homework.zhuzai.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePage extends BaseFragment implements MyAdapter.ListItemClickListener{
    //定义RecyclerView
    private View view;
    private RecyclerView mRv;
    private List<Feed> mFeeds = new ArrayList<>();
    private MyAdapter mAdapter;
    OrientationUtils orientationUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_home_page, container, false);
        initRecyclerView();
        System.out.println("Recycler Success!");
        fetchFeed();
        System.out.println("Feed Fetch Success!");
        return view;
    }

    //建立RecyclerView
    private void initRecyclerView() {
        mRv = view.findViewById(R.id.rv);
        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);

        mRv.setHasFixedSize(true);

        //创建Adapter,将数据传入
        mAdapter = new MyAdapter(mFeeds, this);

        //设置Adapter
        mRv.setAdapter(mAdapter);
    }

    public void fetchFeed() {
        // if success, assign data to mFeeds and call mRv.getAdapter().notifyDataSetChanged()
        // don't forget to call resetRefreshBtn() after response received
        NetworkUtils.getResponseWithRetrofitAsync_Feed(new Callback<FeedResponse>() {
            @Override public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                //接收到返回值，开始进行处理。
                FeedResponse feeds = response.body();
                mFeeds = feeds.getFeeds();
                mAdapter.updateFeeds(mFeeds);
                mRv.setAdapter(mAdapter);
            }

            @Override public void onFailure(Call<FeedResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private int getLayoutId() {
        return R.layout.activity_home_page;
    }

    @Override
    public void onListItemClick(String video_url, String user_name, String image_url, String student_id) {
        //点击视频后跳入详情页面
        System.out.println("details!");
        Intent it = new Intent(getContext(), DetailPlayerActivity.class);
        it.putExtra("video_url", video_url);
        it.putExtra("user_name", user_name);
        it.putExtra("image_url", image_url);
        it.putExtra("student_id", student_id);
        startActivity(it);
    }
}
