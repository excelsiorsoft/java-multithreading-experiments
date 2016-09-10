package com.excelsiorsoft.java8;

import java.util.concurrent.CompletableFuture;

public class CompletableFutures {

	public static void main(String[] args) {

		CompletableFuture<String> communism = CompletableFuture.supplyAsync(() -> {
			for (int k = 0; k < Long.valueOf(1_000_000_000_000L); k++)
				;
			return "42";
		});

		CompletableFuture<Integer> fridge = communism.thenApply(Integer::valueOf);
		CompletableFuture<Double> salami = fridge.thenApply( x -> Math.PI*x*x);
		salami.thenAccept(System.out::println);//will happen when the hell freezes over
		
		System.out.println("END");
	}
	
}
