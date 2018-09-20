package com.dl.mybaseproject.demo3.bean;

/**
 * created by dalang at 2018/9/19
 */
public class TestBean3 {


    /**
     * msg : success
     * describe : 获取成功!!
     * data : {"id":"7","title":"后台管理者","summury":"简介","contents":"文案内容","addtime":"2018/9/4 10:25:34"}
     */

    private String msg;
    private String describe;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "id='" + id + '\'' +
                    ", title='" + title + '\'' +
                    ", summury='" + summury + '\'' +
                    ", contents='" + contents + '\'' +
                    ", addtime='" + addtime + '\'' +
                    '}';
        }

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
