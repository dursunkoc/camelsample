/**
 * 
 */
package com.aric.samples.transforming;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author TTDKOC
 * 
 */
public class XmlTransformationXStream {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://10.10.12.119:61616");
		context.addComponent("jms",
				JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("ftp://10.10.12.119/orders?username=cms&password=crypto13&noop=true")
				.marshal().xstream()
				.to("jms:heros")
				;
			}
		});
		context.start();
		Thread.sleep(50000);
		context.stop();
	}
}
