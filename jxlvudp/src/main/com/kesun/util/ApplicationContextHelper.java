package kesun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 获取Bean对象
 * Created by wph-pc on 2017/9/6.
 */
public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext context;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
    /**
     * 获取ApplicationContext
     */
    public static ApplicationContext getApplicationContext(){
        return context;
    }

    /**
     * 获取Bean对象
     */
    public static Object GetBean( String beanName ) {
        return context.getBean( beanName );
    }
}
