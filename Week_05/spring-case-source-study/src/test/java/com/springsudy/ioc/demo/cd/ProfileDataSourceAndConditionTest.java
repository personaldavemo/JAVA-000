package com.springsudy.ioc.demo.cd;

import com.springstudy.ioc.demo.config.AppConfig;
import com.springstudy.ioc.demo.model.MagicBean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 测试环境H2数据源测试
     * @throws SQLException
     */
    @Test
    public void dataSourceTest() throws SQLException {
        System.out.println(dataSource.getConnection().toString());
    }

    @Test
    public void conditionTest() {
        //预期为Null
        Assert.assertNull(magicBean);
    }
}
