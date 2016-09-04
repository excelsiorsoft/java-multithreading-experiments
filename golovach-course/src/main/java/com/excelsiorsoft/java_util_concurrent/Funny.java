package com.excelsiorsoft.java_util_concurrent;

public class Funny {

	public static void laugh() {System.out.println("hahaha");};
	
	public static void main(String...args) {

		Funny ref = null;
		
		ref.laugh(); //no NPE produced!!
		
		
	}
	
}
