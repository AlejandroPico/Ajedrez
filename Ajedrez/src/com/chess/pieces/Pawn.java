package com.chess.pieces;

public class Pawn extends Piece {
    public Pawn(boolean isWhite) {
        super(isWhite);
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

                // Ensure coordinates are within the board boundaries before accessing the board
                if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 ||
                    toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
                        return false;
                }
                int direction = isWhite ? -1 : 1;

		// Normal move forward
		if (fromCol == toCol) {
			if (board[toRow][toCol] == null) {
				if (toRow == fromRow + direction) {
					return true;
				}
				// Initial double move
				if ((isWhite && fromRow == 6) || (!isWhite && fromRow == 1)) {
					if (toRow == fromRow + 2 * direction && board[fromRow + direction][fromCol] == null) {
						return true;
					}
				}
			}
		} else if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction) {
			// Capturing an opponent's piece diagonally
			if (board[toRow][toCol] != null && board[toRow][toCol].isWhite() != isWhite) {
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getSymbol() {
		return isWhite ? "P" : "p";
	}
}