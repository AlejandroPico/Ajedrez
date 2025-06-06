package com.chess.pieces;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
        // Log message indicating the creation of a Pawn piece with its color
        System.out.println("Creating " + (isWhite ? "white" : "black") + " Pawn");
    }

    /**
     * Returns the movement direction for this pawn.
     * @return -1 for white pawns moving up, +1 for black pawns moving down.
     */
    public int getDirection() {
        return isWhite ? -1 : 1;
    }

	@Override
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
		// Log the initial move being validated
		System.out.println(
				"Validating move for Pawn from (" + fromRow + ", " + fromCol + ") to (" + toRow + ", " + toCol + ")");
		int direction = isWhite ? -1 : 1;

		// Normal move forward
		if (fromCol == toCol) {
			if (board[toRow][toCol] == null) {
				if (toRow == fromRow + direction) {
					System.out.println("Valid move: Normal move forward");
					return true;
				}
				// Initial double move
				if ((isWhite && fromRow == 6) || (!isWhite && fromRow == 1)) {
					if (toRow == fromRow + 2 * direction && board[fromRow + direction][fromCol] == null) {
						System.out.println("Valid move: Initial double move");
						return true;
					}
				}
			}
		} else if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction) {
			// Capturing an opponent's piece diagonally
			if (board[toRow][toCol] != null && board[toRow][toCol].isWhite() != isWhite) {
				System.out.println("Valid move: Capturing an opponent's piece");
				return true;
			}
		}
		System.out.println("Invalid move for Pawn");
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "P" : "p";
	}
}