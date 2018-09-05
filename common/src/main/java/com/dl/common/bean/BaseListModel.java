package com.dl.common.bean;

import java.util.List;

/**
 * Created by dalang on 2018/7/25.
 */

public class BaseListModel<T> {


    /**
     * code :200
     * msg : success
     * pageCount : 10
     */

    private String msg;
    private int code;
    private String pageCount;
    private List<T> data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
