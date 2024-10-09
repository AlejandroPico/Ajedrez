package com.chess.board;

import com.chess.pieces.Bishop;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

public class ChessBoard {
	private Piece[][] board;

	public void initialize() {
		System.out.println("Initializing the chess board...");
		board = new Piece[8][8];

		// Initialize black pieces
		board[0][0] = new Rook(false);
		board[0][1] = new Knight(false);
		board[0][2] = new Bishop(false);
		board[0][3] = new Queen(false);
		board[0][4] = new King(false);
		board[0][5] = new Bishop(false);
		board[0][6] = new Knight(false);
		board[0][7] = new Rook(false);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(false);
		}

		// Initialize white pieces
		board[7][0] = new Rook(true);
		board[7][1] = new Knight(true);
		board[7][2] = new Bishop(true);
		board[7][3] = new Queen(true);
		board[7][4] = new King(true);
		board[7][5] = new Bishop(true);
		board[7][6] = new Knight(true);
		board[7][7] = new Rook(true);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(true);
		}
		System.out.println("Chess board initialized.");
	}

	public void printBoard() {
		System.out.println("Printing the current state of the board:");
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] == null) {
					System.out.print(". ");
				} else {
					System.out.print(board[i][j].getSymbol() + " ");
				}
			}
			System.out.println();
		}
	}

	public boolean makeMove(String move) {
		System.out.println("Attempting to make move: " + move);
		// Basic move parsing (e.g., "e2 e4")
		String[] parts = move.split(" ");
		if (parts.length != 2) {
			System.out.println("Invalid input format. Please use the format 'e2 e4'.");
			return false;
		}

		int fromRow = 8 - Character.getNumericValue(parts[0].charAt(1));
		int fromCol = parts[0].charAt(0) - 'a';
		int toRow = 8 - Character.getNumericValue(parts[1].charAt(1));
		int toCol = parts[1].charAt(0) - 'a';

		// Check if the move is within bounds
		if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 || toRow < 0 || toRow >= 8 || toCol < 0
				|| toCol >= 8) {
			System.out.println("Move out of bounds. Please try again.");
			return false;
		}

		// Check if there is a piece to move
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
			System.out.println("No piece at the source location. Please try again.");
			return false;
		}

		// Validate the move
		if (!piece.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
			System.out.println("Invalid move for the selected piece. Please try again.");
			return false;
		}

		// Move the piece
		System.out.println("Moving piece from " + parts[0] + " to " + parts[1]);
		board[toRow][toCol] = piece;
		board[fromRow][fromCol] = null;
		System.out.println("Move completed.");
		return true;
	}
}