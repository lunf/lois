package com.lunf.lois.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import com.lunf.lois.data.primary.PrimaryMapper;

import java.lang.invoke.MethodHandles;

@Configuration
@MapperScan(basePackages = "com.lunf.lois.data.primary", annotationClass = PrimaryMapper.class, sqlSessionFactoryRef = PrimaryDatabaseConfig.SQL_SESSION_FACTORY_NAME)
public class PrimaryDatabaseConfig {
    public static final String SQL_SESSION_FACTORY_NAME = "sessionFactoryPrimary";
    public static final String TX_MANAGER = "txManagerPrimary";

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private Environment environment;

    @Bean(name = "datasourcePrimary")
    @Primary
    public HikariDataSource dataSourcePrimary() {
        HikariConfig hikariConfig = ConfigHelper.springHikariConfig();

        hikariConfig.setDriverClassName(environment.getProperty("primary.datasource.driver-class-name"));
        hikariConfig.setJdbcUrl(environment.getProperty("primary.datasource.jdbc-url"));
        hikariConfig.setUsername(environment.getProperty("primary.datasource.username"));
        hikariConfig.setPassword(environment.getProperty("primary.datasource.password"));

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = TX_MANAGER)
    @Primary
    public PlatformTransactionManager txManagerPrimary() {
        return new DataSourceTransactionManager(dataSourcePrimary());
    }

    @Bean(name = PrimaryDatabaseConfig.SQL_SESSION_FACTORY_NAME)
    @Primary
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        org.apache.ibatis.session.Configuration ibatisConfiguration = new org.apache.ibatis.session.Configuration();
        ibatisConfiguration.setMapUnderscoreToCamelCase(true);

        sqlSessionFactoryBean.setConfiguration(ibatisConfiguration);
        sqlSessionFactoryBean.setDataSource(dataSourcePrimary());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public LiquibaseProperties primaryLiquibaseProperties() {
        LiquibaseProperties liquibaseProperties = new LiquibaseProperties();

        liquibaseProperties.setChangeLog(environment.getProperty("primary.datasource.liquibase.change-log"));
        return liquibaseProperties;
    }

    @Bean
    public SpringLiquibase primaryLiquibase() {
        return ConfigHelper.springLiquibase(dataSourcePrimary(), primaryLiquibaseProperties());
    }
}
