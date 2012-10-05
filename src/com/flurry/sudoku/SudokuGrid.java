package com.flurry.sudoku;

import java.io.IOException;

/**
 * 
 * @author snikhil
 * 
 *         The class that contains the entire sudoku data. The class stores the
 *         entire table into a 2d array as well as stores an array of
 *         Sudokublocks. The SudokuBlock is a unit of a SudokuGrid. The rules
 *         for correctness of the SudokuGrid apply to the SudokuBlock.
 * 
 */
public class SudokuGrid implements SudokuPlus {

	private static final int MILLISEC = 100000;
	Grid data;
	private SudokuPlus[] blocks;
	
	
	/**
	 * 
	 * @param filename
	 *            the filename(path) that has the csv representation of the data
	 * @throws IOException
	 *             throw an IOException of file is not present or corruptedF
	 */
	public SudokuGrid(Grid grid) throws IOException {

		data = grid;
		
		// Initialize sudoku blocks
		int numBlocks = grid.getSize();
		blocks = new SudokuBlock[numBlocks];
		for (int b = 0; b < numBlocks; b++) {
			blocks[b] = new SudokuBlock(data, b);
		}

	}

	@Override
	public boolean verifySudoku() {
		boolean solved = true;

		// 3 checks
		// verify each block
		// verify all rows one by one
		// verify all columns one by one

		// check blocks
		for (SudokuPlus b : blocks) {
			if (!b.verifySudoku()) {
				return false;
			}
		}

		// check rows of entire table
		if(!data.verifyRow()) {
			return false;
		}
		
		// check columns of entire table
		if(!data.verifyColumn()) {
			return false;
		}
		
		return solved;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(data.toString());
		sb.append("\n");
		for (SudokuPlus b : blocks) {
			sb.append(b.toString()).append("\n");
		}

		return sb.toString();
	}

	

	/**
	 * 
	 * @author snikhil
	 * 
	 *         The basic unit of a Sudoku Grid. The data is stored in a 2d array
	 *         and the same principles of checking it are used.
	 */
	private class SudokuBlock implements SudokuPlus {

		private Grid data;
		private final int blockId;

		/**
		 * 
		 * @param filename
		 *            file to read from
		 * @param blockId
		 *            the blockId = incremental (r, c)
		 */
		public SudokuBlock(Grid grid, int id) {
			blockId = id;
			data = grid.getSample(id);
		}

		@Override
		public boolean verifySudoku() {
			// 3 rules
			// row traversal should have a number exactly once
			// column traversal should have a number exactly once
			// entire table should have every number between 1-blocksize exactly
			// once
			boolean solved = true;

			// row check
			if(!data.verifyRow()) {
				return false;
			}

			// column check
			if(!data.verifyColumn()) {
				return false;
			}

			// entire table check
			if (!data.verifyGrid()) {
				return false;
			}

			return solved;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("The data for blockid %d is:\n", blockId));
			sb.append(data.toString());
			return sb.toString();
		}

	}

	
	
	public static void main(String[] args) {
		try {
			long startTime = System.nanoTime();
			
			Grid g = new SudokuData("data/sampleInput_4x4.txt");
			SudokuPlus grid = new SudokuGrid(g);
			
			long loadTime = System.nanoTime();
			
			System.out.println("Load time in millisecs is: ".concat(String
					.valueOf((loadTime - startTime)/SudokuGrid.MILLISEC)));
			
			System.out.println(grid.toString());
			
			startTime = System.nanoTime();
			System.out.println("Is the given sudoku solved? ".concat(String
					.valueOf(grid.verifySudoku())));
			long checkTime = System.nanoTime();
			System.out
					.println("Time taken to detect correctness of solution in millisecs is: "
							.concat(String.valueOf((checkTime - startTime)/SudokuGrid.MILLISEC)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
