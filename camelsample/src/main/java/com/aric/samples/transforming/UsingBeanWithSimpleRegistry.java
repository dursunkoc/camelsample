/**
 * 
 */
package com.aric.samples.transforming;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

/**
 * @author dursun
 * 
 */
public class UsingBeanWithSimpleRegistry {
	private static SimpleRegistry registry = new SimpleRegistry();
	private static CamelContext camelContext = new DefaultCamelContext(registry);
	private static ProducerTemplate producerTemplate = camelContext
			.createProducerTemplate();

	public static void main(String[] args) throws Exception {
		registry.put("helloBean", new HelloBean());
		camelContext.addRoutes(new RouteBuilder() {
			
			public void configure() throws Exception {
				from("direct:start").beanRef("helloBean");
			}
		});
		camelContext.start();
		Object result = producerTemplate.requestBody("direct:start", "World!");
		System.out.println(result);
		
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("CamelBeanMethodName", "hello");
		result = producerTemplate.requestBodyAndHeaders("direct:start", "World!",map);
		System.out.println(result);
		
		map.put("CamelBeanMethodName", "hellov");
		result = producerTemplate.requestBodyAndHeaders("direct:start", "World!",map);
		System.out.println(result);
		camelContext.stop();
	}
}
