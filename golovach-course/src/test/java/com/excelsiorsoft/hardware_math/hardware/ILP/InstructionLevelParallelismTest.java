package com.excelsiorsoft.hardware_math.hardware.ILP;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.hardware.ILP.InstructionLevelParallelism;

public class InstructionLevelParallelismTest {

	InstructionLevelParallelism cut = new InstructionLevelParallelism();
	
	@Test
	public void testSquaring() {
		int repetitions = 10;
		long sum = 0;
		for (int i = 0; i < repetitions; i++) {
			sum += cut.squaring();
			System.out.println("accumulated: "+ sum + "\n-------------------");
		}
		double avg = sum/repetitions;
		System.out.println("average: "+avg);
		
	}
	
	@Test
	public void testRepeatedSquaring() {
		int repetitions = 10;
		long sum = 0;
		for (int i = 0; i < repetitions; i++) {
			sum += cut.repeated_squaring();
			System.out.println("accumulated: "+ sum + "\n-------------------");
		}
		double avg = sum/repetitions;
		System.out.println("average: "+avg);
		
	}
	
	@Test
	public void testSquaringTwoSeparateNums() {
		int repetitions = 10;
		long sum = 0;
		for (int i = 0; i < repetitions; i++) {
			sum += cut.squaringSeparateTwoNums();
			System.out.println("accumulated: "+ sum + "\n-------------------");
		}
		double avg = sum/repetitions;
		System.out.println("average: "+avg);
		
	}
	
	@Test
	public void testSquaringTenSeparateNums() {
		int repetitions = 10;
		long sum = 0;
		for (int i = 0; i < repetitions; i++) {
			sum += cut.squaringSeparateTenNums();
			System.out.println("accumulated: "+ sum + "\n-------------------");
		}
		double avg = sum/repetitions;
		System.out.println("average: "+avg);
		
	}

}
