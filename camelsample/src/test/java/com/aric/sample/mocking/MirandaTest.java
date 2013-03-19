/**
 * 
 */
package com.aric.sample.mocking;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.aric.samples.transforming.OrderQueryProcessor;
import com.aric.samples.transforming.OrderResponseProcessor;

/**
 * @author dursun
 * 
 */
public class MirandaTest extends CamelTestSupport {
	private String url = "http://localhost:9080/service/order?id=123";;

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("jetty:http://localhost:9080/service/order")
						.process(new OrderQueryProcessor()).to("mock:miranda")
						.process(new OrderResponseProcessor());
			}
		};
	}

	@Test
	public void testMiranda() throws Throwable {
		MockEndpoint mockEndpoint = getMockEndpoint("mock:miranda");
		mockEndpoint.expectedBodiesReceived("ID=123");
		mockEndpoint.whenAnyExchangeReceived(new Processor(){
			@Override
			public void process(Exchange exchange) throws Exception {
				exchange.getIn().setBody("ID=123,STATUS=IN PROGRESS");
			}
		});
		
		String out = template.requestBody(url, null, String.class);
		assertEquals("IN PROGRESS", out);
		assertMockEndpointsSatisfied();
		
	}

}
