package com.lunf.lois.config;

import com.lunf.lois.data.secondary.SecondaryMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.invoke.MethodHandles;

@Configuration
@MapperScan(basePackages = "com.lunf.lois.data.secondary", annotationClass = SecondaryMapper.class, sqlSessionFactoryRef = SecondaryDatabaseConfig.SQL_SESSION_FACTORY_NAME)
public class SecondaryDatabaseConfig {

    public static final String SQL_SESSION_FACTORY_NAME = "sessionFactorySecondary";
    public static final String TX_MANAGER = "txManagerSecondary";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean(name = "datasourceSecondary")
    @ConfigurationProperties(prefix = "secondary.datasource")
    public DataSource dataSourceEsb() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = TX_MANAGER)
    public PlatformTransactionManager txManagerSecondary() {
        return new DataSourceTransactionManager(dataSourceEsb());
    }

    @Bean(name = SecondaryDatabaseConfig.SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);

        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);

        sqlSessionFactoryBean.setDataSource(dataSourceEsb());
        return sqlSessionFactoryBean.getObject();
    }
}
