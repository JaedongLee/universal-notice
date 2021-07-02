package com.zoutairan.universal.notice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @package: com.zoutairan.universal.notice.configuration
 * @className: WebConfig
 * @author: tairan.zou
 * @createDate: 7/2/2021 2:55 PM
 * @updateUser: tairan.zou
 * @updateDate: 7/2/2021 2:55 PM
 * @updateRemark:
 * @version: 1.0
 * @copyright: Copyright (c) 2021
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
