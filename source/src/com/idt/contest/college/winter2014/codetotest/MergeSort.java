package com.idt.contest.college.winter2014.codetotest;

import java.util.Arrays;

import edu.gmu.team1.idt2014.GMUT;
import edu.gmu.team1.idt2014.predicates.IntArrayEquals;

/*
Adapted from:
	http://courses.cs.washington.edu/courses/cse373/13wi/lectures/03-13/MergeSort.java

CSE 373, Winter 2013
The merge sort algorithm, in both a standard sequential version and a parallel
version that uses multiple threads.
*/

public class MergeSort {
	
	public int[] parallelMergeSort(int threadCount, int[] a) {
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

		return a;
	}
	
	
	public int[] broken_parallelMergeSort(int threadCount, int[] a) {
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

		return a;
	}
	
	// Arranges the elements of the given array into sorted order
	// using the "merge sort" algorithm, which splits the array in half,
	// recursively sorts the halves, then merges the sorted halves.
	// It is O(N log N) for all inputs.
	public int[] mergeSort(int[] a) {
		
		int[] ary1 = {1,2,4,3,5};
		int[] ary2 = {1,2,3,4,5};
		
		GMUT.addTest()
			.branches(1)
			.test(new IntArrayEquals(ary1), new IntArrayEquals(ary2))
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
