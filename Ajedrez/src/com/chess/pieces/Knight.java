package com.chess.pieces;

public class Knight extends Piece {
	public Knight(boolean isWhite) {
		super(isWhite);
		// Log message indicating the creation of a Knight piece with its color
		System.out.println("Creating " + (isWhite ? "white" : "black") + " Knight");
	}

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for Knight from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		int rowDiff = Math.abs(toRow - fromRow);
		int colDiff = Math.abs(toCol - fromCol);
		// Knight moves in an L shape: two squares in one direction and one in the
		// perpendicular direction
		if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
			// The destination square must be empty or contain an opponent's piece
			if (board[toRow][toCol] == null || board[toRow][toCol].isWhite() != isWhite) {
				System.out.println("Valid move for Knight");
				return true;
			} else {
				System.out.println("Invalid move: Destination square contains a piece of the same color");
			}
		} else {
			System.out.println("Invalid move: Knight must move in an L shape");
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "N" : "n";
	}
}