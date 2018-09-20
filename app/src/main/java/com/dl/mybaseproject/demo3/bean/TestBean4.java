package com.dl.mybaseproject.demo3.bean;

import java.util.List;

/**
 * created by dalang at 2018/9/19
 */
public class TestBean4 {



    private String msg;
    private String describe;
    private List<DateBean> date;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<DateBean> getDate() {
        return date;
    }

    public void setDate(List<DateBean> date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * id : 7
         * title : 后台管理者
         * summury : 简介
         * contents : 文案内容
         * addtime : 2018/9/4 10:25:34
         */

        private String id;
        private String title;
        private String summury;
        private String contents;
        private String addtime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummury() {
            return summury;
        }

        public void setSummury(String summury) {
            this.summury = summury;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
