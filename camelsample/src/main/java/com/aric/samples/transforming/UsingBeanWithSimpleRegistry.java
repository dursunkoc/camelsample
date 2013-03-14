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
	private static ProducerTemplate pt = camelContext.createProducerTemplate();

	public static void main(String[] args) throws Exception {
		registry.put("helloBean", new HelloBean());
		camelContext.addRoutes(new RouteBuilder() {

			public void configure() throws Exception {
				from("direct:start").beanRef("helloBean");
			}
		});
		camelContext.start();
		Map<String, Object> map;

		map = new HashMap<String, Object>();
		map.put("beanName", "helloBean");
		Object result = pt.requestBodyAndHeaders("direct:start", "World!", map,String.class);
		System.out.println(result);

		map = new HashMap<String, Object>();
		map.put("CamelBeanMethodName", "hello");
		result = pt.requestBodyAndHeaders("direct:start", "World!", map);
		System.out.println(result);

		map = new HashMap<String, Object>();
		map.put("CamelBeanMethodName", "hellov");
		result = pt.requestBodyAndHeaders("direct:start", "World!", map);
		System.out.println(result);
		camelContext.stop();
	}
}
