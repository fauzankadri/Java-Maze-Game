package mazegame;

/**
 * This abstract class is used for Sprites Its responsibilities are to creating
 * Sprites, to get it's symbol, to get its location, and to get it's string
 * value. This class will be used for Monkey.java, Banana.java,
 * VisitedHallway.java, UnvisitedHallway.java, and Wall.java
 */
public abstract class Sprite {
	protected char symbol;
	protected int row;
	protected int column;

	/**
	 * Creates a generic Sprite object
	 * 
	 * @param symbol
	 *            a char to represent the created Sprite
	 * @param row
	 *            the row to place the Sprite
	 * @param column
	 *            the column to place the Sprite
	 */
	public Sprite(char symbol, int row, int col) {
		this.symbol = symbol;
		this.row = row;
		this.column = col;

	}

	/**
	 * 
	 * @return the symbol for a Sprite
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * 
	 * @return the row the that the Sprite is at
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 
	 * @return the column that the Sprite is at
	 */
	public int getCol() {
		return column;
	}

	@Override
	/**
	 * Represents the Sprite's symbol into a String
	 */
	public String toString() {
		return Character.toString(symbol);
	}

}
