package com.springstudy.ioc.demo.el;

public class ElDemo {
    private String name;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ElDemo{" +
                "name='" + name + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
