package com.dl.common.address;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dalang on 2018/8/30.
 * 数据模型
 */

public class AddressBean implements Serializable {

    private List<AddressItemBean> prov;
    private List<AddressItemBean> city;
    private List<AddressItemBean> area;

    public List<AddressItemBean> getProvince() {
        return prov;
    }

    public void setProvince(List<AddressItemBean> province) {
        this.prov = province;
    }

    public List<AddressItemBean> getCity() {
        return city;
    }

    public void setCity(List<AddressItemBean> city) {
        this.city = city;
    }

    public List<AddressItemBean> getDistrict() {
        return area;
    }

    public void setDistrict(List<AddressItemBean> district) {
        this.area = district;
    }

    @Override
    public String toString() {
        return "AddressBean{" +
                "prov=" + prov +
                ", city=" + city +
                ", area=" + area +
                '}';
    }

    public static class AddressItemBean implements Serializable {
        private String id;
        private String name;
        private String pid;

        public String getI() {
            return id;
        }

        public void setI(String i) {
            this.id = i;
        }

        public String getN() {
            return name;
        }

        public void setN(String n) {
            this.name = n;
        }

        public String getP() {
            return pid;
        }

        public void setP(String p) {
            this.pid = p;
        }

        @Override
        public String toString() {
            return "AddressItemBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", pid='" + pid + '\'' +
                    '}';
        }
    }
}
