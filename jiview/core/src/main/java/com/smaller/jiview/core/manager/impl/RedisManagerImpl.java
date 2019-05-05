package com.smaller.jiview.core.manager.impl;

import cn.jiguang.common.utils.StringUtils;
import com.smaller.jiview.core.constant.RedisKeyConstant;
import com.smaller.jiview.core.manager.RedisManager;
import com.smaller.jiview.core.util.DateUtil;
import com.smaller.jiview.core.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiagf 2018/11/22
 */
@Slf4j
@Service
public class RedisManagerImpl implements RedisManager {
    private static final String KEY_OR_HASHKEY_EMPTY = "Redis Key or hashKey is empty";
    private static final String HASHKEY_COLON = "hashKey:";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, Integer expiresInMinute) {
        redisTemplate.opsForValue().set(key, value, expiresInMinute, TimeUnit.SECONDS);
    }

    @Override
    public void set(String key, String value, Integer expiresIn, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, expiresIn, unit);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void saveByHash(String key, String hashKey, String value) {
        log.info("RedisManagerImpl.saveByHash");
        log.info("key:" + key);
        log.info(HASHKEY_COLON + hashKey);
        log.info("value:" + value);

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            log.warn(KEY_OR_HASHKEY_EMPTY);
            return;
        }

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        opsForHash.put(key, hashKey, value);
    }

    @Override
    public String getByHash(String key, String hashKey) {
        log.info("RedisManagerImpl.getByHash");
        log.info("key:" + key);
        log.info(HASHKEY_COLON + hashKey);

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            log.warn(KEY_OR_HASHKEY_EMPTY);
            return null;
        }

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hashKey);
    }

    @Override
    public Map<String, String> listByHash(String key) {
        log.info("RedisManagerImpl.exsitsByHash");
        log.info("key:" + key);

        if (StringUtils.isEmpty(key)) {
            log.warn("StringUtils.isEmpty(key)");
            return null;
        }
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        return opsForHash.entries(key);
    }

    @Override
    public Boolean exsitsByHash(String key, String hashKey) {
        log.info("RedisManagerImpl.exsitsByHash");
        log.info("key:" + key);
        log.info(HASHKEY_COLON + hashKey);

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            log.warn(KEY_OR_HASHKEY_EMPTY);
            return Boolean.FALSE;
        }

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hashKey) != null;
    }

    @Override
    public String removeByHash(String key, String hashKey) {
        log.info("RedisManagerImpl.removeByHash");
        log.info("key:" + key);
        log.info(HASHKEY_COLON + hashKey);

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(hashKey)) {
            log.warn(KEY_OR_HASHKEY_EMPTY);
            return null;
        }

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String deletedValue = opsForHash.get(key, hashKey);
        opsForHash.delete(key, hashKey);
        return deletedValue;
    }

    @Override
    public String generateOrderNo() {
        String redisKey = RedisKeyConstant.INCREASE_KEY_ORDER_NO;

        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(redisKey, redisTemplate.getConnectionFactory());
        redisAtomicLong.expireAt(DateUtil.getEndTime(new Date()));
        Long incrementValue = redisAtomicLong.incrementAndGet();
        String strIncrementValue = "";

        if(incrementValue <= 1000){
            strIncrementValue = NumberUtil.leftPad(incrementValue,"000");
        } else{
            strIncrementValue = String.valueOf(incrementValue);
        }

        StringBuilder sbIncrement = new StringBuilder();
        String valuePrefix = DateUtil.date2string(new Date(), "yyMMdd");
        sbIncrement.append(("WT")).append(valuePrefix).append(strIncrementValue);
        return sbIncrement.toString();
    }
}
