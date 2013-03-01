package com.aric.samples;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author Dursun KOC
 * 
 *         Copies from FTP server and pushes into jms queue
 */
public class FtpToJmsWithCamel {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin","admin",
				"tcp://10.10.12.119:61616");
		camelContext.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				String ftpUrl = "ftp://10.10.12.119/orders?username=cms&password=crypto13&noop=true";
				String toUrl = "jms:camelSampleQ";
//				String toUrl = "file:data/inbox";
				Processor processor = new Processor() {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println("Downloaded File: "+exchange.getIn().getHeader("CamelFileName"));
						System.out.println("++Others: "+exchange.getIn().getHeaders());
					}
				};
				from(ftpUrl).process(processor).to(toUrl);
			}
		});

		camelContext.start();
		Thread.sleep(10000);
		camelContext.stop();
	}
}
