package com.chess.moves;

import com.chess.pieces.Piece;

public class Move {
	private int fromRow;
	private int fromCol;
	private int toRow;
	private int toCol;
	private Piece movedPiece;
	private Piece capturedPiece;

	public Move(int fromRow, int fromCol, int toRow, int toCol, Piece movedPiece, Piece capturedPiece) {
		System.out.println("Creating move from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
		this.movedPiece = movedPiece;
		this.capturedPiece = capturedPiece;
		if (movedPiece != null) {
			System.out.println("Moved piece: " + movedPiece.getClass().getSimpleName());
		}
		if (capturedPiece != null) {
			System.out.println("Captured piece: " + capturedPiece.getClass().getSimpleName());
		}
	}

	public int getFromRow() {
		System.out.println("Getting fromRow: " + fromRow);
		return fromRow;
	}

	public int getFromCol() {
		System.out.println("Getting fromCol: " + fromCol);
		return fromCol;
	}

	public int getToRow() {
		System.out.println("Getting toRow: " + toRow);
		return toRow;
	}

	public int getToCol() {
		System.out.println("Getting toCol: " + toCol);
		return toCol;
	}

	public Piece getMovedPiece() {
		System.out.println(
				"Getting moved piece: " + (movedPiece != null ? movedPiece.getClass().getSimpleName() : "null"));
		return movedPiece;
	}

	public Piece getCapturedPiece() {
		System.out.println("Getting captured piece: "
				+ (capturedPiece != null ? capturedPiece.getClass().getSimpleName() : "null"));
		return capturedPiece;
	}
}
