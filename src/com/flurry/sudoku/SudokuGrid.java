package com.flurry.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class SudokuGrid {

	private static final String SEPERATOR = ",";
	private int[][] data;
	private SudokuBlock[] blocks;

	/**
	 * 
	 * @param filename
	 *            the filename(path) that has the csv representation of the data
	 * @throws IOException
	 */
	public SudokuGrid(String filename) throws IOException {

		BufferedReader rdr = null;
		int rows = 0, cols = 0;

		try {
			FileInputStream fin = new FileInputStream(filename);
			rdr = new BufferedReader(new InputStreamReader(fin));

			String line = null;

			// First get the data size
			while ((line = rdr.readLine()) != null) {
				String[] splits = line.split(SEPERATOR);
				if (cols == 0) {
					cols = splits.length;
				} else if (cols != splits.length) {
					throw new IllegalArgumentException(
							"Number of columns are not consistent");
				}
				++rows;
			}

			assert (rows == cols);

			data = new int[rows][cols];

			// Now read the file into the data member
			// Reset to begiinging of file
			fin.getChannel().position(0);
			rdr = new BufferedReader(new InputStreamReader(fin));

			int r = 0;
			while ((line = rdr.readLine()) != null) {
				String[] splits = line.split(SEPERATOR);
				int c = 0;
				for (String s : splits) {
					data[r][c++] = Integer.valueOf(s);
				}
				r++;
			}
		} catch (IOException e) {

		} finally {
			rdr.close();
		}

		int numBlocks = rows;
		blocks = new SudokuBlock[numBlocks];
		for (int b = 0; b < numBlocks; b++) {
			blocks[b] = new SudokuBlock(data, b, (int) Math.sqrt(rows));
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int r = 0; r < data.length; r++) {
			for (int c = 0; c < data.length; c++) {
				sb.append(data[r][c]);
				if (c + 1 < data.length) {
					sb.append(",");
				}
			}
			sb.append("\n");
		}
		sb.append("\n");
		for (SudokuBlock b : blocks) {
			sb.append(b.toString()).append("\n");
		}

		return sb.toString();
	}

	public boolean checkSolved() {
		boolean solved = true;

		// check blocks
		for (SudokuBlock b : blocks) {
			solved &= b.checkSolved();
		}

		return solved;
		// check entire table
	}

	private class SudokuBlock {

		private int[][] data;
		private final int blockId;

		/**
		 * 
		 * @param filename
		 *            file to read from
		 * @param blockId
		 *            the blockId = incremental (r, c)
		 */
		public SudokuBlock(int[][] grid, int id, int blockSize) {
			blockId = id;
			data = new int[blockSize][blockSize];
			int minc = (id % blockSize) * blockSize;
			int minr = (id / blockSize) * blockSize;

			for (int r = 0; r < blockSize; r++) {
				for (int c = 0; c < blockSize; c++) {
					data[r][c] = grid[minr + r][minc + c];
				}
			}

		}

		public boolean checkSolved() {
			// 3 rules
			// row traversal should have a number exactly once
			// column traversal should have a number exactly once
			// entire table should have every number between 1-blocksize exactly
			// once

			boolean solved = true;

			// row check
			for (int r = 0; r < data.length; r++) {
				solved &= checkArray(data[r]);
			}

			// column check
			int[] colArray = new int[data.length];
			for (int c = 0; c < data.length; c++) {
				for (int r = 0; r < data.length; r++) {
					colArray[r] = data[r][c];
				}

				solved &= checkArray(colArray);
			}

			// entire table check
			return solved &= checkBlock(data);

		}

		private boolean checkBlock(int[][] block) {
			Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

			for (int[] elementArray : block) {
				for (int c = 0; c < block.length; c++) {
					int element = elementArray[c];
					assert (element <= data.length);
					if (counter.containsKey(element)) {
						return false;
					} else {
						counter.put(element, 1);
					}
				}

				assert (counter.values().size() == data.length * data.length);
				for (int v : counter.values()) {
					assert (v == 1);
				}

				return true;

			}

			assert (counter.values().size() == data.length);
			for (int v : counter.values()) {
				assert (v == 1);
			}

			return true;
		}

		private boolean checkArray(int[] array) {
			Map<Integer, Integer> counter = new HashMap<Integer, Integer>();

			for (int i : array) {
				assert (i <= data.length);
				if (counter.containsKey(i)) {
					return false;
				} else {
					counter.put(i, 1);
				}
			}

			assert (counter.values().size() == data.length);
			for (int v : counter.values()) {
				assert (v == 1);
			}

			return true;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();

			sb.append("Data for blockId: ").append(blockId).append("\n");
			for (int r = 0; r < data.length; r++) {
				for (int c = 0; c < data.length; c++) {
					sb.append(data[r][c]);
					if (c + 1 < data.length) {
						sb.append(",");
					}
				}
				sb.append("\n");
			}
			return sb.toString();
		}

	}

	public static void main(String[] args) {
		try {
			SudokuGrid grid = new SudokuGrid("data/MangledsampleInput_4x4.txt");
			System.out.println(grid.toString());
			System.out.println("Is the given sudoku solved? ".concat(String
					.valueOf(grid.checkSolved())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
