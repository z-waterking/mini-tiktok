package com.zhuzai.homework.zhuzai.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.18 17:53
 */
public class PostVideoResponse {

    // TODO-C2 (3) Implement your PostVideoResponse Bean here according to the response json
    @SerializedName("success")
    private String success;
    @SerializedName("item")
    private Feed item;

    public PostVideoResponse() {
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Feed getItem() {
        return item;
    }

    public void setItem(Feed item) {
        this.item = item;
    }
    public boolean isSuccess()
    {
        if(success.equals("true"))
            return true;
        else
            return false;
    }
}
