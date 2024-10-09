package com.chess.pieces;

public abstract class Piece {
	protected boolean isWhite;

	public Piece(boolean isWhite) {
		this.isWhite = isWhite;
		System.out.println("Creating " + (isWhite ? "white" : "black") + " piece: " + this.getClass().getSimpleName());
	}

	public boolean isWhite() {
		return isWhite;
	}

	public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);

	public abstract String getSymbol();
}