package com.zhuzai.homework.zhuzai.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:18
 */
public class Chat {

    // TODO-C2 (1) Implement your Feed Bean here according to the response json
    //{ "student_id": "3220180826", "user_name": "lq", "image_url": "www", "video_url":"www"}
    @SerializedName("content") private String content;
    public String getContent(){
        return content;
    }

    @Override public String toString() {
        return "Chat{" + "content='" + content + '}';
    }
}
