package redis;

import org.apache.shiro.cache.Cache;

/**
 * Created by wph-pc on 2017/9/27.
 */
public interface IShiroCacheManager {
    <K, V> Cache<K, V> getCache(String name);
    void destroy();

}
