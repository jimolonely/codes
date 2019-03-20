package com.jimo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 如果key和value都是string，那最好用这个
 *
 * @author jimo
 * @date 19-3-20 上午11:26
 */
@Service
public class StringRedisServiceImpl implements IRedisService<String, String> {
    private static final Logger logger = LoggerFactory.getLogger(StringRedisServiceImpl.class);

    @Resource
    private StringRedisTemplate template;

    @Override
    public Long del(String... keys) {
        Long num = template.delete(Arrays.asList(keys));
        logger.info("delete {}, result:{}", keys, num);
        return num;
    }

    @Override
    public boolean clear() {
        throw new IllegalStateException("waiting to implement...");
    }

    @Override
    public boolean set(String key, String value, long liveTimeInSeconds) {
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
    public boolean set(String key, String value) {
        return set(key, value, 0);
    }

    @Override
    public long setList(String key, String... values) {
        return setList(0, key, values);
    }

    @Override
    public long setList(long liveTimeInSeconds, String key, String... values) {
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
    public String get(String key) {
        try {
            String s = template.opsForValue().get(key);
            logger.error("get key[{}]-value[{}] ok!", key, s);
            return s;
        } catch (Exception e) {
            logger.error("get key[{}] failed!", key, e);
            return null;
        }
    }

    @Override
    public boolean existAll(String... keys) {
        return existKeyCount(keys) == keys.length;
    }

    private long existKeyCount(String[] keys) {
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
    public boolean existAny(String... keys) {
        return existKeyCount(keys) > 0;
    }
}
