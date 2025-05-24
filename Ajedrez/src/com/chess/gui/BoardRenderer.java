package com.chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import com.chess.board.ChessBoard;
import com.chess.game.ChessGame; // Added for game logic access
import com.chess.pieces.Piece;
import com.chess.utils.Utils; // Added for algebraic notation

public class BoardRenderer {
    private JPanel boardPanel;
    private JButton[][] squares;
    private ChessBoard chessBoard; // Still needed for direct piece info for rendering
    private ChessGame chessGame;   // Added to interact with game logic (makeMove, isWhiteTurn)
    private PieceImageLoader pieceImageLoader;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private final Color SELECTED_COLOR = Color.GREEN; // Color for selected square highlight
    private final Color DEFAULT_WHITE_COLOR = Color.WHITE;
    private final Color DEFAULT_GRAY_COLOR = Color.LIGHT_GRAY; // Changed for better visibility than Color.GRAY

    public BoardRenderer(JPanel boardPanel, ChessGame chessGame) { // Changed constructor
        System.out.println("Initializing BoardRenderer with ChessGame reference.");
        this.boardPanel = boardPanel;
        this.chessGame = chessGame;
        this.chessBoard = chessGame.getChessBoard(); // Get ChessBoard from ChessGame
        this.squares = new JButton[8][8];
        this.pieceImageLoader = new PieceImageLoader();
        initializeBoardGUI();
    }

    private void initializeBoardGUI() {
        System.out.println("Initializing Board GUI (squares, colors, and listeners)");
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int r = row; // Need final vars for use in listener
                final int c = col;
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(75, 75));
                setSquareDefaultColor(square, r, c); // Set default color

                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        handleSquareClick(r, c);
                    }
                });

                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }
    
    private void setSquareDefaultColor(JButton square, int row, int col) {
        if ((row + col) % 2 == 0) {
            square.setBackground(DEFAULT_WHITE_COLOR);
        } else {
            square.setBackground(DEFAULT_GRAY_COLOR);
        }
    }

    private void handleSquareClick(int row, int col) {
        if (chessGame.isGameOver()) {
            System.out.println("Game is over. No clicks handled.");
            return;
        }

        Piece clickedPiece = chessBoard.getPiece(row, col);

        if (selectedRow == -1) { // No piece currently selected
            if (clickedPiece != null && clickedPiece.isWhite() == chessGame.isWhiteTurn()) {
                // Select this piece
                selectedRow = row;
                selectedCol = col;
                System.out.println("Selected piece at (" + row + "," + col + "): " + Utils.toAlgebraicNotation(row, col));
                highlightSelectedSquare();
            } else {
                System.out.println("Cannot select square (" + row + "," + col + "). Empty or not your piece.");
            }
        } else { // A piece is already selected, this click is for the destination
            if (selectedRow == row && selectedCol == col) { // Clicked the same square again
                // Deselect
                System.out.println("Deselected piece at (" + row + "," + col + ")");
                clearSelection();
                renderBoard(); // Re-render to remove highlight
            } else {
                // Attempt to make a move
                String fromAlg = Utils.toAlgebraicNotation(selectedRow, selectedCol);
                String toAlg = Utils.toAlgebraicNotation(row, col);
                String moveString = fromAlg + toAlg;
                
                System.out.println("Attempting move from (" + selectedRow + "," + selectedCol + ") to (" + row + "," + col + ") -> " + moveString);
                chessGame.makeMove(moveString); // This will trigger a renderBoard via ChessGUI's update mechanism
                
                clearSelection(); 
                // ChessGUI should call renderBoard() and updateStatusMessage() after makeMove
            }
        }
    }

    private void highlightSelectedSquare() {
        // First, reset all square backgrounds to default (icons and borders are handled in renderBoard)
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                 setSquareDefaultColor(squares[r][c], r, c); 
                 // squares[r][c].setBorder(BorderFactory.createEmptyBorder()); // Reset border if using border highlight
            }
        }
        // Then, highlight the selected one's background
        if (selectedRow != -1 && selectedCol != -1) {
            squares[selectedRow][selectedCol].setBackground(SELECTED_COLOR);
        }
    }

    private void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
    }

    public void renderBoard() {
        // System.out.println("BoardRenderer rendering board...");
        highlightSelectedSquare(); // Apply selection highlight before piece icons

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = chessBoard.getPiece(row, col);
                if (piece != null) {
                    String pieceKey = getPieceImageKey(piece);
                    ImageIcon pieceImage = pieceImageLoader.getPieceImage(pieceKey);
                    squares[row][col].setIcon(pieceImage);
                } else {
                    squares[row][col].setIcon(null);
                }
                // Ensure background color is maintained or reset if selection changes
                // This is now handled by highlightSelectedSquare called at the beginning of renderBoard
                if (selectedRow == row && selectedCol == col) {
                     squares[row][col].setBackground(SELECTED_COLOR);
                } else {
                     setSquareDefaultColor(squares[row][col], row, col);
                }
            }
        }
        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private String getPieceImageKey(Piece piece) {
        String color = piece.isWhite() ? "white" : "black";
        String type = piece.getClass().getSimpleName().toLowerCase();
        return color + "_" + type;
    }

    // This method might become redundant if ChessGame always provides the latest ChessBoard
    public void setChessBoard(ChessBoard chessBoard) { 
        this.chessBoard = chessBoard;
        renderBoard();
    }
}
