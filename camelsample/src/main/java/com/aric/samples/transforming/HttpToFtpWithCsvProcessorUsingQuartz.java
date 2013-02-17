/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author TTDKOC
 *
 */
public class HttpToFtpWithCsvProcessorUsingQuartz {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("quartz://report?cron=51+*+*+*+*+?").
					to("http://10.10.12.119/index.html").
					process(new PersonToCsvProcessor()).
					to("file://data/out/orders?fileName=report-${date:now:yyyymmdd}.csv");
			}
		});
		context.start();
		Thread.sleep(100000);
		context.stop();
	}

}
