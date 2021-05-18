package com.mini_tiktok.homework.mini_tiktok.bean;

import com.google.gson.annotations.SerializedName;

public class User_Draw {

    // TODO-C2 (1) Implement your Feed Bean here according to the response json
    //{ "student_id": "3220180826", "user_name": "lq", "image_url": "www", "video_url":"www"}
    @SerializedName("word_cloud") private String word_cloud_url;
    @SerializedName("line") private String line_url;
    public String getWord_cloud_url(){
        return word_cloud_url;
    }
    public String getLine_url() {return line_url;}

    @Override public String toString() {
        return "User_Draw{" + "word_cloud='" + word_cloud_url +
                ", line=" + line_url + '}';
    }
}