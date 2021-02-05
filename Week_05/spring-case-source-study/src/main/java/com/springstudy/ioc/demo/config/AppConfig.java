package com.springstudy.ioc.demo.config;

import com.springstudy.ioc.demo.el.ElDemo;
import com.springstudy.ioc.demo.model.MagicBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;

/**
 * 多数据源配置
 */
@ComponentScan("com.springstudy.ioc.demo")
@Configuration
@PropertySource("condition.properties")
public class AppConfig {
    /**
     * 配置文件，解决硬编码
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${magic}")
    private String magic;
    @Value("#{systemProperties['eldemo.name']}")
    private String name;
    @Value("#{systemProperties['eldemo.info']}")
    private String info;

    @Bean(destroyMethod = "shutdown")
    @Profile("dev")
    public DataSource dataSourceH2() {
        return new EmbeddedDatabaseBuilder()
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    /**
     * 激活方式DispatchServlet-web.xml
     * web应用上下文
     * JNDI
     * 环境变量
     * JVM参数
     * @ActiveProfiles
     * @return
     */
    @Bean
    @Profile("prod")
    public DataSource dataSourceMySql() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    /**
     * 获取环境变量
     * @return
     */
    @Bean
    @Conditional(ConditionConfig.class)
    public MagicBean magicBean() {
        return new MagicBean(magic);
    }

    @Bean
    public ElDemo elDemo() {
        return new ElDemo(name,info);
    }
}
