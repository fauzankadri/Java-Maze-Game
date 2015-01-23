package mazegame;

/**
 * This class is used for Mobile Bananas Its responsibilities are to create a
 * Mobile Banana, and to move it. This class inherits from Banana.java and
 * implements Moveable.java
 */
public class MobileBanana extends Banana implements Moveable {

	/**
	 * Creates an object of Mobile Banana
	 * 
	 * @param symbol
	 *            a char that represents Mobile Bananas
	 * @param row
	 *            the row to store the Mobile Banana
	 * @param column
	 *            the column to store the Mobile Banana
	 * @param value
	 *            the value of getting a Mobile Banana
	 */
	public MobileBanana(char symbol, int row, int col, int value) {
		super(symbol, row, col, value);
	}

	/**
	 * moves Mobile Banana to the given row and column
	 */
	@Override
	public void move(int row, int col) {
		this.row = row;
		this.column = col;

	}

}
