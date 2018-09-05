package com.dl.common.bean;

import java.io.Serializable;

/**
 * Created by dalang on 2018/9/1.
 *
 * eventBus传递消息实体类
 */


public class MsgEvent implements Serializable {

    private String request;

    private String msg;

    private Object data;

    public MsgEvent(String request){
        this.request = request;
    }

    public MsgEvent(String request, String msg, Object data){
        this.request = request;
        this.msg = msg;
        this.data = data;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
