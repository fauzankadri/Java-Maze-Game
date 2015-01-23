package mazegame;

/**
 * This interface is used to create a generic grid Its responsibilities are to
 * store generic items, get generic items from specific location, get the number
 * of rows and columns, check if 2 grid are the same and present the grid into a
 * matrices None of the methods are implemented. This interface will be used in
 * ArrayGrid.java
 * 
 * @param <T>
 *            A generic object. Will be used as Sprite
 */
public interface Grid<T> {
	public void setCell(int row, int col, T item);

	public T getCell(int row, int col);

	public int getNumRows();

	public int getNumCols();

	public boolean equals(Grid<T> other);

	public String toString();

}
