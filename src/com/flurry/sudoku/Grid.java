package com.flurry.sudoku;

/**
 * 
 * @author nikhilshirahatti
 * 
 *         Interface Grid provides the following API's: verify columns, rows and
 *         grids A get size and getSample. Sudoku Data is a implementation of it.
 */
public interface Grid {
	boolean verifyRow();

	boolean verifyColumn();

	boolean verifyGrid();

	boolean verify();

	int getSize();

	int[][] getSample(int id);
}
