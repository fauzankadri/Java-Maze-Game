package mazegame;

/**
 * This class is used for Unvisited Hallways. Its responsibilities is to create
 * a unvisited Hallway This class inherits from Sprite.java
 */
public class UnvisitedHallway extends Sprite {

	/**
	 * This will create a Unvisited Hallway
	 * 
	 * @param symbol
	 *            to denote what the unvisited hallway will be
	 * @param row
	 *            the row to put unvisited hallway
	 * @param column
	 *            the column to put unvisited hallway
	 */
	public UnvisitedHallway(char symbol, int row, int col) {
		super(symbol, row, col);
	}

}
