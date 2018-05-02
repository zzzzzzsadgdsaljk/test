package redis;

import kesun.util.LoggerUtils;
import kesun.util.SerializeUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Set;

/**
 * Created by wph-pc on 2017/9/27.
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {

    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";
    private static final int DB_INDEX = 1;
    private RedisManager redisManager;
    private String name;
    static final Class<RedisShiroCache> SELF = RedisShiroCache.class;
    public RedisShiroCache(String name, RedisManager redisManager) {
        this.name = name;
        this.redisManager = redisManager;
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */
    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public V get(K k) throws CacheException {
        byte[] byteKey = SerializeUtil.serialize(buildCacheKey(k));
        byte[] byteValue = new byte[0];
        try {
            byteValue = redisManager.getValueByKey(DB_INDEX, byteKey);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "get value by cache throw exception",e);
        }
        return (V) SerializeUtil.deserialize(byteValue);
    }

    public V put(K k, V v) throws CacheException {
        V previos = get(k);
        try {
            redisManager.saveValueByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(k)),
                    SerializeUtil.serialize(v), -1);
        } catch (Exception e) {
            LoggerUtils.error(SELF, "put cache throw exception",e);
        }
        return previos;
    }

    public V remove(K k) throws CacheException {
        V previos = get(k);
        try {
            redisManager.deleteByKey(DB_INDEX, SerializeUtil.serialize(buildCacheKey(k)));
        } catch (Exception e) {
            LoggerUtils.error(SELF, "remove cache  throw exception",e);
        }
        return previos;
    }

    public void clear() throws CacheException {

    }

    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    public Set<K> keys() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }

    private String buildCacheKey(Object key) {
        return REDIS_SHIRO_CACHE + getName() + ":" + key;
    }
}
