package com.flurry.sudoku;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author snikhil
 * 
 *         Helper class to check correctness of rows, columns and tables
 */
public final class SolutionVerifyHelper {

	static boolean verifyBlock(int[][] block) {
		Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

		int blockSize = block.length;

		for (int[] elementArray : block) {
			for (int c = 0; c < blockSize; c++) {
				int element = elementArray[c];
				assert (element <= blockSize * blockSize);
				if (counter.containsKey(element)) {
					return false;
				} else {
					counter.put(element, 1);
				}
			}

			assert (counter.values().size() == blockSize * blockSize);
			for (int v : counter.values()) {
				assert (v == 1);
			}

			return true;

		}

		assert (counter.values().size() == blockSize);
		for (int v : counter.values()) {
			assert (v == 1);
		}

		return true;
	}

	static boolean verifyArray(int[] array) {
		Map<Integer, Integer> counter = new HashMap<Integer, Integer>();
		int arrayLength = array.length;

		for (int i : array) {
			assert (i <= arrayLength * arrayLength);
			if (counter.containsKey(i)) {
				return false;
			} else {
				counter.put(i, 1);
			}
		}

		assert (counter.values().size() == arrayLength);
		for (int v : counter.values()) {
			assert (v == 1);
		}

		return true;
	}

}
