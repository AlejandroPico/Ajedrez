package com.chess.pieces;

public class Queen extends Piece {
	public Queen(boolean isWhite) {
		super(isWhite);
		// Log message indicating the creation of a Queen piece with its color
		System.out.println("Creating " + (isWhite ? "white" : "black") + " Queen");
	}

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for Queen from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		// Queen moves like a Rook or a Bishop
		Rook rook = new Rook(isWhite);
		Bishop bishop = new Bishop(isWhite);
		if (rook.isValidMove(fromRow, fromCol, toRow, toCol, board)
				|| bishop.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
			System.out.println("Valid move for Queen");
			return true;
		} else {
			System.out.println("Invalid move for Queen");
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "Q" : "q";
	}
}