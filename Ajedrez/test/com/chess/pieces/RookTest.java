package com.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.chess.board.ChessBoard;

public class RookTest {

    private ChessBoard board;
    private Piece[][] boardArray;

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        board.initialize(); // Initialize to get the 8x8 board array structure
        boardArray = board.getBoardArray();
        
        // Clear the board for specific test setups
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardArray[i][j] = null;
            }
        }
    }

    // --- Valid Moves to Empty Squares ---

    @Test
    void testValidHorizontalMoveEmpty_Right() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5 (row 3, col 3)
        assertTrue(whiteRook.isValidMove(3, 3, 3, 7, boardArray), "Rook should move horizontally right to h5");
    }

    @Test
    void testValidHorizontalMoveEmpty_Left() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertTrue(whiteRook.isValidMove(3, 3, 3, 0, boardArray), "Rook should move horizontally left to a5");
    }

    @Test
    void testValidVerticalMoveEmpty_Down() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertTrue(whiteRook.isValidMove(3, 3, 7, 3, boardArray), "Rook should move vertically down to d1");
    }

    @Test
    void testValidVerticalMoveEmpty_Up() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertTrue(whiteRook.isValidMove(3, 3, 0, 3, boardArray), "Rook should move vertically up to d8");
    }
    
    @Test
    void testValidShortHorizontalMove() {
        Rook whiteRook = new Rook(true);
        boardArray[0][0] = whiteRook; // Rook at a8
        assertTrue(whiteRook.isValidMove(0, 0, 0, 1, boardArray), "Rook should move one square horizontally to b8");
    }

    @Test
    void testValidShortVerticalMove() {
        Rook whiteRook = new Rook(true);
        boardArray[0][0] = whiteRook; // Rook at a8
        assertTrue(whiteRook.isValidMove(0, 0, 1, 0, boardArray), "Rook should move one square vertically to a7");
    }

    // --- Valid Captures ---

    @Test
    void testValidHorizontalCapture_Right() {
        Rook whiteRook = new Rook(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[3][3] = whiteRook;   // White Rook at d5
        boardArray[3][7] = blackPawn; // Black Pawn at h5
        assertTrue(whiteRook.isValidMove(3, 3, 3, 7, boardArray), "Rook should capture opponent horizontally right at h5");
    }

    @Test
    void testValidVerticalCapture_Up() {
        Rook whiteRook = new Rook(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[3][3] = whiteRook;   // White Rook at d5
        boardArray[0][3] = blackPawn; // Black Pawn at d8
        assertTrue(whiteRook.isValidMove(3, 3, 0, 3, boardArray), "Rook should capture opponent vertically up at d8");
    }

    // --- Invalid Moves: Path Blocking ---

    @Test
    void testInvalidHorizontalMoveBlocked_Right() {
        Rook whiteRook = new Rook(true);
        Pawn friendlyPawn = new Pawn(true);
        boardArray[3][3] = whiteRook;      // White Rook at d5
        boardArray[3][5] = friendlyPawn; // Friendly Pawn at f5 (blocking path to h5)
        assertFalse(whiteRook.isValidMove(3, 3, 3, 7, boardArray), "Rook cannot move horizontally right if path is blocked");
    }

    @Test
    void testInvalidHorizontalMoveBlocked_ByOpponent() {
        Rook whiteRook = new Rook(true);
        Pawn opponentPawn = new Pawn(false);
        boardArray[3][3] = whiteRook;       // White Rook at d5
        boardArray[3][5] = opponentPawn;  // Opponent Pawn at f5 (blocking path to h5)
        assertFalse(whiteRook.isValidMove(3, 3, 3, 7, boardArray), "Rook cannot move horizontally right if path is blocked by opponent");
    }
    
    @Test
    void testInvalidVerticalMoveBlocked_Down() {
        Rook whiteRook = new Rook(true);
        Pawn friendlyPawn = new Pawn(true);
        boardArray[3][3] = whiteRook;      // White Rook at d5
        boardArray[5][3] = friendlyPawn; // Friendly Pawn at d3 (blocking path to d1)
        assertFalse(whiteRook.isValidMove(3, 3, 7, 3, boardArray), "Rook cannot move vertically down if path is blocked");
    }

    // --- Invalid Moves: Destination Occupied by Friendly ---

    @Test
    void testInvalidMoveToFriendlyPiece_Horizontal() {
        Rook whiteRook = new Rook(true);
        Pawn friendlyPawn = new Pawn(true);
        boardArray[3][3] = whiteRook;      // White Rook at d5
        boardArray[3][7] = friendlyPawn; // Friendly Pawn at h5
        assertFalse(whiteRook.isValidMove(3, 3, 3, 7, boardArray), "Rook cannot move to a square occupied by a friendly piece");
    }
    
    @Test
    void testInvalidMoveToFriendlyPiece_Vertical() {
        Rook whiteRook = new Rook(true);
        Pawn friendlyPawn = new Pawn(true);
        boardArray[3][3] = whiteRook;      // White Rook at d5
        boardArray[0][3] = friendlyPawn; // Friendly Pawn at d8
        assertFalse(whiteRook.isValidMove(3, 3, 0, 3, boardArray), "Rook cannot move to a square occupied by a friendly piece vertically");
    }


    // --- Invalid Moves: Wrong Pattern / Out of Bounds ---

    @Test
    void testInvalidDiagonalMove() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertFalse(whiteRook.isValidMove(3, 3, 4, 4, boardArray), "Rook cannot move diagonally");
    }

    @Test
    void testInvalidKnightLikeMove() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertFalse(whiteRook.isValidMove(3, 3, 5, 4, boardArray), "Rook cannot move like a knight");
    }
    
    @Test
    void testMoveOutOfBounds_RowTooHigh() {
        Rook whiteRook = new Rook(true);
        boardArray[0][0] = whiteRook; // Rook at a8
        assertFalse(whiteRook.isValidMove(0, 0, -1, 0, boardArray), "Rook cannot move out of bounds (row -1)");
    }

    @Test
    void testMoveOutOfBounds_ColTooHigh() {
        Rook whiteRook = new Rook(true);
        boardArray[0][7] = whiteRook; // Rook at h8
        assertFalse(whiteRook.isValidMove(0, 7, 0, 8, boardArray), "Rook cannot move out of bounds (col 8)");
    }
    
    @Test
    void testMoveToSameSquare() {
        Rook whiteRook = new Rook(true);
        boardArray[3][3] = whiteRook; // Rook at d5
        assertFalse(whiteRook.isValidMove(3, 3, 3, 3, boardArray), "Rook cannot move to its current square");
    }
}
