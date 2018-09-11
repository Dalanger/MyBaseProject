package com.dl.mybaseproject.demo2;

import java.io.Serializable;

/**
 * created by dalang at 2018/9/11
 */
public class GridBean implements Serializable {
    private String name;

    public GridBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
