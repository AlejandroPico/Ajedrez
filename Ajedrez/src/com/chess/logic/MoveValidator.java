package com.chess.logic;

import com.chess.pieces.Piece;

public class MoveValidator {
	private Piece[][] board;

	public MoveValidator(Piece[][] board) {
                this.board = board;
	}

	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
		if (fromRow < 0 || fromRow >= board.length || fromCol < 0 || fromCol >= board[0].length || toRow < 0
				|| toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
                        return false;
		}
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
                        return false;
		}
		boolean isValid = piece.isValidMove(fromRow, fromCol, toRow, toCol, board);
                return isValid;
	}

	public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
		if (fromRow < 0 || fromRow >= board.length || fromCol < 0 || fromCol >= board[0].length || toRow < 0
				|| toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
                        return false;
		}
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
                        return false;
		}
		int rowDirection = Integer.compare(toRow, fromRow);
		int colDirection = Integer.compare(toCol, fromCol);
		int currentRow = fromRow + rowDirection;
		int currentCol = fromCol + colDirection;
		while (currentRow != toRow || currentCol != toCol) {
			if (board[currentRow][currentCol] != null) {
                                return false;
			}
			currentRow += rowDirection;
			currentCol += colDirection;
		}
                return true;
        }
}