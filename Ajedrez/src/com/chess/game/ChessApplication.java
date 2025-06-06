package com.chess.game;

import com.chess.gui.ChessGUI;

public class ChessApplication {
	
	public static void main(String[] args) {
        // Start the chess GUI and game logic
        ChessGUI chessGUI = new ChessGUI();
        ChessGame chessGame = new ChessGame("Alice", "Bob", chessGUI);

		// Example moves (in algebraic notation)
		chessGame.makeMove("e2e4");
		chessGame.makeMove("e7e5");
		chessGame.makeMove("g1f3");

		// Print the current game status and move history
		chessGame.printGameStatus();
		chessGame.printMoveHistory();
	}
}
