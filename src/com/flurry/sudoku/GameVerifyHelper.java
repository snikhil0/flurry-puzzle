package com.flurry.sudoku;


/**
 * 
 * @author snikhil
 * 
 *         Helper class to check correctness of rows, columns and tables
 */
public final class GameVerifyHelper {
	
	static int flag = 0;
	
	static boolean verifyBlock(int[][] block) {
		flag = 0;
		for (int[] elementArray : block) {
			if(!verifyArray(elementArray)){
				return false;
			}
		}
		return true;
	}

	static boolean verifyArray(int[] array) {
		int arrayLength = array.length;
		flag = 0;
		// Use a bit map instead of a hashmap (memory effecient)
		for (int i : array) {
			assert (i <= arrayLength * arrayLength);
			if (i != 0) {
				int bit = 1 << i;
				if ((flag & bit) != 0) {
					return false;
				}
				flag |= bit;
			}
		}

		return true;
	}

}
