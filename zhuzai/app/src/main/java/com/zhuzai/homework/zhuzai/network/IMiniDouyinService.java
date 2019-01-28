package com.zhuzai.homework.zhuzai.network;

import com.zhuzai.homework.zhuzai.bean.Chat;
import com.zhuzai.homework.zhuzai.bean.Content;
import com.zhuzai.homework.zhuzai.bean.FeedResponse;

import com.zhuzai.homework.zhuzai.bean.FeedResponse;
import com.zhuzai.homework.zhuzai.bean.Recommend_Feed_Response;
import com.zhuzai.homework.zhuzai.bean.Upload_Response;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Xavier.S
 * @date 2019.01.17 20:38
 */
public interface IMiniDouyinService {
    // TODO-C2 (7) Implement your MiniDouyin PostVideo Request here, url: (POST) http://10.108.10.39:8080/minidouyin/video
    // TODO-C2 (8) Implement your MiniDouyin Feed Request here, url: http://10.108.10.39:8080/minidouyin/feed
    @GET("minidouyin/feed") Call<FeedResponse> allFeed();
    @GET("get_recommend_by_user_id") Call<Recommend_Feed_Response> Recommend_Feed_by_user_id(
            @Query("user_id") String user_id
    );
    //得到通过视频url的推荐列表
    @GET("get_recommend_by_video_url") Call<Recommend_Feed_Response> Recommend_Feed_by_video_url(
            @Query("video_url") String video_url
    );
    //得到聊天对话
    @GET("get_reply") Call<Chat> get_chat(
            @Query("content") String content
    );
    //取得视频内部的信息
    @GET("get_content") Call<Content> videoContent(
            @Query("video_url") String video_url
    );
    //上传观看的视频url
    @POST("submit_sequence") Call<Upload_Response> video_sequence(
            @Query("video_url") String video_url,
            @Query("user_id") String user_id
    );
}
