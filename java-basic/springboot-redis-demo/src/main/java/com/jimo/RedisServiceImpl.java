package com.jimo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author jimo
 * @date 19-3-20 下午5:23
 */
@Service
public class RedisServiceImpl<K, V> implements ICacheService<K, V> {
    private final static Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    /**
     * 这里使用名称注入，因为RedisTemplate有多个实现，不能根据类型来
     */
    @Resource(name = "redisTemplate")
    private RedisTemplate<K, V> template;

    @Override
    public long del(K... keys) {
        Long num = template.delete(Arrays.asList(keys));
        logger.info("delete {}, result:{}", keys, num);
        return num == null ? 0 : num;
    }

    @Override
    public boolean clear() {
        throw new IllegalStateException("waiting to implement...");
    }

    @Override
    public boolean set(K key, V value, long liveTimeInSeconds) {
        try {
            template.opsForValue().set(key, value);
            if (liveTimeInSeconds > 0) {
                template.expire(key, liveTimeInSeconds, TimeUnit.SECONDS);
            }
            logger.info("set key[{}]-value[{}]-liveTime[{}] ok!", key, value, liveTimeInSeconds);
            return true;
        } catch (Exception e) {
            logger.error("set key[{}]-value[{}]-liveTime[{}] failed!", key, value, liveTimeInSeconds, e);
            return false;
        }
    }

    @Override
    public boolean set(K key, V value) {
        return set(key, value, 0);
    }

    @Override
    public long setList(K key, V... values) {
        return setList(0, key, values);
    }

    @Override
    public long setList(long liveTimeInSeconds, K key, V... values) {
        try {
            Long num = template.opsForList().leftPushAll(key, values);
            if (liveTimeInSeconds > 0) {
                template.expire(key, liveTimeInSeconds, TimeUnit.SECONDS);
            }
            logger.info("setList key[{}]-values[{}]-liveTime[{}] ok!", key, values, liveTimeInSeconds);
            return num == null ? 0 : (long) num;
        } catch (Exception e) {
            logger.error("setList key[{}]-values[{}]-liveTime[{}] failed!", key, values, liveTimeInSeconds, e);
            return -1;
        }
    }

    @Override
    public V get(K key) {
        try {
            V s = template.opsForValue().get(key);
            logger.error("get key[{}]-value[{}] ok!", key, s);
            return s;
        } catch (Exception e) {
            logger.error("get key[{}] failed!", key, e);
            return null;
        }
    }

    @Override
    public boolean existAll(K... keys) {
        return existKeyCount(keys) == keys.length;
    }

    private long existKeyCount(K[] keys) {
        try {
            Long num = template.countExistingKeys(Arrays.asList(keys));
            logger.info("existKeyCount keys[{}] ok!", Arrays.asList(keys));
            return (num == null ? 0 : num);
        } catch (Exception e) {
            logger.error("existKeyCount keys[{}] failed!", keys, e);
            return -1;
        }
    }

    @Override
    public boolean existAny(K... keys) {
        return existKeyCount(keys) > 0;
    }
}
