package com.aric.samples.EIP;

import java.io.Serializable;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hawtdb.HawtDBAggregationRepository;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.spi.AggregationRepository;

public class AggregationSample {
	/**
	 * @author dursun
	 * 
	 */
	public static class MyAggregationStrategy implements AggregationStrategy {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.apache.camel.processor.aggregate.AggregationStrategy#aggregate
		 * (org.apache.camel.Exchange, org.apache.camel.Exchange)
		 */
		@Override
		public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
			if (oldExchange != null && oldExchange.getIn() != null) {
				Event oldEvent = oldExchange.getIn().getBody(Event.class);
				Event newEvent = newExchange.getIn().getBody(Event.class);
				if (oldEvent != null) {
					newEvent = new Event(newEvent.getCustId(),
							newEvent.getValue() + oldEvent.getValue());
					newExchange.getIn().setBody(newEvent);
				}
			}
			return newExchange;
		}

	}

	public static class Event implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -974384715663323711L;
		private final String custId;
		private final Integer value;

		public Event(String custId, Integer value) {
			this.custId = custId;
			this.value = value;
		}

		public String getCustId() {
			return custId;
		}

		public Integer getValue() {
			return value;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("Event [custId: ").append(this.custId).append(", value: ")
					.append(this.value).append("]");
			return b.toString();
		}

	}

	public static void main(String[] args) throws Exception {
		CamelContext camelContext = new DefaultCamelContext();
		final AggregationRepository myRepository = new HawtDBAggregationRepository(
				"myRepo", "/Users/dursun/tmp/myRepo.dat");

		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				MyAggregationStrategy aggregationStrategy = new MyAggregationStrategy();
				from("direct:start")
						.aggregate(header("CUST_ID"), aggregationStrategy)
						.aggregationRepository(myRepository)
						.completionSize(500)
						.log("${body}")
						.log("Completed by ${property.CamelAggregatedCompletedBy}");
			}
		});
		camelContext.start();
		ProducerTemplate pt = camelContext.createProducerTemplate();
		for (int i = 0; i < 10000; i++) {
			pt.sendBodyAndHeader("direct:start", new Event("Cust01", 1),
					"CUST_ID", "Cust01");
			pt.sendBodyAndHeader("direct:start", new Event("Cust02", 1),
					"CUST_ID", "Cust02");
//			pt.sendBodyAndHeader("direct:start", new Event("Cust02", 1),
//					"CUST_ID", "Cust02");
//			pt.sendBodyAndHeader("direct:start", new Event("Cust01", 1),
//					"CUST_ID", "Cust01");
//			pt.sendBodyAndHeader("direct:start", new Event("Cust02", 1),
//					"CUST_ID", "Cust02");
//			pt.sendBodyAndHeader("direct:start", new Event("Cust02", 3),
//					"CUST_ID", "Cust02");
		}
		Thread.sleep(3000);
		camelContext.stop();

	}
}