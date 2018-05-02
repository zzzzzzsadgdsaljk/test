package redis;

import org.apache.shiro.cache.Cache;

/**
 * Created by wph-pc on 2017/9/27.
 */
public class RedisShiroCacheManager implements IShiroCacheManager {
    private RedisManager redisManager;
    public <K, V> Cache<K, V> getCache(String name) {
        return new RedisShiroCache<K, V>(name, getRedisManager());
    }

    public void destroy() {

    }
    public RedisManager getRedisManager() {
        return redisManager;
    }

    public void setRedisManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }
}
