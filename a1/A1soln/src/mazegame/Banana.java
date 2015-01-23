package mazegame;

/**
 * This class is used for Banana Objects. Its responsibilities are to create a
 * banana, and to get its value
 */
public class Banana extends Sprite {
	protected int value;

	public Banana(char symbol, int row, int column, int value) {
		super(symbol, row, column);
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
