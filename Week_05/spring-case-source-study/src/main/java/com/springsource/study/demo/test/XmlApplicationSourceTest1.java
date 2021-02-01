package com.springsource.study.demo.test;

import com.springsource.study.demo.model.Person;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public class XmlApplicationSourceTest1 {
    public static void main(String[] args) {
        /**
         * 处理步骤:
         * 读取spring-source-ioc_1.xml配置文件并解析
         * 实例化
         * 实例化后调用实例
         *
         * ConfigReader
         * App 逻辑关联
         * ReflectionUtil----xxx.class反射实例化
         */
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring-source-ioc_1.xml"));
        Person person = beanFactory.getBean("person", Person.class);
        System.out.println(person);
    }

    public void resourceTest() throws IOException {
        /**
         * 1.外部配置文件读入系统buffer
         */
        Resource resource = new ClassPathResource("spring-source-ioc_1.xml");
        InputStream is = resource.getInputStream();
    }
}
