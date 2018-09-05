package com.dl.common.bean;

import java.io.Serializable;

/**
 * Created by hp on 2017/6/8.
 */

public class PushMessage implements Serializable {
    private String title;
    private String contents;
    private String notifyType;


    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }



    public String getContent() {
        return contents;
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
