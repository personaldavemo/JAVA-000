package com.springsudy.aop.demo;

import com.springstudy.aop.demo.AudienceWithPointcut;
import com.springstudy.aop.demo.Performance;
import com.springstudy.aop.demo.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AopTest {
    @Autowired
    private Performance performance;


    @Test
    public void testAop() {
        performance.perform();
    }
}
