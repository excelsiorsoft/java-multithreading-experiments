package com.excelsiorsoft.java8;

import java.util.concurrent.CompletableFuture;

public class CompletableFutures {

	public static void main(String[] args) {

		CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> {
			for (int k = 0; k < Long.valueOf(1_000_000L); k++)
				;
			return "42";
		});

		CompletableFuture<Integer> f1 = f0.thenApply(Integer::valueOf);
		CompletableFuture<Double> f2 = f1.thenApply( x -> Math.PI*x*x);
		f2.thenAccept(System.out::println);
		
		System.out.println("END");
	}
	
}
