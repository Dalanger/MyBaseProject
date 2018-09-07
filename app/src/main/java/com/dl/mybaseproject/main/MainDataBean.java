package com.dl.mybaseproject.main;

public class MainDataBean {
    private int imgRes;
    private String content;
    private Class activity;

    public MainDataBean(int imgRes, String content, Class activity) {
        this.imgRes = imgRes;
        this.content = content;
        this.activity = activity;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
