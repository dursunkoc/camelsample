package com.aric.samples.exceptionhandling;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

/**
 * @author dursun
 *
 */
public class DeadLetterChannelSample {
	private static final SimpleRegistry reg = new SimpleRegistry();
	private static final CamelContext camelContext = new DefaultCamelContext(reg);
	private static final ProducerTemplate pt = camelContext.createProducerTemplate();
	public static void main(String[] args) throws Exception {
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				errorHandler(deadLetterChannel("direct:deadLetters"));
				from("direct:start").beanRef("orderService", "validate")
						.beanRef("orderService", "store");
				from("direct:deadLetters").beanRef("orderService", "deadLetters");
			}
		});
		reg.put("orderService", new OrderService());
		camelContext.start();
		pt.sendBody("direct:start", "produt:1, customer:101");
		camelContext.stop();
	}
}
