package com.excelsiorsoft.java8;

import java.util.Arrays;

public class Java8Examples {
	
	public static void main(String[]args) {
		
		new Thread(Java8Examples::printHello).start();
		
		new Thread(()->System.out.println("Lambda!")).start();
		
		Arrays.asList("A", "BB", "CCC").parallelStream().map(/*str -> str.length()*/String::length).filter(k->k%2 == 1).forEach(System.out::println);
	}

	
	public static void printHello() {
		System.out.print("Method!\n");
	}
}
