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
public class SudokuGame implements Game {

	private static final int MILLISEC = 100000;
	Grid data;
	private Grid[] blocks;

	/**
	 * 
	 * @param filename
	 *            the filename(path) that has the csv representation of the data
	 * @throws IOException
	 *             throw an IOException of file is not present or corruptedF
	 */
	public SudokuGame(Grid grid) throws IOException {

		data = grid;

		// Initialize sudoku blocks
		int numBlocks = grid.getSize();
		blocks = new SudokuData[numBlocks];
		for (int b = 0; b < numBlocks; b++) {
			blocks[b] = new SudokuData(data.getSample(b));
		}

	}

	@Override
	public boolean verifyGame() {
		boolean solved = true;

		// 3 checks
		// verify each block
		// verify all rows one by one
		// verify all columns one by one

		// check blocks
		for (Grid b : blocks) {
			if (!b.verify()) {
				return false;
			}
		}

		// check rows of entire table
		if (!data.verifyRow()) {
			return false;
		}

		// check columns of entire table
		if (!data.verifyColumn()) {
			return false;
		}

		return solved;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(data.toString());
		sb.append("\n");
		for (Grid b : blocks) {
			sb.append(b.toString()).append("\n");
		}

		return sb.toString();
	}


	public static void main(String[] args) {
		try {
			long startTime = System.nanoTime();

			Grid g = new SudokuData("data/sampleInput_4x4.txt");
			Game game = new SudokuGame(g);

			long loadTime = System.nanoTime();

			System.out.println("Load time in millisecs is: ".concat(String
					.valueOf((loadTime - startTime) / SudokuGame.MILLISEC)));

			System.out.println(game.toString());

			startTime = System.nanoTime();
			System.out.println("Is the given sudoku solved? ".concat(String
					.valueOf(game.verifyGame())));
			long checkTime = System.nanoTime();
			System.out
					.println("Time taken to detect correctness of solution in millisecs is: "
							.concat(String.valueOf((checkTime - startTime)
									/ SudokuGame.MILLISEC)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
