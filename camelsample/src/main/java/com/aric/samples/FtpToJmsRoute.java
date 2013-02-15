/**
 * 
 */
package com.aric.samples;

import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author TTDKOC
 * 
 */
@Component
public class FtpToJmsRoute extends SpringRouteBuilder {
	@Autowired
	private Processor downloadLogger;

	@Override
	public void configure() throws Exception {
		String ftpUrl = "ftp://10.10.12.119/orders?username=cms&password=crypto13&noop=true";
		String toXML = "jms:xmlFiles";
		String toCSV = "jms:csvFiles";
		String toBAD = "jms:badFiles";
		String continuedProcess="jms:continuedProcess";
		from(ftpUrl).process(downloadLogger).
			choice().
				when(header("CamelFileName").endsWith(".xml")).to(toXML).
				when(header("CamelFileName").endsWith(".csv")).to(toCSV).
				otherwise().to(toBAD).stop().
			end().
			to(continuedProcess);
		
		from(toXML).filter(xpath("/persons[not(@test)]")).process(downloadLogger);
		from(toCSV).filter(xpath("/persons[not(@test)]")).process(downloadLogger);
	}

}
