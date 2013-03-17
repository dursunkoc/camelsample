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

	public void store(String input) {
		System.out.println("storing :> '" + input + "'");
	}
	
	public void deadLetters(Exchange exchange){
		Exception e = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,
                Exception.class);
		System.out.println("dead letter :> '"+exchange+"' exception caught is:>");
		e.printStackTrace(System.out);
	}
}
