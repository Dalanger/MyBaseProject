package com.dl.mybaseproject.demo3.bean;

import java.util.List;

/**
 * created by dalang at 2018/9/19
 */
public class TestBean2 {

    /**
     * msg : success
     * describe : 获取成功!!
     * date : [{"id":"1","name":"食品"},{"id":"2","name":"文件"},{"id":"3","name":"蛋糕"}]
     */

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
         * id : 1
         * name : 食品
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DateBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
