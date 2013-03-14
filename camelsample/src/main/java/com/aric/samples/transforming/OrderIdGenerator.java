/**
 * 
 */
package com.aric.samples.transforming;

import java.util.Random;

/**
 * @author dursun
 *
 */
public class OrderIdGenerator {
	private static Random random = new Random();
	public static long generate(){
		return random.nextLong();
	}
}
