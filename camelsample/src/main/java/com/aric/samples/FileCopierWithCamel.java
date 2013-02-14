package com.aric.samples;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Basic Camel Sample
 */
public class FileCopierWithCamel {
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();

		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file:data/inbox?noop=true").to("file:data/outbox");
				from("ftp://10.10.12.119/orders?username=cms&password=crypto13&noop=true").to("file:data/inbox");
			}
		});

		context.start();
		Thread.sleep(100000);
		context.stop();
	}
}