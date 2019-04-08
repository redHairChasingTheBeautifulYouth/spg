package com.spg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@SpringBootApplication
@MapperScan("com.spg.dao")
@EnableCaching
public class SpgApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpgApplication.class, args);
    }

    @Bean
    public ConcurrentHashMap<String, Object> generateMap() {
        return new ConcurrentHashMap<String, Object>(2 << 8);
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

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//    }
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//         config = config.entryTtl(Duration.ofMinutes(1)) // 设置缓存的默认过期时间，也是使用Duration设置 .disableCachingNullValues(); // 不缓存空值 // 设置一个初始化的缓存空间set集合 Set<String> cacheNames = new HashSet<>(); cacheNames.add("my-redis-cache1"); cacheNames.add("my-redis-cache2"); // 对每个缓存空间应用不同的配置 Map<String, RedisCacheConfiguration> configMap = new HashMap<>(); configMap.put("my-redis-cache1", config); configMap.put("my-redis-cache2", config.entryTtl(Duration.ofSeconds(120))); RedisCacheManager cacheManager = RedisCacheManager.builder(factory) // 使用自定义的缓存配置初始化一个cacheManager .initialCacheNames(cacheNames) // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置 .withInitialCacheConfigurations(configMap) .build(); return cacheManager; }
//
//
//    }
