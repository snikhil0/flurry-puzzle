package com.flurry.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
		return sb.toString();
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

			for (int r = 0; r < blockSize; r++) {
				for (int c = 0; c < blockSize; c++) {
					data[r][c] = grid[id / blockSize * blockSize + r][(id % (blockSize - 1))
							+ c];
				}
			}

		}

		public boolean checkCompleted() {
			return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return String.valueOf(blockId);
		}

	}

	public static void main(String[] args) {
		try {
			SudokuGrid grid = new SudokuGrid("data/sampleInput_4x4.txt");
			System.out.println(grid.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
