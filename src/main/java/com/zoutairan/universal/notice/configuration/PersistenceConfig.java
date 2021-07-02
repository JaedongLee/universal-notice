package com.zoutairan.universal.notice.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @package: com.zoutairan.universal.notice.configuration
 * @className: PersistenceConfig
 * @author: tairan.zou
 * @createDate: 7/2/2021 11:42 AM
 * @updateUser: tairan.zou
 * @updateDate: 7/2/2021 11:42 AM
 * @updateRemark:
 * @version: 1.0
 * @copyright: Copyright (c) 2021
 */
@Configuration
@MapperScan("com.zoutairan.universal.notice.dao")
public class PersistenceConfig {
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://10.118.71.89:3306/universal_notice")
                .username("root")
                .password("password")
                .build();
    }
}
