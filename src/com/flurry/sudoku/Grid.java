package com.flurry.sudoku;

public interface Grid {
	boolean verifyRow();
	boolean verifyColumn();
	boolean verifyGrid();
	int getSize();
	Grid getSample(int id);
}
