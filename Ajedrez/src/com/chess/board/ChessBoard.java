package com.chess.board;

import com.chess.pieces.Bishop;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Piece;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;
import com.chess.utils.Utils;

public class ChessBoard {
        private Piece[][] board;
        /**
         * Square that can be captured via en passant. Represented as
         * {row, col} or {@code null} if no en passant capture is available.
         */
        private int[] enPassantTargetSquare;

	public void initialize() {
                board = new Piece[8][8];

		// Initialize black pieces
		board[0][0] = new Rook(false);
		board[0][1] = new Knight(false);
		board[0][2] = new Bishop(false);
		board[0][3] = new Queen(false);
		board[0][4] = new King(false);
		board[0][5] = new Bishop(false);
		board[0][6] = new Knight(false);
		board[0][7] = new Rook(false);
		for (int i = 0; i < 8; i++) {
			board[1][i] = new Pawn(false);
		}

		// Initialize white pieces
		board[7][0] = new Rook(true);
		board[7][1] = new Knight(true);
		board[7][2] = new Bishop(true);
		board[7][3] = new Queen(true);
		board[7][4] = new King(true);
		board[7][5] = new Bishop(true);
		board[7][6] = new Knight(true);
		board[7][7] = new Rook(true);
		for (int i = 0; i < 8; i++) {
			board[6][i] = new Pawn(true);
		}
        }

	public void printBoard() {
                for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                                if (board[i][j] == null) {
                                        System.out.print(". ");
                                } else {
                                        System.out.print(board[i][j].getSymbol() + " ");
                                }
                        }
                        System.out.println();
                }
        }

	public boolean makeMove(String move) {
                // Basic move parsing (e.g., "e2 e4")
                String[] parts = move.split(" ");
                if (parts.length != 2) {
                        return false;
                }

		int fromRow = 8 - Character.getNumericValue(parts[0].charAt(1));
		int fromCol = parts[0].charAt(0) - 'a';
		int toRow = 8 - Character.getNumericValue(parts[1].charAt(1));
		int toCol = parts[1].charAt(0) - 'a';

		// Check if the move is within bounds
		if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 || toRow < 0 || toRow >= 8 || toCol < 0
				|| toCol >= 8) {
                        return false;
                }

		// Check if there is a piece to move
		Piece piece = board[fromRow][fromCol];
		if (piece == null) {
                        return false;
                }

		// Validate the move
		if (!piece.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
                        return false;
                }

		// Move the piece
                board[toRow][toCol] = piece;
                board[fromRow][fromCol] = null;
                return true;
        }

	public Piece getPiece(int row, int col) {
		if (row < 0 || row >= 8 || col < 0 || col >= 8) {
			return null; // Or throw an exception
		}
		return board[row][col];
	}

	public void setPiece(int row, int col, Piece piece) {
		if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                        return;
		}
		board[row][col] = piece;
	}

	public void removePiece(int row, int col) {
		if (row < 0 || row >= 8 || col < 0 || col >= 8) {
                        return;
		}
		board[row][col] = null;
	}

	public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
		if (fromRow < 0 || fromRow >= 8 || fromCol < 0 || fromCol >= 8 || toRow < 0 || toRow >= 8 || toCol < 0
				|| toCol >= 8) {
                        return;
		}
		Piece pieceToMove = board[fromRow][fromCol];
		if (pieceToMove == null) {
                        return;
		}
		// Handle capture: if there's a piece at the destination, it's captured (removed)
                if (board[toRow][toCol] != null) {
                        // capture
                }
                board[toRow][toCol] = pieceToMove;
                board[fromRow][fromCol] = null;
        }

        public Piece[][] getBoardArray() {
                return board;
        }

        /**
         * Returns the currently available en passant target square or {@code null}.
         */
        public int[] getEnPassantTargetSquare() {
                return enPassantTargetSquare;
        }

        /**
         * Sets the en passant target square.
         *
         * @param square coordinates {row, col} or {@code null} if none
         */
        public void setEnPassantTargetSquare(int[] square) {
                this.enPassantTargetSquare = square;
        }

        /**
         * Determines if a given square is attacked by any piece of the specified
         * color.
         */
        public boolean isSquareAttacked(int row, int col, boolean byWhite) {
                if (row < 0 || row >= 8 || col < 0 || col >= 8)
                        return false;

                // Directions for sliding pieces
                int[][] rookDirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
                int[][] bishopDirs = { { 1, 1 }, { 1, -1 }, { -1, 1 }, { -1, -1 } };

                // Check each square for attacking piece of the given color
                for (int r = 0; r < 8; r++) {
                        for (int c = 0; c < 8; c++) {
                                Piece p = board[r][c];
                                if (p == null || p.isWhite() != byWhite)
                                        continue;

                                if (p instanceof Pawn) {
                                        int dir = byWhite ? -1 : 1;
                                        if (r + dir == row && Math.abs(c - col) == 1)
                                                return true;
                                } else if (p instanceof Knight) {
                                        int[][] knightMoves = { { 2, 1 }, { 1, 2 }, { -1, 2 }, { -2, 1 }, { -2, -1 },
                                                        { -1, -2 }, { 1, -2 }, { 2, -1 } };
                                        for (int[] m : knightMoves) {
                                                if (r + m[0] == row && c + m[1] == col)
                                                        return true;
                                        }
                                } else if (p instanceof Bishop || p instanceof Queen) {
                                        for (int[] d : bishopDirs) {
                                                int rr = r + d[0];
                                                int cc = c + d[1];
                                                while (Utils.isWithinBounds(rr, cc)) {
                                                        if (rr == row && cc == col)
                                                                return true;
                                                        if (board[rr][cc] != null)
                                                                break;
                                                        rr += d[0];
                                                        cc += d[1];
                                                }
                                        }
                                        if (!(p instanceof Queen))
                                                continue;
                                }

                                if (p instanceof Rook || p instanceof Queen) {
                                        for (int[] d : rookDirs) {
                                                int rr = r + d[0];
                                                int cc = c + d[1];
                                                while (Utils.isWithinBounds(rr, cc)) {
                                                        if (rr == row && cc == col)
                                                                return true;
                                                        if (board[rr][cc] != null)
                                                                break;
                                                        rr += d[0];
                                                        cc += d[1];
                                                }
                                        }
                                        if (!(p instanceof Queen))
                                                continue;
                                }

                                if (p instanceof King) {
                                        if (Math.abs(r - row) <= 1 && Math.abs(c - col) <= 1)
                                                return true;
                                }
                        }
                }
                return false;
        }
}