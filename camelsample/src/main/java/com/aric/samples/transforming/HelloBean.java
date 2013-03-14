package com.aric.samples.transforming;

import org.apache.camel.Handler;

public class HelloBean {
	@Handler
	public String hello(String name) {
		System.out.println("hello : "+name);
		return "Hello "+name;
	}
	@Handler
	public String echo(String name) {
		System.out.println("echo : "+name);
		return "Hello "+name;
	}
	
	public void hellov(String name) {
		System.out.println("hellov : "+name);
	}
}
