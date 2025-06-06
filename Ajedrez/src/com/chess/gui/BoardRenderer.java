package com.chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

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
    private final Color SELECTED_COLOR = Color.YELLOW;
    private final Color MOVE_COLOR = new Color(0, 150, 0);
    private final Color CAPTURE_COLOR = new Color(170, 0, 0);
    private final Color DEFAULT_WHITE_COLOR = Color.WHITE;
    private final Color DEFAULT_BLACK_COLOR = Color.BLACK;
    private final Border DEFAULT_BORDER = BorderFactory.createLineBorder(Color.GRAY);

    public BoardRenderer(JPanel boardPanel, ChessGame chessGame) { // Changed constructor
        this.boardPanel = boardPanel;
        this.chessGame = chessGame;
        this.chessBoard = chessGame.getChessBoard(); // Get ChessBoard from ChessGame
        this.squares = new JButton[8][8];
        this.pieceImageLoader = new PieceImageLoader();
        initializeBoardGUI();
    }

    private void initializeBoardGUI() {
        boardPanel.removeAll();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                final int r = row; // Need final vars for use in listener
                final int c = col;
                JButton square = new JButton();
                square.setPreferredSize(new Dimension(75, 75));
                square.setFocusPainted(false);
                square.setOpaque(true);
                square.setBorder(DEFAULT_BORDER);
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
            square.setBackground(DEFAULT_BLACK_COLOR);
        }
        square.setForeground(Color.BLACK);
        square.setBorder(DEFAULT_BORDER);
    }

    private void handleSquareClick(int row, int col) {
        if (chessGame.isGameOver()) {
            return;
        }

        Piece clickedPiece = chessBoard.getPiece(row, col);

        if (selectedRow == -1) { // No piece currently selected
            if (clickedPiece != null && clickedPiece.isWhite() == chessGame.isWhiteTurn()) {
                selectedRow = row;
                selectedCol = col;
                renderBoard();
            }
        } else { // A piece is already selected, this click is for the destination
            if (selectedRow == row && selectedCol == col) { // Clicked the same square again
                // Deselect
                clearSelection();
                renderBoard();
            } else {
                // Attempt to make a move
                String fromAlg = Utils.toAlgebraicNotation(selectedRow, selectedCol);
                String toAlg = Utils.toAlgebraicNotation(row, col);
                String moveString = fromAlg + toAlg;
                
                chessGame.makeMove(moveString); // ChessGame will update GUI
                clearSelection();
            }
        }
    }

    private void highlightSelectedSquare() {
        if (selectedRow != -1 && selectedCol != -1) {
            squares[selectedRow][selectedCol].setBackground(SELECTED_COLOR);
        }
    }

    private void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
    }

    public void renderBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = chessBoard.getPiece(row, col);
                setSquareDefaultColor(squares[row][col], row, col);
                if (piece != null) {
                    String key = getPieceImageKey(piece);
                    squares[row][col].setIcon(pieceImageLoader.getPieceImage(key));
                } else {
                    squares[row][col].setIcon(null);
                }
            }
        }

        highlightSelectedSquare();
        highlightPossibleMoves();

        boardPanel.revalidate();
        boardPanel.repaint();
    }

    private void highlightPossibleMoves() {
        if (selectedRow == -1 || selectedCol == -1) {
            return;
        }
        Piece piece = chessBoard.getPiece(selectedRow, selectedCol);
        if (piece == null) return;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (piece.isValidMove(selectedRow, selectedCol, r, c, chessBoard.getBoardArray())) {
                    Piece target = chessBoard.getPiece(r, c);
                    if (target == null) {
                        squares[r][c].setBackground(MOVE_COLOR);
                    } else if (target.isWhite() != piece.isWhite()) {
                        squares[r][c].setBackground(CAPTURE_COLOR);
                        squares[r][c].setForeground(Color.RED);
                    }
                }
            }
        }
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
