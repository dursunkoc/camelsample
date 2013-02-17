/**
 * 
 */
package com.aric.samples.transforming;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author TTDKOC
 *
 */
public class PersonToCsvProcessor implements Processor {
	private static final char COMMA = ',';

	/* (non-Javadoc)
	 * @see org.apache.camel.Processor#process(org.apache.camel.Exchange)
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		String name = body.substring(0,19);
		String surname = body.substring(20,39);
		String salary = body.substring(40,49);
		StringBuilder csv = new StringBuilder();
		csv.append(name.trim()).append(COMMA);
		csv.append(surname.trim()).append(COMMA);
		csv.append(salary.trim());
		exchange.getIn().setBody(csv.toString());
	}

}
