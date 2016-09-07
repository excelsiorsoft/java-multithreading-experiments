package com.excelsiorsoft.java8;

public class Java8Examples {
	
	public static void main(String[]args) {
		
		new Thread(Java8Examples::printHello).start();
		
		new Thread(()->System.out.println("Lambda!")).start();
	}

	
	public static void printHello() {
		System.out.print("Static method!\n");
	}
}
