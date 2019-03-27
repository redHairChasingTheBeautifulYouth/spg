package com.spg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.spg.dao")
public class SpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpgApplication.class, args);
    }

}
