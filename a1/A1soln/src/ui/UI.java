package ui;

/**
 * This interface is used to operate the game. Its responsibilities are to
 * launch the game until it finishes and to display the winner. None of the
 * methods are implemented. This interface is used with GUI.java and TextUI.java
 */
public interface UI {
	public void launchGame();

	public void displayWinner();

}
