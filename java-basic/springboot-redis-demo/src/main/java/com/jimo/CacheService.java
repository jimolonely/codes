package com.jimo;

/**
 * 缓存操作定义的接口方法，之所以不指定某个缓存（Redis，Memcached等），
 * 因为以后可能会换成其他缓存
 *
 * @author jimo
 * @date 19-3-20 上午11:29
 */
public interface CacheService<K, V> {

    /**
     * <p>
     * remove key-value(s)
     * </p >
     *
     * @param keys keys
     * @return int 返回移除成功的个数
     * @author jimo
     * @date 19-3-20 上午11:30
     */
    long del(K... keys);

    /**
     * <p>
     * 清空redis
     * </p >
     *
     * @return boolean
     * @author jimo
     * @date 19-3-20 上午11:45
     */
    boolean clear();

    /**
     * <p>
     * set k v
     * </p >
     *
     * @param key               k
     * @param value             v
     * @param liveTimeInSeconds 秒表示的过期时间
     * @return boolean
     * @author jimo
     * @date 19-3-20 上午11:36
     */
    boolean set(K key, V value, long liveTimeInSeconds);

    /**
     * <p>
     * 设置永不过期
     * </p >
     *
     * @param key   k
     * @param value v
     * @return boolean
     * @author jimo
     * @date 19-3-20 上午11:37
     */
    boolean set(K key, V value);

    /**
     * <p>
     * 返回插入成功的个数, 异常返回-1
     * </p >
     *
     * @param key    key
     * @param values values
     * @return long
     * @author jimo
     * @date 19-3-20 下午12:33
     */
    long setList(K key, V... values);

    /**
     * <p>
     * with live time
     * </p >
     *
     * @param liveTimeInSeconds seconds
     * @param key               key
     * @param values            values
     * @return long
     * @author jimo
     * @date 19-3-20 下午12:34
     */
    long setList(long liveTimeInSeconds, K key, V... values);

    /**
     * <p>
     * 不存在返回null
     * </p >
     *
     * @param key key
     * @return V
     * @author jimo
     * @date 19-3-20 上午11:39
     */
    V get(K key);

    /**
     * <p>
     * 多个key时必须都存在才返回true
     * </p >
     *
     * @param keys keys
     * @return boolean
     * @author jimo
     * @date 19-3-20 上午11:40
     */
    boolean existAll(K... keys);

    /**
     * <p>
     * 有一个存在就返回true
     * </p >
     *
     * @param keys keys
     * @return boolean
     * @author jimo
     * @date 19-3-20 上午11:42
     */
    boolean existAny(K... keys);


}
