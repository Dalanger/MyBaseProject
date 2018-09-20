package com.dl.mybaseproject.demo3.bean;

/**
 * created by dalang at 2018/9/19
 */
public class TestBean1 {

    /**
     * msg : success
     * describe : 获取成功!!
     * date : {"id":"1","Proportion":"0.05"}
     */

    private String msg;
    private String describe;
    private DateBean date;

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

    public DateBean getDate() {
        return date;
    }

    public void setDate(DateBean date) {
        this.date = date;
    }

    public static class DateBean {
        /**
         * id : 1
         * Proportion : 0.05
         */

        private String id;
        private String Proportion;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProportion() {
            return Proportion;
        }

        public void setProportion(String Proportion) {
            this.Proportion = Proportion;
        }
    }
}
