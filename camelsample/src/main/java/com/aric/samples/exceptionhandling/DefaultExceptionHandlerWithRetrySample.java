/**
 * 
 */
package com.aric.samples.exceptionhandling;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

/**
 * @author dursun
 * 
 */
public class DefaultExceptionHandlerWithRetrySample {
	private static final SimpleRegistry registry = new SimpleRegistry();
	private static final CamelContext camelContext = new DefaultCamelContext(
			registry);

	private static final ProducerTemplate pt = camelContext
			.createProducerTemplate();

	public static void main(String[] args) throws Exception {
		registry.put("orderService", OrderService.class);
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				errorHandler(defaultErrorHandler().maximumRedeliveries(5)
						.redeliveryDelay(1000).backOffMultiplier(1.1)
						.logStackTrace(false).logExhausted(true)
						.retryAttemptedLogLevel(LoggingLevel.WARN));
				from("direct:start").beanRef("orderService", "validateForTime")
						.beanRef("orderService", "store");
			}
		});

		camelContext.start();
		pt.sendBody("direct:start", "product=1,customer=101");

		Thread.sleep(30000);
		camelContext.stop();
	}
}
