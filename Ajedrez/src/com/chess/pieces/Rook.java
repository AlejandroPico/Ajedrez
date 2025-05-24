package com.chess.pieces;

public class Rook extends Piece {
    private boolean hasMoved = false;

    public Rook(boolean isWhite) {
        super(isWhite);
        // System.out.println("Creating " + (isWhite ? "white" : "black") + " Rook");
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        // Boundary Checks for destination
        if (toRow < 0 || toRow >= 8 || toCol < 0 || toCol >= 8) {
            return false; // Target out of bounds
        }

        // Movement Pattern: Rook moves horizontally or vertically
        if (fromRow != toRow && fromCol != toCol) {
            return false; // Not a straight line move
        }
        if (fromRow == toRow && fromCol == toCol) {
            return false; // Cannot move to the same square
        }

        // Path Blocking
        if (fromRow == toRow) { // Horizontal move
            int step = (toCol > fromCol) ? 1 : -1;
            for (int c = fromCol + step; c != toCol; c += step) {
                if (board[fromRow][c] != null) {
                    return false; // Path blocked
                }
            }
        } else { // Vertical move (fromCol == toCol)
            int step = (toRow > fromRow) ? 1 : -1;
            for (int r = fromRow + step; r != toRow; r += step) {
                if (board[r][fromCol] != null) {
                    return false; // Path blocked
                }
            }
        }

        // Capture Logic: Destination square
        Piece destinationPiece = board[toRow][toCol];
        if (destinationPiece == null) { // Moving to an empty square
            return true;
        } else { // Moving to an occupied square
            if (destinationPiece.isWhite() != this.isWhite()) { // Capturing opponent's piece
                return true;
            } else { // Attempting to capture own piece
                return false;
            }
        }
    }

    @Override
    public String getSymbol() {
        return isWhite ? "R" : "r";
    }
}
