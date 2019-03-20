package com.jimo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 此类配置：当想注入ICacheService的实例时，应该使用哪个实例
 * 当想改变实例时，只需修改这个类，客户端调用无需修改
 *
 * @author jimo
 * @date 19-3-20 下午5:38
 */
@Configuration
public class CacheBeanConfiguration {

    @Resource
    private RedisServiceImpl<Object, Object> redisService;

    @Bean
    public ICacheService<Object, Object> cacheService() {
        return redisService;
    }

    ///
 /*   @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate() throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate<String, String> stringRedisTemplate() throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }*/
}
