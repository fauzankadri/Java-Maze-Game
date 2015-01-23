package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mazegame.MazeConstants;
import mazegame.MazeGame;

/**
 * A class that is used when user's want to play in a simple text format without
 * GUI. It is responsible for running the game until the game is either won,
 * tied, or players are stuck
 */
public class TextUI implements UI {

	private MazeGame game;

	/**
	 * Initializes the text version of the game
	 * 
	 * @param game
	 *            the MazeGame of this UI
	 */
	public TextUI(MazeGame game) {
		this.game = game;
	}

	/**
	 * Runs the game in text version. Players will need to give input
	 */
	@Override
	public void launchGame() {
		// create useful tools
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		char move;
		boolean exit = false;

		// continues to run until the game ends
		while (!exit) {
			try {
				do {
					// get the next move from user's
					System.out.print("next move: ");
					input = br.readLine();
					System.out.println();
				} while (input.length() != 1);
			} catch (IOException e) {
			}
			move = input.charAt(0);
			// player1 moves if its for them
			if (move == MazeConstants.P1_UP) {
				game.move(MazeConstants.P1_UP);
			} else if (move == MazeConstants.P1_LEFT) {
				game.move(move);
			} else if (move == MazeConstants.P1_DOWN) {
				game.move(move);
			} else if (move == MazeConstants.P1_RIGHT) {
				game.move(move);
			}
			// player2 moves if its for them
			else if (move == MazeConstants.P2_UP) {
				game.move(move);
			} else if (move == MazeConstants.P2_LEFT) {
				game.move(move);
			} else if (move == MazeConstants.P2_DOWN) {
				game.move(move);
			} else if (move == MazeConstants.P2_RIGHT) {
				game.move(move);
			}

			// at the end of each move, print the maze to update the user's
			System.out.println(game.getMaze().toString());
			// exit condition: all players are blocked or all bananas are eaten
			if (game.isBlocked() || game.hasWon() != 0) {
				exit = true;
			}

		}
	}

	/**
	 * Tells the user's who wins the game
	 */
	@Override
	public void displayWinner() {
		int won = game.hasWon();

		if (game.isBlocked()) { // no winners
			System.out.print("Game over! Both players are stuck.");
		} else {
			if (won == 0) { // game is still on
				return;
			} else if (won == 1) {
				System.out
						.print("Congratulations Player 1! You won the maze in "
								+ game.getPlayerOne().getNumMoves() + " moves.");
			} else if (won == 2) {
				System.out
						.print("Congratulations Player 2! You won the maze in "
								+ game.getPlayerTwo().getNumMoves() + " moves.");
			} else { // it's a tie
				System.out.print("It's a tie!");
			}
		}

	}

}
