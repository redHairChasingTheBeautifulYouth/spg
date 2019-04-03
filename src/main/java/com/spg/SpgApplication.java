package com.spg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@MapperScan("com.spg.dao")
public class SpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpgApplication.class, args);
    }

    @Bean
    public ConcurrentHashMap<String ,Object> generateMap(){
        return new ConcurrentHashMap<String, Object>(2<<8);
    }

}
