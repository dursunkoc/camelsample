/**
 * 
 */
package com.aric.samples.transforming;


/**
 * @author TTDKOC
 *
 */
public class PersonToCsvBean {
	private static final char COMMA = ',';
	
	public static String map(String in){
			String body = in;
			String name = body.substring(0,19);
			String surname = body.substring(20,39);
			String salary = body.substring(40,49);
			StringBuilder csv = new StringBuilder();
			csv.append(name.trim()).append(COMMA);
			csv.append(surname.trim()).append(COMMA);
			csv.append(salary.trim());
			return csv.toString();
	}

}
