/**
 * 
 */
package com.aric.samples;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * @author TTDKOC
 *
 */
public class FtpToJmsRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		String ftpUrl = "ftp://10.10.12.119/orders?username=cms&password=crypto13&noop=true";
//		String toUrl = "jms:camelSampleQ";
		String toUrl = "file:data/inbox";
		Processor processor = new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("++Downloaded File: "+exchange.getIn().getHeader("CamelFileName"));
				System.out.println("++Others: "+exchange.getIn().getHeaders());
			}
		};
		from(ftpUrl).process(processor ).to(toUrl);
	}

}
