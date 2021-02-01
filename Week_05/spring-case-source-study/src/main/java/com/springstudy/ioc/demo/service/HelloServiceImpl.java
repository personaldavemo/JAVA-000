package com.springstudy.ioc.demo.service;

import com.springstudy.ioc.demo.dao.ContentDao;

public class HelloServiceImpl implements HelloService {
    private ContentDao contentDao;
    @Override
    public String sayHello() {
        return contentDao.getContent();

    }

    public void setContentDao(ContentDao contentDao) {
        this.contentDao = contentDao;
    }
}
