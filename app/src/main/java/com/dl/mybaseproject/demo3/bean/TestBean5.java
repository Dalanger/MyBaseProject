package com.dl.mybaseproject.demo3.bean;

/**
 * created by dalang at 2018/9/20
 */
public class TestBean5 {
    private String name;
    private String title;
    private String addtime;

    public TestBean5(String name, String title, String addtime) {
        this.name = name;
        this.title = title;
        this.addtime = addtime;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "TestBean5{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", addtime='" + addtime + '\'' +
                '}';
    }
}
