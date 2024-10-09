package com.chess.game;

import com.chess.gui.BoardRenderer;
import com.chess.gui.PieceImageLoader;
import com.chess.logic.GameStatus;
import com.chess.moves.MoveHistory;
import com.chess.player.Player;
import com.chess.utils.Utils;

public class ChessGame {
    private GameStatus gameStatus; // Represents the current status of the game (e.g., ongoing, checkmate, etc.)
    private Player whitePlayer; // Player controlling the white pieces
    private Player blackPlayer; // Player controlling the black pieces
    private MoveHistory moveHistory; // Keeps track of all moves made during the game
    private BoardRenderer boardRenderer; // Handles the graphical representation of the chess board
    private PieceImageLoader pieceImageLoader; // Loads images for each chess piece

    // Constructor to initialize the chess game with players, board renderer, and piece loader
    public ChessGame(String whitePlayerName, String blackPlayerName, BoardRenderer boardRenderer, PieceImageLoader pieceImageLoader) {
        System.out.println("Initializing ChessGame");
        this.gameStatus = new GameStatus();
        this.whitePlayer = new Player(whitePlayerName, true);
        this.blackPlayer = new Player(blackPlayerName, false);
        this.moveHistory = new MoveHistory();
        this.boardRenderer = boardRenderer;
        this.pieceImageLoader = pieceImageLoader;
        System.out.println("ChessGame initialized with players: " + whitePlayerName + " (White) and " + blackPlayerName + " (Black)");
        initializeBoard(); // Set up the initial board configuration
    }

    // Method to initialize the board with all the chess pieces in their starting positions
    private void initializeBoard() {
        System.out.println("Initializing chess board with pieces");
        // Set pawns on the board
        for (int col = 0; col < 8; col++) {
            System.out.println("Placing black pawn at (1, " + col + ")");
            boardRenderer.setPiece(1, col, pieceImageLoader.getPieceImage("black_pawn")); // Black pawns
            System.out.println("Placing white pawn at (6, " + col + ")");
            boardRenderer.setPiece(6, col, pieceImageLoader.getPieceImage("white_pawn")); // White pawns
        }
        // Set rooks
        System.out.println("Placing black rook at (0, 0)");
        boardRenderer.setPiece(0, 0, pieceImageLoader.getPieceImage("black_rook"));
        System.out.println("Placing black rook at (0, 7)");
        boardRenderer.setPiece(0, 7, pieceImageLoader.getPieceImage("black_rook"));
        System.out.println("Placing white rook at (7, 0)");
        boardRenderer.setPiece(7, 0, pieceImageLoader.getPieceImage("white_rook"));
        System.out.println("Placing white rook at (7, 7)");
        boardRenderer.setPiece(7, 7, pieceImageLoader.getPieceImage("white_rook"));
        // Set knights
        System.out.println("Placing black knight at (0, 1)");
        boardRenderer.setPiece(0, 1, pieceImageLoader.getPieceImage("black_knight"));
        System.out.println("Placing black knight at (0, 6)");
        boardRenderer.setPiece(0, 6, pieceImageLoader.getPieceImage("black_knight"));
        System.out.println("Placing white knight at (7, 1)");
        boardRenderer.setPiece(7, 1, pieceImageLoader.getPieceImage("white_knight"));
        System.out.println("Placing white knight at (7, 6)");
        boardRenderer.setPiece(7, 6, pieceImageLoader.getPieceImage("white_knight"));
        // Set bishops
        System.out.println("Placing black bishop at (0, 2)");
        boardRenderer.setPiece(0, 2, pieceImageLoader.getPieceImage("black_bishop"));
        System.out.println("Placing black bishop at (0, 5)");
        boardRenderer.setPiece(0, 5, pieceImageLoader.getPieceImage("black_bishop"));
        System.out.println("Placing white bishop at (7, 2)");
        boardRenderer.setPiece(7, 2, pieceImageLoader.getPieceImage("white_bishop"));
        System.out.println("Placing white bishop at (7, 5)");
        boardRenderer.setPiece(7, 5, pieceImageLoader.getPieceImage("white_bishop"));
        // Set queens
        System.out.println("Placing black queen at (0, 3)");
        boardRenderer.setPiece(0, 3, pieceImageLoader.getPieceImage("black_queen"));
        System.out.println("Placing white queen at (7, 3)");
        boardRenderer.setPiece(7, 3, pieceImageLoader.getPieceImage("white_queen"));
        // Set kings
        System.out.println("Placing black king at (0, 4)");
        boardRenderer.setPiece(0, 4, pieceImageLoader.getPieceImage("black_king"));
        System.out.println("Placing white king at (7, 4)");
        boardRenderer.setPiece(7, 4, pieceImageLoader.getPieceImage("white_king"));
    }

    // Method to make a move on the chessboard
    public void makeMove(String move) {
        System.out.println("Making move: " + move);
        // Convert the move to coordinates and validate
        if (move.length() == 4) { // Ensure the move is in the correct format, e.g., "e2e4"
            int[] from = Utils.fromAlgebraicNotation(move.substring(0, 2)); // Parse the source coordinates
            int[] to = Utils.fromAlgebraicNotation(move.substring(2, 4)); // Parse the destination coordinates
            System.out.println("Parsed move from: (" + from[0] + ", " + from[1] + ") to: (" + to[0] + ", " + to[1] + ")");
            if (Utils.isWithinBounds(from[0], from[1]) && Utils.isWithinBounds(to[0], to[1])) { // Check if the coordinates are within bounds
                // Check if there is a piece at the source position
                if (boardRenderer.hasPiece(from[0], from[1])) { // Updated to use hasPiece method
                    System.out.println("Piece found at source position: " + move.substring(0, 2));
                    // Update the board renderer to move the piece
                    boardRenderer.movePiece(from[0], from[1], to[0], to[1]);
                    // Add the move to the history if valid
                    moveHistory.addMove(move);
                    System.out.println("Move made: " + move);
                } else {
                    System.out.println("No piece at the source position: " + move.substring(0, 2));
                }
            } else {
                System.out.println("Move is out of bounds: " + move);
            }
        } else {
            System.out.println("Invalid move format: " + move);
        }
    }

    // Method to print the current status of the game
    public void printGameStatus() {
        System.out.println("Current game status: " + gameStatus.getCurrentStatus());
    }

    // Method to print the history of moves made in the game
    public void printMoveHistory() {
        System.out.println("Printing move history");
        moveHistory.printHistory();
    }
}
