package com.idt.contest.college.winter2014.codetotest;

import java.util.Arrays;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.IntArrayEquals;
import edu.gmu.team1.idt2014.predicates.MultiEquals;

/*
Adapted from:
	http://courses.cs.washington.edu/courses/cse373/13wi/lectures/03-13/MergeSort.java

CSE 373, Winter 2013
The merge sort algorithm, in both a standard sequential version and a parallel
version that uses multiple threads.
*/

public class MergeSort {
	
	public int[] parallelMergeSort(int threadCount, int[] a) {
		int[] testAryA1 = {1,2,4,3,5};
		int[] testAryA2 = {1,2,3,4,5};
		
		int[] testAryB1 = {20,15,10,5,1};
		int[] testAryB2 = {1,5,10,15,20};
		
		int[] testAryC1 = {2,1};
		int[] testAryC2 = {1,2};
		
		int[] testAryD1 = {1,2,4,3,5};
		int[] testAryD2 = {1,2,3,4,5};
		
		int[] testAryE1 = {56,5,7,789,89,43,2,4,56,89,97,5,45,32,32123,32,56,7,867,54,78,9986,55445,3424,234,65,657,7876989,5,5,5634,523,22,2,5342,2,2211,21,11256};
		int[] testAryE2 = {2,2,2,4,5,5,5,5,7,7,21,22,32,32,43,45,54,56,56,56,65,78,89,89,97,234,523,657,789,867,2211,3424,5342,5634,9986,11256,32123,55445,7876989};
		
		GMUT.addTest()
			.branches(1)
			.test(new MultiEquals(5,testAryA1), new IntArrayEquals(testAryA2))
			.testNote("calls mergeSort with one or no threads",new MultiEquals(0,testAryB1), new IntArrayEquals(testAryB2))
			.test(new MultiEquals(20,testAryC1), new IntArrayEquals(testAryC2))
			.testNote("1,000,000 as integer input",new MultiEquals(1000000,testAryD1), new IntArrayEquals(testAryD2))
			.test(new MultiEquals(16,testAryE1), new IntArrayEquals(testAryE2))
			.build();
		
		int originalThreadCount = threadCount;
		int[] originalA = Arrays.copyOf(a, a.length);
		
		if (threadCount <= 1) {
			mergeSort(a);
		} else if (a.length >= 2) {
			// split array in half
			int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			
			// sort the halves
			// mergeSort(left);
			// mergeSort(right);
			Thread lThread = new Thread(new Sorter(this, (threadCount / 2), left));
			Thread rThread = new Thread(new Sorter(this, (threadCount / 2), right));
			lThread.start();
			rThread.start();
			
			try {
				lThread.join();
				rThread.join();
			} catch (InterruptedException ie) {}
			
			// merge them back together
			merge(left, right, a);
		}
		
		GMUT.test(a, 1, originalThreadCount,originalA);
		return a;
	}
	
	
	public int[] broken_parallelMergeSort(int threadCount, int[] a) {
		int[] testAryA1 = {1,2,4,3,5};
		int[] testAryA2 = {1,2,3,4,5};
		
		int[] testAryB1 = {20,15,10,5,1};
		int[] testAryB2 = {1,5,10,15,20};
		
		int[] testAryC1 = {2,1};
		int[] testAryC2 = {1,2};
		
		int[] testAryD1 = {1,2,4,3,5};
		int[] testAryD2 = {1,2,3,4,5};
		
		int[] testAryE1 = {56,5,7,789,89,43,2,4,56,89,97,5,45,32,32123,32,56,7,867,54,78,9986,55445,3424,234,65,657,7876989,5,5,5634,523,22,2,5342,2,2211,21,11256};
		int[] testAryE2 = {2,2,2,4,5,5,5,5,7,7,21,22,32,32,43,45,54,56,56,56,65,78,89,89,97,234,523,657,789,867,2211,3424,5342,5634,9986,11256,32123,55445,7876989};
		
		GMUT.addTest()
			.branches(1)
			.test(new MultiEquals(5,testAryA1), new IntArrayEquals(testAryA2))
			.testNote("calls mergeSort with one or no threads",new MultiEquals(0,testAryB1), new IntArrayEquals(testAryB2))
			.test(new MultiEquals(20,testAryC1), new IntArrayEquals(testAryC2))
			.testNote("1,000,000 as integer input",new MultiEquals(1000000,testAryD1), new IntArrayEquals(testAryD2))
			.test(new MultiEquals(16,testAryE1), new IntArrayEquals(testAryE2))
			.build();
		
		int[] originalA = Arrays.copyOf(a, a.length);
		int originalThreadCount = threadCount;
		
		if (threadCount <= 1) {
			broken_mergeSort(a);
		} else if (a.length >= 2) {
			// split array in half
			int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			
			// sort the halves
			// mergeSort(left);
			// mergeSort(right);
			Thread lThread = new Thread(new BrokenSorter(this, (threadCount / 2), left));
			Thread rThread = new Thread(new BrokenSorter(this, (threadCount / 2), right));
			lThread.start();
			rThread.start();
			
			try {
				lThread.join();
				rThread.join();
			} catch (InterruptedException ie) {}
			
			// merge them back together
			broken_merge(left, right, a);
		}

		GMUT.test(a, 1, originalThreadCount,originalA);
		return a;
	}
	
	// Arranges the elements of the given array into sorted order
	// using the "merge sort" algorithm, which splits the array in half,
	// recursively sorts the halves, then merges the sorted halves.
	// It is O(N log N) for all inputs.
	public int[] mergeSort(int[] a) {
		
		int[] testAryA1 = {1,2,4,3,5};
		int[] testAryA2 = {1,2,3,4,5};
		
		int[] testAryB1 = {20,15,10,5,1};
		int[] testAryB2 = {1,5,10,15,20};
		
		int[] testAryC1 = {2,1};
		int[] testAryC2 = {1,2};
		
		int[] testAryD1 = {56,5,7,789,89,43,2,4,56,89,97,5,45,32,32123,32,56,7,867,54,78,9986,55445,3424,234,65,657,7876989,5,5,5634,523,22,2,5342,2,2211,21,11256};
		int[] testAryD2 = {2,2,2,4,5,5,5,5,7,7,21,22,32,32,43,45,54,56,56,56,65,78,89,89,97,234,523,657,789,867,2211,3424,5342,5634,9986,11256,32123,55445,7876989};
		
		
		GMUT.addTest()
			.branches(1)
			.test(new IntArrayEquals(testAryA1), new IntArrayEquals(testAryA2))
			.test(new IntArrayEquals(testAryB1), new IntArrayEquals(testAryB2))
			.test(new IntArrayEquals(testAryC1), new IntArrayEquals(testAryC2))
			.test(new IntArrayEquals(testAryD1), new IntArrayEquals(testAryD2))
			.build();
		int[] originalA = Arrays.copyOf(a, a.length);
		
		if (a.length >= 2) {
			// split array in half
			int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			
			// sort the halves
			mergeSort(left);
			mergeSort(right);
			
			// merge them back together
			merge(left, right, a);
		}
		
		GMUT.test(a, 1, originalA);
		return a;
	}
	
	// Arranges the elements of the given array into sorted order
	// using the "merge sort" algorithm, which splits the array in half,
	// recursively sorts the halves, then merges the sorted halves.
	// It is O(N log N) for all inputs.
	public int[] broken_mergeSort(int[] a) {
		//
		//
		//
		//
		//
		//
		//BUG below... the a.length should be checked for >= 2
		//
		//
		//
		//
		//
		//
		//
		
		int[] testAryA1 = {1,2,4,3,5};
		int[] testAryA2 = {1,2,3,4,5};
		
		int[] testAryB1 = {20,15,10,5,1};
		int[] testAryB2 = {1,5,10,15,20};
		
		int[] testAryC1 = {2,1};
		int[] testAryC2 = {1,2};
		
		int[] testAryD1 = {56,5,7,789,89,43,2,4,56,89,97,5,45,32,32123,32,56,7,867,54,78,9986,55445,3424,234,65,657,7876989,5,5,5634,523,22,2,5342,2,2211,21,11256};
		int[] testAryD2 = {2,2,2,4,5,5,5,5,7,7,21,22,32,32,43,45,54,56,56,56,65,78,89,89,97,234,523,657,789,867,2211,3424,5342,5634,9986,11256,32123,55445,7876989};
		
		GMUT.addTest()
			.branches(1)
			.test(new IntArrayEquals(testAryA1), new IntArrayEquals(testAryA2))
			.test(new IntArrayEquals(testAryB1), new IntArrayEquals(testAryB2))
			.test(new IntArrayEquals(testAryC1), new IntArrayEquals(testAryC2))
			.test(new IntArrayEquals(testAryD1), new IntArrayEquals(testAryD2))
			.build();
		int[] originalA = Arrays.copyOf(a, a.length);
		
		if (a.length > 2) {
			// split array in half
			int[] left  = Arrays.copyOfRange(a, 0, a.length / 2);
			int[] right = Arrays.copyOfRange(a, a.length / 2, a.length);
			
			// sort the halves
			broken_mergeSort(left);
			broken_mergeSort(right);
			
			// merge them back together
			merge(left, right, a);
		}
		
		GMUT.test(a, 1, originalA);
		return a;
	}
	
	// Combines the contents of sorted left/right arrays into output array a.
	// Assumes that left.length + right.length == a.length.
	private void merge(int[] left, int[] right, int[] a) {
		
		int i1 = 0;
		int i2 = 0;
		for (int i = 0; i < a.length; i++) {
			if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])) {
				a[i] = left[i1];
				i1++;
			} else {
				a[i] = right[i2];
				i2++;
			}
		}
	}
	
	// Combines the contents of sorted left/right arrays into output array a.
	// Assumes that left.length + right.length == a.length.
	private void broken_merge(int[] left, int[] right, int[] a) {
		int i1 = 0;
		int i2 = 0;
		//
		//
		//
		//
		//
		//
		//BUG below... the for loop index should start at zero not 1
		//
		//
		//
		//
		//
		//
		//
		for (int i = 1; i < a.length; i++) {
			if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])) {
				a[i] = left[i1];
				i1++;
			} else {
				a[i] = right[i2];
				i2++;
			}
		}
	}
	
	
	// Swaps the values at the two given indexes in the given array.
	@SuppressWarnings("unused")
	private final void swap(int[] a, int i, int j) {
		if (i != j) {
			int temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
	}
	
	
	// CSE 373, Winter 2013, Marty Stepp
	// A Sorter represents a task that can be run in a thread.
	// It performs a merge sort on a given array.
	// The idea is that the overall parallel merge sort method can create
	// several Sorters, each for a given range of the array, and ask them to sort
	// different portions of the array in parallel.
	// Then it will merge the pieces in a single thread.
	private class Sorter implements Runnable {
		private MergeSort mergeSort;
		private int[] a;
		private int threadCount;
		
		public Sorter(MergeSort mergeSort, int threadCount, int[] a) {
			this.mergeSort = mergeSort;
			this.a = a;
			this.threadCount = threadCount;
		}
		
		public void run() {
			this.mergeSort.parallelMergeSort(this.threadCount, this.a);
		}
	}

	
	
	
	private class BrokenSorter implements Runnable {
		private MergeSort mergeSort;
		private int[] a;
		private int threadCount;
		
		public BrokenSorter(MergeSort mergeSort, int threadCount, int[] a) {
			this.mergeSort = mergeSort;
			this.a = a;
			this.threadCount = threadCount;
		}
		
		public void run() {
			this.mergeSort.broken_parallelMergeSort(this.threadCount, this.a);
		}
	}

	

}
