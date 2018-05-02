package redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;


/**
 * Created by wph-pc on 2017/9/27.
 */
public class ShiroCacheManager implements CacheManager, Destroyable {
    private IShiroCacheManager shiroCacheManager;
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return getShiroCacheManager().getCache(s);
    }

    public void destroy() throws Exception {
        shiroCacheManager.destroy();
    }

    public IShiroCacheManager getShiroCacheManager() {
        return shiroCacheManager;
    }

    public void setShiroCacheManager(IShiroCacheManager shiroCacheManager) {
        this.shiroCacheManager = shiroCacheManager;
    }
}
