package com.chess.logic;

import com.chess.pieces.Piece;

public class MoveValidator {
	private Piece[][] board;

	public MoveValidator(Piece[][] board) {
		System.out.println("Initializing MoveValidator with the current board state.");
		this.board = board;
	}

	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
		System.out.println("Validating move from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		if (fromRow < 0 || fromRow >= board.length || fromCol < 0 || fromCol >= board[0].length || toRow < 0
				|| toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
			System.out.println("Move is out of bounds.");
			return false;
		}
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
			System.out.println("No piece at the source location.");
			return false;
		}
		System.out.println("Validating move for piece: " + piece.getClass().getSimpleName() + " from (" + fromRow + ", "
				+ fromCol + ") to (" + toRow + ", " + toCol + ")");
		boolean isValid = piece.isValidMove(fromRow, fromCol, toRow, toCol, board);
		System.out.println(
				"Move is " + (isValid ? "valid" : "invalid") + " for piece: " + piece.getClass().getSimpleName());
		return isValid;
	}

	public boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol) {
		System.out.println(
				"Checking if path is clear from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		if (fromRow < 0 || fromRow >= board.length || fromCol < 0 || fromCol >= board[0].length || toRow < 0
				|| toRow >= board.length || toCol < 0 || toCol >= board[0].length) {
			System.out.println("Path check is out of bounds.");
			return false;
		}
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
			System.out.println("No piece at the source location.");
			return false;
		}
		int rowDirection = Integer.compare(toRow, fromRow);
		int colDirection = Integer.compare(toCol, fromCol);
		int currentRow = fromRow + rowDirection;
		int currentCol = fromCol + colDirection;
		while (currentRow != toRow || currentCol != toCol) {
			if (board[currentRow][currentCol] != null) {
				System.out.println("Path blocked at (" + currentRow + ", " + currentCol + ")");
				return false;
			}
			currentRow += rowDirection;
			currentCol += colDirection;
		}
		System.out.println("Path is clear from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		return true;
	}
}