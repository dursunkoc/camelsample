/**
 * 
 */
package com.aric.sample.mocking;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author dursun
 * 
 */
public class FirstMockTest extends CamelTestSupport {
	@EndpointInject(uri="jms:topic:quote")
	private ProducerTemplate jms;
	
	@Override
	protected CamelContext createCamelContext() throws Exception {
		CamelContext camelContext = super.createCamelContext();
		camelContext.addComponent("jms", camelContext.getComponent("seda"));
		return camelContext;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.camel.test.junit4.CamelTestSupport#createRouteBuilder()
	 */
	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("jms:topic:quote").to("mock:quote");
			}
		};
	}

	@Test
	public void testQuoteCount() throws Exception {
		MockEndpoint mockEndpoint = getMockEndpoint("mock:quote");
		mockEndpoint.setExpectedMessageCount(1);

		template.sendBody("jms:topic:quote", "hello mock");
		mockEndpoint.assertIsSatisfied();
	}
	
	@Test
	public void testMessageBodies() throws Exception {
		MockEndpoint mockEndpoint = getMockEndpoint("mock:quote");
		mockEndpoint.expectedBodiesReceived(new Object []{"hello mock","hello mars"});
		
		jms.sendBody("hello mock");
		jms.sendBody("hello mars");
		mockEndpoint.assertIsSatisfied();
	}
}
