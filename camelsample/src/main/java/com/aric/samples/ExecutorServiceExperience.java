package com.aric.samples;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ExecutorServiceExperience {
	public static void main(String[] args) throws Exception {
		ttts();
	}

	private static void ttts() throws InterruptedException, ExecutionException,
			TimeoutException {
		System.out.println("Starting... " + new Date());
		final ExecutorService tp = Executors.newFixedThreadPool(10);
		System.out.println("Created ThreadPool " + new Date());
		List<Callable<Double>> jobs = new ArrayList<Callable<Double>>();
		List<Future<Double>> invokeAll = new ArrayList<Future<Double>>();
		System.out.println("Creating Jobs " + new Date());
		for (int i = 0; i < 100; i++) {
			final double index = i;
			jobs.add(new Callable<Double>() {
				@Override
				public Double call() throws Exception {
					Thread.sleep(3000);
					System.out.println("INDEX: " + index);
					return index;
				}
			});
			invokeAll.add(tp.submit(jobs.get(i)));
		}
		System.out.println("Created Jobs " + new Date());
		System.out.println("Issuing Jobs " + new Date());

		new Thread() {
			public void run() {
				try {
					System.out.println("Started Killer Thread!!!");
					Thread.sleep(5000);
					System.out.println("Killer Thread Executing!!!");
					tp.shutdownNow();
					System.out.println("Killer Thread 1 More Second!!!");
					tp.awaitTermination(1, TimeUnit.NANOSECONDS);
					System.out.println("Killer Thread Done!!!");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();

		System.out.println("Issued Jobs " + new Date());
		for (Future<Double> f : invokeAll) {
			try {
				System.out.println("waiting for f:" + invokeAll.indexOf(f));
				if (!tp.isShutdown())
					System.out.println("received: "
							+ f.get() + " - " + new Date());
				else
					System.out.println("received: cancelled - " + new Date());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
