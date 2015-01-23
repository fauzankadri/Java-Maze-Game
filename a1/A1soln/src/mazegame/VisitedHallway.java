package mazegame;

/**
 * This class is used for VisitedHallways. Its responsibilities is to create a
 * VisitedHallway This class inherits from Sprite.java
 */
public class VisitedHallway extends Sprite {

	/**
	 * This will create a unvisited hallway
	 * 
	 * @param symbol
	 *            to denote what visited hallway will be
	 * @param row
	 *            the row to put unvisited hallway
	 * @param column
	 *            the column to put unvisited hallway
	 */
	public VisitedHallway(char symbol, int row, int col) {
		super(symbol, row, col);
	}

}
