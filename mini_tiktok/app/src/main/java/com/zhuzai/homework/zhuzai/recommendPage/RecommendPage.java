package com.mini_tiktok.homework.mini_tiktok.recommendPage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mini_tiktok.homework.mini_tiktok.BaseFragment;
import com.mini_tiktok.homework.mini_tiktok.R;
import com.mini_tiktok.homework.mini_tiktok.bean.Feed;
import com.mini_tiktok.homework.mini_tiktok.bean.FeedResponse;
import com.mini_tiktok.homework.mini_tiktok.bean.Recommend_Feed;
import com.mini_tiktok.homework.mini_tiktok.bean.Recommend_Feed_Response;
import com.mini_tiktok.homework.mini_tiktok.homePage.DetailPlayerActivity;
import com.mini_tiktok.homework.mini_tiktok.recommendPage.adapter.RecommendAdapter;
import com.mini_tiktok.homework.mini_tiktok.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.TELEPHONY_SERVICE;

public class RecommendPage extends BaseFragment implements RecommendAdapter.ListItemClickListener {
    //定义本身的View和RecyclerView
    private View view;
    private RecyclerView mRv;
    private List<Recommend_Feed> mFeeds = new ArrayList<>();
    private RecommendAdapter mAdapter;
    private String my_user_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_recommend_page, container, false);
        //取得本机的IMEI
//        TelephonyManager TelephonyMgr = (TelephonyManager) getContext().getSystemService(TELEPHONY_SERVICE);
//        my_user_id = TelephonyMgr.getDeviceId();
        my_user_id = "865873038279224";
        initRecyclerView();
        fetch_Recommend_Feed();
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
        mAdapter = new RecommendAdapter(mFeeds, this);

        //设置Adapter
        mRv.setAdapter(mAdapter);
    }


    public void fetch_Recommend_Feed() {
        // if success, assign data to mFeeds and call mRv.getAdapter().notifyDataSetChanged()
        // don't forget to call resetRefreshBtn() after response received
        NetworkUtils.getResponseWithRetrofitAsync_Recommend_Feed_by_user_id(new Callback<Recommend_Feed_Response>() {
            @Override public void onResponse(Call<Recommend_Feed_Response> call, Response<Recommend_Feed_Response> response) {
                //接收到返回值，开始进行处理。
                Recommend_Feed_Response recommend_feeds = response.body();
                mFeeds = recommend_feeds.getRecommend_feeds();
                mAdapter.update_Recommend_Feeds(mFeeds);
                mRv.setAdapter(mAdapter);
            }

            @Override public void onFailure(Call<Recommend_Feed_Response> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, my_user_id);
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
