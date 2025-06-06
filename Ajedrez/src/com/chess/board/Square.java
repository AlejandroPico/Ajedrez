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
        }

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
                if (piece == null) {
                        // remove piece
                } else {
                        // place piece
                }
                this.piece = piece;
	}

	@Override
	public String toString() {
		return piece == null ? "." : piece.getSymbol();
	}
}
