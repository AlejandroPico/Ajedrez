package com.chess.pieces;

public class Knight extends Piece {
        public Knight(boolean isWhite) {
                super(isWhite);
        }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		int rowDiff = Math.abs(toRow - fromRow);
		int colDiff = Math.abs(toCol - fromCol);
		// Knight moves in an L shape: two squares in one direction and one in the
		// perpendicular direction
		if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
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
		return isWhite ? "N" : "n";
	}
}