package com.flurry.sudoku;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SudokuData implements Grid {

	private final static String SEPERATOR = ",";
	private int[][] data;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
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

	public SudokuData(int[][] g) {
		data = g;
	}

	public SudokuData(String filename) throws IOException {
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

			// Now read the file for filling out the data member
			// Reset to beginning of file
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
			e.printStackTrace();
		} finally {
			rdr.close();
		}
	}

	@Override
	public boolean verifyRow() {
		// check rows of the entire table
		for (int r = 0; r < data.length; r++) {
			if (!GameVerifyHelper.verifyArray(data[r])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean verifyColumn() {
		int[] colArray = new int[data.length];
		for (int c = 0; c < data.length; c++) {
			for (int r = 0; r < data.length; r++) {
				colArray[r] = data[r][c];
			}

			if (!GameVerifyHelper.verifyArray(colArray)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean verifyGrid() {
		return GameVerifyHelper.verifyBlock(data);
	}

	@Override
	public int getSize() {
		return data.length;
	}

	@Override
	public int[][] getSample(int id) {
		int blockSize = (int) Math.sqrt(data.length);
		int[][] g = new int[blockSize][blockSize];

		int minc = (id % blockSize) * blockSize;
		int minr = (id / blockSize) * blockSize;

		for (int r = 0; r < blockSize; r++) {
			for (int c = 0; c < blockSize; c++) {
				g[r][c] = data[minr + r][minc + c];
			}
		}

		return g;
	}

	@Override
	public boolean verify() {
		return verifyRow() && verifyColumn() && verifyGrid();
	}
}
