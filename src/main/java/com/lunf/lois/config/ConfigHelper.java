package com.lunf.lois.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;

import javax.sql.DataSource;

public class ConfigHelper {

    public static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }

    public static HikariConfig springHikariConfig() {

        HikariConfig config = new HikariConfig();
        config.addDataSourceProperty("cachePrepStmts",true);
        config.addDataSourceProperty("prepStmtCacheSize",250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
        config.addDataSourceProperty("useServerPrepStmts",true);
        config.addDataSourceProperty("useLocalSessionState",true);
        config.addDataSourceProperty("rewriteBatchedStatements",true);

        config.addDataSourceProperty("cacheResultSetMetadata",true);
        config.addDataSourceProperty("cacheServerConfiguration",true);
        config.addDataSourceProperty("elideSetAutoCommits",true);
        config.addDataSourceProperty("maintainTimeStats",true);

        return config;
    }
}
