/**
 * 
 */
package com.aric.samples.testing;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author dursun
 *
 */
public class FileMoveRouteSpringTest extends CamelSpringTestSupport {
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("classpath:firstStepCamelTest.xml");
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
