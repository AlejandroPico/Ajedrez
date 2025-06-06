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
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
		this.movedPiece = movedPiece;
		this.capturedPiece = capturedPiece;
                if (movedPiece != null) {
                }
                if (capturedPiece != null) {
                }
        }

	public int getFromRow() {
                return fromRow;
	}

	public int getFromCol() {
                return fromCol;
	}

	public int getToRow() {
                return toRow;
	}

	public int getToCol() {
                return toCol;
	}

	public Piece getMovedPiece() {
                return movedPiece;
	}

	public Piece getCapturedPiece() {
                return capturedPiece;
        }
}
