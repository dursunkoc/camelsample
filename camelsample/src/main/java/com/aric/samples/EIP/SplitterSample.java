package com.aric.samples.EIP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * @author dursun
 * 
 */
public class SplitterSample {

	public static void main(String[] args) throws Exception {
		simpleForServiceExample();
	}

	public static class Customer {
		private Long id;
		private String name;
		private List<Department> departments;

		public Customer(Long id, String name, List<Department> departments) {
			this.id = id;
			this.name = name;
			this.departments = departments;
		}

		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public List<Department> getDepartments() {
			return departments;
		}
		
		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("Customer( ");
			b.append(" id: ").append(id);
			b.append(", name: ").append(name);
			b.append(", departments: ").append(departments);
			b.append(")");
			return b.toString();
		}

	}

	public static class Department {
		private int id;
		private String address;
		private String zip;
		private String country;

		public Department(int id, String address, String zip, String country) {
			this.id = id;
			this.address = address;
			this.zip = zip;
			this.country = country;
		}

		public int getId() {
			return id;
		}

		public String getAddress() {
			return address;
		}

		public String getZip() {
			return zip;
		}

		public String getCountry() {
			return country;
		}

		@Override
		public String toString() {
			StringBuilder b = new StringBuilder();
			b.append("Department(");
			b.append(" id: ").append(id);
			b.append(", address: ").append(address);
			b.append(", zip: ").append(zip);
			b.append(", country: ").append(country);
			b.append(")");
			return b.toString();
		}
	}

	public static class CustomerService {
		public List<Department> splitDepartments(Customer customer) {
			return customer.getDepartments();
		}
	}
	public static void serviceExample() throws Exception, InterruptedException {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("direct:start").log("Received ${body}").split().method(CustomerService.class,"splitDepartments").log("sending ${body}");
			}
		});
		camelContext.start();
		Thread.sleep(5000);
		ProducerTemplate pt = camelContext.createProducerTemplate();
		List<Department> departments = Arrays.asList(new Department[]{
				new Department(1,"home","kimle","TR"),
				new Department(2,"home2","kimle2","TR"),
				new Department(3,"home3","kimle3","TR"),
				new Department(4,"home4","kimle4","TR")
		});
		Customer customer = new Customer(1l, "Dursun", departments );
		pt.sendBody("direct:start",customer);
	}
	public static void simpleForServiceExample() throws Exception, InterruptedException {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").log("Received ${body}").split().simple("${body.departments}").log("sending ${body}");
			}
		});
		camelContext.start();
		Thread.sleep(5000);
		ProducerTemplate pt = camelContext.createProducerTemplate();
		List<Department> departments = Arrays.asList(new Department[]{
				new Department(1,"home","kimle","TR"),
				new Department(2,"home2","kimle2","TR"),
				new Department(3,"home3","kimle3","TR"),
				new Department(4,"home4","kimle4","TR")
		});
		Customer customer = new Customer(1l, "Dursun", departments );
		pt.sendBody("direct:start",customer);
	}

	@SuppressWarnings("unchecked")
	public static void simpleExample() throws Exception, InterruptedException {
		CamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {

			@Override
			public void configure() throws Exception {
				from("direct:start").split(body()).log("${body}");
			}
		});
		camelContext.start();
		Thread.sleep(5000);
		ProducerTemplate pt = camelContext.createProducerTemplate();
		List<List<String>> body = new ArrayList<List<String>>();
		Object aList = Arrays.asList(new Object[] { "aa", "a" });
		body.add((List<String>) aList);
		Object bList = Arrays.asList(new Object[] { "bb", "b" });
		body.add((List<String>) bList);
		Object cList = Arrays.asList(new Object[] { "cc", "c" });
		body.add((List<String>) cList);
		pt.sendBody("direct:start", body);
		camelContext.stop();
	}
}
