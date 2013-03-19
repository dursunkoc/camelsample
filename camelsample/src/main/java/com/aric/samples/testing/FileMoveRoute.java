/**
 * 
 */
package com.aric.samples.testing;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author dursun
 *
 */
public class FileMoveRoute extends RouteBuilder {

	/* (non-Javadoc)
	 * @see org.apache.camel.builder.RouteBuilder#configure()
	 */
	@Override
	public void configure() throws Exception {
		from("file://target/inbox").to("file://target/outbox");
	}

}
