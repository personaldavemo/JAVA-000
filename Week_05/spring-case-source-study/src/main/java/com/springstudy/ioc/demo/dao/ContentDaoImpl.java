package com.springstudy.ioc.demo.dao;

public class ContentDaoImpl implements ContentDao {
    @Override
    public String getContent() {
        return "getContent from db:Hello";
    }
}
