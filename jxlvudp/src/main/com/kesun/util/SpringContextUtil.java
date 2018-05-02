package kesun.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 获取Bean对象
 * 
 */
public class SpringContextUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public static ApplicationContext  getApplicationContext(){
		return applicationContext;
	}

	public static Object getBean(String beanName){
		return applicationContext.getBean(beanName);
	}

	public static Object getBean(Class c){
		return applicationContext.getBean(c);
	}
}