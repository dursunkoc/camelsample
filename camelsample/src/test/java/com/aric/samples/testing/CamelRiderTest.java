/**
 * 
 */
package com.aric.samples.testing;

import java.io.File;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dursun
 * 
 */
public class CamelRiderTest extends CamelSpringTestSupport {
	private String inboxDir;
	private String outboxDir;

	@EndpointInject(uri = "file://{{file.inbox}}")
	private ProducerTemplate inbox;

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(new String[] {
				"classpath:rider-camel-prod.xml", "rider-camel-test.xml" });
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		inboxDir = context.resolvePropertyPlaceholders("{{file.inbox}}");
		outboxDir = context.resolvePropertyPlaceholders("{{file.outbox}}");
		deleteDirectory(inboxDir);
		deleteDirectory(outboxDir);
	}

	@Test
	public void test() throws Throwable {
		String body = "Hello Spring!";
		inbox.sendBodyAndHeader(body, Exchange.FILE_NAME, "hello.spg");
		Thread.sleep(1000);
		File target = new File(outboxDir+"/hello.spg");
		assertTrue("File not moved",target.exists());
		String content = context.getTypeConverter().convertTo(String.class, target);
		assertEquals("Content is not correct",body, content);
	}

}
