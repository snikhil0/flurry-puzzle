package com.flurry.sudoku;

public interface Grid {
	boolean verifyRow();
	boolean verifyColumn();
	boolean verifyGrid();
	boolean verify();
	int getSize();
	int[][] getSample(int id);
}
