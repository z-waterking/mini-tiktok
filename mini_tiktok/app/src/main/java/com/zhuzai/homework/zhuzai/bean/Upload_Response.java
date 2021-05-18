package com.mini_tiktok.homework.mini_tiktok.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.20 14:18
 */
public class Upload_Response {

    // TODO-C2 (1) Implement your Feed Bean here according to the response json
    //{ "student_id": "3220180826", "user_name": "lq", "image_url": "www", "video_url":"www"}
    @SerializedName("success") private boolean issuccess;

    public boolean isIssuccess() {
        return issuccess;
    }

    @Override public String toString() {
        return "Upload_Response{" + "issuccess='" + issuccess + '}';
    }
}
