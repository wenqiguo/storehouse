package com.nylg.gwq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.nylg.gwq.dao")
public class StorehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorehouseApplication.class, args);
    }

}
