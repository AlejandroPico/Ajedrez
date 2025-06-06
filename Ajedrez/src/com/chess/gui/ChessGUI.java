package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import com.chess.game.ChessGame; 
import com.chess.logic.GameStatus; 

public class ChessGUI {
    private JFrame frame;
    private JPanel boardPanel; 
    private JLabel statusLabel;  
    private ChessGame chessGame; 
    private BoardRenderer boardRenderer; 

    public ChessGUI() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 750); 
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(8, 8));
        frame.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Welcome to Chess!", SwingConstants.CENTER);
        statusLabel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        statusLabel.setFont(new Font("Serif", Font.BOLD, 20));
        frame.add(statusLabel, BorderLayout.SOUTH);
        
        boolean playWhite = showSideSelectionDialog();
        String playerName = "Jugador";
        String opponentName = "Oponente";
        if (playWhite) {
            this.chessGame = new ChessGame(playerName, opponentName, this);
        } else {
            this.chessGame = new ChessGame(opponentName, playerName, this);
        }

        // Create BoardRenderer instance and connect it with the game
        this.boardRenderer = new BoardRenderer(boardPanel, this.chessGame);
        
        // 3. Set the BoardRenderer instance on ChessGame
        this.chessGame.setBoardRenderer(this.boardRenderer);
        // This call to setBoardRenderer will trigger initializeGame() inside ChessGame,
        // which then calls boardRenderer.renderBoard() and chessGUI.updateStatusMessage().

        frame.setVisible(true);
        // updateStatusMessage(); // Initial status update is now handled by ChessGame after setBoardRenderer
    }

    public JPanel getBoardPanel() { 
        return boardPanel;
    }

    public void updateStatusMessage() {
        if (chessGame == null || statusLabel == null) {
            return;
        }

        String turnMessage = chessGame.isWhiteTurn() ? "White's Turn" : "Black's Turn";
        GameStatus.Status currentStatusEnum = chessGame.getGameStatus().getCurrentStatus();
        String finalMessage = turnMessage;

        if (chessGame.isGameOver()) {
            if (currentStatusEnum == GameStatus.Status.CHECKMATE) {
                finalMessage = "CHECKMATE! " + (chessGame.isWhiteTurn() ? "Black" : "White") + " wins!";
            } else if (currentStatusEnum == GameStatus.Status.STALEMATE) {
                finalMessage = "STALEMATE! Game is a draw.";
            } else {
                finalMessage = "Game Over: " + currentStatusEnum.toString();
            }
        } else { 
            // The "CHECK!" announcement is primarily handled by System.out.println in ChessGame's
            // updateGameStatusAndCheckEndConditions. For the label, we can check the GameStatus enum.
            // GameStatus.Status.CHECK is one of the enum values.
            if (currentStatusEnum == GameStatus.Status.CHECK) { 
                 finalMessage += " - CHECK!";
            }
            // If ChessGame doesn't explicitly set GameStatus to CHECK, then this label part won't show "CHECK!".
            // The console output from ChessGame would be the primary indicator in that case.
        }
        
        statusLabel.setText(finalMessage);
    }

    private boolean showSideSelectionDialog() {
        Object[] options = { "Blancas", "Negras" };
        int choice = JOptionPane.showOptionDialog(null,
                "Elige con qué bando jugar",
                "Bienvenido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return choice != 1; // 0 -> white, 1 -> black
    }

    private boolean showSideSelectionDialog() {
        Object[] options = { "Blancas", "Negras" };
        int choice = JOptionPane.showOptionDialog(null,
                "Elige con qué bando jugar",
                "Bienvenido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return choice != 1; // 0 -> white, 1 -> black
    }
}
