package com.chess.pieces;

public class King extends Piece {
	public King(boolean isWhite) {
		super(isWhite);
		// Log message indicating the creation of a King piece with its color
		System.out.println("Creating " + (isWhite ? "white" : "black") + " King");
	}

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for King from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		int rowDiff = Math.abs(toRow - fromRow);
		int colDiff = Math.abs(toCol - fromCol);
		// King moves one square in any direction
		if (rowDiff <= 1 && colDiff <= 1) {
			// The destination square must be empty or contain an opponent's piece
			if (board[toRow][toCol] == null || board[toRow][toCol].isWhite() != isWhite) {
				System.out.println("Valid move for King");
				return true;
			} else {
				System.out.println("Invalid move: Destination square contains a piece of the same color");
			}
		} else {
			System.out.println("Invalid move: King must move one square in any direction");
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "K" : "k";
	}
}