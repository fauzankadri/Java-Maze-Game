package mazegame;

/**
 * 
 * A class that represents the generic grid for the Maze Game. It is responsible
 * for creating a grid, storing generic items, and to print the grid in a
 * matrices form
 * 
 * @param <T>
 *            generic items. For this assignment, will be used as Sprites
 */
public class ArrayGrid<T> implements Grid<T> {
	private int numRows;
	private int numCols;
	private T[][] grid;

	/**
	 * 
	 * @param numRows
	 *            the number of rows for the grid
	 * @param numCols
	 *            the number of columns for the grid
	 */
	@SuppressWarnings("unchecked")
	public ArrayGrid(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.grid = (T[][]) new Object[this.numRows][this.numCols];
	}

	/**
	 * stores item inside the grid at the given row and col
	 */
	@Override
	public void setCell(int row, int col, T item) {
		grid[row][col] = item;
	}

	/**
	 * returns the item stored inside the grid at given row and col
	 */
	@Override
	public T getCell(int row, int col) {
		return grid[row][col];
	}

	/**
	 * returns the number of rows for this grid
	 */
	@Override
	public int getNumRows() {
		return this.numRows;
	}

	/**
	 * returns the number if columns for this grid
	 */
	@Override
	public int getNumCols() {
		return this.numCols;
	}

	/**
	 * returns true if this grid is the same as other grid, false otherwise
	 */
	@Override
	public boolean equals(@SuppressWarnings("rawtypes") Grid other) {
		// basic condition. if the number of columns are rows are not the same,
		// then its false
		if ((this.getNumCols() != other.getNumCols())
				&& (this.getNumRows() != other.getNumRows())) {
			return false;
		}
		// check if every item in this grid is EQUAL to other grid
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				if (this.getCell(i, j).equals(other.getCell(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * returns the grid in a matrices form
	 */
	@Override
	public String toString() {
		String ret = "";
		// iterate through every item in grid and store inside ret
		for (int i = 0; i < this.getNumRows(); i++) {
			for (int j = 0; j < this.getNumCols(); j++) {
				ret += grid[i][j] + "";

			}
			ret += "\n";
		}
		return ret;
	}

}
