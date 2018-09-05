package com.dl.common.bean;

/**
 * Created by dalang on 2018/8/30.
 */

public class BaseDataModel<T> {

    /**
     * code : 200
     * msg : success
     * data : {}
     */

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
