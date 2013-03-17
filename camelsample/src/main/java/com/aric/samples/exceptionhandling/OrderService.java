package com.aric.samples.exceptionhandling;

import org.apache.camel.Exchange;

/**
 * @author dursun
 * 
 */
public class OrderService {
	public String validate(String input) {
		if (input.contains("product") & input.contains("customer")) {
			return input;
		} else {
			throw new RuntimeException("input('" + input + "') is not valid");
		}
	}

	public String validateForTime(String input, Exchange exchange) {
		lapTime(exchange);
		Long retry = getRetry(exchange);
		if (retry < 6) {
			setRetry(exchange, retry + 1);
			System.out.println("input('" + input + "'); exchange('" + exchange
					+ "') should be retried!");
			throw new RuntimeException("input('" + input + "'); exchange('"
					+ exchange + "') should be retried!");
		}
		return validate(input);
	}

	private void lapTime(Exchange exchange) {
		long currentTimeMillis = System.currentTimeMillis();
		Long lap = exchange.getProperty("lap",Long.class);
		if(lap==null){
			System.out.println("First Trial Time :>"+currentTimeMillis);
		}else{
			Long timePeriodAfterLastTrial = currentTimeMillis - lap;
			System.out.println(timePeriodAfterLastTrial+" ms taken from last trial until now ('"+currentTimeMillis+"')");
		}
		exchange.setProperty("lap", currentTimeMillis);
	}

	private void setRetry(Exchange exchange, long retry) {
		exchange.setProperty("retry", retry);
	}

	private Long getRetry(Exchange exchange) {
		Long retry = exchange.getProperty("retry", Long.class);
		if (retry == null) {
			retry = 0l;
		}
		return retry;
	}

	public void store(String input) {
		System.out.println("storing :> '" + input + "'");
	}

	public void deadLetters(Exchange exchange) {
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
				Exception.class);
		System.out.println("dead letter :> '" + exchange
				+ "' exception caught is:>");
		e.printStackTrace(System.out);
	}
}
