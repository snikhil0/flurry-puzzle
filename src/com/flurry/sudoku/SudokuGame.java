package com.flurry.sudoku;

import java.io.IOException;

/**
 * 
 * @author snikhil
 * 
 *         The class implements the Game interface. The sudoku game consists of
 *         a Grid that represents the entire table and an array of Grids tat
 *         represents the so called blocks. To verify this game the following
 *         should be true: * each block should be verified * each row of the
 *         entire grid is verifiable * each column of the entore grid is
 *         verifiable
 * 
 */
public class SudokuGame implements Game {

	private static final int MILLISEC = 100000;
	Grid data;
	private Grid[] blocks;

	/**
	 * 
	 * @param grid
	 *            The big grid data
	 * @throws IOException
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

	/** 
	 * Main runner
	 * @param args
	 *  arguments to the file
	 */
	public static void main(String[] args) {
		
		assert(args.length > 1);
		if(args.length < 1){
			System.out.println("Wrong input args");
			return;
		}
		
		String filename = args[0];
		
		try {
			long startTime = System.nanoTime();

			Grid g = new SudokuData(filename);
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
