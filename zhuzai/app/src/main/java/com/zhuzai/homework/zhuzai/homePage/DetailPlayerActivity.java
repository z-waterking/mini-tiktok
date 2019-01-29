package com.zhuzai.homework.zhuzai.homePage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.Content;
import com.zhuzai.homework.zhuzai.bean.Feed;
import com.zhuzai.homework.zhuzai.bean.FeedResponse;
import com.zhuzai.homework.zhuzai.bean.Recommend_Feed;
import com.zhuzai.homework.zhuzai.bean.Recommend_Feed_Response;
import com.zhuzai.homework.zhuzai.bean.Upload_Response;
import com.zhuzai.homework.zhuzai.homePage.adapter.RecommendAdapter;
import com.zhuzai.homework.zhuzai.utils.NetworkUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author tianye.xy@bytedance.com
 * 2019/1/9
 */
public class DetailPlayerActivity extends GSYBaseActivityDetail<StandardGSYVideoPlayer> implements RecommendAdapter.ListItemClickListener {
    StandardGSYVideoPlayer detailPlayer;

    private String url_video;
    private String title;
    private String my_user_id;
    private String url_image;
    private RecyclerView mRv;
    private TextView content;
    private RecommendAdapter mAdapter;
    private List<Recommend_Feed> mFeeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_detail_page);

        Intent it = getIntent();
        url_video = it.getStringExtra("video_url");
        title = it.getStringExtra("user_name");
        url_image = it.getStringExtra("image_url");
        content = (TextView) findViewById(R.id.content);
        detailPlayer = (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.GONE);
        detailPlayer.getBackButton().setVisibility(View.GONE);
        //TODO: ZSF 申请读取手机状态的权限，预定打开时将所有权限全部申请完成
<<<<<<< HEAD
//        TelephonyManager TelephonyMgr = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
//        my_user_id = TelephonyMgr.getDeviceId();
        my_user_id = "zsf";
=======
        TelephonyManager TelephonyMgr = (TelephonyManager) getApplicationContext().getSystemService(TELEPHONY_SERVICE);
        my_user_id = TelephonyMgr.getDeviceId();
//        my_user_id = "zsf";
>>>>>>> parent of 1a3b405... mePage后端对接，推荐列表完善
//        loadCover(detailPlayer, url_image);
        initVideoBuilderMode();
        //初始化RecyclerView
        initRecyclerView();
        //取得推荐数据
        fetch_Recommend_Feed_by_video_url();
    }


    //建立RecyclerView
    private void initRecyclerView() {
        mRv = findViewById(R.id.rv);
        //设置Manager，即设置其样式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRv.setLayoutManager(layoutManager);

        mRv.setHasFixedSize(true);

        //创建Adapter,将数据传入
        mAdapter = new RecommendAdapter(mFeeds, this);

        //设置Adapter
        mRv.setAdapter(mAdapter);
    }
    public void fetch_Recommend_Feed_by_video_url() {
        // if success, assign data to mFeeds and call mRv.getAdapter().notifyDataSetChanged()
        // don't forget to call resetRefreshBtn() after response received
        //上传本视频的url和user_id
        //取得自己机器的user_id
        NetworkUtils.getResponseWithRetrofitAsync_Submit_video_sequence(new Callback<Upload_Response>() {
            @Override public void onResponse(Call<Upload_Response> call, Response<Upload_Response> response) {
                //接收到返回值，开始进行处理。
                Upload_Response res = response.body();
                if(res.isIssuccess()){
                    Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_LONG).show();
                }
            }

            @Override public void onFailure(Call<Upload_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, url_video, my_user_id);
        //取得推荐列表
        NetworkUtils.getResponseWithRetrofitAsync_Recommend_Feed_by_video_url(new Callback<Recommend_Feed_Response>() {
            @Override public void onResponse(Call<Recommend_Feed_Response> call, Response<Recommend_Feed_Response> response) {
                //接收到返回值，开始进行处理。
                Recommend_Feed_Response recommend_feeds = response.body();
                mFeeds = recommend_feeds.getRecommend_feeds();
                mAdapter.update_Recommend_Feeds(mFeeds);
                mRv.setAdapter(mAdapter);
            }

            @Override public void onFailure(Call<Recommend_Feed_Response> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, url_video);
        //取得视频本身的内容
        NetworkUtils.getResponseWithRetrofitAsync_Content_by_video_url(new Callback<Content>() {
            @Override public void onResponse(Call<Content> call, Response<Content> response) {
                //接收到返回值，开始进行处理。
                Content ct = response.body();
                content.setText(ct.getContent());
            }

            @Override public void onFailure(Call<Content> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        }, url_video);
    }

    @Override
    public StandardGSYVideoPlayer getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //内置封面可参考SampleCoverVideo
        ImageView imageView = new ImageView(this);
        //loadCover(imageView, url);
        return new GSYVideoOptionBuilder()
                .setThumbImageView(imageView)
                .setUrl(url_video)
                .setCacheWithPlay(true)
                .setVideoTitle(title)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setShowFullAnimation(false)//打开动画
                .setNeedLockFull(true)
                .setSeekRatio(1);
    }

    @Override
    public void clickForFullScreen() {

    }
    /**
     * 是否启动旋转横屏，true表示启动
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    private void loadCover(ImageView imageView, String url) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        Glide.with(this.getApplicationContext())
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(3000000)
                                .centerCrop()
                                .error(R.mipmap.xxx2)
                                .placeholder(R.mipmap.xxx1))
                .load(url)
                .into(imageView);
    }

    @Override
    public void onListItemClick(String video_url, String user_name, String image_url, String student_id) {
        //点击视频后跳入详情页面
        System.out.println("details!");
        Intent it = new Intent(this, DetailPlayerActivity.class);
        it.putExtra("video_url", video_url);
        it.putExtra("user_name", user_name);
        it.putExtra("image_url", image_url);
        it.putExtra("student_id", student_id);
        startActivity(it);
    }

}
