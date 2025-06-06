package com.chess.pieces;

public class King extends Piece {
        public King(boolean isWhite) {
                super(isWhite);
        }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		int rowDiff = Math.abs(toRow - fromRow);
		int colDiff = Math.abs(toCol - fromCol);
		// King moves one square in any direction
		if (rowDiff <= 1 && colDiff <= 1) {
			// The destination square must be empty or contain an opponent's piece
			if (board[toRow][toCol] == null || board[toRow][toCol].isWhite() != isWhite) {
                                return true;
                        } else {
                        }
		} else {
                        
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "K" : "k";
	}
}