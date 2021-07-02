package com.zoutairan.universal.notice;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class UniversalNoticeApplication {
    public static void main(String[] args) {
        SpringApplication.run(UniversalNoticeApplication.class, args);
        log.info("##### App Start Success #####");
    }

}
