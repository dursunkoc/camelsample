/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.util.ObjectHelper;

/**
 * @author dursun
 *
 */
public class OrderResponseProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
        String reply = ObjectHelper.after(body, "STATUS=");
        exchange.getIn().setBody(reply);
	}

}
