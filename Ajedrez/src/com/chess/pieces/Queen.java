package com.chess.pieces;

public class Queen extends Piece {
        public Queen(boolean isWhite) {
                super(isWhite);
        }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Queen moves like a Rook or a Bishop
		Rook rook = new Rook(isWhite);
		Bishop bishop = new Bishop(isWhite);
		if (rook.isValidMove(fromRow, fromCol, toRow, toCol, board)
				|| bishop.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
                        return true;
                } else {
                }
                return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "Q" : "q";
	}
}