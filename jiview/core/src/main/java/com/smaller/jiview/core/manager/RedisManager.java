package com.smaller.jiview.core.manager;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiagf 2018/11/22
 */
public interface RedisManager {
    /**
     * 存储单条(String类型)
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 存储带过期分钟数的单条(String类型)
     *
     * @param key
     * @param value
     * @param expiresInMinute
     */
    void set(String key, String value, Integer expiresInMinute);

    /**
     * 存储带过期时间的单条(String类型)
     *
     * @param key
     * @param value
     * @param expiresIn
     * @param unit
     */
    void set(String key, String value, Integer expiresIn, TimeUnit unit);

    /**
     * 获取单条(String类型)
     *
     * @param key
     * @return String
     */
    String get(String key);

    /**
     * 获取过期时间
     *
     * @param key
     * @return Long
     */
    Long getExpire(String key);

    /**
     * 存储单条(Hash类型)
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void saveByHash(String key, String hashKey, String value);

    /**
     * 获取单条(Hash类型)
     *
     * @param key
     * @param hashKey
     * @return String
     */
    String getByHash(String key, String hashKey);

    /**
     * 获取列表(Hash类型)
     *
     * @param key
     */
    Map<String, String> listByHash(String key);

    /**
     * 检查存在(Hash类型)
     *
     * @param key
     * @param hashKey
     * @return Boolean
     */
    Boolean exsitsByHash(String key, String hashKey);

    /**
     * 删除并返回被删除的值(Hash类型)
     *
     * @param key
     * @param hashKey
     * @return String
     */
    String removeByHash(String key, String hashKey);

    /**
     * 生成订单号，规则（yyyyMMdd_每日0点重置的自增数）
     */
    String generateOrderNo();
}
