package com.zhuzai.homework.zhuzai.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recommend_Feed_Response {
    // TODO-C2 (2) Implement your FeedResponse Bean here according to the response json
    @SerializedName("success") private boolean success;
    @SerializedName("recommend_feeds") private List<Recommend_Feed> recommend_feeds;
    public boolean isSuccess(){
        return success;
    }

    public List<Recommend_Feed> getRecommend_feeds(){
        return recommend_feeds;
    }

    @Override public String toString() {
        return "Feeds{" +
                "success='" + success +'\'' +
                ", feeds=" + recommend_feeds.toString() +
                '}';
    }
}

