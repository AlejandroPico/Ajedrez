package com.chess.pieces;

public class Bishop extends Piece {
	public Bishop(boolean isWhite) {
		super(isWhite);
		// Log message indicating the creation of a Bishop piece with its color
		System.out.println("Creating " + (isWhite ? "white" : "black") + " Bishop");
	}

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for Bishop from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		// Bishop moves diagonally, so the row and column difference must be equal
		if (Math.abs(toRow - fromRow) != Math.abs(toCol - fromCol)) {
			System.out.println("Invalid move: Bishop must move diagonally");
			return false;
		}

		// Determine the direction of movement
		int rowDirection = Integer.compare(toRow, fromRow);
		int colDirection = Integer.compare(toCol, fromCol);
		System.out.println("Bishop moving with direction row: " + rowDirection + ", col: " + colDirection);

		// Check all squares between the start and the destination
		int currentRow = fromRow + rowDirection;
		int currentCol = fromCol + colDirection;
		while (currentRow != toRow || currentCol != toCol) {
			// If there is a piece in the way, the move is blocked
			if (board[currentRow][currentCol] != null) {
				System.out.println("Move blocked by piece at (" + currentRow + ", " + currentCol + ")");
				return false;
			}
			currentRow += rowDirection;
			currentCol += colDirection;
		}

		// The destination square must be empty or contain an opponent's piece
		if (board[toRow][toCol] == null || board[toRow][toCol].isWhite() != isWhite) {
			System.out.println("Valid move for Bishop");
			return true;
		} else {
			System.out.println("Invalid move: Destination square contains a piece of the same color");
		}
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "B" : "b";
	}
}