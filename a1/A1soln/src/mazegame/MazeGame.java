package mazegame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class that represents the basic functionality of the maze game. This class
 * is responsible for performing the following operations: 1. At creation, it
 * initializes the instance variables used to store the current state of the
 * game. 2. When a move is specified, it checks if it is a legal move and makes
 * the move if it is legal. 3. It reports information about the current state of
 * the game when asked.
 */
public class MazeGame {

	/** A random number generator to move the MobileBananas. */
	private Random random;

	/** The maze grid. */
	private Grid<Sprite> maze;

	/** The first player. */
	private Monkey player1;

	/** The second player. */
	private Monkey player2;

	/** The bananas to eat. */
	private List<Banana> bananas;

	/**
	 * @param layoutFileName
	 */
	public MazeGame(String layoutFileName) throws IOException {
		random = new Random();
		bananas = new ArrayList<Banana>();

		int[] dimensions = getDimensions(layoutFileName);
		maze = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);

		BufferedReader br = new BufferedReader(new FileReader(layoutFileName));

		/* INITIALIZE THE GRID HERE */
		// read the rows
		for (int i = 0; i < dimensions[0]; i++) {
			String nextLine = br.readLine();
			// read the columns
			for (int j = 0; j < dimensions[1]; j++) {
				// take one char at a time
				char smbl = nextLine.charAt(j);
				// depending on what it is, set it in the ith row and joth
				// column
				if (smbl == MazeConstants.WALL) { // wall
					maze.setCell(i, j, new Wall(MazeConstants.WALL, i, j));
				} else if (smbl == MazeConstants.BANANA) { // banana
					Banana item = new Banana(MazeConstants.BANANA, i, j,
							MazeConstants.BANANA_SCORE);
					bananas.add(item);
					maze.setCell(i, j, item);
				} else if (smbl == MazeConstants.MOBILE_BANANA) { // MobileBanana
					Banana item = new MobileBanana(MazeConstants.MOBILE_BANANA,
							i, j, MazeConstants.MOBILE_BANANA_SCORE);
					bananas.add(item);
					maze.setCell(i, j, item);
				} else if (smbl == MazeConstants.P1) { // player 1
					player1 = new Monkey(MazeConstants.P1, i, j);
					maze.setCell(i, j, player1);

				} else if (smbl == MazeConstants.P2) { // player 2
					player2 = new Monkey(MazeConstants.P2, i, j);
					maze.setCell(i, j, player2);
				} else if (smbl == MazeConstants.VACANT) { // vacant
					maze.setCell(i, j, new UnvisitedHallway(
							MazeConstants.VACANT, i, j));
				}
			}
		}

		br.close();
	}

	/**
	 * 
	 * @param layoutFileName
	 * @return The number of rows and columns of file respectively
	 * @throws IOException
	 */
	private int[] getDimensions(String layoutFileName) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(layoutFileName));

		// find the number of columns
		String nextLine = br.readLine();
		int numCols = nextLine.length();

		int numRows = 0;

		// find the number of rows
		while (nextLine != null && nextLine != "") {
			numRows++;
			nextLine = br.readLine();
		}

		br.close();
		return new int[] { numRows, numCols };
	}

	/**
	 * 
	 * @return the Sprite maze
	 */
	public Grid<Sprite> getMaze() {
		return maze;
	}

	/**
	 * 
	 * @return player 1 as Monkey
	 */
	public Monkey getPlayerOne() {
		return player1;
	}

	/**
	 * 
	 * @return player 2 as Monkey
	 */
	public Monkey getPlayerTwo() {
		return player2;
	}

	/**
	 * 
	 * @return the number of rows that the maze has
	 */
	public int getNumRows() {
		return maze.getNumRows();
	}

	/**
	 * 
	 * @return the number of columns that the maze has
	 */
	public int getNumCols() {
		return maze.getNumCols();
	}

	/**
	 * 
	 * @param i
	 *            ith row
	 * @param j
	 *            jth column
	 * @return the item that is stored in ith row and jth column in the maze
	 */
	public Sprite get(int i, int j) {
		return maze.getCell(i, j);
	}

	/**
	 * 
	 * @param nextMove
	 *            the move that the user's input
	 */
	public void move(char nextMove) {
		// case 1: the nextMove is for player1
		// checks if the nextMove is a valid key/input
		if ((nextMove == MazeConstants.P1_UP)
				|| (nextMove == MazeConstants.P1_LEFT)
				|| (nextMove == MazeConstants.P1_DOWN)
				|| (nextMove == MazeConstants.P1_RIGHT)) {
			// moves player1 and also moves mobile bananas
			playerOneMove(nextMove);
			;
		}
		// case 2: the nextMove is for player2
		// checks if the nextMove is a valid key/input
		else if ((nextMove == MazeConstants.P2_UP)
				|| (nextMove == MazeConstants.P2_LEFT)
				|| (nextMove == MazeConstants.P2_DOWN)
				|| (nextMove == MazeConstants.P2_RIGHT)) {
			// moves player2 if possible and also mobile bananas
			playerTwoMove(nextMove);
		}
	}

	/**
	 * 
	 * @return 0 if game is sill running. 1 if player 1 wins. 2 if player2 wins.
	 *         3 if its a tie.
	 */
	public int hasWon() {
		// the game only ends when all bananas are eaten. 0 will indicate that
		// game is not finished
		if (bananas.size() != 0) {
			return 0;
		}
		// 3 indicates that the game is a tie
		else if (player1.getScore() == player2.getScore()) {
			return 3;
		}
		// 1 indicates that player1 wins
		else if (player1.getScore() > player2.getScore()) {
			return 1;
		}
		// the only other option is that player2 wins, denoted by 2
		else {
			return 2;
		}
	}

	/**
	 * 
	 * @return true if both player 1 and player 2 are stuck. false otherwise
	 */
	public boolean isBlocked() {
		// both players must be stuck to return true
		if ((playerOneStuck() == true) && (playerTwoStuck() == true)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return true if player 1 is stuck from all directions. false otherwise
	 */
	private boolean playerOneStuck() {
		// getting all useful information
		int p1row = player1.getRow();
		int p1col = player1.getCol();
		int numCols = getNumCols();
		int numRows = getNumRows();
		List<Boolean> isStuck = new ArrayList<Boolean>();

		// if something exists above player1, we check what it is and if we can
		// move
		// otherwise player1 can't go above
		if (p1row != 0) {
			Sprite up = get(p1row - 1, p1col);
			isStuck.add(isStuck(up));
		}
		// if something exists left of player1, we check what it is and if we
		// can move
		// otherwise player1 can't go there
		if (p1col != 0) {
			Sprite left = get(p1row, p1col - 1);
			isStuck.add(isStuck(left));
		}
		// if something exists below player1, we check what it is and if we can
		// move
		// otherwise player1 can't go there
		if (p1row != numRows - 1) {
			Sprite down = get(p1row + 1, p1col);
			isStuck.add(isStuck(down));
		}
		// if something exists right of player1, we check what it is and if we
		// can move
		// otherwise player1 can't go there
		if (p1col != numCols - 1) {
			Sprite right = get(p1row, p1col + 1);
			isStuck.add(isStuck(right));
		}
		// we gathered all our information about where player1 is stuck
		// if our array contains false (which will return true) then it means
		// player1
		// is NOT stuck and hence we return player1 is NOT stuck. otherwise true
		if (isStuck.contains(false)) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @return true if player 2 is stuck from all directions. false otherwise
	 */
	private boolean playerTwoStuck() {
		// gather all useful information
		int p2row = player2.getRow();
		int p2col = player2.getCol();
		int numCols = getNumCols();
		int numRows = getNumRows();
		List<Boolean> isStuck = new ArrayList<Boolean>();

		// if something exists above player2, we check what it is and if we can
		// move
		// otherwise player2 can't go there
		if (p2row != 0) {
			Sprite up = get(p2row - 1, p2col);
			isStuck.add(isStuck(up));
		}
		// if something exists left of player2, we check what it is and if we
		// can move
		// otherwise player2 can't go there
		if (p2col != 0) {
			Sprite left = get(p2row, p2col - 1);
			isStuck.add(isStuck(left));
		}
		// if something exists below player2, we check what it is and if we can
		// move
		// otherwise player2 can't go there
		if (p2row != numRows - 1) {
			Sprite down = get(p2row + 1, p2col);
			isStuck.add(isStuck(down));
		}
		// if something exists right of player2, we check what it is and if we
		// can move
		// otherwise player2 can't go there
		if (p2col != numCols - 1) {
			Sprite right = get(p2row, p2col + 1);
			isStuck.add(isStuck(right));
		}
		// we gathered all our information about where player2 is stuck
		// if our array contains false (which will return true) then it means
		// player2
		// is NOT stuck and hence we return player2 is NOT stuck. otherwise true
		if (isStuck.contains(false)) {

			return false;
		}
		return true;

	}

	/**
	 * 
	 * @param item
	 *            an item from the maze
	 * @return true if the item is a wall, a visited dot, or a player. false
	 *         otherwise
	 */
	private boolean isStuck(Sprite item) {
		// after getting Sprites that are around players, we see if the item is
		// a visited node,
		// a wall, or a player. if yes, return true, false otherwise.
		if ((item.symbol == MazeConstants.VISITED)
				|| (item.symbol == MazeConstants.WALL)
				|| (item.symbol == MazeConstants.P1)
				|| (item.symbol == MazeConstants.P2)) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param nextMove
	 *            takes the input and moves player 1 accordingly if its possible
	 */
	private void playerOneMove(char nextMove) {
		// gather all useful information
		int row = player1.getRow();
		int col = player1.getCol();
		int numRows = getNumRows();
		int numCols = getNumCols();

		// player1 wants to go up
		if (nextMove == MazeConstants.P1_UP) {
			// in-case player is not trying to 'leave' the maze
			if (row != 0) {
				Sprite up = get(row - 1, col);
				// if player1 is not stuck from above, then we move our player1
				// and update the maze
				if (!isStuck(up)) {
					moveMobileBananas();
					player1.move(row - 1, col);
					maze.setCell(row - 1, col, player1);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player1.numMoves += 1;

				}
				// if player1 happens to move into a banana, we must remove that
				// banana from the list bananas
				// and increase the score by bananas value
				if (up.symbol == MazeConstants.BANANA) {
					bananas.remove(up);
					player1.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but rare case with mobile banana
				else if (up.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(up);
					player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player1 wants to go left
		else if (nextMove == MazeConstants.P1_LEFT) {
			// in-case player1 is trying to leave the maze
			if (col != 0) {
				Sprite left = get(row, col - 1);
				// make sure that player1 can move to the left
				if (!isStuck(left)) {
					// move accordingly and update the maze
					moveMobileBananas();
					player1.move(row, col - 1);
					maze.setCell(row, col - 1, player1);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player1.numMoves += 1;

				}
				// player1 runs into a banana, remove from list bananas and
				// increase player1 score
				if (left.symbol == MazeConstants.BANANA) {
					bananas.remove(left);
					player1.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile banana
				else if (left.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(left);
					player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player1 wants to go down
		else if (nextMove == MazeConstants.P1_DOWN) {
			// make sure player1 is not leaving the maze
			if (row != numRows - 1) {
				Sprite down = get(row + 1, col);
				// make sure player1 can move down
				if (!isStuck(down)) {
					// move player1 and update the maze
					moveMobileBananas();
					player1.move(row + 1, col);
					maze.setCell(row + 1, col, player1);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player1.numMoves += 1;

				}
				// player1 eats a banana, remove banana from list bananas and
				// increase player1 score
				if (down.symbol == MazeConstants.BANANA) {
					bananas.remove(down);
					player1.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// special case with mobile banana but same as above
				else if (down.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(down);
					player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player1 wants to move right
		else if (nextMove == MazeConstants.P1_RIGHT) {
			// in-case player1 is trying to leave the maze
			if (col != numCols - 1) {
				Sprite right = get(row, col + 1);
				// make sure player1 can move right
				if (!isStuck(right)) {
					// move player1 accordingly and update the maze
					moveMobileBananas();
					player1.move(row, col + 1);
					maze.setCell(row, col + 1, player1);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player1.numMoves += 1;

				}
				// player1 eats a banana, we remove banana from list bananas and
				// increase player1 score
				if (right.symbol == MazeConstants.BANANA) {
					bananas.remove(right);
					player1.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile bananas
				else if (right.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(right);
					player1.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
	}

	/**
	 * 
	 * @param nextMove
	 *            takes the move and moves player 2 accordingly if its possible
	 */
	private void playerTwoMove(char nextMove) {
		// gather useful information
		int row = player2.getRow();
		int col = player2.getCol();
		int numRows = getNumRows();
		int numCols = getNumCols();

		// player2 wants to move up
		if (nextMove == MazeConstants.P2_UP) {
			// in-case player2 tried to leave maze
			if (row != 0) {
				Sprite up = get(row - 1, col);
				// make sure player2 can move up
				if (!isStuck(up)) {
					// move player2 and update the maze
					moveMobileBananas();
					player2.move(row - 1, col);
					maze.setCell(row - 1, col, player2);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player2.numMoves += 1;

				}
				// if player2 eats a banana, we remove the banana from list
				// bananas, and increase player2 score
				if (up.symbol == MazeConstants.BANANA) {
					bananas.remove(up);
					player2.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile bananas
				else if (up.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(up);
					player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player2 tries to move left
		else if (nextMove == MazeConstants.P2_LEFT) {
			// in-case player2 tries to leave maze
			if (col != 0) {
				Sprite left = get(row, col - 1);
				// make sure player2 can move left
				if (!isStuck(left)) {
					// move player2 and update the maze
					moveMobileBananas();
					player2.move(row, col - 1);
					maze.setCell(row, col - 1, player2);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player2.numMoves += 1;

				}
				// player2 eats a banana, we remove banana from list bananas and
				// increase player2 score
				if (left.symbol == MazeConstants.BANANA) {
					bananas.remove(left);
					player2.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile bananas
				else if (left.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(left);
					player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player2 tries to move down
		else if (nextMove == MazeConstants.P2_DOWN) {
			// in-case player2 tries to leave maze
			if (row != numRows - 1) {
				Sprite down = get(row + 1, col);
				// make sure player2 can move down
				if (!isStuck(down)) {
					// move player2 accordingly and update the maze
					moveMobileBananas();
					player2.move(row + 1, col);
					maze.setCell(row + 1, col, player2);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player2.numMoves += 1;

				}
				// player2 eats a banana, remove banana from list bananas and
				// increase player2 score
				if (down.symbol == MazeConstants.BANANA) {
					bananas.remove(down);
					player2.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile bananas
				else if (down.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(down);
					player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
		// player2 tries to move right
		else if (nextMove == MazeConstants.P2_RIGHT) {
			// make sure player2 is not leaving the maze
			if (col != numCols - 1) {
				Sprite right = get(row, col + 1);
				// make sure player2 can move right
				if (!isStuck(right)) {
					// move player2 and update the maze
					moveMobileBananas();
					player2.move(row, col + 1);
					maze.setCell(row, col + 1, player2);
					maze.setCell(row, col, new VisitedHallway(
							MazeConstants.VISITED, row, col));
					player2.numMoves += 1;

				}
				// player2 eats banana, remove banana from list bananas and
				// increase player2 score
				if (right.symbol == MazeConstants.BANANA) {
					bananas.remove(right);
					player2.eatBanana(MazeConstants.BANANA_SCORE);
				}
				// same as above but with mobile bananas
				else if (right.symbol == MazeConstants.MOBILE_BANANA) {
					bananas.remove(right);
					player2.eatBanana(MazeConstants.MOBILE_BANANA_SCORE);
				}
			}
		}
	}

	/**
	 * moves mobile bananas randomly one tile at a time
	 */
	private void moveMobileBananas() {
		// make useful tools to help us move mobile bananas
		int rand = 0;
		boolean[] tried = { false, false, false, false };

		// we get every banana from bananas, denotes as b
		for (Banana b : bananas) {
			// we check if b is a mobile banana. if yes, we continue. else we go
			// to the next banana
			if (b instanceof MobileBanana) {
				do {
					// generate a random integer from 0-3
					// get useful information about the mobile banana
					rand = random.nextInt(4);
					int row = b.getRow();
					int col = b.getCol();

					// we tried to move the banana
					// tried[0] means we tried to move it up
					// tried[1] means we tried to move it left
					// tried[2] means we tried to move it down
					// tried[3] means we tried to move it right
					tried[rand] = true;

					// trying to move mobile banana up
					if ((rand == 0) && (row != 0)) {
						Sprite up = get(row - 1, col);
						// it can only move if there's a vacant
						if (up.symbol == MazeConstants.VACANT) {
							// move mobile banana and update the maze
							((MobileBanana) b).move(row - 1, col);
							maze.setCell(row - 1, col, b);
							maze.setCell(row, col, new UnvisitedHallway(
									MazeConstants.VACANT, row, col));
							// if mobile banana is moved, then we don't need
							// to check other location
							// this is our exit condition for the current banana
							tried[1] = true;
							tried[2] = true;
							tried[3] = true;
						}
					}
					// trying to move mobile banana left
					else if ((rand == 1) && (col != 0)) {
						Sprite left = get(row, col - 1);
						// it can only move if there's a vacant
						if (left.symbol == MazeConstants.VACANT) {
							// move mobile banana and update the maze
							((MobileBanana) b).move(row, col - 1);
							maze.setCell(row, col - 1, b);
							maze.setCell(row, col, new UnvisitedHallway(
									MazeConstants.VACANT, row, col));
							// if mobile banana is moved, then we don't need
							// to check other location
							// this is our exit condition for the current banana
							tried[0] = true;
							tried[2] = true;
							tried[3] = true;
						}
					}
					// trying to move mobile banana down
					else if ((rand == 2) && (row != getNumRows() - 1)) {
						Sprite down = get(row + 1, col);
						// it can only move if there's a vacant
						if (down.symbol == MazeConstants.VACANT) {
							// move mobile banana and update the maze
							((MobileBanana) b).move(row + 1, col);
							maze.setCell(row + 1, col, b);
							maze.setCell(row, col, new UnvisitedHallway(
									MazeConstants.VACANT, row, col));
							// if mobile banana is moved, then we don't need
							// to check other location
							// this is our exit condition for the current banana
							tried[0] = true;
							tried[1] = true;
							tried[3] = true;
						}
					}
					// trying to move mobile banana right
					else if ((rand == 3) && (col != getNumCols() - 1)) {
						Sprite right = get(row, col + 1);
						// it can only move if there's a vacant
						if (right.symbol == MazeConstants.VACANT) {
							// move mobile banana and update the maze
							((MobileBanana) b).move(row, col + 1);
							maze.setCell(row, col + 1, b);
							maze.setCell(row, col, new UnvisitedHallway(
									MazeConstants.VACANT, row, col));
							// if mobile banana is moved, then we don't need
							// to check other location
							// this is our exit condition for the current banana
							tried[0] = true;
							tried[1] = true;
							tried[2] = true;
						}
					}
				} while (!tried[0] || !tried[1] || !tried[2] || !tried[3]);
			}
		}
	}
}
