package com.aric.samples.transforming;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class UsingBeanWithSimpleRegistryForMultipleParametersLanguageAnnotations {

	public static void main(String[] args) throws Exception {
		SimpleRegistry registry = new SimpleRegistry();
		registry.put("orderProcessorBean", new OrderProcessorBean());
		registry.put("orderIdGen", OrderIdGenerator.class);
		CamelContext camelContext = new DefaultCamelContext(registry);
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").beanRef("orderProcessorBean");
			}
		});
		camelContext.start();
		ProducerTemplate pt = camelContext.createProducerTemplate();
		Map<String,Object> header=new HashMap<String, Object>();
		String body = 
				"<order customerId=\"312\" productId=\"726\" />";
		System.out.println(body);
		pt.requestBody("direct:start", body);
		pt.requestBodyAndHeaders("direct:start", "<order customerId=\"312\" productId=\"726\" />", header);
		camelContext.stop();
	}
}
