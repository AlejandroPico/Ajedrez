package com.chess.board;

import com.chess.pieces.Piece;

public class Square {
	private int row;
	private int col;
	private Piece piece;

	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		this.piece = null;
		System.out.println("Square created at position (" + row + ", " + col + ")");
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		if (piece == null) {
			System.out.println("Removing piece from square at position (" + row + ", " + col + ")");
		} else {
			System.out.println("Placing " + (piece.isWhite() ? "white" : "black") + " "
					+ piece.getClass().getSimpleName() + " at position (" + row + ", " + col + ")");
		}
		this.piece = piece;
	}

	@Override
	public String toString() {
		return piece == null ? "." : piece.getSymbol();
	}
}
