package com.chess.pieces;

public class Rook extends Piece {
	public Rook(boolean isWhite) {
		super(isWhite);
		// Log message indicating the creation of a Rook piece with its color
		System.out.println("Creating " + (isWhite ? "white" : "black") + " Rook");
	}

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for Rook from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");

		// Rook moves in a straight line either horizontally or vertically
		if (fromRow != toRow && fromCol != toCol) {
			// If the move is neither purely horizontal nor vertical, it's invalid for a
			// rook
			System.out.println("Invalid move: Rook must move in a straight line");
			return false;
		}

		// Determine the direction of movement
		// rowDirection and colDirection will be -1, 0, or 1 to indicate the direction
		// of movement
		int rowDirection = Integer.compare(toRow, fromRow);
		int colDirection = Integer.compare(toCol, fromCol);
		System.out.println("Rook moving with direction row: " + rowDirection + ", col: " + colDirection);

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

		// The destination square must be either empty or contain an opponent's piece
		if (board[toRow][toCol] == null) {
			System.out.println("Destination square is empty");
		} else if (board[toRow][toCol].isWhite() != isWhite) {
			// If the destination contains an opponent's piece, the move is valid
			System.out.println("Destination square contains an opponent's piece");
		} else {
			// If the destination contains a piece of the same color, the move is invalid
			System.out.println("Invalid move: Destination square contains a piece of the same color");
			return false;
		}

		return true;
	}

	@Override
	public String getSymbol() {
		// Return the symbol for the rook, uppercase for white and lowercase for black
		return isWhite ? "R" : "r";
	}
}
