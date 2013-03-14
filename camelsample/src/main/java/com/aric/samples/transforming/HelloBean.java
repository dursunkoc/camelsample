package com.aric.samples.transforming;

import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.Header;

public class HelloBean {
	public String hello(String name) {
		System.out.println("hello : " + name);
		return "Hello " + name;
	}

	@Handler
	public String echo(String name, CamelContext context,
			@Header("beanName") String beanName) {
		System.out.println("echo : " + name);
		Object lookup = context.getRegistry().lookup(beanName);
		System.out.println("found " + lookup);
		return "Hello " + name;
	}

	public void hellov(String name) {
		System.out.println("hellov : " + name);
	}
}
