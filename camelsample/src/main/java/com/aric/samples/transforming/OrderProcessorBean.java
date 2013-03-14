/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.language.Bean;
import org.apache.camel.language.XPath;
import org.w3c.dom.Document;

/**
 * @author dursun
 * 
 */
public class OrderProcessorBean {

	@Handler
	public Long createOrder(@Body Document xml,
			@XPath("/order/@customerId") Integer customerId,
			@XPath("/order/@productId") Integer productId,
			@Bean(ref = "orderIdGen", method = "generate") long orderId) {
		// long orderId = customerId + productId + random.nextLong();
		System.out.println("Creating the order(" + orderId + ") for customer ("
				+ customerId + ") for product (" + productId
				+ ") depending on the " + xml);
		return orderId;
	}
}
