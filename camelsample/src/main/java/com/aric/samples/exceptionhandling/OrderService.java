package com.aric.samples.exceptionhandling;

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
}
