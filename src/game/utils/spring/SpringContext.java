package game.utils.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContext {
	private static ClassPathXmlApplicationContext ctx;
	/**
	 * 加载spring配置
	 */
	public static void initSpringCtx(String filePath) {
		ctx = new ClassPathXmlApplicationContext(filePath);
		ctx.registerShutdownHook();
	}

	/**
	 * 根据beanId获取bean
	 * 
	 * @param beanId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanId) {
		return (T) ctx.getBean(beanId);
	}
}
