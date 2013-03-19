/**
 * 
 */
package com.aric.sample;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dursun
 * 
 */
public class FirstTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("file://target/inbox").to("file://target/outbox");
			}
		};
	}

	@Test
	public void testMoveFile() throws Exception {
		template.sendBodyAndHeader("file://target/inbox", "hello world",
				Exchange.FILE_NAME, "hello.txt");
		Thread.sleep(1000);
		File targetFile = new File("target/outbox/hello.txt");
		assertTrue("File does not exists",targetFile.exists());
		String content = context.getTypeConverter().convertTo(String.class, targetFile);
		assertEquals("hello world", content);
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		deleteDirectory("target/inbox");
		deleteDirectory("target/outbox");
		super.setUp();
	}

}
