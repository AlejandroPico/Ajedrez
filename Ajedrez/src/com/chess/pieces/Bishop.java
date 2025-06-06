package com.chess.pieces;

public class Bishop extends Piece {
        public Bishop(boolean isWhite) {
                super(isWhite);
        }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Bishop moves diagonally, so the row and column difference must be equal
		if (Math.abs(toRow - fromRow) != Math.abs(toCol - fromCol)) {
                        return false;
		}

		// Determine the direction of movement
		int rowDirection = Integer.compare(toRow, fromRow);
		int colDirection = Integer.compare(toCol, fromCol);

		// Check all squares between the start and the destination
		int currentRow = fromRow + rowDirection;
		int currentCol = fromCol + colDirection;
		while (currentRow != toRow || currentCol != toCol) {
			// If there is a piece in the way, the move is blocked
                        if (board[currentRow][currentCol] != null) {
                                return false;
                        }
			currentRow += rowDirection;
			currentCol += colDirection;
		}

		// The destination square must be empty or contain an opponent's piece
                if (board[toRow][toCol] == null || board[toRow][toCol].isWhite() != isWhite) {
                        return true;
                } else {
                }
                return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "B" : "b";
	}
}