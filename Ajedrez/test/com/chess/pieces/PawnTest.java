package com.chess.pieces;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.chess.board.ChessBoard; // Assuming ChessBoard can be instantiated for tests

public class PawnTest {

    private ChessBoard board;
    private Piece[][] boardArray; // To pass to isValidMove

    @BeforeEach
    void setUp() {
        board = new ChessBoard();
        // Initialize an empty board for direct manipulation in tests
        // ChessBoard.initialize() sets up a full game, which might be too much for unit tests.
        // For Pawn tests, we often need specific sparse setups.
        // So, we'll get the board array and manipulate it directly.
        board.initialize(); // To get an initialized 8x8 board array structure
        boardArray = board.getBoardArray(); 
        
        // Clear the board for fresh test setups
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardArray[i][j] = null;
            }
        }
    }

    // --- White Pawn Tests ---

    @Test
    void testWhitePawn_ValidSingleSquareForward_ToEmpty() {
        Pawn whitePawn = new Pawn(true);
        boardArray[6][4] = whitePawn; // White pawn at e2 (row 6, col 4)
        // Intended logic: Pawns move one square forward to an empty square.
        assertTrue(whitePawn.isValidMove(6, 4, 5, 4, boardArray), "White pawn should move one square forward to e3");
    }

    @Test
    void testWhitePawn_InvalidSingleSquareForward_ToOccupiedByWhite() {
        Pawn whitePawn1 = new Pawn(true);
        Pawn whitePawn2 = new Pawn(true);
        boardArray[6][4] = whitePawn1; // White pawn at e2
        boardArray[5][4] = whitePawn2; // Another white pawn at e3
        assertFalse(whitePawn1.isValidMove(6, 4, 5, 4, boardArray), "White pawn cannot move forward to square occupied by own piece");
    }
    
    @Test
    void testWhitePawn_InvalidSingleSquareForward_ToOccupiedByBlack() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[6][4] = whitePawn; // White pawn at e2
        boardArray[5][4] = blackPawn; // Black pawn at e3
        // Standard pawn forward move requires the destination to be empty.
        assertFalse(whitePawn.isValidMove(6, 4, 5, 4, boardArray), "White pawn cannot move forward to square occupied by opponent piece (must capture diagonally)");
    }

    @Test
    void testWhitePawn_ValidDiagonalCapture_ToOpponent() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[6][4] = whitePawn;  // White pawn at e2
        boardArray[5][3] = blackPawn;  // Black pawn at d3 (target for capture)
        assertTrue(whitePawn.isValidMove(6, 4, 5, 3, boardArray), "White pawn should capture black pawn diagonally at d3");
    }
    
    @Test
    void testWhitePawn_ValidDiagonalCapture_ToOpponentOtherSide() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[6][4] = whitePawn;  // White pawn at e2
        boardArray[5][5] = blackPawn;  // Black pawn at f3 (target for capture)
        assertTrue(whitePawn.isValidMove(6, 4, 5, 5, boardArray), "White pawn should capture black pawn diagonally at f3");
    }

    @Test
    void testWhitePawn_InvalidDiagonalCapture_ToEmptySquare() {
        Pawn whitePawn = new Pawn(true);
        boardArray[6][4] = whitePawn; // White pawn at e2
        // Attempt to move to d3 (empty)
        assertFalse(whitePawn.isValidMove(6, 4, 5, 3, boardArray), "White pawn cannot move diagonally to an empty square (no en-passant here)");
    }

    @Test
    void testWhitePawn_InvalidDiagonalCapture_ToFriendlyPiece() {
        Pawn whitePawn1 = new Pawn(true);
        Pawn whitePawn2 = new Pawn(true);
        boardArray[6][4] = whitePawn1; // White pawn at e2
        boardArray[5][3] = whitePawn2; // Another white pawn at d3
        assertFalse(whitePawn1.isValidMove(6, 4, 5, 3, boardArray), "White pawn cannot capture a friendly piece");
    }

    @Test
    void testWhitePawn_InvalidBackwardMove_ForwardOneSquare() {
        Pawn whitePawn = new Pawn(true);
        boardArray[5][4] = whitePawn; // White pawn at e3
        assertFalse(whitePawn.isValidMove(5, 4, 6, 4, boardArray), "White pawn cannot move backward");
    }
    
    @Test
    void testWhitePawn_InvalidBackwardMove_Diagonally() {
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        boardArray[5][4] = whitePawn;  // White pawn at e3
        boardArray[6][3] = blackPawn;  // Black pawn at d2 (potential backward diagonal)
        assertFalse(whitePawn.isValidMove(5, 4, 6, 3, boardArray), "White pawn cannot move backward diagonally");
    }

    // --- Black Pawn Tests ---
    @Test
    void testBlackPawn_ValidSingleSquareForward_ToEmpty() {
        Pawn blackPawn = new Pawn(false);
        boardArray[1][4] = blackPawn; // Black pawn at e7 (row 1, col 4)
        assertTrue(blackPawn.isValidMove(1, 4, 2, 4, boardArray), "Black pawn should move one square forward to e6");
    }

    @Test
    void testBlackPawn_InvalidSingleSquareForward_ToOccupiedByBlack() {
        Pawn blackPawn1 = new Pawn(false);
        Pawn blackPawn2 = new Pawn(false);
        boardArray[1][4] = blackPawn1; // Black pawn at e7
        boardArray[2][4] = blackPawn2; // Another black pawn at e6
        assertFalse(blackPawn1.isValidMove(1, 4, 2, 4, boardArray), "Black pawn cannot move forward to square occupied by own piece");
    }
    
    @Test
    void testBlackPawn_InvalidSingleSquareForward_ToOccupiedByWhite() {
        Pawn blackPawn = new Pawn(false);
        Pawn whitePawn = new Pawn(true);
        boardArray[1][4] = blackPawn; // Black pawn at e7
        boardArray[2][4] = whitePawn; // White pawn at e6
        assertFalse(blackPawn.isValidMove(1, 4, 2, 4, boardArray), "Black pawn cannot move forward to square occupied by opponent piece (must capture diagonally)");
    }

    @Test
    void testBlackPawn_ValidDiagonalCapture_ToOpponent() {
        Pawn blackPawn = new Pawn(false);
        Pawn whitePawn = new Pawn(true);
        boardArray[1][4] = blackPawn;  // Black pawn at e7
        boardArray[2][3] = whitePawn;  // White pawn at d6 (target for capture)
        assertTrue(blackPawn.isValidMove(1, 4, 2, 3, boardArray), "Black pawn should capture white pawn diagonally at d6");
    }

    @Test
    void testBlackPawn_InvalidDiagonalCapture_ToEmptySquare() {
        Pawn blackPawn = new Pawn(false);
        boardArray[1][4] = blackPawn; // Black pawn at e7
        // Attempt to move to d6 (empty)
        assertFalse(blackPawn.isValidMove(1, 4, 2, 3, boardArray), "Black pawn cannot move diagonally to an empty square");
    }

    @Test
    void testBlackPawn_InvalidDiagonalCapture_ToFriendlyPiece() {
        Pawn blackPawn1 = new Pawn(false);
        Pawn blackPawn2 = new Pawn(false);
        boardArray[1][4] = blackPawn1; // Black pawn at e7
        boardArray[2][3] = blackPawn2; // Another black pawn at d6
        assertFalse(blackPawn1.isValidMove(1, 4, 2, 3, boardArray), "Black pawn cannot capture a friendly piece");
    }

    @Test
    void testBlackPawn_InvalidBackwardMove_ForwardOneSquare() {
        Pawn blackPawn = new Pawn(false);
        boardArray[2][4] = blackPawn; // Black pawn at e6
        assertFalse(blackPawn.isValidMove(2, 4, 1, 4, boardArray), "Black pawn cannot move backward");
    }
    
    @Test
    void testPawn_MoveOutOfBounds() {
        Pawn whitePawn = new Pawn(true);
        boardArray[0][0] = whitePawn; // White pawn at a8 (already at edge)
        // Attempt to move off board forward
        assertFalse(whitePawn.isValidMove(0, 0, -1, 0, boardArray), "Pawn cannot move out of bounds (row -1)");
        
        Pawn blackPawn = new Pawn(false);
        boardArray[7][7] = blackPawn; // Black pawn at h1 (already at edge)
        assertFalse(blackPawn.isValidMove(7, 7, 8, 7, boardArray), "Pawn cannot move out of bounds (row 8)");
    }

    @Test
    void testPawn_InvalidHorizontalMove() {
        Pawn whitePawn = new Pawn(true);
        boardArray[6][4] = whitePawn; // White pawn at e2
        assertFalse(whitePawn.isValidMove(6, 4, 6, 5, boardArray), "Pawn cannot move horizontally");
    }

    @Test
    void testPawn_InvalidJumpMove() {
        Pawn whitePawn = new Pawn(true);
        boardArray[6][4] = whitePawn; // White pawn at e2
        // Attempt to jump two squares forward without it being initial move (or to occupied path)
        assertFalse(whitePawn.isValidMove(6, 4, 4, 4, boardArray), "Pawn cannot jump two squares (initial two-square move is not part of this test set's assumed logic)");
    }
}
