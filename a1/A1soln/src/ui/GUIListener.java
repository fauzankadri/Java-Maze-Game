package ui;

import java.awt.event.*;

public class GUIListener extends KeyAdapter {

	private GUI window;

	public GUIListener(GUI window) {
		this.window = window;
	}

	public void keyPressed(KeyEvent event) {
	}

	public void keyTyped(KeyEvent event) {
		char nextMove = event.getKeyChar();
		this.window.getGame().move(nextMove);
		this.window.updateLabels();
	}

	public void keyReleased(KeyEvent event) {
	}
}
