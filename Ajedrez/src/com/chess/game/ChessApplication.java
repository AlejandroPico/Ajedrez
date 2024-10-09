package com.chess.game;

import com.chess.gui.BoardRenderer;
import com.chess.gui.ChessGUI;
import com.chess.gui.PieceImageLoader;

public class ChessApplication {
	
	public static void main(String[] args) {
		// Start the chess GUI
		System.out.println("Launching Chess GUI");
		ChessGUI chessGUI = new ChessGUI();
		BoardRenderer boardRenderer = new BoardRenderer(chessGUI.getBoardPanel());
        PieceImageLoader pieceImageLoader = new PieceImageLoader();

		// Initialize the chess game logic
		System.out.println("Initializing Chess Game");
		ChessGame chessGame = new ChessGame("Alice", "Bob", boardRenderer, pieceImageLoader);

		// Example moves (in algebraic notation)
		chessGame.makeMove("e2e4");
		chessGame.makeMove("e7e5");
		chessGame.makeMove("g1f3");

		// Print the current game status and move history
		chessGame.printGameStatus();
		chessGame.printMoveHistory();
	}
}
