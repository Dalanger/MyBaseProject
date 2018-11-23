package com.dl.mybaseproject.demo6;

import java.util.List;

/**
 * created by dalang at 2018/11/23
 */
public class Demo6Bean {
    private int count;
    private List<String> imgUrls;


    public Demo6Bean(int count, List<String> imgUrls) {
        this.count = count;
        this.imgUrls = imgUrls;
    }



    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
