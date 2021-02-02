package com.springstudy.ioc.demo.service;

import java.util.List;

public class ActionServiceImpl implements ActionService {
    private Integer id;
    private String name;
    private List<String> likes;

    public ActionServiceImpl(Integer id, String name, List<String> likes) {
        this.id = id;
        this.name = name;
        this.likes = likes;
    }

    @Override
    public void walk() {
        System.out.println(id + " person name " + name + " walking on road");
    }

    @Override
    public String toString() {
        return "ActionServiceImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", likes=" + likes +
                '}';
    }
}
