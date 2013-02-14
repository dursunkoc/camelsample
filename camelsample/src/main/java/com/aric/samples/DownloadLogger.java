/**
 * 
 */
package com.aric.samples;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author TTDKOC
 *
 */
public class DownloadLogger implements Processor {

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("++Downloaded File: "+exchange.getIn().getHeader("CamelFileName"));
		System.out.println("++Others: "+exchange.getIn().getHeaders());
	}

}
