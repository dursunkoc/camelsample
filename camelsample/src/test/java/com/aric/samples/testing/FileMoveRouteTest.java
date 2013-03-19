package com.aric.samples.testing;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FileMoveRouteTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new FileMoveRoute();
	}

	@Test
	public void test() throws Throwable {
		template.sendBodyAndHeader("file://target/inbox", "Hello Mars!",
				Exchange.FILE_NAME, "helloMars.lgt");
		Thread.sleep(1000);
		File targetFile = new File("target/outbox/helloMars.lgt");
		assertTrue("Target file does not exists!",targetFile.exists());
		String content = context.getTypeConverter().convertTo(String.class, targetFile);
		assertEquals("Content is not correct", "Hello Mars!", content);
	}

}
