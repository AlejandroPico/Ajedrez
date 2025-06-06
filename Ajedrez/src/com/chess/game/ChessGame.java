package com.chess.game;

import javax.swing.JPanel; 

import com.chess.board.ChessBoard;
import com.chess.gui.ChessGUI; // Added for callback
import com.chess.gui.BoardRenderer;
import com.chess.logic.GameStatus; 
import com.chess.moves.MoveHistory;
import com.chess.pieces.Piece;
import com.chess.pieces.Pawn; 
import com.chess.pieces.Queen; 
import com.chess.pieces.King; 
import com.chess.pieces.Rook; 
import com.chess.player.Player;
import com.chess.utils.Utils;

public class ChessGame {
    private GameStatus gameStatus;
    private Player whitePlayer;
    private Player blackPlayer;
    private MoveHistory moveHistory;
    private BoardRenderer boardRenderer; // Set via setBoardRenderer by ChessGUI after construction
    private ChessBoard chessBoard;
    private boolean isWhiteTurn;
    private boolean isGameOver; 
    private ChessGUI chessGUI; // Reference to GUI for callbacks

    // Constructor updated to accept ChessGUI
    public ChessGame(String whitePlayerName, String blackPlayerName, ChessGUI chessGUI) {
        System.out.println("Initializing ChessGame with ChessGUI callback...");
        this.gameStatus = new GameStatus();
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.chessGUI = chessGUI; // Store GUI reference
        this.moveHistory = new MoveHistory();
        this.chessBoard = new ChessBoard();
        // BoardRenderer is now set via setBoardRenderer by ChessGUI after both are created
        this.isWhiteTurn = true;
        this.isGameOver = false; 
        System.out.println("ChessGame initialized partially. BoardRenderer to be set.");
        // initializeGame() will be called after BoardRenderer is set.
    }

    // Setter for BoardRenderer
    public void setBoardRenderer(BoardRenderer boardRenderer) {
        this.boardRenderer = boardRenderer;
        System.out.println("BoardRenderer set in ChessGame.");
        // Now that BoardRenderer is set, we can initialize the game (which renders the board)
        initializeGame(); 
        // Initial status update after board is rendered
        if (this.chessGUI != null) { 
            this.chessGUI.updateStatusMessage();
        }
    }


    private void initializeGame() {
        System.out.println("Initializing chess board state via ChessBoard.initialize()...");
        chessBoard.initialize(); 
        System.out.println("Chess board state initialized.");
        if (boardRenderer != null) {
            System.out.println("Requesting BoardRenderer to render the board...");
            boardRenderer.renderBoard();
            System.out.println("Initial board rendered.");
        } else {
            System.out.println("BoardRenderer not set yet in initializeGame.");
        }
    }
    
    // Method to be called by BoardRenderer or after a move is processed
    private void updateGUIAfterMove() {
        if (boardRenderer != null) {
            boardRenderer.renderBoard();
        }
        if (chessGUI != null) {
            chessGUI.updateStatusMessage();
        }
    }

    private int[] findKingPosition(boolean isWhiteKing) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = chessBoard.getPiece(r, c);
                if (p instanceof King && p.isWhite() == isWhiteKing) {
                    return new int[]{r, c};
                }
            }
        }
        return null; 
    }

    private boolean isKingInCheck(boolean kingIsWhite) {
        int[] kingPos = findKingPosition(kingIsWhite);
        if (kingPos == null) return false; 
        try {
            return chessBoard.isSquareAttacked(kingPos[0], kingPos[1], !kingIsWhite);
        } catch (NoSuchMethodError e) {
            System.out.println("CRITICAL ERROR: chessBoard.isSquareAttacked method is missing. Check detection will not work.");
            return false; 
        } catch (Exception e) {
            System.out.println("Exception during isSquareAttacked call: " + e.getMessage());
            return false;
        }
    }

    private boolean hasAnyLegalMoves(boolean forPlayerIsWhite) {
        try {
            if (chessBoard.getBoardArray() == null) { 
                System.out.println("Error in hasAnyLegalMoves: Board array is null.");
                return false; 
            }
        } catch (NoSuchMethodError e) {
             System.out.println("CRITICAL ERROR: chessBoard.getBoardArray method is missing. Cannot check for legal moves.");
             return false; 
        }

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece piece = chessBoard.getPiece(r, c);
                if (piece != null && piece.isWhite() == forPlayerIsWhite) {
                    for (int tr = 0; tr < 8; tr++) { 
                        for (int tc = 0; tc < 8; tc++) { 
                            if (piece.isValidMove(r, c, tr, tc, chessBoard.getBoardArray())) {
                                Piece originalDestinationPiece = chessBoard.getPiece(tr, tc);
                                chessBoard.setPiece(tr, tc, piece);
                                chessBoard.setPiece(r, c, null);
                                boolean resultsInCheck = isKingInCheck(forPlayerIsWhite);
                                chessBoard.setPiece(r, c, piece);
                                chessBoard.setPiece(tr, tc, originalDestinationPiece);
                                if (!resultsInCheck) {
                                    return true; 
                                }
                            }
                        }
                    }
                }
            }
        }
        return false; 
    }
    
    private void updateGameStatusAndCheckEndConditions() {
        // It's now the next player's turn, so isWhiteTurn reflects the player TO MOVE.
        boolean kingCurrentlyInCheck = isKingInCheck(isWhiteTurn);
        boolean currentPlayerHasLegalMoves = hasAnyLegalMoves(isWhiteTurn);

        if (!currentPlayerHasLegalMoves) {
            if (kingCurrentlyInCheck) {
                gameStatus.setCurrentStatus(GameStatus.Status.CHECKMATE);
                System.out.println("CHECKMATE! " + (isWhiteTurn ? "Black" : "White") + " wins."); // Winner is opposite of current turn
                isGameOver = true;
            } else {
                gameStatus.setCurrentStatus(GameStatus.Status.STALEMATE);
                System.out.println("STALEMATE! The game is a draw.");
                isGameOver = true;
            }
        } else if (kingCurrentlyInCheck) {
            System.out.println((isWhiteTurn ? "White" : "Black") + " is in CHECK!");
        }
    }


    public void makeMove(String move) {
        if (isGameOver) {
            System.out.println("Game is over. No more moves allowed. Status: " + gameStatus.getCurrentStatus());
            updateGUIAfterMove(); // Ensure UI reflects final status
            return;
        }

        System.out.println("Attempting to make move: " + move + " (" + (isWhiteTurn ? "White" : "Black") + "'s turn)");
        
        if (move == null || move.length() != 4) {
            System.out.println("Invalid move format. Please use 'e2e4'.");
            updateGUIAfterMove(); 
            return;
        }
        String fromAlg = move.substring(0, 2);
        String toAlg = move.substring(2, 4);
        int[] fromCoords;
        int[] toCoords;
        try {
            fromCoords = Utils.fromAlgebraicNotation(fromAlg);
            toCoords = Utils.fromAlgebraicNotation(toAlg);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid algebraic notation in move: " + move + ". Error: " + e.getMessage());
            updateGUIAfterMove();
            return;
        }
        int fromRow = fromCoords[0];
        int fromCol = fromCoords[1];
        int toRow = toCoords[0];
        int toCol = toCoords[1];
        if (!Utils.isWithinBounds(fromRow, fromCol) || !Utils.isWithinBounds(toRow, toCol)) {
            System.out.println("Move coordinates are out of bounds.");
            updateGUIAfterMove();
            return;
        }
        Piece pieceToMove = chessBoard.getPiece(fromRow, fromCol);
        if (pieceToMove == null) {
            System.out.println("No piece at source square: " + fromAlg);
            updateGUIAfterMove();
            return;
        }
        if ((pieceToMove.isWhite() && !isWhiteTurn) || (!pieceToMove.isWhite() && isWhiteTurn)) {
            System.out.println("Error: It's not " + (pieceToMove.isWhite() ? "Black's" : "White's") + " turn.");
            updateGUIAfterMove();
            return;
        }

        boolean isPawnTwoSquareAdvance = (pieceToMove instanceof Pawn && Math.abs(toRow - fromRow) == 2);
        if (!isPawnTwoSquareAdvance) { 
             try { chessBoard.setEnPassantTargetSquare(null); } catch (Error e) { /* Method missing */ }
        }

        if (pieceToMove.isValidMove(fromRow, fromCol, toRow, toCol, chessBoard.getBoardArray())) {
            Piece originalDestinationPiece = chessBoard.getPiece(toRow, toCol); 
            chessBoard.setPiece(toRow, toCol, pieceToMove); 
            chessBoard.setPiece(fromRow, fromCol, null);
            boolean selfCheck = isKingInCheck(isWhiteTurn); 
            chessBoard.setPiece(fromRow, fromCol, pieceToMove);
            chessBoard.setPiece(toRow, toCol, originalDestinationPiece);
            if (selfCheck) {
                System.out.println("Invalid move: " + move + " leaves your King in check.");
                updateGUIAfterMove(); 
                return;
            }
            Piece capturedPieceActual = chessBoard.getPiece(toRow, toCol); 
            chessBoard.movePiece(fromRow, fromCol, toRow, toCol); 
            if (capturedPieceActual != null && capturedPieceActual.isWhite() != pieceToMove.isWhite()) {
                 System.out.println("Captured " + capturedPieceActual.getClass().getSimpleName() + " at " + toAlg);
            }
            moveHistory.addMove(move);
            if (pieceToMove instanceof King ) { 
                try { /* ((King)pieceToMove).setHasMoved(true); */ } catch (Error e) { /* Method missing */ }
            } else if (pieceToMove instanceof Rook ) {
                try { ((Rook)chessBoard.getPiece(toRow, toCol)).setHasMoved(true); } catch (Error e) { /* Method missing */ }
            }
            if (isPawnTwoSquareAdvance) { 
                try {
                    // int enPassantRow = fromRow + ((Pawn) pieceToMove).getDirection(); 
                    // chessBoard.setEnPassantTargetSquare(new int[]{enPassantRow, toCol});
                } catch (Error e) { /* Method missing */ }
            } 
            Piece movedPiece = chessBoard.getPiece(toRow, toCol); 
            if (movedPiece instanceof Pawn) { 
                boolean whitePromotes = movedPiece.isWhite() && toRow == 0;
                boolean blackPromotes = !movedPiece.isWhite() && toRow == 7;
                if (whitePromotes || blackPromotes) {
                    chessBoard.setPiece(toRow, toCol, new Queen(movedPiece.isWhite()));
                    System.out.println("Pawn promoted to Queen at " + toAlg + "!");
                }
            }
            
            isWhiteTurn = !isWhiteTurn; 
            System.out.println("Move successful. It is now " + (isWhiteTurn ? "White's" : "Black's") + " turn.");
            updateGameStatusAndCheckEndConditions(); // Check for check/checkmate/stalemate AFTER turn switch
        } else {
            System.out.println("Invalid move for " + pieceToMove.getClass().getSimpleName() + " from " + fromAlg + " to " + toAlg + " according to its isValidMove method.");
        }
        updateGUIAfterMove(); // Central place to update UI after any move attempt outcome
    }

    public void printGameStatus() {
        System.out.println("Current game status: " + gameStatus.getCurrentStatus() + ". Turn: " + (isWhiteTurn ? "White" : "Black"));
        chessBoard.printBoard(); 
    }

    public void printMoveHistory() {
        System.out.println("Printing move history:");
        moveHistory.printHistory();
    }

    public BoardRenderer getBoardRenderer() { 
        return boardRenderer;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }

    public GameStatus getGameStatus() {
        return this.gameStatus;
    }
}
