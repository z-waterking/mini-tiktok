package com.mini_tiktok.homework.mini_tiktok.messagePage.model;

/**
 * Created by 41626 on 2019/1/20.
 */

public class ChatMessage {
    private String icon;
    private String content;
    private boolean ismyself;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean ismyself() {
        return ismyself;
    }

    public void setIsmyself(boolean ismyself) {
        this.ismyself = ismyself;
    }
}
