package com.spg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@MapperScan("com.spg.dao")
public class SpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpgApplication.class, args);
    }

    @Bean(name = "saveMessageExecutor")
    public Executor orderSolrSumExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(40);
        executor.setQueueCapacity(5000);
        executor.setThreadNamePrefix("spg-saveMessageExecutor");
        return executor;
    }
}
