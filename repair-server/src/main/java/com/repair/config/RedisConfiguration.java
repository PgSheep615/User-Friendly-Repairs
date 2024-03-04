package com.repair.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;



    /*@Bean
    //@SuppressWarnings(value = {"unchecked", "rawtypes"})
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis对象");

        RedisTemplate redisTemplate = new RedisTemplate<>();
        ///redisTemplate.setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);
// 使用StringRedisSerializer来序列化和反序列化redis的key值
        ///redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(serializer);
// Hash的key也采用StringRedisSerializer的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.afterPropertiesSet();*/

@Configuration
@Slf4j
public class RedisConfiguration {

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("开始创建redis对象");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis的连接工厂对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis key的序列化器(key的)
        //存到Redis中后的数据和原始数据有差别
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
