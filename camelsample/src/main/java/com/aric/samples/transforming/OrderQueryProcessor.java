/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author dursun
 * 
 */
public class OrderQueryProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String id = exchange.getIn().getHeader("id", String.class);
		exchange.getIn().setBody("ID=" + id);
	}
}
