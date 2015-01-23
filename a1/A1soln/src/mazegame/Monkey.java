package mazegame;

/**
 * This class is used for Players Its responsibilities are to create
 * Monkeys(players), to increment their score if they eat a banana, to get the
 * score of the Monkey, to get the number of moves, and to move the Monkey. This
 * class inherits Sprite and implements Moveable
 */
public class Monkey extends Sprite implements Moveable {
	int score;
	int numMoves;

	/**
	 * Creates a Monkey at the given row and column and denotes a symbol
	 * 
	 * @param symbol
	 *            a char to denote Monkey
	 * @param row
	 *            the row to place Monkey
	 * @param column
	 *            the column to place Monkey
	 */
	public Monkey(char symbol, int row, int col) {
		super(symbol, row, col);
		this.score = 0;
		this.numMoves = 0;
	}

	/**
	 * increments the score
	 * 
	 * @param score
	 *            the number to add to Monkey's score
	 */
	public void eatBanana(int score) {
		this.score += score;

	}

	/**
	 * 
	 * @return the score of Monkey
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * 
	 * @return the number of times Monkey moved
	 */
	public int getNumMoves() {
		return this.numMoves;
	}

	@Override
	/**
	 * Changes the location of Monkey to the given row and column
	 */
	public void move(int row, int col) {
		this.row = row;
		this.column = col;

	}

}
