package com.springsudy.ioc.demo.cd;

import com.springstudy.ioc.demo.config.AppConfig;
import com.springstudy.ioc.demo.config.Office;
import com.springstudy.ioc.demo.el.ElDemo;
import com.springstudy.ioc.demo.model.MagicBean;
import com.springstudy.ioc.demo.service.Food;
import com.springstudy.ioc.demo.service.Worker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("dev")
public class ProfileDataSourceAndConditionTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MagicBean magicBean;
    @Autowired
    private Food food;
    @Autowired
    @Office
    private Worker worker;
    @Autowired
    private ElDemo elDemo;

    /**
     * 测试环境H2数据源测试
     * @throws SQLException
     */
    @Test
    public void dataSourceTest() throws SQLException {
        System.out.println(dataSource.getConnection().toString());
    }

    /**
     * Conditional测试
     */
    @Test
    public void conditionTest() {
        //预期为NotNull
        Assert.assertNotNull(magicBean);
    }

    /**
     * 选定主要的bean
     */
    @Test
    public void primaryTest() {
        food.cook();
    }

    /**
     * 自定义@Qualifier
     */
    @Test
    public void qualifierTest() {
        worker.work();
    }

    /**
     * 设置系统变量
     */
    @BeforeClass
    public static void setSystemProperties() {
        System.setProperty("eldemo.name", "el");
        System.setProperty("eldemo.info", "test");
    }
    @Test
    public void elDemoTest() {
        System.out.println(elDemo);
    }

}
