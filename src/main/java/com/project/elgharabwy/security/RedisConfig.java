package com.project.elgharabwy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName("redis-17613.c250.eu-central-1-1.ec2.redns.redis-cloud.com");
        redisConfig.setPort(17613);
        redisConfig.setUsername("default");
        redisConfig.setPassword("Rqq0V4FA2RzguVL8Amj1lGP2rLyZHClg");
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Integer> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Integer> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        return template;
    }
}
