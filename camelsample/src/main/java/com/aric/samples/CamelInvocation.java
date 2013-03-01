/**
 * 
 */
package com.aric.samples;

import java.io.PrintStream;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.SynchronizationAdapter;

/**
 * @author TTDKOC
 * 
 */
public class CamelInvocation {
	private static final int _500 = 1000;

	public static void main(String[] args) throws Exception {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"admin", "admin", "tcp://10.10.12.119:61616");
		CamelContext context = new DefaultCamelContext();
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:start").to("jms:holla");
			}
		});
		context.start();
		long start = syncInvocation(context);
		System.out.println((System.currentTimeMillis()-start));
		asyncInvocation(context);		
		Thread.sleep(1);
		context.stop();
	}

	private static void asyncInvocation(CamelContext context) {
		ProducerTemplate template = context.createProducerTemplate();
		long start = System.currentTimeMillis();
		final PrintStream out = System.out;
		for (int i = 0; i < _500; i++)
		template.asyncCallbackSendBody("direct:start", "Hello Mars", new SynchronizationAdapter() {
		    @Override
		    public void onComplete(Exchange exchange) {
		        out.println((exchange.getIn().getBody()));
		    }
		});
		out.println("ASYNC: "+(System.currentTimeMillis()-start));
		start = System.currentTimeMillis();
	}

	private static long syncInvocation(CamelContext context) {
		ProducerTemplate template = context.createProducerTemplate();
		long start = System.currentTimeMillis();
		for (int i = 0; i < _500; i++)
			template.sendBody("direct:start", "Hello World");
		System.out.println("SYNC: "+(System.currentTimeMillis()-start));
		start = System.currentTimeMillis();
		return start;
	}
}
