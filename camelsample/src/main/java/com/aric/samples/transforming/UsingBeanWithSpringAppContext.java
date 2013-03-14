/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dursun
 * 
 */
public class UsingBeanWithSpringAppContext {
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			"classpath:META-INF/spring/application-context-registry.xml");

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = applicationContext.getBean(CamelContext.class);
		ProducerTemplate producerTemplate = context.createProducerTemplate();
		context.start();
		System.out.println(producerTemplate.requestBody("direct:start","Mars!"));
		context.stop();
	}
}
